package uqac.natacha.food_calendar.Modele;
/**
 * Classe permettant de définir un aliment et sa quantité
 * @author Natacha GUILLET - Pierre MASQUILIER - Florian VIDAL
 * @version 1.0
 */

public class AlimentQuantifie {

    private Aliment aliment;
    private double  quantite;

    public AlimentQuantifie() {
    }

    /**
     * Constructeur de la classe AlimentQuantifie
     * @param aliment aliment de base
     * @param quantite quantité de l'aliment
     */
    public AlimentQuantifie(Aliment aliment, double quantite){
        this.aliment  = aliment;
        this.quantite = quantite;
    }

    /* ***************************************************************************************** **
     *                             G E T T E R    &    S E T T E R                                *
     * ***************************************************************************************** **/

    public Aliment getAliment() {
        return aliment;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    /* ***************************************************************************************** **
     *                          F O N C T I O N S    P U B L I Q U E S                            *
     * ***************************************************************************************** **/

    /**
     * Augmente la quantité de l'aliment de la valeur en paramètre
     * @param quantite quantité à ajouter
     */
    public void ajouter(double quantite){
        this.quantite += quantite;
    }

    /**
     * Diminue la quantité de l'aliment de la valeur en paramètre
     * @param quantite quantité à retirer
     */
    public void soustraire(double quantite){
        this.quantite -= quantite;
    }

    /**
     * Donne le nombre de calories actuelles de l'AlimentQuantifié
     * @return nombre de calories
     */
    public double calculerCalories(){
        return this.quantite*this.aliment.getCaloriesParUnite();
    }

    @Override
    public String toString() {
        return this.aliment.getNom().toUpperCase()+" : "+this.quantite+" "+this.aliment.getUnite().toString();
    }
}
