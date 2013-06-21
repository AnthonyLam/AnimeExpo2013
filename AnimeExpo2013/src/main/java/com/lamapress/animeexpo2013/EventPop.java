package com.lamapress.animeexpo2013;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class EventPop extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_event);

        Intent intent = getIntent();
        int location = intent.getIntExtra("Position",0);

        TextView text = (TextView)findViewById(R.id.testID);
        switch(location){
            // Panels
            case 0:{
                text.setText("event1");
                break;

            }
            // Guest of honor
            case 1:{

                text.setText("Event2");
                break;
            }
            // Films
            case 2:{

                text.setText("Event3");
                break;
            }
            // Ticketed Event
            case 3:{
                break;
            }
            // Workshop
            case 4:{
                break;
            }
            // Premiere
            case 5:{
                break;
            }
            // Non-Ticketed Event
            case 6:{

            }
            // Mature Content 18+ Only
            case 7:{
                break;
            }
            default:{

                text.setText(location + "");
                break;
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event_pop, menu);
        return true;
    }
    
}
