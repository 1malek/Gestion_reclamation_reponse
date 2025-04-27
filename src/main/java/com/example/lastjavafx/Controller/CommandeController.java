package com.example.lastjavafx.Controller;

import com.example.lastjavafx.models.Commande;
import com.example.lastjavafx.services.ServiceCommande;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CommandeController {
    @FXML
    private TextField idClientField, idLivraisonField, produitField, prixField, adresseField, telField;
    private ServiceCommande serviceCommande = new ServiceCommande();

    @FXML
    private void validerCommande() {
        try {
            if (idClientField.getText().isEmpty() || idLivraisonField.getText().isEmpty() ||
                    prixField.getText().isEmpty() || telField.getText().isEmpty()) {
                System.out.println("⚠️ Veuillez remplir tous les champs !");
                return;
            }

            int idClient = Integer.parseInt(idClientField.getText());
            int idLivraison = Integer.parseInt(idLivraisonField.getText());
            String produit = produitField.getText();
            float prix = Float.parseFloat(prixField.getText());
            String adresse = adresseField.getText();
            int numTel = Integer.parseInt(telField.getText());

        /*    if (!serviceCommande.verifierIdExisteDansPanier(idClient)) {
                System.out.println("❌ L'ID client spécifié n'existe pas !");
                return;
            }*/

            Commande commande = new Commande(idClient, idLivraison, produit, prix, adresse, numTel);
            serviceCommande.ajouterCommande(commande);
            System.out.println("✅ Commande validée et enregistrée !");
        } catch (NumberFormatException e) {
            System.out.println("❌ Erreur : Champ numérique invalide - " + e.getMessage());
        }
    }

    @FXML
    private void annulerCommande() {
        idClientField.clear();
        idLivraisonField.clear();
        produitField.clear();
        prixField.clear();
        adresseField.clear();
        telField.clear();
    }
}
