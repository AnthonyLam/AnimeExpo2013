package com.lamapress.animeexpo2013;

/**
 *  Class describing behavior of an Event
 */
public class Expo_Events {
    public int icon;
    public String event_type;
    public String panel_info;

    public Expo_Events(){
        super();
    }

    public Expo_Events(int icon, String event_type,String panel_info){
        super();
        this.icon = icon;
        this.event_type = event_type;
        this.panel_info = panel_info;
    }
}
