package com.lamapress.animeexpo2013;

import android.content.res.AssetManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anthony on 6/21/13.
 */
// Sample implementation of this class,
//      obtains the 2nd element of the list of panels and outputs the current title.
// listPanel.get(1).title;
public class Panels {

    public int day;
    public String title;
    public int hourBegin;
    public int minuteBegin;
    public int hourEnd;
    public int minuteEnd;
    public String location;

    public String timeBegin;
    public String timeEnd;

    public Panels(){
        super();
    }

    public List<Panels> panel (InputStream fileName)
        throws XmlPullParserException,IOException {

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);

        XmlPullParser parsing = factory.newPullParser();

        String text = "";
        List<Panels> panelEvents = new ArrayList<Panels>();
        Panels panelEvent = null;

        parsing.setInput(fileName,"utf-8");

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
                    else if(tagname.equalsIgnoreCase("minutened")){
                        panelEvent.minuteEnd = Integer.parseInt(text);
                    }
                    else if(tagname.equalsIgnoreCase("location")){
                        panelEvent.location = text;

                        // Generate time string for output to USERS
                        if(hourBegin > 12){

                            panelEvent.timeBegin = (panelEvent.hourBegin - 12) +
                                    ":" + panelEvent.minuteBegin + " PM";
                        }
                        else{
                            panelEvent.timeBegin = (panelEvent.hourBegin) +
                                    ":" + panelEvent.minuteBegin + " AM";
                        }

                        if(hourEnd > 12){
                            panelEvent.timeEnd = (panelEvent.hourEnd -12) +
                                    ":" + panelEvent.minuteEnd + " PM";
                        }
                        else{
                            panelEvent.timeEnd = (panelEvent.hourEnd) +
                                    ":" + panelEvent.minuteEnd + " AM";
                        }
                    }

                    break;

                }

                default:{
                    break;
                }
            }
            eventType = parsing.next(); // Prevents infinite loops
        }



        return panelEvents;
    }
}
