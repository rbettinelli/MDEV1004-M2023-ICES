// -------------------------------------------------------------
// - Robert Bettinelli - MDEV1004 - S2023
// - 090003683@student.georgianc.on.ca
// -------------------------------------------------------------
// (Controllers) login.ts - As Provided in Class Instruction
// Personally entered and followed as pert of in class learning.
// -------------------------------------------------------------
// 06/12/2023 - RBettinelli - Authentication Creation.
// -------------------------------------------------------------

// AUTHENTICATION
import { Request, Response, NextFunction } from "express";
import User from "../Models/user";
import passport from "passport";
import mongoose from "mongoose";
import { GenerateToken } from "../Util/index";

export function ProcessRegistration(
  req: Request,
  res: Response,
  next: NextFunction
): void {
  // instantiate a new user object
  let newUser = new User({
    username: req.body.username,
    emailAddress: req.body.EmailAddress,
    displayName: req.body.FirstName + " " + req.body.LastName,
    password: req.body.password,
  });

  User.register(newUser, req.body.password, (err) => {
    if (err instanceof mongoose.Error.ValidationError) {
      console.error("ERROR: All Fields Required.");
      return res.json({
        success: false,
        msg: "ERROR: User Registration error. All Fields Req'd.",
      });
    }

    return res.json({
      success: true,
      msg: "User Registered. - Now Log in.",
    });
  });
}

export function ProcessLogin(
  req: Request,
  res: Response,
  next: NextFunction
): void {
  passport.authenticate("local", (err: any, user: any, info: any) => {
    // are there server errors?
    if (err) {
      console.error(err);
      return next(err);
    }
    // are the login errors?
    if (!user) {
      return res.json({ success: false, msg: "ERROR: User Not Logged in." });
    }

    req.logIn(user, (err) => {
      // are there db errors?
      if (err) {
        console.error(err);
        res.end(err);
      }

      const authToken = GenerateToken(user);

      return res.json({
        success: true,
        msg: "User Logged In Successfully!",
        user: {
          id: user._id,
          displayName: user.displayName,
          username: user.username,
          emailAddress: user.emailAddress,
        },
        token: authToken,
      });
    });
    return;
  })(req, res, next);
}

export function ProcessLogout(
  req: Request,
  res: Response,
  next: NextFunction
): void {
  req.logout(() => {
    console.log("User Logged Out");
  });

  // if we had a front-end (Angular, React or Mobile UI)...
  res.json({ success: true, msg: "User Logged out Successfully!" });
}
