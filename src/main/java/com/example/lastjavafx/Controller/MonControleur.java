package com.example.lastjavafx.Controller;


import javafx.event.ActionEvent;
import java.io.IOException;

public class MonControleur {
    public void genererPDF(ActionEvent event) {
        try {
            com.example.lastjavafx.utils.GenerateurPDF.creerPDF("facture.pdf", "Meher", "Produit XYZ", 120.50);
            System.out.println("PDF généré avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la génération du PDF.");
        }
    }
}
