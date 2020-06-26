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
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListAdapter;
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

public class SubList extends AppCompatActivity {

    ImageView leftNav,rightNav;
    ImageView purchase,repair,finance,safety;
    HorizontalScrollView hsv;
    String eqname,componentname,eqitem,eqent;
    public static TextView Equipment_name, Name, loggedon, User_code,Pms_list_texty,check234;
    private int previousDistanceFromFirstCellToTop;
    RelativeLayout R_pms,Main_Done,Pms_list_inv,Purchase_lay,Repair_lay,Finance_lay,Safety_lay;
    Button selectall,done;
    SearchView searchView;
    CheckBox c;

    String qr, registerstatus;
    String ec = "0";
    String userName, password, USerCode, strMsg,equip_name,geturlvalues;


    private ListView listview,listview_duplicate,m_list;
    String getvalues;

    private ArrayList<ArrayList<String>> data,m_data;
    private ArrayList<String> list;
    private ArrayList<String> m_code_list;
    ArrayAdapter<String> sd,md;

    ArrayList<Object> check=new ArrayList<>();

    String maintanance = "", days = "", donedate = "", due = "",equipname="",doneeg="",dueeg="",maintenancecode="",componentcode="";
    String[] cut;
    String Msg="",listvalue,s,checkboxc1;
    Date date1=null,date2=null;
    int checkedc1=0,currentFirstVisibleItem,currentVisibleItemCount,currentScrollState;
    int  i2,  i3;

    sqlconnect sq=new sqlconnect(this);
    ArrayList lnew= new ArrayList();

    CheckedTextView item;

    private static final String SOAP_ACTION = "http://tempuri.org/maintanance";
    private static final String METHOD_NAME = "maintanance";
    private static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    private static  String URL = "";
    View checkboxv;

    ArrayList<String> l1=new ArrayList<String>();
    ArrayList<String> l2=new ArrayList<String>();
    ArrayList<String> l3=new ArrayList<String>();
    ArrayList<String> l4=new ArrayList<String>();
    ArrayList<String> l5=new ArrayList<String>();
    ArrayList<String> l6=new ArrayList<String>();
    ArrayList add=new ArrayList();
    ArrayList addnew=new ArrayList();
    ArrayList overall=new ArrayList();


    ArrayList<String> all=new ArrayList<String>();
    ArrayList l234=new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration configsecond = getResources().getConfiguration();
        if (configsecond.smallestScreenWidthDp >= 600) {
            setContentView(R.layout.activity_sub_list_tab);
        } else {
        setContentView(R.layout.activity_sub_list);
        }

//        leftNav = (ImageView) findViewById(R.id.left_nav);
//        rightNav = (ImageView) findViewById(R.id.right_nav);
//        hsv =(HorizontalScrollView) findViewById(R.id.hsv);

        check234=(TextView) findViewById(R.id.check);
        Equipment_name=(TextView) findViewById(R.id.equipname_sub);
        Pms_list_texty=(TextView) findViewById(R.id.pmslist_text);
        R_pms=(RelativeLayout)findViewById(R.id.pms_lay);
        Main_Done=(RelativeLayout)findViewById(R.id.main_done);
        Pms_list_inv=(RelativeLayout)findViewById(R.id.Pms_list_inv);


        Purchase_lay=(RelativeLayout)findViewById(R.id.purchase_lay);
        Repair_lay=(RelativeLayout)findViewById(R.id.repair_lay);
        Finance_lay=(RelativeLayout)findViewById(R.id.finance_lay);
        Safety_lay=(RelativeLayout)findViewById(R.id.safety_lay);

//        searchView=(SearchView) findViewById(R.id.searchview_pms);
        //selectall=(Button)findViewById(R.id.selectall);
        done=(Button)findViewById(R.id.done);

        listview = (ListView) findViewById(R.id.list_sub);
        listview_duplicate= (ListView) findViewById(R.id.list_sub_dup);


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

        Configuration config = getResources().getConfiguration();
        if (config.smallestScreenWidthDp >= 600) {
            sd = new ArrayAdapter<String>(this,R.layout.listcheckbox_tab, list);
            md = new ArrayAdapter<String>(this, R.layout.listcheckbox_tab, m_code_list);
        } else {
            sd = new ArrayAdapter<String>(this,R.layout.listcheckbox, list);
            md = new ArrayAdapter<String>(this, R.layout.listcheckbox, m_code_list);
        }




        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(SubList.this);
        userName = sharedPref.getString("username", "");
        password = sharedPref.getString("password", "");
        USerCode = sharedPref.getString("usercode", "");
        Pms_list_texty.setTextColor(getResources().getColor(R.color.app_blue));




        SharedPreferences sharepref = getSharedPreferences("URLREG", Context.MODE_PRIVATE);
        geturlvalues = sharepref.getString("url_register", "");
        URL=geturlvalues;

