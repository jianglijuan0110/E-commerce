package com.example.e_commerce.model.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Produit implements Parcelable{
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

    protected Produit(Parcel in) {
        produitId = in.readString();
        produitImg = in.readString();
        nom = in.readString();
        prix = in.readDouble();
        description = in.readString();
    }

    public static final Creator<Produit> CREATOR = new Creator<Produit>() {
        @Override
        public Produit createFromParcel(Parcel in) {
            return new Produit(in);
        }

        @Override
        public Produit[] newArray(int size) {
            return new Produit[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(produitId);
        dest.writeString(produitImg);
        dest.writeString(nom);
        dest.writeDouble(prix);
        dest.writeString(description);
    }
}
