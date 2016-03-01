package eurecom.fr.mycontactlist;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by pacellig on 17/11/2015.
 */
public class ResponseHandler {

    private final String url = "http://1-dot-lab1mobserv.appspot.com/";

    private Context context;
    private String request;
    private String format;
    private Handler handler;
    private Activity activity;
    private Map<String,String> data;
    private int statusCode;

    public Activity getActivity() {
        return activity;
    }

    public ResponseHandler(Context context, String request, String format, Handler handler, Activity activity, Map<String,String> data)
    {
        this.context = context;
        this.request = url + request;
        this.format = format;
        this.handler = handler;
        this.activity = activity;
        this.data = data;
    }

    public void finish()
    {
        handler.post(new Runnable() {
            public void run() {
                activity.finish();
            }
        });
    }

    private void popUp(String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public String start()
    {
        String out = null;
        if(format.compareTo("POST") == 0)
            try {
                out = postData();
                Log.i("Response==", out);
                statusCode = 1;
            } catch (IOException e) {
                e.printStackTrace();
                statusCode = 0;
            }
        else{
            try {
                deleteData();
                statusCode = 1;
            }catch (Exception e){
                statusCode = 0;

            }
        }
        return out;
    }

    // A revoir
    public void respond(String response)
    {
        /* To be implemented
        * If the statusCode is ok?
        * Notify the user about the successful operation
        * If not simply state that a problem has occurred
        * Remember to conclude the activity
        * */
        if ( statusCode == 0 ) {
            // failed
            Toast.makeText(context, "Request Failed", Toast.LENGTH_LONG).show();
        } else if ( statusCode == 1 ) {
            // success
            Toast.makeText(context, "Request Succedeed", Toast.LENGTH_LONG).show();
        }

        finish(); // terminate the activity
    }

    private String convertParameters(Map <String,String> data){
        String result = "";
        for (Map.Entry<String,String> e: data.entrySet()){
            result += e.getKey()+"="+e.getValue()+"&";
        }
        return result.substring(0,result.length()-1);
    }

    public String postData() throws IOException {
        URL urlToRequest = new URL(request);
        HttpURLConnection urlConnection = null;
        String postParameters = convertParameters(data);
        Log.d(ResponseHandler.class.toString(), postParameters);
        urlConnection = (HttpURLConnection) urlToRequest.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        urlConnection.setFixedLengthStreamingMode(postParameters.getBytes().length);
        PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
        out.print(postParameters);
        out.close();
        return urlConnection.getResponseMessage();
    }

    private void deleteData() throws MalformedURLException, IOException {
        Log.i("Request:", request);
        URL page = new URL(request);
        HttpURLConnection conn = null;
        conn = (HttpURLConnection) page.openConnection();
        conn.connect();
        InputStreamReader in = null;
        String message = conn.getResponseMessage();
        return;
    }
}
