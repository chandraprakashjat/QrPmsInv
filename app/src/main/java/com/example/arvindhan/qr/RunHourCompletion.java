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
import com.google.zxing.integration.android.IntentResult;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RunHourCompletion extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView leftNav,rightNav,change_img;
    ImageView purchase,repair,finance,safety,inventory,pms;
    HorizontalScrollView hsv;
    public static EditText from_date,from_date1,to_date,to_date1,hours, remsrks,remsrks1,CurrentReading;
    public static TextView scan,Pms_list_texty, value, Com_Date,To_Date, Com_Status, Com_By, itemcode, entitycode, datetime,Category,Main_code,Name,loggedon,User_code;
    TextView Equip_name;
    public static Spinner s1;
    Button b,b1;
    TextView prev_value;
    RelativeLayout relative1;
    Date date,date1,date3,date4,datefrom,dateto,datefrom1,dateto1;
    private int mYear, mMonth, mDay;
    RelativeLayout comp_inv,pms_lay,runhour,counter,Purchase_lay,Repair_lay,Finance_lay,Safety_lay;


    String qr, registerstatus, dy,dy1, qy,qy1, tp, item, ent, hr, dt, remark,remark1, spin1, spin2,maint;
    String ec = "0",geturlvalues;
    String eqname,componentname,eqitem,eqent,takeValue = "";
    String userName, password,USerCode,LangCode,strMsg,date_server,currentReading,currentCounter,previousReading,exitcode;

    private static final String SOAP_ACTION = "http://tempuri.org/qrrunhour";
    private static final String METHOD_NAME = "qrrunhour";
    private static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

    private static final String SOAP_ACTION1 = "http://tempuri.org/runhourlastdonedate";
    private static final String METHOD_NAME1 = "runhourlastdonedate";
    private static final String WSDL_TARGET_NAMESPACE1 = "http://tempuri.org/";
    private static String URL = "";

    sqlconnect sq=new sqlconnect(this);
    ArrayList l1= new ArrayList();

    Float f,p;
    double comp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration configsecond = getResources().getConfiguration();
        if (configsecond.smallestScreenWidthDp >= 600) {
            setContentView(R.layout.activity_run_hour_completion_tab);
        } else {
            setContentView(R.layout.activity_run_hour_completion);
        }

        comp_inv=(RelativeLayout) findViewById(R.id.Pms_list_inv);
//        leftNav = (ImageView) findViewById(R.id.left_nav);
//        rightNav = (ImageView) findViewById(R.id.right_nav);
        change_img = (ImageView) findViewById(R.id.change_img);
//        hsv =(HorizontalScrollView) findViewById(R.id.hsv);
        pms_lay=(RelativeLayout) findViewById(R.id.pms_lay);

        Purchase_lay=(RelativeLayout)findViewById(R.id.purchase_lay);
        Repair_lay=(RelativeLayout)findViewById(R.id.repair_lay);
        Finance_lay=(RelativeLayout)findViewById(R.id.finance_lay);
        Safety_lay=(RelativeLayout)findViewById(R.id.safety_lay);


        runhour=(RelativeLayout) findViewById(R.id.relative1_runhour);
        counter=(RelativeLayout) findViewById(R.id.relative1_runhour2);

        Equip_name = (TextView) findViewById(R.id.eqname_run);
        from_date = (EditText) findViewById(R.id.date);
        from_date1 = (EditText) findViewById(R.id.date1);
        to_date = (EditText) findViewById(R.id.Todate);
        to_date1 = (EditText) findViewById(R.id.Todate1);
        hours = (EditText) findViewById(R.id.hour);
        remsrks = (EditText) findViewById(R.id.remarks);
        remsrks1 = (EditText) findViewById(R.id.remarks1);
        CurrentReading= (EditText) findViewById(R.id.current_reading);
        Pms_list_texty=(TextView) findViewById(R.id.pmslist_text);
        prev_value=(TextView) findViewById(R.id.prev_value);

        Com_Date = (TextView) findViewById(R.id.com_date);
        To_Date = (TextView) findViewById(R.id.To_date);
        Com_Status = (TextView) findViewById(R.id.com_status);
        Com_By = (TextView) findViewById(R.id.com_by);
//        itemcode = (TextView) findViewById(R.id.item);
        entitycode = (TextView) findViewById(R.id.entity);
