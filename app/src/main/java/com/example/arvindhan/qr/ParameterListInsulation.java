package com.example.arvindhan.qr;

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
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.arvindhan.parameter.R;

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

/**
 * Created by Arvindhan on 11/10/2017.
 */

public class ParameterListInsulation extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ImageView leftNav,rightNav;
    ImageView purchase,repair,finance,safety;
    HorizontalScrollView hsv;
    String eqname,componentname,eqitem,eqent;
    public static TextView Equipment_name, Name, loggedon, User_code,Pms_list_texty,Title_parameter;

    RelativeLayout R_pms,Main_Done,Pms_list_inv,Purchase_lay,Repair_lay,Finance_lay,Safety_lay;

    SearchView searchView;

    ImageView more;
    String qr, registerstatus;
    String ec = "0";
    String userName, password, USerCode,LangCode, strMsg,equip_name,geturlvalues;


    private ListView listview,m_list;
    String getvalues;

    private ArrayList<ArrayList<String>> data,m_data;
    private ArrayList<String> list;
    private ArrayList<String> m_code_list;
    ArrayAdapter<String> sd,md;

    String maintanance = "", days = "", donedate = "", due = "",equipname="",doneeg="",dueeg="",maintenancecode="",componentcode="";
    String[] cut;
    String Msg="",takeValue;
    Date date1=null,date2=null;


    private static final String SOAP_ACTION = "http://tempuri.org/parameterlist";
    private static final String METHOD_NAME = "parameterlist";
    private static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    private static  String URL = "";

    ArrayList<String> l1=new ArrayList<String>();
    ArrayList<String> l2=new ArrayList<String>();
    ArrayList<String> l3=new ArrayList<String>();
    ArrayList<String> l4=new ArrayList<String>();
    ArrayList<String> l5=new ArrayList<String>();
    ArrayList<String> l6=new ArrayList<String>();

    sqlconnect sq=new sqlconnect(this);
    ArrayList lnew= new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration config = getResources().getConfiguration();
        if (config.smallestScreenWidthDp >= 600) {
            setContentView(R.layout.activity_parameter_list_tab);
        } else {
            setContentView(R.layout.activity_parameter_list);
        }

