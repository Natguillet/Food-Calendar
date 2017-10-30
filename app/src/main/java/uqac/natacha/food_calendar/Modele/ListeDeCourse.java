package uqac.natacha.food_calendar.Modele;

import java.util.ArrayList;

/**
 * Classe permettant de définir une Liste de course
 * @author Natacha GUILLET - Pierre MASQUILIER - Florian VIDAL
 * @version 1.0
 */

public class ListeDeCourse {

    private ArrayList<AlimentQuantifie> articles;

    /**
     * Constructeur de la classe ListeDeCourse. Initialise une liste vide.
     */
    public ListeDeCourse() {
        this.articles = new ArrayList<>();
    }

    /**
     * Constructeur de la classe ListeDeCourse. Initialise une liste déjà existante.
     *
     * @param articles articles présent dans la liste
     */
    public ListeDeCourse(ArrayList<AlimentQuantifie> articles) {
        this.articles = articles;
    }


    /* ***************************************************************************************** **
     *                             G E T T E R    &    S E T T E R                                *
     * ***************************************************************************************** **/

    public ArrayList<AlimentQuantifie> getArticles() {
        return articles;
    }


    /* ***************************************************************************************** **
     *                          F O N C T I O N S    P U B L I Q U E S                            *
     * ***************************************************************************************** **/

    /**
     * Ajouter un article à la liste de course
     * @param article article quantifié à ajouter
     */
    public void ajouterAlimentQuantifie(AlimentQuantifie article) {

        AlimentQuantifie articleDejaPresent = trouverAliment(article);
        if (articleDejaPresent == null) {
            this.articles.add(article);
        } else {
            articleDejaPresent.ajouter(article.getQuantite());
        }
    }


    /**
     * Supprimer un article à la liste de course (réduit la quantité et si <=0 supprime)
     * @param article article quantifié à enlever
     */
    public void supprimerAlimentQuantifie(AlimentQuantifie article) {

        AlimentQuantifie articleDejaPresent = trouverAliment(article);
        if (articleDejaPresent != null) {
            double nouvelleQuantite = articleDejaPresent.getQuantite() - article.getQuantite();
            if (nouvelleQuantite > 0) {
                articleDejaPresent.ajouter(article.getQuantite());
            } else {
                this.articles.remove(articleDejaPresent);
            }
        }
    }

    /**
     * Remise à zéro de la liste de course
     */
    public void raz(){ this.articles = new ArrayList<>(); }


    @Override
    public String toString() {

        String res = "----------------------------\nListe :";
        for(AlimentQuantifie a : this.articles){
            res += "\n" + a.toString();
        }
        return res+"\n----------------------------";
    }



    /* ***************************************************************************************** **
     *                         F O N C T I O N S    A U X I L I A I R E S                         *
     * ***************************************************************************************** **/


    /**
     * Retourne l'aliment quantifié correspondant à l'aliment paramètre s'il se trouve déjà dans la
     * liste. Sinon on retourne null
     * @param article article
     * @return aliment quantifié de la liste ou null;
     */
    private AlimentQuantifie trouverAliment(AlimentQuantifie article) {
        Aliment aliment = article.getAliment();
        for(AlimentQuantifie a : this.articles){
            if (a.getAliment().equals(aliment)){
                return a;
            }
        }
        return null;
    }

}
