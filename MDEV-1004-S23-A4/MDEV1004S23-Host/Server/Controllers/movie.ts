// -------------------------------------------------------------
// - Robert Bettinelli - MDEV1004 - S2023 - Assignment#1
// - 090003683@student.georgianc.on.ca
// -------------------------------------------------------------
// (Controllers) movie.ts - As Provided in Class Instruction
// Personally entered and followed as pert of in class learning.
// -------------------------------------------------------------
// 06/10/2023 - RBettinelli - Header and Documentation Added
// -------------------------------------------------------------

import { Request, Response, NextFunction } from "express";
import mongoose from "mongoose";
import Movie from "../Models/movie";

// UTILITY
// Takes Array and removes spaces @ Front and End
function SanitizeArray(unsanitizedValue: string | string[]): string[] {
  if (Array.isArray(unsanitizedValue)) {
    return unsanitizedValue.map((value) => value.trim());
  } else if (typeof unsanitizedValue === "string") {
    return unsanitizedValue.split(",").map((value) => value.trim());
  } else {
    return [];
  }
}

// API FUNCTIONS

// Pull All Mongo Movies Database Documents and Outputs.
export function DisplayMovieList(
  req: Request,
  res: Response,
  next: NextFunction
): void {
  Movie.find({})
    .sort({ movieID: 1 })
    .then(function (data) {
      res
        .status(200)
        .json({ success: true, msg: "Full List Found.", data: data });
    })
    .catch(function (err) {
      console.error(err);
      res
        .status(404)
        .json({ success: false, msg: "ERROR: No List Data.", data: null });
    });
}

// Pull All Mongo Movie Titles Database Documents and Outputs.
export function DisplayMovieListTitle(
  req: Request,
  res: Response,
  next: NextFunction
): void {
  Movie.find({}, { movieID: 1, title: 1 })
    .sort({ movieID: 1 })
    .then(function (data) {
      res
        .status(200)
        .json({ success: true, msg: "Title List Found.", data: data });
    })
    .catch(function (err) {
      console.error(err);
      res.status(404).json({
        success: false,
        msg: "ERROR: No Title List Data.",
        data: null,
      });
    });
}

// Find Movie by ID in MongoDB and Outputs.
export function DisplayMovieByID(
  req: Request,
  res: Response,
  next: NextFunction
): void {
  try {
    let id = req.params.id;
    Movie.findById({ _id: id })
      .then(function (data) {
        if (data) {
          res
            .status(200)
            .json({ success: true, msg: "Movie Found.", data: data });
        } else {
          res
            .status(404)
            .json({ success: false, msg: "ERROR: No Movie Data.", data: data });
        }
      })
      .catch(function (err) {
        console.error(err);
        res.status(400).json({
          success: false,
          msg: "ERROR: Server Error - Check ID ? - Bad Request",
          data: null,
        });
      });
  } catch (err) {
    console.error(err);
    res
      .status(500)
      .json({ success: false, msg: "ERROR: Server Error.", data: null });
  }
}

// Add Movie to MongoDB and Returns Move output.
export function AddMovie(
  req: Request,
  res: Response,
  next: NextFunction
): void {
  // This section will take in-line Entry and Splits then Sanitizes
  // For unlimited Array of items.
  try {
    let genres = SanitizeArray(req.body.genres as string);
    let directors = SanitizeArray(req.body.directors as string);
    let writers = SanitizeArray(req.body.writers as string);
    let actors = SanitizeArray(req.body.actors as string);

    let parsedCriticRating = parseFloat(req.body.criticsRating);
    let criticsRating = isNaN(parsedCriticRating) ? 0.0 : parsedCriticRating;

    let parsedID = parseInt(req.body.movieID);
    let pid = isNaN(parsedID) ? 0 : parsedID;

    let parsedYear = parseInt(req.body.year);
    let year = isNaN(parsedYear) ? 0 : parsedYear;

    // Populates movie with data from API.
    let movie = new Movie({
      movieID: pid,
      title: req.body.title,
      studio: req.body.studio,
      genres: genres,
      directors: directors,
      writers: writers,
      actors: actors,
      length: req.body.length,
      year: year,
      shortDescription: req.body.shortDescription,
      mpaRating: req.body.mpaRating,
      criticsRating: criticsRating,
      posterLink: req.body.posterLink,
    });

    // Post Data.
    Movie.create(movie)
      .then(function () {
        res
          .status(200)
          .json({ success: true, msg: "Movie Added.", data: movie });
      })
      .catch(function (err) {
        console.error(err);
        if (err instanceof mongoose.Error.ValidationError) {
          res.status(400).json({
            success: false,
            msg: "ERROR: Movie not added. All Fields Required",
            data: null,
          });
        } else {
          res.status(400).json({
            success: false,
            msg: "ERROR: Movie not added. Other Error.",
            data: null,
          });
        }
      });
  } catch (err) {
    console.error(err);
    res
      .status(500)
      .json({ success: false, msg: "ERROR: Server Error", data: null });
  }
}

// See ADD functionality Above with the addition it only updated by _id
export function UpdateMovie(
  req: Request,
  res: Response,
  next: NextFunction
): void {
  try {
    let id = req.params.id;
    let genres = SanitizeArray(req.body.genres as string);
    let directors = SanitizeArray(req.body.directors as string);
    let writers = SanitizeArray(req.body.writers as string);
    let actors = SanitizeArray(req.body.actors as string);

    let parsedCriticRating = parseFloat(req.body.criticsRating);
    let criticsRating = isNaN(parsedCriticRating) ? 0.0 : parsedCriticRating;

    let parsedID = parseInt(req.body.movieID);
    let pid = isNaN(parsedID) ? 0 : parsedID;

    let parsedYear = parseInt(req.body.year);
    let year = isNaN(parsedYear) ? 0 : parsedYear;

    let movieToUpdate = new Movie({
      _id: id,
      movieID: pid,
      title: req.body.title,
      studio: req.body.studio,
      genres: genres,
      directors: directors,
      writers: writers,
      actors: actors,
      length: req.body.length,
      year: year,
      shortDescription: req.body.shortDescription,
      mpaRating: req.body.mpaRating,
      criticsRating: criticsRating,
      posterLink: req.body.posterLink,
    });

    Movie.updateOne({ _id: id }, movieToUpdate)
      .then(function () {
        res
          .status(200)
          .json({ success: true, msg: "Movie Updated.", data: movieToUpdate });
        console.log("Updated!");
      })
      .catch(function (err) {
        console.error(err);
        if (err instanceof mongoose.Error.ValidationError) {
          res.status(400).json({
            success: false,
            msg: "ERROR: Movie not updated. All Fields Required",
            data: null,
          });
        } else {
          res.status(400).json({
            success: false,
            msg: "ERROR: Movie not updated. id Bad?",
            data: null,
          });
        }
      });
  } catch (err) {
    console.error(err);
    res
      .status(500)
      .json({ success: false, msg: "ERROR: Server Error", data: null });
  }
}

// Delete movie based on _id
export function DeleteMovie(
  req: Request,
  res: Response,
  next: NextFunction
): void {
  let id = req.params.id;
  try {
    Movie.deleteOne({ _id: id })
      .then(function () {
        res
          .status(200)
          .json({ success: true, msg: "Movie Removed.", data: id });
      })
      .catch(function (err) {
        res.status(400).json({
          success: false,
          msg: "ERROR: Bad Id? Cant Complete Removal.",
          data: null,
        });
        console.error(err);
      });
  } catch (err) {
    console.error(err);
    res
      .status(500)
      .json({ success: false, msg: "ERROR: Server Error", data: null });
  }
}
