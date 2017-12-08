package uqac.natacha.food_calendar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import uqac.natacha.food_calendar.Database.DatabaseManager;
import uqac.natacha.food_calendar.Modele.User;

public class StatActivity extends AppCompatActivity {

    @BindView(R.id.iv_arriereplan) ImageView ivBackground;
    @BindView(R.id.textView4)      TextView tvScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);
        ButterKnife.bind(this);

        // Charger les images lourdes
        Glide.with(this).load(R.drawable.arriereplan_accueil_gris).into(ivBackground);
        
        setScore();
    }

    private void setScore() {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        final DatabaseManager db = DatabaseManager.getInstance();

        FirebaseUser user = auth.getCurrentUser();
        String uid = user.getUid();
        db.getUser(uid, new DatabaseManager.Result<User>() {
            @Override
            public void onSuccess(User user) {
                int heigth = user.getSize();
                double weight = user.getWeight();
                double base = 0;
                if (user.getGender().equalsIgnoreCase("Homme")){
                    base = (1.083*Math.pow(weight,0.48)*Math.pow(heigth,0.5)*Math.pow(30,-0.13))*(1000/4.1855);
                } else {
                    base = (0.963*Math.pow(weight,0.48)*Math.pow(heigth,0.5)*Math.pow(30,-0.13))*(1000/4.1855);
                }
                switch (user.getLevelActivity()){
                    case "Légère":
                        tvScore.setText(String.valueOf((int) ((int) base*1.56)));
                        break;
                    case "Modérée":
                        tvScore.setText(String.valueOf((int) ((int) base*1.64)));
                        break;
                    case "Importante":
                        tvScore.setText(String.valueOf((int) ((int) base*1.82)));
                        break;
                }
            }
        });
    }
}
