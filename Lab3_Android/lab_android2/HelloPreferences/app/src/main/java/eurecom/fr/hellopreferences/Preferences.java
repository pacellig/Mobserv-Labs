package eurecom.fr.hellopreferences;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by pacellig on 22/11/2015.
 */
public class Preferences extends PreferenceActivity {

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

}
