package com.example.lastjavafx.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    private Stage stage;
    private Scene scene;

    @FXML
    private void handleAccueilClick(ActionEvent event) {
        chargerScene(event, "/com/example/lastjavafx/AccueilClient.fxml");
    }

    @FXML
    private void handleStatistiquesClick(ActionEvent event) {
        chargerScene(event, "/com/example/lastjavafx/Statistiques.fxml");
    }

    @FXML
    private void handleParametresClick(ActionEvent event) {
        chargerScene(event, "/com/example/lastjavafx/Commande.fxml");
    }

    @FXML
    private void handleCommandesClick(ActionEvent event) {
        chargerScene(event, "/com/example/lastjavafx/Facture.fxml");
    }

    @FXML
    private void handleDeconnexionClick() {
        Platform.exit();
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
}
