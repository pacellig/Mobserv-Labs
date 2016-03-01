package eurecom.fr.mycontactlist;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pacellig on 17/11/2015.
 */
public class JsonLoader extends AsyncTaskLoader<List<Contact>> {
    public  JsonLoader(Context context){
        super(context);
        Log.i("main","loader created");
    }

    @Override
    public List<Contact> loadInBackground() {
        Log.i("main", "loader in Background");
        URL page = null;
        try {
            SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(getContext());
            //page = new URL("http://1-dot-lab1mobserv.appspot.com/contactlist?respType=json");
            page = new URL(p.getString("link", "n/a"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        StringBuffer text = new StringBuffer();
        HttpURLConnection conn = null;
        try {
            Log.i("main","before coon");
            conn = (HttpURLConnection) page.openConnection();
            Log.i("main","after conn");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Log.i("main", "before connection");
            conn.connect();
            Log.i("main", "after connection");
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStreamReader in = null;
        String jsonString=null;
        try {
            in = new InputStreamReader((InputStream) conn.getContent(),"UTF-8");
            Reader reader = in;
            jsonString = readAll(in);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (jsonString!=null){
            List<Contact> result;
            try {
                JSONArray listOfContact = new JSONArray(jsonString);
                int len = listOfContact.length();
                result = new ArrayList<Contact>(len);
                Log.i("main", "json string length is " + len);
                for(int i=0;i<len;i++) {
                    Bitmap im = null;
                    im = loadImage(listOfContact.getJSONObject(i).getString("pict"));
                    result.add(new Contact(listOfContact.getJSONObject(i), im));
                }
            }catch (JSONException e){
                //TODO handle

                e.printStackTrace();
                return null;
            }
            return result;

        }else {


            return null;
        }
    }
    private Bitmap loadImage(String link){
        Bitmap bmp = null;
        try{
            URL urln = new URL(link);
            HttpURLConnection con = (HttpURLConnection)urln.openConnection();
            InputStream is = con.getInputStream();
            bmp = BitmapFactory.decodeStream(is);
            if(bmp != null)
                return bmp;
            Log.i("IMAGELOADING", "image loading #failed");
        } catch (Exception e){}
        return bmp;
    }

    private String readAll(Reader reader) throws IOException {
        StringBuilder builder = new StringBuilder(4096);
        for (CharBuffer buf = CharBuffer.allocate(512); (reader.read(buf)) > -1; buf
                .clear()) {
            builder.append(buf.flip());
        }
        return builder.toString();
    }

}
