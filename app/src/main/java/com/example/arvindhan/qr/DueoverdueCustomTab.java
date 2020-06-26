package com.example.arvindhan.qr;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.arvindhan.parameter.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DueoverdueCustomTab extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> maintanance;
    private final ArrayList<String> equipname;
    private final ArrayList<String> donedate;
    private final ArrayList<String> due;
    private final ArrayList<String> maintenanceid;
    private final ArrayList<String> maintenancecode;
    private final ArrayList<String> componentcode;
    private final ArrayList<String> frequency;
    private final String completion;


    public DueoverdueCustomTab(Activity context, ArrayList<String> maintanance,
                            ArrayList<String> equipname, ArrayList<String> donedate,ArrayList<String> due,ArrayList<String> maintenanceid,ArrayList<String> maintenancecode,
                            ArrayList<String> componentcode,ArrayList<String> frequency,String completion) {
        super(context, R.layout.activity_dueoverdue_custom_tab, maintanance);
        this.context = context;
        this.maintanance = maintanance;
        this.equipname = equipname;
        this.donedate = donedate;
        this.due = due;
        this.maintenanceid = maintenanceid;
        this.maintenancecode = maintenancecode;
        this.componentcode = componentcode;
        this.frequency = frequency;
        this.completion = completion;
    }

    @Override
    public View getView(int position, View View, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.activity_dueoverdue_custom_tab, null, true);

        CheckBox cb=(CheckBox)rowView.findViewById(R.id.check);

        final Calendar mycalender = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String dates=sdf.format(mycalender.getTime());
        Date date1=null,date2=null;

        TextView txtmaintenance = (TextView) rowView.findViewById(R.id.Maintenance_custom);
        TextView txtfrequency = (TextView) rowView.findViewById(R.id.freq_custom);
        TextView txtnmaintid = (TextView) rowView.findViewById(R.id.maintid_custom);
        TextView txtequipmentname = (TextView) rowView.findViewById(R.id.Comp_custom);
        TextView txtlastdone = (TextView) rowView.findViewById(R.id.lastdone_custom);
        TextView txtnextdue = (TextView) rowView.findViewById(R.id.nextdue_custom);
        TextView txtmaincode = (TextView) rowView.findViewById(R.id.maintenancecode_custom);
        TextView txtcompcode = (TextView) rowView.findViewById(R.id.componentcode_custom);
        TextView Main_head = (TextView) rowView.findViewById(R.id.main_text);
        TextView Main_id = (TextView) rowView.findViewById(R.id.maintenanceid_text);
        TextView txtconterbased = (TextView) rowView.findViewById(R.id.counterbased_custom);
        TextView txtdays = (TextView) rowView.findViewById(R.id.days_custom);
        TextView txtaverage = (TextView) rowView.findViewById(R.id.avg_custom);
        TextView txtcumi = (TextView) rowView.findViewById(R.id.cumi_custom);

        TableRow Maintenancename = (TableRow) rowView.findViewById(R.id.tablerow_maintenance);
        TableRow Maintenanceid = (TableRow) rowView.findViewById(R.id.tablerow_MaintenanceId);
        TableRow Frequency = (TableRow) rowView.findViewById(R.id.tablerow_frequency);
        TableRow Nextdue = (TableRow) rowView.findViewById(R.id.tablerow_nextdue);
        TableRow Counterbased = (TableRow) rowView.findViewById(R.id.tablerow_Counterbased);
        TableRow days = (TableRow) rowView.findViewById(R.id.tablerow_days);
        TableRow Avg = (TableRow) rowView.findViewById(R.id.tablerow_avg);
        TableRow Cumilative = (TableRow) rowView.findViewById(R.id.tablerow_cumi);

        if(completion.contains("1")) {
            Main_head.setText("Maintenance Name");
            Main_id.setText("Maintenance Code");
            Maintenancename.setVisibility(android.view.View.VISIBLE);
            Maintenanceid.setVisibility(android.view.View.VISIBLE);
            Frequency.setVisibility(android.view.View.VISIBLE);
            Nextdue.setVisibility(android.view.View.VISIBLE);
            Counterbased.setVisibility(android.view.View.GONE);
            days.setVisibility(android.view.View.GONE);
            Avg.setVisibility(android.view.View.GONE);
            Cumilative.setVisibility(android.view.View.GONE);
        }
        else if(completion.contains("2")) {
            Main_head.setText("Parameter Name");
            Main_id.setText("Equipment Code");
            Maintenancename.setVisibility(android.view.View.VISIBLE);
            Maintenanceid.setVisibility(android.view.View.VISIBLE);
            Frequency.setVisibility(android.view.View.VISIBLE);
            Nextdue.setVisibility(android.view.View.VISIBLE);
            Counterbased.setVisibility(android.view.View.GONE);
            days.setVisibility(android.view.View.GONE);
            Avg.setVisibility(android.view.View.GONE);
            Cumilative.setVisibility(android.view.View.GONE);
        }
        else if(completion.contains("3")){
            Main_head.setText("Calibration Name");
            Main_id.setText("Equipment Code");
            Maintenancename.setVisibility(android.view.View.VISIBLE);
            Maintenanceid.setVisibility(android.view.View.VISIBLE);
            Frequency.setVisibility(android.view.View.VISIBLE);
            Nextdue.setVisibility(android.view.View.VISIBLE);
            Counterbased.setVisibility(android.view.View.GONE);
            days.setVisibility(android.view.View.GONE);
            Avg.setVisibility(android.view.View.GONE);
            Cumilative.setVisibility(android.view.View.GONE);
        }
        else if(completion.contains("4")){
            Main_head.setText("Safety Alarm Name");
            Main_id.setText("Equipment Code");
            Maintenancename.setVisibility(android.view.View.VISIBLE);
            Maintenanceid.setVisibility(android.view.View.VISIBLE);
            Frequency.setVisibility(android.view.View.VISIBLE);
            Nextdue.setVisibility(android.view.View.VISIBLE);
            Counterbased.setVisibility(android.view.View.GONE);
            days.setVisibility(android.view.View.GONE);
            Avg.setVisibility(android.view.View.GONE);
            Cumilative.setVisibility(android.view.View.GONE);
        }
        else if(completion.contains("5"))
        {
            Main_id.setText("Equipment Code");
            Maintenancename.setVisibility(android.view.View.GONE);
            Maintenanceid.setVisibility(android.view.View.GONE);
            Frequency.setVisibility(android.view.View.GONE);
            Nextdue.setVisibility(android.view.View.GONE);
            Counterbased.setVisibility(android.view.View.VISIBLE);
            days.setVisibility(android.view.View.VISIBLE);
            Avg.setVisibility(android.view.View.VISIBLE);
            Cumilative.setVisibility(android.view.View.VISIBLE);
        }

        if(completion.contains("5")) {
        }
        else{
            if (due.get(position).equalsIgnoreCase("")) {
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
        }
        if(completion.contains("5"))
        {
            txtcompcode.setText(maintanance.get(position));
            txtequipmentname.setText(equipname.get(position));
            if(frequency.get(position).contains("0"))
            {
                txtconterbased.setText("No");
            }
            else if(frequency.get(position).contains("1"))
            {
                txtconterbased.setText("Yes");
            }

            txtlastdone.setText(donedate.get(position));
            txtcumi.setText(due.get(position));
            txtnmaintid.setText(maintenanceid.get(position));
            txtaverage.setText(maintenancecode.get(position));
            txtdays.setText(componentcode.get(position));
        }
        else {
            txtmaintenance.setText(maintanance.get(position));
            txtequipmentname.setText(equipname.get(position));
            txtfrequency.setText(frequency.get(position));
            txtlastdone.setText(donedate.get(position));
            txtnextdue.setText(due.get(position));
            txtnmaintid.setText(maintenanceid.get(position));
            txtmaincode.setText(maintenancecode.get(position));
            txtcompcode.setText(componentcode.get(position));
        }

        return  rowView;

    }
}
