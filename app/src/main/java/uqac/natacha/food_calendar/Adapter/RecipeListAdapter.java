package uqac.natacha.food_calendar.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import uqac.natacha.food_calendar.Interface.ItemClickListener;
import uqac.natacha.food_calendar.Modele.Recipe;
import uqac.natacha.food_calendar.R;
import uqac.natacha.food_calendar.RecipeDetails;
import uqac.natacha.food_calendar.ViewHolder.RecipesListViewHolder;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipesListViewHolder> {

    private List<Recipe> recipes;
    private Context      context;

    public RecipeListAdapter(List<Recipe> recipes, Context context) {
        this.recipes = recipes;
        this.context = context;
    }

    public Context getContext(){
        return this.context;
    }

    @Override
    public RecipesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.card_recipe_layout, parent, false);

        return new RecipesListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecipesListViewHolder holder, int position) {
        Recipe recipe = this.recipes.get(position);
        ImageView imageView = holder.getImage();
        Glide.with(this.context).load(recipe.getImageUrl()).into(imageView);
        TextView textView = holder.getTitle();
        textView.setText(recipe.getTitle());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean longClick) {
                Intent intent = new Intent(getContext(), RecipeDetails.class);
                intent.putExtra("RecipeId", recipes.get(position).getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
}
