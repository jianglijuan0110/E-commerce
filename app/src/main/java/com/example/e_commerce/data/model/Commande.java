package com.example.e_commerce.data.model;

public class Commande {
    private String commandeId;
    public Commande() {
    }

    public Commande(String commandeId) {
        this.commandeId = commandeId;
    }

    public String getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(String commandeId) {
        this.commandeId = commandeId;
    }
}
