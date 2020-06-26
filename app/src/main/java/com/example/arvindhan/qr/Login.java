package com.example.arvindhan.qr;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arvindhan.parameter.R;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class Login extends AppCompatActivity {

    TextView b;
    EditText Username_id, Password_id;
    TextView Login;
    String username, pswd, LoginStatus,geturlvalues,registerstatus,strMsg;
    TextView register;
    public static TextView User,Designation,UserNAme,LangCode,UserCode,EntityCode;
    static  String tv,s;
    ImageView iv;

    private static final String SOAP_ACTION = "http://tempuri.org/login";
    private static final String METHOD_NAME = "login";
    private static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

    private static final String SOAP_ACTION1 = "http://tempuri.org/tablemodule";
    private static final String METHOD_NAME1 = "tablemodule";


    private static  String URL = "";

    String logoutvalue;
    SharedPreferences sp;
    SharedPreferences.Editor edt;
    public Context c = this;
    sqlconnect sq=new sqlconnect(this);
    ArrayList l1= new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        b=(TextView) findViewById(R.id.l_buton);
        SharedPreferences sp = getSharedPreferences("URLREG", Context.MODE_PRIVATE);
        geturlvalues = sp.getString("url_register", "");
        URL=geturlvalues;



        Username_id = (EditText) findViewById(R.id.l_user);
        Password_id = (EditText) findViewById(R.id.l_pass);
        Login = (TextView) findViewById(R.id.l_buton);
//        register=(TextView) findViewById(R.id.registerlink);
        User=(TextView) findViewById(R.id.usercode);
        Designation=(TextView) findViewById(R.id.designation);
        UserNAme=(TextView) findViewById(R.id.username);
        LangCode=(TextView) findViewById(R.id.languagecode);
        UserCode=(TextView) findViewById(R.id.uservalue);
        EntityCode=(TextView) findViewById(R.id.entitycode);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SharedPreferences sharepref = getSharedPreferences("logoutvalue", Context.MODE_PRIVATE);
        logoutvalue = sharepref.getString("logval", "");

//        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
//        if (activeNetwork != null && activeNetwork.isConnected()) {
//            new AsyncCheckLogins().execute();
//
//        } else {
//        //notify user you are not online
//        showAlertDialog(Login.this, "No Internet Connection",
//                "please check your internet connection.", false);
//    }


        LoginStatus="";
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {

                    username=Username_id.getText().toString();
                    pswd=Password_id.getText().toString();

                    SharedPreferences pref = getSharedPreferences("loginreg", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("username", username);
                    edit.putString("password", pswd);
                    edit.commit();


                    if(username.matches("") || pswd.matches("")){
                        //et1.setError("Username is required");
                        showAlertDialog(Login.this, "Mandatory",
                                "Username or Password cannot be empty", false);

                    }
                    else
                    {

                        new AsyncCheckLogin().execute();
                       // sq.insertDeviceName(strMsg);
                    }
                    //notify user you are online
                } else {
                    //notify user you are not online
                    showAlertDialog(Login.this, "No Internet Connection",
                            "please check your internet connection.", false);
                }
            }
        });
//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent register=new Intent(LoginActivity.this,RegisterActivity.class);
//                startActivity(register);
//            }
//        });
//        iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPopup(v);
//            }
//        });


    }

