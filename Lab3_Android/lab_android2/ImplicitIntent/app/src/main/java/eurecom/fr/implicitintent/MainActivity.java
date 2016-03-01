package eurecom.fr.implicitintent;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.security.Permission;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
   private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
   Uri imageURI = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "hello.jpg"));

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
   }

   public void callIntent (View view){
      Intent intent = null;
      switch (view.getId()) {

         case R.id.btnCallBrowser:
            EditText textBrow = (EditText) findViewById(R.id.edit_url);
            String url = textBrow.getText().toString();
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + url));
            startActivity(intent);
            break;

         case R.id.btnCallSomeone:
            String numToCall = ((EditText) findViewById(R.id.edit_call)).getText().toString();
            intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+numToCall));
            startActivity(intent);
            break;

         case R.id.btnDial:
            intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"));
            startActivity(intent);
            break;

         case R.id.btnEditFirstContact:
            intent = new Intent(Intent.ACTION_EDIT, Uri.parse("content://contacts/people/1"));
            startActivity(intent);
            break;

         case R.id.btnPicture:
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

            break;

         case R.id.btnSearchonmap:
            EditText etmaps = (EditText) findViewById(R.id.edit_maps);
            String q = "geo:0,0?q="+etmaps.getText().toString();
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(q));
            startActivity(intent);
            break;

         case R.id.btnShowContact:
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("content://contacts/people/1"));
            startActivity(intent);
            break;

         case R.id.btnShowMap:
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("geo:0,0?q=eurecom"));
            startActivity(intent);
            break;

         case R.id.btnAddCalendarEvent:
            /*intent = new Intent(Intent.ACTION_INSERT);
            intent.setData(CalendarContract.Events.CONTENT_URI);
            Intent chooser = Intent.createChooser(intent, "Which calendar app?");
            if (intent.resolveActivity(getPackageManager()) != null) {
               startActivity(chooser);
            }*/
            //startActivity(intent);intent = new Intent(Intent.ACTION_INSERT);
            startActivity(new Intent(this, CalendarActivity.class));
            break;
      }

   }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
         if (resultCode == RESULT_OK) {
            // Image captured and saved to fileUri specified in the Intent
            try {
               Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imageURI);
               if ( bitmap == null) {
                  Log.d("CAMERA", "camera retrieving image failed");
               } else {
                  Log.d("CAMERA","Saved");
                  Toast.makeText(this, "Image saved to:\n" + imageURI, Toast.LENGTH_LONG).show();
               }
            } catch (Exception ex){}

         } else if (resultCode == RESULT_CANCELED) {
            // User cancelled the image capture
            Toast.makeText(this, "Image canceled", Toast.LENGTH_LONG).show();
         } else {
            // Image capture failed, advise user
            Toast.makeText(this, "Error: Image not taken", Toast.LENGTH_LONG).show();
         }
      }

   }

}
