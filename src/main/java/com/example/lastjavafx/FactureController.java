package com.example.lastjavafx;

import com.example.lastjavafx.models.Facture;
import com.example.lastjavafx.services.ServiceFacture;
import com.example.lastjavafx.utils.GenerateurPDF;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class FactureController {

    @FXML
    private TextField nomClientField;

    @FXML
    private TextField adresseClientField;

    @FXML
    private TextField totalHTField;

    @FXML
    private TextField tvaField;

    @FXML
    private TextField totalTTCField;

    private final ServiceFacture serviceFacture = new ServiceFacture();

    // Load an existing invoice into the form
    public void chargerFacture(Facture facture) {
        nomClientField.setText(facture.getNomClient());
        adresseClientField.setText(facture.getAdresseClient());
        totalHTField.setText(String.valueOf(facture.getTotalHT()));
        tvaField.setText(String.valueOf(facture.getTva()));
        totalTTCField.setText(String.valueOf(facture.getTotalTTC()));
    }

    // Update an existing invoice
    @FXML
    private void modifierFacture() {
        try {
            // Validate input
            if (nomClientField.getText().isEmpty() || adresseClientField.getText().isEmpty() ||
                    totalHTField.getText().isEmpty() || tvaField.getText().isEmpty() || totalTTCField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur de Saisie", "Tous les champs doivent être remplis !");
                return;
            }

            // Create and update the invoice
            Facture facture = new Facture();
            facture.setNomClient(nomClientField.getText());
            facture.setAdresseClient(adresseClientField.getText());
            facture.setTotalHT(Double.parseDouble(totalHTField.getText()));
            facture.setTva(Double.parseDouble(tvaField.getText()));
            facture.setTotalTTC(Double.parseDouble(totalTTCField.getText()));

            serviceFacture.modifierFacture(facture);

            // Show success message
            showAlert(Alert.AlertType.INFORMATION, "Modification Réussie", "La facture a été modifiée avec succès !");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de Saisie", "Veuillez entrer des valeurs valides pour les champs numériques.");
        }
    }

    // Generate a PDF for the invoice
    @FXML
    private void genererPDF() {
        try {
            Facture facture = new Facture();
            facture.setNomClient(nomClientField.getText());
            facture.setAdresseClient(adresseClientField.getText());
            facture.setTotalHT(Double.parseDouble(totalHTField.getText()));
            facture.setTva(Double.parseDouble(tvaField.getText()));
            facture.setTotalTTC(Double.parseDouble(totalTTCField.getText()));

            String chemin = "facture.pdf"; // Path to save the PDF
            GenerateurPDF.creerPDF(chemin, facture.getNomClient(), facture.getAdresseClient(), facture.getTotalTTC());
            showAlert(Alert.AlertType.INFORMATION, "PDF Généré", "La facture a été exportée en PDF avec succès !");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de générer le PDF : " + e.getMessage());
        }
    }

    // Helper method to display alerts
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
