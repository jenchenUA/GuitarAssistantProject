package jenchenua.guitarassistantapp.utils;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import jenchenua.guitarassistantapp.BuildConfig;
import jenchenua.guitarassistantapp.R;
import jenchenua.guitarassistantapp.fragments.ChordFragment;
import jenchenua.guitarassistantapp.fragments.ScaleFragment;

public class Utilities {
    private static final String LOG_TAG = Utilities.class.getSimpleName();
    private static final String version = "Version: " + BuildConfig.VERSION_NAME + '.' + BuildConfig.VERSION_CODE;


    public static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
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
                            Log.i(LOG_TAG, "Click on Scales");
                            activity.getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, new ScaleFragment())
                                    .commit();
                            break;
                        /*case 2:
                            Log.i(LOG_TAG, "Click on Chords");
                            activity.getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, new ChordFragment())
                                    .commit();
                            break;*/
                        case 4:
                            Log.i(LOG_TAG, "Click on Version");
                            Toast.makeText(activity.getApplicationContext(), version, Toast.LENGTH_SHORT).show();
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
                .withHeaderBackground(R.drawable.account_header_guitar_1)
                .build();

    }

    public static Drawer.Result createDrawer(final AppCompatActivity activity, Toolbar toolbar, AccountHeader.Result accountHeader) {
        Drawer.Result drawerResult = new Drawer()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withDisplayBelowToolbar(false)
                .withActionBarDrawerToggleAnimated(true)
                .withTranslucentStatusBar(false)
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
                //new PrimaryDrawerItem()
                //        .withName(R.string.primary_drawer_item_chords)
                //        .withIdentifier(2),
                new DividerDrawerItem(),
                new SecondaryDrawerItem()
                        .withName(R.string.secondary_drawer_item_version)
                        .withIdentifier(4)};
    }
}
