package info.androidhive.simdocomo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
 * Created by User on 3/2/2016.
 * modified by vaibhav pote on 26/05/2016
 * fragment attach to mainActivity class
 */
public class closed extends Fragment {
    public closed(){}
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS1 = "success1";
    private static final String TAG_PRODUCTS1 = "products1";
    JSONArray products1 = null;
    String id;
    SharedPreferences sharedpreferences;
    ListView list;
    ProgressDialog pDialog;
    String code,name;
    ArrayList<category2> userArray = new ArrayList<category2>();
    CloseListCustomAdapter userAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    public closed(String feed,String name) {
        this.code =feed;
        this.name =name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.open, container, false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //   mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        list = (ListView)rootView.findViewById(R.id.listview);
        new getCloseListData().execute();
        return rootView;
    }
    private class getCloseListData extends AsyncTask<String, String, ArrayList<category2>> {
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

        protected ArrayList<category2> doInBackground(String... param) {
            ArrayList<category2> userArray1 = new ArrayList<category2>();
            Log.d("setGrid back", "In setGridData on do in background" );
            List<NameValuePair> params3 = new ArrayList<NameValuePair>();
            params3.add(new BasicNameValuePair("code1",code));
            JSONObject json3 = jsonParser.makeHttpRequest(URLConfig.CloseList, "POST", params3);
            Log.d("Close_list",json3.toString());

            try {
                final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> map;
                success1 = json3.getInt(TAG_SUCCESS1);
                if (success1 == 1) {
                    products1 = json3.getJSONArray(TAG_PRODUCTS1);

                    String  pro, uname,ac_no,segment;
                    for (int i1 = 0; i1 < products1.length(); i1++) {
                        JSONObject c = products1.getJSONObject(i1);
                        pro=c.getString("rec_id");
                        ac_no=c.getString("account_no");
                        uname = c.getString("cust_name");
                        segment = c.getString("segment");
                        userArray.add(new category2(pro,ac_no, uname,segment));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return userArray1;
        }

        protected void onPostExecute(ArrayList<category2> userArray1) {

            Log.d("setGrid on post", "In setGriddata on post");
            if(success1==1){
                Log.d("setGrid if success", "In setGriddata on if success is 1" );
                userAdapter = new CloseListCustomAdapter(getActivity(), R.layout.list_row, userArray);
                list.setAdapter(userAdapter);
            }else{
                Log.d("setGrid else success ", "In setGriddata on else success is 0");
            }
            pDialog.dismiss();
        }
    }
    // updated by vaibhav pote on 26/05/2016
    public class CloseListCustomAdapter extends ArrayAdapter<category2> {
        Context context;
        int layoutResourceId;
        ArrayList<category2> data = new ArrayList<category2>();

        public CloseListCustomAdapter(Context context, int layoutResourceId,
                                                 ArrayList<category2> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }
        public View getView(final int position, View convertView, ViewGroup parent) {
            View list = convertView;
            UserHolder holder = null;
            if (list == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                list = inflater.inflate(layoutResourceId, parent, false);
                holder = new UserHolder();
                holder.image = (ImageView) list.findViewById(R.id.imageView4);
                holder.segmentImage = (TextView) list.findViewById(R.id.textView3);
                holder.fname = (TextView) list.findViewById(R.id.textView1);
                holder.age = (TextView) list.findViewById(R.id.textView2);

                list.setTag(holder);
            } else {
                holder = (UserHolder) list.getTag();
            }
            category2 member = data.get(position);
            final String temp = member.getU_id();
            final String segment = member.getSegment();
            if (segment.equals("A")){
                holder.segmentImage.setText("PLATINUM");
                holder.image.setImageResource(R.drawable.plattinum_type);
            }else if (segment.equals("B")){
                holder.segmentImage.setText("GOLD");
                holder.image.setImageResource(R.drawable.gold_type);
            }else if (segment.equals("C")){
                holder.segmentImage.setText("SILVER");
                holder.image.setImageResource(R.drawable.silver_type);
            }

            holder.fname.setText(member.getFirst_name());
            holder.age.setText(member.getAge());
            list.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                }
            });
            return list;
        }
        private class UserHolder {
            TextView segmentImage;
            ImageView image;
            TextView age;
            TextView fname;
        }
    }
}

