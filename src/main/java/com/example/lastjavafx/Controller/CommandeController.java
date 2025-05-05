package com.example.lastjavafx.Controller;

import com.example.lastjavafx.models.Commande;
import com.example.lastjavafx.services.ServiceCommande;
import com.example.lastjavafx.services.ServicePanier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class CommandeController implements Initializable {

    private Stage stage;
    private Scene scene;

    @FXML
    private TextField idField;
    @FXML
    private TextField idClientField;
    @FXML
    private TextField idLivraisonField;
    @FXML
    private TextField produitField;
    @FXML
    private TextField prixField;
    @FXML
    private TextField adresseField;
    @FXML
    private TextField telField;
    private ServiceCommande serviceCommande = new ServiceCommande();
    private ServicePanier servicePanier = new ServicePanier();
    private static int compteurCommande = 1;
    private int generateRandomCode() {
        Random random = new Random();
        return 100 + random.nextInt(900); // Génère un nombre entre 100et 999
    }
    //


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
    public void voirCommande(ActionEvent event) {
        chargerScene(event, "/com/example/lastjavafx/Facture.fxml");
    }

    private void afficherErreur(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }
    @FXML
    private void validerCommande() {
        try {
            if (idClientField.getText().isEmpty() || idLivraisonField.getText().isEmpty() ||
                    prixField.getText().isEmpty() || telField.getText().isEmpty()
                    || idField.getText().isEmpty() || produitField.getText().isEmpty() || adresseField.getText().isEmpty()){
                System.out.println("⚠️ Veuillez remplir tous les champs !");
                return;
            }
            int id = Integer.parseInt(idField.getText());
            int idClient = generateRandomCode();;
            int idLivraison = generateRandomCode();
            //idClientField.setText(String.valueOf(idClient));
            //idClientField.setText(String.valueOf(idClient));
            //idLivraisonField.setText(String.valueOf(idLivraison));
            String produit = produitField.getText();
            float prix = Float.parseFloat(prixField.getText());
            String adresse = adresseField.getText();
            int numTel = Integer.parseInt(telField.getText());

        /*    if (!serviceCommande.verifierIdExisteDansPanier(idClient)) {
                System.out.println("❌ L'ID client spécifié n'existe pas !");
                return;
            }*/

            Commande commande = new Commande(id,idClient, idLivraison, produit, prix, adresse, numTel);
            serviceCommande.ajouterCommande(commande);
            System.out.println("✅ Commande validée et enregistrée !");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lastjavafx/Paiement.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Paiement");
            stage.show();
        } catch (NumberFormatException e) {
            System.out.println("❌ Erreur : Champ numérique invalide - " + e.getMessage());
        }
       catch (IOException e) {
            System.err.println("❌ Erreur lors de l'ouverture de la fenêtre Paiement : " + e.getMessage());
        }
    }

    @FXML
    private void annulerCommande() {
        /*idClientField.clear();
        idLivraisonField.clear();
        produitField.clear();
        prixField.clear();
        idField.clear();*/
        adresseField.clear();
        telField.clear();
    }

    private double calculerPrixTotal() {
        return servicePanier.calculerTotalPanier(); // Appelle la méthode depuis l'instance de ServicePanier
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            // 🔥 Génération automatique au chargement de la fenêtre
            long idClient = generateRandomCode();
            long idLivraison = generateRandomCode();

            // 🔥 Affichage des codes générés dans les champs dès l’ouverture
            idClientField.setText(String.valueOf(idClient));
            idLivraisonField.setText(String.valueOf(idLivraison));
            produitField.setText("Fruits et légumes");
            prixField.setText(String.valueOf(calculerPrixTotal())); // Affiche le total du panier dans le champ prix
            idField.setText(String.valueOf(compteurCommande++)); // Incrémente à chaque commande

    }
    }

