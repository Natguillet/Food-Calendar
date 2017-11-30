package uqac.natacha.food_calendar.Modele;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Pierre on 28/11/2017.
 */

public class Utilisateur {

    private String email;
    private String ID;
    private ArrayList<ListeDeCourse> listOfShoppingList;

    public Utilisateur(){
    }

    public Utilisateur( String email, String ID,ArrayList<ListeDeCourse>  listeDeCourseTadam) {
        this.email = email;
        this.ID = ID;
       this.listOfShoppingList = listeDeCourseTadam;

    }

    //ajoute une liste de course à la liste de liste de course de l'utilisateur
    public void addShoppingListInListOfShoppingList(ListeDeCourse listeDeCourse){
        this.listOfShoppingList.add(listeDeCourse);
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

    public ArrayList<ListeDeCourse> getListOfShoppingList() {
        return listOfShoppingList;
    }

    public void setListOfShoppingList(ArrayList<ListeDeCourse> listOfShoppingList) {
        this.listOfShoppingList = listOfShoppingList;
    }






}
