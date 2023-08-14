"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.DeleteItem = exports.UpdateItem = exports.AddItem = exports.DisplayItemByID = exports.DisplayListItems = exports.DisplayList = void 0;
const mongoose_1 = __importDefault(require("mongoose"));
const item_1 = __importDefault(require("../Models/item"));
function SanitizeArray(unsanitizedValue) {
    if (Array.isArray(unsanitizedValue)) {
        return unsanitizedValue.map((value) => value.trim());
    }
    else if (typeof unsanitizedValue === "string") {
        return unsanitizedValue.split(",").map((value) => value.trim());
    }
    else {
        return [];
    }
}
function DisplayList(req, res, next) {
    item_1.default.find({})
        .sort({ name: 1 })
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
exports.DisplayList = DisplayList;
function DisplayListItems(req, res, next) {
    item_1.default.find({}, { name: 1, title: 1 })
        .sort({ name: 1 })
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
exports.DisplayListItems = DisplayListItems;
function DisplayItemByID(req, res, next) {
    try {
        let id = req.params.id;
        item_1.default.findById({ _id: id })
            .then(function (data) {
            if (data) {
                res
                    .status(200)
                    .json({ success: true, msg: "Item Found.", data: data });
            }
            else {
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
    }
    catch (err) {
        console.error(err);
        res
            .status(500)
            .json({ success: false, msg: "ERROR: Server Error.", data: null });
    }
}
exports.DisplayItemByID = DisplayItemByID;
function AddItem(req, res, next) {
    try {
        let architects = SanitizeArray(req.body.architects);
        let parsedCost = parseFloat(req.body.cost);
        let cost = isNaN(parsedCost) ? 0.0 : parsedCost;
        let item = new item_1.default({
            name: req.body.name,
            type: req.body.type,
            dateBuilt: req.body.dateBuilt,
            city: req.body.city,
            country: req.body.country,
            description: req.body.description,
            architects: architects,
            cost: cost,
            website: req.body.website,
            imageURL: req.body.imageURL,
        });
        item_1.default.create(item)
            .then(function () {
            res.status(200).json({ success: true, msg: "Item Added.", data: item });
        })
            .catch(function (err) {
            console.error(err);
            if (err instanceof mongoose_1.default.Error.ValidationError) {
                res.status(400).json({
                    success: false,
                    msg: "ERROR: Item not added. All Fields Required",
                    data: null,
                });
            }
            else {
                res.status(400).json({
                    success: false,
                    msg: "ERROR: Item not added. Other Error.",
                    data: null,
                });
            }
        });
    }
    catch (err) {
        console.error(err);
        res
            .status(500)
            .json({ success: false, msg: "ERROR: Server Error", data: null });
    }
}
exports.AddItem = AddItem;
function UpdateItem(req, res, next) {
    try {
        let id = req.params.id;
        let architects = SanitizeArray(req.body.architects);
        let parsedCost = parseFloat(req.body.cost);
        let cost = isNaN(parsedCost) ? 0.0 : parsedCost;
        let itemToUpdate = new item_1.default({
            name: req.body.name,
            type: req.body.type,
            dateBuilt: req.body.dateBuilt,
            city: req.body.city,
            country: req.body.country,
            description: req.body.description,
            architects: architects,
            cost: cost,
            website: req.body.website,
            imageURL: req.body.imageURL,
        });
        item_1.default.updateOne({ _id: id }, itemToUpdate)
            .then(function () {
            res
                .status(200)
                .json({ success: true, msg: "Item Updated.", data: itemToUpdate });
            console.log("Updated!");
        })
            .catch(function (err) {
            console.error(err);
            if (err instanceof mongoose_1.default.Error.ValidationError) {
                res.status(400).json({
                    success: false,
                    msg: "ERROR: Item not updated. All Fields Required",
                    data: null,
                });
            }
            else {
                res.status(400).json({
                    success: false,
                    msg: "ERROR: Item not updated. id Bad?",
                    data: null,
                });
            }
        });
    }
    catch (err) {
        console.error(err);
        res
            .status(500)
            .json({ success: false, msg: "ERROR: Server Error", data: null });
    }
}
exports.UpdateItem = UpdateItem;
function DeleteItem(req, res, next) {
    let id = req.params.id;
    try {
        item_1.default.deleteOne({ _id: id })
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
    }
    catch (err) {
        console.error(err);
        res
            .status(500)
            .json({ success: false, msg: "ERROR: Server Error", data: null });
    }
}
exports.DeleteItem = DeleteItem;
//# sourceMappingURL=item.js.map