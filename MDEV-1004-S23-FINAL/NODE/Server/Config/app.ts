// -------------------------------------------------------------
// - Robert Bettinelli - MDEV1004 - S2023
// - 090003683@student.georgianc.on.ca
// -------------------------------------------------------------
// (Config) app.ts - As Provided in Class Instruction
// Personally entered and followed as pert of in class learning.
// -------------------------------------------------------------
// 06/10/2023 - RBettinelli - Header and Documentation Added
// 06/12/2023 - RBettinelli - Added Authentication Learned
// -------------------------------------------------------------

import express from "express";
import cookieParser from "cookie-parser";
import logger from "morgan";
import indexRouter from "../Routes/index";
import mongoose from "mongoose";
import db from "./db";

// Modules for Auth.
import session from "express-session";
import passport from "passport";
import passportLocal from "passport-local";

// modules for jwt support
import cors from "cors";
import passportJWT from "passport-jwt";

// define JWT aliases
let JWTStrategy = passportJWT.Strategy;
let ExtractJWT = passportJWT.ExtractJwt;

// authentication objects
let localStrategy = passportLocal.Strategy; // alias
import User from "../Models/user";

// Mongoose Connection Functionality
// db.remoteURI - MongoDB Atlas
// db.localURI  - MongoDB LocalHost
mongoose.connect(db.remoteURI);
mongoose.connection.on("connected", function () {
  console.log(`connected to mongo.`);
});
mongoose.connection.on("disconnected", function () {
  console.log(`mongo disconnected.`);
});

// App configuration setup Base Access Point.
let app = express();

// Middleware
app.use(logger("dev"));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(cors());

// setup express session
app.use(
  session({
    secret: db.secret,
    saveUninitialized: false,
    resave: false,
  })
);

app.use(passport.initialize());
app.use(passport.session());

// implement an Auth Strategy
passport.use(User.createStrategy());

// serialize and deserialize user data
// Please note Adjustment to serialUser Type Definition to User:any !
passport.serializeUser(User.serializeUser() as any);
passport.deserializeUser(User.deserializeUser());

// passport.use(strategy); <- Not Required.

// setup JWT Options
let jwtOptions = {
  jwtFromRequest: ExtractJWT.fromAuthHeaderAsBearerToken(),
  secretOrKey: db.secret,
};

// setup JWT Strategy
let strategy = new JWTStrategy(jwtOptions, function (jwt_payload, done) {
  try {
    const user = User.findById(jwt_payload.id);
    if (user) {
      return done(null, user);
    }
    return done(null, false);
  } catch (error) {
    return done(error, false);
  }
});

passport.use(strategy);
// Default Base Route
app.use("/api/", indexRouter);

export default app;
