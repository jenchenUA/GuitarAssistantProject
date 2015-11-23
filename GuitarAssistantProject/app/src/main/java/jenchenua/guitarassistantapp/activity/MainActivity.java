package jenchenua.guitarassistantapp.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;

import jenchenua.guitarassistantapp.R;
import jenchenua.guitarassistantapp.utils.AnalyticsActivity;
import jenchenua.guitarassistantapp.utils.Utilities;

public class MainActivity extends AnalyticsActivity {
    private final static String LOG_TAG = "MainActivity";

    private Drawer.Result drawerResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        AccountHeader.Result headerResult = Utilities.getAccountHeader(MainActivity.this, savedInstanceState);
        drawerResult = Utilities.createDrawer(MainActivity.this, toolbar, headerResult);
        drawerResult.setSelectionByIdentifier(1, true); // Set proper selection

        drawerResult.openDrawer();
    }

    @Override
    public void onBackPressed() {
        if (drawerResult != null && drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
