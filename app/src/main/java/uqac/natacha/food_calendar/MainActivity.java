package uqac.natacha.food_calendar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.ButterKnife;
import uqac.natacha.food_calendar.Database.DatabaseManager;
import uqac.natacha.food_calendar.Modele.User;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    private DatabaseManager db;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // Charger les images lourdes
        Glide.with(this).load(R.drawable.arriereplan_accueil_gris).into((ImageView) findViewById(R.id.iv_arriereplan));
        Glide.with(this).load(R.drawable.logo_couleur).into((ImageView) findViewById(R.id.iv_logo));

        auth = FirebaseAuth.getInstance();
        db = DatabaseManager.getInstance();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                Log.i("SIGNIN", " " + firebaseUser);
                if (firebaseUser == null) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                } else {
                    uid = firebaseUser.getUid();
                    db.getUser(uid, new DatabaseManager.Result<User>()
                    {
                        @Override
                        public void onSuccess(User user)
                        {
                            if (!user.hasGender()) {
                                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                            } else {
                                startActivity(new Intent(MainActivity.this, CalendarActivity.class));
                            }
                        }

                        @Override
                        public void onFailure()
                        {
                            Log.e("User:", "User don't exist");
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                    });
                }
            }
        };
    }

    @Override
    public void onResume()
    {
        super.onResume();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        auth.removeAuthStateListener(authListener);
    }
}
