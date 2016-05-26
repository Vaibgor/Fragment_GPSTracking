package info.androidhive.simdocomo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import info.androidhive.simdocomo.adapter.URLConfig;

public class GamesFragment extends Fragment {
    Spinner fos, issue, payment, voc, payment_type;
    EditText eddate,amount, a_mobile, a_email, remark, receipt;
    Button submit;
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS1 = "success1";
    private static final String TAG_PRODUCTS1 = "products1";
    JSONArray products1 = null;
    int success1;
    String paymrnt_spin[] = {"NA", "Paid", "Unpaid"};
    String paymenttype[] = {"NA", "Cash", "Cheque", "Online"};// updated by vaibhav on 17/05/2016 add 0th position
    String recid,id_fos,voc_name,payment_name,issue_name,fos_name,payment_mode;
    ImageView caldate;
    private int day;
    private int month;
    private int year;
    TextView txvoc, txt_payment, pay_mode;

    static final int DATE_PICKER_ID = 1111;

    FrameLayout dateframe;
    FrameLayout vocframe, pay_frame, pay_mode_frame;

    public GamesFragment(String feed) {
        this.recid = feed;
    }
    public GamesFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_games, container, false);
        fos = (Spinner) rootView.findViewById(R.id.spinner);
        issue = (Spinner) rootView.findViewById(R.id.spinner2);
        payment = (Spinner) rootView.findViewById(R.id.spinner3);
        voc = (Spinner) rootView.findViewById(R.id.spinner4);
        payment_type = (Spinner) rootView.findViewById(R.id.spinner8);

        amount = (EditText) rootView.findViewById(R.id.editText5);
        remark = (EditText) rootView.findViewById(R.id.editText6);
        eddate = (EditText) rootView.findViewById(R.id.editText9);
        a_mobile = (EditText) rootView.findViewById(R.id.editText7);
        a_email = (EditText) rootView.findViewById(R.id.editText8);
        receipt = (EditText) rootView.findViewById(R.id.editText10);

        submit = (Button) rootView.findViewById(R.id.button5);
        dateframe = (FrameLayout) rootView.findViewById(R.id.framl9);
        vocframe = (FrameLayout) rootView.findViewById(R.id.frame_voc);
        pay_frame = (FrameLayout) rootView.findViewById(R.id.frame_payment1);
        pay_mode_frame = (FrameLayout) rootView.findViewById(R.id.frame_payment);

        txvoc = (TextView) rootView.findViewById(R.id.textView34);
        txt_payment = (TextView) rootView.findViewById(R.id.textView33);
        pay_mode = (TextView) rootView.findViewById(R.id.textView35);
        dateframe.setVisibility(View.GONE);
        caldate = (ImageView) rootView.findViewById(R.id.imageView7);

        amount.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("")) { //do your work here
                }}
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {
                //		Toast.makeText(getActivity(), amount.getText().toString(), Toast.LENGTH_SHORT).show();
                ((MainActivity) getActivity()).onTextChanged(fos_name, issue_name, voc_name, payment_name,
                        amount.getText().toString(), remark.getText().toString(), payment_mode,
                        a_mobile.getText().toString(), a_email.getText().toString(), receipt.getText().toString());
            }
        });

        a_mobile.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (!s.equals("")) { //do your work here }
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {
                //		Toast.makeText(getActivity(), amount.getText().toString(), Toast.LENGTH_SHORT).show();
                ((MainActivity) getActivity()).onTextChanged(fos_name, issue_name, voc_name, payment_name,
                        amount.getText().toString(), remark.getText().toString(), payment_mode,
                        a_mobile.getText().toString(), a_email.getText().toString(), receipt.getText().toString());

            }
        });
        a_email.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (!s.equals("")) { //do your work here }
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {
                //		Toast.makeText(getActivity(), amount.getText().toString(), Toast.LENGTH_SHORT).show();
                ((MainActivity) getActivity()).onTextChanged(fos_name, issue_name, voc_name, payment_name,
                        amount.getText().toString(), remark.getText().toString(), payment_mode,
                        a_mobile.getText().toString(), a_email.getText().toString(), receipt.getText().toString());

            }
        });

        receipt.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (!s.equals("")) { //do your work here }
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {
                //		Toast.makeText(getActivity(), amount.getText().toString(), Toast.LENGTH_SHORT).show();
                ((MainActivity) getActivity()).onTextChanged(fos_name, issue_name, voc_name, payment_name,
                        amount.getText().toString(), remark.getText().toString(), payment_mode,
                        a_mobile.getText().toString(), a_email.getText().toString(), receipt.getText().toString());

            }
        });

        remark.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (!s.equals("")) { //do your work here }
                }}

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {
                //	Toast.makeText(getActivity(), remark.getText().toString(), Toast.LENGTH_SHORT).show();
                ((MainActivity) getActivity()).onTextChanged(fos_name, issue_name, voc_name, payment_name,
                        amount.getText().toString(), remark.getText().toString(), payment_mode,
                        a_mobile.getText().toString(), a_email.getText().toString(), receipt.getText().toString());

            }
        });

        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
        JSONObject json1 = jsonParser.makeHttpRequest(URLConfig.FOS_Status, "POST", params1);
        Log.d("FOS_Status", json1.toString());
        try {
            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;
            success1 = json1.getInt(TAG_SUCCESS1);
            if (success1 == 1) {
                products1 = json1.getJSONArray(TAG_PRODUCTS1);

                String name_fos[] = new String[products1.length()];
                String name2[] = new String[products1.length()];

                for (int i1 = 0; i1 < products1.length(); i1++) {
                    JSONObject c = products1.getJSONObject(i1);
                    id_fos = c.getString("fos_id");
                    name_fos[i1] = c.getString("fos");
                    ArrayAdapter adapter11 = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, name_fos);
                    adapter11.setDropDownViewResource(R.layout.spinner_dropdown_item);
                    fos.setAdapter(adapter11);
                }
            }
        } catch (JSONException e) {e.printStackTrace();}

        fos.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        fos_name = parent.getItemAtPosition(pos).toString();
                        ((MainActivity) getActivity()).onTextChanged(fos_name, issue_name, voc_name,
                                payment_name, amount.getText().toString(), remark.getText().toString(),
                                payment_mode, a_mobile.getText().toString(), a_email.getText().toString(),
                                receipt.getText().toString());
                        switch (pos) {
                            case 1:
                                //not contact
                                // updated by vaibhav pote on 17/05/16
                                //do the changes on spinner selection
                                payment.setSelection(0);
                                amount.setVisibility(View.GONE);
                                txt_payment.setVisibility(view.GONE);
                                pay_frame.setVisibility(view.GONE);
                                pay_mode.setVisibility(view.GONE);
                                pay_mode_frame.setVisibility(view.GONE);
                                a_mobile.setVisibility(view.GONE);
                                a_email.setVisibility(view.GONE);
                                receipt.setVisibility(view.GONE);
                                break;
                            case 2:
                                //followup
                                dateframe.setVisibility(View.VISIBLE);
                                vocframe.setVisibility(View.GONE);
                                txvoc.setVisibility(View.GONE);
                                amount.setVisibility(View.GONE);
                                txt_payment.setVisibility(view.GONE);
                                pay_frame.setVisibility(view.GONE);
                                pay_mode.setVisibility(view.GONE);
                                pay_mode_frame.setVisibility(view.GONE);
                                a_mobile.setVisibility(view.GONE);
                                a_email.setVisibility(view.GONE);
                                receipt.setVisibility(view.GONE);
                                remark.setVisibility(view.VISIBLE);
                                break;
                            default:
                                dateframe.setVisibility(View.GONE);
                                vocframe.setVisibility(View.VISIBLE);
                                txvoc.setVisibility(View.VISIBLE);
                                amount.setVisibility(View.VISIBLE);
                                txt_payment.setVisibility(view.VISIBLE);
                                pay_frame.setVisibility(view.VISIBLE);
                                pay_mode.setVisibility(view.VISIBLE);
                                pay_mode_frame.setVisibility(view.VISIBLE);
                                a_mobile.setVisibility(view.VISIBLE);
                                a_email.setVisibility(view.VISIBLE);
                                receipt.setVisibility(view.VISIBLE);
                                break;
                        }

                        List<NameValuePair> params2 = new ArrayList<NameValuePair>();
                        params2.add(new BasicNameValuePair("fos_name", fos_name));

                        JSONObject json2 = jsonParser.makeHttpRequest(URLConfig.Scenario_Status, "POST", params2);
                        Log.d("Scenario_Status", json2.toString());
                        try {
                            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
                            HashMap<String, String> map;
                            success1 = json2.getInt(TAG_SUCCESS1);
                            if (success1 == 1) {

                                products1 = json2.getJSONArray(TAG_PRODUCTS1);
                                String scenerio[] = new String[products1.length()];
                                for (int i1 = 0; i1 < products1.length(); i1++) {
                                    JSONObject c = products1.getJSONObject(i1);
                                    id_fos = c.getString("scenarioid");
                                    scenerio[i1] = c.getString("scenario");

                                    ArrayAdapter adapter2 = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, scenerio);
                                    adapter2.setDropDownViewResource(R.layout.spinner_dropdown_item);
                                    issue.setAdapter(adapter2);
                                }
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                        issue.setOnItemSelectedListener(
                                new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                        issue_name = parent.getItemAtPosition(pos).toString();
                                        ((MainActivity) getActivity()).onTextChanged(fos_name, issue_name, voc_name, payment_name, amount.getText().toString(), remark.getText().toString(), payment_mode, a_mobile.getText().toString(), a_email.getText().toString(), receipt.getText().toString());

                                        List<NameValuePair> params3 = new ArrayList<NameValuePair>();
                                        params3.add(new BasicNameValuePair("issue_name", issue_name));

                                        JSONObject json3 = jsonParser.makeHttpRequest(URLConfig.Scenario_Reason, "POST", params3);
                                        Log.d("Scenario_Reason", json3.toString());
                                        try {
                                            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
                                            HashMap<String, String> map;
                                            success1 = json3.getInt(TAG_SUCCESS1);
                                            if (success1 == 1) {

                                                products1 = json3.getJSONArray(TAG_PRODUCTS1);
                                                //   String[]  pro = new String[products1.length()];
                                                String scenerio_reason[] = new String[products1.length()];
                                                String name2[] = new String[products1.length()];

                                                String ac_no, imgname = null, img = null;
                                                for (int i1 = 0; i1 < products1.length(); i1++) {
                                                    JSONObject c = products1.getJSONObject(i1);

                                                    // String id = c.getString(TAG_PID1);
                                                    id_fos = c.getString("reason_id");
                                                    scenerio_reason[i1] = c.getString("reason");

                                                    ArrayAdapter adapter4 = new ArrayAdapter(getActivity(),
                                                            android.R.layout.simple_spinner_item, scenerio_reason);
                                                    adapter4.setDropDownViewResource(R.layout.spinner_dropdown_item);
                                                    voc.setAdapter(adapter4);
                                                }
                                            }
                                        } catch (JSONException e) {

                                            e.printStackTrace();
                                        }
                                        voc.setOnItemSelectedListener(
                                                new AdapterView.OnItemSelectedListener() {
                                                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                                        voc_name = parent.getItemAtPosition(pos).toString();
                                                        ((MainActivity) getActivity()).onTextChanged(fos_name,
                                                                issue_name, voc_name, payment_name,
                                                                amount.getText().toString(), remark.getText().toString(),
                                                                payment_mode, a_mobile.getText().toString(),
                                                                a_email.getText().toString(), receipt.getText().toString());
                                                    }

                                                    public void onNothingSelected(AdapterView<?> parent) {}});
                                    }
                                    public void onNothingSelected(AdapterView<?> parent) {}
                                });

                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

        ArrayAdapter a3 = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, paymrnt_spin);
        a3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        payment.setAdapter(a3);
        payment.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        payment_name = parent.getItemAtPosition(pos).toString();
                        ((MainActivity) getActivity()).onTextChanged(fos_name, issue_name, voc_name,
                                payment_name, amount.getText().toString(), remark.getText().toString(),
                                payment_mode, a_mobile.getText().toString(), a_email.getText().toString(),
                                receipt.getText().toString());
                        switch (pos) {
                            //updated by vaibhav pote on 17/05/2016
                            // add extra fields in spi(--)at 0th position
                            case 1:
                                //paid
                                amount.setVisibility(View.VISIBLE);
                                pay_mode.setVisibility(view.VISIBLE);
                                pay_mode_frame.setVisibility(view.VISIBLE);
                                a_mobile.setVisibility(view.VISIBLE);
                                a_email.setVisibility(view.VISIBLE);
                                receipt.setVisibility(view.VISIBLE);
                                break;
                            default:
                                //unpaid
                                payment_type.setSelection(0);
                                amount.setVisibility(View.GONE);
                                pay_mode.setVisibility(view.GONE);
                                pay_mode_frame.setVisibility(view.GONE);
                                a_mobile.setVisibility(view.GONE);
                                a_email.setVisibility(view.GONE);
                                receipt.setVisibility(view.GONE);
                                break;
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {}
                });


        ArrayAdapter a4 = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, paymenttype);
        a4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        payment_type.setAdapter(a4);
        payment_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                payment_mode = parent.getItemAtPosition(pos).toString();
                ((MainActivity) getActivity()).onTextChanged(fos_name, issue_name, voc_name,
                        payment_name, amount.getText().toString(), remark.getText().toString(),
                        payment_mode, a_mobile.getText().toString(), a_email.getText().toString(),
                        receipt.getText().toString());
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        final Calendar cal = Calendar.getInstance();

        year = cal.get(Calendar.YEAR);

        month = cal.get(Calendar.MONTH);

        day = cal.get(Calendar.DAY_OF_MONTH);

        caldate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                createFancyDateTimePicker(DATE_PICKER_ID).show();//DATE_PICKER_ID
            }
        });

        return rootView;
    }

    private Dialog createFancyDateTimePicker(int datePickerId) {
        switch (datePickerId) {
            case DATE_PICKER_ID:
                return new DatePickerDialog(getActivity(),
                        mDateSetListener,
                        year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            year = year;
            month = monthOfYear;
            day = dayOfMonth;
            eddate.setText(new StringBuilder().append(day).append("/").append(month + 1).append("/").append(year));
        }
    };
}
