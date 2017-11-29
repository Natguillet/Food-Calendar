package uqac.natacha.food_calendar.Modele;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Pierre on 28/11/2017.
 */

public class Utilisateur {

    private String email;
    private String ID;

    public Utilisateur(){


    }

    public Utilisateur( String email, String ID) {
        this.email = email;
        this.ID = ID;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


}
