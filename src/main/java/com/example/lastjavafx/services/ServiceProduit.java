package com.example.lastjavafx.services;

import com.example.lastjavafx.IService;
import com.example.lastjavafx.models.Produit;
import com.example.lastjavafx.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceProduit implements IService<Produit> {
    private final Connection cnx;
    private static final Logger LOGGER = Logger.getLogger(ServiceProduit.class.getName());

    public ServiceProduit() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void add(Produit produit) {
        String qry = "INSERT INTO produit_store (agriculteur_id_id, categorie_id_id, nom, description, quantite, prix, path_img) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            setProduitParameters(pstm, produit);
            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                LOGGER.info("Produit ajouté avec succès !");
            } else {
                LOGGER.warning("Échec de l'ajout du produit.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'ajout : {0}", e.getMessage());
        }
    }

    @Override
    public List<Produit> getAll() {
        List<Produit> produits = new ArrayList<>();
        String qry = "SELECT * FROM produit_store";
        try (Statement stm = cnx.createStatement(); ResultSet rs = stm.executeQuery(qry)) {
            while (rs.next()) {
                Produit produit = extractProduitFromResultSet(rs);
                produits.add(produit);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la récupération des produits : {0}", e.getMessage());
        }
        return produits;
    }

    @Override
    public void update(Produit produit) {
        String qry = "UPDATE produit_store SET agriculteur_id_id = ?, categorie_id_id = ?, nom = ?, description = ?, quantite = ?, prix = ?, path_img = ? WHERE id = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            setProduitParameters(pstm, produit);
            pstm.setInt(8, produit.getId());
            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                LOGGER.info("Produit mis à jour avec succès !");
            } else {
                LOGGER.warning("Aucun produit trouvé pour la mise à jour.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la modification : {0}", e.getMessage());
        }
    }

    @Override
    public void delete(Produit produit) {
        String qry = "DELETE FROM produit_store WHERE id = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, produit.getId());
            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                LOGGER.info("Produit supprimé avec succès !");
            } else {
                LOGGER.warning("Aucun produit trouvé pour la suppression.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la suppression : {0}", e.getMessage());
        }
    }

    private Produit extractProduitFromResultSet(ResultSet rs) throws SQLException {
        Produit produit = new Produit();
        produit.setId(rs.getInt("id"));
        produit.setAgriculteurId(rs.getInt("agriculteur_id_id"));
        produit.setCategorieId(rs.getInt("categorie_id_id"));
        produit.setNom(rs.getString("nom"));
        produit.setDescription(rs.getString("description"));
        produit.setQuantite(rs.getInt("quantite"));
        produit.setPrix(rs.getDouble("prix"));
        produit.setPathImg(rs.getString("path_img"));
        return produit;
    }

    private void setProduitParameters(PreparedStatement pstm, Produit produit) throws SQLException {
        pstm.setInt(1, produit.getAgriculteurId());
        pstm.setInt(2, produit.getCategorieId());
        pstm.setString(3, produit.getNom());
        pstm.setString(4, produit.getDescription());
        pstm.setInt(5, produit.getQuantite());
        pstm.setDouble(6, produit.getPrix());
        pstm.setString(7, produit.getPathImg());
    }
}
