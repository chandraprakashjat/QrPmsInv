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
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class PmsMainList extends AppCompatActivity implements SearchView.OnQueryTextListener{

    ImageView leftNav,rightNav;
    ImageView purchase,repair,finance,safety;
    HorizontalScrollView hsv;
    String eqname,componentname,eqitem,eqent;
    public static TextView Equipment_name, Name, loggedon, User_code,Pms_list_texty;

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

    String maintanance = "", days = "", donedate = "", due = "",equipname="",doneeg="",dueeg="",maintenancecode="",componentcode="";
    String[] cut;
    String Msg="",listvalue;
    Date date1=null,date2=null;


    private static final String SOAP_ACTION = "http://tempuri.org/maintanance";
    private static final String METHOD_NAME = "maintanance";
    private static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    private static  String URL = "";

    ArrayList<String> l1=new ArrayList<String>();
    ArrayList<String> l2=new ArrayList<String>();
    ArrayList<String> l3=new ArrayList<String>();
    ArrayList<String> l4=new ArrayList<String>();
    ArrayList<String> l5=new ArrayList<String>();
    ArrayList<String> l6=new ArrayList<String>();
    ArrayList<String> all=new ArrayList<String>();

    sqlconnect sq=new sqlconnect(this);
    ArrayList lnew= new ArrayList();

    CustomList adapter;
    CustomListTab tabadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration config = getResources().getConfiguration();
        if (config.smallestScreenWidthDp >= 600) {
            setContentView(R.layout.activity_pms_main_list_tab);
        } else {
            setContentView(R.layout.activity_pms_main_list);
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

        searchView=(SearchView)findViewById(R.id.searchview_pms);



        listview = (ListView) findViewById(R.id.list);


        purchase=(ImageView)findViewById(R.id.img_crw1);
        repair=(ImageView)findViewById(R.id.img_trn1);
        finance=(ImageView)findViewById(R.id.img_alm1);
        safety=(ImageView)findViewById(R.id.img_cal1);

        int color = Color.parseColor("#A6A5A3"); // To hide the purchase,repair,etc.. in home modules
        purchase.setColorFilter(color);
        repair.setColorFilter(color);
        finance.setColorFilter(color);
        safety.setColorFilter(color);


        data = new ArrayList<>();
        m_data = new ArrayList<>();
        list = new ArrayList<String>();
        m_code_list = new ArrayList<String>();

        sd = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);
        md = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, m_code_list);

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




        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(PmsMainList.this);
        userName = sharedPref.getString("username", "");
        password = sharedPref.getString("password", "");
        USerCode = sharedPref.getString("usercode", "");
        Pms_list_texty.setTextColor(getResources().getColor(R.color.app_blue));

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        SharedPreferences sharepref = getSharedPreferences("URLREG", Context.MODE_PRIVATE);
        geturlvalues = sharepref.getString("url_register", "");
        URL=geturlvalues;

        SharedPreferences sp1 = getSharedPreferences("pmsvalues", Context.MODE_PRIVATE);
        eqname= sp1.getString("eqname","");
        eqitem= sp1.getString("eqitem","").trim();
        eqent=sp1.getString("eqent", "").trim();
        USerCode=sp1.getString("USerCode", "").trim();

        SharedPreferences pref1 = getSharedPreferences("exitcodes", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit1 = pref1.edit();
        edit1.putString("exitcode","0");
        edit1.commit();

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
            Toast.makeText(PmsMainList.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        Configuration configsecond = getResources().getConfiguration();
        if (configsecond.smallestScreenWidthDp >= 600) {
            tabadapter = new CustomListTab(PmsMainList.this, l1,l2,l3,l4,l5,l6);
            listview.setAdapter(tabadapter);
        } else {
            adapter = new CustomList(PmsMainList.this, l1,l2,l3,l4,l5,l6);
            listview.setAdapter(adapter);
        }


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
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

                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(PmsMainList.this);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("Data",data);
                editor.putString("Item",componentname);
                editor.putString("Ent",eqent);
                editor.putString("Maint",maintenancecode);
                editor.putString("eqname",eqname);
                editor.putString("message",Msg);
                editor.putString("componentcode",componentcode);

                editor.commit();

                Intent i=new Intent(PmsMainList.this,PmsCompletion.class);
                startActivity(i);
                finish();

            }
        });

