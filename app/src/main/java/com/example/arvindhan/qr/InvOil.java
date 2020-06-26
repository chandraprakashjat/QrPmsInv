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
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arvindhan.parameter.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class InvOil extends AppCompatActivity {

    ImageView leftNav,rightNav;
    ImageView purchase,repair,finance,safety,inventory,pms;
    HorizontalScrollView hsv;
    RelativeLayout R_pms,Main_Done,Pms_list_inv,Purchase_lay,Repair_lay,Finance_lay,Safety_lay;

    Button store,rec;
    TextView consumption,receipt,InvText;
    public static EditText day,qty,unitcost,Remarks;
    public static TextView oilvalue,qrvalue,type,itemcode,entitycode,categorycode,datetime,deviceID,recon,TypeOfCategory,quantity,Logout,name,loggedon,RobValue;
    public static TextView RobText,unitText,dateText,quantityText;
    private IntentIntegrator qrscan;
    String qr,registerstatus,dy,qy,tp,item,ent,cat,dt,device,recorcons,s1,rob;
    String Designation,Username,LangCode,UserCode,Entitycode;
    String ec="2",itemId,eqent;
    String userName,password,UnitCost="0",remarks,s,qrs,oilcontent,geturlvalues,finalvalue;
    private int mYear,mMonth,mDay;
    double r;
    float q;
    RelativeLayout relative1;
    TableRow tableRow2;



    private static final String SOAP_ACTION1 = "http://tempuri.org/qrcode";
    private static final String METHOD_NAME1 = "qrcode";
    private static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    private static  String URL = "",qrval,qrval1;


    private static final String SOAP_ACTION2 = "http://tempuri.org/roboil";
    private static final String METHOD_NAME2 = "roboil";
    private static final String SOAP_ACTION3 = "http://tempuri.org/robparts";
    private static final String METHOD_NAME3 = "robparts";
    private static final String SOAP_ACTION4 = "http://tempuri.org/robstore";
    private static final String METHOD_NAME4 = "robstore";
//    private static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
//    private static final String URL2 = "http://203.154.162.66/mobileappsdb/WebService1.asmx";
    sqlconnect sq=new sqlconnect(this);
    ArrayList l1= new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration config = getResources().getConfiguration();
        if (config.smallestScreenWidthDp >= 600) {
            setContentView(R.layout.activity_inv_oil_tab);
        } else {
            setContentView(R.layout.activity_inv_oil);
        }

        SharedPreferences sharedPreferences = getSharedPreferences("URLREG", Context.MODE_PRIVATE);
        geturlvalues = sharedPreferences.getString("url_register", "");
        URL=geturlvalues;

//        leftNav = (ImageView) findViewById(R.id.left_nav);
//        rightNav = (ImageView) findViewById(R.id.right_nav);
//        hsv =(HorizontalScrollView) findViewById(R.id.hsv);
        R_pms=(RelativeLayout)findViewById(R.id.pms_lay);
        Pms_list_inv=(RelativeLayout)findViewById(R.id.Pms_list_inv);

        Purchase_lay=(RelativeLayout)findViewById(R.id.purchase_lay);
        Repair_lay=(RelativeLayout)findViewById(R.id.repair_lay);
        Finance_lay=(RelativeLayout)findViewById(R.id.finance_lay);
        Safety_lay=(RelativeLayout)findViewById(R.id.safety_lay);

        InvText=(TextView) findViewById(R.id.inv_text);

        receipt = (TextView) findViewById(R.id.receipt);
        consumption = (TextView) findViewById(R.id.consumption);
        qty = (EditText) findViewById(R.id.quantity);
        day = (EditText) findViewById(R.id.date_inv);
        unitcost = (EditText) findViewById(R.id.unit_cost);
        Remarks = (EditText) findViewById(R.id.remarks);
        store = (Button) findViewById(R.id.storecon);
        rec = (Button) findViewById(R.id.storerec);
        oilvalue = (TextView) findViewById(R.id.oilcontents);

        categorycode=(TextView)findViewById(R.id.category);
        itemcode=(TextView)findViewById(R.id.itemid);
        entitycode=(TextView)findViewById(R.id.entity);
        deviceID=(TextView)findViewById(R.id.deviceid);
        recon=(TextView)findViewById(R.id.recorcon);
        quantity=(TextView)findViewById(R.id.qty);
        type = (TextView) findViewById(R.id.type);
        Logout = (TextView) findViewById(R.id.logout);

        datetime = (TextView) findViewById(R.id.datetime);
        RobValue = (TextView) findViewById(R.id.robvalue);
        RobText = (TextView) findViewById(R.id.text_rob);
        unitText = (TextView) findViewById(R.id.text_unit);
        dateText = (TextView) findViewById(R.id.text_date);
        relative1=(RelativeLayout)findViewById(R.id.relative1);
        tableRow2=(TableRow)findViewById(R.id.tableRow2);

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
            Pms_list_inv.setEnabled(true);
            Pms_list_inv.setVisibility(View.VISIBLE);
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


//        quantityText = (TextView) findViewById(R.id.text_quantity);
        qrscan = new IntentIntegrator(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        InvText.setTextColor(getResources().getColor(R.color.app_blue));

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(InvOil.this);
        userName = sharedPref.getString("username", "");
        password = sharedPref.getString("password", "");
        Designation=sharedPref.getString("designation", "");
        Username=sharedPref.getString("Username", "");
        LangCode=sharedPref.getString("langcode", "");
        UserCode=sharedPref.getString("usercode", "");
        Entitycode=sharedPref.getString("entity", "");

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        SharedPreferences pref = getSharedPreferences("oil", Context.MODE_PRIVATE);
        qrval1 = pref.getString("values", "");
        oilcontent=qrval1;

        oilvalue.setText(oilcontent);

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
        R_pms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1= String.valueOf(pms.getResources().getColor(R.color.lightgrey));

                if (s1.equalsIgnoreCase("-5855837")){
                    Toast.makeText(InvOil.this, "Update this screen before moving to PMS", Toast.LENGTH_SHORT).show();

                } else {
                    SharedPreferences pref = getSharedPreferences("Pms", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("qr", "code");
                    edit.commit();
                    Intent inn = new Intent(InvOil.this, Home.class);
                    startActivity(inn);
                    finish();
                }


            }
        });
        Pms_list_inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1= String.valueOf(inventory.getResources().getColor(R.color.lightgrey));

                if (s1.equalsIgnoreCase("-5855837")){
                    Toast.makeText(InvOil.this, "Complete this screen and once again click INVENTORY for new scan", Toast.LENGTH_SHORT).show();

                } else {
                    SharedPreferences pref = getSharedPreferences("inv", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("qr", "code");
                    edit.commit();

                    Intent inn = new Intent(InvOil.this, Home.class);
                    startActivity(inn);
                    finish();
                }
            }
        });


        registerstatus = "";
        device="";
        receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TypeOfCategory.setText("RECEIPT");