        SharedPreferences sp1 = getSharedPreferences("pmsvalues", Context.MODE_PRIVATE);
        eqname= sp1.getString("eqname","");
        eqitem= sp1.getString("eqitem","");
        eqent=sp1.getString("eqent", "");
        USerCode=sp1.getString("USerCode", "");

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
            Toast.makeText(SubList.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
//        checkboxc1="v";
//        final CustomList adapter = new CustomList(SubList.this, all,l1,l2,l3,l4,l5,l6,checkboxc1);
//        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {

                 item = (CheckedTextView) view;
                String data = (String) listview_duplicate.getItemAtPosition(position);
                if (item.isChecked()==false) {

               //     String s=item.getText().toString();
                    if (add.contains(data)){
                        add.remove(data);
                    } else {

                    }

                } else if (item.isChecked()==true){
                    add.add(data.toString());


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
                Intent inn = new Intent(SubList.this, Home.class);
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

                Intent inn = new Intent(SubList.this, Home.class);
                startActivity(inn);
                finish();
            }
        });



        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnew.clear();
//                String s=item.getText().toString();
//                int count = listview.getCount(),i;
//                        addnew.add(add.toString());
//                Equipment_name.setText(addnew.toString());
                if (add.size()==0){
                    showAlertDialog(SubList.this, "Alert !!",
                            "Select atleast one maintenance", false);

                } else {
                    SharedPreferences pref = getSharedPreferences("total", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("add", add.toString());

                    edit.commit();

                    Intent i = new Intent(SubList.this, PmsTotalCompletion.class);
                    startActivity(i);
                    finish();
                }
            }
        });



    }







    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);

    }

    public class AsyncCheckLogin extends AsyncTask<Void, Void, Void> {
        String error = "";
        ProgressDialog mProgressDialog;
        private ListView listView;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(SubList.this, "Please wait...","It may take few seconds to Load...", true);

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
            if (registerstatus.equalsIgnoreCase("Success")) {

                Equipment_name.setText(eqname);

                cut = strMsg.split("@");


                for (int j = 0; j < cut.length; j++) {

                    String[] cut1 = cut[j].split(",");

                    if (cut1.length > 0)
                        maintanance = cut1[0].toString();
                        maintanance = maintanance.replace("&amp;","&");

                    if (cut1.length > 1)
                        days = cut1[1].toString();

                    if (cut1.length > 2)
                        doneeg = cut1[2].toString();

                    if (doneeg.equalsIgnoreCase("")){
                        donedate="";
                    } else {
                        String[] done1=doneeg.split(" ");
                        String s_1=done1[0].toString();

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
                    if (dueeg.equalsIgnoreCase("")){
                        due="";
                    } else {
                        String[] due1=dueeg.split(" ");
                        String s_2=due1[0].toString();

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


                    list.add("Maintenance Name : " + maintanance + "\n" + "Frequency                  : " + days + "\n" + "Last Done                  : " + donedate + "\n" + "Next Due                    : " + due);
                    m_code_list.add("Maintenance Name : " + maintanance + "\n" + "Frequency : " + days + "\n" + "Last Done : " + donedate + "\n" + "Next Due   : " + due + "\n" + "maintenancecode :" + maintenancecode + "\n" + "componentcode :" + componentcode);

                }
//                    data.add(list);
//                    m_data.add(list);
                listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listview.setTextFilterEnabled(true);
                listview.setMinimumHeight(1000);

                listview.setAdapter(sd);
                listview_duplicate.setAdapter(md);

//                    listview.setTextFilterEnabled(true);
//                    setupSearchView();
//                    l1.add(maintanance);
//                    l2.add(days);
//                    l3.add(donedate);
//                    l4.add(due);
//                    l5.add(maintenancecode);
//                    l6.add(componentcode);
//                    all.add(maintenancecode+","+componentcode+","+maintanance+","+days+","+donedate+","+due);


//                    check.add(l1);
//                    check.add(l2);
//                    check.add(l3);
//                    check.add(l4);
//                    check.add(l5);
//                    check.add(l6);


            } else if (registerstatus.equalsIgnoreCase("Failed")) {
                showAlertDialog(SubList.this, "Alert",
                        "No Record's Available", false);
            } else if (registerstatus.equalsIgnoreCase("")) {
                Toast.makeText(SubList.this, "Server is not connected.Try after sometimes!", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(SubList.this, "Successful", Toast.LENGTH_SHORT).show();
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

//    private void setupSearchView() {
//        searchView.setIconifiedByDefault(false);
//        searchView.setOnQueryTextListener(this);
//        searchView.setSubmitButtonEnabled(true);
//        searchView.setQueryHint("Search Here");
//    }

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
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(SubList.this,PmsMainList.class);
        startActivity(i);
        finish();

    }
}


