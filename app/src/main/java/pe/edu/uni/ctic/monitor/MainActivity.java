package pe.edu.uni.ctic.monitor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import pe.edu.uni.ctic.monitor.Device.Dispositivo;
import pe.edu.uni.ctic.monitor.Device.InfoDevicesActivityFragment;
import pe.edu.uni.ctic.monitor.Maps.HeatMap;
import pe.edu.uni.ctic.monitor.Maps.MarkersMap;
import pe.edu.uni.ctic.monitor.Parametrizacion.Parametrizacion;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Dispositivo> results;
    Fragment supportFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState != null)
            getSupportActionBar().setTitle(savedInstanceState.getCharSequence("actionbar_title"));
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putCharSequence("actionbar_title", getSupportActionBar().getTitle());
    }

    @Override
    public void onBackPressed() {
        getSupportActionBar().setTitle("Monitor");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //FragmentManager mFragmentManager = getSupportFragmentManager();
            //mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            /*FragmentManager fm = getSupportFragmentManager();
            Log.d("portland", "seattle - " + getSupportFragmentManager().getBackStackEntryCount());
            fm.popBackStackImmediate();
            fm.popBackStackImmediate();
            Log.d("portlandia", "seattle - " + getSupportFragmentManager().getBackStackEntryCount());
            if (fm.getFragments().size() > 0)
                for (Fragment frag : fm.getFragments()) {
                    if (frag.isVisible()) {
                        FragmentManager childFm = frag.getChildFragmentManager();
                        if (childFm.getBackStackEntryCount() > 0) {
                            childFm.popBackStack();
                            return;
                        }
                    }
                }*/
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        getSupportFragmentManager().popBackStack();
        Fragment fragment = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.param) {
            try {
                new cargarDispositivos("consInf/").execute().get();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("dispositivos", results);
                fragment = new InfoDevicesActivityFragment();
                fragment.setArguments(bundle);
            } catch(InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            getSupportActionBar().setTitle(R.string.param);
        } else if (id == R.id.marker_map) {
            fragment = new MarkersMap();
            getSupportActionBar().setTitle(R.string.marker_map);
        } else if (id == R.id.heat_map) {
            fragment = new HeatMap();
            getSupportActionBar().setTitle(R.string.heat_map);
        } else if (id == R.id.paramfil) {
            fragment = new Parametrizacion();
            getSupportActionBar().setTitle("Parametrización Test");
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_main, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class cargarDispositivos extends AsyncTask<String, Void, String> {
        String url;
        public cargarDispositivos(String url){
            this.url=url;
        }

        @Override
        protected String doInBackground(String... urls) {
            String respues="...";
            try {
                ConexionServer cs= new ConexionServer();
                results = cs.receiveJsonDispositivo(url);
            }catch (IOException e) {
                e.printStackTrace();
            }
            return respues;
        }

        @Override
        protected void onPostExecute(String result) {
        }
    }

}
