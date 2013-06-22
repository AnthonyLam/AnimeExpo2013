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
import java.util.List;

/**
 * Created by Anthony on 6/21/13.
 */
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

    public Panels[] panel (String fileName)
        throws XmlPullParserException,IOException {

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);

        XmlPullParser parsing = factory.newPullParser();

        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        int index = -1;
        Panels inPanel[] = new Panels[index]; // OUTPUT - Array of objects
        String text = "";

        parsing.setInput(new InputStreamReader(fis));

        int eventType = parsing.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT){

            String tagname = parsing.getName();

            switch(eventType){
                case XmlPullParser.START_TAG: {
                    if(tagname.equalsIgnoreCase("panel")){
                        index++;
                    }

                    break;
                }

                case XmlPullParser.TEXT: {
                    text = parsing.getText();

                }

                case XmlPullParser.END_TAG: {
                    if(tagname.equalsIgnoreCase("day")){
                        this.day =Integer.parseInt(text);
                    }
                    else if(tagname.equalsIgnoreCase("title")){
                        this.title = text;
                    }
                    else if(tagname.equalsIgnoreCase("hourbegin")){
                        this.hourBegin = Integer.parseInt(text);
                    }
                    else if(tagname.equalsIgnoreCase("minutebegin")){
                        this.minuteBegin = Integer.parseInt(text);
                    }
                    else if(tagname.equalsIgnoreCase("hourend")){
                        this.hourEnd = Integer.parseInt(text);

                    }
                    else if(tagname.equalsIgnoreCase("minutened")){
                        this.minuteEnd = Integer.parseInt(text);
                    }
                    else if(tagname.equalsIgnoreCase("location")){
                        this.location = text;
                    }


                }

                default:{
                    break;
                }
            }

        }

        if(hourBegin > 12){

            this.timeBegin = (hourBegin - 12) + ":" + minuteBegin + " PM";
        }
        else{
            this.timeBegin = (hourBegin) + ":" + minuteBegin + " AM";
        }

        if(hourEnd > 12){
            this.timeEnd = (hourEnd -12) +":" + minuteEnd + " PM";
        }
        else{
            this.timeEnd = (hourEnd) + ":" + minuteEnd + " AM";
        }

        return inPanel;
    }
}
