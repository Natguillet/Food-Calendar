package uqac.natacha.food_calendar;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import uqac.natacha.food_calendar.Modele.AlimentQuantifie;
import uqac.natacha.food_calendar.Modele.User;

public class DayActivity extends AppCompatActivity {
    private FloatingActionButton mFloatingActionButton;
    private TextView thedate;
    ListView menuListView;
    private FirebaseAuth auth;
    private uqac.natacha.food_calendar.Database.DatabaseManager db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        thedate = (TextView) findViewById(R.id.date);
        Intent incoming = getIntent();
        String date = incoming.getStringExtra("date");
        thedate.setText(date);
        //mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton_add);
        menuListView = (ListView) findViewById(R.id.menu_list_views);

    }

    public void updateView(){

        auth = FirebaseAuth.getInstance();
        FirebaseUser currentFirebaseUser = auth.getCurrentUser() ;
        String currentFirebaseUserID = currentFirebaseUser.getUid();
        db = uqac.natacha.food_calendar.Database.DatabaseManager.getInstance();
        db.getUser(currentFirebaseUserID, new uqac.natacha.food_calendar.Database.DatabaseManager.Result<User>() {
            @Override
            public void onSuccess(User user) {

                List<String> ARcourante = new ArrayList<>();
                ARcourante.add("Petit déjeuner");
                ARcourante.add("Déjeuner");
                ARcourante.add("Dîner");
                ARcourante.add("Souper");

                if(ARcourante != null){
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(DayActivity.this, android.R.layout.simple_list_item_1, ARcourante);
                    menuListView.setAdapter(adapter);
                }

            }
        });

    }



}
