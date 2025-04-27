package com.example.lastjavafx.services;

import com.example.lastjavafx.models.Facture;
import com.example.lastjavafx.utils.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ServiceFacture {
    private final Connection cnx;

    public ServiceFacture() {
        this.cnx = MyDataBase.getInstance().getCnx();
    }

    // Méthode pour modifier une facture existante
    public void modifierFacture(Facture facture) {
        String query = "UPDATE commande SET nom_client = ?, adresse_client = ?, total_ht = ?, tva = ?, total_ttc = ? WHERE id = ?";

        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, facture.getNomClient());
            pst.setString(2, facture.getAdresseClient());
            pst.setDouble(3, facture.getTotalHT());
            pst.setDouble(4, facture.getTva());
            pst.setDouble(5, facture.getTotalTTC());
            pst.setInt(6, facture.getId());

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Facture modifiée avec succès !");
            } else {
                System.out.println("⚠️ Aucune facture trouvée pour modification.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la modification de la facture : " + e.getMessage());
        }
    }
}
