package info.androidhive.simdocomo.adapter;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import info.androidhive.simdocomo.GPS.GPSTracker;
import info.androidhive.simdocomo.GPS.GPSTrackerService;
import info.androidhive.simdocomo.JSONParser;
import info.androidhive.simdocomo.MainActivity;
import info.androidhive.simdocomo.MainActivity2;
import info.androidhive.simdocomo.R;

/**
 * Created by User on 2/16/2016.
 */
public class login extends Activity {
    Button login_btn;
    EditText username, password, eddate;
    ImageView caldate;
    Database database;
    CheckBox checkBox;
    ArrayList<LoginDetails_model> details_models;
    JSONParser jsonParser = new JSONParser();
    private String vendor_code, agency_id, strpassword, name;
    private static final String TAG_SUCCESS1 = "success1";
    private static final String TAG_PRODUCTS1 = "products1";
    JSONArray products2 = null, products1 = null;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    String strLat, strLong, strnull, strAddress;

    GPSTracker gps;
    GPSTrackerService trackerService;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        gps = new GPSTracker(this);
        trackerService = new GPSTrackerService();
        database = new Database(this);
        actionBar.hide();
        setContentView(R.layout.activity_login);
        sharedpreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        login_btn = (Button) findViewById(R.id.button);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        username = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        caldate = (ImageView) findViewById(R.id.imageView7);
        eddate = (EditText) findViewById(R.id.editText9);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // updated by vaibhav pote on 16/05/2016
                if (isConnectingToInternet(getApplicationContext())) {
                    Log.i("INTERNET", "Available");
                    if (!gps.canGetLocation()) {
                        if (flag == 0) {
                            gps.showSettingsAlert();
                            flag = 1;
                        } else {
                            btnLogin();
                        }
                    } else if (gps.canGetLocation()) {
                        btnLogin();
                    }

                } else {
                    Log.i("CHECK INTERNET", "Not Available");
                    Toast toast = Toast.makeText(getApplicationContext(), "Check your Internet Connectivity", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
        if (checkBox.isChecked()) {
            username.setText(sharedpreferences.getString("vendor_name", name));
            password.setText(sharedpreferences.getString("password", strpassword));
        }
    }

    // updated by vaibhav pote on 16/05/2016
    public void btnLogin() {

            if (username.getText().toString().length()==0){
                //Toast.makeText(getApplicationContext(), "Username cannot be Blank", Toast.LENGTH_LONG).show();
                username.setError("username cannot be Blank");
            }else if (password.getText().toString().length()==0){
               // Toast.makeText(getApplicationContext(), "Password cannot be Blank", Toast.LENGTH_LONG).show();
                password.setError("Password cannot be Blank");
            }else if (username.getText().toString().equals("") || password.getText().toString().equals("")){
                Toast.makeText(login.this, "Please Enter Username Or Password", Toast.LENGTH_LONG).show();
            }else {
            int success1;
            List<NameValuePair> params2 = new ArrayList<NameValuePair>();
            params2.add(new BasicNameValuePair("username", username.getText().toString()));
            params2.add(new BasicNameValuePair("password", password.getText().toString()));
            JSONObject json2 = jsonParser.makeHttpRequest(URLConfig.Login, "POST", params2);
            Log.d("Login data", json2.toString());
            try {
                final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> map;
                success1 = json2.getInt(TAG_SUCCESS1);
                if (success1 == 1) {

                    products1 = json2.getJSONArray(TAG_PRODUCTS1);
                    //   String[]  pro = new String[products1.length()];
                    String vender_name[] = new String[products1.length()];

                    String name, imgname = null, img = null;
                    for (int i1 = 0; i1 < products1.length(); i1++) {
                        JSONObject c = products1.getJSONObject(i1);

                        // String id = c.getString(TAG_PID1);
                        name = c.getString("vendor_name");
                        vendor_code = c.getString("vendor_code");
                        agency_id = c.getString("agency");
                        strpassword = password.getText().toString();
                        //    SharedPreferences = getSharedPreferences(NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("vendor_code", vendor_code);
                        editor.putString("vendor_name", name);
                        editor.putString("agency_id", agency_id);
                        editor.putString("password", strpassword);
                        editor.apply();
                        if (checkBox.isChecked()) {
                            details_models = new ArrayList<LoginDetails_model>();
                            LoginDetails_model details_model = new LoginDetails_model();
                            details_model.setUserId(Integer.parseInt(vendor_code));
                            details_model.setUserName(name);
                            details_model.setAgency_code(agency_id);
                            database.insertUserID(details_model);
                            Log.i("User_id", String.valueOf(database.getUserID()));
                        } else {
                            checkBox.setChecked(false);
                        }
                        // updated by Vaibhav pote on 21/05/2016

                        Log.i("AGENCY_ID", agency_id);
                        gpsTrack();
                        if (gps.canGetLocation()) {
                            double dobLat = Double.parseDouble(strLat);
                            Double dobLong = Double.parseDouble(strLong);
                            strAddress = getCompleteAddressString(dobLat, dobLong);
                            Log.i("Login Address", strAddress);
                            trackerService.trackGPS(agency_id, vendor_code, strLat, strLong, strnull, strAddress);
                        } else {
                            trackerService.trackGPS(agency_id, vendor_code, strLat, strLong, strnull, strAddress);
                        }
                        // Toast.makeText(login.this,"welcome"+" "+name,Toast.LENGTH_LONG).show();
                        Intent i = new Intent(login.this, MainActivity2.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                       /* i.putExtra("vendor_code", vendor_code);
                        i.putExtra("name", name);
                        i.putExtra("agency_id", agency_id);*/
                        startActivity(i);
                        finish();
                    }
                }
                if (success1 == 0) {
                    Toast.makeText(login.this, "Wrong Username Or Password", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // GPS Tracker Calling function on login
    public void gpsTrack() {
        // handle GPS data\
        try {
            if (gps.canGetLocation()) { // gps enabled} // return boolean true/false
                gps.getLatitude(); // returns latitude
                gps.getLongitude(); // returns longitude
                strLat = String.valueOf(gps.getLatitude());
                strLong = String.valueOf(gps.getLongitude());

                Log.i("TRACK_LATITUDE", String.valueOf(gps.getLatitude()));
                Log.i("TRACK_LONGITUDE", String.valueOf(gps.getLongitude()));
               /* Toast.makeText(login.this, "TACK_LATITUDE" + gps.getLatitude(), Toast.LENGTH_SHORT).show();
                Toast.makeText(login.this, "TRAK_LONGITUDE" + gps.getLongitude(), Toast.LENGTH_SHORT).show();*/
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    // for complete address through lat long
    // updated by vaibhav pote on 21/05/2016
    private String getCompleteAddressString(Double LATITUDE, Double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.d("My Current address", " " + strReturnedAddress.toString());
            } else {
                Log.d("My Current address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current address", "Cannot get Address!");
        }
        return strAdd;
    }

    // updated by vaibhav pote on 16/05/2016
    private boolean isConnectingToInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            Toast toast = Toast.makeText(getApplicationContext(), "Unable to connect", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        } else
            return true;
    }
}
