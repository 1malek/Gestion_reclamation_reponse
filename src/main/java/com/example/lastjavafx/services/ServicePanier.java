package com.example.lastjavafx.services;

import com.example.lastjavafx.models.Commande;
import com.example.lastjavafx.models.Marchandise;
import com.example.lastjavafx.models.Panier;
import com.example.lastjavafx.models.Produit;
import com.example.lastjavafx.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicePanier {

    private final Panier panier; // Gestion locale du panier
    private final Connection cnx; // Connexion à la base de données
    private static final Logger LOGGER = Logger.getLogger(ServicePanier.class.getName());

    public ServicePanier() {
        this.panier = new Panier();
        this.cnx = MyDataBase.getInstance().getCnx();
        chargerPanierDepuisBDD();
    }

    // Vérifie si un produit est déjà dans le panier
    public boolean verifierProduitDansPanier(int idProduit) {
        String req = "SELECT id_produit FROM nv_panier WHERE id_produit = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setInt(1, idProduit);
            ResultSet rs = pst.executeQuery();
            return rs.next(); // Retourne true si le produit est trouvé
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "❌ Erreur lors de la vérification du produit dans le panier : {0}", e.getMessage());
            return false;
        }
    }

    // Ajoute un produit au panier
    public void ajouterProduitAuPanier(Produit produit) {
        String req = "INSERT INTO nv_panier (nom, prix, id_produit) VALUES (?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setString(1, produit.getNom());
            pst.setDouble(2, produit.getPrix());
            pst.setInt(3, produit.getId());

            pst.executeUpdate();
            System.out.println("✅ Produit ajouté à la base de données : " + produit.getNom());
        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL : " + e.getMessage());
        }
    }

    public void supprimerToutLePanier() {
        String req = "DELETE FROM nv_panier"; // Supprime tous les produits du panier
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("🗑️ Tous les produits du panier ont été supprimés !");
            } else {
                System.out.println("⚠️ Le panier était déjà vide !");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL lors de la suppression du panier : " + e.getMessage());
        }
    }


    // Charge les données du panier depuis la base de données
    public void chargerPanierDepuisBDD() {
        String req = "SELECT id, id_produit, nom, prix FROM nv_panier";
        try (Statement stm = cnx.createStatement(); ResultSet rs = stm.executeQuery(req)) {
            while (rs.next()) {
                Commande commande = new Commande(
                        0,
                        rs.getInt("id"),

                        rs.getInt("id_produit"),
                        rs.getString("nom"),
                        (float) rs.getDouble("prix"),
                        "Adresse inconnue",
                        0
                );
                panier.ajouterCommande(commande); // Ajoute chaque commande au panier local
            }
            LOGGER.info("✅ Panier chargé depuis la base de données !");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "❌ Erreur lors du chargement du panier depuis la base de données : {0}", e.getMessage());
        }
    }

    // Affiche le contenu du panier local (pour débogage)
    public void afficherPanier() {
        if (panier.getCommandes().isEmpty()) {
            LOGGER.warning("⚠️ Le panier est vide !");
        } else {
            LOGGER.info("🛒 Contenu du panier : " + panier.getCommandes());
        }
    }

    // Calcule le total du panier
    public double calculerTotalPanier() {
        double total = 0;
        String req = "SELECT SUM(prix) FROM nv_panier"; // Calcule le total en SQL
        try (Statement stm = cnx.createStatement();
             ResultSet rs = stm.executeQuery(req)) {
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL : " + e.getMessage());
        }
        return total;
    }


    // Supprime un produit du panier
    public void supprimerProduitDuPanier(String nomProduit) {
        String req = "DELETE FROM nv_panier WHERE nom = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setString(1, nomProduit);
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("🗑️ Produit supprimé : " + nomProduit);
            } else {
                System.out.println("⚠️ Aucun produit trouvé avec ce nom !");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL : " + e.getMessage());
        }
    }
    public void supprimerProduitDuPanierParId(int id) {
        String req = "DELETE FROM nv_panier WHERE id = ?"; // Supprime avec ID unique du panier
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setInt(1, id);
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("🗑️ Produit supprimé - ID Panier : " + id);
            } else {
                System.out.println("⚠️ Aucun produit trouvé avec cet ID !");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL : " + e.getMessage());
        }
    }

    public List<Marchandise> getMarchandisesPanier() {
        List<Marchandise> marchandises = new ArrayList<>();
        String req = "SELECT nom, prix_unitaire, quantite FROM nv_panier";

        try (PreparedStatement pst = cnx.prepareStatement(req);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Marchandise item = new Marchandise(
                        rs.getString("nom"),
                        rs.getFloat("prix_unitaire"),
                        rs.getInt("quantite")
                );
                marchandises.add(item);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL : " + e.getMessage());
        }

        return marchandises;
    }



    public List<Commande> getCommandesDepuisBDD() {
        List<Commande> commandes = new ArrayList<>();
        String req = "SELECT id, id_produit, nom, prix FROM nv_panier";

        try (Statement stm = cnx.createStatement();
             ResultSet rs = stm.executeQuery(req)) {

            while (rs.next()) {
                Commande commande = new Commande(
                        0,
                        rs.getInt("id"),
                        rs.getInt("id_produit"),
                        rs.getString("nom"),
                        (float) rs.getDouble("prix"),
                        "Adresse inconnue", // Valeur par défaut
                        0  // Valeur par défaut pour le numéro de téléphone
                );
                commandes.add(commande);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "❌ Erreur lors du chargement des commandes depuis la base de données : {0}", e.getMessage());
        }

        return commandes;
    }


    // Retourne le panier local
    public Panier getPanier() {
        return panier;
    }
}