//        datetime = (TextView) findViewById(R.id.modifieddate);
        Category = (TextView) findViewById(R.id.category);



        s1 = (Spinner) findViewById(R.id.spinner2);
//        s2 = (Spinner) findViewById(R.id.spinner2);
        b = (Button) findViewById(R.id.store);
        b1 = (Button) findViewById(R.id.store1);
//        relative1=(RelativeLayout)findViewById(R.id.relative1_runhour);
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

            pms_lay.setEnabled(true);
            pms_lay.setVisibility(View.VISIBLE);
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



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        SharedPreferences sharedPreferece = getSharedPreferences("URLREG", Context.MODE_PRIVATE);
        geturlvalues = sharedPreferece.getString("url_register", "");
        URL=geturlvalues;

        SharedPreferences sp1 = getSharedPreferences("runhrvalues", Context.MODE_PRIVATE);
        eqname= sp1.getString("eqname","");
        eqitem= sp1.getString("eqitem","");
        eqent=sp1.getString("eqent", "");
        USerCode=sp1.getString("USerCode", "");
        LangCode = sp1.getString("LangCode", "");



        SharedPreferences sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE);
        userName = sharedPref.getString("username", "");
        password = sharedPref.getString("password", "");
        USerCode=sharedPref.getString("usercode", "");


        SharedPreferences pref1 = getSharedPreferences("exitcodes", Context.MODE_PRIVATE);
        exitcode = pref1.getString("exitcode", "");

//        Entitycode=sharedPref.getString("entity", "");
        Pms_list_texty.setTextColor(getResources().getColor(R.color.app_blue));


        Equip_name.setText(eqname);

        SharedPreferences subdate = getSharedPreferences("datevalue", Context.MODE_PRIVATE);
        takeValue= subdate.getString("takeValue","");


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
        pms_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1= String.valueOf(pms.getResources().getColor(R.color.lightgrey));

                if (s1.equalsIgnoreCase("-5855837")){
                    Toast.makeText(RunHourCompletion.this, "Complete this screen and once again click PMS for new Equipment scan", Toast.LENGTH_SHORT).show();

                } else {
                    SharedPreferences pref = getSharedPreferences("Pms", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("qr", "code");
                    edit.commit();
                    Intent i = new Intent(RunHourCompletion.this, Home.class);
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
                    Toast.makeText(RunHourCompletion.this, "Update this screen before moving to INVENTORY", Toast.LENGTH_SHORT).show();

                } else {
                    SharedPreferences pref = getSharedPreferences("inv", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("qr", "code");
                    edit.commit();
                    Intent i = new Intent(RunHourCompletion.this, Home.class);
                    startActivity(i);
                    finish();
                }
            }
        });


        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnected()) {
            new AsyncLastDateValue().execute();
        }else {
            Toast.makeText(RunHourCompletion.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        final Calendar mycalender = Calendar.getInstance();

        from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(RunHourCompletion.
                        this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub

                        String mDate=convertDate(convertToMillis(selectedday,selectedmonth,selectedyear));
                        from_date.setText(mDate);                    }
                }, mYear, mMonth, mDay);
//                mDatePicker.setTitle("Select date");
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

        from_date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(RunHourCompletion.
                        this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub

                        String mDate=convertDate(convertToMillis(selectedday,selectedmonth,selectedyear));
                        from_date1.setText(mDate);                    }
                }, mYear, mMonth, mDay);
//                mDatePicker.setTitle("Select date");
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
        to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(RunHourCompletion.
                        this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub

                        String mDate=convertDate(convertToMillis(selectedday,selectedmonth,selectedyear));
                        to_date.setText(mDate);                       }
                }, mYear, mMonth, mDay);
//                mDatePicker.setTitle("Select date");
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
        to_date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(RunHourCompletion.
                        this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub

                        String mDate=convertDate(convertToMillis(selectedday,selectedmonth,selectedyear));
                        to_date1.setText(mDate);                       }
                }, mYear, mMonth, mDay);
