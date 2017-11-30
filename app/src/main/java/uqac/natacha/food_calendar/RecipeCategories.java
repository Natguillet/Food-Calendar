package uqac.natacha.food_calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeCategories extends AppCompatActivity {

    @BindView(R.id.arriereplan_categorie) ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_category_layout);
        ButterKnife.bind(this);
        
        // Load background
        Glide.with(this).load(R.drawable.arriereplan_categories).into(background);
    }

    public void onClick(View view) {
        String categoryExtra = "";

        switch (view.getId()) {
            case R.id.btn_entree:
                categoryExtra = "Entrée";
                break;
            case R.id.btn_plat:
                categoryExtra = "Plat principal";
                break;
            case R.id.btn_dessert:
                categoryExtra = "Dessert";
                break;
        }

        Intent intent = new Intent(this, RecipesList.class);
        intent.putExtra("CATEGORY", categoryExtra);
        startActivity(intent);
    }
}
