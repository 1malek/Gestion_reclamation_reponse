package com.example.lastjavafx.models;

public class Facture{
    private String nom;
    private double prix;
    private int idProduit;

    public Facture(String nom, double prix, int idProduit) {
        this.nom = nom;
        this.prix = prix;
        this.idProduit = idProduit;
    }

    public String getNom() { return nom; }
    public double getPrix() { return prix; }
    public int getIdProduit() { return idProduit; }

    @Override
    public String toString() {
        return nom + " - " + prix + " TND";
    }
}