//                mDatePicker.setTitle("Select date");
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
        from_date.setText(simpleDateFormat.format(mycalender.getTime()));
        from_date1.setText(simpleDateFormat.format(mycalender.getTime()));
        to_date.setText(simpleDateFormat.format(mycalender.getTime()));
        to_date1.setText(simpleDateFormat.format(mycalender.getTime()));


        s1.setOnItemSelectedListener(this);

        List<String> categories = new ArrayList<String>();
        categories.add("0");
        categories.add("6");
        categories.add("12");
        categories.add("18");
        categories.add("24");
        categories.add("30");
        categories.add("36");
        categories.add("42");
        categories.add("48");
        categories.add("54");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(RunHourCompletion.this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        s1.setAdapter(dataAdapter);




        registerstatus = "";
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {

                    String spin1 = s1.getSelectedItem().toString();
                    String gethour=hours.getText().toString();
                    String getfrom=from_date.getText().toString();
                    String tofrom=to_date.getText().toString();

                    if (getfrom.equalsIgnoreCase("                        ") && tofrom.equalsIgnoreCase("                        ")){
                        showAlertDialog(RunHourCompletion.this, "Mandatory",
                                "Select The From And To Date !", false);
                    } else if (getfrom.equalsIgnoreCase("                        ")) {
                        showAlertDialog(RunHourCompletion.this, "Mandatory",
                                "Select The From Date !", false);
                    } else if (tofrom.equalsIgnoreCase("                        ")) {
                        showAlertDialog(RunHourCompletion.this, "Mandatory",
                                "Select The To Date !", false);
                    } else {
//                    currentReading=CurrentReading.getText().toString();
                        SimpleDateFormat format10 = new SimpleDateFormat("MM/dd/yyyy");
                        SimpleDateFormat format11 = new SimpleDateFormat("dd/MMM/yyyy");
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
                        Date dt = null;
                        Date dt1 = null;
                        try {
                            dt = format11.parse(getfrom);
                            dt1 = format11.parse(tofrom);
                        } catch (ParseException e) {
                            e.printStackTrace();

                        }
                        String datefield1 = format10.format(dt);
                        String datefield2 = format10.format(dt1);
                        try {

                            datefrom = format10.parse(datefield1);
                            dateto = format10.parse(datefield2);
//                        datefrom=new Date(date3.getTime()+(60 * 60 * 24 * 1000));

                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        String datefield = format11.format(datefrom);
                        String datecomp = format11.format(dateto);


                        if (datefield.matches(datecomp)) {
                            if (gethour.equalsIgnoreCase("")) {
                                showAlertDialog(RunHourCompletion.this, "Mandatory",
                                        "Hour's Field Is Mandatory", false);
                            } else {
                                Float f = Float.parseFloat(gethour);
                                if (f >= 27.0) {
                                    showAlertDialog(RunHourCompletion.this, "Mandatory",
                                            "RunningHour beyond 27 hr per day limit", false);
                                } else {
                                    new AsyncCheckLogin().execute();
                                }
                            }
                        } else if (spin1.matches("") || gethour.matches("")) {
                            showAlertDialog(RunHourCompletion.this, "Mandatory",
                                    "Hour's Field Is Mandatory", false);
                        } else {
                            new AsyncCheckLogin().execute();
                        }
                    }

                } else {
                    Toast.makeText(RunHourCompletion.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {

                    previousReading=prev_value.getText().toString();
                    currentReading=CurrentReading.getText().toString();
                    String getfrom=from_date1.getText().toString();
                    String tofrom=to_date1.getText().toString();

                    if (getfrom.equalsIgnoreCase("                        ") && tofrom.equalsIgnoreCase("                        ")){
                        showAlertDialog(RunHourCompletion.this, "Mandatory",
                                "Select The From And To Date !", false);
                    } else if (getfrom.equalsIgnoreCase("                        ")) {
                        showAlertDialog(RunHourCompletion.this, "Mandatory",
                                "Select The From Date !", false);
                    } else if (tofrom.equalsIgnoreCase("                        ")) {
                        showAlertDialog(RunHourCompletion.this, "Mandatory",
                                "Select The To Date !", false);
                    } else {
//                    currentReading=CurrentReading.getText().toString();
                        SimpleDateFormat format10 = new SimpleDateFormat("MM/dd/yyyy");
                        SimpleDateFormat format11 = new SimpleDateFormat("dd/MMM/yyyy");
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
                        Date dt = null;
                        Date dt1 = null;
                        try {
                            dt = format11.parse(getfrom);
                            dt1 = format11.parse(tofrom);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String datefield1 = format10.format(dt);
                        String datefield2 = format10.format(dt1);
                        try {

                            datefrom1 = format10.parse(datefield1);
                            dateto1 = format10.parse(datefield2);
//                        datefrom=new Date(date3.getTime()+(60 * 60 * 24 * 1000));

                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        String datefield = format11.format(datefrom1);
                        String datecomp = format11.format(dateto1);

                        if (datefield.matches(datecomp)) {
                            if (currentReading.equalsIgnoreCase("")) {
                                showAlertDialog(RunHourCompletion.this, "Mandatory",
                                        "Current Reading Field Is Mandatory", false);
                            } else {
                                f = Float.parseFloat(currentReading);
                                p = Float.parseFloat(previousReading);
                                comp = p + 27.0;
                                if (f < p) {
                                    showAlertDialog(RunHourCompletion.this, "Mandatory",
                                            "Current Counter Should Greater Than The Previous Counter", false);
                                } else if (f >= comp) {
                                    showAlertDialog(RunHourCompletion.this, "Mandatory",
                                            "RunningHour beyond 27 hr per day limit", false);
                                } else {
                                    new AsyncCheckLogin().execute();
                                }

                            }
                        } else if (currentReading.matches("")) {
                            showAlertDialog(RunHourCompletion.this, "Mandatory",
                                    "Current Reading Field Is Mandatory", false);

                        } else if (Float.parseFloat(currentReading) < Float.parseFloat(previousReading)) {
                            showAlertDialog(RunHourCompletion.this, "Mandatory",
                                    "Current Counter Should Greater Than The Previous Counter", false);
                        } else if(datefrom1.after(dateto1)){
                            showAlertDialog(RunHourCompletion.this, "Mandatory",
                                    "To Date Should Be Greater Than From Date", false);
                        }
                        else {
                            new AsyncCheckLogin().execute();
                        }
                    }

                } else {
                    Toast.makeText(RunHourCompletion.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        change_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialogView();
            }
        });
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

    private void AlertDialogView() {
        final CharSequence[] items = { "Running Hours", "Counter Reading" };

        AlertDialog.Builder builder = new AlertDialog.Builder(RunHourCompletion.this);
        builder.setTitle("Category Type");
        builder.setSingleChoiceItems(items, -1,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
//                        Toast.makeText(getApplicationContext(), items[item],
//                                Toast.LENGTH_SHORT).show();
                        String type=String.valueOf(items[item]);

                        switch (type){
                            case "Running Hours":
                                takeValue="1";
                                break;
                            case "Counter Reading":
                                takeValue="2";
                                break;
                        }

                    }
                });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (takeValue.equalsIgnoreCase("1")){


                    SharedPreferences pref = getSharedPreferences("datevalue", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("takeValue", takeValue);
                    edit.commit();
                    Intent i=new Intent(RunHourCompletion.this,RunHourCompletion.class);
                    startActivity(i);
                    finish();


                } else if (takeValue.equalsIgnoreCase("2")) {
//                    counter.setVisibility(View.VISIBLE);
//                    runhour.setVisibility(View.INVISIBLE);

                    SharedPreferences pref = getSharedPreferences("datevalue", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("takeValue", takeValue);
                    edit.commit();
                    Intent j=new Intent(RunHourCompletion.this,RunHourCompletion.class);
                    startActivity(j);
                    finish();

                }

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(RunHourCompletion.this, "Fail", Toast.LENGTH_SHORT)
                        .show();
            }
        });

       AlertDialog alert = builder.create();
        alert.show();
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
            Intent back = new Intent(RunHourCompletion.this, Dueoverdue.class);
            startActivity(back);
            finish();
        }
        else {
            Intent back = new Intent(RunHourCompletion.this, HomeSub.class);
            startActivity(back);
            finish();
        }

    }


    public class AsyncCheckLogin extends AsyncTask<Void, Void, Void> {
        String error = "";
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(RunHourCompletion.this, "Please wait...","It may take few seconds to Load...", true);

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
                    Toast.makeText(RunHourCompletion.this, "Run Hour Saved Successfully !", Toast.LENGTH_SHORT).show();
                    if(exitcode.contains("1"))
                    {
                        Intent back = new Intent(RunHourCompletion.this, Dueoverdue.class);
                        startActivity(back);
                        finish();
                    }
                    else {
                        Intent i = new Intent(RunHourCompletion.this, HomeSub.class);
                        startActivity(i);
                        finish();
                    }
                } else if (registerstatus.equalsIgnoreCase("Failed")) {
                    Toast.makeText(RunHourCompletion.this, "Wrong QR Value", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RunHourCompletion.this, strMsg, Toast.LENGTH_SHORT).show();
                }
//            else  if(registerstatus.equalsIgnoreCase("Success"))
//            {
//
//            }

            } else {
                Toast.makeText(RunHourCompletion.this, "Internet Connection Failed ! Please Try Again", Toast.LENGTH_SHORT).show();
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
            dy = from_date.getText().toString();
            dy1 = from_date1.getText().toString();
            qy = to_date.getText().toString();
            qy1 = to_date1.getText().toString();
            hr=hours.getText().toString();
            spin1 = s1.getSelectedItem().toString();
            remark = remsrks.getText().toString();
            remark1 = remsrks1.getText().toString();
            currentReading=CurrentReading.getText().toString();
            previousReading=prev_value.getText().toString();

//            dt = datetime.getText().toString();
//            maint=Main_code.getText().toString();

            request.addProperty("componentcode", eqitem);
            request.addProperty("entitycode", eqent);
            request.addProperty("usercode", USerCode);
            request.addProperty("takevalue", takeValue);

            if (takeValue.equalsIgnoreCase("1")) {
                request.addProperty("minutes", spin1);
                request.addProperty("hour", hr);
                request.addProperty("fromdate", dy);
                request.addProperty("todate", qy);
                request.addProperty("remarks", remark);
            } else if (takeValue.equalsIgnoreCase("2")){
                request.addProperty("currentreading", currentReading);
                request.addProperty("previousReading", previousReading);
                request.addProperty("fromdate", dy1);
                request.addProperty("todate", qy1);
                request.addProperty("remarks", remark1);
            }


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
                } else if(strMsg.equalsIgnoreCase("Success")) {

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

    public class AsyncLastDateValue extends AsyncTask<Void, Void, Void> {
        String error = "";
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(RunHourCompletion.this, "Please wait...","It may take few seconds to Load...", true);

        }

        @Override
        protected Void doInBackground(Void... params) {
            calldate();
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
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

                    String[] seperate = strMsg.split(",");
                    String[] s = seperate[0].split(" ");
                    date_server = s[0];
                    currentCounter = seperate[1];
                    String lastdate;

                    if (date_server.equalsIgnoreCase("")) {
                        String todate = "                        ";
                        lastdate = "                        ";
                        to_date.setText(todate);
                        to_date1.setText(todate);
                    } else {
                        SimpleDateFormat format5 = new SimpleDateFormat("MM/dd/yyyy");
                        SimpleDateFormat format6 = new SimpleDateFormat("dd/MMM/yyyy");
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
                        try {
                            date = format5.parse(date_server);
                            date1 = new Date(date.getTime() + (60 * 60 * 24 * 1000));
//                    date = DateUtil.addDays(date, 1);

                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        lastdate = format6.format(date1);
                    }

                    from_date.setText(lastdate);
                    from_date1.setText(lastdate);
                    if (takeValue.equalsIgnoreCase("1")) {
                        runhour.setVisibility(View.VISIBLE);
                        counter.setVisibility(View.INVISIBLE);
                    } else if (takeValue.equalsIgnoreCase("2")) {
                        counter.setVisibility(View.VISIBLE);
                        runhour.setVisibility(View.INVISIBLE);
                        prev_value.setText(currentCounter);
                    }


                } else if (registerstatus.equalsIgnoreCase("Failed")) {
                    Toast.makeText(RunHourCompletion.this, "Wrong QR Value", Toast.LENGTH_SHORT).show();
                } else if (registerstatus.equalsIgnoreCase("")) {
                    Toast.makeText(RunHourCompletion.this, strMsg, Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(RunHourCompletion.this, "Successful", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RunHourCompletion.this, "Internet Connection Failed ! Please Try Again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void calldate() {
     try {
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE1, METHOD_NAME1);

        request.addProperty("componentcode", eqitem.trim());
        request.addProperty("entitycode", eqent.trim());

         SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(URL);
        Object response = null;
        try {
            httpTransport.call(SOAP_ACTION1, envelope);

            response = envelope.getResponse();

            strMsg = response.toString();
            if (strMsg.equalsIgnoreCase("Failed")) {
                // set status Failed
                registerstatus = "Failed";
            } else {

                registerstatus = "Success";
                //surveystat="SurveySuccess";
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
//
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

