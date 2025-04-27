package com.example.lastjavafx.models;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Panier {
    private final List<Commande> commandes;
    private static final Logger LOGGER = Logger.getLogger(Panier.class.getName());

    public Panier() {
        this.commandes = new ArrayList<>();
    }

    public void ajouterCommande(Commande commande) {
        commandes.add(commande);
        LOGGER.info("Commande ajoutée au panier : " + commande);
    }

    public void supprimerCommande(String produit) {
        commandes.removeIf(c -> c.getProduit().equalsIgnoreCase(produit));
        LOGGER.info("Commande supprimée du panier : " + produit);
    }

    public boolean estVide() {
        return commandes.isEmpty();
    }

    public List<Commande> getCommandes() {
        return commandes;
    }
}
