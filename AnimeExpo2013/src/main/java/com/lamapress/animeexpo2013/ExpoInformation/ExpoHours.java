package com.lamapress.animeexpo2013.ExpoInformation;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lamapress.animeexpo2013.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExpoHours extends Activity implements
            ActionBar.OnNavigationListener{

    private final String[] actions = new String[] {"Day 0","Day 1","Day 2","Day 3","Day 4"};
    private Hours hourHolder = new Hours();
    private HourAdapter adapter;
    List<Hours> hourList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expo_hours);

        final ActionBar actionBar = getActionBar();
        ArrayAdapter<String> dropdownadapter = new ArrayAdapter<String>(this
                 ,android.R.layout.simple_spinner_dropdown_item,actions);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(dropdownadapter,this);


        try{
            hourList = hourHolder.parser(this,0);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(XmlPullParserException e){
            e.printStackTrace();
        }

        adapter = new HourAdapter(this,R.layout.panel_list_row,hourList);
        ListView listview = (ListView)findViewById(R.id.expo_hour_list);
        listview.setAdapter(adapter);
    }


    public class HourAdapter extends ArrayAdapter<Hours>{
        Context context;
        int layoutResourceId;
        List<Hours> hours = null;

        public HourAdapter(Context context, int layoutResourceId, List<Hours> data){
            super(context,layoutResourceId,data);
            this.context = context;
            this.layoutResourceId = layoutResourceId;
            this.hours = data;
        }

        @Override
        public View getView(int position,View convertView,ViewGroup parent){
            View row = convertView;
            HourHolder holder = null;

            if(row == null){
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId,parent,false);

                holder = new HourHolder();

                holder.title = (TextView)row.findViewById(R.id.panel_title);
                holder.date = (TextView)row.findViewById(R.id.panel_location);
                holder.time = (TextView)row.findViewById(R.id.panel_time);
                row.setTag(holder);
            }
            else{
                holder = (HourHolder)row.getTag();
            }

            Hours hour = hours.get(position);
            holder.title.setText(hour.title);
            holder.date.setText(hour.dateLocation);
            holder.time.setText("");

            return row;
        }


    }

    public class Hours{
        String title;
        String dateLocation;

        public List<Hours> parser (Context context, int day)
                throws XmlPullParserException,IOException {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            //Declarations
            XmlPullParser parsing = factory.newPullParser();
            List<Hours> hourList = new ArrayList<Hours>();
            String text = "";
            Hours hour = null;

            InputStream fileLocation = context.getAssets()
                    .open("hours/day" + Integer.toString(day) + ".xml");
            parsing.setInput(fileLocation,"utf-8");

            int eventType = parsing.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                String tagname = parsing.getName();

                switch(eventType){
                    case XmlPullParser.START_TAG:{
                        if(tagname.equalsIgnoreCase("li")){
                            hour = new Hours();
                        }
                        break;
                    }

                    case XmlPullParser.TEXT:{
                        text = parsing.getText();
                        break;
                    }

                    case XmlPullParser.END_TAG:{
                        if(tagname.equalsIgnoreCase("li")){
                            hourList.add(hour);
                        }
                        else if(tagname.equalsIgnoreCase("h2")){
                            hour.title = text;
                        }
                        else if(tagname.equalsIgnoreCase("p")){
                            hour.dateLocation = text;
                        }
                        break;
                    }
                    default:{
                        break;
                    }
                }
                eventType = parsing.next();
            }
            return hourList;
        }
    }

    public class HourHolder{
        TextView title;
        TextView date;
        TextView time;
    }

    @Override
    public boolean onNavigationItemSelected(int position,long id){
        hourList = null;
        try{
            hourList = hourHolder.parser(this,position);
            adapter.clear();
            adapter.addAll(hourList);
            adapter.notifyDataSetChanged();
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
