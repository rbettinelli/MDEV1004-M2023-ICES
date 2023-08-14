// -------------------------------------------------------------
// - Robert Bettinelli - MDEV1004 - S2023
// - 090003683@student.georgianc.on.ca
// -------------------------------------------------------------
// (Util) index.ts - As Provided in Class Instruction
// Personally entered and followed as pert of in class learning.
// -------------------------------------------------------------
// 06/10/2023 - RBettinelli - Header and Documentation Added
// -------------------------------------------------------------

import jwt from "jsonwebtoken";
import db from "../Config/db";

/**
 * Convenience function to generate a JWT token
 *
 * @export
 * @param {UserDocument} user
 * @returns {string}
 */
export function GenerateToken(user: UserDocument): string {
  const payload = {
    id: user._id,
    DisplayName: user.displayName,
    username: user.username,
    EmailAddress: user.emailAddress,
  };

  const jwtOptions = {
    expiresIn: 604800, // 1 week
    // Note: this may be made considerably shorter for security purposes
  };

  return jwt.sign(payload, db.secret, jwtOptions);
}
