package info.androidhive.simdocomo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import info.androidhive.simdocomo.adapter.URLConfig;

public class TopRatedFragment extends Fragment {
    public TopRatedFragment() {
    }

    AlertDialog dialog1;
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS1 = "success1";
    private static final String TAG_PRODUCTS1 = "products1";
    JSONArray products1 = null;
    int success1;
    Button backpg, wrong_allocation, case_query;
    String refid, code, name1,segment;
    ImageView image4;
    TextView ac_no, tvLocation, tvDellNo, tvBillCycle, tvAltContact_no, tvCustName, tvCustAdd, tvNetBalance, tvProduct, previews_note,
            tvDellCount, tvSegment, acc_status, tvComp_name, calling_voc;
    SharedPreferences sharedpreferences;

    public TopRatedFragment(String rec_id, String code, String name1,String segment) {
        //Toast.makeText(getActivity(), "User id is =" + feed,Toast.LENGTH_LONG).show();
        this.refid = rec_id;
        this.code = code;
        this.name1 = name1;
        this.segment=segment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_top_rated, container, false);

        ac_no = (TextView) rootView.findViewById(R.id.textView2);
        tvLocation = (TextView) rootView.findViewById(R.id.textView4);
        tvDellNo = (TextView) rootView.findViewById(R.id.textView6);
        tvBillCycle = (TextView) rootView.findViewById(R.id.textView8);
        tvAltContact_no = (TextView) rootView.findViewById(R.id.textView10);
        tvCustName = (TextView) rootView.findViewById(R.id.textView12);
        tvCustAdd = (TextView) rootView.findViewById(R.id.textView14);
        tvNetBalance = (TextView) rootView.findViewById(R.id.textView16);
        tvProduct = (TextView) rootView.findViewById(R.id.textView18);
        previews_note = (TextView) rootView.findViewById(R.id.textView20);
        tvDellCount = (TextView) rootView.findViewById(R.id.textView22);
        tvSegment = (TextView) rootView.findViewById(R.id.textView24);
        acc_status = (TextView) rootView.findViewById(R.id.textView26);
        tvComp_name = (TextView) rootView.findViewById(R.id.textView28);
        calling_voc = (TextView) rootView.findViewById(R.id.textView30);

        // topRatedActivity
        case_query = (Button) rootView.findViewById(R.id.button3);
        wrong_allocation = (Button) rootView.findViewById(R.id.button2);
        image4 = (ImageView) rootView.findViewById(R.id.mIvSegmentImage);

        case_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });

        wrong_allocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

                // Setting Dialog Title
                alertDialog.setTitle("Confirm Save...");

                // Setting Dialog Message
                alertDialog.setMessage("Are you sure you want Save this?");

                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.msg);

                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        int success1;
                        List<NameValuePair> params12 = new ArrayList<NameValuePair>();
                        params12.add(new BasicNameValuePair("wrong_allocation", "WA"));
                        params12.add(new BasicNameValuePair("rec_id", refid));

                        Toast.makeText(getActivity(), "refif=" + refid, Toast.LENGTH_SHORT).show();
                        JSONObject json12 = jsonParser.makeHttpRequest(URLConfig.Wrong_Allocation, "POST", params12);
                        Log.d("Wrong_Allocation", json12.toString());

                        // Write your code here to invoke YES event
                        //Toast.makeText(getActivity(), "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getActivity(), MainActivity2.class);
                        i.putExtra("vendor_code", code);
                        i.putExtra("name", name1);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); ///to clear all previous activities
                        startActivity(i);
                        //finish();
                        //getActivity().finish();
                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        Toast.makeText(getActivity(), "Cancled", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }
        });

        List<NameValuePair> params11 = new ArrayList<NameValuePair>();
        params11.add(new BasicNameValuePair("rec_id", refid));
        JSONObject json11 = jsonParser.makeHttpRequest(URLConfig.Details, "POST", params11);
        Log.d("Details_data", json11.toString());
        try {
            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;
            success1 = json11.getInt(TAG_SUCCESS1);
            if (success1 == 1) {
                products1 = json11.getJSONArray(TAG_PRODUCTS1);
                for (int i1 = 0; i1 < products1.length(); i1++) {
                    JSONObject c = products1.getJSONObject(i1);

                    ac_no.setText(c.getString("account_no"));
                    tvLocation.setText(c.getString("location"));
                    tvDellNo.setText(c.getString("del_no"));
                    tvComp_name.setText(c.getString("comp_name"));
                    tvBillCycle.setText(c.getString("Bill_Cycle"));
                    tvAltContact_no.setText(c.getString("alt_no"));
                    tvCustName.setText(c.getString("Cust_Name"));
                    tvCustAdd.setText(c.getString("cust_add"));
                    tvNetBalance.setText(c.getString("net_bal"));
                    tvProduct.setText(c.getString("product"));
                    previews_note.setText("null");
                    tvSegment.setText(c.getString("Segment"));
                    tvDellCount.setText("1");

                    if (c.getString("Segment").equals("A")) {
                        image4.setImageResource(R.drawable.plattinum_type);
                    } else if (c.getString("Segment").equals("B")) {
                        image4.setImageResource(R.drawable.gold_type);
                    } else if (c.getString("Segment").equals("C")) {
                        image4.setImageResource(R.drawable.silver_type);
                    }
                    acc_status.setText(c.getString("status"));
                    /*calling_status.setText("null");
                    calling_voc.setText("null");*/

                    //  Toast.makeText(getActivity(),name,Toast.LENGTH_LONG).show();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        final EditText ed_query = (EditText) promptView.findViewById(R.id.edittext);
        final ImageView delimg = (ImageView) promptView.findViewById(R.id.imageView6);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //    resultText.setText("Hello, " + editText.getText());
                        int success1;
                        List<NameValuePair> params2 = new ArrayList<NameValuePair>();
                        params2.add(new BasicNameValuePair("fos_user_id", code));
                        params2.add(new BasicNameValuePair("cust_id", refid));
                        params2.add(new BasicNameValuePair("query", ed_query.getText().toString()));

                        JSONObject json2 = jsonParser.makeHttpRequest(URLConfig.Case_Query, "POST", params2);
                        Log.d("Case_Query", json2.toString());
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        dialog1 = alertDialogBuilder.create();
        dialog1.show();
        delimg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });


    }


}
