package com.example.arvindhan.qr;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class Url extends AppCompatActivity {

    ImageView Btn;
    EditText url_edit;
    String url,registerstatus="",strMsg,urlnew;
    sqlconnect sq=new sqlconnect(this);

    private static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    private static final String SOAP_ACTION1 = "http://tempuri.org/tablemodule";
    private static final String METHOD_NAME1 = "tablemodule";


    private static  String URL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);

        url_edit=(EditText)findViewById(R.id.url_edit);
        Btn=(ImageView) findViewById(R.id.url_btn);

        SharedPreferences sp1 = getSharedPreferences("URL", Context.MODE_PRIVATE);
        urlnew= sp1.getString("urlvalue","");

        if (urlnew.equalsIgnoreCase("")){
            url_edit.setText("http://***.***.***.**/mobileappsdb/WebService1.asmx");

        } else {
            url_edit.setText(urlnew);
        }



        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url=url_edit.getText().toString();
                if (url.contains("http://") && url.contains("WebService1.asmx")&& !url.contains("***.***.***.**")) {
                    URL=url;

                    final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
                    if (activeNetwork != null && activeNetwork.isConnected()) {
                        new AsyncCheckLogins().execute();
                        //sq.insertDeviceName(strMsg);

                    } else {
                        //notify user you are not online
                        showAlertDialog(Url.this, "No Internet Connection",
                                "please check your internet connection.", false);
                    }

                } else {
                    Toast.makeText(Url.this, "This is not a valid URl! Please check the URL", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    private class AsyncCheckLogins extends AsyncTask {
        String error = "";
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(Url.this, "Please wait...","It may take few seconds to Load...", true);

        }
        @Override
        protected Object doInBackground(Object[] objects) {
            calls();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
            if (registerstatus.equalsIgnoreCase("Success")) {
//                textview.setText(strMsg);
                sq.insertDeviceName(strMsg);
                SharedPreferences sp = getSharedPreferences("URLREG", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("url_register", url);
                editor.commit();

                SharedPreferences pref = getSharedPreferences("URL", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("urlvalue", url);
                edit.commit();

                Intent intent = new Intent(Url.this, Login.class);
                startActivity(intent);
                finish();
                Toast.makeText(Url.this, "Connected to the Database !", Toast.LENGTH_SHORT).show();


            } else if (registerstatus.equalsIgnoreCase("Failed")) {
                Toast.makeText(Url.this, "Connection Failed", Toast.LENGTH_SHORT).show();
            }

            else if (registerstatus.equalsIgnoreCase("null")) {
                Toast.makeText(Url.this, "Connection Failed", Toast.LENGTH_LONG).show();

            } else if (registerstatus.equalsIgnoreCase("")) {
                Toast.makeText(Url.this, "Connection Failed", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(Url.this, "Connection Failed", Toast.LENGTH_SHORT).show();
            }

        }

    }
    public void calls() {
        try {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME1);

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        Url.this);

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
                                        SharedPreferences sp1 = getSharedPreferences("back", Context.MODE_PRIVATE);
                                        String urlscreen= sp1.getString("comeback","");

                                        if (urlscreen.equalsIgnoreCase("homescreen") && urlnew.equalsIgnoreCase(url_edit.getText().toString())){
                                            Intent i =new Intent(Url.this,Home.class);
                                            startActivity(i);
                                            finish();
                                        } else {
                                            Intent intent = new Intent(
                                                Intent.ACTION_MAIN);
                                        intent.addCategory(Intent.CATEGORY_HOME);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                            finish();
                                        }
//                                        finish();

//                                        Intent intent = new Intent(
//                                                Intent.ACTION_MAIN);
//                                        intent.addCategory(Intent.CATEGORY_HOME);
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        startActivity(intent);
                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {

                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }


        return false;
    }
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
