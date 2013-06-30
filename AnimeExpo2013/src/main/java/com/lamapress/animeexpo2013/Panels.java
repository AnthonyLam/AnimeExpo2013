package com.lamapress.animeexpo2013;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

// Sample implementation of this class,
//      obtains the 2nd element of the list of panels and outputs the current title.
//          listPanel.get(1).title;
public class Panels implements Comparable<Panels>{
    public static final String DATE_FORMAT = "h:mm aa";

    public int day;
    public String title;
    public int hourBegin;
    public int minuteBegin;
    public int hourEnd;
    public int minuteEnd;
    public String location;

    Calendar begin;
    Calendar end;

    public Panels(){
        super();
        begin = Calendar.getInstance();
        end = Calendar.getInstance();
    }

    public List<Panels> panel (Context context,String fileName, int day)
        throws XmlPullParserException,IOException {


        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);

        XmlPullParser parsing = factory.newPullParser();

        String text = "";
        List<Panels> panelEvents = new ArrayList<Panels>();
        Panels panelEvent = null;

        InputStream fileLocation = context.getAssets()
                .open("XML/Day" + Integer.toString(day) + "/" + fileName + ".xml");
        parsing.setInput(fileLocation,"utf-8");

        int eventType = parsing.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT){

            String tagname = parsing.getName();

            switch(eventType){
                case XmlPullParser.START_TAG: {
                    if(tagname.equalsIgnoreCase("panel")){
                        panelEvent = new Panels(); // Create new object of Panels
                    }

                    break;
                }

                case XmlPullParser.TEXT: {
                    text = parsing.getText(); // Get the current value
                    break;

                }

                case XmlPullParser.END_TAG: {
                    if(tagname.equalsIgnoreCase("panel")){
                        panelEvents.add(panelEvent);
                    }
                    else if(tagname.equalsIgnoreCase("day")){
                        panelEvent.day =Integer.parseInt(text);
                    }
                    else if(tagname.equalsIgnoreCase("title")){
                        panelEvent.title = text;
                    }
                    else if(tagname.equalsIgnoreCase("hourbegin")){
                        panelEvent.hourBegin = Integer.parseInt(text);
                    }
                    else if(tagname.equalsIgnoreCase("minutebegin")){
                        panelEvent.minuteBegin = Integer.parseInt(text);
                    }
                    else if(tagname.equalsIgnoreCase("hourend")){
                        panelEvent.hourEnd = Integer.parseInt(text);

                    }
                    else if(tagname.equalsIgnoreCase("minuteend")){
                        panelEvent.minuteEnd = Integer.parseInt(text);
                    }
                    else if(tagname.equalsIgnoreCase("location")){
                        panelEvent.location = text;

                        panelEvent.begin.set(2013,Calendar.JULY,3+panelEvent.day,panelEvent.hourBegin,panelEvent.minuteBegin,0);
                        panelEvent.end.set(2013,Calendar.JULY,3+panelEvent.day,panelEvent.hourEnd,panelEvent.minuteEnd,0);
                    }

                    break;


                }

                default:{
                    break;
                }
            }
            eventType = parsing.next(); // Iterate to next item in the list
        }



        return panelEvents;
    }

    public int compareTo(Panels compareOne){

        if(getBegin() == null || compareOne.getBegin() == null){
            return 0;
        }
        Calendar compare = ((Panels)compareOne).getBegin();
        return getBegin().compareTo(compare);
    }

    public String formatTime(Calendar time){
        if(time == null){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        return sdf.format(time.getTime());
    }

    public Calendar getBegin(){
        return begin;
    }

    public Calendar getEnd(){
        return end;
    }

    //TODO: Implement a to string with the parser NEEDS A PARSE FORMAT
    public Calendar fromString(String time){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("");
        return cal;
    }
}
