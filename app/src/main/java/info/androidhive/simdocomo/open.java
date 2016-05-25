package info.androidhive.simdocomo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import info.androidhive.simdocomo.adapter.URLConfig;

/**
 * Created by User on 2/18/2016.
 */
public class open extends Fragment {


    private static final int MODE_PRIVATE = 4355;
    int status = 0;
    int numberPc = 0;
    JSONParser jsonParser = new JSONParser();

    private String last, first;
    private static final String TAG_SUCCESS6 = "success6", TAG_SUCCESS1 = "success1";
    private static final String TAG_PRODUCTS6 = "assemblyconstituency", TAG_PRODUCTS1 = "products1";
    JSONArray products6 = null, products1 = null;
    private static final String TAG_PID1 = "pid1";
    private static final String TAG_NAME6 = "name1", TAG_NAME1 = "name1";
    String pro, id;
    public static final String mypreference = "mypref";
    public static final String Name = "namekey";

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    ListView list;
    GridView category;
    View rootView;
    Context context;
    ProgressDialog pDialog;
    int[] imageId;
    String code, agency_id, name, queryval;
    ArrayList<category> userArray = new ArrayList<category>();
    UserCustomAdapterMatrimonySearch userAdapter;

    public open() {
    }

    public open(String feed, String name, String val,String agency_id) {
        this.code = feed;
        this.name = name;
        this.queryval = val;
        this.agency_id=agency_id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.open, container, false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
//Toast.makeText(getActivity(),"query value from mainactivity2"+queryval,Toast.LENGTH_LONG).show();
        list = (ListView) rootView.findViewById(R.id.listview);
        //  Toast.makeText(getActivity(), code, Toast.LENGTH_SHORT).show();
        new getList().execute();
        return rootView;
    }
    private class getList extends AsyncTask<String, String, ArrayList<category>> {
        int success1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("setGrid on pre", "In setGriddata on preexecute");
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading.... Please Wait!");
            pDialog.setCancelable(false);
            pDialog.show();
            Log.d("ImageLoadTask", "Loading image...");
        }

        // param[0] is img url
        protected ArrayList<category> doInBackground(String... param) {
            ArrayList<category> userArray1 = new ArrayList<category>();
            Log.d("setGrid back", "In setGriddata on do in background");
            List<NameValuePair> params3 = new ArrayList<NameValuePair>();
            params3.add(new BasicNameValuePair("code1", code));
            params3.add(new BasicNameValuePair("queryval", queryval));
            JSONObject json3 = jsonParser.makeHttpRequest(URLConfig.OpenList, "POST", params3);
            Log.d("Open Customer list", json3.toString());

            try {
                final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> map;
                success1 = json3.getInt(TAG_SUCCESS1);
                if (success1 == 1) {
                    products1 = json3.getJSONArray(TAG_PRODUCTS1);
                    //   String[]  pro = new String[products1.length()];
                    String name1[] = new String[products1.length()];
                    String name2[] = new String[products1.length()];

                    String pro, uname, ac_no, loc, imgname = null, img = null;
                    for (int i1 = 0; i1 < products1.length(); i1++) {
                        JSONObject c = products1.getJSONObject(i1);

                        // String id = c.getString(TAG_PID1);
                        pro = c.getString("rec_id");
                        ac_no = c.getString("account_no");
                        loc = c.getString("location");
                        uname = c.getString("cust_name");
                        imgname = c.getString("segment");

                        //img = URLConfig.Imgage;
                        userArray.add(new category(pro, ac_no, uname, loc, code, name));
                    }

                }
            } catch (JSONException e) {

                e.printStackTrace();
            }
            return userArray1;
        }

        protected void onPostExecute(ArrayList<category> userArray1) {

            Log.d("setGrid on post", "In setGriddata on post");
            if (success1 == 1) {
                Log.d("setGrid if success", "In setGriddata on if success is 1");
                userAdapter = new UserCustomAdapterMatrimonySearch(getActivity(), R.layout.list_row, userArray,agency_id);
                //        list = (ListView)rootView. findViewById(R.id.listview);
                list.setAdapter(userAdapter);

                for (category m : userArray) {
                    // START LOADING IMAGES FOR EACH STUDENT
                    m.loadImage(userAdapter);
                }
            } else {
                Log.d("setGrid else success ", "In setGriddata on else success is 0");
            }
            pDialog.dismiss();
        }
    }

    public void viewMemberInfo(final String listSelectedId1) {
        Intent intent = null;
        try {
            intent = new Intent(getActivity(), MainActivity.class);

        } catch (Exception e) {

        }
        intent.putExtra("cat_it", listSelectedId1);
        intent.putExtra("vendor_code", code);
        startActivity(intent);


    }

    public void quervalue(final String listSelectedId1) {
        Intent intent = null;
        try {
            intent = new Intent(getActivity(), MainActivity.class);

        } catch (Exception e) {

        }
        intent.putExtra("cat_it", listSelectedId1);
        intent.putExtra("vendor_code", code);
        startActivity(intent);


    }

}
