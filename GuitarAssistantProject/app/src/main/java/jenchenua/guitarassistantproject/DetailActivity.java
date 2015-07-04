package jenchenua.guitarassistantproject;

import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import jenchenua.guitarassistantproject.tabs.SlidingTabLayout;
import jenchenua.guitarassistantproject.utils.AnalyticsActivity;
import jenchenua.guitarassistantproject.utils.ViewPagerAdapter;


public class DetailActivity extends AnalyticsActivity {

    private Toolbar toolbar = null;

    private ViewPager pager = null;
    private ViewPagerAdapter adapter = null;
    private SlidingTabLayout tabs = null;

    private CharSequence[] titles;

    private static final int NUMB_OF_TABS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Creating The Toolbar and setting it as the Toolbar for the activity
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("fingeringName"));
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        titles = new CharSequence[] {
                getResources().getString(R.string.tab_1),
                getResources().getString(R.string.tab_2),
                getResources().getString(R.string.tab_3),
                getResources().getString(R.string.tab_4),
                getResources().getString(R.string.tab_5)
        };
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), titles, NUMB_OF_TABS);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.detail_pager);
        pager.setAdapter(adapter);

        // Assigning the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.detail_tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }
}
