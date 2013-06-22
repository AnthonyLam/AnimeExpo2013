package com.lamapress.animeexpo2013;

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

    public Panels(int day,String title,
                  int hourBegin,int minuteBegin,
                  int hourEnd,int minuteEnd){
        this.day = day;
        this.title  = title;
        this.hourBegin = hourBegin;
        this.minuteBegin = minuteBegin;
        this.hourEnd = hourEnd;
        this.minuteEnd = minuteEnd;

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

    }
}
