package uqac.natacha.food_calendar.Modele;

/**
 * Created by Florian on 22/10/2017.
 */

public enum Unite {
    GRAMME("g"),
    LITRE("L"),
    UNITE("unité");

    private String nom = "";

     Unite(String nom) {
        this.nom = nom;
    }
    public String toString(){ return this.nom; }
}
