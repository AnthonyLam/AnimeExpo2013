package com.lamapress.animeexpo2013;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ScheduleFragment extends Fragment {
    public ScheduleFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View listView = inflater.inflate(R.layout.fragment_schedule_view,container,false);
        ListView mainList = (ListView) listView.findViewById(R.id.mainList);
        return listView;
    }
}
