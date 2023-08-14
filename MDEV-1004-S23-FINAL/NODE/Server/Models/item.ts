// -------------------------------------------------------------
// - Robert Bettinelli - MDEV1004 - S2023
// - 090003683@student.georgianc.on.ca
// -------------------------------------------------------------
// (Models) movie.ts - As Provided in Class Instruction
// Personally entered and followed as pert of in class learning.
// -------------------------------------------------------------
// 06/10/2023 - RBettinelli - Header and Documentation Added
// -------------------------------------------------------------

import { Schema, model } from "mongoose";

// Interface for Collecting data in specific types.
interface ImyItem {
  name: string;
  type: string;
  dateBuilt: string;
  city: string;
  country: string;
  description: string;
  architects: string[];
  cost: string;
  website: string;
  imageURL: string;
}

// Mongo DB schema Setup
const itemSchema = new Schema<ImyItem>({
  name: {
    type: String,
    required: true,
  },
  type: {
    type: String,
    required: true,
  },
  dateBuilt: {
    type: String,
    required: true,
  },
  city: {
    type: String,
    required: true,
  },
  country: {
    type: String,
    required: true,
  },
  description: {
    type: String,
    required: true,
  },
  architects: {
    type: [String],
    required: true,
  },
  cost: {
    type: String,
    required: true,
  },
  website: {
    type: String,
    required: true,
  },
  imageURL: {
    type: String,
    required: true,
  },
});

// Build Model Object as Interface utilizing Schema.
let MyItem = model<ImyItem>("buildings", itemSchema);

export default MyItem;
