package info.androidhive.simdocomo;


import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import info.androidhive.simdocomo.GPS.GPSTrackerService;
import info.androidhive.simdocomo.adapter.Database;
import info.androidhive.simdocomo.adapter.login;


public class splash extends Activity {
    Database database;
    private static final int SPLASH_TIME_OUT = 5000;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    String vendor_code, vendor_name, agency_id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new Database(this);
        try{
            sharedpreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
            vendor_code = sharedpreferences.getString("vendor_code", null);
            vendor_name = sharedpreferences.getString("vendor_name", null);
            agency_id = sharedpreferences.getString("agency_id", null);

            ActionBar actionBar = getActionBar();
            actionBar.hide();
            setContentView(R.layout.splash);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    int userId = database.getUserID();
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.clear();
                    if (vendor_code != null) {
                        Intent i = new Intent(splash.this, MainActivity2.class);
                        startActivity(i);
                    } else if (vendor_code == null) {
                        Intent intent = new Intent(splash.this, login.class);
                        startActivity(intent);
                    }
                    // close this activity
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    /**
     * Created by Administrator on 3/23/16.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (!isMyGPSTrackerServiceRunning()) {
            startService(new Intent(getApplicationContext(), GPSTrackerService.class));
            //Log.i("reminderp pop up",String.valueOf());
        }
    }

    private boolean isMyGPSTrackerServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if ("info.androidhive.simdocomo.GPS.GPSTrackerService"
                    .equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
