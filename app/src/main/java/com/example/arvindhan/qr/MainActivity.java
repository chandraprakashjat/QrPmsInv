package com.example.arvindhan.qr;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arvindhan.parameter.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ImageView iv;
    public static TextView value, datetime, Com_Date, Com_Status, Com_By, itemcode, entitycode,Num,Equipment;
    public static EditText date, observed, measured, remarks;
    Button done;
    String qr, registerstatus, dy, qy, tp, item, ent, cat, dt, remark, spin1, spin2,equipment;

    private int mYear, mMonth, mDay;
    private IntentIntegrator qrscan;
    String userName, password;

    private static final String SOAP_ACTION = "http://tempuri.org/qrparameter";
    private static final String METHOD_NAME = "qrparameter";
    private static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    private static final String URL = "http://203.154.162.66/mobile/WebService1.asmx";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
//        iv = (ImageView) findViewById(R.id.qr);
//        value = (TextView) findViewById(R.id.qrvalue);
//        datetime = (TextView) findViewById(R.id.currentdate);
//        Com_Date = (TextView) findViewById(R.id.com_date);
//        Com_Status = (TextView) findViewById(R.id.com_status);
//        Com_By = (TextView) findViewById(R.id.com_by);
//        itemcode = (TextView) findViewById(R.id.uniqueId);
//        entitycode = (TextView) findViewById(R.id.entity);
//        Num = (TextView) findViewById(R.id.num);
//        Equipment = (TextView) findViewById(R.id.equip_code);
//        date = (EditText) findViewById(R.id.date);
//        observed = (EditText) findViewById(R.id.spinner1);
//        measured = (EditText) findViewById(R.id.spinner2);
//        remarks = (EditText) findViewById(R.id.remarks);
//        done = (Button) findViewById(R.id.store);
//
//        qrscan = new IntentIntegrator(this);
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//
////        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
////        userName = sharedPref.getString("userName", "");
////        password = sharedPref.getString("password", "");
//
//
//        final Calendar mycalender = Calendar.getInstance();
//
//        date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Calendar mcurrentDate = Calendar.getInstance();
//                mYear = mcurrentDate.get(Calendar.YEAR);
//                mMonth = mcurrentDate.get(Calendar.MONTH);
//                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog mDatePicker = new DatePickerDialog(MainActivity.
//                        this, new DatePickerDialog.OnDateSetListener() {
//                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
//                        // TODO Auto-generated method stub
//
//                        date.setText(selectedmonth + 1 + "/" + selectedday + "/" + selectedyear);
//                    }
//                }, mYear, mMonth, mDay);
////                mDatePicker.setTitle("Select date");
//                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
//                mDatePicker.show();
//            }
//        });
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
//        date.setText(simpleDateFormat.format(mycalender.getTime()));
//        datetime.setText(simpleDateFormat.format(mycalender.getTime()));
//
//        iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                qrscan.initiateScan();
//            }
//        });
//
//        registerstatus = "";
//        done.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//                final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
//                if (activeNetwork != null && activeNetwork.isConnected()) {
//
//                    String spin1 = observed.getText().toString();
//                    String spin2 = measured.getText().toString();
////                    int num=Integer.parseInt(observed.getText().toString());
//
//
//                    if (spin1.matches("") || spin2.matches("")) {
//                        Toast.makeText(MainActivity.this, "Enter the necessary fields", Toast.LENGTH_SHORT).show();
//
//                        } else if (observed.getText().toString().trim().isEmpty() || Float.valueOf(observed.getText().toString()) > 5.00 || Float.valueOf(observed.getText().toString()) < 2.00) {
//                        Toast.makeText(MainActivity.this, "Enter the correct observed value", Toast.LENGTH_SHORT).show();
//
//                    } else {
//                        qr = value.getText().toString();
//                        String name = value.getText().toString();
//                        String[] cut = name.split("Param Code :");
//                        String[] oilname = cut[1].split("\\n");
//                        itemcode.setText(oilname[0]);
//                        String[] entity = name.split("Entity :");
//                        String[] entityname = entity[1].split("\\n");
//                        entitycode.setText(entityname[0]);
//                        String[] category=name.split("Category :");
//                        String[] content=category[1].split("\\n");
//                        Num.setText(content[0]);
//                        String[] equipcode=name.split("Equip Code :");
//                        String[] eqcode=equipcode[1].split("\\n");
//                        Equipment.setText(eqcode[0]);
//
//                        new AsyncCheckLogin().execute();
////                        qr = value.getText().toString();
////                        new AsyncCheckLogin().execute();
//
//
//                    }
//
//
//                } else {
//                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//
//    }
//
//    private void showAlertDialog(Context context, String title, String message, Boolean status) {
//        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
//
//        // Setting Dialog Title
//        alertDialog.setTitle(title);
//
//        // Setting Dialog Message
//        alertDialog.setMessage(message);
//
//        // Setting alert dialog icon
////        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.icon);
//
//        // Setting OK Button
//        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//            }
//        });
//
//        // Showing Alert Message
//        alertDialog.show();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//        if (result != null) {
//            if (result.getContents() == null) {
//                Toast.makeText(this, "Record Not Found", Toast.LENGTH_SHORT).show();
//            } else {
//                value.setText(result.getContents());
//                String s = value.getText().toString();
//
//                if (s.contains("Param Code :")) {
//                    value.setVisibility(View.VISIBLE);
//                    date.setVisibility(View.VISIBLE);
//                    remarks.setVisibility(View.VISIBLE);
//                    observed.setVisibility(View.VISIBLE);
//                    measured.setVisibility(View.VISIBLE);
//                    done.setVisibility(View.VISIBLE);
//                    Com_By.setVisibility(View.VISIBLE);
//                    Com_Date.setVisibility(View.VISIBLE);
//                    Com_Status.setVisibility(View.VISIBLE);
//                } else {
//                    Toast.makeText(this, "Wrong QR Value! Scan Again", Toast.LENGTH_SHORT).show();
//                }
//            }
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }
//
//    public class AsyncCheckLogin extends AsyncTask<Void, Void, Void> {
//        String error = "";
//        ProgressDialog mProgressDialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            mProgressDialog = ProgressDialog.show(MainActivity.this, "Please wait...", "It may take few seconds to Load Products list...", true);
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            call();
//
//
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            if (mProgressDialog != null) {
//                if (mProgressDialog.isShowing()) {
//                    mProgressDialog.dismiss();
//                }
//            }
//            if (registerstatus.equalsIgnoreCase("Success")) {
//                Toast.makeText(MainActivity.this, "Parameter Saved Successfully !", Toast.LENGTH_SHORT).show();
//
//                Intent i = new Intent(MainActivity.this, Main2Activity.class);
//                startActivity(i);
//                finish();
//            }
//            else if (registerstatus.equalsIgnoreCase("Failed")) {
//                Toast.makeText(MainActivity.this, "Wrong QR Value", Toast.LENGTH_SHORT).show();
//            }
////            else  if(registerstatus.equalsIgnoreCase("Success"))
////            {
////
////            }
//            else if (registerstatus.equalsIgnoreCase("")) {
//                Toast.makeText(MainActivity.this, "Server is not connected.Try after sometimes!", Toast.LENGTH_LONG).show();
//
//            } else {
//                Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    public void call() {
//        try {
//            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);
//            qr = value.getText().toString();
//            item = itemcode.getText().toString().trim();
//            ent = entitycode.getText().toString();
//            dy = date.getText().toString();
//            spin1 = observed.getText().toString();
//            spin2 = measured.getText().toString();
//            remark=remarks.getText().toString();
//            equipment=Equipment.getText().toString().trim();
//            userName="0";
////            dt = datetime.getText().toString();
//
////            device=deviceID.getText().toString();
////            device=getIntent().getExtras().getString("user");
////            request.addProperty("category", cat);
//            request.addProperty("parameterId", item);
//            request.addProperty("entitycode", ent);
////            request.addProperty("loginVessel", ec);
//            request.addProperty("date", dy);
//            request.addProperty("observed", spin1);
//            request.addProperty("measurement", spin2);
//            request.addProperty("remarks", remark);
//            request.addProperty("usercode", userName);
//            request.addProperty("componentcode", equipment);
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//            envelope.dotNet = true;
//            envelope.setOutputSoapObject(request);
//
//            HttpTransportSE httpTransport = new HttpTransportSE(URL);
//            Object response = null;
//            try {
//                httpTransport.call(SOAP_ACTION, envelope);
//
//                response = envelope.getResponse();
//
//                String s = response.toString();
//                if (s.equalsIgnoreCase("Failed")) {
//                    // set status Failed
//                    registerstatus = "Failed";
//                } else {
//
//                    registerstatus = "Success";
//                    //surveystat="SurveySuccess";
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
//
//    }

