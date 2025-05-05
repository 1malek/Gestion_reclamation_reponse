package com.example.lastjavafx.services;

import com.example.lastjavafx.models.Facture;
import com.example.lastjavafx.models.Facture;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceFacture {
    private final String URL = "jdbc:mysql://localhost:3306/mehdi-db";
    private final String USER = "root";
    private final String PASSWORD = "";

    public List<Facture> getPanier() {
        List<Facture> panier = new ArrayList<>();
        String query = "SELECT nom, prix, id_produit FROM nv_panier";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String nom = rs.getString("nom");
                double prix = rs.getDouble("prix");
                int idProduit = rs.getInt("id_produit");

                panier.add(new Facture(nom, prix, idProduit));
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL : " + e.getMessage());
        }

        return panier;
    }
}
