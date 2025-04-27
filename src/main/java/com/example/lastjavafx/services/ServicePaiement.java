package com.example.lastjavafx.services;

import com.example.lastjavafx.models.Paiement;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import java.util.HashMap;
import java.util.Map;

public class ServicePaiement {

    public ServicePaiement() {
        // Clé secrète Stripe (remplacez YOUR_SECRET_KEY)
        Stripe.apiKey = "YOUR_SECRET_KEY"; //nbadlouh mel stripe
    }

    public String processPayment(Paiement paiement) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("amount", (int) (paiement.getAmount() * 100)); // Conversion en centimes
            params.put("currency", "eur");
            params.put("payment_method_types", java.util.List.of("card"));
            params.put("description", "Paiement pour " + paiement.getClientName());

            // Crée un PaymentIntent
            PaymentIntent paymentIntent = PaymentIntent.create(params);

            return paymentIntent.getId(); // Retourne l'ID du paiement pour suivi
        } catch (StripeException e) {
            System.err.println("Erreur Stripe : " + e.getMessage());
            return null;
        }
    }
}


