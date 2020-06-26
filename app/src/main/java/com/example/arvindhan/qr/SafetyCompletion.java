package com.example.arvindhan.qr;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arvindhan.parameter.R;
import com.example.arvindhan.qr.Dueoverdue;
import com.google.zxing.integration.android.IntentIntegrator;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SafetyCompletion extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView leftNav,rightNav;
    ImageView purchase,repair,finance,safety,inventory,pms;
    HorizontalScrollView hsv;
    public static EditText date, remsrks;
    public static TextView Maintname,Frequency,Lastdone,Nextdue,Equip_name, value, Com_Date, Com_Status, Com_By, itemcode, entitycode, datetime,Category,Main_code,Name,loggedon,User_code;
    public ImageView PMS;
    public static Spinner s1;
    public static EditText s2;
    Button b;
    TextView Logoutttt,Pmscomp_text;
    RelativeLayout comp_inv,Purchase_lay,Repair_lay,Finance_lay,Safety_lay,R_pms;

    private int mYear, mMonth, mDay;
    private IntentIntegrator qrscan;

    String qr, registerstatus, dy, qy, tp, item, ent, cat, dt, remark, spin1, spin2,maint,componentcode,mcode="",ccode="",mcode1="",ccode1="";
    String ec = "0",strMsg;
    String userName, password,USerCode,text,equip_name,alert,geturlvalues,exitcode;

    private static final String SOAP_ACTION = "http://tempuri.org/qrsafetydone";
    private static final String METHOD_NAME = "qrsafetydone";
    private static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    private static  String URL = "";

    sqlconnect sq=new sqlconnect(this);
    ArrayList l1= new ArrayList();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration config = getResources().getConfiguration();
        if (config.smallestScreenWidthDp >= 600) {
            setContentView(R.layout.activity_safety_completion_tab);
        } else {
            setContentView(R.layout.activity_safety_completion);
        }


        SharedPreferences sharedPreferece = getSharedPreferences("URLREG", Context.MODE_PRIVATE);
        geturlvalues = sharedPreferece.getString("url_register", "");
        URL=geturlvalues;


        SharedPreferences pref1 = getSharedPreferences("exitcodes", Context.MODE_PRIVATE);
        exitcode = pref1.getString("exitcode", "");

//        leftNav = (ImageView) findViewById(R.id.left_nav);
//        rightNav = (ImageView) findViewById(R.id.right_nav);
//        hsv =(HorizontalScrollView) findViewById(R.id.hsv);

        comp_inv=(RelativeLayout) findViewById(R.id.comp_inve);
        R_pms=(RelativeLayout)findViewById(R.id.pms_lay);
        Purchase_lay=(RelativeLayout)findViewById(R.id.purchase_lay);
        Repair_lay=(RelativeLayout)findViewById(R.id.repair_lay);
        Finance_lay=(RelativeLayout)findViewById(R.id.finance_lay);
        Safety_lay=(RelativeLayout)findViewById(R.id.safety_lay);

        PMS=(ImageView)findViewById(R.id.img_inv1);
        date = (EditText) findViewById(R.id.date);
        remsrks = (EditText) findViewById(R.id.remarks);
        Maintname = (TextView) findViewById(R.id.scan);
        Frequency = (TextView) findViewById(R.id.freq);
        Lastdone = (TextView) findViewById(R.id.lastdone);
        Nextdue = (TextView) findViewById(R.id.nextdue);
        Pmscomp_text = (TextView) findViewById(R.id.pmscomp_text);

//        value = (TextView) findViewById(R.id.value);
        Equip_name = (TextView) findViewById(R.id.eqnamesafety);

        Com_Date = (TextView) findViewById(R.id.com_date);
        Com_Status = (TextView) findViewById(R.id.com_status);
        Com_By = (TextView) findViewById(R.id.com_by);
//        itemcode = (TextView) findViewById(R.id.mainatanance);
        entitycode = (TextView) findViewById(R.id.entity);
        datetime = (TextView) findViewById(R.id.modifieddate);
        Category = (TextView) findViewById(R.id.category);
        Main_code = (TextView) findViewById(R.id.maint_code);
