package jenchenua.guitarassistantapp.utils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

import jenchenua.guitarassistantapp.R;

public class AnalyticsActivity extends AppCompatActivity {
    private static GoogleAnalytics analytics;
    private static Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        analytics = GoogleAnalytics.getInstance(getApplicationContext());
        analytics.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);

        tracker = analytics.newTracker(R.xml.app_tracker);
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);
    }

    synchronized public static Tracker getTracker() {
        return tracker;
    }
}
