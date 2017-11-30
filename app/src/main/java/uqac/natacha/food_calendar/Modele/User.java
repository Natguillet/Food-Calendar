package uqac.natacha.food_calendar.Modele;

import com.google.firebase.database.Exclude;

import java.util.Set;

/**
 * Created by Natiassa on 2017-11-30.
 */

public class User {

    @Exclude
    private String uid;

    private String username;
    private String gender;
    private String levelActivity;
    private int nbMeals;
    private double weight;
    private int size;

    public User() {}

    public User(String uid, String username, String gender, String levelActivity, int nbMeals, double weight, int size) {
        this.uid = uid;
        this.username = username;
        this.gender = gender;
        this.levelActivity = levelActivity;
        this.nbMeals = nbMeals;
        this.weight = weight;
        this.size = size;
    }

    public String getUid() {
        return uid;
    }

    public User setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public Boolean hasUsername(){
        return username != null;
    }
}