//        Logoutttt = (TextView) findViewById(R.id.logoutt);
//        Name = (TextView) findViewById(R.id.name);
//        loggedon = (TextView) findViewById(R.id.datewelcome);
        User_code = (TextView) findViewById(R.id.usercode);

        s1 = (Spinner) findViewById(R.id.spinner1);
        s2 = (EditText) findViewById(R.id.spinner2);
        b = (Button) findViewById(R.id.store);

        pms=(ImageView)findViewById(R.id.img_inv1);
        inventory=(ImageView)findViewById(R.id.img_pur1);
        purchase=(ImageView)findViewById(R.id.img_crw1);
        repair=(ImageView)findViewById(R.id.img_trn1);
        finance=(ImageView)findViewById(R.id.img_alm1);
        safety=(ImageView)findViewById(R.id.img_cal1);

        int color = Color.parseColor("#A6A5A3"); // To hide the purchase,repair,etc.. in home modules
        pms.setColorFilter(color);
        inventory.setColorFilter(color);
        purchase.setColorFilter(color);
        repair.setColorFilter(color);
        finance.setColorFilter(color);
        safety.setColorFilter(color);

        l1=sq.Getid();
        String splt=l1.get(0).toString();

        if (splt.contains("1")){

            R_pms.setEnabled(true);
            R_pms.setVisibility(View.VISIBLE);
        }
        if (splt.contains("2")){
            comp_inv.setEnabled(true);
            comp_inv.setVisibility(View.VISIBLE);
        }
        if (splt.contains("3")) {
            Purchase_lay.setEnabled(true);
            Purchase_lay.setVisibility(View.VISIBLE);
        }
        if (splt.contains("4")) {
            Repair_lay.setEnabled(true);
            Repair_lay.setVisibility(View.VISIBLE);
        }
        if (splt.contains("5")) {
            Finance_lay.setEnabled(true);
            Finance_lay.setVisibility(View.VISIBLE);
        }
        if (splt.contains("6")) {
            Safety_lay.setEnabled(true);
            Safety_lay.setVisibility(View.VISIBLE);
        }


        qrscan = new IntentIntegrator(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(SafetyCompletion.this);
        userName = sharedPref.getString("username", "");
        password = sharedPref.getString("password", "");
//        Designation=sharedPref.getString("designation", "");
//        Username=sharedPref.getString("Username", "");
//        LangCode=sharedPref.getString("langcode", "");
        USerCode=sharedPref.getString("usercode", "");
//        Entitycode=sharedPref.getString("entity", "");
        text = sharedPref.getString("Data", "");
        item = sharedPref.getString("Item", "").trim();
        ent = sharedPref.getString("Ent", "").trim();
        maint = sharedPref.getString("Maint", "").trim();
        equip_name = sharedPref.getString("eqname", "");
        alert=sharedPref.getString("message","");
        componentcode=sharedPref.getString("componentcode","");

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

//        hsv.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if(hsv.getScrollX()==0) {
//                    leftNav.setVisibility(View.GONE);
//                }
//                else
//                {
//                    leftNav.setVisibility(View.VISIBLE);
//                }
//                int maxScrollX = hsv.getChildAt(0).getMeasuredWidth()-hsv.getMeasuredWidth();
//                if (hsv.getScrollX()== maxScrollX)
//                {
//                    rightNav.setVisibility(View.GONE);
//                }
//                else
//                {
//                    rightNav.setVisibility(View.VISIBLE);
//                }
//                return false;
//            }
//        });
//        leftNav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                hsv.scrollTo((int)hsv.getScrollX() -150, (int)hsv.getScrollY()) ;
//                rightNav.setVisibility(View.VISIBLE);
//                if(hsv.getScrollX()==0) {
//                    leftNav.setVisibility(View.GONE);
//                }
//                else
//                {
//                    leftNav.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });
//        rightNav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                hsv.scrollTo((int)hsv.getScrollX() + 150, (int)hsv.getScrollY());
//                leftNav.setVisibility(View.VISIBLE);
//                int maxScrollX = hsv.getChildAt(0).getMeasuredWidth()-hsv.getMeasuredWidth();
//                if (hsv.getScrollX()== maxScrollX)
//                {
//                    rightNav.setVisibility(View.GONE);
//                }
//                else
//                {
//                    rightNav.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });



        Pmscomp_text.setTextColor(getResources().getColor(R.color.app_blue));

        Equip_name.setText(equip_name);


        final Calendar mycalender = Calendar.getInstance();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(SafetyCompletion.
                        this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        String mDate=convertDate(convertToMillis(selectedday,selectedmonth,selectedyear));
                        date.setText(mDate);
                    }
                }, mYear, mMonth, mDay);

                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();
            }
            public String convertDate(long mTime) {
                SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
                String formattedDate = df.format(mTime);
                return formattedDate;
            }

            public long convertToMillis(int day, int month, int year) {
                Calendar calendarStart = Calendar.getInstance();
                calendarStart.set(Calendar.YEAR, year);
                calendarStart.set(Calendar.MONTH, month);
                calendarStart.set(Calendar.DAY_OF_MONTH, day);
                return calendarStart.getTimeInMillis();
            }
        });



        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
        date.setText(simpleDateFormat.format(mycalender.getTime()));
        datetime.setText(simpleDateFormat.format(mycalender.getTime()));


        s1.setOnItemSelectedListener(this);
