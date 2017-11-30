package uqac.natacha.food_calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Florian on 24/10/2017.
 */


public class Accueil extends AppCompatActivity {

    private static final String TAG = "Accueil";

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
        //TODO Pierre
    }

    /**
     * Actions à effectuer lors du click sur le bouton "CONNEXION"
     * @param view
     */
    public void onConnexionClick(View view) {
        //TODO Pierre
        Intent intent = new Intent(this, RecipeCategories.class);
        startActivity(intent);
    }
}