//                TypeOfCategory.setVisibility(View.VISIBLE);
                SharedPreferences sharepref = getSharedPreferences("qr", Context.MODE_PRIVATE);
                qrval = sharepref.getString("values", "");
                itemId = sharepref.getString("eqitem", "").trim();
                eqent= sharepref.getString("eqent", "").trim();

                qrs=qrval;
                qty.getText().clear();
                unitcost.getText().clear();


                if (qrs.contains("UNIQUEKEY :") || qrs.contains("Entity :") || qrs.contains("Category :")) {

                    recon.setVisibility(View.INVISIBLE);
                consumption.setTypeface(null, Typeface.NORMAL);
                receipt.setTextColor(getResources().getColor(R.color.white));
                consumption.setTextColor(getResources().getColor(R.color.black));
                String text_view_str1 = "Consumption";
                consumption.setText(text_view_str1);
                String text_view_str = "<u>Receipt</u>";
                receipt.setText(Html.fromHtml(text_view_str));

                recon.setText("receipt");
                    type.setText("2");



                relative1.setVisibility(View.VISIBLE);
                tableRow2.setVisibility(View.VISIBLE);
                rec.setVisibility(View.VISIBLE);
                store.setVisibility(View.GONE);

                    String[] category = qrs.split("Category :");
                    String[] content = category[1].split("\\n");
                    categorycode.setText(content[0]);

                        new AsyncCheckRob().execute();

                } else {
                    Toast.makeText(InvOil.this, "QR Field Name Mis-Matched", Toast.LENGTH_SHORT).show();
                }

            }
        });
        consumption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                TypeOfCategory.setText("CONSUMPTION");
