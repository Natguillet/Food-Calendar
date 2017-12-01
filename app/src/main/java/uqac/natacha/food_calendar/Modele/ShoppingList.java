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

import java.lang.reflect.Array;
import java.util.ArrayList;
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
    private User UtilisateurCourant ;
    private FirebaseAuth auth;
    private uqac.natacha.food_calendar.Database.DatabaseManager db;
    private   ContextMenu menuInner;
    ContextMenu.ContextMenuInfo menuInfoInner;
    ArrayList<ListeDeCourse> shoppingListArray;

    public  ContextMenu menu;
    public View v, ContextMenu;
    public android.view.ContextMenu.ContextMenuInfo menuInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i("Shopping List", "ON CREATE ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton_add);
        shoppingList = (ListView) findViewById(R.id.listview_shoppingList);


        printAdapterListOfShoppingList();


        shoppingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ShoppingList.this, StuffList.class);

                intent.putExtra("position", position);
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
                                        addListeDeCourse(listeDeCourse);


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




    }

    private void  addListeDeCourse(final ListeDeCourse listeDeCourse){

        auth = FirebaseAuth.getInstance();
        FirebaseUser currentFirebaseUser = auth.getCurrentUser() ;
        String currentFirebaseUserID = currentFirebaseUser.getUid();
        db = uqac.natacha.food_calendar.Database.DatabaseManager.getInstance();
        db.getUser(currentFirebaseUserID, new uqac.natacha.food_calendar.Database.DatabaseManager.Result<User>() {
            @Override
            public void onSuccess(User user) {

               user.addShoppingListInListOfShoppingList(listeDeCourse);
               db.setUser(user);
               printAdapterListOfShoppingList();
            }
        });


    }


    private void  printAdapterListOfShoppingList(){

            auth = FirebaseAuth.getInstance();
            FirebaseUser currentFirebaseUser = auth.getCurrentUser() ;
            String currentFirebaseUserID = currentFirebaseUser.getUid();
            db = uqac.natacha.food_calendar.Database.DatabaseManager.getInstance();
            db.getUser(currentFirebaseUserID, new uqac.natacha.food_calendar.Database.DatabaseManager.Result<User>() {
                @Override
                public void onSuccess(User user) {

                    if (user.getListOfShoppingList()!= null){

                        if (!user.getListOfShoppingList().isEmpty()) {
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShoppingList.this, android.R.layout.simple_list_item_1, (List) user.getListOfShoppingList());
                            shoppingList.setAdapter(adapter);
                        }
                    }

                    else
                    {
                        user.setListOfShoppingList(new ArrayList<ListeDeCourse>());
                        db.setUser(user);
                    }
                }
            });
        }


    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {



        auth = FirebaseAuth.getInstance();
        FirebaseUser currentFirebaseUser = auth.getCurrentUser() ;
        String currentFirebaseUserID = currentFirebaseUser.getUid();
        db = uqac.natacha.food_calendar.Database.DatabaseManager.getInstance();

        menuInner =menu;

        db.getUser(currentFirebaseUserID, new uqac.natacha.food_calendar.Database.DatabaseManager.Result<User>() {
            @Override
            public void onSuccess(User user) {

                shoppingListArray = user.getListOfShoppingList();
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInner;
                menuInner.setHeaderTitle(shoppingListArray.get(info.position).getNomListeDeCourse());
                getMenuInflater().inflate(R.menu.menu_option_shopping_list, menuInner);
            }
        });

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
