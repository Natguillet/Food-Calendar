package uqac.natacha.food_calendar.Utils;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import uqac.natacha.food_calendar.Modele.Aliment;

public class CreateFood {

    public static final String DATABASE_PATH = "Food";

    public static void main(String[] args){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Food");

        ArrayList<Aliment> liste = new ArrayList<>();

        liste.add(new Aliment("Farine", 10, "g"));
        liste.add(new Aliment("Beurre", 100, "g"));
        liste.add(new Aliment("Sucre", 30, "g"));
        liste.add(new Aliment("Sucre glace", 20, "g"));
        liste.add(new Aliment("Oeuf", 100, "unité"));
        liste.add(new Aliment("Citron", 40, "unité"));
        liste.add(new Aliment("Maizena", 10, "g"));
        liste.add(new Aliment("Sel", 5, "g"));

        liste.add(new Aliment("Concombre", 30, "unite"));
        liste.add(new Aliment("Vinaigre", 40, "L"));
        liste.add(new Aliment("Persil", 10, "g"));
        liste.add(new Aliment("Menthe", 20, "g"));

        liste.add(new Aliment("Magret de canard", 200, "unité"));
        liste.add(new Aliment("Huile de pépin de raisin", 90, "L"));
        liste.add(new Aliment("Poivre", 5, "g"));

        liste.add(new Aliment("Bouillon de poulet", 50, "unité"));
        liste.add(new Aliment("Safran", 10, "g"));
        liste.add(new Aliment("Pilon de poulet", 100, "unité"));
        liste.add(new Aliment("Huilde d'olive", 100, "L"));
        liste.add(new Aliment("Oignon", 50, "unité"));
        liste.add(new Aliment("Gousse d'ail", 30, "unité"));
        liste.add(new Aliment("Poivron rouge", 40, "unité"));
        liste.add(new Aliment("Riz", 60, "g"));
        liste.add(new Aliment("Crevette", 80, "unité"));
        liste.add(new Aliment("Moule", 60, "unité"));

        liste.add(new Aliment("Lait", 120, "L"));
        liste.add(new Aliment("Cannelle", 5, "g"));


        for (Aliment a: liste) {
            String uploadId = databaseReference.push().getKey();
            databaseReference.child(uploadId).setValue(a);
        }

    }
}
