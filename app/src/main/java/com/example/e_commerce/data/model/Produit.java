package com.example.e_commerce.data.model;

public class Produit {
    private String produitId;
    private String produitImg;
    private String nom;
    private double prix;
    private String description;

    public Produit() {
    }

    public Produit(String nom, double prix, String description) {
        this.nom = nom;
        this.prix = prix;
        this.description = description;
    }

    public String getProduitId() {
        return produitId;
    }

    public String getProduitImg() {
        return produitImg;
    }

    public void setProduitImg(String produitImg) {
        this.produitImg = produitImg;
    }

    public void setProduitId(String produitId) {
        this.produitId = produitId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
