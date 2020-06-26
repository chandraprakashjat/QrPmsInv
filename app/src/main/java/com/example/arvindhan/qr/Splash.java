package com.example.arvindhan.qr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arvindhan.parameter.R;


public class Splash extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3500;
    String geturl,setURL,Username,Password;
    SharedPreferences sharedPref ,sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

         sharedPref = getSharedPreferences("URLREG", Context.MODE_PRIVATE);
        geturl = sharedPref.getString("url_register", "");
        setURL=geturl;

        sp = getSharedPreferences("loginreg", Context.MODE_PRIVATE);
        Username = sp.getString("username", "");
        Password = sp.getString("password", "");

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
//                Intent mainIntent = new Intent(SplashActivity.this,LoginActivity.class);
//                SplashActivity.this.startActivity(mainIntent);
//                SplashActivity.this.finish();

                if (setURL.equalsIgnoreCase("")){
                    Intent i = new Intent(Splash.this, Url.class);
                    startActivity(i);
                    finish();
                }
                else  if (sp.getString("username", "").matches("") || sp.getString("password", "").matches(""))
                {
                    Intent i = new Intent(Splash.this, Login.class);
                    startActivity(i);
                    finish();

                }
                else
                {
                    Intent i = new Intent(Splash.this, Home.class);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    }

