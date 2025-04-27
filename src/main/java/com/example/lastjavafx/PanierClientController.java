package com.example.lastjavafx;

import com.example.lastjavafx.models.Commande;
import com.example.lastjavafx.services.ServicePanier;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PanierClientController implements Initializable {

    @FXML
    private GridPane gridPanier;
    @FXML
    private Label labelTotal;

    private final ServicePanier servicePanier = new ServicePanier();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherPanier();
    }

    private void afficherPanier() {
        gridPanier.getChildren().clear();

        List<Commande> commandes = servicePanier.getPanier().getCommandes();
        double total = 0;

        int row = 0;
        for (Commande commande : commandes) {
            Label nomProduit = new Label("🛍️ " + commande.getProduit());
            Label prix = new Label("💰 " + commande.getPrix() + " DT");
            Label adresse = new Label("📍 " + commande.getAdresse());
            Label tel = new Label("📞 " + commande.getNum_tel());

            Button supprimerBtn = new Button("Supprimer");
            supprimerBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
            supprimerBtn.setOnAction(e -> {
                servicePanier.supprimerProduitDuPanier(commande.getIdClient());
                afficherPanier();
            });

            VBox infoBox = new VBox(5, nomProduit, prix, adresse, tel);
            HBox ligneCommande = new HBox(20, infoBox, supprimerBtn);
            ligneCommande.setPadding(new Insets(10));
            ligneCommande.setStyle("-fx-border-color: #ccc; -fx-border-width: 1px; -fx-background-color: #f9f9f9;");

            gridPanier.addRow(row++, ligneCommande);

            total += commande.getPrix();
        }

        labelTotal.setText("Total : " + String.format("%.3f", total) + " DT");
    }

    @FXML
    private void supprimerCommande() {
        servicePanier.getPanier().getCommandes().clear(); // ou servicePanier.viderPanier();
        afficherPanier();
        System.out.println("🧹 Panier vidé.");
    }

    @FXML
    private void validerPanier() {
        List<Commande> commandes = servicePanier.getPanier().getCommandes();
        if (commandes.isEmpty()) {
            System.out.println("⚠️ Impossible de valider, panier vide !");
        } else {
            System.out.println("✅ Panier validé avec " + commandes.size() + " commande(s).");
            commandes.clear(); // ou servicePanier.viderPanier();
            afficherPanier();
        }
    }
    @FXML
    private void retourAccueil() {
        try {
            Button btnRetour  = new Button("Retour");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lastjavafx/AccueilClient.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) gridPanier.getScene().getWindow(); // Get current stage
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("❌ Erreur lors du chargement de la page d'accueil : " + e.getMessage());
        }
    }
    @FXML
    private void supprimerTout() {
        servicePanier.getPanier().getCommandes().clear();
        afficherPanier();
    }
}
