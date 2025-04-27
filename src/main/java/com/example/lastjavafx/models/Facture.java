package com.example.lastjavafx.models;

public class Facture {

    private int id;
    private String nomClient;       // Nom du client
    private String adresseClient;   // Adresse du client
    private double totalHT;         // Total hors taxes
    private double tva;             // Taxe sur la valeur ajoutée
    private double totalTTC;        // Total TTC (toutes taxes comprises)

    // Constructeur par défaut
    public Facture() {}

    // Constructeur complet
    public Facture(int id, String nomClient, String adresseClient, double totalHT, double tva, double totalTTC) {
        this.id = id;
        this.nomClient = nomClient;
        this.adresseClient = adresseClient;
        this.totalHT = totalHT;
        this.tva = tva;
        this.totalTTC = totalTTC;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getAdresseClient() {
        return adresseClient;
    }

    public void setAdresseClient(String adresseClient) {
        this.adresseClient = adresseClient;
    }

    public double getTotalHT() {
        return totalHT;
    }

    public void setTotalHT(double totalHT) {
        this.totalHT = totalHT;
    }

    public double getTva() {
        return tva;
    }

    public void setTva(double tva) {
        this.tva = tva;
    }

    public double getTotalTTC() {
        return totalTTC;
    }

    public void setTotalTTC(double totalTTC) {
        this.totalTTC = totalTTC;
    }

    @Override
    public String toString() {
        return String.format(
                "Facture {ID: %d, Nom Client: '%s', Adresse: '%s', Total HT: %.2f, TVA: %.2f, Total TTC: %.2f}",
                id, nomClient, adresseClient, totalHT, tva, totalTTC
        );
    }
}