//    private void showPopup(View view) {
//        PopupMenu popup = new PopupMenu(this, view);
//        MenuInflater inflater = popup.getMenuInflater();
//        inflater.inflate(R.menu.menu_main, popup.getMenu());
//        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.item1:
//                        Intent changeurl=new Intent(Login.this,UrlActivity.class);
//                        startActivity(changeurl);
//                        finish();
//                    default:
//                        return false;
//                }
//
//            }
//        });
//        popup.show();
//    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.item1:
//                Intent changeUrl=new Intent(LoginActivity.this,UrlActivity.class);
//                startActivity(changeUrl);
//                finish();
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//
//    }


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

    private class AsyncCheckLogin extends AsyncTask{

        String error="";
        ProgressDialog mProgressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //this method will be running on UI thread
            mProgressDialog=ProgressDialog.show(Login.this, "Please wait...","It may take few seconds to Load...",true);
        }
        @Override
        protected Object doInBackground(Object[] params) {
            call();

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
            if(LoginStatus.equalsIgnoreCase("Success"))
            {
                String[] cut=s.split(",");

                Designation.setText(cut[0]);
                UserNAme.setText(cut[1]);
                LangCode.setText(cut[2]);
                UserCode.setText(cut[3]);
                EntityCode.setText(cut[3]);

                String s1=Designation.getText().toString();
                String s2=UserNAme.getText().toString();
                String s3=LangCode.getText().toString();
                String s4=UserCode.getText().toString();
                String s5=EntityCode.getText().toString();

                SharedPreferences sharedPref = getSharedPreferences("homecode", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("designation",s1);
                editor.putString("Username",s2);
                editor.putString("langcode",s3);
                editor.putString("usercode",s4);
                editor.putString("entity",s5);
                editor.commit();
                // for inserting images based on surveyid
                Intent i=new Intent(Login.this,Home.class);
//                i.putExtra("user",tv);
                startActivity(i);
                finish();
                Toast.makeText(Login.this,"User signed in !",Toast.LENGTH_LONG).show();
            }

            else if(LoginStatus.equalsIgnoreCase("Failed"))
            {
                Toast.makeText(Login.this,"Username or Password Invalid",Toast.LENGTH_LONG).show();
            } else if (LoginStatus.equalsIgnoreCase("")){
                Toast.makeText(Login.this,"Server not connected.Try after sometimes!",Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(c, "Successful", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void call() {
        try {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,METHOD_NAME);

            Username_id=(EditText)findViewById(R.id.l_user);
            Password_id=(EditText)findViewById(R.id.l_pass);
            username=Username_id.getText().toString();
            pswd=Password_id.getText().toString();
            request.addProperty("UserName",username);
            request.addProperty("password",pswd);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);
//	         HttpTransportSE httpTransport = new HttpTransportSE(URL);
            HttpTransportSE httpTransport = new HttpTransportSE(URL);
//            httpTransport.debug=true;
            Object response=null;
            try {
                httpTransport.call(SOAP_ACTION, envelope);
                //          SoapObject object = (SoapObject) envelope.getResponse();
                //           SoapObject object1 = (SoapObject) object.getProperty(0);
                response = envelope.getResponse();
//                SoapObject response = (SoapObject) envelope.bodyIn;
                //            String s=((SoapObject) object.getProperty(0)).toString();
                s=response.toString();
                if (s.equalsIgnoreCase("Failed"))
                {
                    // set status Failed
                    LoginStatus="Failed";
                }
                else
                {

                    LoginStatus="Success";
                    //surveystat="SurveySuccess";
                }
            } catch(Exception exception){
                // Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
                // surveystat="Failed";
                Log.i("error", exception.getMessage().toString());
            }
        } catch (Exception e) {
            //Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            // surveystat="Failed";
            Log.i("error", e.getMessage().toString());
        }
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);


    }

//    private class AsyncCheckLogins extends AsyncTask {
//        String error = "";
//        ProgressDialog mProgressDialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            mProgressDialog = ProgressDialog.show(Login.this, "Please wait...","It may take few seconds to Load...", true);
//
//        }
//        @Override
//        protected Object doInBackground(Object[] objects) {
//            calls();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Object o) {
//            super.onPostExecute(o);
//            if (mProgressDialog != null) {
//                if (mProgressDialog.isShowing()) {
//                    mProgressDialog.dismiss();
//                }
//            }
//            if (registerstatus.equalsIgnoreCase("Success")) {
////                textview.setText(strMsg);
//                if (logoutvalue.equalsIgnoreCase("logout")){
//                    sp=getSharedPreferences("logoutvalue", Context.MODE_PRIVATE);
//                    edt = sp.edit();
//                    edt.clear();
//                    edt.commit();
//
//                    Toast.makeText(Login.this,"User logged out" , Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(Login.this, "Got The Module Values", Toast.LENGTH_SHORT).show();
//                }
//            } else if (registerstatus.equalsIgnoreCase("Failed")) {
//                Toast.makeText(Login.this, "Wrong QR Value", Toast.LENGTH_SHORT).show();
//            }
//
//            else if (registerstatus.equalsIgnoreCase("null")) {
//                Toast.makeText(Login.this, strMsg, Toast.LENGTH_LONG).show();
//
//            } else {
//                Toast.makeText(Login.this, "Successful", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//
//    }
//    public void calls() {
//        try {
//            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME1);
//
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//            envelope.dotNet = true;
//            envelope.setOutputSoapObject(request);
//
//            HttpTransportSE httpTransport = new HttpTransportSE(URL);
//            Object response = null;
//            try {
//                httpTransport.call(SOAP_ACTION1, envelope);
//
//                response = envelope.getResponse();
//
//                strMsg = response.toString();
//                if (strMsg.equalsIgnoreCase("Failed")) {
//                    // set status Failed
//                    registerstatus = "Failed";
//                } else {
//
//                    registerstatus = "Success";
//                }
//            } catch (SoapFault soapFault) {
//                Log.i("error", soapFault.getMessage().toString());
//
//            } catch (XmlPullParserException e) {
//                Log.i("error", e.getMessage().toString());
//
//            } catch (IOException e) {
//                Log.i("error", e.getMessage().toString());
//
//            }
//        } catch (Exception e) {
//            Log.i("error", e.getMessage().toString());
//        }
//
//    }
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
