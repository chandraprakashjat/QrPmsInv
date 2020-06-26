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
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.arvindhan.qr.Dueoverdue;
import com.example.arvindhan.qr.PurSub;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Home extends AppCompatActivity {
    ImageView leftNav,rightNav,leftNav1,rightNav1,iv_menu;
    ImageView purchase,repair,finance,safety;
    HorizontalScrollView hsv,hsv1;
    RelativeLayout R_pms,Main_Done,InvLay,Purchase_lay,Repair_lay,Finance_lay,Safety_lay,Inv_oil,Inv_part,Inv_store,Para_Lay,cal_rel,safety_rel,Runhour_Lay,Pms_frame_rel,MainSpinner_Lay,SideSpinner_Lay,SideSpinner1_Lay,SideSpinner2_Lay,VesselSpinner_Lay;
    SharedPreferences sp,sp2,sp3,sp4,sp5;
    SharedPreferences.Editor edt;
    FrameLayout fl,fl1,fl2;
    ImageView iv,iv1, iv2,Arrow;
    TextView tv;
    Spinner EquipmentSpinner,DueSpinner,MethodSpinner,EntitySpinner,ParamterSpinner;

//    String[] check;
    ArrayList<String> l2=new ArrayList<String>();
    ArrayList<String> all=new ArrayList<String>();

    ArrayList<String> check=new ArrayList<String>();
    ArrayList<String> trimvalues=new ArrayList<String>();
    public static TextView value,value1,value2,qrtext1,qrtext2,User_code,qrvalue,OilText,PartText,StoreText,PmsText,InvText,PurText;
    String oil_name,id,oil_type,oil_unit,eqitem,eqent,userName, password,USerCode,LangCode,res,eqname,oil,temp=null,qrret=null,ivvisi,method,completion,Due,purres;
    String eqcode,eqmodel,eqactive,ent,finalvalue,finalvalue1,registerstatus="",strMsg,cat,active,Entityname,Entitycode,Completion = null,pocode;

    private IntentIntegrator qrscan;
    private IntentIntegrator qrscan1;
    private IntentIntegrator qrscan2;

    String variable="0",geturlvalues,strMsgInv,strMsgPur,checkcat,checkuniq,EntityValue="",EntityNameValue;
    String[] cut;

    sqlconnect sq=new sqlconnect(this);
    ArrayList l1= new ArrayList();
    ArrayList l3= new ArrayList();
    ArrayList<String> aaslist= new ArrayList<String>();

    private static final String SOAP_ACTION = "http://tempuri.org/Uniqueget";
    private static final String METHOD_NAME = "Uniqueget";
    private static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    private static final String SOAP_ACTION1 = "http://tempuri.org/EntityList";
    private static final String METHOD_NAME1 = "EntityList";
    private static  String URL = "";

    ArrayList<String> aas=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration config = getResources().getConfiguration();
        if (config.smallestScreenWidthDp >= 600) {
            setContentView(R.layout.activity_home_tab);
        } else {
            setContentView(R.layout.activity_home);
        }

        iv = (ImageView) findViewById(R.id.qr_img);
        iv1 = (ImageView) findViewById(R.id.qr_img1);
        iv2 = (ImageView) findViewById(R.id.qr_img2);
        value = (TextView) findViewById(R.id.text_qr);
        value1 = (TextView) findViewById(R.id.text_qr1);
        value2 = (TextView) findViewById(R.id.text_qr2);
        tv = (TextView) findViewById(R.id.tv);
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
        MainSpinner_Lay=(RelativeLayout)findViewById(R.id.main_spinner);
        SideSpinner_Lay=(RelativeLayout)findViewById(R.id.Side_spinner);
        SideSpinner1_Lay=(RelativeLayout)findViewById(R.id.Side_spinner1);
        SideSpinner2_Lay=(RelativeLayout)findViewById(R.id.Side_spinner2);
        EquipmentSpinner = (Spinner)findViewById(R.id.equipmentspinner);
        DueSpinner = (Spinner)findViewById(R.id.Duespinner);
        MethodSpinner = (Spinner)findViewById(R.id.Methodspinner);
        ParamterSpinner = (Spinner)findViewById(R.id.Parameterspinner);
        VesselSpinner_Lay=(RelativeLayout)findViewById(R.id.Vessel_spinner);
        EntitySpinner = (Spinner)findViewById(R.id.entityspinner);
        Arrow = (ImageView)findViewById(R.id.imagedown);


        iv_menu=(ImageView)findViewById(R.id.optionmenu);
        User_code = (TextView) findViewById(R.id.usercode);
        qrvalue = (TextView) findViewById(R.id.qrvalue);
        qrtext1=(TextView) findViewById(R.id.qrvalue1);
        qrtext2=(TextView) findViewById(R.id.qrvalue2);
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
        PurText=(TextView) findViewById(R.id.pur_text);


        purchase=(ImageView)findViewById(R.id.img_crw1);
        repair=(ImageView)findViewById(R.id.img_trn1);
        finance=(ImageView)findViewById(R.id.img_alm1);
        safety=(ImageView)findViewById(R.id.img_cal1);

        int color = Color.parseColor("#A6A5A3"); // To hide the purchase,repair,etc.. in home modules
        purchase.setColorFilter(color);
        repair.setColorFilter(color);
        finance.setColorFilter(color);
        safety.setColorFilter(color);

        final List<String> Equipmenttype = new ArrayList<String>();
        Equipmenttype.add("-- Select --");
        Equipmenttype.add("Non QR");
        Equipmenttype.add("QR");

        final List<String> Duetype = new ArrayList<String>();
        Duetype.add("-- Select --");
        Duetype.add("Due");
        Duetype.add("OverDue");

        final List<String> Methodtype = new ArrayList<String>();
        Methodtype.add("-- Select --");
        Methodtype.add("Maintenance");
        Methodtype.add("Parameter");
        Methodtype.add("Calibration");
        Methodtype.add("Safety Alarm");
        Methodtype.add("Run Hour");

        final List<String> Parametertype = new ArrayList<String>();
        Parametertype.add("-- Select --");
        Parametertype.add("Temperature");
        Parametertype.add("Pressure");
        Parametertype.add("Insulation");
        Parametertype.add("Vibration");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Home.this, android.R.layout.simple_spinner_item, Equipmenttype);

        dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        EquipmentSpinner.setAdapter(dataAdapter);

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(Home.this, android.R.layout.simple_spinner_item, Duetype);

        dataAdapter1.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        DueSpinner.setAdapter(dataAdapter1);

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(Home.this, android.R.layout.simple_spinner_item, Methodtype);

        dataAdapter2.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        MethodSpinner.setAdapter(dataAdapter2);

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(Home.this, android.R.layout.simple_spinner_item, Parametertype);

        dataAdapter3.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        ParamterSpinner.setAdapter(dataAdapter3);

        SharedPreferences sharepref = getSharedPreferences("URLREG", Context.MODE_PRIVATE);
        geturlvalues = sharepref.getString("url_register", "");
        URL=geturlvalues;

        l3=sq.Getid();
        tv.setText(l3.get(0).toString());
        String splt=tv.getText().toString();

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



        qrscan = new IntentIntegrator(this);
        qrscan1 = new IntentIntegrator(this);
        qrscan2 = new IntentIntegrator(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SharedPreferences sp = getSharedPreferences("homecode", Context.MODE_PRIVATE);
        userName = sp.getString("username", "");
        password = sp.getString("password", "");
        USerCode=sp.getString("usercode", "");
        LangCode=sp.getString("langcode", "");

        SharedPreferences sp8 = getSharedPreferences("login", Context.MODE_PRIVATE);
        if(USerCode != "")
        {
            SharedPreferences.Editor edit2 = sp8.edit();
            edit2.putString("username", userName);
            edit2.putString("password", password);
            edit2.putString("usercode", USerCode);
            edit2.putString("langcode", LangCode);
            edit2.commit();
        }

        if(USerCode == "")
        {
            userName = sp8.getString("username", "");
            password = sp8.getString("password", "");
            USerCode=sp8.getString("usercode", "");
            LangCode=sp8.getString("langcode", "");

            SharedPreferences.Editor edit3 = sp.edit();
            edit3.putString("username", userName);
            edit3.putString("password", password);
            edit3.putString("usercode", USerCode);
            edit3.putString("langcode", LangCode);

        }


        SharedPreferences sp2 = getSharedPreferences("pms", Context.MODE_PRIVATE);
        ivvisi= sp2.getString("qr","");
        if(ivvisi.equalsIgnoreCase("code"))
        {
            iv.setVisibility(View.VISIBLE);
            qrvalue.setVisibility(View.VISIBLE);
            sp2=getSharedPreferences("pms", Context.MODE_PRIVATE);
            edt = sp2.edit();
            edt.clear();
            edt.commit();
            PmsText.setTextColor(getResources().getColor(R.color.app_blue));
            InvText.setTextColor(getResources().getColor(R.color.black));
            PurText.setTextColor(getResources().getColor(R.color.black));

        }

        SharedPreferences sp1 = getSharedPreferences("inv", Context.MODE_PRIVATE);
        ivvisi= sp1.getString("qr","");
        if(ivvisi.equalsIgnoreCase("code"))
        {
            iv1.setVisibility(View.VISIBLE);
            qrtext1.setVisibility(View.VISIBLE);
            sp1=getSharedPreferences("inv", Context.MODE_PRIVATE);
            edt = sp1.edit();
            edt.clear();
            edt.commit();
            InvText.setTextColor(getResources().getColor(R.color.app_blue));
            PmsText.setTextColor(getResources().getColor(R.color.black));
            PurText.setTextColor(getResources().getColor(R.color.black));
        }

        SharedPreferences sp3 = getSharedPreferences("pur", Context.MODE_PRIVATE);
        ivvisi= sp3.getString("qr","");
        if(ivvisi.equalsIgnoreCase("code"))
        {
            iv2.setVisibility(View.VISIBLE);
            qrtext2.setVisibility(View.VISIBLE);
            sp3=getSharedPreferences("pur", Context.MODE_PRIVATE);
            edt = sp3.edit();
            edt.clear();
            edt.commit();
            InvText.setTextColor(getResources().getColor(R.color.black));
            PmsText.setTextColor(getResources().getColor(R.color.black));
            PurText.setTextColor(getResources().getColor(R.color.app_blue));
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
//        hsv1.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if(hsv1.getScrollX()==0) {
//                    leftNav1.setVisibility(View.GONE);
//                }
//                else
//                {
//                    leftNav1.setVisibility(View.VISIBLE);
//                }
//                int maxScrollX = hsv1.getChildAt(0).getMeasuredWidth()-hsv1.getMeasuredWidth();
//                if (hsv1.getScrollX()== maxScrollX)
//                {
//                    rightNav1.setVisibility(View.GONE);
//                }
//                else
//                {
//                    rightNav1.setVisibility(View.VISIBLE);
//                }
//                return false;
//            }
//        });
//
//
//
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
//        leftNav1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                hsv1.scrollTo((int)hsv1.getScrollX() -150, (int)hsv1.getScrollY()) ;
//                rightNav1.setVisibility(View.VISIBLE);
//                if(hsv1.getScrollX()==0) {
//                    leftNav1.setVisibility(View.GONE);
//                }
//                else
//                {
//                    leftNav1.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });
//
//        // Images right navigatin
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
//        rightNav1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                hsv1.scrollTo((int)hsv1.getScrollX() + 150, (int)hsv1.getScrollY());
//                leftNav1.setVisibility(View.VISIBLE);
//                int maxScrollX = hsv1.getChildAt(0).getMeasuredWidth()-hsv1.getMeasuredWidth();
//                if (hsv1.getScrollX()== maxScrollX)
//                {
//                    rightNav1.setVisibility(View.GONE);
//                }
//                else
//                {
//                    rightNav1.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp="1";
                qrscan.initiateScan();


            }
        });
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp="2";
                qrscan1.initiateScan();

            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp="3";
                qrscan2.initiateScan();

            }
        });

        R_pms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PmsText.setTextColor(getResources().getColor(R.color.app_blue));
                InvText.setTextColor(getResources().getColor(R.color.black));
                PurText.setTextColor(getResources().getColor(R.color.black));
                if(value.getVisibility()==View.VISIBLE || value1.getVisibility()==View.VISIBLE ||iv1.getVisibility()==View.VISIBLE) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            Home.this);

                    // set title
                    alertDialogBuilder.setTitle("Alert !!");
                    // set icon
                    // alertDialogBuilder.setIcon(R.drawable.fail);
                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Do u want to scan different QR ...!")
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            // if this button is clicked, close
                                            // current activity
                                            iv.setVisibility(View.VISIBLE);
                                            qrvalue.setVisibility(View.VISIBLE);
                                            value.setVisibility(View.INVISIBLE);
                                            fl.setVisibility(View.INVISIBLE);
                                            Pms_frame_rel.setVisibility(View.INVISIBLE);
                                            fl1.setVisibility(View.INVISIBLE);
                                            fl2.setVisibility(View.INVISIBLE);
                                            iv1.setVisibility(View.INVISIBLE);
                                            iv2.setVisibility(View.INVISIBLE);
                                            value1.setVisibility(View.INVISIBLE);
                                            value2.setVisibility(View.INVISIBLE);
                                            qrtext1.setVisibility(View.INVISIBLE);
                                            qrtext2.setVisibility(View.INVISIBLE);

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
                else
                {
                    MainSpinner_Lay.setVisibility(View.VISIBLE);
                    iv.setVisibility(View.GONE);
                    qrvalue.setVisibility(View.GONE);
                }

            }
        });
        InvLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InvText.setTextColor(getResources().getColor(R.color.app_blue));
                PmsText.setTextColor(getResources().getColor(R.color.black));
                PurText.setTextColor(getResources().getColor(R.color.black));
                if(value1.getVisibility()==View.VISIBLE || value.getVisibility()==View.VISIBLE||iv.getVisibility()==View.VISIBLE) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            Home.this);

                    // set title
                    alertDialogBuilder.setTitle("Alert !!");
                    // set icon
                    // alertDialogBuilder.setIcon(R.drawable.fail);
                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Do u want to scan different QR ...!")
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            // if this button is clicked, close
                                            // current activity
                                            iv1.setVisibility(View.VISIBLE);
                                            qrtext1.setVisibility(View.VISIBLE);
                                            value1.setVisibility(View.INVISIBLE);
                                            fl.setVisibility(View.INVISIBLE);
                                            Pms_frame_rel.setVisibility(View.INVISIBLE);
                                            fl1.setVisibility(View.INVISIBLE);
                                            fl2.setVisibility(View.INVISIBLE);
                                            value.setVisibility(View.INVISIBLE);
                                            value1.setVisibility(View.INVISIBLE);
                                            value2.setVisibility(View.INVISIBLE);
                                            iv.setVisibility(View.INVISIBLE);
                                            iv1.setVisibility(View.INVISIBLE);
                                            iv2.setVisibility(View.INVISIBLE);
                                            qrvalue.setVisibility(View.INVISIBLE);

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
                else
                {
                    SideSpinner_Lay.setVisibility(View.GONE);
                    MainSpinner_Lay.setVisibility(View.GONE);
                    SideSpinner1_Lay.setVisibility(View.GONE);
                    VesselSpinner_Lay.setVisibility(View.GONE);
                    iv1.setVisibility(View.VISIBLE);
                    qrtext1.setVisibility(View.VISIBLE);
                }

            }

        });

        Purchase_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InvText.setTextColor(getResources().getColor(R.color.black));
                PmsText.setTextColor(getResources().getColor(R.color.black));
                PurText.setTextColor(getResources().getColor(R.color.app_blue));
                if(value2.getVisibility()==View.VISIBLE || value.getVisibility()==View.VISIBLE||iv2.getVisibility()==View.VISIBLE) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            Home.this);

                    // set title
                    alertDialogBuilder.setTitle("Alert !!");
                    // set icon
                    // alertDialogBuilder.setIcon(R.drawable.fail);
                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Do u want to scan different QR ...!")
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            // if this button is clicked, close
                                            // current activity
                                            iv2.setVisibility(View.VISIBLE);
                                            qrtext2.setVisibility(View.VISIBLE);
                                            value2.setVisibility(View.INVISIBLE);
                                            fl.setVisibility(View.INVISIBLE);
                                            Pms_frame_rel.setVisibility(View.INVISIBLE);
                                            fl1.setVisibility(View.INVISIBLE);
                                            fl2.setVisibility(View.INVISIBLE);
                                            value.setVisibility(View.INVISIBLE);
                                            value1.setVisibility(View.INVISIBLE);
                                            value2.setVisibility(View.INVISIBLE);
                                            iv.setVisibility(View.INVISIBLE);
                                            iv1.setVisibility(View.INVISIBLE);
                                            iv2.setVisibility(View.INVISIBLE);
                                            qrvalue.setVisibility(View.INVISIBLE);

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
                else
                {
                    SideSpinner_Lay.setVisibility(View.GONE);
                    MainSpinner_Lay.setVisibility(View.GONE);
                    SideSpinner1_Lay.setVisibility(View.GONE);
                    VesselSpinner_Lay.setVisibility(View.GONE);
                    iv2.setVisibility(View.VISIBLE);
                    qrtext2.setVisibility(View.VISIBLE);
                }

            }

        });




        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });

        EquipmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (EquipmentSpinner.getSelectedItem().toString().equalsIgnoreCase("--Select--")) {


                    } else if (EquipmentSpinner.getSelectedItem().toString().equalsIgnoreCase("QR")) {
                        iv.setVisibility(View.VISIBLE);
                        qrvalue.setVisibility(View.VISIBLE);
                        SideSpinner_Lay.setVisibility(View.GONE);
                        MainSpinner_Lay.setVisibility(View.GONE);
                        SideSpinner1_Lay.setVisibility(View.GONE);
                        VesselSpinner_Lay.setVisibility(View.GONE);
                    }
                        else if (EquipmentSpinner.getSelectedItem().toString().equalsIgnoreCase("Non QR")) {
                        SideSpinner1_Lay.setVisibility(View.VISIBLE);
                         new AsyncCheckLoginAuditName().execute();
                        VesselSpinner_Lay.setVisibility(View.VISIBLE);
                        SideSpinner_Lay.setVisibility(View.VISIBLE);
                        } else {

                        }
                    }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        MethodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (MethodSpinner.getSelectedItem().toString().equalsIgnoreCase("--Select--")) {

                }
                else if(MethodSpinner.getSelectedItem().toString().equalsIgnoreCase("Maintenance")){
                        Completion = "1";
                    SideSpinner_Lay.setVisibility(View.VISIBLE);
                    SideSpinner2_Lay.setVisibility(View.GONE);
                }
                else if(MethodSpinner.getSelectedItem().toString().equalsIgnoreCase("Parameter")){
                    Completion = "2";
                    SideSpinner_Lay.setVisibility(View.GONE);
                    SideSpinner2_Lay.setVisibility(View.VISIBLE);

                }
                else if(MethodSpinner.getSelectedItem().toString().equalsIgnoreCase("Calibration")){
                    Completion = "3";
                    SideSpinner_Lay.setVisibility(View.VISIBLE);
                    SideSpinner2_Lay.setVisibility(View.GONE);
                }
                else if(MethodSpinner.getSelectedItem().toString().equalsIgnoreCase("Safety Alarm")){
                    Completion = "4";
                    SideSpinner_Lay.setVisibility(View.VISIBLE);
                    SideSpinner2_Lay.setVisibility(View.GONE);
                }
                else if(MethodSpinner.getSelectedItem().toString().equalsIgnoreCase("Run Hour")){
                    Completion = "5";
                    SideSpinner_Lay.setVisibility(View.GONE);
                    SideSpinner2_Lay.setVisibility(View.GONE);

                    sp4 = getSharedPreferences("dueoverdue", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp4.edit();
                    if(EntityValue != "") {
                        edit.putString("vessel", EntityValue);
                    }
                    edit.putString("completion",Completion);
                    edit.putString("Due","0");
                    edit.putString("Usercode",USerCode);
                    edit.putString("Languagecode",LangCode);
                    edit.commit();
                    Intent Due= new Intent(Home.this,Dueoverdue.class);
                    startActivity(Due);
                    finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        EntitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sa = EntitySpinner.getSelectedItem().toString();
                if (sa.equalsIgnoreCase("--Select--")) {

                } else {
                    for (int j = 0; j < l1.size(); j++) {
                        if (EntitySpinner.getItemIdAtPosition(i) == j) {
                            EntityValue = l1.get(j).toString();
                            EntityNameValue=l2.get(j).toString();

                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        DueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (DueSpinner.getSelectedItem().toString().equalsIgnoreCase("--Select--")) {


                } else if (DueSpinner.getSelectedItem().toString().equalsIgnoreCase("Due")) {
                    String due = "1";
                    sp4 = getSharedPreferences("dueoverdue", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp4.edit();
                    if(EntityValue != "") {
                        edit.putString("vessel", EntityValue);
                    }
                    edit.putString("completion",Completion);
                    edit.putString("Due",due);
                    edit.putString("Usercode",USerCode);
                    edit.putString("Languagecode",LangCode);
                    edit.commit();
                    Intent Due= new Intent(Home.this,Dueoverdue.class);
                    startActivity(Due);
                    finish();
                }
                else if (DueSpinner.getSelectedItem().toString().equalsIgnoreCase("OverDue")) {
                    String due = "2";
                    sp4 = getSharedPreferences("dueoverdue", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp4.edit();
                    if(EntityValue != "") {
                        edit.putString("vessel", EntityValue);
                    }
                    edit.putString("completion",Completion);
                    edit.putString("Due",due);
                    edit.putString("Usercode",USerCode);
                    edit.putString("Languagecode",LangCode);
                    edit.commit();
                    Intent Due= new Intent(Home.this,Dueoverdue.class);
                    startActivity(Due);
                    finish();
                } else {

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ParamterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (ParamterSpinner.getSelectedItem().toString().equalsIgnoreCase("--Select--")) {

                }
                else if(ParamterSpinner.getSelectedItem().toString().equalsIgnoreCase("Temperature")){
                    String due = "1";
                    sp4 = getSharedPreferences("dueoverdue", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp4.edit();
                    if(EntityValue != "") {
                        edit.putString("vessel", EntityValue);
                    }
                    edit.putString("completion",Completion);
                    edit.putString("Due",due);
                    edit.putString("Usercode",USerCode);
                    edit.putString("Languagecode",LangCode);
                    edit.commit();
                    Intent Due= new Intent(Home.this,Dueoverdue.class);
                    startActivity(Due);
                    finish();
                }
                else if(ParamterSpinner.getSelectedItem().toString().equalsIgnoreCase("Pressure")){
                    String due = "2";
                    sp4 = getSharedPreferences("dueoverdue", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp4.edit();
                    if(EntityValue != "") {
                        edit.putString("vessel", EntityValue);
                    }
                    edit.putString("completion",Completion);
                    edit.putString("Due",due);
                    edit.putString("Usercode",USerCode);
                    edit.putString("Languagecode",LangCode);
                    edit.commit();
                    Intent Due= new Intent(Home.this,Dueoverdue.class);
                    startActivity(Due);
                    finish();
                }
                else if(ParamterSpinner.getSelectedItem().toString().equalsIgnoreCase("Insulation")){
                    String due = "3";
                    sp4 = getSharedPreferences("dueoverdue", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp4.edit();
                    if(EntityValue != "") {
                        edit.putString("vessel", EntityValue);
                    }
                    edit.putString("completion",Completion);
                    edit.putString("Due",due);
                    edit.putString("Usercode",USerCode);
                    edit.putString("Languagecode",LangCode);
                    edit.commit();
                    Intent Due= new Intent(Home.this,Dueoverdue.class);
                    startActivity(Due);
                    finish();
                }
                else if(ParamterSpinner.getSelectedItem().toString().equalsIgnoreCase("Vibration")){
                    String due = "4";
                    sp4 = getSharedPreferences("dueoverdue", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp4.edit();
                    if(EntityValue != "") {
                        edit.putString("vessel", EntityValue);
                    }
                    edit.putString("completion",Completion);
                    edit.putString("Due",due);
                    edit.putString("Usercode",USerCode);
                    edit.putString("Languagecode",LangCode);
                    edit.commit();
                    Intent Due= new Intent(Home.this,Dueoverdue.class);
                    startActivity(Due);
                    finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Main_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {
                    SharedPreferences pref = getSharedPreferences("pmsvalues", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("eqname", eqname);
                    edit.putString("eqitem", eqitem);
                    edit.putString("eqent", eqent);
                    edit.putString("USerCode", USerCode);
                    edit.commit();
                    Intent i= new Intent(Home.this,PmsMainList.class);
                    startActivity(i);
                    finish();
                }else {
                    Toast.makeText(Home.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }


            }
        });
        cal_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {
                    SharedPreferences pref = getSharedPreferences("calvalues", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("eqname", eqname);
                    edit.putString("eqitem", eqitem);
                    edit.putString("eqent", eqent);
                    edit.putString("USerCode", USerCode);
                    edit.putString("LangCode", LangCode);
                    edit.commit();
                    Intent i= new Intent(Home.this,CalMainList.class);
                    startActivity(i);
                    finish();
                }else {
                    Toast.makeText(Home.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }


            }
        });
        safety_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {
                    SharedPreferences pref = getSharedPreferences("safetyvalues", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("eqname", eqname);
                    edit.putString("eqitem", eqitem);
                    edit.putString("eqent", eqent);
                    edit.putString("USerCode", USerCode);
                    edit.putString("LangCode", LangCode);
                    edit.commit();
                    Intent i= new Intent(Home.this,SafetyList.class);
                    startActivity(i);
                    finish();
                }else {
                    Toast.makeText(Home.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }


            }
        });
        Para_Lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {
                    SharedPreferences pref = getSharedPreferences("paramvalues", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("eqname", eqname);
                    edit.putString("eqitem", eqitem);
                    edit.putString("eqent", eqent);
                    edit.putString("USerCode", USerCode);
                    edit.putString("LangCode", LangCode);
                    edit.commit();
                    Intent i= new Intent(Home.this,ParameterList.class);
                    startActivity(i);
                    finish();
                }else {
                    Toast.makeText(Home.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }


            }
        });
        Runhour_Lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {
                    SharedPreferences pref = getSharedPreferences("runhrvalues", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("eqname", eqname);
                    edit.putString("eqitem", eqitem);
                    edit.putString("eqent", eqent);
                    edit.putString("USerCode", USerCode);
                    edit.putString("LangCode", LangCode);
                    edit.commit();
                    Intent i= new Intent(Home.this,RunHourCompletion.class);
                    startActivity(i);
                    finish();
                }else {
                    Toast.makeText(Home.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }


            }
        });
        Inv_oil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(value1.getText().toString().contains("Category : Oil")) {
                    OilText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    PartText.setTextColor(getResources().getColor(R.color.lightgrey));
                    StoreText.setTextColor(getResources().getColor(R.color.lightgrey));


                    SharedPreferences pref = getSharedPreferences("qr", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("values", value1.getText().toString());
                    edit.putString("eqitem", eqitem);
                    edit.putString("eqent", eqent);
                    edit.commit();
                    Intent oil = new Intent(Home.this, InvOil.class);
                    startActivity(oil);
                    finish();
                }
                else
                {
                    Toast.makeText(Home.this, "QR Not Valid For Oil", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Inv_part.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(value1.getText().toString().contains("Category : Part")) {
                    PartText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    OilText.setTextColor(getResources().getColor(R.color.lightgrey));
                    StoreText.setTextColor(getResources().getColor(R.color.lightgrey));

                    SharedPreferences pref = getSharedPreferences("qr", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("values", value1.getText().toString());
                    edit.putString("eqitem", eqitem);
                    edit.putString("eqent", eqent);
                    edit.commit();
                    Intent part=new Intent(Home.this,InvOil.class);
                    startActivity(part);
                    finish();
                }
                else
                {
                    Toast.makeText(Home.this, "QR Not Valid For Part", Toast.LENGTH_SHORT).show();
                }
            }

        });
        Inv_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(value1.getText().toString().contains("Category : Store")) {
                    StoreText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    PartText.setTextColor(getResources().getColor(R.color.lightgrey));
                    OilText.setTextColor(getResources().getColor(R.color.lightgrey));

                    SharedPreferences pref = getSharedPreferences("qr", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("values", value1.getText().toString());
                    edit.putString("eqitem", eqitem);
                    edit.putString("eqent", eqent);
                    edit.commit();
                    Intent stores=new Intent(Home.this,InvOil.class);
                    startActivity(stores);
                    finish();
                }
                else
                {
                    Toast.makeText(Home.this, "QR Not Valid For Store", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Home.this, "User Logged Out !", Toast.LENGTH_SHORT).show();


//                        SharedPreferences pref = getSharedPreferences("logoutvalue", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor edit = pref.edit();
//                        edit.putString("logval", "logout");
//                        edit.commit();

                        Intent inn = new Intent(Home.this, Login.class);
                        startActivity(inn);
                        finish();
                        break;

                    }

                    case R.id.churl: {
                        sp=getSharedPreferences("loginreg", Context.MODE_PRIVATE);
                        String UserName = sp.getString("username", "");
                        String Password = sp.getString("password", "");

                        SharedPreferences pref = getSharedPreferences("back", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = pref.edit();
                        edit.putString("comeback", "homescreen");
                        edit.commit();

                        if (UserName.equalsIgnoreCase("superuser") && Password.equalsIgnoreCase("SUPER")) {
                            Intent inc = new Intent(Home.this, Url.class);
                            startActivity(inc);
                            break;
                        } else {
                            AlertDialog.Builder adb = new AlertDialog.Builder(Home.this);
                            adb.setTitle("Information");
                            adb.setMessage("This user does not have the rights to change the URL !");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Record Not Found", Toast.LENGTH_SHORT).show();
            } else {
//                value.setText(result.getContents());
                if(temp.equalsIgnoreCase(""))
                {
                    Toast.makeText(this, "No value obtained scan QR again", Toast.LENGTH_SHORT).show();
                }
                else if(temp.equalsIgnoreCase("1")) {
                    res = result.getContents();
                    String check1 = res.toUpperCase();


                            String[] cutsample = res.split("\n");
                            String[] name = cutsample[0].split(":");
                             checkcat = name[1];
                    String[] name1 = cutsample[1].split(":");
                     checkuniq = name1[1];
                            finalvalue = checkuniq;

                            finalvalue1 = checkcat;

                    if (checkcat.contains("9")) {

                        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();

                        if (activeNetwork != null && activeNetwork.isConnected()) {
                            new AsyncCheckLogin().execute();
                        } else {
                            Toast.makeText(Home.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        showAlertDialog(Home.this, "Alert !!",
                                "This Is PMS Module ! Please scan a proper PMS equipment", false);
                    }

                }
                else if(temp.equalsIgnoreCase("2"))
                {
                    oil = result.getContents();

                    String[] cutsample = oil.split("\n");
                    String[] name = cutsample[0].split(":");
                    checkcat = name[1];
                    String[] name1 = cutsample[1].split(":");
                    checkuniq = name1[1];
                    finalvalue = checkuniq;

                    finalvalue1 = checkcat;

                    if (checkcat.contains("9")) {
                        showAlertDialog(Home.this, "Alert !!",
                                "This Is INVENTORY Module ! Please scan a proper INVENTORY equipment", false);
                    } else {

                            final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                            final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();

                            if (activeNetwork != null && activeNetwork.isConnected()) {
                                new AsyncCheckLoginInv().execute();
                            }else {
                                Toast.makeText(Home.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                            }

                    }
                }
                else if(temp.equalsIgnoreCase("3"))
                {
                    purres = result.getContents();

                    String[] posample = purres.split("\n");
                    String[] name = posample[0].split(":");
                    checkuniq = name[1];
                    String[] name1 = posample[1].split(":");
                    checkcat = name1[1];
                    finalvalue = checkuniq;

                    finalvalue1 = checkcat;

                    if (checkcat.contains("10")) {
                        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();

                        if (activeNetwork != null && activeNetwork.isConnected()) {
                            new AsyncCheckLoginPur().execute();
                        }else {
                            Toast.makeText(Home.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        showAlertDialog(Home.this, "Alert !!",
                                "This Is PURCHASE Module ! Please scan a proper PURCHASE equipment", false);
                    }
                }

            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(iv.getVisibility()==View.VISIBLE) {
                Intent i=new Intent(Home.this,Home.class);
                startActivity(i);
                finish();
            }
            else
            {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        Home.this);

                alertDialogBuilder.setTitle("Alert !!");

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

                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();

            }
        }
        return false;
    }
    public class AsyncCheckLogin extends AsyncTask<Void, Void, Void> {
        String error = "";
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(Home.this, "Please wait...","It may take few seconds to Load...", true);

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

                    String name = strMsg;
                    ArrayList<String> textlist = new ArrayList<String>();
                    String[] cut = name.split("anyType=");
                    for (int i = 0; i < cut.length; i++) {
                        if (i != 0) {
                            textlist.add(cut[i]);
                        }
                    }
                    for (int j = 0; j < textlist.size(); j++) {
                        String checking = textlist.get(j);
                        String[] value1 = checking.split(";");
                        check.add(value1[0].toString());
                    }


                    for (int k = 0; k < check.size(); k++) {
                        if (check.get(k).contains("Unique Id :")) {

                        } else if (check.get(k).contains("Entity :")) {

                        } else {
                            trimvalues.add(check.get(k));
                        }

                    }

                    for (int i = 0; i < trimvalues.size(); i++) {
                        value.append(trimvalues.get(i));

                    }
                    for (int j = 0; j < check.size(); j++) {

                        if (check.get(j).contains("Equipment Name :")) {
                            eqname = check.get(j);
                        }

                        if (check.get(j).contains("Unique Id :")) {
                            id = check.get(j);
                        }
                        if (check.get(j).contains("Entity :")) {
                            ent = check.get(j);

                        }

                    }
                    String[] sep1 = id.split(":");
                    eqitem = sep1[1];

                    String[] sep2 = ent.split(":");
                    eqent = sep2[1].trim();

                    if (eqent.equalsIgnoreCase("0")) {
                        showAlertDialog(Home.this, "Alert",
                                "Please scan the vessel based QR code for any transaction", false);
                    } else {

                        aas.add(eqname);
                        aas.add(eqactive);
                        aas.add(eqcode);
                        aas.add(eqmodel);
                        aas.add(active);
                        aas.add(cat);

//                value.setVisibility(View.VISIBLE);
                        iv.setVisibility(View.INVISIBLE);
                        qrvalue.setVisibility(View.INVISIBLE);
                        iv1.setVisibility(View.INVISIBLE);
                        qrtext1.setVisibility(View.INVISIBLE);
                        value1.setVisibility(View.INVISIBLE);

                        SharedPreferences pref = getSharedPreferences("subActivity", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = pref.edit();
                        edit.putString("value", value.getText().toString());
                        edit.putString("eqname", eqname);
                        edit.putString("eqitem", eqitem);
                        edit.putString("eqent", eqent);
                        edit.putString("USerCode", USerCode);
                        edit.putString("LangCode", LangCode);
                        edit.commit();
                        Intent i = new Intent(Home.this, HomeSub.class);
                        startActivity(i);
                        finish();
                    }


                } else if (registerstatus.equalsIgnoreCase("Failed")) {
                    showAlertDialog(Home.this, "Alert",
                            "No Record's Available", false);
                } else if (registerstatus.equalsIgnoreCase("")) {
                    Toast.makeText(Home.this, strMsg, Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(Home.this, "Successful", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Home.this, "Internet Connection Failed ! Please Try Again", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void call() {
        try {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);

            request.addProperty("uniquekey", finalvalue);
            request.addProperty("category", finalvalue1);

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
                    aaslist.add(response.toString());
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

    public class AsyncCheckLoginInv extends AsyncTask<Void, Void, Void> {
        String error = "";
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(Home.this, "Please wait...","It may take few seconds to Load...", true);

        }

        @Override
        protected Void doInBackground(Void... params) {
            callInv();
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

                    String name = strMsgInv;
                    ArrayList<String> textlist = new ArrayList<String>();
                    String[] cut = name.split("anyType=");
                    for (int i = 0; i < cut.length; i++) {
                        if (i != 0) {
                            textlist.add(cut[i]);
                        }
                    }
                    for (int j = 0; j < textlist.size(); j++) {
                        String checking = textlist.get(j);
                        String[] value1 = checking.split(";");
                        check.add(value1[0].toString());
                    }

                    for (int k = 0; k < check.size(); k++) {
                        if (check.get(k).contains("Unique Id :")) {

                        } else if (check.get(k).contains("Entity :")) {

                        } else {
                            trimvalues.add(check.get(k));
                        }

                    }

                    for (int i = 0; i < trimvalues.size(); i++) {
                        value1.append(trimvalues.get(i));

                    }
                    for (int j = 0; j < check.size(); j++) {

                        if (check.get(j).contains("Equipment Name :")) {
                            eqname = check.get(j);
                        }

                        if (check.get(j).contains("Unique Id :")) {
                            id = check.get(j);
                        }
                        if (check.get(j).contains("Entity :")) {
                            ent = check.get(j);

                        }

                    }
                    String[] sep1 = id.split(":");
                    eqitem = sep1[1];

                    String[] sep2 = ent.split(":");
                    eqent = sep2[1].trim();

                    if (eqent.equalsIgnoreCase("0")) {
                        showAlertDialog(Home.this, "Alert",
                                "Please scan the vessel based QR code for any transaction", false);
                    } else {

//                fl1.setVisibility(View.VISIBLE);
                        fl2.setVisibility(View.INVISIBLE);
                        fl1.setVisibility(View.INVISIBLE);
                        fl.setVisibility(View.INVISIBLE);
                        Pms_frame_rel.setVisibility(View.INVISIBLE);
                        qrtext1.setVisibility(View.INVISIBLE);

//                value1.setVisibility(View.VISIBLE);
                        value1.setVisibility(View.INVISIBLE);
                        value.setVisibility(View.INVISIBLE);
                        iv.setVisibility(View.INVISIBLE);
                        qrvalue.setVisibility(View.INVISIBLE);
                        iv1.setVisibility(View.INVISIBLE);
                        SharedPreferences prefold = getSharedPreferences("oil", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editold = prefold.edit();
                        editold.putString("values", value1.getText().toString());
                        editold.commit();

                        SharedPreferences pref = getSharedPreferences("qr", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = pref.edit();
                        edit.putString("values", value1.getText().toString());
                        edit.putString("eqitem", eqitem);
                        edit.putString("eqent", eqent);
                        edit.commit();
                        Intent oil = new Intent(Home.this, InvOil.class);
                        startActivity(oil);
                        finish();
                    }


                } else if (registerstatus.equalsIgnoreCase("Failed")) {
                    showAlertDialog(Home.this, "Alert",
                            "No Record's Available", false);
                } else if (registerstatus.equalsIgnoreCase("Failed1")) {
                    showAlertDialog(Home.this, "Alert",
                            "QR Available only for Oil, Parts And Stores", false);
                }else if (registerstatus.equalsIgnoreCase("")) {
                    Toast.makeText(Home.this, strMsg, Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(Home.this, "Successful", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Home.this, "Internet Connection Failed ! Please Try Again", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void callInv() {
        try {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);

            request.addProperty("uniquekey", finalvalue);
            request.addProperty("category", finalvalue1);
            if (finalvalue1.contains("5") || finalvalue1.contains("6") || finalvalue1.contains("8"))
            {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(URL);
            Object response = null;
            try {
                httpTransport.call(SOAP_ACTION, envelope);

                response = envelope.getResponse();

                strMsgInv = response.toString();
                if (strMsgInv.equalsIgnoreCase("Failed")) {
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
            }
            else
            {
                registerstatus = "Failed1";
            }
        } catch (Exception e) {
            Log.i("error", e.getMessage().toString());
        }
    }

    public class AsyncCheckLoginPur extends AsyncTask<Void, Void, Void> {
        String error = "";
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(Home.this, "Please wait...","It may take few seconds to Load...", true);

        }

        @Override
        protected Void doInBackground(Void... params) {
            callPur();

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
                    String name = strMsgPur;
                    ArrayList<String> textlist = new ArrayList<String>();
                    String[] cut = name.split("anyType=");

                    for (int i = 0; i < cut.length; i++) {
                        if (i != 0) {
                            textlist.add(cut[i]);
                        }
                    }
                    for (int j = 0; j < textlist.size(); j++) {
                        String checking = textlist.get(j);
                        String[] value1 = checking.split(";");
                        check.add(value1[0].toString());
                    }

                    for (int k = 0; k < check.size(); k++) {

                            trimvalues.add(check.get(k));
                    }

                    for (int i = 0; i < trimvalues.size(); i++) {
                        value2.append(trimvalues.get(i));

                   }

//                    for (int j = 0; j < check.size(); j++) {

//                        if (check.get(j).contains("Equipment Name :")) {
//                            eqname = check.get(j);
//                        }

//                        if (check.get(j).contains("Unique Id :")) {
//                            id = check.get(j);
//                        }
//                        if (check.get(j).contains("Entity :")) {
//                            ent = check.get(j);

//                        }

//                    }
//                    String[] sep1 = id.split(":");
//                    eqitem = sep1[1];

//                    String[] sep2 = ent.split(":");
//                    eqent = sep2[1].trim();

//                    if (eqent.equalsIgnoreCase("0")) {
//                        showAlertDialog(Home.this, "Alert",
//                                "Please scan the vessel based QR code for any transaction", false);
//                    Toast.makeText(Home.this, "check3", Toast.LENGTH_SHORT).show();
                    if(check.size() != 0)
                    {
//                fl1.setVisibility(View.VISIBLE);
                        Toast.makeText(Home.this, "check1", Toast.LENGTH_SHORT).show();
                         fl1.setVisibility(View.INVISIBLE);
                         fl.setVisibility(View.INVISIBLE);
                         Pms_frame_rel.setVisibility(View.INVISIBLE);
                         qrtext1.setVisibility(View.INVISIBLE);

//                value1.setVisibility(View.VISIBLE);
                         value1.setVisibility(View.INVISIBLE);
                         value.setVisibility(View.INVISIBLE);
                         iv.setVisibility(View.INVISIBLE);
                        qrvalue.setVisibility(View.INVISIBLE);
                         iv1.setVisibility(View.INVISIBLE);
                        Toast.makeText(Home.this, "Successful", Toast.LENGTH_SHORT).show();

                        SharedPreferences pref = getSharedPreferences("purActivity", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = pref.edit();
                       edit.putString("value", value2.getText().toString());
                        edit.commit();

                        Intent pur= new Intent(Home.this,PurSub.class);
                        startActivity(pur);
                        finish();
                    }


                } else if (registerstatus.equalsIgnoreCase("Failed")) {
                    showAlertDialog(Home.this, "Alert",
                            "No Record's Available", false);
                } else if (registerstatus.equalsIgnoreCase("")) {
                    Toast.makeText(Home.this, strMsg, Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(Home.this, "Successful", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Home.this, "Internet Connection Failed ! Please Try Again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void callPur() {
        try {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);

            request.addProperty("uniquekey", finalvalue);
            request.addProperty("category", finalvalue1);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE httpTransport = new HttpTransportSE(URL);
                Object response = null;
                try {
                    httpTransport.call(SOAP_ACTION, envelope);

                    response = envelope.getResponse();

                    strMsgPur = response.toString();

                    if (strMsgPur.equalsIgnoreCase("Failed")) {
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

    public class AsyncCheckLoginAuditName extends AsyncTask<Void, Void, Void> {
        String error = "";
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(Home.this, "Please wait...","It may take few seconds to Load...", true);

        }

        @Override
        protected Void doInBackground(Void... params) {

            callAuditName();

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
//                    l1.add("--Select--");
//                    l2.add("--Select--");
                    if (strMsg.equalsIgnoreCase("")){
                        Toast.makeText(Home.this, "Error in network connection ! Try again !", Toast.LENGTH_SHORT).show();
                    } else if (strMsg.equalsIgnoreCase("null")){
                        Toast.makeText(Home.this, "Error in network connection ! Try again !", Toast.LENGTH_SHORT).show();
                    } else {

                        cut = strMsg.split("@");

                        int length = cut.length;

                        if (length == 1) {

                            String[] cut1 = strMsg.split(",");

                            if (cut1.length > 0)
                                Entitycode = cut1[0].toString();

                            if (cut1.length > 1)
                                Entityname = cut1[1].toString();

//                        SpinnerOption.setText(Entityname);
//                        SpinnerOption.setVisibility(View.VISIBLE);
                            Arrow.setVisibility(View.INVISIBLE);
                            //EntitySpinner.setVisibility(View.INVISIBLE);
                            l1.add(Entitycode);
                            l2.add(Entityname);

                            all.add(Entitycode + " , " + Entityname);

                        } else {
                            l1.add("--Select--");
                            l2.add("--Select--");

                            //SpinnerOption.setVisibility(View.INVISIBLE);
                            Arrow.setVisibility(View.VISIBLE);
                            //EntitySpinner.setVisibility(View.VISIBLE);

                            for (int j = 0; j < cut.length; j++) {

                                String[] cut1 = cut[j].split(",");

                                if (cut1.length > 0)
                                    Entitycode = cut1[0].toString();

                                if (cut1.length > 1)
                                    Entityname = cut1[1].toString();


                                l1.add(Entitycode);
                                l2.add(Entityname);

                                all.add(Entitycode + " , " + Entityname);
                            }
                        }

                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Home.this, android.R.layout.simple_spinner_item, l2);

                        dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

                        EntitySpinner.setAdapter(dataAdapter);
                    }



                } else if (registerstatus.equalsIgnoreCase("Failed")) {
                    showAlertDialog(Home.this, "Alert",
                            "No Record's Available", false);
                } else if (registerstatus.equalsIgnoreCase("")) {
                    Toast.makeText(Home.this, "Network Connection Failed ! Try again", Toast.LENGTH_LONG).show();
                    SideSpinner_Lay.setVisibility(View.GONE);
                    MainSpinner_Lay.setVisibility(View.GONE);
                    SideSpinner1_Lay.setVisibility(View.GONE);
                    VesselSpinner_Lay.setVisibility(View.GONE);
                    EquipmentSpinner.setSelection(0);

                } else if (registerstatus.equalsIgnoreCase("null")) {
                    Toast.makeText(Home.this, "Network Connection Failed ! Try again", Toast.LENGTH_LONG).show();
                    SideSpinner_Lay.setVisibility(View.GONE);
                    MainSpinner_Lay.setVisibility(View.GONE);
                    SideSpinner1_Lay.setVisibility(View.GONE);
                    VesselSpinner_Lay.setVisibility(View.GONE);
                    EquipmentSpinner.setSelection(0);
                } else {
                    Toast.makeText(Home.this, "Successful", Toast.LENGTH_SHORT).show();
                }
            } else {

                Toast.makeText(Home.this, "Internet Connection Failed ! Please Try Again", Toast.LENGTH_SHORT).show();
                SideSpinner_Lay.setVisibility(View.GONE);
                MainSpinner_Lay.setVisibility(View.GONE);
                SideSpinner1_Lay.setVisibility(View.GONE);
                VesselSpinner_Lay.setVisibility(View.GONE);
                EquipmentSpinner.setSelection(0);
            }
        }

    }
    public void callAuditName() {
        try {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME1);
            request.addProperty("Usercode",USerCode);
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



}
