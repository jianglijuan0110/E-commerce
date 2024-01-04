package com.example.e_commerce.model.model;

public class Panier {
    private String idPanier;
    private int quantite;
    private Produit produit;

    public Panier(){}

    public Panier(String idPanier, int quantite, Produit produit) {
        this.idPanier = idPanier;
        this.quantite = quantite;
        this.produit = produit;
    }

    public String getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(String idPanier) {
        this.idPanier = idPanier;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
}
