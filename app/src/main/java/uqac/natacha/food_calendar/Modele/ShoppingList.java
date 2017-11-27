package uqac.natacha.food_calendar.Modele;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uqac.natacha.food_calendar.R;
import uqac.natacha.food_calendar.StuffList;


public class ShoppingList extends AppCompatActivity {

    ListView shoppingList = null;
    private DatabaseReference mDatabase;
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton_add);

        initDatabase();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("ShoppingList");

        shoppingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ShoppingList.this, StuffList.class);
                startActivity(intent);
            }
        });

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ajout d'une liste de course
            }
        });

    }
    public void initDatabase(){

        shoppingList = (ListView) findViewById(R.id.listview_shoppingList);

        List<String> exemple = new ArrayList<String>();
        exemple.add("ShoppingList 1");
        exemple.add("ShoppingList 2");
        exemple.add("ShoppingList 3");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShoppingList.this, android.R.layout.simple_list_item_1, exemple);
        shoppingList.setAdapter(adapter);

        //mDatabase.push().setValue("ShoppingList 1");
        HashMap<String,String> dataMap = new HashMap<>();
        dataMap.put("name", "ShoppingList1");



        mDatabase.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){

                    Toast.makeText(ShoppingList.this, "Data stored !", Toast.LENGTH_LONG).show();
                }

                else {
                    Toast.makeText(ShoppingList.this, "Data store failed !", Toast.LENGTH_LONG).show();

                }
            }
        });

    }






}
