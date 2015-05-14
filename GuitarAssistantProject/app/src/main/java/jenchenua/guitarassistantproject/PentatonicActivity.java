package jenchenua.guitarassistantproject;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import jenchenua.guitarassistantproject.tools.NavigatonDrawerInicialize;


public class PentatonicActivity extends AppCompatActivity {
    final static String SCALE_TAG = "scale";
    final static String PATTERN_TAG = "pattern";
    final static String PENTATONIC_TAG = "pentatonic";
    final static String VERSION_TAG = "version";

    private Drawer.Result drawerResult;
    private NavigatonDrawerInicialize navigatonDrawerInicialize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pentatonic);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        navigatonDrawerInicialize = new NavigatonDrawerInicialize(this);
        drawerResult = navigatonDrawerInicialize.inicializeDrawerResult(toolbar)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        switch ((String)iDrawerItem.getTag()) {
                            case SCALE_TAG:
                                startActivity(new Intent(getApplicationContext(), ScalesActivity.class));
                                break;
                            case PATTERN_TAG:
                                startActivity(new Intent(getApplicationContext(), PatternActivity.class));
                                break;
                            case PENTATONIC_TAG:
                                startActivity(new Intent(getApplicationContext(), PentatonicActivity.class));
                        }
                    }
                })
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pentatonic, menu);
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
}
