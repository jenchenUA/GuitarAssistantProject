package jenchenua.guitarassistantproject.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.List;

import jenchenua.guitarassistantproject.BuildConfig;
import jenchenua.guitarassistantproject.MainActivity;
import jenchenua.guitarassistantproject.R;
import jenchenua.guitarassistantproject.fragments.PatternFragment;
import jenchenua.guitarassistantproject.fragments.PentatonicFragment;
import jenchenua.guitarassistantproject.fragments.ScaleFragment;

public class Utilities {
    private static final String version = "Version: " + BuildConfig.VERSION_NAME + '.' + BuildConfig.VERSION_CODE;
    private static final String LOG_TAG = "Utilities";

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Drawer.OnDrawerItemClickListener handlerOnClick(final Drawer.Result drawerResult, final AppCompatActivity activity) {
        return new Drawer.OnDrawerItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                //check if the drawerItem is set.
                //there are different reasons for the drawerItem to be null
                //--> click on the header
                //--> click on the footer
                //those items don't contain a drawerItem
                if (drawerItem != null) {
                    switch (drawerItem.getIdentifier()) {
                        case 1:
                            Log.i(LOG_TAG, "Click on Scale");
                            activity.getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, new ScaleFragment())
                                    .commit();
                            break;
                        case 2:
                            Log.i(LOG_TAG, "Click on Pattern");
                            activity.getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, new PatternFragment())
                                    .commit();
                            break;
                        case 3:
                            Log.i(LOG_TAG, "Click on Pentatonic");
                            activity.getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, new PentatonicFragment())
                                    .commit();
                            break;
                        case 4:
                            Log.i(LOG_TAG, "Click on Version");
                            Toast.makeText(activity.getApplicationContext(), version, Toast.LENGTH_SHORT).show();
                            break;
                        case 70:
                            try {
                                Intent int_rate = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + activity.getApplicationContext().getPackageName()));
                                int_rate.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                activity.getApplicationContext().startActivity(int_rate);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }
            }
        };
    }

    public static AccountHeader.Result getAccountHeader(final AppCompatActivity activity, final Bundle savedInstanceState) {
        return new AccountHeader()
                .withActivity(activity)
                .withSavedInstance(savedInstanceState)
                .build();

    }

    public static Drawer.Result createDrawer(final AppCompatActivity activity, Toolbar toolbar, AccountHeader.Result accountHeader) {
        Drawer.Result drawerResult = new Drawer()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withDisplayBelowToolbar(true)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(accountHeader)
                .addDrawerItems(createDrawerItems())
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public boolean equals(Object o) {
                        return super.equals(o);
                    }

                    @Override
                    public void onDrawerOpened(View view) {
                        hideSoftKeyboard(activity);
                    }

                    @Override
                    public void onDrawerClosed(View view) {

                    }
                })
                .build();

        drawerResult.setOnDrawerItemClickListener(handlerOnClick(drawerResult, activity));

        return drawerResult;
    }

    private static IDrawerItem[] createDrawerItems() {
        return new IDrawerItem[]{
                new PrimaryDrawerItem()
                        .withName(R.string.primary_drawer_item_scales)
                        .withIdentifier(1),
                new PrimaryDrawerItem()
                        .withName(R.string.primary_drawer_item_patterns)
                        .withIdentifier(2),
                new PrimaryDrawerItem()
                        .withName(R.string.primary_drawer_item_pentatonic)
                        .withIdentifier(3),
                new DividerDrawerItem(),
                new SecondaryDrawerItem()
                        .withName(R.string.secondary_drawer_item_version)
                        .withIdentifier(4)};
    }
}
