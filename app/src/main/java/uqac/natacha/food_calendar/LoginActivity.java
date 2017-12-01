package uqac.natacha.food_calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import uqac.natacha.food_calendar.Database.DatabaseManager;
import uqac.natacha.food_calendar.Modele.User;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    // --------------------------------------------------------------------------------------------
    // INFO POUR PIERRE :
    // C'est peut être pas les TextInputLayout qu'il faut instancier pour avoir acces aux info
    // Si c'est pas ça instancies les EditText
    // --------------------------------------------------------------------------------------------

    @BindView(R.id.til_pseudo)             TextInputLayout tilPseudo;
    @BindView(R.id.til_mdp)                TextInputLayout tilMotdepasse;
    @BindView(R.id.til_pseudo_inscription) TextInputLayout tilPseudoInscription;
    @BindView(R.id.til_mdp_inscription)    TextInputLayout tilMotdepasseInscription;
    @BindView(R.id.til_mdp_inscription2)   TextInputLayout tilMotdepasseInscription2;
    @BindView(R.id.btn_connexion)          Button btnConnexion;
    @BindView(R.id.tv_inscription)         TextView tvInscription;
    @BindView(R.id.iv_arriereplan)         ImageView ivArriereplan;
    @BindView(R.id.iv_logo)                ImageView ivLogo;
    @BindView(R.id.ib_bas)                 ImageButton btnMonter;
    @BindView(R.id.ib_haut)                ImageButton btnDescendre;
    @BindView(R.id.my_view)                View myView;

    private boolean estEnHaut = false;
    private int angleRotation = 0;

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private FirebaseAuth.AuthStateListener authListener;

    private EditText inputEmail, inputPassword, inputPassword2;

    private DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil_layout);
        ButterKnife.bind(this);

        // Charger les images lourdes
        Glide.with(this).load(R.drawable.arriereplan_accueil_gris).into(ivArriereplan);
        Glide.with(this).load(R.drawable.logo_couleur).into(ivLogo);

        //
        myView.setVisibility(View.INVISIBLE);

        //on récupère l'utilisateur courant
        auth = FirebaseAuth.getInstance();
        db = DatabaseManager.getInstance();

        authListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser == null) {
                    //finish();
                    Log.e("USER", "NULL USER !");
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

    @Override
    public void onBackPressed() {
        if (estEnHaut){
            onSlideViewButtonClick(null);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Fait monter la view pour la faire apparaitre à l'écran
     * @param view
     */
    public void faireMonter(View view){
        // Animer le panneaux avec toutes les informations
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,0,
                view.getHeight(),0);
        animate.setDuration(300);
        animate.setFillAfter(true);
        view.startAnimation(animate);

        //
        TranslateAnimation animateButton = new TranslateAnimation(
                0,0,
                btnMonter.getHeight(),-myView.getHeight() + btnDescendre.getHeight()+ 50);
        animateButton.setDuration(300);
        animateButton.setFillAfter(false);
        btnMonter.startAnimation(animateButton);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                btnMonter.setVisibility(View.INVISIBLE);
                btnDescendre.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                btnDescendre.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    /**
     * Fait descendre la view pour la faire apparaitre à l'écran
     * @param view
     */
    public void faireDescendre(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0, 0,
                0,view.getHeight());
        animate.setDuration(300);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        TranslateAnimation animateButton = new TranslateAnimation(
                0,0,
                btnDescendre.getHeight(), btnMonter.getHeight());
        animateButton.setDuration(100);
        animateButton.setFillAfter(false);
        btnDescendre.startAnimation(animateButton);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                btnMonter.setVisibility(View.INVISIBLE);
                btnDescendre.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                btnMonter.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    /**
     * Lorsqu'un des imagesButton est clické, on fait monter ou descendre la view comportant les
     * informations pour l'insctiption
     * @param view le bouton
     */
    public void onSlideViewButtonClick(View view) {
        if (estEnHaut) {
            faireDescendre(myView);
        } else {
            faireMonter(myView);
        }
        estEnHaut = !estEnHaut;
        angleRotation = angleRotation == 0 ? 180 : 0;  //toggle
        btnMonter.animate().rotation(angleRotation).setDuration(100).start();
        btnDescendre.animate().rotation(angleRotation).setDuration(100).start();

    }

    /**
     * Actions à effectuer lors du click sur le bouton "S'INSCRIRE"
     * @param view le bouton
     */
    public void onInscriptionClick(View view) {


        inputEmail = (EditText) findViewById(R.id.eti_pseudo_inscription);
        inputPassword = (EditText) findViewById(R.id.eti_mdp_inscription);
        inputPassword2 = (EditText) findViewById(R.id.eti_mdp_inscription2);

        if (TextUtils.isEmpty(inputEmail.getText().toString().trim())) {

            Toast.makeText(LoginActivity.this, "Veuillez rentrer une adresse mail", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(inputPassword.getText().toString().trim())) {
            Toast.makeText(LoginActivity.this, "Veuillez rentrer un mot de passe", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(inputPassword2.getText().toString().trim())) {
            Toast.makeText(LoginActivity.this, "Veuillez confirmer votre mot de passe", Toast.LENGTH_SHORT).show();
            return;
        }

        if (inputPassword.getText().toString().trim().length() < 6) {
            Toast.makeText(LoginActivity.this, "Veuillez rentrer un mot de passe d'au moins 6 caractères ! ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (inputPassword.getText().toString().trim().length() != inputPassword2.getText().toString().trim().length()) {
            Toast.makeText(LoginActivity.this, "Les deux mots de passe ne sont pas identiques", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(inputEmail.getText().toString().trim(), inputPassword.getText().toString().trim())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>()
                {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.i("succes", "enregistrement authen");
                        if (firebaseUser != null){
                            String email = inputEmail.getText().toString();
                            db.setUser(new User(firebaseUser.getUid(), email))
                                    .addOnSuccessListener(new OnSuccessListener<Void>()
                                    {
                                        @Override
                                        public void onSuccess(Void aVoid)
                                        {
                                            Log.i("succes", "enregistrement bd réussi");
                                            startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener()
                                    {
                                        @Override
                                        public void onFailure(@NonNull Exception e)
                                        {
                                            Log.d("Erreur", e.getMessage());
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("SOS DO IT", e.getMessage());
                        Toast.makeText(LoginActivity.this, "L'enregistrement a échoué. Veuillez essayer avec une autre adresse mail.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Actions à effectuer lors du click sur le bouton "CONNEXION"
     * @param view
     */
    public void onConnexionClick(View view) {

        inputEmail = (EditText) findViewById(R.id.eti_pseudo);
        inputPassword = (EditText) findViewById(R.id.eti_mdp);

        if (TextUtils.isEmpty(inputEmail.getText().toString().trim())){
            Toast.makeText(LoginActivity.this, "Veuillez rentrer une adresse mail ! ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(inputPassword.getText().toString().trim())) {
            Toast.makeText(LoginActivity.this, "Veuillez renter un mot de passe !", Toast.LENGTH_SHORT).show();
            return;
        }


        final Task<AuthResult> authResultTask = auth.signInWithEmailAndPassword(inputEmail.getText().toString().trim(), inputPassword.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "L'identification a échoué !", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            Toast.makeText(LoginActivity.this, "L'identification a réussi !", Toast.LENGTH_SHORT).show();

                            String uid = firebaseUser.getUid();
                            db.getUser(uid, new DatabaseManager.Result<User>() {
                                @Override
                                public void onSuccess(User user) {
                                    if (!user.hasGender()) {
                                        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                                    } else {
                                        startActivity(new Intent(LoginActivity.this, CalendarActivity.class));
                                    }
                                }
                            });
                            return;
                        }
                    }
                });

    }
}
