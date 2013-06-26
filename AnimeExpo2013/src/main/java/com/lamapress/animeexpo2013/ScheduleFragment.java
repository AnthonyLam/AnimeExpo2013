package com.lamapress.animeexpo2013;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

public class ScheduleFragment extends Fragment {
    public ScheduleFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ListView mainList;

        SqlMaker sqlmaker = new SqlMaker(this.getActivity());
        List<Panels> panelList =  sqlmaker.getContent();
        Collections.sort(panelList);

        View viewInflated = inflater.inflate(R.layout.fragment_schedule_view,container,false);
        PanelAdapter adapter = new PanelAdapter(getActivity(),R.layout.panel_list_row,panelList);
        mainList = (ListView)viewInflated.findViewById(R.id.schedule_list);

        mainList.setAdapter(adapter);
        return viewInflated;
    }
}