//                TypeOfCategory.setVisibility(View.VISIBLE);
                SharedPreferences sharepref = getSharedPreferences("qr", Context.MODE_PRIVATE);
                qrval = sharepref.getString("values", "");
                itemId = sharepref.getString("eqitem", "").trim();
                eqent= sharepref.getString("eqent", "").trim();
                qrs=qrval;

                qty.getText().clear();
                if (qrs.contains("UNIQUEKEY :") || qrs.contains("Entity :") || qrs.contains("Category :")) {
                recon.setVisibility(View.INVISIBLE);
//                consumption.setTypeface(consumption.getTypeface(), Typeface.BOLD);
                consumption.setTextAppearance(InvOil.this, R.style.TitleColor);
                receipt.setTypeface(null,Typeface.NORMAL);
                consumption.setTextColor(getResources().getColor(R.color.white));
                receipt.setTextColor(getResources().getColor(R.color.black));
                String text_view_str1 = "Receipt";
                receipt.setText(text_view_str1);
                String text_view_str = "<u>Consumption</u>";
                consumption.setText(Html.fromHtml(text_view_str));



                recon.setText("consumption");

                type.setText("1");

                relative1.setVisibility(View.VISIBLE);
                tableRow2.setVisibility(View.GONE);
                rec.setVisibility(View.GONE);
                store.setVisibility(View.VISIBLE);

                String[] category=qrs.split("Category :");
                String[] content=category[1].split("\\n");
                categorycode.setText(content[0]);

                        new AsyncCheckRob().execute();


                } else {
                    Toast.makeText(InvOil.this, "QR Field Name Mis-Matched", Toast.LENGTH_SHORT).show();
                }

            }
        });

        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {
                    final Calendar mycalender = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
                    String getfrom=simpleDateFormat.format(mycalender.getTime());
                    String tofrom=day.getText().toString();

                    SimpleDateFormat format11 = new SimpleDateFormat("dd/MMM/yyyy");
                    Date dt = null;
                    Date dt1 = null;
                    try {
                        dt = format11.parse(getfrom);
                        dt1 = format11.parse(tofrom);
                    } catch (ParseException e) {
                        e.printStackTrace();

                    }

                    String s=qty.getText().toString();
                    if (s.matches("")){
                        showAlertDialog(InvOil.this, "Mandatory",
                                "Quantity cannot be empty", false);
                    } else if (dt1.compareTo(dt) > 0){
                        showAlertDialog(InvOil.this, "Alert !!",
                                "Future Date cannot be Accepted", false);
                    } else {

                        quantity.setText(s);
                        q=Float.parseFloat(quantity.getText().toString());
                        if (q >= r)
                        {
                            showAlertDialog(InvOil.this, "Mandatory",
                                    "Quantity Value Should Be Less Than ROB", false);
                        } else {

                            new AsyncCheckLogin().execute();
                        }
                    }

                } else {
                    Toast.makeText(InvOil.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                }

            }
        });
        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent j=new Intent(MainActivity.this,ReceiptActivity.class);