//        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
////                c=(CheckBox)findViewById(R.id.check);
//                RelativeLayout rl=(RelativeLayout)findViewById(R.id.rl1);
//                rl.setVisibility(View.VISIBLE);
//                return true;
//            }
//        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView,final View view, final int i, final long l) {

                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(PmsMainList.this);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("all", all.toString());
                editor.putString("l1",l1.toString());
                editor.putString("l2",l2.toString());
                editor.putString("l3",l3.toString());
                editor.putString("l4",l4.toString());
                editor.putString("l5",l5.toString());
                editor.putString("l6",l6.toString());

                Configuration configthird = getResources().getConfiguration();
                if (configthird.smallestScreenWidthDp >= 600) {
                    editor.putString("adapter", tabadapter.toString());
                } else {
                    editor.putString("adapter", adapter.toString());
                }


                editor.putString("eqname",eqname);
                editor.commit();


                Intent list=new Intent(PmsMainList.this,SubList.class);
                startActivity(list);
                finish();

                return true;
            }

        });



        R_pms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("Pms", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("qr","code");
                edit.commit();
                Intent inn = new Intent(PmsMainList.this, Home.class);
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

                Intent inn = new Intent(PmsMainList.this, Home.class);
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
            mProgressDialog = ProgressDialog.show(PmsMainList.this, "Please wait...","It may take few seconds to Load...", true);

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


//                    list.add("Maintenance Name : " + maintanance + "\n" + "Frequency : " + days + "\n" + "Last Done : " + donedate + "\n" + "Next Due   : " + due);
//                    m_code_list.add("Maintenance Name : " + maintanance + "\n" + "Frequency : " + days + "\n" + "Last Done : "+ donedate + "\n" + "Next Due   : " + due + "\n" +"maintenancecode :"+ maintenancecode +"\n" +"componentcode :" + componentcode);
//                    data.add(list);
//                    m_data.add(list);
//                    listview.setAdapter(sd);
//                    m_list.setAdapter(md);
//                    listview.setTextFilterEnabled(true);
                        setupSearchView();
                        l1.add(maintanance);
                        l2.add(days);
                        l3.add(donedate);
                        l4.add(due);
                        l5.add(maintenancecode);
                        l6.add(componentcode);
                        all.add(maintenancecode + "," + componentcode + "," + maintanance + "," + days + "," + donedate + "," + due);


//                    check.add(l1);
//                    check.add(l2);
//                    check.add(l3);
//                    check.add(l4);
//                    check.add(l5);
//                    check.add(l6);


                    }

                } else if (registerstatus.equalsIgnoreCase("Failed")) {
                    showAlertDialog(PmsMainList.this, "Alert",
                            "No Record's Available", false);
                } else if (registerstatus.equalsIgnoreCase("")) {
                    Toast.makeText(PmsMainList.this, strMsg, Toast.LENGTH_LONG).show();

                }else if (registerstatus.equalsIgnoreCase("null")) {
                    Toast.makeText(PmsMainList.this, strMsg, Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(PmsMainList.this, "Successful", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(PmsMainList.this, "Internet Connection Failed ! Please Try Again", Toast.LENGTH_SHORT).show();
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
                } else if (strMsg.equalsIgnoreCase("")) {
                    registerstatus = "null";
                } else if (strMsg.equalsIgnoreCase("null")) {
                    registerstatus = "null";
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
        SharedPreferences pref = getSharedPreferences("numValue", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("val", "1");
        edit.commit();
        Intent i = new Intent(PmsMainList.this,HomeSub.class);
        startActivity(i);
        finish();

    }
}

