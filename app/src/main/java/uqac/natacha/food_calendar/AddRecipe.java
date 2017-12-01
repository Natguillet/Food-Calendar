package uqac.natacha.food_calendar;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uqac.natacha.food_calendar.Modele.Aliment;
import uqac.natacha.food_calendar.Modele.AlimentQuantifie;
import uqac.natacha.food_calendar.Modele.Recipe;

public class AddRecipe extends AppCompatActivity {

    private StorageReference   storageReference;
    private DatabaseReference  databaseReference;
    private Uri                imageURI;
    private String             imageURL = "";
    private ArrayList<Aliment> foodList;
    private ArrayAdapter<String> spinnerArrayAdapter;

    @BindView(R.id.eti_name)          EditText     etName;
    @BindView(R.id.rg_category)       RadioGroup   rgCategory;
    @BindView(R.id.iv_image)          ImageView    ivImage;
    @BindView(R.id.sp_food_0)         Spinner      spFood;
    @BindView(R.id.eti_quantity_0)    EditText     etQuantity;
    @BindView(R.id.eti_instruction_0) EditText     etInstruction;
    @BindView(R.id.ll_ingredients)    LinearLayout llIngredient;
    @BindView(R.id.ll_instructions)   LinearLayout llInstruction;
    @BindView(R.id.eti_cooking)       EditText     etCooking;
    @BindView(R.id.eti_difficulty)    EditText     etDifficulty;
    @BindView(R.id.eti_preparation)   EditText     etPreparation;
    @BindView(R.id.eti_number)        EditText     etNumber;

    public static final String STORAGE_PATH  = "images/";
    public static final String DATABASE_PATH = "Recipes";
    public static final int    REQUEST_CODE  = 1995;

    private static final String TAG = "AddRecipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_recipe_layout);
        ButterKnife.bind(this);
        storageReference  = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);
        createFoodsList();
    }

    private void createFoodsList() {
        foodList = new ArrayList<>();
        String DATABASE_PATH = "Food";
        DatabaseReference foodReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);
        foodReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> spinnerArray = new ArrayList<>();
                Collections.sort(foodList);
                for(Aliment a:foodList){
                    spinnerArray.add(a.getNom()+" (en "+ a.getUnite()+")");
                }
                spinnerArrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.item, spinnerArray);
                spinnerArrayAdapter.setDropDownViewResource(R.layout.dropdown_item);
                spFood.setAdapter(spinnerArrayAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        foodReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Aliment a = dataSnapshot.getValue(Aliment.class);
                foodList.add(a);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    /**
     * Redirect the user to the gallery in order to choose an image
     * @param view
     */
    public void chooseImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select image"), REQUEST_CODE);
    }

    /**
     * Action to perform after an image has been selected
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageURI = data.getData();
            try {
                Bitmap image = MediaStore.Images.Media.getBitmap(getContentResolver(), imageURI);
                ivImage.setImageBitmap(image);
            } catch (java.io.IOException e ){
                e.printStackTrace();
            }
        }
    }


    public String getImageExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    /**
     * Check if all the fields are completed
     * @return True if everything is completed else False
     */
    private boolean isValid() {
        if (imageURI == null
                || TextUtils.isEmpty(etName.getText().toString().trim())
                || TextUtils.isEmpty(etInstruction.getText().toString().trim())
                || TextUtils.isEmpty(etQuantity.getText().toString().trim())
                || TextUtils.isEmpty(etNumber.getText().toString().trim())
                || TextUtils.isEmpty(etDifficulty.getText().toString().trim())
                || TextUtils.isEmpty(etPreparation.getText().toString().trim())
                || TextUtils.isEmpty(etCooking.getText().toString().trim())){
            return false;
        }
        return true;
    }


    /**
     * Complete the process of adding the recipe by sending the informations to the database
     * @param view
     */
    public void addRecipe(View view) {
        if (isValid()){
            // Display a progress dialog to inform the user of the uploading
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Création de la recette en cours...");
            dialog.show();

            // Store the image in Firebase
            StorageReference ref = storageReference.child(STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(imageURI));
            ref.putFile(imageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Add the recipe to the Database
                    imageURL = taskSnapshot.getDownloadUrl().toString();
                    String uploadId = databaseReference.push().getKey();
                    String title = etName.getText().toString().trim();
                    String category = (String) ((RadioButton) findViewById(rgCategory.getCheckedRadioButtonId())).getText();
                    int cooking = Integer.parseInt(etCooking.getText().toString().trim());
                    int preparation = Integer.parseInt(etPreparation.getText().toString().trim());
                    int difficulty = Integer.parseInt(etDifficulty.getText().toString().trim());
                    int number = Integer.parseInt(etNumber.getText().toString().trim());

                    // --- Get ingredients
                    ArrayList<AlimentQuantifie> ingredientsList = new ArrayList<>();
                    ingredientsList.add(new AlimentQuantifie(findFood(spFood.getSelectedItem().toString()), Double.parseDouble(etQuantity.getText().toString())));
                    for(int i=1; i < llIngredient.getChildCount()-1; i++){
                        View view = llIngredient.getChildAt(i);
                        Spinner sp = view.findViewById(R.id.sp_food);
                        EditText et = view.findViewById(R.id.eti_quantity);
                        Aliment food = findFood(sp.getSelectedItem().toString());
                        double quantity = Double.parseDouble(et.getText().toString().trim());
                        ingredientsList.add(new AlimentQuantifie(food,quantity));
                    }

                    // --- Get instructions
                    ArrayList<String> instructionsList = new ArrayList<>();
                    instructionsList.add(etInstruction.getText().toString().trim());
                    for(int i=1; i < llInstruction.getChildCount()-1; i++){
                        View view = llInstruction.getChildAt(i);
                        EditText et = view.findViewById(R.id.eti_instruction);
                        instructionsList.add(et.getText().toString().trim());
                    }

                    Recipe recipe = new Recipe(uploadId, title, category, imageURL, number, difficulty, preparation, cooking, ingredientsList, instructionsList);

                    databaseReference.child(uploadId).setValue(recipe);
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Recette ajoutée", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Erreur :( Veuillez recommencer...", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    dialog.setMessage("Chargement "+ (int)progress+"%");
                }
            });

            Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
            startActivity(intent);

        } else {
            Toast.makeText(this,"Remplir tous les champs", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Return the Food object associated to its name
     */
    private Aliment findFood(String s) {
        int position = s.indexOf('(');
        for (Aliment f : this.foodList){
            if (s.substring(0,position-1).equalsIgnoreCase(f.getNom())){
                return f;
            }
        }
        return null;
    }


    /**
     * Add a row of ingredient
     * @param view
     */
    public void onAddFood(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.add_recipe_food_field_layout, null);
        Spinner sp = rowView.findViewById(R.id.sp_food);
        sp.setAdapter(spinnerArrayAdapter);
        llIngredient.addView(rowView, llIngredient.getChildCount() - 1);
    }

    /**
     * Delete a row of ingredient
     * @param view
     */
    public void onDeleteFood(View view) {
        llIngredient.removeView((View) view.getParent());
    }

    /**
     * Add a row of instructions
     * @param view
     */
    public void onAddInstruction(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.add_recipe_instruction_field_layout, null);
        llInstruction.addView(rowView, llInstruction.getChildCount() - 1);
    }

    /**
     * Delete a row of instructions
     * @param view
     */
    public void onDeleteInstruction(View view) {
        llInstruction.removeView((View) view.getParent());
    }
}
