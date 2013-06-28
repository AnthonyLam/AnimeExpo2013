package com.lamapress.animeexpo2013;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class HoursView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hours_view_activity);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hours_view, menu);
        return true;
    }
    
}
