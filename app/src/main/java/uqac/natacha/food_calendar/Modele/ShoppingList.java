package uqac.natacha.food_calendar.Modele;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.concurrent.TimeUnit;

import uqac.natacha.food_calendar.CalendarActivity;
import uqac.natacha.food_calendar.LoginActivity;
import uqac.natacha.food_calendar.MainActivity;
import uqac.natacha.food_calendar.R;
import uqac.natacha.food_calendar.RegisterActivity;
import uqac.natacha.food_calendar.StuffList;


public class ShoppingList extends AppCompatActivity {

    ListView shoppingList = null;
    private FloatingActionButton mFloatingActionButton;
     private  DatabaseManager databaseManager;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private String uid;


    private uqac.natacha.food_calendar.Database.DatabaseManager db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i("Shopping List", "ON CREATE ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton_add);
        shoppingList = (ListView) findViewById(R.id.listview_shoppingList);


        addList();


        shoppingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ShoppingList.this, StuffList.class);
                startActivity(intent);
            }
        });

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(ShoppingList.this);
                View promptsView = li.inflate(R.layout.prompts, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        ShoppingList.this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);
// set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {

                                        ListeDeCourse listeDeCourse = new ListeDeCourse(userInput.getText().toString());



                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();



            }
        });

        printAdapterListOfShoppingList();


    }



        private void printAdapterListOfShoppingList(){

              //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShoppingList.this, android.R.layout.simple_list_item_1, (List) utilisateur2.getListOfShoppingList());
                //shoppingList.setAdapter(adapter);
    }

        private void addList(){

            auth = FirebaseAuth.getInstance();
            db = uqac.natacha.food_calendar.Database.DatabaseManager.getInstance();

            authListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    Log.i("SIGNIN", " " + firebaseUser);
                        uid = firebaseUser.getUid();
                        db.getUser(uid, new uqac.natacha.food_calendar.Database.DatabaseManager.Result<User>()
                        {
                            @Override
                            public void onSuccess(User user)
                            {

                                Toast.makeText(ShoppingList.this, "EMAIL UTILISATEUR :" +   user.getEmail(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onFailure()
                            {

                            }
                        });
                    }

            };


        }



    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(databaseManager.getUtilisateur().getListOfShoppingList().get(info.position).getNomListeDeCourse());
        getMenuInflater().inflate(R.menu.menu_option_shopping_list, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;

        switch (item.getItemId()){
            case R.id.item_delete:

                //supprimer l'item
                break;


        }

        return super.onContextItemSelected(item);
    }







}
