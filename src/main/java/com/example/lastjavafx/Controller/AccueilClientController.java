package com.example.lastjavafx.Controller;

import com.example.lastjavafx.models.Produit;
import com.example.lastjavafx.services.ServicePanier;
import com.example.lastjavafx.services.ServiceProduit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AccueilClientController implements Initializable {

    private Stage stage;
    private Scene scene;

    @FXML
    private VBox listeProduitsVBox;

    private final ServiceProduit serviceProduit = new ServiceProduit();
    private final ServicePanier servicePanier = new ServicePanier(); // Gestion du panier

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherProduits(); // Affiche la liste des produits dès l'initialisation
    }

    // Méthodes pour naviguer entre les scènes
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
        chargerScene(event, "/com/example/lastjavafx/Dashboard.fxml");
    }

    // Méthode générique pour changer de scène
    private void chargerScene(ActionEvent event, String cheminFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(cheminFXML));
            Parent root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            afficherErreur("Erreur de chargement", "Impossible de charger la scène : " + cheminFXML);
        }
    }

    // Affichage des produits depuis la base de données
    private void afficherProduits() {
        listeProduitsVBox.getChildren().clear(); // Nettoyer avant d'ajouter
        List<Produit> produits = serviceProduit.getAll(); // Récupère les produits

        if (produits.isEmpty()) {
            Label aucunProduit = new Label("Aucun produit disponible pour le moment.");
            listeProduitsVBox.getChildren().add(aucunProduit);
            return;
        }

        for (Produit produit : produits) {
            HBox produitBox = createProduitBox(produit); // Crée une boîte de produit
            listeProduitsVBox.getChildren().add(produitBox);
        }
    }

    // Création d'une HBox pour chaque produit
    private HBox createProduitBox(Produit produit) {
        HBox produitBox = new HBox(20);
        produitBox.setStyle("-fx-padding: 10; -fx-border-color: #ddd; -fx-border-radius: 8; -fx-background-color: #f9f9f9;");

        Label nom = new Label(produit.getNom());
        nom.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Label prix = new Label(produit.getPrix() + " DT");
        prix.setStyle("-fx-text-fill: green;");

        Button boutonAjouter = new Button("Ajouter au panier");
        boutonAjouter.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        boutonAjouter.setOnAction(e -> ajouterAuPanier(produit)); // Ajout au panier

        produitBox.getChildren().addAll(nom, prix, boutonAjouter);
        return produitBox;
    }

    // Gestion de l'ajout au panier avec confirmation
    private void ajouterAuPanier(Produit produit) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Ajout au panier");
        confirmation.setHeaderText("Voulez-vous ajouter ce produit ?");
        confirmation.setContentText(produit.getNom() + " - " + produit.getPrix() + " DT");

        if (confirmation.showAndWait().get() == ButtonType.OK) {
            try {
                // Call service to insert product into nv_panier table
                servicePanier.ajouterProduitAuPanier(produit);

                // Fetch updated list (optional, to refresh display dynamically)
                afficherProduits();

                afficherMessage("Succès", "Le produit " + produit.getNom() + " a été ajouté au panier !");
            } catch (Exception e) {
                afficherErreur("Erreur", "Impossible d'ajouter le produit au panier : " + e.getMessage());
            }
        }
    }


    // Affichage d'un message de confirmation
    private void afficherMessage(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    // Affichage d'un message d'erreur
    private void afficherErreur(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }
}
