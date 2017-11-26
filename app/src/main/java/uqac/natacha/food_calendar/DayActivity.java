package uqac.natacha.food_calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DayActivity extends AppCompatActivity {

    private TextView thedate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        thedate = (TextView) findViewById(R.id.date);

        Intent incoming = getIntent();
        String date = incoming.getStringExtra("date");
        thedate.setText(date);
    }
}
