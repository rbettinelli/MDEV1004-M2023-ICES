// -------------------------------------------------------------
// - Robert Bettinelli - MDEV1004 - S2023
// - 090003683@student.georgianc.on.ca
// -------------------------------------------------------------
// (Controllers) user.ts - Split user from Movie Controller.
// -------------------------------------------------------------
// 08/12/2023 - RBettinelli - Header and Documentation Added
// -------------------------------------------------------------

import mongoose from "mongoose";
const Schema = mongoose.Schema;
import passportLocalMongoose from "passport-local-mongoose";
import { GenerateToken } from "../Util/index";

// User Data Structure
interface IUser {
  username: String;
  emailAddress: String;
  displayName: String;
  created: Date;
  updated: Date;
}

// Data Schema for Mongo
const UserSchema = new Schema<IUser>(
  {
    username: { type: String, required: true },
    emailAddress: { type: String, required: true },
    displayName: { type: String, required: true },
    created: {
      type: Date,
      default: Date.now(),
    },
    updated: {
      type: Date,
      default: Date.now(),
    },
  },
  {
    collection: "users",
  }
);
UserSchema.plugin(passportLocalMongoose);
const Model = mongoose.model<IUser>("User", UserSchema);

declare global {
  export type UserDocument = mongoose.Document & {
    _id: String;
    username: String;
    emailAddress: String;
    displayName: String;
  };
}
export default Model;
