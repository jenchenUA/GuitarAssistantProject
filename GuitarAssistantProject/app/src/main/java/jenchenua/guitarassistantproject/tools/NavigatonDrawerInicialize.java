package jenchenua.guitarassistantproject.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import jenchenua.guitarassistantproject.PatternActivity;
import jenchenua.guitarassistantproject.PentatonicActivity;
import jenchenua.guitarassistantproject.R;
import jenchenua.guitarassistantproject.ScalesActivity;

public class NavigatonDrawerInicialize {
    final static String SCALE_TAG = "scale";
    final static String PATTERN_TAG = "pattern";
    final static String PENTATONIC_TAG = "pentatonic";
    final static String VERSION_TAG = "version";

    private Activity activity;

    public NavigatonDrawerInicialize(Activity activity) {
        this.activity = activity;
    }

    private AccountHeader.Result inizializeAccountHeader() {
        return new AccountHeader()
                .withActivity(activity)
                .build();

    }

    public Drawer inicializeDrawerResult(Toolbar toolbar) {
        AccountHeader.Result accountHeader = inizializeAccountHeader();

        return new Drawer()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withDisplayBelowToolbar(true)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(accountHeader)
                .addDrawerItems(createDrawerItems());
    }

    private IDrawerItem[] createDrawerItems() {
        return new IDrawerItem[]{
                new PrimaryDrawerItem()
                    .withName(R.string.primary_drawer_item_scales)
                    .withIdentifier(1)
                    .withTag(SCALE_TAG),
                new PrimaryDrawerItem()
                    .withName(R.string.primary_drawer_item_patterns)
                    .withIdentifier(2)
                    .withTag(PATTERN_TAG),
                new PrimaryDrawerItem()
                    .withName(R.string.primary_drawer_item_pentatonic)
                    .withIdentifier(3)
                    .withTag(PENTATONIC_TAG),
                new DividerDrawerItem(),
                new SecondaryDrawerItem()
                    .withName(R.string.secondary_drawer_item_version)
                    .withTag(VERSION_TAG)};
    }
}
