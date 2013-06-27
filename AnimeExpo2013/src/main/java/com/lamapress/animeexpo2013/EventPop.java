package com.lamapress.animeexpo2013;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class EventPop extends Activity {

    List<Panels> listPanel;

    public  EventPop(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_event);

        Intent intent = getIntent();
        int location = intent.getIntExtra("Position", 0);
        String file = "";
        String actionBarTitle = "";
        Panels panHandle = new Panels();

        ListView Listview;

        switch(location){
            // Panels
            case 0:{
                file = "panels.xml";
                actionBarTitle = "Panels";
                break;

            }
            // Guest of honor
            case 1:{
                actionBarTitle = "Guests Of Honor";
                file = "guest_of_honor.xml";
                break;
            }
            // Films
            case 2:{
                actionBarTitle = "Films/Screenings";
                file = "films.xml";
                break;
            }
            // Ticketed Event
            case 3:{
                file = "ticketed_event.xml";
                break;
            }
            // Workshop
            case 4:{
                file = "workshop.xml";
                break;
            }
            // Premiere
            case 5:{
                file = "premiere.xml";
                break;
            }
            // Non-Ticketed Event
            case 6:{
                file = "non_ticketed_event.xml";
                break;

            }
            // Mature Content 18+ Only
            case 7:{
                file = "mature_content.xml";
                break;
            }
            case 8:
            {
                file = "miscellaneous.xml";
                break;
            }
            default:{

                break;
            }
        }

        try{
            listPanel = panHandle.panel(getAssets().open(file));
        }
        catch(XmlPullParserException e){
            e.printStackTrace();
            listPanel = null;
        }
        catch(IOException e){
            e.printStackTrace();
            listPanel = null;
        }

        Collections.sort(listPanel);
        actionBarSetup(actionBarTitle);
        PanelAdapter adapter = new PanelAdapter(this,R.layout.panel_list_row,listPanel);
        Listview = (ListView)findViewById(R.id.popup_events);

        registerForContextMenu(Listview);

        Listview.setAdapter(adapter);

    }

    private void actionBarSetup(String title) {
        ActionBar ab = getActionBar();
        ab.setTitle(title);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event_pop, menu);
        return true;
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);

        Boolean enabled;
        SqlMaker sql = new SqlMaker(this);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.event_pop_context,menu);

        enabled = sql.findContent("title",listPanel.get(info.position).title);

        MenuItem myMenuItem = menu.findItem(R.id.remove_from_schedule);
        myMenuItem.setVisible(enabled);
        myMenuItem = menu.findItem(R.id.add_to_schedule);
        myMenuItem.setVisible(!enabled);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch(item.getItemId()){
            case R.id.add_to_schedule:
                AddToSchedule(info.position);
                return true;
            case R.id.view_on_map:
                return true;
            case R.id.remove_from_schedule:
                RemoveFromSchedule(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void AddToSchedule(int position){
        SqlMaker sql = new SqlMaker(this);
        sql.addContent(listPanel.get(position));
    }

    public void RemoveFromSchedule(int position){
        SqlMaker sql = new SqlMaker(this);
        sql.removeContent(listPanel.get(position).title,"title");

    }
}
