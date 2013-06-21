package com.lamapress.animeexpo2013;

import java.nio.BufferUnderflowException;
import java.util.Locale;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class HomePage extends FragmentActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_home);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }
    
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            Fragment fragment = new Fragment();
            Bundle args = new Bundle();
            switch(position){
                case 0:{
                   fragment = new ScheduleFragment();
                    break;
                }
                case 1:{
                    fragment = new EventViewFragment();
                    break;
                }
                case 2:{
                    break;
                }
                default:
                    fragment = new DummySectionFragment();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.schedule).toUpperCase(l);
                case 1:
                    return getString(R.string.events).toUpperCase(l);
                case 2:
                    return getString(R.string.map).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A dummy fragment representing a section of the app, but that simply
     * displays dummy text.
     */
    public static class DummySectionFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public static final String ARG_SECTION_NUMBER = "section_number";

        public DummySectionFragment() {

        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home_page_dummy, container, false);
            TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
            return rootView;
        }
    }

    public static class ScheduleFragment extends Fragment{
        public ScheduleFragment(){

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View listView = inflater.inflate(R.layout.fragment_schedule_view,container,false);
            ListView mainList = (ListView) listView.findViewById(R.id.mainList);
            return listView;
        }
    }

    public static class EventViewFragment extends Fragment {
        public EventViewFragment(){

        }
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            ListView listView;
            View view = inflater.inflate(R.layout.fragment_event_view,container,false);

            Expo_Events expos[] = new Expo_Events[]{
                    new Expo_Events(R.drawable.axstockphoto,"Panels",
                            ""), // Item 0
                    new Expo_Events(R.drawable.axstockphoto,"Guest of Honor",
                            "Meet industry professionals"),                     // Item 1
                    new Expo_Events(R.drawable.axstockphoto,"Films",
                            "Relax and watch your favorite anime"),             // Item 2
                    new Expo_Events(R.drawable.axstockphoto,"Ticketed Event",
                            "Prepurchased ticket required"),                    // Item 3
                    new Expo_Events(R.drawable.axstockphoto,"Workshop",
                            "Learn something new"),                             // Item 4
                    new Expo_Events(R.drawable.axstockphoto,"Premiere",
                            "Brand new anime to watch"),                        // Item 5
                    new Expo_Events(R.drawable.axstockphoto,"Non-Ticketed Event",
                            "Various AX hosted events"),                        // Item 6
                    new Expo_Events(R.drawable.axstockphoto,"Mature Content",
                            "18+ Only")                                         // Item 7

            };

            EventAdapter adapter = new EventAdapter(getActivity(),R.layout.event_list_row,expos);
            listView = (ListView)view.findViewById(R.id.eventTypes);
            listView.setAdapter(adapter);

            return view;
        }
    }
}
