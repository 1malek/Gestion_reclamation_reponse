package com.example.lastjavafx.services;

import com.example.lastjavafx.models.Commande;
import com.example.lastjavafx.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCommande {
    private final Connection cnx;

    public ServiceCommande() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    // Ajouter une commande
    public void ajouterCommande(Commande commande) {
        String req = "INSERT INTO `commande`(`id_client`, `livraison_id`, `produit`, `prix`, `adresse`, `num_tel`) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setLong(1, commande.getIdClient());
            pst.setLong(2, commande.getIdLivraison());
            pst.setString(3, commande.getProduit());
            pst.setFloat(4, commande.getPrix());
            pst.setString(5, commande.getAdresse());
            pst.setInt(6, commande.getNum_tel());
            pst.executeUpdate();

            System.out.println("✅ Commande ajoutée avec succès !");
        } catch (SQLException ex) {
            System.err.println("❌ Erreur lors de l'ajout de la commande : " + ex.getMessage());
        }
    }

    // Afficher toutes les commandes
    public List<Commande> afficherCommandes() {
        List<Commande> commandes = new ArrayList<>();
        String req = "SELECT * FROM commande";

        try (Statement stm = cnx.createStatement(); ResultSet rs = stm.executeQuery(req)) {
            while (rs.next()) {
                Commande c = new Commande();
                c.setProduit(rs.getString("produit"));
                c.setPrix(rs.getFloat("prix"));
                c.setAdresse(rs.getString("adresse"));
                c.setNum_tel(rs.getInt("num_tel"));

                // Ajout de la commande dans la liste
                commandes.add(c);
            }
        } catch (SQLException ex) {
            System.err.println("❌ Erreur lors de l'affichage des commandes : " + ex.getMessage());
        }

        return commandes;
    }

    // Supprimer une commande (par nom de produit)
    public void supprimerCommande(String nomProduit) {
        String req = "DELETE FROM commande WHERE produit = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setString(1, nomProduit);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("🗑️ Commande supprimée avec succès.");
            } else {
                System.out.println("⚠️ Aucune commande trouvée avec ce nom.");
            }
        } catch (SQLException ex) {
            System.err.println("❌ Erreur lors de la suppression : " + ex.getMessage());
        }
    }

    // Modifier une commande (par nom de produit)
    public void modifierCommande(Commande commande) {
        String req = "UPDATE commande SET prix = ?, adresse = ?, num_tel = ? WHERE produit = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setFloat(1, commande.getPrix());
            pst.setString(2, commande.getAdresse());
            pst.setInt(3, commande.getNum_tel());
            pst.setString(4, commande.getProduit());
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✏️ Commande modifiée avec succès !");
            } else {
                System.out.println("⚠️ Aucune commande trouvée pour modifier.");
            }
        } catch (SQLException ex) {
            System.err.println("❌ Erreur lors de la modification : " + ex.getMessage());
        }
    }
}
