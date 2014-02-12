package com.mycompany.myapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ExpoHours extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expo_hours_list);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.expo_hours, menu);
        return true;
    }
    
}
