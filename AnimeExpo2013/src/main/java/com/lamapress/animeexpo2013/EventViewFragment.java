package com.lamapress.animeexpo2013;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public  class EventViewFragment extends Fragment {
    public EventViewFragment(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ListView listView;
        View view = inflater.inflate(R.layout.fragment_event_view,container,false);

        Expo_Events expos[] = new Expo_Events[]{
                new Expo_Events(R.drawable.ic_panel,"Panels",
                        ""), // Item 0
                new Expo_Events(R.drawable.ic_guest,"Guest of Honor",
                        "Meet industry professionals"),                     // Item 1
                new Expo_Events(R.drawable.ic_films,"Films",
                        "Relax and watch your favorite anime"),             // Item 2
                new Expo_Events(R.drawable.ic_ticketed,"Ticketed Event",
                        "Prepurchased ticket required"),                    // Item 3
                new Expo_Events(R.drawable.ic_workshop,"Workshop",
                        "Learn something new"),                             // Item 4
                new Expo_Events(R.drawable.ic_nonticket,"Non-Ticketed Event",
                        "Various AX hosted events"),                        // Item 5
                new Expo_Events(R.drawable.ic_sadpanda,"Mature Content",
                        "18+ Only"),                                         // Item 6
                new Expo_Events(R.drawable.ic_amv,"Anime Music Videos",
                        ""),
                new Expo_Events(R.drawable.ic_misc,"Miscellaneous",     // Item 7
                        "No Category")

        };

        EventAdapter adapter = new EventAdapter(getActivity(),R.layout.event_list_row,expos);
        listView = (ListView)view.findViewById(R.id.eventTypes);
        listView.setAdapter(adapter);

        return view;
    }
}