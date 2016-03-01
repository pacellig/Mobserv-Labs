package eurecom.fr.mycontactlist;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class addContactActivity extends AppCompatActivity {
    private EditText name;
    private EditText email;
    private EditText number;
    private EditText url;
    private Button addB;
    private String defaultS = "https://pixabay.com/static/uploads/photo/2012/04/18/18/16/man-37468_640.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        name = (EditText) findViewById(R.id.nameAdd);
        email = (EditText) findViewById(R.id.emailAdd);
        number = (EditText) findViewById(R.id.numberAdd);
        url = (EditText) findViewById(R.id.urlAdd);
    }

    public void onClickAdd(View view){
        Handler handler= new Handler();

            Log.i("main", "I clicked add");
            Map<String,String> data = new HashMap<String,String>();
            data.put("name", name.getText().toString());
            data.put("email",email.getText().toString());
            data.put("phone",number.getText().toString());
            data.put("pict",defaultS);
            ResponseHandler rh = new ResponseHandler(this,"save?id=", "POST", handler, this, data);
            new ModifyTask().execute(rh);
    }
}
