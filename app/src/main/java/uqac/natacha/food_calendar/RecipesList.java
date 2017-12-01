package uqac.natacha.food_calendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uqac.natacha.food_calendar.Adapter.RecipeListAdapter;
import uqac.natacha.food_calendar.Modele.Recipe;

public class RecipesList extends AppCompatActivity {

    @BindView(R.id.recyclerRecette) RecyclerView recycler;

    private LinearLayoutManager layoutManager;
    private FirebaseDatabase database;
    private DatabaseReference recipesList;
    private RecipeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_recettes_layout);
        ButterKnife.bind(this);

        // Get recipes reference from database
        database = FirebaseDatabase.getInstance();
        recipesList = database.getReference().child("Recipes");

        recycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);

        // Get the category from intent and display the recipes
        if(getIntent() != null){
            String category = getIntent().getStringExtra("CATEGORY");
            loadRecipes(category);
        }
    }

    /**
     * Load the recipes according to the category filter
     * @param category
     */
    private void loadRecipes(String category) {
        final Query query = recipesList
                .orderByChild("category")
                .equalTo(category);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Recipe> filteredList = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    filteredList.add(ds.getValue(Recipe.class));
                }
                adapter = new RecipeListAdapter(filteredList, getBaseContext());
                recycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
