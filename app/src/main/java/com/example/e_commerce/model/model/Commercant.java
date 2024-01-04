package com.example.e_commerce.model.model;

public class Commercant {

    private String nomCommercant;
    private String prenomCommercant;
    private String email;
    private String motDePasse;

    public Commercant() {
    }

    public Commercant(String nomCommercant, String prenomCommercant, String email, String motDePasse) {
        this.nomCommercant = nomCommercant;
        this.prenomCommercant = prenomCommercant;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public String getNomCommercant() {
        return nomCommercant;
    }

    public void setNomCommercant(String nomCommercant) {
        this.nomCommercant = nomCommercant;
    }

    public String getPrenomCommercant() {
        return prenomCommercant;
    }

    public void setPrenomCommercant(String prenomCommercant) {
        this.prenomCommercant = prenomCommercant;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

}
