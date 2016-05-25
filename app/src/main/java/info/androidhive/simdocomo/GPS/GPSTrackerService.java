package info.androidhive.simdocomo.GPS;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import info.androidhive.simdocomo.JSONParser;
import info.androidhive.simdocomo.MainActivity2;
import info.androidhive.simdocomo.R;
import info.androidhive.simdocomo.adapter.URLConfig;

public class GPSTrackerService extends Service {

    LocationManager locationManager;
    GPSTracker gpsTracker;
    String strLat, strLong;

    JSONParser jsonParser = new JSONParser();
    private String vendor_code, agency_id, name;
    private static final String TAG_SUCCESS2 = "success";
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;

    public GPSTrackerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sharedpreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        // new SenderThread().start();
    }

    public class SenderThread extends Thread {
        SenderThread() {
        }

        public void run() {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        /*locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            gpsTracker.showSettingsAlert();
                        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            sentGPSLocation();
                        }*/
                        Thread.sleep(1800000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void sentGPSLocation() {
        gpsTracker = new GPSTracker(GPSTrackerService.this);
        if (gpsTracker.canGetLocation()) {
            strLat = String.valueOf(gpsTracker.getLatitude());
            strLong = String.valueOf(gpsTracker.getLongitude());
            //trackGPS("1223","121212");
        } else {
            gpsTracker.showSettingsAlert();
        }
    }

    public void trackGPS(String agency_id, String vendor_code, String strLat, String strLong, String ac_no, String address) {

        try {
            //new SenderThread().start();
            // sentGPSLocation();
            int success2;

            Log.e("strLat", strLat);
            Log.e("strLong", strLong);
            List<NameValuePair> params3 = new ArrayList<NameValuePair>();
            params3.add(new BasicNameValuePair("agency_id", agency_id));
            params3.add(new BasicNameValuePair("fos_id", vendor_code));
            params3.add(new BasicNameValuePair("lat", strLat));
            params3.add(new BasicNameValuePair("long", strLong));
            params3.add(new BasicNameValuePair("ac_no", ac_no));
            params3.add(new BasicNameValuePair("address", address));
            JSONObject json3 = jsonParser.makeHttpRequest(URLConfig.GPSTrackLocation, "POST", params3);
            Log.d("GPSTrackLocation", json3.toString());
            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;
            success2 = json3.getInt(TAG_SUCCESS2);
            Log.i("GPS_RESPONSE", String.valueOf(success2));
            if (success2 == 1) {
                Log.i("GPS Call service", String.valueOf(success2));
            }
            if (success2 == 0) {
                Log.i("GPS service not Call", String.valueOf(success2));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

    }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
