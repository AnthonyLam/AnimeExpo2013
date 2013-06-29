package com.lamapress.animeexpo2013;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

public class ScheduleFragment extends Fragment{

    ListView mainList;
    List<Panels> listPanel;
    PanelAdapter adapter;
    SqlMaker sqlmaker;
    View viewInflated;

    public ScheduleFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        sqlmaker = new SqlMaker(this.getActivity());
        listPanel = sqlmaker.getContent();
        Collections.sort(listPanel);

        viewInflated = inflater.inflate(R.layout.fragment_schedule_view,container,false);
        adapter = new PanelAdapter(getActivity(),R.layout.panel_list_row,listPanel);
        mainList = (ListView)viewInflated.findViewById(R.id.schedule_list);
        registerForContextMenu(mainList);

        mainList.setAdapter(adapter);
        return viewInflated;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);

        Boolean enabled;
        SqlMaker sql = new SqlMaker(getActivity());
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.event_pop_context,menu);

        enabled = sql.findContent("title",listPanel.get(info.position).title);

        MenuItem myMenuItem = menu.findItem(R.id.remove_from_schedule);
        myMenuItem.setVisible(enabled);
        myMenuItem = menu.findItem(R.id.add_to_schedule);
        myMenuItem.setVisible(!enabled);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch(item.getItemId()){
            case R.id.add_to_schedule:
                AddToSchedule(info.position);
                adapter.notifyDataSetChanged();
                return true;
            case R.id.view_on_map:
                Intent intent = new Intent(getActivity(),ExpoMapFragment.class);
                intent.putExtra("location",listPanel.get(info.position).location);
                this.startActivity(intent);
                return true;
            case R.id.remove_from_schedule:
                RemoveFromSchedule(info.position);
                refreshView();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void AddToSchedule(int position){
        SqlMaker sql = new SqlMaker(getActivity());
        sql.addContent(listPanel.get(position));
    }

    public void RemoveFromSchedule(int position){
        SqlMaker sql = new SqlMaker(getActivity());
        sql.removeContent("title",listPanel.get(position).title);

    }

    @Override
    public void onResume(){
        super.onResume();
        refreshView();
    }

    public void refreshView(){
        listPanel = sqlmaker.getContent();
        Collections.sort(listPanel);
        adapter.clear();
        adapter.addAll(listPanel);
        adapter.notifyDataSetChanged();
    }

}
