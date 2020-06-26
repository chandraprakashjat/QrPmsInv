package com.example.arvindhan.qr;

import android.app.AlertDialog;
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
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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

public class Dueoverdue extends AppCompatActivity implements SearchView.OnQueryTextListener{

    ImageView leftNav,rightNav;
    ImageView purchase,repair,finance,safety;
    HorizontalScrollView hsv;
    String vessel,completion,Due,usercode,languagecode,mainname,compid;
    public static TextView Equipment_name, Name, loggedon, User_code,Pms_list_texty,Main_head,Main_id;

    RelativeLayout R_pms,Main_Done,Pms_list_inv,Purchase_lay,Repair_lay,Finance_lay,Safety_lay;
    Button selectall,done;
    SearchView searchView;
    CheckBox c;

    String qr, registerstatus;
    String ec = "0";
    String userName, password, USerCode, strMsg,equip_name,geturlvalues;


    private ListView listview,m_list;
    String getvalues;

    private ArrayList<ArrayList<String>> data,m_data;
    private ArrayList<String> list;
    private ArrayList<String> m_code_list;
    ArrayAdapter<String> sd,md;

    ArrayList<Object> check=new ArrayList<>();

    String maintanance = "", days = "", donedate = "", due = "",equipname="",doneeg="",dueeg="",maintenancecode="",componentcode="",maintenanceid="",frequency="",averagerun = "",totalrun = "",Totaldays = "",Counterbased = "";
    String[] cut;
    String Msg="",listvalue;
    Date date1=null,date2=null;


    private static final String SOAP_ACTION = "http://tempuri.org/DueOverDue";
    private static final String METHOD_NAME = "DueOverDue";
    private static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    private static  String URL = "";

    ArrayList<String> l1=new ArrayList<String>();
    ArrayList<String> l2=new ArrayList<String>();
    ArrayList<String> l3=new ArrayList<String>();
    ArrayList<String> l4=new ArrayList<String>();
    ArrayList<String> l5=new ArrayList<String>();
    ArrayList<String> l6=new ArrayList<String>();
    ArrayList<String> l7=new ArrayList<String>();
    ArrayList<String> l8=new ArrayList<String>();

    sqlconnect sq=new sqlconnect(this);
    ArrayList lnew= new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration config = getResources().getConfiguration();
        if (config.smallestScreenWidthDp >= 600) {
            setContentView(R.layout.activity_due_main_list_tab);
        } else {
            setContentView(R.layout.activity_due_main_list);
        }

//        leftNav = (ImageView) findViewById(R.id.left_nav);
//        rightNav = (ImageView) findViewById(R.id.right_nav);
//        hsv =(HorizontalScrollView) findViewById(R.id.hsv);

        Equipment_name=(TextView) findViewById(R.id.dueoverduetext);
        Pms_list_texty=(TextView) findViewById(R.id.pmslist_text);
        R_pms=(RelativeLayout)findViewById(R.id.pms_lay);
        Purchase_lay=(RelativeLayout)findViewById(R.id.purchase_lay);
        Repair_lay=(RelativeLayout)findViewById(R.id.repair_lay);
        Finance_lay=(RelativeLayout)findViewById(R.id.finance_lay);
        Safety_lay=(RelativeLayout)findViewById(R.id.safety_lay);
        Main_Done=(RelativeLayout)findViewById(R.id.main_done);
        Pms_list_inv=(RelativeLayout)findViewById(R.id.Pms_list_inv);
        searchView=(SearchView)findViewById(R.id.searchview_pms);
        selectall=(Button)findViewById(R.id.selectall);
        Main_head = (TextView)findViewById(R.id.main_text);
        Main_id = (TextView)findViewById(R.id.maintenanceid_text);

        purchase=(ImageView)findViewById(R.id.img_crw1);
        repair=(ImageView)findViewById(R.id.img_trn1);
        finance=(ImageView)findViewById(R.id.img_alm1);
        safety=(ImageView)findViewById(R.id.img_cal1);

