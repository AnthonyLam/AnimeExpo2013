package com.lamapress.animeexpo2013;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PanelList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panel_list);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.panel_list, menu);
        return true;
    }
    
}
