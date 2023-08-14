// -------------------------------------------------------------
// - Robert Bettinelli - MDEV1004 - S2023
// - 090003683@student.georgianc.on.ca
// -------------------------------------------------------------
// (Controllers) movie.ts - As Provided in Class Instruction
// Personally entered and followed as pert of in class learning.
// -------------------------------------------------------------
// 06/10/2023 - RBettinelli - Header and Documentation Added
// -------------------------------------------------------------

import { Request, Response, NextFunction } from "express";
import mongoose from "mongoose";
import MyItem from "../Models/item";

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

// Pull All Mongo Database Documents and Outputs.
export function DisplayList(
  req: Request,
  res: Response,
  next: NextFunction
): void {
  MyItem.find({})
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

// Pull All Mongo Item Database Documents and Outputs.
export function DisplayListItems(
  req: Request,
  res: Response,
  next: NextFunction
): void {
  MyItem.find({}, { name: 1, title: 1 })
    .then(function (data) {
      res.status(200).json({ success: true, msg: "List Found.", data: data });
    })
    .catch(function (err) {
      console.error(err);
      res.status(404).json({
        success: false,
        msg: "ERROR: No List Data.",
        data: null,
      });
    });
}

// Find Movie by ID in MongoDB and Outputs.
export function DisplayItemByID(
  req: Request,
  res: Response,
  next: NextFunction
): void {
  try {
    let id = req.params.id;
    MyItem.findById({ _id: id })
      .then(function (data) {
        if (data) {
          res
            .status(200)
            .json({ success: true, msg: "Item Found.", data: data });
        } else {
          res
            .status(404)
            .json({ success: false, msg: "ERROR: No Item Data.", data: data });
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
export function AddItem(req: Request, res: Response, next: NextFunction): void {
  // This section will take in-line Entry and Splits then Sanitizes
  // For unlimited Array of items.
  try {
    let architects = SanitizeArray(req.body.architects as string);

    // Populates movie with data from API.
    let item = new MyItem({
      name: req.body.name,
      type: req.body.type,
      dateBuilt: req.body.dateBuilt,
      city: req.body.city,
      country: req.body.country,
      description: req.body.description,
      architects: architects,
      cost: req.body.cost,
      website: req.body.website,
      imageURL: req.body.imageURL,
    });

    // Post Data.
    MyItem.create(item)
      .then(function () {
        res.status(200).json({ success: true, msg: "Item Added.", data: item });
      })
      .catch(function (err) {
        console.error(err);
        if (err instanceof mongoose.Error.ValidationError) {
          res.status(400).json({
            success: false,
            msg: "ERROR: Item not added. All Fields Required",
            data: null,
          });
        } else {
          res.status(400).json({
            success: false,
            msg: "ERROR: Item not added. Other Error.",
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
export function UpdateItem(
  req: Request,
  res: Response,
  next: NextFunction
): void {
  try {
    let id = req.params.id;
    let architects = SanitizeArray(req.body.architects as string);

    // Populates movie with data from API.
    let itemToUpdate = new MyItem({
      _id: id,
      name: req.body.name,
      type: req.body.type,
      dateBuilt: req.body.dateBuilt,
      city: req.body.city,
      country: req.body.country,
      description: req.body.description,
      architects: architects,
      cost: req.body.cost,
      website: req.body.website,
      imageURL: req.body.imageURL,
    });

    MyItem.updateOne({ _id: id }, itemToUpdate)
      .then(function () {
        res
          .status(200)
          .json({ success: true, msg: "Item Updated.", data: itemToUpdate });
        console.log("Updated!");
      })
      .catch(function (err) {
        console.error(err);
        if (err instanceof mongoose.Error.ValidationError) {
          res.status(400).json({
            success: false,
            msg: "ERROR: Item not updated. All Fields Required",
            data: null,
          });
        } else {
          res.status(400).json({
            success: false,
            msg: "ERROR: Item not updated. id Bad?",
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
export function DeleteItem(
  req: Request,
  res: Response,
  next: NextFunction
): void {
  let id = req.params.id;
  try {
    MyItem.deleteOne({ _id: id })
      .then(function () {
        res.status(200).json({ success: true, msg: "Item Removed.", data: id });
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
