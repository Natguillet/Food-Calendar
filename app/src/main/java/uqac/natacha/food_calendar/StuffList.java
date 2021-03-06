package uqac.natacha.food_calendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import uqac.natacha.food_calendar.Modele.Aliment;
import uqac.natacha.food_calendar.Modele.AlimentQuantifie;
import uqac.natacha.food_calendar.Modele.ListeDeCourse;
import uqac.natacha.food_calendar.Modele.User;


public class StuffList extends AppCompatActivity {


    ListView itemListView;
    ImageView imageViewItem;
    TextView textViewNameItem;
    TextView textViewQuantityItem;
    CheckBox checkBoxOwnItem;
    Button buttonAddStuff;
    EditText editText_stuff;
    EditText editText_quantity;
    int iGlob;
    int positionShoppingList;
    ListeDeCourse itemListArray;


    ListView shoppingList = null;
    private User UtilisateurCourant ;
    private FirebaseAuth auth;
    private uqac.natacha.food_calendar.Database.DatabaseManager db;
    private int positionDansLaListe;
    public  int index;
    public  ContextMenu menu;

    private   ContextMenu menuInner;
    ContextMenu.ContextMenuInfo menuInfoInner;

    List<String> list = new ArrayList<String>();
    ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stuff_list);

        itemListView = (ListView) findViewById(R.id.item_list_view);
        positionDansLaListe = getIntent().getIntExtra("position", 1);

        updateView();
        registerForContextMenu(itemListView);
        //list.add("delete");
       // adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,list);
        //itemListView.setAdapter(adapter);

        buttonAddStuff = (Button) findViewById(R.id.button_addStuff) ;

        buttonAddStuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editText_stuff = findViewById(R.id.editText_stuffAdd);
                editText_quantity = findViewById(R.id.quantityAdd);

                auth = FirebaseAuth.getInstance();
                FirebaseUser currentFirebaseUser = auth.getCurrentUser() ;
                String currentFirebaseUserID = currentFirebaseUser.getUid();
                db = uqac.natacha.food_calendar.Database.DatabaseManager.getInstance();
                positionShoppingList = getIntent().getIntExtra("position", 1);

                db.getUser(currentFirebaseUserID, new uqac.natacha.food_calendar.Database.DatabaseManager.Result<User>() {
                    @Override
                    public void onSuccess(User user) {
                        int position = getIntent().getIntExtra("position", 1);

                        if (!user.getListOfShoppingList().get(position).getArticles().isEmpty() ){

                            if ( !TextUtils.isEmpty(editText_stuff.getText().toString().trim()) && !TextUtils.isEmpty(editText_quantity.getText().toString().trim()))
                            {

                                user.getListOfShoppingList().get(position).getArticles().add(
                                        new AlimentQuantifie(
                                                new Aliment(
                                                        editText_stuff.getText().toString()
                                                        , 1
                                                        , "unité")
                                                ,Integer.parseInt(editText_quantity.getText().toString() )));

                                db.setUser(user);

                                updateView();

                            }

                            else
                            {
                               Toast.makeText(StuffList.this, "Please fill in both fields", Toast.LENGTH_LONG);
                            }


                        }
                    }
                });
            }


            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser currentFirebaseUser = auth.getCurrentUser() ;
            String currentFirebaseUserID = currentFirebaseUser.getUid();

            uqac.natacha.food_calendar.Database.DatabaseManager  db = uqac.natacha.food_calendar.Database.DatabaseManager.getInstance();


        });


    }

    public void updateView(){

        auth = FirebaseAuth.getInstance();
        FirebaseUser currentFirebaseUser = auth.getCurrentUser() ;
        String currentFirebaseUserID = currentFirebaseUser.getUid();
        db = uqac.natacha.food_calendar.Database.DatabaseManager.getInstance();
        db.getUser(currentFirebaseUserID, new uqac.natacha.food_calendar.Database.DatabaseManager.Result<User>() {
            @Override
            public void onSuccess(User user) {

                ArrayList<AlimentQuantifie> ARcourante = new ArrayList<>();
                ARcourante = user.getListOfShoppingList().get(positionDansLaListe).getArticles();
                CustomListAdapter customAdapter = new CustomListAdapter(ARcourante);
                itemListView = (ListView) findViewById(R.id.item_list_view);
                itemListView.setAdapter(customAdapter);
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        menuInner =menu;

        View view = v;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_option_shopping_list, menuInner);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
       index = info.position;

        switch (item.getItemId()){
            case R.id.item_delete:

                auth = FirebaseAuth.getInstance();
                FirebaseUser currentFirebaseUser = auth.getCurrentUser() ;
                String currentFirebaseUserID = currentFirebaseUser.getUid();
                db = uqac.natacha.food_calendar.Database.DatabaseManager.getInstance();


                db.getUser(currentFirebaseUserID, new uqac.natacha.food_calendar.Database.DatabaseManager.Result<User>() {
                    @Override
                    public void onSuccess(User user) {
                        user.getListOfShoppingList().get(positionDansLaListe).getArticles().remove(index);
                        db.setUser(user);

                        updateView();
                    }
                });

                break;
        }
        return super.onContextItemSelected(item);
    }



    public  class CustomListAdapter extends BaseAdapter{

        private ArrayList<AlimentQuantifie> alimentQuantifieArrayList;


        public CustomListAdapter(ArrayList<AlimentQuantifie> alimentQuantifieArrayList){
            this.alimentQuantifieArrayList=alimentQuantifieArrayList;
        }
        @Override
        public int getCount() {
            return alimentQuantifieArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return alimentQuantifieArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.custom_list_stuff,null);
            }

            AlimentQuantifie currentAlimentQuantifie = (AlimentQuantifie) getItem(position);

            imageViewItem = (ImageView ) convertView.findViewById(R.id.imageViewItem);
            textViewNameItem = (TextView) convertView.findViewById(R.id.textView_name);
            textViewQuantityItem = (TextView) convertView.findViewById(R.id.textView_quantity);
            checkBoxOwnItem = (CheckBox) convertView.findViewById(R.id.checkBox_posseder);

            textViewNameItem.setText(currentAlimentQuantifie.getString());
            textViewQuantityItem.setText(String.valueOf(currentAlimentQuantifie.getQuantite()));


            return convertView;
        }
    }

}
