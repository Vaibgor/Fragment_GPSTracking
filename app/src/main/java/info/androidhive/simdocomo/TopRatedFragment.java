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

public class TopRatedFragment extends Fragment{
	public TopRatedFragment(){}
	private static final int MODE_PRIVATE = 0;
	AlertDialog dialog1;
	JSONParser jsonParser = new JSONParser();
	private String vendor_code, name;
	private static final String  TAG_SUCCESS1 = "success1";

	private static final String TAG_PRODUCTS1 = "products1";
	JSONArray products6 = null, products1 = null;
	private static final String TAG_PID1 = "pid1";
	private static final String TAG_NAME6 = "name1", TAG_NAME1 = "name1";
    int success1;
	Button backpg;
	String refid,code,name1;
	String feed;
    Button wrong_allocation,case_query;
	TextView ac_no,area,del_no,bill_cycle,contact_no,cust_name,add,total_out,product,previews_note,total_del,segment,acc_status,calling_status,calling_voc;
	SharedPreferences sharedpreferences;

	public TopRatedFragment(String feed,String code,String name1) {
		//Toast.makeText(getActivity(), "User id is =" + feed,Toast.LENGTH_LONG).show();
	this.refid=feed;
		this.code=code;
		this.name1=name1;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_top_rated, container, false);


		//Toast.makeText(getActivity(), "code="+code+" "+"name"+ name1, Toast.LENGTH_LONG).show();


		ac_no=(TextView)rootView.findViewById(R.id.textView2);
		area=(TextView)rootView.findViewById(R.id.textView4);
		del_no=(TextView)rootView.findViewById(R.id.textView6);
		bill_cycle=(TextView)rootView.findViewById(R.id.textView8);
		contact_no=(TextView)rootView.findViewById(R.id.textView10);
		cust_name=(TextView)rootView.findViewById(R.id.textView12);
		add=(TextView)rootView.findViewById(R.id.textView14);
		total_out=(TextView)rootView.findViewById(R.id.textView16);
		product=(TextView)rootView.findViewById(R.id.textView18);
		previews_note=(TextView)rootView.findViewById(R.id.textView20);
		total_del=(TextView)rootView.findViewById(R.id.textView22);
		segment=(TextView)rootView.findViewById(R.id.textView24);
		acc_status=(TextView)rootView.findViewById(R.id.textView26);
		calling_status=(TextView)rootView.findViewById(R.id.textView28);
		calling_voc=(TextView)rootView.findViewById(R.id.textView30);

		// topRatedActivity
		case_query=(Button)rootView.findViewById(R.id.button3);
		wrong_allocation=(Button)rootView.findViewById(R.id.button2);


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

						Toast.makeText(getActivity(), "refif="+refid, Toast.LENGTH_SHORT).show();
						JSONObject json12= jsonParser.makeHttpRequest(URLConfig.Wrong_Allocation, "POST", params12);
						Log.d("Wrong_Allocation",json12.toString());

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
				//   String[]  pro = new String[products1.length()];
				String vender_name[] = new String[products1.length()];

				String  name, imgname = null, img = null;
				for (int i1 = 0; i1 < products1.length(); i1++) {
					JSONObject c = products1.getJSONObject(i1);

					// String id = c.getString(TAG_PID1);

					name=c.getString("del_no");
					ac_no.setText(c.getString("account_no"));
					area.setText(c.getString("location"));
					del_no.setText(c.getString("del_no"));
					bill_cycle.setText(c.getString("Bill_Cycle"));
					contact_no.setText(c.getString("del_no"));
					cust_name.setText(c.getString("Cust_Name"));
					add.setText(c.getString("cust_add"));
					total_out.setText(c.getString("net_bal"));
					product.setText(c.getString("product"));
					previews_note.setText("null");
					total_del.setText(c.getString("location"));
					segment.setText(c.getString("Segment"));
					acc_status.setText(c.getString("status"));
					calling_status.setText("null");
					calling_voc.setText("null");

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
						Log.d("Case_Query",json2.toString());
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
