package com.example.moderartui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

public class LinearLayout extends AppCompatActivity {

    SeekBar seekBar4;
    View view9,view8,view19,view20,view21;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_layout);

        seekBar4=(SeekBar)findViewById(R.id.seekBar4);
        view9 = findViewById(R.id.view9);
        view8 = findViewById(R.id.view8);
        view19 = findViewById(R.id.view19);
        view20 = findViewById(R.id.view20);
        view21 = findViewById(R.id.view21);

        seekBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                int pro = progress/2;
                Toast.makeText(getApplicationContext(),"seekbar progress: "+progress, Toast.LENGTH_SHORT).show();
                view9.setBackgroundColor(Color.rgb(63,81+pro,181+pro));
                view8.setBackgroundColor(Color.rgb(236-progress,4,244));
                view21.setBackgroundColor(Color.rgb(233,30+pro,99+progress));
                view20.setBackgroundColor(Color.rgb(244,67+pro,54+progress));
                view19.setBackgroundColor(Color.rgb(255,255-pro,255-progress));
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