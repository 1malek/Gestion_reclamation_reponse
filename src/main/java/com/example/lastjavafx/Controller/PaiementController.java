package com.example.lastjavafx.Controller;

import com.example.lastjavafx.models.Paiement;
import com.example.lastjavafx.services.ServicePaiement;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


public class PaiementController {



    @FXML
    private TextField clientNameField, emailField, amountField;

    private final ServicePaiement paymentService = new ServicePaiement();

    @FXML
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
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


