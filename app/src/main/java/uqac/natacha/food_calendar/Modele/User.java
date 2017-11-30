package uqac.natacha.food_calendar.Modele;

import com.google.firebase.database.Exclude;

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

    public User() {}

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
}
