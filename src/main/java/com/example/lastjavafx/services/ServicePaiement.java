package com.example.lastjavafx.services;

import com.example.lastjavafx.models.Paiement;
import com.example.lastjavafx.utils.MyDataBase;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ServicePaiement {
    private final Connection cnx;
    public ServicePaiement(Connection cnx) {
        this.cnx = cnx;
        // Clé secrète Stripe (remplacez YOUR_SECRET_KEY)
        Stripe.apiKey = "sk_test_51RHs4AQvPdGWXTnhssT9w2EcysIyARvmbWc8jA0KXueCof1LycUKuBBhjo4wRDulonRKEAYjrlZKhDKm2egidgmV00IlgfyEkB"; //nbadlouh mel stripe
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
    public ServicePaiement() {
        cnx = MyDataBase.getInstance().getCnx(); // Connexion à la base
    }

    public void enregistrerPaiement(Paiement paiement) {
        String req = "INSERT INTO paiement (client_name, email, montant) VALUES (?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setString(1, paiement.getClientName());
            pst.setString(2, paiement.getEmail());
            pst.setDouble(3, paiement.getAmount());
            pst.executeUpdate();
            System.out.println("✅ Paiement enregistré en base !");
        } catch (SQLException ex) {
            System.err.println("❌ Erreur lors de l'enregistrement du paiement : " + ex.getMessage());
        }
    }
}


