package com.example.lastjavafx.models;

public class Marchandise {
    private String nom;
    private float prixUnitaire;
    private int quantite;

    // 🔹 Constructeur
    public Marchandise(String nom, float prixUnitaire, int quantite) {
        this.nom = nom;
        this.prixUnitaire = prixUnitaire;
        this.quantite = quantite;
    }

    // 🔹 Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(float prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    // 🔹 Calculer le total de cet article
    public float calculerTotal() {
        return prixUnitaire * quantite;
    }

    // 🔹 Affichage des infos
    @Override
    public String toString() {
        return "Marchandise{" +
                "nom='" + nom + '\'' +
                ", prixUnitaire=" + prixUnitaire +
                ", quantite=" + quantite +
                ", total=" + calculerTotal() + " TND" +
                '}';
    }
}
