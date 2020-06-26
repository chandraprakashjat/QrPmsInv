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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class Polist extends AppCompatActivity implements SearchView.OnQueryTextListener{

    ImageView leftNav,rightNav,save;
    ImageView purchase,repair,finance,safety;
    HorizontalScrollView hsv;
    String eqname,componentname,eqitem,eqent;
    public static TextView Equipment_name, Name, loggedon, User_code,Pms_list_texty,PoText,invtext,purtext;

    RelativeLayout R_pms,Main_Done,Pms_list_inv,Purchase_lay,Repair_lay,Finance_lay,Safety_lay;
    Button selectall,done;
    SearchView searchView;
    CheckBox c;

    String qr, registerstatus;
    String ec = "0";
    String userName, password, USerCode, strMsg,equip_name,geturlvalues;


    private ListView m_list;
    private RecyclerView listview;
    String getvalues;

    private ArrayList<ArrayList<String>> data,m_data;
    private ArrayList<String> list;
    private ArrayList<String> m_code_list;
    ArrayAdapter<String> sd,md;

    ArrayList<Object> check=new ArrayList<>();

    String maintanance = "", days = "", donedate = "", due = "",equipname="",doneeg="",dueeg="",maintenancecode="",componentcode="",POcodevalue="",POnovalue="",POdatevalue="",Requestioncode="",Requestionno="",Requestiondate="",Entitycode="",Storecode="",Storename="",Storeid="",Requestionqty="",Orderqty="",Recievedqty="",Unitprice="",Discount="",Tax="",Totalprice="",Leadtime="",Accountname="",potextvalue="",storecodevalue="",recqtyvalue="",categorytype="";
    String[] cut;
    String Msg="",listvalue,designation,langcode,usercode,entity;
    Date date1=null,date2=null;

    sqlconnect sq=new sqlconnect(this);
    ArrayList lnew= new ArrayList();
    ArrayList recqty= new ArrayList();

    int j,k;

    private static final String SOAP_ACTION = "http://tempuri.org/safetylist";
    private static final String METHOD_NAME = "safetylist";
    private static final String SOAP_ACTION1 = "http://tempuri.org/POItemlist";
    private static final String METHOD_NAME1 = "POItemlist";
    private static final String SOAP_ACTION2 = "http://tempuri.org/MRRecievedqty";
    private static final String METHOD_NAME2 = "MRRecievedqty";
    private static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    private static  String URL = "";

    ArrayList<String> l1=new ArrayList<String>();
    ArrayList<String> l2=new ArrayList<String>();
    ArrayList<String> l3=new ArrayList<String>();
    ArrayList<String> l4=new ArrayList<String>();
    ArrayList<String> l5=new ArrayList<String>();
    ArrayList<String> l6=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration config = getResources().getConfiguration();
        if (config.smallestScreenWidthDp >= 600) {
            setContentView(R.layout.activity_safety_list_tab);
        } else {
            setContentView(R.layout.activity_po_list);
        }

//        leftNav = (ImageView) findViewById(R.id.left_nav);
//        rightNav = (ImageView) findViewById(R.id.right_nav);
//        hsv =(HorizontalScrollView) findViewById(R.id.hsv);

        Equipment_name=(TextView) findViewById(R.id.equipname);
        Pms_list_texty=(TextView) findViewById(R.id.pmslist_text);
        invtext=(TextView) findViewById(R.id.inv_text);
        purtext=(TextView) findViewById(R.id.pur_text);
        PoText =(TextView) findViewById(R.id.text_PO);

        R_pms=(RelativeLayout)findViewById(R.id.pms_lay);
        Main_Done=(RelativeLayout)findViewById(R.id.main_done);
        Pms_list_inv=(RelativeLayout)findViewById(R.id.Pms_list_inv);

        Purchase_lay=(RelativeLayout)findViewById(R.id.purchase_lay);
        Repair_lay=(RelativeLayout)findViewById(R.id.repair_lay);
        Finance_lay=(RelativeLayout)findViewById(R.id.finance_lay);
        Safety_lay=(RelativeLayout)findViewById(R.id.safety_lay);

        searchView=(SearchView)findViewById(R.id.searchview_pms);
        selectall=(Button)findViewById(R.id.selectall);
//        done=(Button)findViewById(R.id.done);

        save=(ImageView)findViewById(R.id.img_save);

        listview = (RecyclerView) findViewById(R.id.POlist);
        listview.setLayoutManager(new LinearLayoutManager(this));


        purchase=(ImageView)findViewById(R.id.img_crw11);
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
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Polist.this);
//        userName = sharedPref.getString("username", "");
//        password = sharedPref.getString("password", "");
//        USerCode = sharedPref.getString("usercode", "");
        Pms_list_texty.setTextColor(getResources().getColor(R.color.black));
        invtext.setTextColor(getResources().getColor(R.color.black));
        purtext.setTextColor(getResources().getColor(R.color.app_blue));

        SharedPreferences sharepref = getSharedPreferences("URLREG", Context.MODE_PRIVATE);
        geturlvalues = sharepref.getString("url_register", "");
        URL=geturlvalues;

       SharedPreferences sp1 = getSharedPreferences("safetyvalues", Context.MODE_PRIVATE);
        eqname= sp1.getString("eqname","");
        eqitem= sp1.getString("eqitem","");
        eqent=sp1.getString("eqent", "");
        USerCode=sp1.getString("USerCode", "");

        SharedPreferences sp2 = getSharedPreferences("homecode", Context.MODE_PRIVATE);
        designation = sp2.getString("designation","");
        userName = sp2.getString("Username","");
        langcode = sp2.getString("langcode","");
        usercode = sp2.getString("usercode","");
        entity = sp2.getString("entity","");


        SharedPreferences pref1 = getSharedPreferences("exitcodes", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit1 = pref1.edit();
        edit1.putString("exitcode","0");
        edit1.commit();

        SharedPreferences POSP = getSharedPreferences("Pursubactivity", Context.MODE_PRIVATE);
        POcodevalue= POSP.getString("POcodevalue","");
        POnovalue= POSP.getString("POnovalue","");
        POdatevalue= POSP.getString("POdatevalue","");
        Requestioncode= POSP.getString("Requestioncode","");
        Requestionno= POSP.getString("Requestionno","");
        Requestiondate= POSP.getString("Requestiondate","");
        Entitycode= POSP.getString("Entitycode","");
        categorytype= POSP.getString("Categorytype","");

        potextvalue = "PO No : "+POnovalue+"\nPO Date : "+POdatevalue+"\nRequestion No : "+Requestionno+"\nRequestion Date : "+Requestiondate;


        PoText.setText(potextvalue.toString());

        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnected()) {
            new AsyncPOlist().execute();
        }else {
            Toast.makeText(Polist.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }






//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                TextView txtTitle = (TextView) view.findViewById(R.id.scan_custom);
//                TextView txt1Title = (TextView) view.findViewById(R.id.freq_custom);
//                TextView txtTitle1 = (TextView) view.findViewById(R.id.lastdone_custom);
//                TextView txtTitle2 = (TextView) view.findViewById(R.id.nextdue_custom);
//                TextView txtTitle3 = (TextView) view.findViewById(R.id.maintenancecode_custom);
//                TextView txtTitle4 = (TextView) view.findViewById(R.id.componentcode_custom);
//
//                String data=(txtTitle.getText().toString()+":"+txt1Title.getText().toString()+":"+txtTitle1.getText().toString()+":"+txtTitle2.getText().toString()+":"+txtTitle3.getText().toString()+":"+txtTitle4.getText().toString());
////                String data = (String) m_list.getItemAtPosition(position);
//                final Calendar mycalender = Calendar.getInstance();
//                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//                String dates=sdf.format(mycalender.getTime());
//
//                String[] cut=data.split(":");
//                String splt=cut[3];
//
//                if (splt.equalsIgnoreCase("")){
//                    Msg = "Null";
//                } else {
//
//                    SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
//                    try {
//                        date1 = sdf.parse(dates);
//                        date2 = format2.parse(splt);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    if (date2.compareTo(date1) < 0) {
//                        Msg = "Success";
//                    } else {
//                        Msg = "Failed";
//                    }
//                }
//
//                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Polist.this);
//                SharedPreferences.Editor editor = sharedPref.edit();
//                editor.putString("Data",data);
//                editor.putString("Item",componentname);
//                editor.putString("Ent",eqent);
//                editor.putString("Maint",maintenancecode);
//                editor.putString("eqname",eqname);
//                editor.putString("message",Msg);
//                editor.putString("componentcode",componentcode);
//
//                editor.commit();
//
//                Intent i=new Intent(Polist.this,SafetyCompletion.class);
//                startActivity(i);
//                finish();
//
//            }
//        });

        R_pms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("Pms", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("qr","code");
                edit.commit();
                Intent inn = new Intent(Polist.this, Home.class);
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

                Intent inn = new Intent(Polist.this, Home.class);
                startActivity(inn);
                finish();
            }
        });
        Purchase_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("pur", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("qr","code");
                edit.commit();

                Intent inn = new Intent(Polist.this, Home.class);
                startActivity(inn);
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<String> strrecqty = new ArrayList<String>();
                strrecqty=sq.Getstrrecqty(POcodevalue);

                for ( j = 0; j < strrecqty.size(); j++) {
                    String storeqty = strrecqty.get(j).toString();
                    String[] fullqty = storeqty.split("~");
                    if(storecodevalue != "") {
                        storecodevalue = storecodevalue + "~" + fullqty[0];
                    }
                    else
                    {
                        storecodevalue = fullqty[0];
                    }

                    if(recqtyvalue != "") {
                        recqtyvalue = recqtyvalue + "~" + fullqty[1];
                    }
                    else
                    {
                        recqtyvalue = fullqty[1];
                    }

                }

                final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();

                if (activeNetwork != null && activeNetwork.isConnected()) {
                    new Asyncrecqtylist().execute();
                }else {
                    Toast.makeText(Polist.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }


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

        } else {

        }
        return true;
    }

    public class AsyncPOlist extends AsyncTask<Void, Void, Void> {
        String error = "";
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(Polist.this, "Please wait...","It may take few seconds to Load...", true);

        }

        @Override
        protected Void doInBackground(Void... params) {
            POcall();
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

                    cut = strMsg.split("@");


                    for (int j = 0; j < cut.length; j++) {

                        String[] POlistitemvalue = cut[j].split("~");

                        if(POlistitemvalue.length > 0) {
                            Storecode = POlistitemvalue[0].toString();
                        }
                        if(POlistitemvalue.length > 1) {
                            Storename = POlistitemvalue[1].toString();
                        }
                        if(POlistitemvalue.length > 2) {
                            Storeid = POlistitemvalue[2].toString();
                        }
                        if(POlistitemvalue.length > 3) {
                            Requestionqty = POlistitemvalue[3].toString();
                        }
                        if(POlistitemvalue.length > 4) {
                            Orderqty = POlistitemvalue[4].toString();
                        }
                        if(POlistitemvalue.length > 5) {
                            Recievedqty = POlistitemvalue[5].toString();
                        }
                        if(POlistitemvalue.length > 6) {
                            Unitprice = POlistitemvalue[6].toString();
                        }
                        if(POlistitemvalue.length > 7) {
                            Discount = POlistitemvalue[7].toString();
                        }
                        if(POlistitemvalue.length > 8) {
                            Tax = POlistitemvalue[8].toString();
                        }
                        if(POlistitemvalue.length > 9) {
                            Totalprice = POlistitemvalue[9].toString();
                        }
                        if(POlistitemvalue.length > 10) {
                            Leadtime = POlistitemvalue[10].toString();
                        }
                        if(POlistitemvalue.length > 11) {
                            Accountname = POlistitemvalue[11].toString();
                        }

                        l1.add(Storecode);
                        l2.add(Storename);
                        l3.add(Requestionqty);
                        l4.add(Orderqty);
                        l5.add(Storeid);

                    }

                    Configuration configsecond = getResources().getConfiguration();
                    if (configsecond.smallestScreenWidthDp >= 600) {
                        final PoCustom adapter = new PoCustom(Polist.this, l1, l2, l3, l4, l5, POcodevalue,categorytype,sq);
                        listview.setAdapter(adapter);

                    } else {
                        final PoCustom adapter = new PoCustom(Polist.this, l1, l2, l3, l4, l5, POcodevalue,categorytype,sq);
                        listview.setAdapter(adapter);

                    }

                } else if (registerstatus.equalsIgnoreCase("Failed")) {
                    showAlertDialog(Polist.this, "Alert !!",
                            "No Record's Available", false);
                } else if (registerstatus.equalsIgnoreCase("")) {
                    Toast.makeText(Polist.this, "Server is not connected.Try after sometimes!", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(Polist.this, "Successful", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Polist.this, "Internet Connection Failed ! Please Try Again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class Asyncrecqtylist extends AsyncTask<Void, Void, Void> {
        String error = "";
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(Polist.this, "Please wait...","It may take few seconds to Load...", true);

        }

        @Override
        protected Void doInBackground(Void... params) {
            POrecqty();
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
                    Toast.makeText(Polist.this, "MR Saved Succesfully", Toast.LENGTH_SHORT).show();
                    sq.DeletePO();
                    Intent i = new Intent(Polist.this,Home.class);
                    startActivity(i);
                    finish();
                } else if (registerstatus.equalsIgnoreCase("Failed")) {
                    showAlertDialog(Polist.this, "Alert !!",
                            "No Record's Available", false);
                } else if (registerstatus.equalsIgnoreCase("")) {
                    Toast.makeText(Polist.this, "Server is not connected.Try after sometimes!", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(Polist.this, "Successful", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Polist.this, "Internet Connection Failed ! Please Try Again", Toast.LENGTH_SHORT).show();
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

    public void POcall() {
        try {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME1);

            request.addProperty("POCode", POcodevalue);
            request.addProperty("EntityCode", Entitycode);
            request.addProperty("categorytype", categorytype);

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

    public void POrecqty() {
        try {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME2);

            request.addProperty("pocode", POcodevalue);
            request.addProperty("mrstorecode", storecodevalue);
            request.addProperty("recievedqty", recqtyvalue);
            request.addProperty("rowusercode", usercode);
            request.addProperty("entitycode", Entitycode);
            request.addProperty("categorytype", categorytype);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(URL);
            Object response = null;
            try {
                httpTransport.call(SOAP_ACTION2, envelope);

                response = envelope.getResponse();

                strMsg = response.toString();
                if (strMsg.equalsIgnoreCase("Failed")) {
                    // set status Failed
                    registerstatus = "Failed";
                } else if (strMsg.equalsIgnoreCase("Success")) {

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
        Intent i = new Intent(Polist.this,Home.class);
        startActivity(i);
        finish();

    }
}

