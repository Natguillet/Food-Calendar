package uqac.natacha.food_calendar;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
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
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import uqac.natacha.food_calendar.Modele.Aliment;
import uqac.natacha.food_calendar.Modele.AlimentQuantifie;
import uqac.natacha.food_calendar.Modele.ListeDeCourse;
import uqac.natacha.food_calendar.Modele.ShoppingList;
import uqac.natacha.food_calendar.Modele.Unite;
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


    ListView shoppingList = null;
    private User UtilisateurCourant ;
    private FirebaseAuth auth;
    private uqac.natacha.food_calendar.Database.DatabaseManager db;
    private int positionDansLaListe;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stuff_list);

        itemListView = (ListView) findViewById(R.id.item_list_view);
        positionDansLaListe = getIntent().getIntExtra("position", 1);

        updateView();

        registerForContextMenu(itemListView);

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
                                                        , Unite.UNITE)
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
/*

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(nameStuff.get(info.position));
        getMenuInflater().inflate(R.menu.menu_option_stuff_list, menu);
*/

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;

        switch (item.getItemId()){
            case R.id.item_delete:

                break;

            case R.id.item_moreInfo:
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


   /* class CustomAdapter extends BaseAdapter{


        int count =0;
        AlimentQuantifie alimentQuantifieCourant = null;



        @Override
        public int getCount() {

            auth = FirebaseAuth.getInstance();
            FirebaseUser currentFirebaseUser = auth.getCurrentUser() ;
            String currentFirebaseUserID = currentFirebaseUser.getUid();
            db = uqac.natacha.food_calendar.Database.DatabaseManager.getInstance();
            db.getUser(currentFirebaseUserID, new uqac.natacha.food_calendar.Database.DatabaseManager.Result<User>() {
                @Override
                public void onSuccess(User user) {

                    CustomAdapter.this.count = (user.getListOfShoppingList().get(positionShoppingList).getArticles().size());
                    Log.i("Custom adapter getCount", "size : " + user.getListOfShoppingList().get(positionShoppingList).getArticles().size());
                }
            });

            return  count;

        }

        @Override
        public Object getItem(int i) {

            iGlob = i;

            Log.i("Custom adapter getItem", "iGlob : " + iGlob);




            auth = FirebaseAuth.getInstance();
            FirebaseUser currentFirebaseUser = auth.getCurrentUser() ;
            String currentFirebaseUserID = currentFirebaseUser.getUid();
            db = uqac.natacha.food_calendar.Database.DatabaseManager.getInstance();
            db.getUser(currentFirebaseUserID, new uqac.natacha.food_calendar.Database.DatabaseManager.Result<User>() {
                @Override
                public void onSuccess(User user) {

                   alimentQuantifieCourant = (user.getListOfShoppingList().get(positionShoppingList).getArticles().get(iGlob));
                }
            });

            return alimentQuantifieCourant;
        }

        @Override
        public long getItemId(int i) {
           return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            iGlob =i;
            view = getLayoutInflater().inflate(R.layout.custom_list_stuff,null);

            imageViewItem = (ImageView ) view.findViewById(R.id.imageViewItem);
            textViewNameItem = (TextView) view.findViewById(R.id.textView_name);
            textViewQuantityItem = (TextView) view.findViewById(R.id.textView_quantity);
            checkBoxOwnItem = (CheckBox) view.findViewById(R.id.checkBox_posseder);

            auth = FirebaseAuth.getInstance();
            FirebaseUser currentFirebaseUser = auth.getCurrentUser() ;
            String currentFirebaseUserID = currentFirebaseUser.getUid();
            db = uqac.natacha.food_calendar.Database.DatabaseManager.getInstance();

            getItem(i);
            textViewNameItem.setText(alimentQuantifieCourant.getString());
            getCount();

            textViewQuantityItem.setText(String.valueOf(count));


            *//*db.getUser(currentFirebaseUserID, new uqac.natacha.food_calendar.Database.DatabaseManager.Result<User>() {
                @Override
                public void onSuccess(User user) {
                    int position = getIntent().getIntExtra("position", 1);
                    if (!user.getListOfShoppingList().get(position  ).getArticles().isEmpty() ){

                    textViewNameItem.setText(user.getListOfShoppingList().get(position).getArticles().get(iGlob).getString());
                    textViewQuantityItem.setText(String.valueOf( user.getListOfShoppingList().get(position).getArticles().get(iGlob).getQuantite()));

                    }
                }
            });*//*


            return view;
        }
    }*/



}
