package jenchenua.guitarassistantproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jenchenua.guitarassistantproject.fragments.ScaleFragment;
import jenchenua.guitarassistantproject.utils.Utilities;



public class MainActivity extends AppCompatActivity {
    private final static String LOG_TAG = "MainActivity";

    private Drawer.Result drawerResult = null;
    private AccountHeader.Result headerResult = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Replace standard ActionBar for Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        headerResult = Utilities.getAccountHeader(MainActivity.this, savedInstanceState);
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
