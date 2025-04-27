package com.example.lastjavafx.models;

public class Produit {
    private int id;
    private int agriculteurId;
    private int categorieId;
    private String nom;
    private String description;
    private int quantite;
    private double prix;
    private String pathImg;

    // Default constructor
    public Produit() {}

    // Full constructor
    public Produit(int id, int agriculteurId, int categorieId, String nom, String description, int quantite, double prix, String pathImg) {
        this.id = id;
        this.agriculteurId = agriculteurId;
        this.categorieId = categorieId;
        this.nom = nom;
        this.description = description;
        this.quantite = Math.max(0, quantite); // Ensure non-negative quantity
        this.prix = Math.max(0.0, prix); // Ensure non-negative price
        this.pathImg = pathImg;
    }

    // Additional constructor for flexibility
    public Produit(int id, String nom, double prix) {
        this.id = id;
        this.nom = nom;
        this.prix = Math.max(0.0, prix); // Ensure non-negative price
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAgriculteurId() { return agriculteurId; }
    public void setAgriculteurId(int agriculteurId) { this.agriculteurId = agriculteurId; }

    public int getCategorieId() { return categorieId; }
    public void setCategorieId(int categorieId) { this.categorieId = categorieId; }

    public String getNom() { return nom; }
    public void setNom(String nom) {
        if (nom == null || nom.isEmpty()) {
            throw new IllegalArgumentException("Le nom du produit ne peut pas être vide !");
        }
        this.nom = nom;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("La description ne peut pas être vide !");
        }
        this.description = description;
    }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) {
        if (quantite < 0) {
            throw new IllegalArgumentException("La quantité doit être positive !");
        }
        this.quantite = quantite;
    }

    public double getPrix() { return prix; }
    public void setPrix(double prix) {
        if (prix < 0.0) {
            throw new IllegalArgumentException("Le prix doit être positif !");
        }
        this.prix = prix;
    }

    public String getPathImg() { return pathImg; }
    public void setPathImg(String pathImg) {
        /*if (pathImg == null || pathImg.isEmpty()) {
            throw new IllegalArgumentException("Le chemin de l'image ne peut pas être vide !");
        }*/
        this.pathImg = pathImg;
    }

    @Override
    public String toString() {
        return String.format(
                "Produit {ID: %d, Agriculteur: %d, Catégorie: %d, Nom: '%s', Description: '%s', Quantité: %d, Prix: %.2f, Image: '%s'}",
                id, agriculteurId, categorieId, nom, description, quantite, prix, pathImg
        );
    }
}
