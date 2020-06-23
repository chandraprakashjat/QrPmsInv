package com.example.arvindhan.qr;

import android.app.Activity;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.arvindhan.parameter.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ParameterCustom extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> maintenance;
    private final ArrayList<String> days;
    private final ArrayList<String> donedate;
    private final ArrayList<String> due;
    private final ArrayList<String> maintenancecode;
    private final ArrayList<String> componentcode;


    public ParameterCustom(Activity context, ArrayList<String> maintenance,
                        ArrayList<String> days, ArrayList<String> donedate,ArrayList<String> due,ArrayList<String> maintenancecode,
                        ArrayList<String> componentcode) {
        super(context, R.layout.activity_parameter_custom, maintenance);
        this.context = context;
        this.maintenance = maintenance;
        this.days = days;
        this.donedate = donedate;
        this.due = due;
        this.maintenancecode = maintenancecode;
        this.componentcode = componentcode;
    }




    @Override
    public View getView(int position, View View, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.activity_parameter_custom, null, true);

        CheckBox cb=(CheckBox)rowView.findViewById(R.id.check);

        final Calendar mycalender = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String dates=sdf.format(mycalender.getTime());
        Date date1=null,date2=null;

        TextView txtmaintenance = (TextView) rowView.findViewById(R.id.scan_custom);
        TextView txtfrequency = (TextView) rowView.findViewById(R.id.freq_custom);
        TextView txtlastdone = (TextView) rowView.findViewById(R.id.lastdone_custom);
        TextView txtnextdue = (TextView) rowView.findViewById(R.id.nextdue_custom);
        TextView txtmaincode = (TextView) rowView.findViewById(R.id.maintenancecode_custom);
        TextView txtcompcode = (TextView) rowView.findViewById(R.id.componentcode_custom);

        if (due.get(position).equalsIgnoreCase("")){
            txtnextdue.setText("");
        } else {

            SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
            try {
                date1 = sdf.parse(dates);
                date2 = format2.parse(due.get(position));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (date2.compareTo(date1) < 0) {
                txtnextdue.setTextColor(Color.parseColor("#FF0000"));
                txtmaintenance.setTextColor(Color.parseColor("#FF0000"));
            } else {
                txtnextdue.setTextColor(Color.parseColor("#303F9F"));
                txtmaintenance.setTextColor(Color.parseColor("#303F9F"));
            }
        }

        txtmaintenance.setText(maintenance.get(position));
        txtfrequency.setText(days.get(position));
        txtlastdone.setText(donedate.get(position));
        txtnextdue.setText(due.get(position));
        txtmaincode.setText(maintenancecode.get(position));
        txtcompcode.setText(componentcode.get(position));

        return  rowView;

    }
}
