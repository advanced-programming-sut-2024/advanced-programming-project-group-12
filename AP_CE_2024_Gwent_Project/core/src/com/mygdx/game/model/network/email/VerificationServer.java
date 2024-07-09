//package com.mygdx.game.model.network.email;
//
//import static spark.Spark.*;
//
//public class VerificationServer {
//
//    public static void main(String[] args) {
//        port(4567);
//
//        get("/verify", (req, res) -> {
//            String token = req.queryParams("token");
//            String email = UserVerificationStore.getEmailByToken(token);
//
//            if (email != null) {
//                // Mark user as verified
//                UserVerificationStore.markUserAsVerified(email);
//
//                // Send registration message to the game server
//                Client.getInstance().sendMassage(new RegisterMessage(email));
//
//                return "Email verified successfully. You can now log in.";
//            } else {
//                return "Invalid or expired verification link.";
//            }
//        });
//    }
//}
