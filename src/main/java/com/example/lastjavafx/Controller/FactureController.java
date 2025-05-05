package com.example.lastjavafx.Controller;

import com.example.lastjavafx.models.Facture;
import com.example.lastjavafx.services.ServiceFacture;
import com.example.lastjavafx.services.ServicePanier;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;

import java.util.List;

public class FactureController {
    @FXML private ListView<Facture> listViewFacture;
    @FXML private Label labelSousTotal, labelTVA, labelTotal;

    private final ServiceFacture serviceFacture = new ServiceFacture();

    @FXML
    public void initialize() {
        List<Facture> panier = serviceFacture.getPanier();
        listViewFacture.getItems().addAll(panier);

        double total = panier.stream().mapToDouble(Facture::getPrix).sum();
        double tva = total * 0.2;  // TVA 20%

        labelTVA.setText(String.format("%.2f", tva) + " TND");
        labelTotal.setText( String.format("%.2f", total + tva) + " TND");
        labelSousTotal.setText( String.format("%.2f", total) + " TND");
    }

}

