package com.example.lastjavafx.models;

public class Commande {
    private int idClient;
    private int idLivraison;
    private String produit;
    private float prix;
    private String adresse;
    private int num_tel;

    public Commande() {}

    public Commande(int idClient, int idLivraison, String produit, float prix, String adresse, int num_tel) {
        this.idClient = idClient;
        this.idLivraison = idLivraison;
        this.produit = produit;
        this.prix = prix;
        this.adresse = adresse;
        this.num_tel = num_tel;
    }

    public int getIdClient() { return idClient; }
    public void setIdClient(int idClient) { this.idClient = idClient; }
    public int getIdLivraison() { return idLivraison; }
    public void setIdLivraison(int idLivraison) { this.idLivraison = idLivraison; }
    public String getProduit() { return produit; }
    public void setProduit(String produit) { this.produit = produit; }
    public float getPrix() { return prix; }
    public void setPrix(float prix) { this.prix = prix; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public int getNum_tel() { return num_tel; }
    public void setNum_tel(int num_tel) { this.num_tel = num_tel; }

    @Override
    public String toString() {
        return String.format("Commande [Client ID: %d, Livraison ID: %d, Produit: %s, Prix: %.2f, Adresse: %s, Téléphone: %d]",
                idClient, idLivraison, produit, prix, adresse, num_tel);
    }
}
