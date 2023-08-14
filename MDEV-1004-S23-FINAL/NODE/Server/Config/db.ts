// -------------------------------------------------------------
// - Robert Bettinelli - MDEV1004 - S2023
// - 090003683@student.georgianc.on.ca
// -------------------------------------------------------------
// (Config) db.ts - As Provided in Class Instruction
// Personally entered and followed as pert of in class learning.
// -------------------------------------------------------------
// 06/10/2023 - RBettinelli - Header and Documentation Added
// -------------------------------------------------------------

// Connection Setup for MongoDB Locations.
let localURI = "mongodb://localhost:27017/buildings";
let remoteURI =
  "mongodb+srv://robertbettinelli:k9P5Zy44TKJZsGls@cluster0.z16ahlj.mongodb.net/buildings";
let secret = "#mySecret2023!";

export default {
  localURI: localURI,
  remoteURI: remoteURI,
  secret: secret,
};
