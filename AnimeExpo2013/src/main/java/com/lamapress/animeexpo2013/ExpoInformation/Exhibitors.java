package com.lamapress.animeexpo2013.ExpoInformation;

import android.content.res.Resources;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lamapress.animeexpo2013.R;

public class Exhibitors extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exhibitor_view);

        Resources res = getResources();
        String[] exhibitors = res.getStringArray(R.array.exhibitors_array);

        ListView exhibitorsList  = (ListView)findViewById(R.id.exhibitor_text);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,exhibitors);
        exhibitorsList.setAdapter(adapter);
    }

}
