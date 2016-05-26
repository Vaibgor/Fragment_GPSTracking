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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
 * Created by Vaibhav on 20/05/2016.
 * updated by vaibhav pote on 26/05/2016
 */
public class open extends Fragment {
    JSONParser jsonParser = new JSONParser();

    private static final String TAG_SUCCESS1 = "success1";
    private static final String TAG_PRODUCTS1 = "products1";
    JSONArray products1 = null;
    SharedPreferences sharedpreferences;
    ListView list;
    ProgressDialog pDialog;
    String code, agency_id, name, queryval, segmentImage,id;
    ArrayList<category> userArray = new ArrayList<category>();
    OpenListCustomAdapter userAdapter;
    public open() {}

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
        list = (ListView) rootView.findViewById(R.id.listview);
        new getOpenListData().execute();
        return rootView;
    }
    private class getOpenListData extends AsyncTask<String, String, ArrayList<category>> {
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
                    String rec_id, custName, ac_no, location;
                    if (userArray.size()==0){
                    for (int i1 = 0; i1 < products1.length(); i1++) {
                        JSONObject c = products1.getJSONObject(i1);
                        rec_id = c.getString("rec_id");
                        ac_no = c.getString("account_no");
                        location = c.getString("location");
                        custName = c.getString("cust_name");
                        segmentImage = c.getString("segment");
                        userArray.add(new category(rec_id, ac_no, custName, location, code, name,segmentImage));
                        }
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
                userAdapter = new OpenListCustomAdapter(getActivity(), R.layout.list_row, userArray,agency_id);
                list.setAdapter(userAdapter);
            } else {
                Log.d("setGrid else success ", "In setGriddata on else success is 0");
            }
            pDialog.dismiss();
        }
    }

    //custom adpter to bind list row data to the custom list view
    // created by vaibhav pote on 26/05/2016
    public class OpenListCustomAdapter extends ArrayAdapter<category> {

        Context context;
        int layoutResourceId;
        String agency_id;
        ArrayList<category> data = new ArrayList<category>();
        public OpenListCustomAdapter(Context context, int layoutResourceId,
                                                ArrayList<category> data, String agency_id) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
            this.agency_id = agency_id;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View list = convertView;
            UserHolder holder = null;
            if (list == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                list = inflater.inflate(layoutResourceId, parent, false);
                holder = new UserHolder();
                holder.image = (ImageView) list.findViewById(R.id.imageView4);

                holder.fname = (TextView) list.findViewById(R.id.textView1);
                holder.age = (TextView) list.findViewById(R.id.textView2);
                holder.area = (TextView) list.findViewById(R.id.textView3);

                list.setTag(holder);
            } else {
                holder = (UserHolder) list.getTag();
            }
            category member = data.get(position);
            final String temp = member.getRec_id();
            final String cust_name = member.getCustName();
            final String accNo = member.getAcc_no();
            final String name1 = member.getName1();
            final String code = member.getCode();
            final String segmentImage = member.getSegmentImage();

            if (segmentImage.equals("A")){
                holder.image.setImageResource(R.drawable.plattinum_type);
            }else if (segmentImage.equals("B")){
                holder.image.setImageResource(R.drawable.gold_type);
            }else if (segmentImage.equals("C")){
                holder.image.setImageResource(R.drawable.silver_type);
            }

            holder.fname.setText(member.getAcc_no());
            holder.age.setText(member.getCustName());
            holder.area.setText(member.getLocation());

            // onClick listRow get affected and data pass to the next activity
            list.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent op = new Intent(context, MainActivity.class);
                    op.putExtra("cat_it", temp);
                    op.putExtra("cust_name", cust_name);
                    op.putExtra("name", name1);
                    op.putExtra("code", code);
                    op.putExtra("account_no", accNo);
                    op.putExtra("agency_id", agency_id);
                    op.putExtra("segment", segmentImage);
                    context.startActivity(op);
                }
            });
            return list;
        }

        private class UserHolder {
            TextView area;
            ImageView image;
            TextView age;
            TextView fname;
        }
    }
}
