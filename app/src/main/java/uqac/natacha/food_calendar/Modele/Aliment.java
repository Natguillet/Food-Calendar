package uqac.natacha.food_calendar.Modele;

/**
 * Classe permettant de définir un aliment
 * @author Natacha GUILLET - Pierre MASQUILIER - Florian VIDAL
 * @version 1.0
 */

public class Aliment {

    private String nom;
    private double caloriesParUnite;
    private Unite  unite;

    /**
     * Constructeur de la classe Aliment
     * @param nom nom de l'aliment
     * @param caloriesParUnite nombre de calories pour une unité (g,L,u) de l'aliment
     * @param unite gramme, litre ou unité
     */
    public Aliment(String nom, double caloriesParUnite, Unite unite){
        this.nom              = nom;
        this.caloriesParUnite = caloriesParUnite;
        this.unite            = unite;
    }


    public Aliment() {
    }


    /* ***************************************************************************************** **
     *                             G E T T E R    &    S E T T E R                                *
     * ***************************************************************************************** **/

    public String getNom() {
        return nom;
    }

    public double getCaloriesParUnite() {
        return caloriesParUnite;
    }

    public Unite getUnite() {
        return unite;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCaloriesParUnite(double caloriesParUnite) {
        this.caloriesParUnite = caloriesParUnite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }
}
