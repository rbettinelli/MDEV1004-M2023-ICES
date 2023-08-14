// -------------------------------------------------------------
// - Robert Bettinelli - MDEV1004 - S2023
// - 090003683@student.georgianc.on.ca
// -------------------------------------------------------------
// (Routes) index.ts - As Provided in Class Instruction
// Personally entered and followed as pert of in class learning.
// -------------------------------------------------------------
// 06/10/2023 - RBettinelli - Header and Documentation Added
// 06/12/2023 - RBettinelli - Added Login.
// -------------------------------------------------------------

import express, { response } from "express";
let router = express.Router();
import passport from "passport";

import {
  DisplayList,
  DisplayItemByID,
  AddItem,
  UpdateItem,
  DeleteItem,
  DisplayListItems,
} from "../Controllers/item";
import {
  ProcessRegistration,
  ProcessLogin,
  ProcessLogout,
} from "../Controllers/login";

// Item Complete List Route (Secured)
router.get(
  "/list",
  passport.authenticate("jwt", { session: false }),
  (req, res, next) => DisplayList(req, res, next)
);

// Movie Summary List Items (UnSecured)
router.get("/listItems", (req, res, next) => DisplayListItems(req, res, next));

// Find By ID Route
router.get(
  "/find/:id",
  passport.authenticate("jwt", { session: false }),
  (req, res, next) => DisplayItemByID(req, res, next)
);

// Add Document Route
router.post(
  "/add",
  passport.authenticate("jwt", { session: false }),
  (req, res, next) => AddItem(req, res, next)
);

// Delete By ID Route
router.delete(
  "/delete/:id",
  passport.authenticate("jwt", { session: false }),
  (req, res, next) => DeleteItem(req, res, next)
);

// Update Document By ID Route
router.put(
  "/update/:id",
  passport.authenticate("jwt", { session: false }),
  (req, res, next) => UpdateItem(req, res, next)
);

// AUTHENTICATE

router.post("/register", function (req, res, next) {
  ProcessRegistration(req, res, next);
});

router.post("/login", function (req, res, next) {
  ProcessLogin(req, res, next);
});

router.get("/logout", function (req, res, next) {
  ProcessLogout(req, res, next);
});

export default router;
