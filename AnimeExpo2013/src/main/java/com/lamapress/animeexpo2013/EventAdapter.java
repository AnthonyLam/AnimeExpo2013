package com.lamapress.animeexpo2013;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Anthony on 6/19/13.
 */
public class EventAdapter extends ArrayAdapter<Expo_Events> {
    Context context;
    int layoutResourceId;
    Expo_Events data[] = null;

    public EventAdapter(Context context, int layoutResourceId, Expo_Events[] data){
       super(context,layoutResourceId,data);
       this.layoutResourceId = layoutResourceId;
       this.context = context;
       this.data = data;

    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        View row = convertView;
        EventHolder holder = null;
        final int pos = position;

        if(row == null){

            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId,parent,false);

            holder = new EventHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.event_image);
            holder.txtTitle = (TextView)row.findViewById(R.id.event_type_panel);

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,EventPop.class);
                    intent.putExtra("Position",pos);
                    context.startActivity(intent);
                }
            });

            row.setTag(holder);
        }
        else{
            holder = (EventHolder)row.getTag();
        }

        Expo_Events events = data[position];
        holder.txtTitle.setText(events.event_type);
        holder.imgIcon.setImageResource(events.icon);

       return row;
    }

    static class EventHolder{
        ImageView imgIcon;
        TextView txtTitle;
    }
}


