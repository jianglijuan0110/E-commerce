package com.example.e_commerce.data.model;

public class Adresse {
    private String titre;
    private String ville;
    private String pays;
    private String nom;
    private String rue;
    private String tel;

    public Adresse(){}

    public Adresse(String titre,String ville,String pays,String nom,String rue,String tel){
        this.titre = titre;
        this.ville = ville;
        this.pays = pays;
        this.nom = nom;
        this.rue = rue;
        this.tel = tel;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
