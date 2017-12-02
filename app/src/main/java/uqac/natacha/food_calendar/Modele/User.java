package uqac.natacha.food_calendar.Modele;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natiassa on 2017-11-30.
 */

public class User {

    @Exclude
    private String uid;

    private String email;
    private String gender;
    private String levelActivity;
    private int nbMeals;
    private double weight;
    private int size;
    private ArrayList<ListeDeCourse> listOfShoppingList;

    public List<String> getNomRepas() {
        return nomRepas;
    }

    public void setNomRepas(List<String> nomRepas) {
        this.nomRepas = nomRepas;
    }

    private List<String> nomRepas;

    public User() {}

    public User(String uid, String email, String gender, String levelActivity, int nbMeals, double weight, int size, ArrayList<ListeDeCourse> listOfShoppingList, List<String> nomRepas) {
        this.uid = uid;
        this.email = email;
        this.gender = gender;
        this.levelActivity = levelActivity;
        this.nbMeals = nbMeals;
        this.weight = weight;
        this.size = size;
        this.listOfShoppingList = new ArrayList<>();
        this.nomRepas=nomRepas;
    }
    public User(String uid, String email, String gender, String levelActivity, int nbMeals, double weight, int size) {
        this.uid = uid;
        this.email = email;
        this.gender = gender;
        this.levelActivity = levelActivity;
        this.nbMeals = nbMeals;
        this.weight = weight;
        this.size = size;
    }

    public User(String uid, String email) {
        this.uid = uid;
        this.email = email;
        this.listOfShoppingList = new ArrayList<>();
        listOfShoppingList.add(new ListeDeCourse("Exemple"));
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }


    public String getUid() {
        return uid;
    }

    public User setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public Boolean hasEmail(){
        return email != null;
    }

    //ajoute une liste de course à la liste de liste de course de l'utilisateur
    public void addShoppingListInListOfShoppingList(ListeDeCourse listeDeCourse){

        if (this.listOfShoppingList == null){
            this.listOfShoppingList = new ArrayList<>();
            this.listOfShoppingList.add(listeDeCourse);
        }
        else{
            this.listOfShoppingList.add(listeDeCourse);
        }

    }


    public void setEmail(String email) {
        this.email = email;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLevelActivity() {
        return levelActivity;
    }

    public void setLevelActivity(String levelActivity) {
        this.levelActivity = levelActivity;
    }

    public int getNbMeals() {
        return nbMeals;
    }

    public void setNbMeals(int nbMeals) {
        this.nbMeals = nbMeals;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ArrayList<ListeDeCourse> getListOfShoppingList() {
        return listOfShoppingList;
    }

    public void setListOfShoppingList(ArrayList<ListeDeCourse> listOfShoppingList) {
        this.listOfShoppingList = listOfShoppingList;
    }


    public Boolean hasGender(){
        return gender != null;
    }
}
