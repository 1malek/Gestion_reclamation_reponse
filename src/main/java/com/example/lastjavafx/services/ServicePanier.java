package com.example.lastjavafx.services;

import com.example.lastjavafx.models.Commande;
import com.example.lastjavafx.models.Panier;
import com.example.lastjavafx.models.Produit;
import com.example.lastjavafx.utils.MyDataBase;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicePanier {
    private final Panier panier;
    private final Connection cnx;
    private static final Logger LOGGER = Logger.getLogger(ServicePanier.class.getName());

    public ServicePanier() {
        this.panier = new Panier();
        this.cnx = MyDataBase.getInstance().getCnx();
        chargerPanierDepuisBDD();
    }

    public boolean verifierProduitDansPanier(int idProduit) {
        String req = "SELECT id FROM nv_panier WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setInt(1, idProduit);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la vérification du produit dans le panier : {0}", e.getMessage());
            return false;
        }
    }

    public void ajouterProduitAuPanier(Produit produit) {
        String reqPanier = "INSERT INTO nv_panier (nom, prix) VALUES (?, ?)";

        if (!verifierProduitDansPanier(produit.getId())) {
            try (PreparedStatement ps = cnx.prepareStatement(reqPanier)) {
                ps.setString(1, produit.getNom());
                ps.setDouble(2, produit.getPrix());
                ps.executeUpdate();
                LOGGER.info("✅ Produit ajouté au panier avec succès !");
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "❌ Erreur lors de l'ajout du produit : {0}", e.getMessage());
            }
        } else {
            LOGGER.warning("⚠️ Produit déjà présent dans le panier.");
        }

        panier.ajouterCommande(new Commande(produit.getId(), 0, produit.getNom(), (float) produit.getPrix(), "Adresse inconnue", 0));
        LOGGER.info("✅ Produit ajouté à la liste locale du panier : " + produit.getNom());
    }

    public void chargerPanierDepuisBDD() {
        String req = "SELECT * FROM nv_panier";
        try (Statement stm = cnx.createStatement(); ResultSet rs = stm.executeQuery(req)) {
            while (rs.next()) {
                panier.ajouterCommande(new Commande(
                        rs.getInt("id"),
                        0,
                        rs.getString("nom"),
                        (float) rs.getDouble("prix"),
                        "Adresse inconnue",
                        0
                ));
            }
            LOGGER.info("✅ Panier chargé depuis la base de données !");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "❌ Erreur lors du chargement du panier depuis la base de données : {0}", e.getMessage());
        }
    }

    public void afficherPanier() {
        if (panier.getCommandes().isEmpty()) {
            LOGGER.warning("⚠️ Le panier est vide !");
        } else {
            LOGGER.info("🛒 Contenu du panier : " + panier.getCommandes());
        }
    }

    public double calculerTotalPanier() {
        return panier.getCommandes().stream().mapToDouble(Commande::getPrix).sum();
    }

    public void supprimerProduitDuPanier(int idProduit) {
        String req = "DELETE FROM nv_panier WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setInt(1, idProduit);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                LOGGER.info("🗑️ Produit supprimé du panier avec succès !");
                panier.getCommandes().removeIf(c -> c.getIdClient() == idProduit);
            } else {
                LOGGER.warning("⚠️ Aucun produit trouvé pour la suppression.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "❌ Erreur lors de la suppression du produit : {0}", e.getMessage());
        }
    }

    public Panier getPanier() {
        return panier;
    }
}
