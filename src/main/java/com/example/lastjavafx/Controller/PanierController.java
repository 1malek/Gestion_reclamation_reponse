package com.example.lastjavafx.Controller;

import com.example.lastjavafx.models.Commande;
import com.example.lastjavafx.services.ServicePanier;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
        gridPanier.getChildren().clear(); // Effacer l'affichage actuel

        List<Commande> commandes = servicePanier.getCommandesDepuisBDD(); // Récupère les commandes depuis MySQL
        int row = 0;

        for (Commande commande : commandes) {
            HBox ligneCommande = createCommandeRow(commande);
            gridPanier.addRow(row++, ligneCommande);
        }
    }


    private HBox createCommandeRow(Commande commande) {
        Label nomProduit = new Label("🛍️ " + commande.getProduit());
        Label prix = new Label("💰 " + commande.getPrix() + " DT");

        Button supprimerBtn = new Button("Supprimer");
        supprimerBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        supprimerBtn.setOnAction(e -> {
                //servicePanier.supprimerProduitDuPanier(commande.getProduit()); // Supprime de MySQL
                servicePanier.supprimerProduitDuPanierParId(commande.getId()); // Supprime de MySQL
                afficherPanier(); // Rafraîchit l'affichage après suppression
            });

        HBox ligneCommande = new HBox(20, nomProduit, prix, supprimerBtn);
        ligneCommande.setPadding(new Insets(10));
        return ligneCommande;
    }


    @FXML
    private void supprimerTout() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText("Êtes-vous sûr de vouloir supprimer tout le panier ?");
        confirmation.setContentText("Cette action est irréversible.");

        // Attendre la réponse de l'utilisateur
        if (confirmation.showAndWait().get() == ButtonType.OK) {
            servicePanier.supprimerToutLePanier(); // Supprime tout en base
            afficherPanier(); // Rafraîchir l'affichage
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Suppression réussie");
            info.setHeaderText(null);
            info.setContentText("Le panier a été vidé avec succès !");
            info.show();
        }
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

        // Check if the cart is empty
        if (commandes.isEmpty()) {
            System.out.println("⚠️ Panier vide !");
        } else {
            // Clear the cart and refresh the display
            System.out.println("✅ Panier validé.");
            commandes.clear();
            afficherPanier();

            // Redirect to "Commande" page
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lastjavafx/Commande.fxml")); // Path to Commande.fxml
                Parent root = loader.load();

                // Get the current stage and update the scene
                Stage stage = (Stage) gridPanier.getScene().getWindow(); // Replace gridPanier with the correct UI element's fx:id
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                System.err.println("❌ Erreur lors du chargement de la page des commandes : " + e.getMessage());
            }
        }
    }

    private void mettreAJourGridPanier() {
        gridPanier.getChildren().clear(); // Effacer les anciennes données

        int row = 0; // Compteur de lignes
        for (Commande commande : servicePanier.getPanier().getCommandes()) {
            Label labelNom = new Label("Produit : " + commande.getProduit());
            Label labelPrix = new Label("Prix : " + commande.getPrix() + " DT");

            Button btnSupprimer = new Button("Supprimer");
            btnSupprimer.setOnAction(e -> {
                servicePanier.supprimerProduitDuPanier(commande.getProduit ());
                mettreAJourGridPanier(); // Réactualiser après suppression
            });

            HBox ligne = new HBox(10, labelNom, labelPrix, btnSupprimer);
            ligne.setPadding(new Insets(10));
            gridPanier.add(ligne, 0, row++); // Ajouter chaque ligne au GridPane
        }
    }

}
