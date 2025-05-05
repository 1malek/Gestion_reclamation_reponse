package com.example.lastjavafx.Controller;

import com.example.lastjavafx.models.Paiement;
import com.example.lastjavafx.services.ServicePaiement;
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
import java.util.ResourceBundle;


public class PaiementController implements Initializable {



    @FXML
    private TextField clientNameField, emailField, amountField;
    private Stage stage;
    private Scene scene;

    private final ServicePaiement paymentService = new ServicePaiement();
   // private final ServicePaiement paymentService = new ServicePaiement();
    private final ServicePanier servicePanier = new ServicePanier(); // Instance pour récupérer le prix total

    /*public void effectuerPaiement(ActionEvent event) {
        chargerScene(event, "/com/example/lastjavafx/Facture.fxml");
    }*/

   /* @FXML
    private void effectuerPaiement() {

        try {
            String clientName = clientNameField.getText();
            String email = emailField.getText();
            double amount = Double.parseDouble(amountField.getText());


            // Crée un objet PaymentInfo
            Paiement paymentInfo = new Paiement(clientName, email, amount);

            // Appelle le service de paiement
            String paymentId = paymentService.processPayment(paymentInfo);

            if (paymentId != null) {
                showAlert(Alert.AlertType.INFORMATION, "Paiement réussi", "ID du paiement : " + paymentId);
            } else {
                showAlert(Alert.AlertType.ERROR, "Paiement échoué", "Veuillez réessayer.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Montant invalide !");
        }
    }*/
    @FXML
   private void ouvrirFenetreFacture(String clientName, String email, double prixTotal) {
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/lastjavafx/Facture.fxml"));
           Parent root = loader.load();

           // 🔹 Passer les données à FactureController
           FactureController factureController = loader.getController();
           factureController.initialize();

           Stage stage = new Stage();
           stage.setScene(new Scene(root));
           stage.setTitle("Facture");
           stage.show();
       } catch (IOException e) {
           System.err.println("❌ Erreur lors de l'ouverture de la fenêtre Facture : " + e.getMessage());
       }
   }
    public void effectuerPaiement(ActionEvent event) {


        try {
            String clientName = clientNameField.getText();
            String email = emailField.getText();
            double amount = Double.parseDouble(amountField.getText());

            Paiement paymentInfo = new Paiement(clientName, email, amount);

            // 🔥 Enregistrer le paiement dans la base
            paymentService.enregistrerPaiement(paymentInfo);
            showAlert(Alert.AlertType.INFORMATION, "Paiement réussi", "Merci pour votre confiance !");
            //ouvrirFenetreFacture(clientName, email, amount);
            chargerScene(event, "/com/example/lastjavafx/Facture.fxml");

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de paiement! !");
        }
    }


    private void chargerScene(ActionEvent event, String cheminFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(cheminFXML));
            Parent root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.getMessage(/*"Erreur de chargement", "Impossible de charger la scène : " + cheminFXML*/);
        }
    }

   /* @FXML
   private void effectuerPaiement() {
       try {
           String clientName = clientNameField.getText();
           String email = emailField.getText();
           double amount = Double.parseDouble(amountField.getText());

           Paiement paymentInfo = new Paiement(clientName, email, amount);

           // 🔥 Enregistrer le paiement dans la base
           paymentService.enregistrerPaiement(paymentInfo);
           showAlert(Alert.AlertType.INFORMATION, "Paiement réussi", "Merci pour votre confiance !");
           ouvrirFenetreFacture(clientName, email, amount);

       } catch (NumberFormatException e) {
           showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de paiement! !");
       }

   }*/

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        double totalPrix = servicePanier.calculerTotalPanier(); // Récupère le prix total du panier
        amountField.setText(String.valueOf(totalPrix));
    }
    /*private void chargerScene(ActionEvent event, String cheminFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(cheminFXML));
            Parent root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("❌ Erreur lors de l'ouverture de la fenêtre Facture : " + e.getMessage());
        }
    }*/

}


