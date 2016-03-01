package eurecom.fr.implicitintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.EditText;

public class CalendarActivity extends Activity {
    EditText etTitle, etDescription, etLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);

        etTitle = (EditText) findViewById(R.id.etTitleCalendar);
        etDescription = (EditText) findViewById(R.id.etDescriptionCalendar);
        etLocation = (EditText) findViewById(R.id.etLocationCalendar);

    }

    public void onClickAddEvent(View view){
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.Events.TITLE, etTitle.getText().toString());
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, etLocation.getText().toString());
        intent.putExtra(CalendarContract.Events.DESCRIPTION, etDescription.getText().toString());

        startActivity(intent);
    }
}
