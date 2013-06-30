package com.lamapress.animeexpo2013.ExpoInformation;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.lamapress.animeexpo2013.R;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        TextView legal = (TextView)findViewById(R.id.google_legal);
        legal.setText(GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(this));
    }


}
