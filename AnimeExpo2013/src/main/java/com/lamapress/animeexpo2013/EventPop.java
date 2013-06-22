package com.lamapress.animeexpo2013;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.content.res.AssetManager;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class EventPop extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_event);

        Intent intent = getIntent();
        int location = intent.getIntExtra("Position",0);
        String file = "";
        List<Panels> listPanel;
        Panels panHandle = new Panels();

        TextView text = (TextView)findViewById(R.id.testID);
        switch(location){
            // Panels
            case 0:{
                file = "panels.xml";
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
                break;

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

        try{
            listPanel = panHandle.panel(getAssets().open(file));
            text.setText(listPanel.get(0).timeBegin);
        }
        catch(XmlPullParserException e){
            e.printStackTrace();
            text.setText("Hi, my creator seems to have done something wrong. He probably won't fix it");
        }
        catch(IOException e){
            e.printStackTrace();
            text.setText("IOException");
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event_pop, menu);
        return true;
    }
    
}