        int color = Color.parseColor("#A6A5A3"); // To hide the purchase,repair,etc.. in home modules
        purchase.setColorFilter(color);
        repair.setColorFilter(color);
        finance.setColorFilter(color);
        safety.setColorFilter(color);


        listview = (ListView) findViewById(R.id.list);
        lnew=sq.Getids();

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
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        SharedPreferences sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE);
        userName = sharedPref.getString("username", "");
        password = sharedPref.getString("password", "");
        USerCode = sharedPref.getString("usercode", "");

        Pms_list_texty.setTextColor(getResources().getColor(R.color.app_blue));

        SharedPreferences sharepref = getSharedPreferences("URLREG", Context.MODE_PRIVATE);
        geturlvalues = sharepref.getString("url_register", "");
        URL=geturlvalues;


        SharedPreferences sp1 = getSharedPreferences("dueoverdue", Context.MODE_PRIVATE);
        vessel = sp1.getString("vessel","");
        completion = sp1.getString("completion","");
        Due = sp1.getString("Due", "");
        usercode =  sp1.getString("Usercode", "");
        languagecode = sp1.getString("Languagecode", "");

        if(completion.contains("1")) {
            if (Due.contains("1")) {
                Equipment_name.setText(getString(R.string.mainduelist));
            } else if (Due.contains("2")) {
                Equipment_name.setText(getString(R.string.mainoverduelist));
            }
        }
        else if(completion.contains("2")) {
            Equipment_name.setText(getString(R.string.paraduelist));
        }
        else if(completion.contains("3")){
            if (Due.contains("1")) {
                Equipment_name.setText(getString(R.string.caliduelist));
            } else if (Due.contains("2")) {
                Equipment_name.setText(getString(R.string.calioverduelist));
            }
        }
        else if(completion.contains("4")){
            if (Due.contains("1")) {
                Equipment_name.setText(getString(R.string.safetyduelist));
            } else if (Due.contains("2")) {
                Equipment_name.setText(getString(R.string.safetyoverduelist));
            }
        }
        else if(completion.contains("5")) {
            Equipment_name.setText(getString(R.string.runhourlist));
        }
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
            Toast.makeText(Dueoverdue.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        Configuration configsecond = getResources().getConfiguration();

               listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txtTitle = (TextView) view.findViewById(R.id.Comp_custom);
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

                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Dueoverdue.this);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();


                SharedPreferences pref1 = getSharedPreferences("exitcodes", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit1 = pref1.edit();
                edit1.putString("exitcode","1");
                edit1.commit();
                if(completion.contains("1")) {
                    editor.putString("Data",data);
                    editor.putString("Item",txtTitle.getText().toString());
                    editor.putString("Ent",vessel);
                    editor.putString("Maint",txtTitle3.getText().toString());
                    editor.putString("eqname",txtTitle.getText().toString());
                    editor.putString("message",Msg);
                    editor.putString("componentcode",txtTitle4.getText().toString());
                    editor.putString("usercode",usercode);

                    editor.commit();

                    Intent i = new Intent(Dueoverdue.this, PmsCompletion.class);
                    startActivity(i);
                    finish();
                }
                else if(completion.contains("2"))
                {
                    editor.putString("Data",data);
                    editor.putString("Item",txtTitle.getText().toString());
                    editor.putString("Ent",vessel);
                    editor.putString("Maint",txtTitle3.getText().toString());
                    editor.putString("eqname",txtTitle.getText().toString());
                    editor.putString("message",Msg);
                    editor.putString("componentcode",txtTitle4.getText().toString());
                    editor.putString("type",Due);
                    editor.commit();
                    Intent i = new Intent(Dueoverdue.this, ParameterCompletion.class);
                    startActivity(i);
                    finish();
                }
                else if(completion.contains("3"))
                {
                    editor.putString("Data",data);
                    editor.putString("Item",txtTitle.getText().toString());
                    editor.putString("Ent",vessel);
                    editor.putString("Maint",txtTitle3.getText().toString());
                    editor.putString("eqname",txtTitle.getText().toString());
                    editor.putString("message",Msg);
                    editor.putString("componentcode",txtTitle4.getText().toString());
                    editor.commit();
                    Intent i = new Intent(Dueoverdue.this, CalibrationCompletion.class);
                    startActivity(i);
                    finish();
                }
                else if(completion.contains("4"))
                {
                    editor.putString("Data",data);
                    editor.putString("Item",txtTitle.getText().toString());
                    editor.putString("Ent",vessel);
                    editor.putString("Maint",txtTitle3.getText().toString());
                    editor.putString("eqname",txtTitle.getText().toString());
                    editor.putString("message",Msg);
                    editor.putString("componentcode",txtTitle4.getText().toString());
                    editor.putString("usercode",usercode);
                    editor.commit();
                    Intent i = new Intent(Dueoverdue.this, SafetyCompletion.class);
                    startActivity(i);
                    finish();
                }
                else if(completion.contains("5"))
                {
                    SharedPreferences runsp = getSharedPreferences("runhrvalues", Context.MODE_PRIVATE);
                    SharedPreferences.Editor runedit = runsp.edit();
                    runedit.putString("eqname",txtTitle.getText().toString());
                    runedit.putString("eqitem",txtTitle4.getText().toString());
                    runedit.putString("eqent",vessel);
                    runedit.putString("USerCode",usercode);
                    runedit.putString("LangCode",languagecode);
                    runedit.commit();

                    Intent i = new Intent(Dueoverdue.this, RunHourCompletion.class);
                    startActivity(i);
                    finish();
                }

           }
                  });

        R_pms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("Pms", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("qr","code");
                edit.commit();
                Intent inn = new Intent(Dueoverdue.this, Home.class);
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

                Intent inn = new Intent(Dueoverdue.this, Home.class);
                startActivity(inn);
                finish();
            }
        });



    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
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
            mProgressDialog = ProgressDialog.show(Dueoverdue.this, "Please wait...","It may take few seconds to Load...", true);

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
                    if (completion.contains("5")) {
                        cut = strMsg.split("@");


                        for (int j = 0; j < cut.length; j++) {

                            String[] cut1 = cut[j].split("~");

                            if (cut1.length > 0)
                                componentcode = cut1[0].toString();

                            if (cut1.length > 1)
                                equipname = cut1[1].toString();


                            if (cut1.length > 2)
                                maintenanceid = cut1[2].toString();

                            if (cut1.length > 3)
                                totalrun = cut1[3].toString() + ':' + cut1[4].toString();

                            if (cut1.length > 5)
                                averagerun = cut1[5].toString() + ':' + cut1[6].toString();

                            if (cut1.length > 7)
                                doneeg = cut1[7].toString();

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


                            if (cut1.length > 8)
                                Totaldays = cut1[8].toString();

                            if (cut1.length > 9)
                                Counterbased = cut1[9].toString();


//                    list.add("Maintenance Name : " + maintanance + "\n" + "Frequency : " + days + "\n" + "Last Done : " + donedate + "\n" + "Next Due   : " + due);
//                    m_code_list.add("Maintenance Name : " + maintanance + "\n" + "Frequency : " + days + "\n" + "Last Done : "+ donedate + "\n" + "Next Due   : " + due + "\n" +"maintenancecode :"+ maintenancecode +"\n" +"componentcode :" + componentcode);
//                    data.add(list);
//                    m_data.add(list);
//                    listview.setAdapter(sd);
//                    m_list.setAdapter(md);
//                    listview.setTextFilterEnabled(true);
//                        setupSearchView();
                            l1.add(componentcode);
                            l2.add(equipname);
                            l3.add(donedate);
                            l4.add(totalrun);
                            l5.add(maintenanceid);
                            l6.add(averagerun);
                            l7.add(Totaldays);
                            l8.add(Counterbased);

                        }
                        Configuration config = getResources().getConfiguration();
                        if (config.smallestScreenWidthDp >= 600) {
                            final DueoverdueCustomTab adapter1 = new DueoverdueCustomTab(Dueoverdue.this, l1, l2, l3, l4, l5, l6, l7, l8, completion);
                            listview.setAdapter(adapter1);
                        } else {
                            final DueoverdueCustom adapter = new DueoverdueCustom(Dueoverdue.this, l1, l2, l3, l4, l5, l6, l7, l8, completion);
                            listview.setAdapter(adapter);
                        }

                    } else {
                        cut = strMsg.split("@");


                        for (int j = 0; j < cut.length; j++) {

                            String[] cut1 = cut[j].split("~");

                            if (cut1.length > 0)
                                maintenancecode = cut1[0].toString();

                            if (cut1.length > 1)
                                componentcode = cut1[1].toString();


                            if (cut1.length > 2)
                                maintanance = cut1[2].toString();

                            if (cut1.length > 3)
                                equipname = cut1[3].toString();

                            if (cut1.length > 4)
                                maintenanceid = cut1[4].toString();

                            if (cut1.length > 5)
                                frequency = cut1[5].toString();

                            if (cut1.length > 6)
                                doneeg = cut1[6].toString();

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


                            if (cut1.length > 7)
                                dueeg = cut1[7].toString();
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

//                    list.add("Maintenance Name : " + maintanance + "\n" + "Frequency : " + days + "\n" + "Last Done : " + donedate + "\n" + "Next Due   : " + due);
//                    m_code_list.add("Maintenance Name : " + maintanance + "\n" + "Frequency : " + days + "\n" + "Last Done : "+ donedate + "\n" + "Next Due   : " + due + "\n" +"maintenancecode :"+ maintenancecode +"\n" +"componentcode :" + componentcode);
//                    data.add(list);
//                    m_data.add(list);
//                    listview.setAdapter(sd);
//                    m_list.setAdapter(md);
//                    listview.setTextFilterEnabled(true);
//                        setupSearchView();
                            l1.add(maintanance);
                            l2.add(equipname);
                            l3.add(donedate);
                            l4.add(due);
                            l5.add(maintenanceid);
                            l6.add(maintenancecode);
                            l7.add(componentcode);
                            l8.add(frequency);

                        }
                        Configuration config = getResources().getConfiguration();

                        if (config.smallestScreenWidthDp >= 600) {
                            final DueoverdueCustomTab adapter1 = new DueoverdueCustomTab(Dueoverdue.this, l1, l2, l3, l4, l5, l6, l7, l8, completion);
                            listview.setAdapter(adapter1);
                        } else {
                            final DueoverdueCustom adapter = new DueoverdueCustom(Dueoverdue.this, l1, l2, l3, l4, l5, l6, l7, l8, completion);
                            listview.setAdapter(adapter);
                        }


                    }
                    } else if (registerstatus.equalsIgnoreCase("Failed")) {
                        showAlertDialog(Dueoverdue.this, "Alert !!",
                                "No Record's Available", false);
                    } else if (registerstatus.equalsIgnoreCase("")) {
                        Toast.makeText(Dueoverdue.this, "Server is not connected.Try after sometimes!", Toast.LENGTH_LONG).show();
                    } else {

                        Toast.makeText(Dueoverdue.this, "Successful", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Dueoverdue.this, "Internet Connection Failed ! Please Try Again", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Dueoverdue.this, Home.class);
                    startActivity(i);
                    finish();
                }

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

    private void setupSearchView() {
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search Here");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void call() {
        try {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);

                request.addProperty("Usercode", usercode);
                request.addProperty("Entitycode", vessel);
                request.addProperty("Completion", completion);
                request.addProperty("Duetype", Due);
                request.addProperty("Languagecode", languagecode);


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
    public void onBackPressed() {
        Intent i = new Intent(Dueoverdue.this,Home.class);
        startActivity(i);
        finish();

    }
}

