"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const mongoose_1 = require("mongoose");
const itemSchema = new mongoose_1.Schema({
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
        type: Number,
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
let MyItem = (0, mongoose_1.model)("buildings", itemSchema);
exports.default = MyItem;
//# sourceMappingURL=item.js.map