package uqac.natacha.food_calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import uqac.natacha.food_calendar.Modele.ShoppingList;

public class CalendarActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnDateSelectedListener {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();

    @BindView(R.id.calendarView) MaterialCalendarView materialCalendarView;

    @BindView(R.id.toolbar) Toolbar toolbar;

    @BindView(R.id.drawer_layout) DrawerLayout drawer;

    @BindView(R.id.nav_view) NavigationView navigationView;

    private  static final String TAG = "CalendarActivity";

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        materialCalendarView.setOnDateChangedListener(this);

    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @Nullable CalendarDay date, boolean selected) {
        String datesString = getSelectedDatesString();
        Log.d(TAG, "onSelectedDayChange: yyyy/mm/dd:" + datesString);
        Intent intent = new Intent(CalendarActivity.this, DayActivity.class);
        intent.putExtra("date",datesString);
        startActivity(intent);

    }

    private String getSelectedDatesString() {
        CalendarDay date = materialCalendarView.getSelectedDate();
        if (date == null) {
            return "No Selection";
        }
        return FORMATTER.format(date.getDate());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_stat) {
            Intent intent = new Intent(CalendarActivity.this, StatActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_recipe) {
            Intent intent = new Intent(CalendarActivity.this, RecipeCategories.class);
            startActivity(intent);

        } else if (id == R.id.nav_shopping) {
            Intent intent = new Intent(CalendarActivity.this, ShoppingList.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(CalendarActivity.this, RegisterActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_disconnection) {
            auth.getInstance().signOut();
            Toast.makeText(this,"Déconnexion", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CalendarActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
