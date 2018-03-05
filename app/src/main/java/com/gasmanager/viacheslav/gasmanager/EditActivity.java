package com.gasmanager.viacheslav.gasmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

//import android.util.Log;

public class EditActivity extends Activity {


    Context mContext;
    private Button butSave, butCancel;
    private TextView mTextView;
    private long MyDataID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);


        final TextView date = (TextView) findViewById(R.id.Date);
        final TextView text1 = (TextView) findViewById(R.id.odometer);
        final EditText text2 = findViewById(R.id.distance);
        final TextView text3 = (TextView) findViewById(R.id.liters);
        final TextView text4 = (TextView) findViewById(R.id.price);
        final EditText text5 = findViewById(R.id.paid);
        final TextView text6 = (TextView) findViewById(R.id.itemid);
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        text2.setKeyListener(null);
        text5.setKeyListener(null);
        if (getIntent().hasExtra("MyData")) {
            MyData md = (MyData) getIntent().getSerializableExtra("MyData");
            long mydate = md.getDate();
            date.setText(formatter.format(mydate));
            text1.setText(String.valueOf(md.getOdometer()));
            text2.setText(String.valueOf(md.getDistance()));
            text3.setText(String.valueOf(md.getLiters()));
            text4.setText(String.valueOf(md.getPrice()));
            text5.setText(String.valueOf(md.getPaid()));
            text6.setText(String.valueOf(md.getID()));
            MyDataID = md.getID();
        } else {
            MyDataID = 0;
            long mydate = new Date().getTime();
            date.setText(formatter.format(mydate));
            text2.setText("0.00");

            text5.setText("0.00");

        }


        butSave = (Button) findViewById(R.id.butSave);
        butCancel = (Button) findViewById(R.id.butCancel);

        butSave.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyData md0;
                long datenow;
                if (getIntent().hasExtra("MyData")) {
                    md0 = (MyData) getIntent().getSerializableExtra("MyData");
                    datenow = md0.getDate();
                } else {
                    datenow = new Date().getTime();
                }
                MyData md = new MyData(MyDataID, datenow, Double.valueOf(text1.getText().toString()), Double.valueOf(text2.getText().toString()), Double.valueOf(text3.getText().toString()), Double.valueOf(text4.getText().toString()), Double.valueOf(text3.getText().toString()) * Double.valueOf(text4.getText().toString()));
                Intent intent = getIntent();
                intent.putExtra("MyData", md);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        butCancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_CANCELED, new Intent());
                finish();
            }
        });
    }

}
