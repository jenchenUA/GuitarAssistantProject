package jenchenua.guitarassistantapp.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import jenchenua.guitarassistantapp.R;
import jenchenua.guitarassistantapp.database.DBHelper;
import jenchenua.guitarassistantapp.database.FingeringDatabase;
import jenchenua.guitarassistantapp.tabs.SlidingTabLayout;
import jenchenua.guitarassistantapp.utils.AnalyticsActivity;
import jenchenua.guitarassistantapp.utils.ViewPagerAdapter;

public class DetailActivity extends AnalyticsActivity {
    private static final int NUMB_OF_TABS = 5;
    private DBHelper mDBHelper;
    private SQLiteDatabase mDatabase;
    private Cursor mCursor;
    private String positions;
    private String formula;
    private String fingeringName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("fingeringName"));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String tableName = getIntent().getStringExtra(MainActivity.TABLE_NAME);
        String[] columns = {
                FingeringDatabase.FORMULA_COLUMN,
                FingeringDatabase.POSITIONS_COLUMN
        };
        fingeringName = getIntent().getStringExtra(MainActivity.FINGERING_NAME);
        getDataFromDatabase(tableName, columns, fingeringName);
        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getSupportFragmentManager(),
                getTabTitlesFromResources(),
                NUMB_OF_TABS,
                formula,
                positions
        );
        ViewPager pager = (ViewPager) findViewById(R.id.detail_pager);
        pager.setAdapter(adapter);

        SlidingTabLayout tabs = (SlidingTabLayout) findViewById(R.id.detail_tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
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

    private void getDataFromDatabase(String tableName, String[] columnsName, String fingeringName) {
        mDatabase = getDatabase();

        String where = FingeringDatabase.NAME_COLUMN + " = " + "\"" + fingeringName + "\"";
        mCursor = mDatabase.query(
                tableName,
                columnsName,
                where,
                null,
                null,
                null,
                null
        );

        mCursor.moveToFirst();
        formula = mCursor.getString(mCursor.getColumnIndex(FingeringDatabase.FORMULA_COLUMN));
        positions = mCursor.getString(mCursor.getColumnIndex(FingeringDatabase.POSITIONS_COLUMN));

        closeDatabaseConnection();
    }

    private SQLiteDatabase getDatabase() {
        mDBHelper = new DBHelper(this);
        return mDBHelper.getReadableDatabase();
    }

    private void closeDatabaseConnection() {
        mCursor.close();
        mDatabase.close();
        mDBHelper.close();
    }

    private String[] getTabTitlesFromResources() {
        String[] titles = new String[]{
                getResources().getString(R.string.tab_1),
                getResources().getString(R.string.tab_2),
                getResources().getString(R.string.tab_3),
                getResources().getString(R.string.tab_4),
                getResources().getString(R.string.tab_5)
        };
        return titles;
    }
}
