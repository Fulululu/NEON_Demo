package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnJAVA;
    private Button btnC;
    private Button btnNEON;
    private Button btnASM;
    private TextView timeCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnJAVA = findViewById(R.id.button_demo0);
        btnC = findViewById(R.id.button_demo1);
        btnNEON = findViewById(R.id.button_demo2);
        btnASM = findViewById(R.id.button_demo3);
        timeCost = findViewById(R.id.textView_time);
        timeCost.setText(String.format(getResources().getString(R.string.escapedTime),
                "0"));
    }

    @SuppressLint("StringFormatMatches")
    public void I420ToNV21_JAVA(View view) {
        long escapedTime = System.currentTimeMillis();
        //TODO
        escapedTime = System.currentTimeMillis() - escapedTime;
        timeCost.setText(String.format(getResources().getString(R.string.escapedTime),
                escapedTime));
    }

    @SuppressLint("StringFormatMatches")
    public void I420ToNV21_C(View view) {
        long escapedTime = System.currentTimeMillis();
        //TODO
        escapedTime = System.currentTimeMillis() - escapedTime;
        timeCost.setText(String.format(getResources().getString(R.string.escapedTime),
                escapedTime));
    }

    @SuppressLint("StringFormatMatches")
    public void I420ToNV21_NEON(View view) {
        long escapedTime = System.currentTimeMillis();
        //TODO
        escapedTime = System.currentTimeMillis() - escapedTime;
        timeCost.setText(String.format(getResources().getString(R.string.escapedTime),
                escapedTime));
    }

    @SuppressLint("StringFormatMatches")
    public void I420ToNV21_ASM(View view) {
        long escapedTime = System.currentTimeMillis();
        //TODO
        escapedTime = System.currentTimeMillis() - escapedTime;
        timeCost.setText(String.format(getResources().getString(R.string.escapedTime),
                escapedTime));
    }
}