//                startActivity(j);
                final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {
                    final Calendar mycalender = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
                    String getfrom=simpleDateFormat.format(mycalender.getTime());
                    String tofrom=day.getText().toString();

                    SimpleDateFormat format11 = new SimpleDateFormat("dd/MMM/yyyy");
                    Date dt = null;
                    Date dt1 = null;
                    try {
                        dt = format11.parse(getfrom);
                        dt1 = format11.parse(tofrom);
                    } catch (ParseException e) {
                        e.printStackTrace();

                    }


                    String s=qty.getText().toString();
                    if (s.matches("") && unitcost.getText().toString().equalsIgnoreCase("")) {
                        showAlertDialog(InvOil.this, "Mandatory",
                                "Quantity & Unit Cost cannot be empty", false);
                    }
                    else if (s.matches("")){
                        showAlertDialog(InvOil.this, "Mandatory",
                                "Quantity cannot be empty", false);
                    } else if(unitcost.getText().toString().equalsIgnoreCase("")) {
                        showAlertDialog(InvOil.this, "Mandatory",
                                "Unit Cost cannot be empty", false);
                    }  else if (dt1.compareTo(dt) > 0){
                        showAlertDialog(InvOil.this, "Alert !!",
                                "Future Date cannot be Accepted", false);
                    } else {

                        quantity.setText(s);

                        new AsyncCheckLogin().execute();


                    }

                } else {
                    Toast.makeText(InvOil.this, "No internet connection", Toast.LENGTH_SHORT).show();
                }

            }
        });
        final Calendar mycalender = Calendar.getInstance();
        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(InvOil.
                        this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        String mDate=convertDate(convertToMillis(selectedday,selectedmonth,selectedyear));
                        day.setText(mDate);
                        day.setTextColor(Color.parseColor("#000000"));
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
        day.setText(simpleDateFormat.format(mycalender.getTime()));
        datetime.setText(simpleDateFormat.format(mycalender.getTime()));

    }

    private void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setTitle(title);

        alertDialog.setMessage(message);

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alertDialog.show();
    }


    public class AsyncCheckLogin extends AsyncTask<Void, Void, Void> {
        String error="";
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog=ProgressDialog.show(InvOil.this, "Please wait...","It may take few seconds to Load...",true);

        }

        @Override
        protected Void doInBackground(Void... params) {
            call();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(mProgressDialog != null)
            {
                if(mProgressDialog.isShowing())
                {
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

                    if (tp == "1") {
                        if (qrs.contains("Category : Oil")) {
                            Toast.makeText(InvOil.this, "Oil Consumption Saved Successfully !", Toast.LENGTH_SHORT).show();
                        } else if (qrs.contains("Category : Part")) {
                            Toast.makeText(InvOil.this, "Part Consumption Saved Successfully !", Toast.LENGTH_SHORT).show();
                        } else if (qrs.contains("Category : Store")) {
                            Toast.makeText(InvOil.this, "Store Consumption Saved Successfully !", Toast.LENGTH_SHORT).show();
                        }
                        Intent i = new Intent(InvOil.this, InvOil.class);
                        startActivity(i);
                        finish();

                    } else {
                        if (qrs.contains("Category : Oil")) {
                            Toast.makeText(InvOil.this, "Oil Receipt Saved Successfully !", Toast.LENGTH_SHORT).show();
                        } else if (qrs.contains("Category : Part")) {
                            Toast.makeText(InvOil.this, "Part Receipt Saved Successfully !", Toast.LENGTH_SHORT).show();
                        } else if (qrs.contains("Category : Store")) {
                            Toast.makeText(InvOil.this, "Store Receipt Saved Successfully !", Toast.LENGTH_SHORT).show();
                        }

                        Intent j = new Intent(InvOil.this, InvOil.class);
                        startActivity(j);
                        finish();
                    }
                } else if (registerstatus.equalsIgnoreCase("Failed")) {
                    Toast.makeText(InvOil.this, "Wrong QR Value", Toast.LENGTH_SHORT).show();
                }
//            else  if(registerstatus.equalsIgnoreCase("Success"))
//            {
//
//            }
                else if (registerstatus.equalsIgnoreCase("")) {
                    Toast.makeText(InvOil.this, "Server not connected ! Try after sometimes", Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(InvOil.this, s, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(InvOil.this, "Internet Connection Failed ! Please Try Again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void call() {
        try{
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME1);
            dy=day.getText().toString();
            qy=quantity.getText().toString();
            tp=type.getText().toString();
            item=itemcode.getText().toString().trim();
            ent=entitycode.getText().toString().trim();
            cat=categorycode.getText().toString().trim();
            dt=datetime.getText().toString();
            UnitCost=unitcost.getText().toString();
            remarks=Remarks.getText().toString();

            request.addProperty("category",cat);
            request.addProperty("itemid",itemId);
            request.addProperty("entitycode",eqent);
            request.addProperty("quantity",qy);
            request.addProperty("date",dy);
            request.addProperty("type",tp);
            if (tp=="2"){
                request.addProperty("unitcost", UnitCost);
            }
            request.addProperty("usercode",UserCode);
            request.addProperty("remarks", remarks);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(URL);
            Object response=null;
            try {
                httpTransport.call(SOAP_ACTION1, envelope);

                response = envelope.getResponse();

                s=response.toString();
                if (s.equalsIgnoreCase("Failed"))
                {
                    registerstatus="Failed";
                }
                else if (s.equalsIgnoreCase("Success"))
                {

                    registerstatus="Success";
                } else {
                    registerstatus=s;
                }
            } catch (SoapFault soapFault) {
                Log.i("error", soapFault.getMessage().toString());

            } catch (XmlPullParserException e) {
                Log.i("error", e.getMessage().toString());

            } catch (IOException e) {
                Log.i("error", e.getMessage().toString());

            }
        } catch (Exception e){
            Log.i("error", e.getMessage().toString());
        }

    }

    private class AsyncCheckRob extends  AsyncTask {

        String error="";
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog=ProgressDialog.show(InvOil.this, "Please wait...","It may take few seconds to Load...",true);

        }

        @Override
        protected Object doInBackground(Object[] params) {
            callFunc();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            if(mProgressDialog != null)
            {
                if(mProgressDialog.isShowing())
                {
                    mProgressDialog.dismiss();
                }
            }
            final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();

            if (activeNetwork != null && activeNetwork.isConnected()) {
                if (device.equalsIgnoreCase("Success")) {
                    RobText.setVisibility(View.VISIBLE);
                    RobValue.setVisibility(View.VISIBLE);
                    RobValue.setText(s1);
                    rob = RobValue.getText().toString();
                    r = Double.parseDouble(String.format("%.2f", new BigDecimal(rob)));

                } else if (device.equalsIgnoreCase("Failed")) {
                    Toast.makeText(InvOil.this, s1, Toast.LENGTH_SHORT).show();
                } else if (device.equalsIgnoreCase("")) {
                    Toast.makeText(InvOil.this, "Server not connected ! Try after sometimes", Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(InvOil.this, "Retry", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(InvOil.this, "Internet Connection Failed ! Please Try Again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void callFunc() {
        try{
            if (qrs.contains("Category : Oil") || qrs.contains("Category : Oil")  ) {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME2);

            item=itemcode.getText().toString().trim();
            ent=entitycode.getText().toString().trim();

                request.addProperty("oilcode", itemId);
                request.addProperty("entitycode", eqent);
                request.addProperty("languagecode", LangCode);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE httpTransport = new HttpTransportSE(URL);
                Object response=null;
                try {

                    httpTransport.call(SOAP_ACTION2, envelope);


                    response = envelope.getResponse();

                    s1=response.toString();
                    if (s1.equalsIgnoreCase("Failed"))
                    {
                        device="Failed";
                    }
                    else
                    {
                        device="Success";
                    }
                } catch (SoapFault soapFault) {
                    Log.i("error", soapFault.getMessage().toString());

                } catch (XmlPullParserException e) {
                    Log.i("error", e.getMessage().toString());

                } catch (IOException e) {
                    Log.i("error", e.getMessage().toString());

                }


            }
            else if(qrs.contains("Category : Part"))
            {
                SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME3);

                item=itemcode.getText().toString().trim();
                ent=entitycode.getText().toString().trim();
                request.addProperty("partcode", itemId);
                request.addProperty("entitycode", eqent);
                request.addProperty("languagecode", LangCode);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE httpTransport = new HttpTransportSE(URL);
                Object response=null;
                try {

                    httpTransport.call(SOAP_ACTION3, envelope);

                    response = envelope.getResponse();

                    s1=response.toString();
                    if (s1.equalsIgnoreCase("Failed"))
                    {
                        // set status Failed
                        device="Failed";
                    }
                    else
                    {

                        device="Success";
                        //surveystat="SurveySuccess";
                    }
                } catch (SoapFault soapFault) {
                    Log.i("error", soapFault.getMessage().toString());

                } catch (XmlPullParserException e) {
                    Log.i("error", e.getMessage().toString());

                } catch (IOException e) {
                    Log.i("error", e.getMessage().toString());

                }

            }
            else if(qrs.contains("Category : Store"))
            {
                SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME4);

                item=itemcode.getText().toString().trim();
                ent=entitycode.getText().toString().trim();
                request.addProperty("storecode", itemId);
                request.addProperty("entitycode", eqent);
                request.addProperty("languagecode", LangCode);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE httpTransport = new HttpTransportSE(URL);
                Object response=null;
                try {

                    httpTransport.call(SOAP_ACTION4, envelope);


                    response = envelope.getResponse();

                    s1=response.toString();
                    if (s1.equalsIgnoreCase("Failed"))
                    {
                        // set status Failed
                        device="Failed";
                    }
                    else
                    {

                        device="Success";
                        //surveystat="SurveySuccess";
                    }
                } catch (SoapFault soapFault) {
                    Log.i("error", soapFault.getMessage().toString());

                } catch (XmlPullParserException e) {
                    Log.i("error", e.getMessage().toString());

                } catch (IOException e) {
                    Log.i("error", e.getMessage().toString());

                }

            }
        } catch (Exception e){
            Log.i("error", e.getMessage().toString());

        }


    }
    @Override
    public void onBackPressed() {
        Intent back = new Intent(InvOil.this, Home.class);
        startActivity(back);
        finish();
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {  // This is used to hide keypad where we touch in the screen instead of back button , and is used for edittext
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
