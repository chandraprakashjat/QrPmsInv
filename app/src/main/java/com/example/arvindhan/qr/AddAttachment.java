package com.example.arvindhan.qr;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arvindhan.parameter.R;
import com.example.arvindhan.qr.CameraUtility;
import com.example.arvindhan.qr.Utility;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddAttachment extends AppCompatActivity {

    EditText date,filename;
    Button save,selectimg;
    ImageView imageview;

    String geturlvalues,userChoosenTask,encodedString,imageString,registerstatus,ent,mcode,ccode,USerCode,strmsg,datestring,value,filenamestring,Selection,userName,password,LangCode;
    private int mYear, mMonth, mDay;
    private int SELECT_FILE = 1, REQUEST_CAMERA = 0;

    private static final String SOAP_ACTION = "http://tempuri.org/Storeimage";
    private static final String METHOD_NAME = "Storeimage";
    private static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    private static  String URL = "";

//    private sqlconnect connect;

    sqlconnect sq=new sqlconnect(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attachment);

        SharedPreferences sharedPreferece = getSharedPreferences("URLREG", Context.MODE_PRIVATE);
        geturlvalues = sharedPreferece.getString("url_register", "");
        URL=geturlvalues;

        date=(EditText)findViewById(R.id.date_add);
        filename=(EditText)findViewById(R.id.filename_add);
        save=(Button)findViewById(R.id.save);
        selectimg=(Button)findViewById(R.id.select_image);
        imageview=(ImageView)findViewById(R.id.imageadd);

        SharedPreferences sp1 = getSharedPreferences("addattach", Context.MODE_PRIVATE);
        ent= sp1.getString("ent","");
        mcode= sp1.getString("mcode","").trim();
        ccode=sp1.getString("ccode", "").trim();
        USerCode=sp1.getString("USerCode", "").trim();
        datestring=sp1.getString("date", "").trim();
        Selection=sp1.getString("selection", "").trim();

        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        userName = sp.getString("username", "");
        password = sp.getString("password", "");
        USerCode=sp.getString("usercode", "");
        LangCode=sp.getString("langcode", "");



        date.setText(datestring);

        selectimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });



        registerstatus="";
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {

                    if (imageview.getDrawable()== null){
                        showAlertDialog(AddAttachment.this, "Alert !!",
                                "Attach Any Image !!", false);
                    }

                    else if (userChoosenTask.equalsIgnoreCase("Take Photo")){
                        String s=filename.getText().toString();
                        if (s.equalsIgnoreCase("") && imageview.getDrawable()== null){
                        showAlertDialog(AddAttachment.this, "Alert !!",
                                "Enter The FileName & Attach Any Image !!", false);
                         }   else if (s.equalsIgnoreCase("")){
                        showAlertDialog(AddAttachment.this, "Alert !!",
                                "Enter The File Name !!", false);
                             }
                             else {
                            imageString=encodedString;

                            Bitmap bitmap = ((BitmapDrawable) imageview.getDrawable()).getBitmap();
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, bytes);
                            byte[] data = bytes.toByteArray();

//                     String imagevalue=sq.getImageId();
//                     String imagevalue="0";
                            String extr = Environment.getExternalStorageDirectory().toString()
                                    + File.separator + "Gallery";
                            File myPath = null;
//                     int i = Integer.parseInt(imagevalue) + 1;

                            myPath = new File(extr, filenamestring + ".jpg");
                            myPath.getParentFile();

                            FileOutputStream fo;
                            try {
                                fo = new FileOutputStream(myPath);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 5, fo);

                                fo.flush();
                                fo.close();
                                MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),
                                        bitmap, myPath.getPath(), ".jpg");

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            new AsyncCheckLogin().execute();
                        }

                    } else {
                     imageString=encodedString;

                     Bitmap bitmap = ((BitmapDrawable) imageview.getDrawable()).getBitmap();
                     ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                     bitmap.compress(Bitmap.CompressFormat.JPEG, 75, bytes);
                     byte[] data = bytes.toByteArray();

//                     String imagevalue=sq.getImageId();
//                     String imagevalue="0";
                     String extr = Environment.getExternalStorageDirectory().toString()
                             + File.separator + "Gallery";
                     File myPath = null;
//                     int i = Integer.parseInt(imagevalue) + 1;

                     myPath = new File(extr, filenamestring + ".jpg");
                     myPath.getParentFile();

                     FileOutputStream fo;
                     try {
                         fo = new FileOutputStream(myPath);
                         bitmap.compress(Bitmap.CompressFormat.JPEG, 5, fo);

                         fo.flush();
                         fo.close();
                         MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),
                                 bitmap, myPath.getPath(), ".jpg");

                     } catch (FileNotFoundException e) {
                         e.printStackTrace();
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                     new AsyncCheckLogin().execute();
                  }

                } else {
                    Toast.makeText(AddAttachment.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AddAttachment.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {
                    boolean camresult = CameraUtility.checkPermission(AddAttachment.this);
                    userChoosenTask = "Take Photo";
                    if (camresult)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    boolean result = Utility.checkPermission(AddAttachment.this);
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                try {
                    onCaptureImageResult(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }
    private byte[] onCaptureImageResult(Intent data) throws IOException {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        encodedString = Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);

        imageview.setImageBitmap(thumbnail);
        imageview.setVisibility(View.VISIBLE);
        return bytes.toByteArray();

    }
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        try {
            Uri selectedImageURI = data.getData();
            File imageFile = new File(String.valueOf(selectedImageURI));
            String s = String.valueOf(imageFile);

            if (s == null) {
                showAlertDialog(AddAttachment.this, "Alert !!",
                        "Attach an image with jpg or png format", false);
            } else {

                Bitmap bm = null;
                if (data != null) {
                    try {
                        bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                ByteArrayOutputStream bytess = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 90, bytess);

                encodedString = Base64.encodeToString(bytess.toByteArray(), Base64.DEFAULT);
                imageview.setImageBitmap(bm);
                imageview.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getRealPathFromURI(Uri selectedImageURI) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectedImageURI, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public class AsyncCheckLogin extends AsyncTask<Void, Void, Void> {
        String error = "";
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(AddAttachment.this, "Please wait...","It may take few seconds to Load...", true);

        }

        @Override
        protected Void doInBackground(Void... params) {
            call();
            return null;

        }

        @Override
        protected void onPostExecute (Void aVoid){
            super.onPostExecute(aVoid);
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
            if (registerstatus.equalsIgnoreCase("Success")) {
                imageview.setVisibility(View.INVISIBLE);

                Toast.makeText(AddAttachment.this, "Attachment Saved Successfully !", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(AddAttachment.this, AddAttachment.class);
                startActivity(i);
                finish();
            }
            else if (registerstatus.equalsIgnoreCase("Failed")) {
                Toast.makeText(AddAttachment.this, "Wrong QR Value", Toast.LENGTH_SHORT).show();
            }

            else if (registerstatus.equalsIgnoreCase("")) {
                Toast.makeText(AddAttachment.this, "Server is not connected.Try after sometimes!", Toast.LENGTH_LONG).show();

            } else {
                showAlertDialog(AddAttachment.this, "Alert !!",
                        strmsg, false);
//                Toast.makeText(AddAttachment.this, strmsg , Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void call() {
        try {
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);

            String datevalue=date.getText().toString();
            String filevalue=filename.getText().toString();

            request.addProperty("maintananceId", mcode);
            request.addProperty("entitycode", ent);
            request.addProperty("date", datevalue);
            if (userChoosenTask.equalsIgnoreCase("Take Photo")){
                request.addProperty("filename", filevalue);
            }
            request.addProperty("filename", filenamestring);
            request.addProperty("imagevalue", imageString);
            request.addProperty("usercode", USerCode);
            request.addProperty("componentcode",ccode);
            request.addProperty("selection",Selection);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(URL);
            Object response = null;
            try {
                httpTransport.call(SOAP_ACTION, envelope);

                response = envelope.getResponse();

                strmsg = response.toString();
                if (strmsg.equalsIgnoreCase("Failed")) {
                    // set status Failed
                    registerstatus = "Failed";
                }  else if (strmsg.equalsIgnoreCase("Success")){

                    registerstatus = "Success";
                    //surveystat="SurveySuccess";
                } else {
                    registerstatus=strmsg;
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
    @Override
    public void onBackPressed() {

        if (Selection.equalsIgnoreCase("Maintenance")){
            Intent back=new Intent(AddAttachment.this,PmsCompletion.class);
            startActivity(back);
            finish();
        } else {
            Intent backnew=new Intent(AddAttachment.this,CalibrationCompletion.class);
            startActivity(backnew);
            finish();
        }

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
