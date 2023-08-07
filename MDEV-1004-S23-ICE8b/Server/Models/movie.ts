// -------------------------------------------------------------
// - Robert Bettinelli - MDEV1004 - S2023 - Assignment#1
// - 090003683@student.georgianc.on.ca
// -------------------------------------------------------------
// (Models) movie.ts - As Provided in Class Instruction
// Personally entered and followed as pert of in class learning.
// -------------------------------------------------------------
// 06/10/2023 - RBettinelli - Header and Documentation Added
// -------------------------------------------------------------

import { Schema, model } from "mongoose";

// Movie interface for Collecting data in specific types. 
interface IMovie {
    movieID: number,
    title: string,
    studio: string,
    genres: string[],
    directors: string[],
    writers: string[],
    actors: string[],
    year: number,
    length: string,
    shortDescription: string,
    mpaRating: string,
    criticsRating: number,
    posterLink: string
}

// Mongo DB schema Setup
const movieSchema = new Schema<IMovie>({
    movieID: {
      type: Number,
      required: true,
    },
    title: {
      type: String,
      required: true,
    },
    studio: {
      type: String,
      required: true,
    },
    genres: {
      type: [String],
      required: true,
    },
    directors: {
      type: [String],
      required: true,
    },
    writers: {
      type: [String],
      required: true,
    },
    actors: {
      type: [String],
      required: true,
    },
    year: {
      type: Number,
      required: true, 
    },
    length: {
      type: String,
      required: true, 
    },
    shortDescription: {
      type: String,
      required: true, 
    },
    mpaRating: {
      type: String,
      required: true, 
    },
    criticsRating: {
      type: Number,
      required: true, 
    },
    posterLink: {
      type: String,
      required: true, 
    },
  });

// Build Model Object as Interface utilizing Schema. 
let Movie = model<IMovie>('movies', movieSchema);

export default Movie;
