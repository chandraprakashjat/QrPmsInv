package com.example.arvindhan.qr;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.arvindhan.parameter.R;

import java.util.ArrayList;

public class HomeSub extends AppCompatActivity {

    ImageView leftNav,rightNav,leftNav1,rightNav1,iv_menu;
    ImageView purchase,repair,finance,safety;
    HorizontalScrollView hsv,hsv1;
    RelativeLayout R_pms,Main_Done,InvLay,Purchase_lay,Repair_lay,Finance_lay,Safety_lay,Inv_oil,Inv_part,Inv_store,Para_Lay,cal_rel,safety_rel,Runhour_Lay,Pms_frame_rel,Spinner_Lay;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    FrameLayout fl,fl1;
    ImageView iv,iv1;
    TextView tv1;
    public static TextView value,value1,qrtext1,User_code,qrvalue,OilText,PartText,StoreText,PmsText,InvText,Pms_list_texty;
    String oil_name,oil_type,oil_unit,eqitem,eqent,userName, password,USerCode,LangCode,res,eqname,oil,temp=null,qrret=null,ivvisi;
    String eqcode,eqmodel,id,ent;
//    private IntentIntegrator qrscan;
//    private IntentIntegrator qrscan1;

    String newvalue;

    sqlconnect sq=new sqlconnect(this);
    ArrayList l1= new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration config = getResources().getConfiguration();
        if (config.smallestScreenWidthDp >= 600) {
            setContentView(R.layout.activity_home_sub_tab);
        } else {
            setContentView(R.layout.activity_home_sub);
        }

        iv = (ImageView) findViewById(R.id.qr_img);
        iv1 = (ImageView) findViewById(R.id.qr_img1);
        value = (TextView) findViewById(R.id.text_qr);
        value1 = (TextView) findViewById(R.id.text_qr1);
        tv1 = (TextView) findViewById(R.id.tv1);
        fl=(FrameLayout)findViewById(R.id.frame_layout1);
        fl1=(FrameLayout)findViewById(R.id.frame_layout2);
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
        Pms_list_texty=(TextView) findViewById(R.id.pms_text);



        iv_menu=(ImageView)findViewById(R.id.optionmenu);
        User_code = (TextView) findViewById(R.id.usercode);
        qrvalue = (TextView) findViewById(R.id.qrvalue);
        qrtext1=(TextView) findViewById(R.id.qrvalue1);
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

        purchase=(ImageView)findViewById(R.id.img_crw1);
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
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(HomeSub.this);
        userName = sp.getString("username", "");
        password = sp.getString("password", "");
        USerCode=sp.getString("usercode", "");
        LangCode=sp.getString("langcode", "");

        SharedPreferences subs = getSharedPreferences("subActivity", Context.MODE_PRIVATE);
        newvalue= subs.getString("value","");
        eqname= subs.getString("eqname","");
        eqitem= subs.getString("eqitem","");
        eqent= subs.getString("eqent","");
        USerCode= subs.getString("USerCode","");
        LangCode= subs.getString("LangCode","");
        Pms_list_texty.setTextColor(getResources().getColor(R.color.app_blue));

        value.setText(newvalue);

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
        }

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
//        iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                temp="1";
//                qrscan.initiateScan();
//
//
//            }
//        });
//        iv1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                temp="2";
//                qrscan1.initiateScan();
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
                Intent inn = new Intent(HomeSub.this, Home.class);
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
                Intent inn = new Intent(HomeSub.this, Home.class);
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

                    SharedPreferences pref1 = getSharedPreferences("exitvalues", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit1 = pref1.edit();
                    edit1.putString("exitvalue","1");
                    edit1.commit();

                    Intent i= new Intent(HomeSub.this,PmsMainList.class);
                    startActivity(i);
                    finish();
                }else {
                    Toast.makeText(HomeSub.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
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
                    Intent i= new Intent(HomeSub.this,CalMainList.class);
                    startActivity(i);
                    finish();
                }else {
                    Toast.makeText(HomeSub.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
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
                    Intent i= new Intent(HomeSub.this,SafetyList.class);
                    startActivity(i);
                    finish();
                }else {
                    Toast.makeText(HomeSub.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
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
                    Intent i= new Intent(HomeSub.this,ParameterList.class);
                    startActivity(i);
                    finish();
                }else {
                    Toast.makeText(HomeSub.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
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
                    Intent i= new Intent(HomeSub.this,RunHourCompletion.class);
                    startActivity(i);
                    finish();
                }else {
                    Toast.makeText(HomeSub.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
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
                    Intent oil = new Intent(HomeSub.this, InvOil.class);
                    startActivity(oil);
                    finish();
                }
                else
                {
                    Toast.makeText(HomeSub.this, "QR Not Valid For Oil", Toast.LENGTH_SHORT).show();
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
                    Intent part=new Intent(HomeSub.this,InvOil.class);
                    startActivity(part);
                    finish();
                }
                else
                {
                    Toast.makeText(HomeSub.this, "QR Not Valid For Part", Toast.LENGTH_SHORT).show();
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
                    Intent stores=new Intent(HomeSub.this,InvOil.class);
                    startActivity(stores);
                    finish();
                }
                else
                {
                    Toast.makeText(HomeSub.this, "QR Not Valid For Store", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(HomeSub.this, "User Logged Out !", Toast.LENGTH_SHORT).show();


                        Intent inn = new Intent(HomeSub.this, Login.class);
                        startActivity(inn);
                        finish();
                        break;

                    }

                    case R.id.churl: {
                        sp=getSharedPreferences("login", Context.MODE_PRIVATE);
                        String UserName = sp.getString("username", "");
                        String Password = sp.getString("password", "");
                        if (UserName.equalsIgnoreCase("superuser") && Password.equalsIgnoreCase("SUPER")) {
                            Intent inc = new Intent(HomeSub.this, Url.class);
                            startActivity(inc);
                            break;
                        } else {
                            AlertDialog.Builder adb = new AlertDialog.Builder(HomeSub.this);
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
                Intent i=new Intent(HomeSub.this,Home.class);
                startActivity(i);
                finish();
            }
            else
            {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        HomeSub.this);

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

}
