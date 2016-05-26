package info.androidhive.simdocomo;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;

import info.androidhive.simdocomo.adapter.Database;
import info.androidhive.simdocomo.adapter.TabsPagerAdapter2;

/**
 * Created by User on 3/2/2016.
 */
public class MainActivity2 extends FragmentActivity implements ActionBar.TabListener {
    Database database;
    private ViewPager viewPager;
    private TabsPagerAdapter2 mAdapter;
    private ActionBar actionBar;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    String  queryval = null, vendor_code, vendor_name, agency_id1;
    private String[] tabs = {"OPEN", "CLOSED", "PENDING FOR SYNC"};

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        database = new Database(this);

        sharedpreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        vendor_code = sharedpreferences.getString("vendor_code", null);
        vendor_name = sharedpreferences.getString("vendor_name", null);
        agency_id1 = sharedpreferences.getString("agency_id", null);

        ActionBar actionBar1 = getActionBar();
        actionBar1.setTitle("SIM");
        actionBar1.setSubtitle(vendor_name);
        actionBar1.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3d1010")));
        actionBar1.setDisplayHomeAsUpEnabled(true);
        actionBar1.setDisplayShowHomeEnabled(true);
        actionBar1.setDisplayUseLogoEnabled(false);
        actionBar1.setDisplayShowHomeEnabled(false);
        actionBar1.setIcon(R.drawable.star);
        viewPager = (ViewPager) findViewById(R.id.pager2);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter2(getSupportFragmentManager(), vendor_code, vendor_name, queryval, agency_id1);

        viewPager.setAdapter(mAdapter);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {}

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent i = new Intent(MainActivity2.this, MainActivity2.class);
                i.putExtra("qval", query);
                startActivity(i);
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);
        return super.onCreateOptionsMenu(menu);
    }
    /**
     * On selecting action bar icons
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_search:
                // search action
                return true;
            case R.id.action_logout:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity2.this);
                // Setting Dialog Title
                alertDialog.setTitle("Logout");
                // Setting Dialog Message
                alertDialog.setMessage("Are you sure you want to Logout?");
                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.ic_settings_power_black_24dp);

                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sharedpreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                        sharedpreferences.edit().clear().commit();
                        finish();
                    }
                });
                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
                break;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}

