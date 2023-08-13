"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
let router = express_1.default.Router();
const passport_1 = __importDefault(require("passport"));
const movie_1 = require("../Controllers/movie");
const login_1 = require("../Controllers/login");
const stripe_1 = require("../Controllers/stripe");
router.get("/list", passport_1.default.authenticate("jwt", { session: false }), (req, res, next) => (0, movie_1.DisplayMovieList)(req, res, next));
router.get("/listTitle", (req, res, next) => (0, movie_1.DisplayMovieListTitle)(req, res, next));
router.get("/find/:id", passport_1.default.authenticate("jwt", { session: false }), (req, res, next) => (0, movie_1.DisplayMovieByID)(req, res, next));
router.post("/add", passport_1.default.authenticate("jwt", { session: false }), (req, res, next) => (0, movie_1.AddMovie)(req, res, next));
router.delete("/delete/:id", passport_1.default.authenticate("jwt", { session: false }), (req, res, next) => (0, movie_1.DeleteMovie)(req, res, next));
router.put("/update/:id", passport_1.default.authenticate("jwt", { session: false }), (req, res, next) => (0, movie_1.UpdateMovie)(req, res, next));
router.post("/register", function (req, res, next) {
    (0, login_1.ProcessRegistration)(req, res, next);
});
router.post("/login", function (req, res, next) {
    (0, login_1.ProcessLogin)(req, res, next);
});
router.get("/logout", function (req, res, next) {
    (0, login_1.ProcessLogout)(req, res, next);
});
router.post("/payment", function (req, res, next) {
    (0, stripe_1.CreatePaymentIntent)(req, res, next);
});
exports.default = router;
//# sourceMappingURL=index.js.map