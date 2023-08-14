// -------------------------------------------------------------
// - Robert Bettinelli - MDEV1004 - S2023
// - 090003683@student.georgianc.on.ca
// -------------------------------------------------------------
// (Controllers) stripe.ts - As Provided in Class Instruction
// Personally entered and followed as pert of in class learning.
// -------------------------------------------------------------
// 08/12/2023 - RBettinelli - Header and Documentation Added
// -------------------------------------------------------------

import { Request, Response, NextFunction } from "express";
import config from "../Config/stripe";
import Stripe from "stripe";

// Required for Init.
const stripe = new Stripe(config.secretKey, {
  apiVersion: "2022-11-15",
});

// Function for creating Required Intent Call.
// Must be completed prior to payment
export async function CreatePaymentIntent(
  req: Request,
  res: Response,
  next: NextFunction
) {
  const customer = await stripe.customers.create();
  const paymentIntent = await stripe.paymentIntents.create({
    amount: 999,
    currency: "cad",
    customer: customer.id,
    automatic_payment_methods: { enabled: true },
  });

  return res.status(200).json({
    success: true,
    msg: "Payment Intent Created",
    clientSecret: paymentIntent.client_secret,
    customer: customer.id,
    publishableKey: config.publishableKey,
  });
}