//        leftNav = (ImageView) findViewById(R.id.left_nav);
//        rightNav = (ImageView) findViewById(R.id.right_nav);
//        hsv =(HorizontalScrollView) findViewById(R.id.hsv);

        Equipment_name=(TextView) findViewById(R.id.equipname);
        Pms_list_texty=(TextView) findViewById(R.id.pmslist_text);
        R_pms=(RelativeLayout)findViewById(R.id.pms_lay);
        Main_Done=(RelativeLayout)findViewById(R.id.main_done);
        Pms_list_inv=(RelativeLayout)findViewById(R.id.Pms_list_inv);

        Purchase_lay=(RelativeLayout)findViewById(R.id.purchase_lay);
        Repair_lay=(RelativeLayout)findViewById(R.id.repair_lay);
        Finance_lay=(RelativeLayout)findViewById(R.id.finance_lay);
        Safety_lay=(RelativeLayout)findViewById(R.id.safety_lay);

        more=(ImageView)findViewById(R.id.more);
        Title_parameter=(TextView) findViewById(R.id.title_parameter);

        searchView=(SearchView)findViewById(R.id.searchview);


        listview = (ListView) findViewById(R.id.list1);
        m_list=  (ListView) findViewById(R.id.list_mcode1);

        purchase=(ImageView)findViewById(R.id.img_crw1);
        repair=(ImageView)findViewById(R.id.img_trn1);
        finance=(ImageView)findViewById(R.id.img_alm1);
        safety=(ImageView)findViewById(R.id.img_cal1);

        int color = Color.parseColor("#A6A5A3"); // To hide the purchase,repair,etc.. in home modules
        purchase.setColorFilter(color);
        repair.setColorFilter(color);
        finance.setColorFilter(color);
        safety.setColorFilter(color);

        lnew=sq.Getid();
        String splt=lnew.get(0).toString();

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


        data = new ArrayList<>();
        m_data = new ArrayList<>();
        list = new ArrayList<String>();
        m_code_list = new ArrayList<String>();
        sd = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);
        md = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, m_code_list);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ParameterListInsulation.this);
        userName = sharedPref.getString("username", "");
        password = sharedPref.getString("password", "");
        USerCode = sharedPref.getString("usercode", "");

        Pms_list_texty.setTextColor(getResources().getColor(R.color.app_blue));


        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        SharedPreferences sharepref = getSharedPreferences("URLREG", Context.MODE_PRIVATE);
        geturlvalues = sharepref.getString("url_register", "");
        URL=geturlvalues;

        SharedPreferences sp1 = getSharedPreferences("paramvalues", Context.MODE_PRIVATE);
        eqname= sp1.getString("eqname","");
        eqitem= sp1.getString("eqitem","");
        eqent=sp1.getString("eqent", "");
        USerCode=sp1.getString("USerCode", "");
        LangCode = sp1.getString("LangCode", "");

        SharedPreferences pref1 = getSharedPreferences("exitcodes", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit1 = pref1.edit();
        edit1.putString("exitcode","0");
        edit1.commit();

        Title_parameter.setText("INSULATION");

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


        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnected()) {
            new AsyncCheckLogin().execute();
        }else {
            Toast.makeText(ParameterListInsulation.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

//        final ParameterCustom adapter = new ParameterCustom(ParameterList.this, l1,l2,l3,l4,l5,l6);
//        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txtTitle = (TextView) view.findViewById(R.id.scan_custom);
                TextView txt1Title = (TextView) view.findViewById(R.id.freq_custom);
                TextView txtTitle1 = (TextView) view.findViewById(R.id.lastdone_custom);
                TextView txtTitle2 = (TextView) view.findViewById(R.id.nextdue_custom);
                TextView txtTitle3 = (TextView) view.findViewById(R.id.maintenancecode_custom);
                TextView txtTitle4 = (TextView) view.findViewById(R.id.componentcode_custom);

                String data=(txtTitle.getText().toString()+":"+txt1Title.getText().toString()+":"+txtTitle1.getText().toString()+":"+txtTitle2.getText().toString()+":"+txtTitle3.getText().toString()+":"+txtTitle4.getText().toString());
//                String data = (String) m_list.getItemAtPosition(position);
                final Calendar mycalender = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String dates=sdf.format(mycalender.getTime());

                String[] cut=data.split(":");
                String splt=cut[3];

                if (splt.equalsIgnoreCase("")){
                    Msg = "Null";
                } else {

                    SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        date1 = sdf.parse(dates);
                        date2 = format2.parse(splt);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (date2.compareTo(date1) < 0) {
                        Msg = "Success";
                    } else {
                        Msg = "Failed";
                    }
                }

                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ParameterListInsulation.this);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("Data",data);
                editor.putString("Item",componentname);
                editor.putString("Ent",eqent);
                editor.putString("Maint",maintanance);
                editor.putString("eqname",eqname);
                editor.putString("message",Msg);
                editor.putString("componentcode",componentcode);
                editor.putString("type","3");


                editor.commit();

                Intent i=new Intent(ParameterListInsulation.this,ParameterCompletion.class);
                startActivity(i);
                finish();
            }
        });

        R_pms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("Pms", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("qr","code");
                edit.commit();
                Intent inn = new Intent(ParameterListInsulation.this, Home.class);
                startActivity(inn);
                finish();


            }
        });
        Pms_list_inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("inv", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("qr","code");
                edit.commit();

                Intent inn = new Intent(ParameterListInsulation.this, Home.class);
                startActivity(inn);
                finish();
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialogView();
            }
        });



    }
    private void AlertDialogView() {
        final CharSequence[] items = { "Temperature", "Pressure", "Insulation", "Vibration" };

        AlertDialog.Builder builder = new AlertDialog.Builder(ParameterListInsulation.this);
        builder.setTitle("Category Type");
        builder.setSingleChoiceItems(items, -1,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
//                        Toast.makeText(getApplicationContext(), items[item],
//                                Toast.LENGTH_SHORT).show();
                        String type=String.valueOf(items[item]);

                        switch (type){
                            case "Temperature":
                                takeValue="1";
                                break;
                            case "Pressure":
                                takeValue="2";
                                break;
                            case "Insulation":
                                takeValue="3";
                                break;
                            case "Vibration":
                                takeValue="4";
                                break;

                        }

                    }
                });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                l1.clear();
                l2.clear();
                l3.clear();
                l4.clear();
                l5.clear();
                l6.clear();
                if (takeValue=="1"){
                    Intent i=new Intent(ParameterListInsulation.this,ParameterList.class);
                    startActivity(i);
                    finish();
                } else if (takeValue=="2"){
                    Intent i=new Intent(ParameterListInsulation.this,ParameterListPressure.class);
                    startActivity(i);
                    finish();
                } else if (takeValue=="3"){
                    Intent i=new Intent(ParameterListInsulation.this,ParameterListInsulation.class);
                    startActivity(i);
                    finish();
                } else if (takeValue=="4"){
                    Intent i=new Intent(ParameterListInsulation.this,ParameterListVibration.class);
                    startActivity(i);
                    finish();
                }

            }
        });



        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(ParameterListInsulation.this, "Fail", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }



    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            listview.clearTextFilter();
        } else {
            listview.setFilterText(newText.toString());
        }
        return true;
    }

    public class AsyncCheckLogin extends AsyncTask<Void, Void, Void> {
        String error = "";
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(ParameterListInsulation.this, "Please wait...","It may take few seconds to Load...", true);

        }

        @Override
        protected Void doInBackground(Void... params) {
            call();
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

                    Equipment_name.setText(eqname);

                    cut = strMsg.split("@");


                    for (int j = 0; j < cut.length; j++) {

                        String[] cut1 = cut[j].split("~");

                        if (cut1.length > 0)
                            maintanance = cut1[0].toString();
                            maintanance = maintanance.replace("&amp;","&");

                        if (cut1.length > 1)
                            days = cut1[1].toString();


                        if (cut1.length > 2)
                            doneeg = cut1[2].toString();

                        if (doneeg.equalsIgnoreCase("")) {
                            donedate = "";
                        } else {
                            String[] done1 = doneeg.split(" ");
                            String s_1 = done1[0].toString();

                            SimpleDateFormat format3 = new SimpleDateFormat("MM/dd/yyyy");
                            SimpleDateFormat format4 = new SimpleDateFormat("dd/MM/yyyy");
                            try {
                                date2 = format3.parse(s_1);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            donedate = format4.format(date2);
                        }


                        if (cut1.length > 3)
                            dueeg = cut1[3].toString();
                        if (dueeg.equalsIgnoreCase("")) {
                            due = "";
                        } else {
                            String[] due1 = dueeg.split(" ");
                            String s_2 = due1[0].toString();

                            SimpleDateFormat format5 = new SimpleDateFormat("MM/dd/yyyy");
                            SimpleDateFormat format6 = new SimpleDateFormat("dd/MM/yyyy");
                            try {
                                date1 = format5.parse(s_2);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            due = format6.format(date1);
                        }

                        if (cut1.length > 4)
                            equipname = cut1[4].toString();

                        if (cut1.length > 5)
                            maintenancecode = cut1[5].toString();

                        if (cut1.length > 6)
                            componentcode = cut1[6].toString();


                        setupSearchView();
                        l1.add(maintenancecode);
                        l2.add(days);
                        l3.add(donedate);
                        l4.add(due);
                        l5.add(maintanance);
                        l6.add(componentcode);

                    }

                    Title_parameter.setText("INSULATION");


                    Configuration configsecond = getResources().getConfiguration();
                    if (configsecond.smallestScreenWidthDp >= 600) {
                        final ParameterCustomTab adapter = new ParameterCustomTab(ParameterListInsulation.this, l1, l2, l3, l4, l5, l6);
                        listview.setAdapter(adapter);
                    } else {
                        final ParameterCustom adapter = new ParameterCustom(ParameterListInsulation.this, l1, l2, l3, l4, l5, l6);
                        listview.setAdapter(adapter);
                    }

                } else if (registerstatus.equalsIgnoreCase("Failed")) {
                    l1.clear();
                    l2.clear();
                    l3.clear();
                    l4.clear();
                    l5.clear();
                    l6.clear();
                    final ParameterCustom adapter = new ParameterCustom(ParameterListInsulation.this, l1, l2, l3, l4, l5, l6);
                    listview.setAdapter(adapter);

                    Title_parameter.setText("INSULATION");

                    showAlertDialog(ParameterListInsulation.this, "No Record's Available!",
                            "This Equipment Not Having This Particular Record", false);
                } else if (registerstatus.equalsIgnoreCase("")) {
                    Toast.makeText(ParameterListInsulation.this, strMsg, Toast.LENGTH_LONG).show();

                } else if (registerstatus.equalsIgnoreCase("null")) {
                    Toast.makeText(ParameterListInsulation.this, strMsg, Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(ParameterListInsulation.this, "Successful", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ParameterListInsulation.this, "Internet Connection Failed ! Please Try Again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupSearchView() {
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search Here");

    }
    private void showAlertDialog(Context context, String title, String message, Boolean status) {
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(context).create();

        alertDialog.setTitle(title);

        alertDialog.setMessage(message);


        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void call() {
        try {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);

            request.addProperty("componentcode", eqitem);
            request.addProperty("entitycode", eqent);
            request.addProperty("rowusercode", USerCode);
            request.addProperty("langcode", LangCode);
            request.addProperty("parametertype", "3");



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
                    registerstatus = "Failed";
                } else  {

                    registerstatus = "Success";
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
    public void onBackPressed() {
        Intent i = new Intent(ParameterListInsulation.this,HomeSub.class);
        startActivity(i);
        finish();

    }
}




