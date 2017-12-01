package uqac.natacha.food_calendar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import uqac.natacha.food_calendar.Database.DatabaseManager;
import uqac.natacha.food_calendar.Modele.User;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.eti_heigth)           EditText   etHeight;
    @BindView(R.id.eti_weigth)           EditText   etWeight;
    @BindView(R.id.eti_number_meals)     EditText   etNumberMeals;
    @BindView(R.id.sp_physical_activity) Spinner    spPhysicalActivity;
    @BindView(R.id.rg_gender)            RadioGroup rgGender;
    @BindView(R.id.imageViewBackground)  ImageView  ivBackground;

    private FirebaseAuth auth;

    private DatabaseManager db;
    private ArrayAdapter<CharSequence> arrayAdapter;

    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.jogger).into(ivBackground);

        // Instantiate the spinner with its dropdown items
        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.physicalActivity,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPhysicalActivity.setAdapter(arrayAdapter);
    }

    public void setUserProfile(View view) {
        if (etHeight.getText().toString().trim().isEmpty()
                || etWeight.getText().toString().trim().isEmpty()
                || etNumberMeals.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Veuillez entrer tous les champs...", Toast.LENGTH_SHORT).show();

        } else {
            // Save the info into the database
            auth = FirebaseAuth.getInstance();
            db = DatabaseManager.getInstance();
            FirebaseUser user = auth.getCurrentUser();

            String gender = (String) ((RadioButton) findViewById(rgGender.getCheckedRadioButtonId())).getText();
            String levelActivity = spPhysicalActivity.getSelectedItem().toString();
            int nbMeals = Integer.parseInt(etNumberMeals.getText().toString().trim());
            int size = Integer.parseInt(etHeight.getText().toString().trim());
            double weight = Double.parseDouble(etWeight.getText().toString().trim());

            User userUpdated = new User(user.getUid(), user.getEmail(), gender, levelActivity, nbMeals, weight, size);
            db.setUser(userUpdated);
            Log.d(TAG, "onSuccess: "+ user.getUid() + " - " + user.getEmail());

            Intent intent = new Intent(this, CalendarActivity.class);
            startActivity(intent);
        }
    }
}
