package com.ece.nsu.fall2020.cse299.sec2.appuitest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rbddevs.splashy.Splashy;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_profile);
        setSplashy();

    }

    public void setSplashy(){
        new Splashy(this)
                .setLogo(R.drawable.ic_applogo)
                .setTitle("Help Me App")
                .setTitleColor("#272727")
                .setSubTitle("Stand against the social violence around you")
                .setSubTitleColor("#374045")
                .setBackgroundResource(R.drawable.custom_gradient_color)
                .setProgressColor("#FFFFFF")
                .setFullScreen(true)
                .setDuration(3000)
                .show();
    }
}