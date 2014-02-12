package com.lamapress.animeexpo2013;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PanelAdapter extends ArrayAdapter<Panels> {
    Context context;
    int layoutResourceId;
    List<Panels> data = null;
    private static final int[] COLORS = new int[] {
            android.R.color.holo_green_light, android.R.color.holo_orange_light,
            android.R.color.holo_blue_light, android.R.color.holo_red_light };

    public PanelAdapter(Context context, int layoutResourceId, List<Panels> data){
        super(context,layoutResourceId,data);
        this.layoutResourceId =layoutResourceId;
        this.context = context;
        this.data = data;

    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        View row = convertView;
        EventHolder holder = null;



        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId,parent,false);

            holder = new EventHolder();

            //TODO: Make thread safe
            holder.panel_location = (TextView)row.findViewById(R.id.panel_location);
            holder.panel_time = (TextView)row.findViewById(R.id.panel_time);
            holder.panel_title = (TextView)row.findViewById(R.id.panel_title);

            row.setTag(holder);
        }
        else {
            holder = (EventHolder)row.getTag();
        }

        Panels panel = data.get(position);
        holder.panel_location.setText(panel.location);
        holder.panel_title.setText(panel.title);
        holder.panel_time.setText("D" + panel.day + " "+ panel.formatTime(panel.begin) + " - "
                +panel.formatTime(panel.end));

        return row;

    }

    static class EventHolder{
        TextView panel_location;
        TextView panel_time;
        TextView panel_title;
    }


}
