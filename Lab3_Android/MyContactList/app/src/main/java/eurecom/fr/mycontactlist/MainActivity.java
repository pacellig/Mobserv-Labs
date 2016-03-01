package eurecom.fr.mycontactlist;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Contact>>, AdapterView.OnItemClickListener {
    private ListView listView;
    private List<Contact> contacts;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //return netInfo != null && netInfo.isConnectedOrConnecting();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("link", "http://1-dot-lab1mobserv.appspot.com/contactlist?respType=json");
        edit.commit(); // Apply changes
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView1);
        listView.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        // return true;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i("main", "onResume");
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<List<Contact>> onCreateLoader(int id, Bundle args) {

        Log.i("main", "creating loader");
        JsonLoader loader = new JsonLoader(this);
        loader.forceLoad();
        return loader;

    }

    @Override
    public void onLoadFinished(Loader<List<Contact>> arg0, List<Contact> arg1){

        contacts = arg1;
        ContactAdapter Ca = new ContactAdapter(this,R.layout.contact, contacts);
        listView.setAdapter(Ca); //new ContactAdapter(this, R.layout.contact, contacts));
        //Any additional code you might already have added for previous tasks    

    }

    @Override
    public void onLoaderReset(Loader<List<Contact>> arg0){

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_add:
                openAddContact();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        /*
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        //return super.onOptionsItemSelected(item);
    }
   @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       Intent i = new Intent(this, ModifyContactActivity.class);
       i.putExtra("contact", contacts.get(position));
       startActivity(i);
        Log.i("Main:", "someone clicked on item: " + position);

        //Toast.makeText(this, "You picked "+position, Toast.LENGTH_LONG).show();
    }

    public void openAddContact(){
        Intent i = new Intent(this, addContactActivity.class);
        startActivity(i);
    }

}
