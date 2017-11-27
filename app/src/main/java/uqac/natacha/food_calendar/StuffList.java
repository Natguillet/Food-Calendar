package uqac.natacha.food_calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;



public class StuffList extends AppCompatActivity {




    ArrayList<String> numberOfThisStuff = new ArrayList<String>();
    ArrayList<String> nameStuff = new ArrayList<String>();



    ListView itemListView;
    ImageView imageViewItem;
    TextView textViewNameItem;
    TextView textViewQuantityItem;
    CheckBox checkBoxOwnItem;
    CustomAdapter customAdapter;
    Button buttonAddStuff;
    EditText editText_stuff;
    EditText editText_quantity;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        numberOfThisStuff.add("1");
        nameStuff.add("chocolat");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stuff_list);

        itemListView = (ListView) findViewById(R.id.item_list_view);
        customAdapter = new CustomAdapter();
        itemListView.setAdapter(customAdapter);
        registerForContextMenu(itemListView);


        buttonAddStuff = (Button) findViewById(R.id.button_addStuff) ;





        buttonAddStuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editText_stuff = findViewById(R.id.editText_stuffAdd);
                editText_quantity = findViewById(R.id.quantityAdd);


                nameStuff.add(editText_stuff.getText().toString());
                numberOfThisStuff.add(editText_quantity.getText().toString());




            }
        });


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {


        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(nameStuff.get(info.position));
        getMenuInflater().inflate(R.menu.menu_option_stuff_list, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {


        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;



        switch (item.getItemId()){
            case R.id.item_delete:

                numberOfThisStuff.remove(index);
                nameStuff.remove(index);
                break;



            case R.id.item_moreInfo:
                break;

        }



        return super.onContextItemSelected(item);
    }

    class CustomAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return nameStuff.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
           view = getLayoutInflater().inflate(R.layout.custom_list_stuff,null);
            imageViewItem = (ImageView ) view.findViewById(R.id.imageViewItem);
            textViewNameItem = (TextView) view.findViewById(R.id.textView_name);
            textViewQuantityItem = (TextView) view.findViewById(R.id.textView_quantity);
            checkBoxOwnItem = (CheckBox) view.findViewById(R.id.checkBox_posseder);

            textViewNameItem.setText(nameStuff.get(i));
            textViewQuantityItem.setText(numberOfThisStuff.get(i));

            return view;
        }
    }



}