//        s2.setOnItemSelectedListener(this);


        List<String> categories = new ArrayList<String>();
        categories.add("--Select--");
        categories.add("Yes");
        categories.add("No");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SafetyCompletion.this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        s1.setAdapter(dataAdapter);

//        List<String> cat = new ArrayList<String>();
//        cat.add("Ship Staff");
//        cat.add("Shore Staff");
//        cat.add("Dry Dock");
//        ArrayAdapter<String> data = new ArrayAdapter<String>(SafetyCompletion.this, android.R.layout.simple_spinner_dropdown_item, cat);
//        data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        s2.setAdapter(data);



        String[] s=text.split(":");

        String mainNAme="",fre="",last="",next="",due="";
        mainNAme=s[0];
        fre=s[1];
        last=s[2];
        next=s[3];
        mcode=s[4];
        ccode=s[5];
//        String[] mc=mcode.split(":");
//        String[] cc=ccode.split(":");
//        mcode1=mc[1];
//        ccode1=cc[1];

        Maintname.setText(mainNAme);
        Maintname.setTypeface(null, Typeface.BOLD);
        Maintname.setTextColor(Color.parseColor("#303F9F"));

        Frequency.setText(fre);
        Lastdone.setText(last);


        if (alert.equalsIgnoreCase("Success")){
            Maintname.setText(mainNAme);
            Maintname.setTextColor(Color.parseColor("#FF0000"));
            Nextdue.setText(next);
            Nextdue.setTextColor(Color.parseColor("#FF0000"));
        } else if(alert.equalsIgnoreCase("Failed")) {
            Maintname.setText(mainNAme);
            Maintname.setTextColor(Color.parseColor("#303F9F"));
            Nextdue.setText(next);
            Nextdue.setTextColor(Color.parseColor("#303F9F"));
        } else if(alert.equalsIgnoreCase("Null")) {
//            Equip_name.setText("Equipment Name: "+equip_name);
//            Equip_name.setTypeface(null, Typeface.BOLD);
//            Equip_name.setTextColor(Color.parseColor("#3F51B5"));
            Nextdue.setText("");
//            Nextdue.setTextColor(Color.parseColor("#303F9F"));
        }


        registerstatus = "";
        PMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1= String.valueOf(pms.getResources().getColor(R.color.lightgrey));

                if (s1.equalsIgnoreCase("-5855837")){
                    Toast.makeText(SafetyCompletion.this, "Complete this screen and once again click PMS for new Equipment scan", Toast.LENGTH_SHORT).show();

                } else {
                    SharedPreferences pref = getSharedPreferences("Pms", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("qr", "code");
                    edit.commit();
                    Intent i = new Intent(SafetyCompletion.this, Home.class);
                    startActivity(i);
                    finish();
                }
            }
        });
        comp_inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1= String.valueOf(inventory.getResources().getColor(R.color.lightgrey));

                if (s1.equalsIgnoreCase("-5855837")){
                    Toast.makeText(SafetyCompletion.this, "Update this screen before moving to INVENTORY", Toast.LENGTH_SHORT).show();

                } else {
                    SharedPreferences pref = getSharedPreferences("inv", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("qr", "code");
                    edit.commit();
                    Intent i = new Intent(SafetyCompletion.this, Home.class);
                    startActivity(i);
                    finish();
                }
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {

                    String spin1 = s1.getSelectedItem().toString();
                    remark = remsrks.getText().toString();


                    if (spin1.equalsIgnoreCase("--Select--") && remark.equalsIgnoreCase("")) {

                            Toast.makeText(SafetyCompletion.this, "Operational & Remarks is mandatory", Toast.LENGTH_SHORT).show();

                    } else if (spin1.equalsIgnoreCase("--Select--")){
                        Toast.makeText(SafetyCompletion.this, "Select The Operational", Toast.LENGTH_SHORT).show();
                    } else if (remark.equalsIgnoreCase("")){
                        Toast.makeText(SafetyCompletion.this, "Remarks is mandatory", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        new AsyncCheckLogin().execute();
                    }



                } else {
                    Toast.makeText(SafetyCompletion.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        if(exitcode.contains("1"))
        {
            Intent back = new Intent(SafetyCompletion.this, Dueoverdue.class);
            startActivity(back);
            finish();
        }
        else {
            Intent back = new Intent(SafetyCompletion.this, SafetyList.class);
            startActivity(back);
            finish();
        }
    }
    private void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
//        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.icon);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    public class AsyncCheckLogin extends AsyncTask<Void, Void, Void> {
        String error = "";
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(SafetyCompletion.this, "Please wait...","It may take few seconds to Load...", true);

        }

        @Override
        protected Void doInBackground(Void... params) {
            call();
            return null;

        }

        @Override
        protected void onPostExecute (Void aVoid){
            super.onPostExecute(aVoid);
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
            final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();

            if (activeNetwork != null && activeNetwork.isConnected()) {
                if (registerstatus.equalsIgnoreCase("Success")) {

                    int color = Color.parseColor("#00ffffff"); // To hide the purchase,repair,etc.. in home modules
                    pms.setColorFilter(color);
                    inventory.setColorFilter(color);

                    Toast.makeText(SafetyCompletion.this, "Safety Alarm Saved Successfully !", Toast.LENGTH_SHORT).show();

                    if(exitcode.contains("1"))
                    {
                        Intent back = new Intent(SafetyCompletion.this, Dueoverdue.class);
                        startActivity(back);
                        finish();
                    }
                    else {
                        Intent i = new Intent(SafetyCompletion.this, SafetyList.class);
                        startActivity(i);
                        finish();
                    }
                } else if (registerstatus.equalsIgnoreCase("Failed")) {
                    Toast.makeText(SafetyCompletion.this, "Wrong QR Value", Toast.LENGTH_SHORT).show();
                }
//            else  if(registerstatus.equalsIgnoreCase("Success"))
//            {
//
//            }
                else if (registerstatus.equalsIgnoreCase("")) {
                    Toast.makeText(SafetyCompletion.this, strMsg, Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(SafetyCompletion.this, "Successful", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SafetyCompletion.this, "Internet Connection Failed ! Please Try Again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public void call() {
        try {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);
//            qr = value.getText().toString();
//            item = itemcode.getText().toString();
//            ent = entitycode.getText().toString();
            dy = date.getText().toString();
            spin1 = s1.getSelectedItem().toString();
            spin2 = s2.getText().toString();
            remark = remsrks.getText().toString();
            dt = datetime.getText().toString();

//            maint=Main_code.getText().toString();
//            device=deviceID.getText().toString();
//            device=getIntent().getExtras().getString("user");
//            request.addProperty("category", cat);
            request.addProperty("calibrationcode", mcode);
            request.addProperty("entitycode", ent);
            request.addProperty("date", dy);
            request.addProperty("operational", spin1);
            if (spin2.equalsIgnoreCase("")){
                request.addProperty("testvalue", 0);
            } else {
                request.addProperty("testvalue", spin2);
            }
            request.addProperty("remarks", remark);
            request.addProperty("rowusercode", USerCode);
            request.addProperty("componentcode",ccode);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(URL);
            Object response = null;
            try {
                httpTransport.call(SOAP_ACTION, envelope);

                response = envelope.getResponse();

                 strMsg = response.toString();
                if (strMsg.equalsIgnoreCase("Failed")) {
                    // set status Failed
                    registerstatus = "Failed";
                } else if (strMsg.equalsIgnoreCase("Success")){

                    registerstatus = "Success";
                    //surveystat="SurveySuccess";
                } else {
                    registerstatus=strMsg;
                }
            } catch (SoapFault soapFault) {
                Log.i("error", soapFault.getMessage().toString());

            } catch (XmlPullParserException e) {
                Log.i("error", e.getMessage().toString());

            } catch (IOException e) {
                Log.i("error", e.getMessage().toString());

            }
        } catch (Exception e) {
            Log.i("error", e.getMessage().toString());
        }

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);

    }
}


