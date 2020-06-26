package com.example.arvindhan.qr;

import android.accounts.Account;
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
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PurSub extends AppCompatActivity {

    ImageView leftNav,rightNav,leftNav1,rightNav1,iv_menu;
    ImageView purchase,repair,finance,safety;
    HorizontalScrollView hsv,hsv1;
    RelativeLayout R_pms,Main_Done,InvLay,Purchase_lay,Repair_lay,Finance_lay,Safety_lay,Inv_oil,Inv_part,Inv_store,Para_Lay,cal_rel,safety_rel,Runhour_Lay,Pms_frame_rel,MainSpinner_Lay,SideSpinner_Lay,SideSpinner1_Lay,SideSpinner2_Lay,VesselSpinner_Lay,Pur_Rec;
    SharedPreferences sp,sp2,sp3,sp4,sp5;
    SharedPreferences.Editor edt;
    FrameLayout fl,fl1,fl2;
    ImageView iv,iv1, iv2,Arrow;
    TextView tv,Pur_list_text,tv1;
    Spinner EquipmentSpinner,DueSpinner,MethodSpinner,EntitySpinner,ParamterSpinner;
    public static TextView value,value1,value2,qrtext1,qrtext2,User_code,qrvalue,OilText,PartText,StoreText,PmsText,InvText,PurText,Purheadtext;
    String oil_name,oil_type,oil_unit,eqitem,eqent,userName, password,USerCode,LangCode,res,eqname,oil,temp=null,qrret=null,ivvisi;
    String eqcode,eqmodel,id,ent,POno,POdate,Category,Categorytype,geturlvalues,strMsg,registerstatus,POcodevalue,PODatevalue,POnovalue,Requestioncode,Requestionno,Requestiondate,Entitycode,Storecode;
    String[] POlist,POvalues,POitemvalue,POlistitemvalue;
    String dispvalue = null;
    int i;
//    private IntentIntegrator qrscan;
//    private IntentIntegrator qrscan1;

    String newvalue;

    sqlconnect sq=new sqlconnect(this);
    ArrayList l1= new ArrayList();


    private static final String SOAP_ACTION = "http://tempuri.org/POlist";
    private static final String METHOD_NAME = "POlist";
    private static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    private static  String URL = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration config = getResources().getConfiguration();
        if (config.smallestScreenWidthDp >= 600) {
            setContentView(R.layout.activity_home_sub_tab);
        } else {
            setContentView(R.layout.activity_pur_sub);

        }

        iv = (ImageView) findViewById(R.id.qr_img);
        iv1 = (ImageView) findViewById(R.id.qr_img1);
        iv2 = (ImageView) findViewById(R.id.qr_img2);
        value = (TextView) findViewById(R.id.text_qr);
        value1 = (TextView) findViewById(R.id.text_qr1);
        value2 = (TextView) findViewById(R.id.text_qr2);
        tv1 = (TextView) findViewById(R.id.tv1);
        fl=(FrameLayout)findViewById(R.id.frame_layout1);
        fl1=(FrameLayout)findViewById(R.id.frame_layout2);
        fl2=(FrameLayout)findViewById(R.id.frame_layout3);
        R_pms=(RelativeLayout)findViewById(R.id.pms_lay);
        Purchase_lay=(RelativeLayout)findViewById(R.id.purchase_lay);
        Repair_lay=(RelativeLayout)findViewById(R.id.repair_lay);
        Finance_lay=(RelativeLayout)findViewById(R.id.finance_lay);
        Safety_lay=(RelativeLayout)findViewById(R.id.safety_lay);
        InvLay=(RelativeLayout)findViewById(R.id.inv_lay);
        cal_rel=(RelativeLayout)findViewById(R.id.cal_rel);
        safety_rel=(RelativeLayout)findViewById(R.id.safety_rel);
        Main_Done=(RelativeLayout)findViewById(R.id.main_done);
        Inv_oil=(RelativeLayout)findViewById(R.id.inv_oil);
        Inv_part=(RelativeLayout)findViewById(R.id.inv_part);
        Inv_store=(RelativeLayout)findViewById(R.id.inv_store);
        Para_Lay=(RelativeLayout)findViewById(R.id.parameter_rel_layout);
        Runhour_Lay=(RelativeLayout)findViewById(R.id.runhr_rel_lay);
        Pms_frame_rel=(RelativeLayout)findViewById(R.id.pms_frame_rel);
        Arrow = (ImageView)findViewById(R.id.imagedown);
        Pur_Rec=(RelativeLayout)findViewById(R.id.pur_rec);





        iv_menu=(ImageView)findViewById(R.id.optionmenu);
        User_code = (TextView) findViewById(R.id.usercode);
        qrvalue = (TextView) findViewById(R.id.qrvalue);
        qrtext1=(TextView) findViewById(R.id.qrvalue1);
        PurText=(TextView) findViewById(R.id.pur_mr_text);

//        leftNav = (ImageView) findViewById(R.id.left_nav);
//        rightNav = (ImageView) findViewById(R.id.right_nav);
//        leftNav1 = (ImageView) findViewById(R.id.left_nav1);
//        rightNav1 = (ImageView) findViewById(R.id.right_nav1);
//        hsv =(HorizontalScrollView) findViewById(R.id.hsv);
//        hsv1 =(HorizontalScrollView) findViewById(R.id.hsv1);

        OilText=(TextView) findViewById(R.id.oil_text);
        PartText=(TextView) findViewById(R.id.part_text);
        StoreText=(TextView) findViewById(R.id.store_text);
        PmsText=(TextView) findViewById(R.id.pms_text);
        InvText=(TextView) findViewById(R.id.inv_text);
        Purheadtext=(TextView) findViewById(R.id.pur_text);

        purchase=(ImageView)findViewById(R.id.img_crw11);
        repair=(ImageView)findViewById(R.id.img_trn1);
        finance=(ImageView)findViewById(R.id.img_alm1);
        safety=(ImageView)findViewById(R.id.img_cal1);

        int color = Color.parseColor("#A6A5A3"); // To hide the purchase,repair,etc.. in home modules
        purchase.setColorFilter(color);
        repair.setColorFilter(color);
        finance.setColorFilter(color);
        safety.setColorFilter(color);

        l1=sq.Getid();
        tv1.setText(l1.get(0).toString());
        String splt=tv1.getText().toString();

        if (splt.contains("1")){

            R_pms.setEnabled(true);
            R_pms.setVisibility(View.VISIBLE);
        }
        if (splt.contains("2")){
            InvLay.setEnabled(true);
            InvLay.setVisibility(View.VISIBLE);
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


//        qrscan = new IntentIntegrator(this);
//        qrscan1 = new IntentIntegrator(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(PurSub.this);
        userName = sp.getString("username", "");
        password = sp.getString("password", "");
        USerCode=sp.getString("usercode", "");
        LangCode=sp.getString("langcode", "");

        SharedPreferences subs = getSharedPreferences("purActivity", Context.MODE_PRIVATE);
        newvalue= subs.getString("value","");

        SharedPreferences sharepref = getSharedPreferences("URLREG", Context.MODE_PRIVATE);
        geturlvalues = sharepref.getString("url_register", "");
        URL=geturlvalues;

        PurText.setTextColor(getResources().getColor(R.color.app_blue));


        if(newvalue != null)
        {

                POlist = newvalue.split("\n");
                String[] POnumlist = POlist[0].split(":");
                String[] POdatelist = POlist[1].split(":");
                String[] POcatlist = POlist[2].split(":");
                String[] POcattype = POlist[3].split(":");

                    POno = POnumlist[1].trim().toString();
                    POdate = POdatelist[1].trim().toString();
                    Category = POcatlist[1].trim().toString();
                    Categorytype = POcattype[1].trim().toString();

            dispvalue = POlist[0]+"\n"+POlist[1]+"\n"+POlist[2]+"\n";

        }
        value.setText(dispvalue);

        SharedPreferences sp2 = getSharedPreferences("pms", Context.MODE_PRIVATE);
        ivvisi= sp2.getString("qr","");

        Purheadtext.setTextColor(getResources().getColor(R.color.app_blue));
        PmsText.setTextColor(getResources().getColor(R.color.black));
        InvText.setTextColor(getResources().getColor(R.color.black));

        SharedPreferences pref1 = getSharedPreferences("exitcodes", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit1 = pref1.edit();
        edit1.putString("exitcode","0");
        edit1.commit();

        R_pms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences pref = getSharedPreferences("Pms", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("qr","code");
                edit.commit();
                Intent inn = new Intent(PurSub.this, Home.class);
                startActivity(inn);
                finish();

            }
        });
        InvLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("inv", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("qr","code");
                edit.commit();
                Intent inn = new Intent(PurSub.this, Home.class);
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
                Intent inn = new Intent(PurSub.this, Home.class);
                startActivity(inn);
                finish();

            }

        });
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });
        Pur_Rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {
                    new AsyncPOlist().execute();
                }else {
                    Toast.makeText(PurSub.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }


            }
        });

        Inv_oil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(oil.contains("Category : OIL")) {
                    OilText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    PartText.setTextColor(getResources().getColor(R.color.lightgrey));
                    StoreText.setTextColor(getResources().getColor(R.color.lightgrey));


                    SharedPreferences pref = getSharedPreferences("qr", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("values", oil);
                    edit.commit();
                    Intent oil = new Intent(PurSub.this, InvOil.class);
                    startActivity(oil);
                    finish();
                }
                else
                {
                    Toast.makeText(PurSub.this, "QR Not Valid For Oil", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Inv_part.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(oil.contains("Category : PART")) {
                    PartText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    OilText.setTextColor(getResources().getColor(R.color.lightgrey));
                    StoreText.setTextColor(getResources().getColor(R.color.lightgrey));

                    SharedPreferences pref = getSharedPreferences("qr", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("values", oil);
                    edit.commit();
                    Intent part=new Intent(PurSub.this,InvOil.class);
                    startActivity(part);
                    finish();
                }
                else
                {
                    Toast.makeText(PurSub.this, "QR Not Valid For Part", Toast.LENGTH_SHORT).show();
                }
            }

        });
        Inv_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(oil.contains("Category : STORE")) {
                    StoreText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    PartText.setTextColor(getResources().getColor(R.color.lightgrey));
                    OilText.setTextColor(getResources().getColor(R.color.lightgrey));

                    SharedPreferences pref = getSharedPreferences("qr", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("values", oil);
                    edit.commit();
                    Intent stores=new Intent(PurSub.this,InvOil.class);
                    startActivity(stores);
                    finish();
                }
                else
                {
                    Toast.makeText(PurSub.this, "QR Not Valid For Store", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void showPopup(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout: {
                        sp=getSharedPreferences("login", Context.MODE_PRIVATE);
                        edt = sp.edit();
                        edt.clear();
                        edt.commit();
                        Toast.makeText(PurSub.this, "User Logged Out !", Toast.LENGTH_SHORT).show();


                        Intent inn = new Intent(PurSub.this, Login.class);
                        startActivity(inn);
                        finish();
                        break;

                    }

                    case R.id.churl: {
                        sp=getSharedPreferences("login", Context.MODE_PRIVATE);
                        String UserName = sp.getString("username", "");
                        String Password = sp.getString("password", "");
                        if (UserName.equalsIgnoreCase("superuser") && Password.equalsIgnoreCase("SUPER")) {
                            Intent inc = new Intent(PurSub.this, Url.class);
                            startActivity(inc);
                            break;
                        } else {
                            AlertDialog.Builder adb = new AlertDialog.Builder(PurSub.this);
                            adb.setTitle("Information");
                            adb.setMessage("This User Does Not Have The Rights To Change The URL !");
                            adb.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                            adb.show();
                        }

                    }

                }
                return true;
            }
        });
        popup.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    // Back button event
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(iv.getVisibility()==View.VISIBLE) {
                Intent i=new Intent(PurSub.this,Home.class);
                startActivity(i);
                finish();
            }
            else
            {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        PurSub.this);

                // set title
                alertDialogBuilder.setTitle("Alert !!");
                // set icon
                // alertDialogBuilder.setIcon(R.drawable.fail);
                // set dialog message
                alertDialogBuilder
                        .setMessage("Click yes to exit!")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        // if this button is clicked, close
                                        // current activity
                                        finish();
                                        Intent intent = new Intent(
                                                Intent.ACTION_MAIN);
                                        intent.addCategory(Intent.CATEGORY_HOME);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        // if this button is clicked, just close
                                        // the dialog box and do nothing
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        }
        return false;
    }

    public class AsyncPOlist extends AsyncTask<Void, Void, Void> {
        String error = "";
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(PurSub.this, "Please wait...","It may take few seconds to Load...", true);

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

                    String PO = strMsg;

                    POitemvalue = PO.split("~");

                    if(POitemvalue.length > 0) {
                        POcodevalue = POitemvalue[0].toString();
                    }
                    if(POitemvalue.length > 1) {
                        POnovalue = POitemvalue[1].toString();
                    }
                    if(POitemvalue.length > 2) {
                        PODatevalue = POitemvalue[2].toString();
                    }

                    if(POitemvalue.length > 3) {
                        Requestioncode = POitemvalue[3].toString();
                    }

                    if(POitemvalue.length > 4) {
                        Requestionno = POitemvalue[4].toString();
                    }
                    if(POitemvalue.length > 5) {
                        Requestiondate = POitemvalue[5].toString();
                    }
                    if(POitemvalue.length > 6) {
                        Entitycode = POitemvalue[6].toString();
                    }

                    if (Entitycode.equalsIgnoreCase("0")) {
                        showAlertDialog(PurSub.this, "Alert",
                                "Please scan the vessel based QR code for any transaction", false);
                    } else {

                        SharedPreferences pref = getSharedPreferences("Pursubactivity", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = pref.edit();
                        edit.putString("POcodevalue",POcodevalue.toString());
                        edit.putString("POnovalue",POnovalue.toString());
                        edit.putString("POdatevalue",PODatevalue.toString());
                        edit.putString("Requestioncode",Requestioncode.toString());
                        edit.putString("Requestionno",Requestionno.toString());
                        edit.putString("Requestiondate",Requestiondate.toString());
                        edit.putString("Entitycode",Entitycode.toString());
                        edit.putString("Categorytype",Categorytype.toString());
                        edit.commit();
                        Intent point = new Intent(PurSub.this, Polist.class);
                        startActivity(point);
                        finish();
                    }


                } else if (registerstatus.equalsIgnoreCase("Failed")) {
                    showAlertDialog(PurSub.this, "Alert",
                            "No Record's Available", false);
                } else if (registerstatus.equalsIgnoreCase("")) {
                    Toast.makeText(PurSub.this, strMsg, Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(PurSub.this, "Successful", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(PurSub.this, "Internet Connection Failed ! Please Try Again", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void POcall() {
        try {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);

            request.addProperty("pono", POno);

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
                } else {
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

    }

    private void showAlertDialog(Context context, String title, String message, Boolean status) {
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(context).create();

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


}
