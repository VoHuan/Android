package com.example.moderartui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;
import android.os.Bundle;
import android.view.MenuInflater;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
    View view12,view13,view14,view15,view16;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar=(SeekBar)findViewById(R.id.seekBar);
        view12 = findViewById(R.id.view12);
        view13 = findViewById(R.id.view13);
        view14 = findViewById(R.id.view14);
        view15 = findViewById(R.id.view15);
        view16 = findViewById(R.id.view16);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                int pro = progress/2;
                Toast.makeText(getApplicationContext(),"seekbar progress: "+progress, Toast.LENGTH_SHORT).show();
                view12.setBackgroundColor(Color.rgb(236-progress,4,244));
                view13.setBackgroundColor(Color.rgb(233,30+pro,99+progress));
                view14.setBackgroundColor(Color.rgb(244,67+pro,54+progress));
                view15.setBackgroundColor(Color.rgb(255,255-pro,255-progress));
                view16.setBackgroundColor(Color.rgb(63,81+pro,181+pro));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(),"seekbar touch started!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(),"seekbar touch stopped!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItem_inf:
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        //set icon
                    .setIcon(android.R.drawable.ic_dialog_alert)
                        //set title
                    .setTitle("Thông Tin Nhóm")
                        //set message
                    .setMessage("Võ Văn Huấn\nTrương Văn Công\nHuỳnh Phan Quốc Huy\nLữ Ngọc Hộp")
                        //set positive button
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        //set what would happen when positive button is clicked
                        finish();
                    })
                        //set negative button
                    .setNegativeButton("No", (dialogInterface, i) -> {
                        //set what should happen when negative button is clicked
                        Toast.makeText(getApplicationContext(),"Nothing Happened",Toast.LENGTH_LONG).show();
                    })
                    .show();
                return true;
            case R.id.menuItem_relative:
                Intent intentRelative = new Intent(this,RelativeLayout.class);
                startActivity(intentRelative);
                return true;
            case R.id.menuItem_linear:
                Intent intentLinear = new Intent(this,LinearLayout.class);
                startActivity(intentLinear);
                return true;
            case R.id.menuItem_table:
                Intent intentTable = new Intent(this,MainActivity.class);
                startActivity(intentTable);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}