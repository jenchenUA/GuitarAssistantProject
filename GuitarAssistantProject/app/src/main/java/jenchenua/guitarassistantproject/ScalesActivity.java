package jenchenua.guitarassistantproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


public class ScalesActivity extends AppCompatActivity {
    private Drawer.Result drawerResult; //Declare Drawer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scales);

        //Replace standard ActionBar for Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        initiliazeDrawerResult(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scales, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerResult != null && drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    private void initiliazeDrawerResult(Toolbar toolbar) {
        AccountHeader.Result accountHeaderResult = createAccountHeader();

        drawerResult = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowToolbar(true)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(accountHeaderResult)
                .addDrawerItems(createDrawerItem())
                .build();
    }

    private AccountHeader.Result createAccountHeader() {
        return new AccountHeader()
                .withActivity(this)
                .build();
    }

    //Create DrawerItems for DrawerResult
    private IDrawerItem[] createDrawerItem() {
        return new IDrawerItem[]{new PrimaryDrawerItem()
                .withName(R.string.primary_drawer_item_scales)
                .withIdentifier(1)
                .withTag("scale"),
                new PrimaryDrawerItem()
                        .withName(R.string.primary_drawer_item_patterns)
                        .withIdentifier(2)
                        .withTag("pattern"),
                new PrimaryDrawerItem()
                        .withName(R.string.primary_drawer_item_pentatonic)
                        .withIdentifier(3)
                        .withTag("pentatonic"),
                new DividerDrawerItem(),
                new SecondaryDrawerItem()
                        .withName(R.string.secondary_drawer_item_version)
                        .withTag("version")};
    }

}
