"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.DeleteMovie = exports.UpdateMovie = exports.AddMovie = exports.DisplayMovieByID = exports.DisplayMovieListTitle = exports.DisplayMovieList = void 0;
const mongoose_1 = __importDefault(require("mongoose"));
const movie_1 = __importDefault(require("../Models/movie"));
function SanitizeArray(unsanitizedString) {
    if (unsanitizedString == null || unsanitizedString == undefined) {
        return Array();
    }
    let unsanitizedArray = unsanitizedString.split(",");
    let sanitizedArray = Array();
    for (const unsanitizedString of unsanitizedArray) {
        sanitizedArray.push(unsanitizedString.trim());
    }
    return sanitizedArray;
}
function DisplayMovieList(req, res, next) {
    movie_1.default.find({})
        .sort({ movieID: 1 })
        .then(function (data) {
        res.status(200).json({ success: true, msg: "Full List Found.", data: data });
    })
        .catch(function (err) {
        console.error(err);
        res.status(404).json({ success: false, msg: "ERROR: No List Data.", data: null });
    });
}
exports.DisplayMovieList = DisplayMovieList;
function DisplayMovieListTitle(req, res, next) {
    movie_1.default.find({}, { movieID: 1, title: 1 })
        .sort({ movieID: 1 })
        .then(function (data) {
        res.status(200).json({ success: true, msg: "Title List Found.", data: data });
    })
        .catch(function (err) {
        console.error(err);
        res.status(404).json({ success: false, msg: "ERROR: No Title List Data.", data: null });
    });
}
exports.DisplayMovieListTitle = DisplayMovieListTitle;
function DisplayMovieByID(req, res, next) {
    try {
        let id = req.params.id;
        movie_1.default.findById({ _id: id })
            .then(function (data) {
            if (data) {
                res.status(200).json({ success: true, msg: "Movie Found.", data: data });
            }
            else {
                res.status(404).json({ success: false, msg: "ERROR: No Movie Data.", data: data });
            }
        })
            .catch(function (err) {
            console.error(err);
            res.status(400).json({ success: false, msg: "ERROR: Server Error - Check ID ? - Bad Request", data: null });
        });
    }
    catch (err) {
        console.error(err);
        res.status(500).json({ success: false, msg: "ERROR: Server Error.", data: null });
    }
}
exports.DisplayMovieByID = DisplayMovieByID;
function AddMovie(req, res, next) {
    try {
        let genres = SanitizeArray(req.body.genres);
        let directors = SanitizeArray(req.body.directors);
        let writers = SanitizeArray(req.body.writers);
        let actors = SanitizeArray(req.body.actors);
        let movie = new movie_1.default({
            movieID: req.body.movieID,
            title: req.body.title,
            studio: req.body.studio,
            genres: genres,
            directors: directors,
            writers: writers,
            actors: actors,
            length: req.body.length,
            year: req.body.year,
            shortDescription: req.body.shortDescription,
            mpaRating: req.body.mpaRating,
            criticsRating: req.body.criticsRating,
            posterLink: req.body.posterLink
        });
        movie_1.default.create(movie)
            .then(function () {
            res.status(200).json({ success: true, msg: "Movie Added.", data: movie });
        })
            .catch(function (err) {
            console.error(err);
            if (err instanceof mongoose_1.default.Error.ValidationError) {
                res.status(400).json({ success: false, msg: "ERROR: Movie not added. All Fields Required", data: null });
            }
            else {
                res.status(400).json({ success: false, msg: "ERROR: Movie not added. Other Error.", data: null });
            }
        });
    }
    catch (err) {
        console.error(err);
        res.status(500).json({ success: false, msg: "ERROR: Server Error", data: null });
    }
}
exports.AddMovie = AddMovie;
function UpdateMovie(req, res, next) {
    try {
        let id = req.params.id;
        let genres = SanitizeArray(req.body.genres);
        let directors = SanitizeArray(req.body.directors);
        let writers = SanitizeArray(req.body.writers);
        let actors = SanitizeArray(req.body.actors);
        let movieToUpdate = new movie_1.default({
            _id: id,
            movieID: req.body.movieID,
            title: req.body.title,
            studio: req.body.studio,
            genres: genres,
            directors: directors,
            writers: writers,
            actors: actors,
            length: req.body.length,
            year: req.body.year,
            shortDescription: req.body.shortDescription,
            mpaRating: req.body.mpaRating,
            criticsRating: req.body.criticsRating,
            posterLink: req.body.posterLink
        });
        movie_1.default.updateOne({ _id: id }, movieToUpdate)
            .then(function () {
            res.status(200).json({ success: true, msg: "Movie Updated.", data: movieToUpdate });
        })
            .catch(function (err) {
            console.error(err);
            if (err instanceof mongoose_1.default.Error.ValidationError) {
                res.status(400).json({ success: false, msg: "ERROR: Movie not updated. All Fields Required", data: null });
            }
            else {
                res.status(400).json({ success: false, msg: "ERROR: Movie not updated. id Bad?", data: null });
            }
        });
    }
    catch (err) {
        console.error(err);
        res.status(500).json({ success: false, msg: "ERROR: Server Error", data: null });
    }
}
exports.UpdateMovie = UpdateMovie;
function DeleteMovie(req, res, next) {
    let id = req.params.id;
    try {
        movie_1.default.deleteOne({ _id: id })
            .then(function () {
            res.status(200).json({ success: true, msg: "Movie Removed.", data: id });
        })
            .catch(function (err) {
            res.status(400).json({ success: false, msg: "ERROR: Bad Id? Cant Complete Removal.", data: null });
            console.error(err);
        });
    }
    catch (err) {
        console.error(err);
        res.status(500).json({ success: false, msg: "ERROR: Server Error", data: null });
    }
}
exports.DeleteMovie = DeleteMovie;
//# sourceMappingURL=movie.js.map