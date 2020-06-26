package com.example.arvindhan.qr;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.arvindhan.parameter.R;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PoCustom extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<String> storecode;
    private final ArrayList<String> storename;
    private final ArrayList<String> Requestionqty;
    private final ArrayList<String> Orderqty;
    private final ArrayList<String> Storeid;
    private sqlconnect sq = null;
    private String Sqrec = null;
    private String Pocode = null;
    private String Categorytype = null;
    static private ItemClickListener clickListener;
    int temp;

    TextView txtstorecode,txtstorename,txtreqqty,txtorderqty,txtstoreid,txtstorecodehead,txtstorenamehead;


   // sqlconnect sq=new sqlconnect(this);

    public PoCustom(Context context, ArrayList<String> storecode,
                        ArrayList<String> storename, ArrayList<String> Requestionqty,ArrayList<String> Orderqty,ArrayList<String> Storeid,String Pocode,String Categorytype,sqlconnect sq) {
        this.storecode = storecode;
        this.storename = storename;
        this.Requestionqty = Requestionqty;
        this.Orderqty = Orderqty;
        this.Storeid = Storeid;
        this.Pocode = Pocode;
        this.Categorytype = Categorytype;
        this.sq = sq;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_po_custom, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder ch, final int position) {

        final Calendar mycalender = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String dates=sdf.format(mycalender.getTime());
        Date date1=null,date2=null;
        int cat = Integer.parseInt(Categorytype);

        if(cat == 8)
        {
            ((CustomViewHolder) ch).txtstorecodehead.setText("Store Code");
            ((CustomViewHolder) ch).txtstorenamehead.setText("Store Name");
        }
        else if(cat == 6)
        {
            ((CustomViewHolder) ch).txtstorecodehead.setText("Part Code");
            ((CustomViewHolder) ch).txtstorenamehead.setText("Part Name");
        }
        else if(cat == 5) {
            ((CustomViewHolder) ch).txtstorecodehead.setText("Oil Code");
            ((CustomViewHolder) ch).txtstorenamehead.setText("Oil Name");
        }

        ((CustomViewHolder) ch).txtstoreid.setText(storecode.get(position));
        ((CustomViewHolder) ch).txtstorename.setText(storename.get(position));
        ((CustomViewHolder) ch).txtreqqty.setText(Requestionqty.get(position));
        ((CustomViewHolder) ch).txtorderqty.setText(Orderqty.get(position));
        ((CustomViewHolder) ch).txtstorecode.setText(Storeid.get(position));
        ((CustomViewHolder) ch).txtstoreid.setText(storecode.get(position));
        ((CustomViewHolder) ch).txtrecqty.setText(Sqrec);



    }

    @Override
    public int getItemCount() {
        return (null != Storeid ? Storeid.size() : 0);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public  class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtstoreid;
        public TextView txtstorecode;
        public TextView txtstorename;
        public TextView txtreqqty;
        public TextView txtorderqty;
        public TextView txtstorecodehead;
        public TextView txtstorenamehead;
        public EditText txtrecqty;


        public CustomViewHolder(final View view) {
            super(view);


            this.txtrecqty = (EditText)view.findViewById(R.id.recqty_value);
            this.txtstoreid = (TextView) view.findViewById(R.id.storeid_value);
            this.txtstorecode = (TextView) view.findViewById(R.id.storecode_value);
            this.txtstorename = (TextView) view.findViewById(R.id.storename_value);
            this.txtreqqty = (TextView) view.findViewById(R.id.reqqty_value);
            this.txtorderqty = (TextView) view.findViewById(R.id.orderqty_value);
            this.txtstorecodehead = (TextView) view.findViewById(R.id.storecode_text);
            this.txtstorenamehead = (TextView) view.findViewById(R.id.storename_text);


            txtrecqty.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String Storecode = null,Recqty = null;
                        Storecode = txtstoreid.getText().toString();
                        Recqty = txtrecqty.getText().toString();

                    if(!Recqty.isEmpty()) {
                        try {
                            Sqrec = sq.Getrecqty(Storecode);
                            if(Sqrec != null) {
                                sq.UpdatePORecievedqty(Storecode, Recqty ,Pocode);
                            }else{
                                sq.insertPORecievedqty(Storecode, Recqty ,Pocode);
                            }
                            Sqrec = sq.Getrecqty(Storecode);

                        }
                        catch (Exception e) {
                            Log.i("error", e.getMessage().toString());

                        }

                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }

            });
            txtrecqty.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        // Do whatever you want here
                        //Utility.hideKeyboard((Activity) mContext);
                        return true;
                    }
                    return false;
                }
            });
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

}
