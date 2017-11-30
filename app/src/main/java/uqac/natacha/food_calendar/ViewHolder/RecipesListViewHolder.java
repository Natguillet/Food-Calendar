package uqac.natacha.food_calendar.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import uqac.natacha.food_calendar.Interface.ItemClickListener;
import uqac.natacha.food_calendar.R;

/**
 * Created by Florian on 02/11/2017.
 */

public class RecipesListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView image;
    private TextView title;

    private ItemClickListener itemClickListener;

    public RecipesListViewHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.iv_CardRecette);
        title = itemView.findViewById(R.id.tv_CardRecette);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener icl) {
        this.itemClickListener = icl;
    }

    @Override
    public void onClick(View view) {
        this.itemClickListener.onClick(view, getAdapterPosition(), false);
    }

    public ImageView getImage() {
        return image;
    }

    public TextView getTitle() {
        return title;
    }
}
