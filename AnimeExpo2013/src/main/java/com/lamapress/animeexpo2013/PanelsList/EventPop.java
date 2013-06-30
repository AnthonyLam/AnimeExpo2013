package com.lamapress.animeexpo2013.PanelsList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lamapress.animeexpo2013.MapFragments.ExpoMapFragment;
import com.lamapress.animeexpo2013.PanelAdapter;
import com.lamapress.animeexpo2013.Panels;
import com.lamapress.animeexpo2013.R;
import com.lamapress.animeexpo2013.SqlMaker;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class EventPop extends Activity implements
            ActionBar.OnNavigationListener{

    private final String[] actions = new String[] {"All","Day 1", "Day 2", "Day 3", "Day 4"};
    List<Panels> listPanel;
    private Panels panHandle = new Panels();
    private String file = "";
    private PanelAdapter adapter;
    /** Create an array adapter to populate dropdownlist */
    public  EventPop(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_event);

        Intent intent = getIntent();
        int location = intent.getIntExtra("Position", 0);
        String actionBarTitle = "";
        final ActionBar actionBar = getActionBar();

        // ActionBar spinner setup
        ArrayAdapter<String> dropdowndapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, actions);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(dropdowndapter,this);


        ListView Listview;

        switch(location){
            // Panels
            case 0:{
                file = "panels";
                actionBarTitle = "Panels";
                break;

            }
            // Guest of honor
            case 1:{
                actionBarTitle = "Guests Of Honor";
                file = "guest_of_honor";
                break;
            }
            // Films
            case 2:{
                actionBarTitle = "Films/Screenings";
                file = "films";
                break;
            }
            // Ticketed Event
            case 3:{
                file = "ticketed_event";
                break;
            }
            // Workshop
            case 4:{
                file = "workshop";
                break;
            }
            // Non-Ticketed Event
            case 5:{
                file = "non_ticketed_event";
                break;

            }
            // Mature Content 18+ Only
            case 6:{
                file = "mature_content";
                break;
            }
            case 7:{
                file="AMV";
                break;
            }
            case 8:
            {
                file = "miscellaneous";
                break;
            }
            default:{

                break;
            }
        }

        try{
                    listPanel = panHandle.panel(this,file,1);
                    listPanel.addAll(panHandle.panel(this,file,2));
                    listPanel.addAll(panHandle.panel(this,file,3));
                    listPanel.addAll(panHandle.panel(this,file,4));
                    Collections.sort(listPanel);

        }
        catch(XmlPullParserException e){
            e.printStackTrace();
            listPanel = null;
        }
        catch(IOException e){
            e.printStackTrace();
            listPanel = null;
        }

        actionBarSetup(actionBarTitle);
        adapter = new PanelAdapter(this,R.layout.panel_list_row,listPanel);
        Listview = (ListView)findViewById(R.id.popup_events);

        registerForContextMenu(Listview);

        Listview.setAdapter(adapter);

    }

    private void actionBarSetup(String title) {
        ActionBar ab = getActionBar();
        ab.setTitle(title);
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.event_pop, menu);
//        return true;
//    }

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
                Intent intent = new Intent(this,ExpoMapFragment.class);
                intent.putExtra("location",listPanel.get(info.position).location);
                this.startActivity(intent);
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
        sql.removeContent("title",listPanel.get(position).title);
    }

    @Override
    public boolean onNavigationItemSelected(int position,long id){
        listPanel = null;
        try{
            if(position != 0){
                listPanel = (panHandle.panel(this,file,position));
                adapter.clear();
                adapter.addAll(listPanel);
                adapter.notifyDataSetChanged();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(XmlPullParserException e){
            e.printStackTrace();
        }
        return true;
    }
}
