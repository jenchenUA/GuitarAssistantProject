package jenchenua.guitarassistantproject.utils;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.Logger;

import jenchenua.guitarassistantproject.R;

public class GuitarAssistantAnalytics {
    private GoogleAnalytics analytics;
    private Tracker tracker;

    private Context context;

    public GuitarAssistantAnalytics(Context context) {
        this.context = context;
    }

    public Tracker tracker() {
        if (tracker == null) {
            analytics = GoogleAnalytics.getInstance(context);
            analytics.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
            analytics.setDryRun(false);
            analytics.setLocalDispatchPeriod(1200);

            tracker = analytics.newTracker(R.xml.global_tracker);
            tracker.enableExceptionReporting(true);
            tracker.enableAdvertisingIdCollection(true);
            tracker.enableAutoActivityTracking(true);
        }
        return tracker;
    }
}
