package com.lamapress.animeexpo2013.MapFragments;


import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MarkerHolder {
    double geoLat;
    double geoLong;
    String title;
    String text;

    public MarkerHolder(){
    }

    public List<Marker> loadMarker(InputStream xmlFile,GoogleMap map) throws XmlPullParserException,IOException {
        List<Marker> markers = new ArrayList<Marker>();
        Marker holder;

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser parsing = factory.newPullParser();
        parsing.setInput(xmlFile,"utf-8");

        int eventType = parsing.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT){
            String tag = parsing.getName();

            switch(eventType){
                case XmlPullParser.START_TAG:{
                    if(tag.equalsIgnoreCase("marker")){
                    }
                    break;
                }
                case XmlPullParser.TEXT:{
                    text = parsing.getText();
                    break;
                }

                case XmlPullParser.END_TAG:{
                    if(tag.equalsIgnoreCase("marker")){
                         holder = map.addMarker(new MarkerOptions()
                                .position(new LatLng(geoLat,geoLong))
                                .title(title));
                        markers.add(holder);
                    }
                    else if(tag.equalsIgnoreCase("latitude")){
                        geoLat = Double.parseDouble(text);
                    }
                    else if(tag.equalsIgnoreCase("longitude")){
                        geoLong = Double.parseDouble(text);
                    }
                    else if(tag.equalsIgnoreCase("text")){
                        title = text;
                    }

                    break;

                }
                default:{
                    break;
                }
            }
            eventType = parsing.next();
        }

        return markers;
    }
}