// -------------------------------------------------------------
// - Robert Bettinelli - MDEV1004 - S2023
// - 090003683@student.georgianc.on.ca
// -------------------------------------------------------------
// (Config) stripe.ts - As Provided in Class Instruction
// Personally entered and followed as pert of in class learning.
// -------------------------------------------------------------
// 08/12/2023 - RBettinelli - Base.
// -------------------------------------------------------------

import "dotenv/config";

// Keys for Stripe (Keep Private!!!)
export default {
  secretKey: process.env.STRIPE_SECRET_KEY as string,
  publishableKey: process.env.STRIPE_PUBLISHABLE_KEY as string,
};
