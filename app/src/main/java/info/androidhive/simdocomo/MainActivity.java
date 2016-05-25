package info.androidhive.simdocomo;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.androidhive.simdocomo.GPS.GPSTracker;
import info.androidhive.simdocomo.GPS.GPSTrackerService;
import info.androidhive.simdocomo.adapter.TabsPagerAdapter;
import info.androidhive.simdocomo.adapter.URLConfig;

public class MainActivity extends FragmentActivity implements
        ActionBar.TabListener {
    GPSTracker gps;
    GPSTrackerService trackerService;
    String strLat, strLong, strAddress;

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;

    String text1, text2, text3, text4, text5, text6, video_id, cust_name, code, name1, payment_mode, a_mobile, a_email, receipt, account_no;
    int flag = 0;
    JSONParser jsonParser = new JSONParser();

    private String last, first;
    private static final String TAG_SUCCESS6 = "success6", TAG_SUCCESS1 = "success1";
    private static final String TAG_PRODUCTS6 = "assemblyconstituency", TAG_PRODUCTS1 = "products1";
    JSONArray products6 = null, products1 = null;
    private static final String TAG_PID1 = "pid1";
    private static final String TAG_NAME6 = "name1", TAG_NAME1 = "name1";
    int success1;
    String vendor_code, agency_id;
    // Tab titles
    private String[] tabs = {"Details", "Reports", "Images", "Videos"};

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // updated by vaibhav pote on 21/05/2016
        gps = new GPSTracker(this);
        trackerService = new GPSTrackerService();

        Bundle bundle = getIntent().getExtras();
        video_id = bundle.getString("cat_it");
        cust_name = bundle.getString("cust_name");
        code = bundle.getString("code");//vendor_code
        name1 = bundle.getString("name");
        account_no = bundle.getString("account_no");
        agency_id = bundle.getString("agency_id");

        ActionBar actionBar1 = getActionBar();
        actionBar1.setTitle("SIM");
        actionBar1.setSubtitle(cust_name);
        actionBar1.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3d1010")));
        //	Toast.makeText(MainActivity.this,name1,Toast.LENGTH_LONG).show();

        actionBar1.setHomeButtonEnabled(true);
        actionBar1.setDisplayHomeAsUpEnabled(true);
        //actionBar1.setHomeAsUpIndicator(R.drawable.back);
        actionBar1.setDisplayShowHomeEnabled(true);
        actionBar1.setDisplayUseLogoEnabled(false);
        actionBar1.setDisplayShowHomeEnabled(false);

        actionBar1.setIcon(R.drawable.star);
        //	Toast.makeText(getApplicationContext(), "User name is =" +name1,Toast.LENGTH_LONG).show();

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager(), video_id, code, name1);

        viewPager.setAdapter(mAdapter);
        //	actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
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

    void onTextChanged(String text1, String text2, String text3, String text4, String text5, String text6,
                       String payment_mode, String a_mobile, String a_email, String receipt) {
        this.text1 = text1;//fos_name
        this.text2 = text2;//issue_name
        this.text3 = text3;//voc_name
        this.text4 = text4;//payment_name
        this.text5 = text5;//amount
        this.text6 = text6;//remark
        this.payment_mode = payment_mode;//payment_mode
        this.a_mobile = a_mobile;//a_mobile
        this.a_email = a_email;//a_email
        this.receipt = receipt;//receipt
        //Receive new text here
        //Toast.makeText(MainActivity.this, "pass value activity"+text1, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.Submit:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                // Setting Dialog Title
                alertDialog.setTitle("Confirm Save...");
                // Setting Dialog Message
                alertDialog.setMessage("Are you sure you want Save this?");
                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.msg);

                // Setting Positive "Yes" Button
                if (gps.canGetLocation()){
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            submitDataOnSubmit();
                            double dobLat = Double.parseDouble(strLat);
                            Double dobLong = Double.parseDouble(strLong);
                            strAddress = getCompleteAddressString(dobLat, dobLong);
                            Log.i("Address on submit", strAddress);
                            trackerService.trackGPS(agency_id, code, strLat, strLong, account_no, strAddress);
                            Log.d("DialogOn GPS", agency_id + " " + code + " " + strLat + " " + strLong + " " + account_no);
                            //updated by vaibhav pote on 21/5/2016
                            if (!gps.canGetLocation()) {
                            /*if (flag == 0) {

                                flag = 1;
                            } else {
                                submitDataOnSubmit();
                                trackerService.trackGPS(agency_id, code, strLat, strLong, account_no, strAddress);
                                Log.d("DialogOn GPS", agency_id + " " + code + " " + strLat + " " + strLong + " " + account_no);
                            }*/
                            } else if (gps.canGetLocation()) {

                            }
                            // Write your code here to invoke YES event
                            Intent i = new Intent(MainActivity.this, MainActivity2.class);
                            i.putExtra("vendor_code", code);
                            i.putExtra("name", name1);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); ///to clear all previous activities
                            startActivity(i);
                        }
                    });
                }else{
                    gps.showSettingsAlert();
                }

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        Toast.makeText(getApplicationContext(), "Cancled", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void submitDataOnSubmit() {
        gps.getLatitude(); // returns latitude
        gps.getLongitude(); // returns longitude
        strLat = String.valueOf(gps.getLatitude());
        strLong = String.valueOf(gps.getLongitude());

        Log.i("LATITUDE", String.valueOf(gps.getLatitude()));
        Log.i("LONGITUDE", String.valueOf(gps.getLongitude()));
        int success1;
        List<NameValuePair> params2 = new ArrayList<NameValuePair>();
        params2.add(new BasicNameValuePair("fos", text1));
        params2.add(new BasicNameValuePair("issue", text2));
        params2.add(new BasicNameValuePair("voc", text3));
        params2.add(new BasicNameValuePair("payment", text4));
        params2.add(new BasicNameValuePair("rec_id", video_id));
        params2.add(new BasicNameValuePair("amount_paid", text5));
        params2.add(new BasicNameValuePair("remarks", text6));
        params2.add(new BasicNameValuePair("code", code));
        params2.add(new BasicNameValuePair("pay_by", payment_mode));
        params2.add(new BasicNameValuePair("a_mobno", a_mobile));
        params2.add(new BasicNameValuePair("a_email", a_email));
        params2.add(new BasicNameValuePair("receipt_no", receipt));

        JSONObject json2 = jsonParser.makeHttpRequest(URLConfig.Report, "POST", params2);
        Log.d("Report", json2.toString());
        Toast.makeText(getApplicationContext(), "Data Saved Successfully", Toast.LENGTH_SHORT).show();
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
            Log.w("My Current address", "Canont get Address!");
        }
        return strAdd;
    }
}
