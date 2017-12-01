package uqac.natacha.food_calendar.Modele;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import uqac.natacha.food_calendar.LoginActivity;
import uqac.natacha.food_calendar.CalendarActivity;

/**
 * Created by Pierre on 30/11/2017.
 */


// Cette classe à pour but de gérer la base de données, c'est à dire son initialisation et la communication
public class DatabaseManager {


    private String currentFirebaseUserID;
    private FirebaseDatabase database;
    //private DatabaseReference table_user;
    private FirebaseAuth auth;
    private FirebaseUser currentFirebaseUser;


    private Utilisateur utilisateur;


    // constructeur : on initialise l'utilisateur
    public DatabaseManager() {

        Log.i("DatabaseManager", "LE CONSTRUCTEUR DE DATABASEMANAGER");

    }


    // initialise l'utilisateur (le récupère depuis la base de donnée)
    private void initUser(){




    }

    // Enregistre/met à jour les dsonnées de l'utilisateur
    private void storeUtilisateur( Utilisateur utilisateur){

       // table_user.child(currentFirebaseUserID).setValue(utilisateur);
        Log.w("DatabaseManager", "New utilisateur stored");

    }



    public Utilisateur getUtilisateur() {

        Log.i("DatabaseManager", "ON INITIALISE L'UTILISATEUR ");


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference table_user = database.getReference("User");
        auth = FirebaseAuth.getInstance();
        //currentFirebaseUser = auth.getCurrentUser() ;
        //currentFirebaseUserID = currentFirebaseUser.getUid();
        FirebaseUser currentFirebaseUser = auth.getCurrentUser() ;
        String currentFirebaseUserID = currentFirebaseUser.getUid();

        table_user.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                utilisateur = dataSnapshot.getValue(Utilisateur.class);
                System.out.println(utilisateur);
                Log.i("DatabaseManager", "get Value of Utilisateur");
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("DatabaseManager", "onCancelled error");
            }


        });


        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

}
