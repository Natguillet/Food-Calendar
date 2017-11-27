package uqac.natacha.food_calendar.Modele;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import uqac.natacha.food_calendar.R;

public class RecipeList extends AppCompatActivity {

    ListView recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        recipeList = (ListView) findViewById(R.id.listview_recipe);
        List<String> exemple = new ArrayList<String>();
        exemple.add("Item 1");
        exemple.add("Item 2");
        exemple.add("Item 3");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_recipe_list, exemple);
        recipeList.setAdapter(adapter);
    }






}
