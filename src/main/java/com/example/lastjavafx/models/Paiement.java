package com.example.lastjavafx.models;

public class Paiement {
    private String clientName;
    private String email;
    private double amount;

    // Constructeur
    public Paiement(String clientName, String email, double amount) {
        this.clientName = clientName;
        this.email = email;
        this.amount = amount;
    }

    // Getters et Setters
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}


