package uqac.natacha.food_calendar;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import uqac.natacha.food_calendar.Modele.AlimentQuantifie;
import uqac.natacha.food_calendar.Modele.Recipe;

/**
 * Created by Florian on 31/10/2017.
 */

public class RecipeDetails extends AppCompatActivity {

    @BindView(R.id.textViewCooking)     TextView  tvCooking;
    @BindView(R.id.textViewNumber)      TextView  tvNumber;
    @BindView(R.id.textViewPreparation) TextView  tvPreparation;
    @BindView(R.id.textViewDifficulty)  TextView  tvDifficulty;
    @BindView(R.id.iv_image_recipe)     ImageView ivImage;
    @BindView(R.id.tb_recipe)           android.support.v7.widget.Toolbar   tbRecipe;

    @BindView(R.id.llIngredients) LinearLayout llIngredients;
    @BindView(R.id.llIstructions) LinearLayout llInstructions;

    private String recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_layout);
        ButterKnife.bind(this);

        if (getIntent() != null){
            recipeId = getIntent().getStringExtra("RecipeId");
            if (!recipeId.isEmpty()){
                getRecipeDetails();
            }
        }
    }

    private void getRecipeDetails() {
        FirebaseDatabase.getInstance().getReference("Recipes").child(recipeId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Recipe recipe = dataSnapshot.getValue(Recipe.class);
                Glide.with(getBaseContext()).load(recipe.getImageUrl()).into(ivImage);
                tbRecipe.setTitle(recipe.getTitle());
                tvCooking.setText(recipe.getCookingTime()+" mn");
                tvPreparation.setText(recipe.getPreparationTime()+" mn");
                tvDifficulty.setText(recipe.getDifficulty()+"/5");
                tvNumber.setText(recipe.getNumberPeople()+" personne");

                for (AlimentQuantifie ingredient : recipe.getIngredients()){
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View rowView = inflater.inflate(R.layout.recipe_field, null);
                    TextView tv = rowView.findViewById(R.id.tv_recipe_field);
                    tv.setText(ingredient.getAliment().getNom()+" - "+ingredient.getQuantite()+" "+ingredient.getAliment().getUnite());
                    llIngredients.addView(rowView, llIngredients.getChildCount());
                }

                for (String instruction : recipe.getInstructions()){
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View rowView = inflater.inflate(R.layout.recipe_field, null);
                    TextView tv = rowView.findViewById(R.id.tv_recipe_field);
                    tv.setText(instruction.toString().trim());
                    llInstructions.addView(rowView, llInstructions.getChildCount());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public void addRecipeToMenu(View view) {
        //TODO
    }
}
