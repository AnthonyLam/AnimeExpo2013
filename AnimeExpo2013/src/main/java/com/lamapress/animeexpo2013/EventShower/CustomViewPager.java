package com.lamapress.animeexpo2013.EventShower;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.gms.maps.MapView;

public class CustomViewPager extends ViewPager {

    public CustomViewPager(Context context){
        super(context);
    }

    public CustomViewPager(Context context,AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    protected boolean canScroll(View v,boolean checkV,int dx, int x, int y){

        return (v instanceof MapView) || super.canScroll(v,checkV,dx,x,y);
    }
}
