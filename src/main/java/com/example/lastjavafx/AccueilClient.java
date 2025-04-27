package com.example.lastjavafx;

import com.example.lastjavafx.models.Produit;
import com.example.lastjavafx.services.ServiceProduit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AccueilClient implements Initializable {

    private Stage stage;
    private Scene scene;

    @FXML
    private VBox listeProduitsVBox;

    private final ServiceProduit serviceProduit = new ServiceProduit();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherProduits(); // Display products when the screen is initialized
    }

    // Methods for scene navigation
    public void handleVoirPanierClient(ActionEvent event) {
        chargerScene(event, "/com/example/lastjavafx/PanierClient.fxml");
    }

    public void handleVoirPanier(ActionEvent event) {
        chargerScene(event, "/com/example/lastjavafx/Panier.fxml");
    }
    public void handleVoirFacture(ActionEvent event) {
        chargerScene(event, "/com/example/lastjavafx/Facture.fxml");
    }

    public void handleVoirCommandes(ActionEvent event) {
        chargerScene(event, "/com/example/lastjavafx/Commande.fxml");
    }

    public void handleDeconnexion(ActionEvent event) {
        chargerScene(event, "/com/example/lastjavafx/PanierVide.fxml");
    }

    // Generic method to switch scenes
    private void chargerScene(ActionEvent event, String cheminFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(cheminFXML));
            Parent root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("❌ Erreur lors du chargement de la scène : " + cheminFXML);
            e.printStackTrace();
        }
    }

    // Display products in the VBox
    private void afficherProduits() {
        List<Produit> produits = serviceProduit.getAll(); // Get products from the database

        for (Produit p : produits) {
            HBox produitBox = createProduitBox(p);
            listeProduitsVBox.getChildren().add(produitBox); // Add each product to the VBox
        }
    }

    // Helper method to create an HBox for a product
    private HBox createProduitBox(Produit produit) {
        HBox produitBox = new HBox(20);
        produitBox.setStyle("-fx-padding: 10; -fx-border-color: #ddd; -fx-border-radius: 8; -fx-background-color: #f9f9f9;");

        Label nom = new Label(produit.getNom());
        nom.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Label prix = new Label(produit.getPrix() + " DT");
        prix.setStyle("-fx-text-fill: green;");

        Button boutonAjouter = new Button("Ajouter");
        boutonAjouter.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        boutonAjouter.setOnAction(e -> ajouterAuPanier(produit));

        produitBox.getChildren().addAll(nom, prix, boutonAjouter);
        return produitBox;
    }

    // Method to handle adding a product to the cart
    private void ajouterAuPanier(Produit produit) {
        System.out.println("✅ Ajouté au panier : " + produit.getNom());
        // TODO: Call ServicePanier to add the product to the user's cart
    }
}
