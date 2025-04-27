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

public class PanierController implements Initializable {

    @FXML
    private GridPane gridPanier;
    private final ServicePanier servicePanier = new ServicePanier();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        afficherPanier();
    }

    private void afficherPanier() {
        gridPanier.getChildren().clear();
        List<Commande> commandes = servicePanier.getPanier().getCommandes();
        int row = 0;

        for (Commande commande : commandes) {
            HBox ligneCommande = createCommandeRow(commande);
            gridPanier.addRow(row++, ligneCommande);
        }
    }

    private HBox createCommandeRow(Commande commande) {
        Label nomProduit = new Label("🛍️ " + commande.getProduit());
        Label prix = new Label("💰 " + commande.getPrix() + " DT");
        Label adresse = new Label("📍 " + commande.getAdresse());
        Label tel = new Label("📞 " + commande.getNum_tel());

        Button supprimerBtn = new Button("Supprimer");
        supprimerBtn.setOnAction(e -> {
            servicePanier.supprimerProduitDuPanier(commande.getIdClient());
            afficherPanier();
        });

        VBox infoBox = new VBox(5, nomProduit, prix, adresse, tel);
        HBox ligneCommande = new HBox(20, infoBox, supprimerBtn);
        ligneCommande.setPadding(new Insets(10));

        return ligneCommande;
    }

    @FXML
    private void supprimerTout() {
        servicePanier.getPanier().getCommandes().clear();
        afficherPanier();
    }
    @FXML
    private void retourAccueil() {
        try {
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
    private void validerPanier() {
        List<Commande> commandes = servicePanier.getPanier().getCommandes();
        if (commandes.isEmpty()) {
            System.out.println("⚠️ Panier vide !");
        } else {
            System.out.println("✅ Panier validé.");
            commandes.clear();
            afficherPanier();
        }
    }
}
