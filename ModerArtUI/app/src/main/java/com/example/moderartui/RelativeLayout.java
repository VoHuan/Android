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

public class RelativeLayout extends AppCompatActivity {
    SeekBar seekBar2;
    View view2,view3,view4,view5,view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative_layout);

        seekBar2=(SeekBar)findViewById(R.id.seekBar2);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);
        view4 = findViewById(R.id.view4);
        view5 = findViewById(R.id.view5);
        view = findViewById(R.id.view);

        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                int pro = progress/2;
                Toast.makeText(getApplicationContext(),"seekbar progress: "+progress, Toast.LENGTH_SHORT).show();
                view.setBackgroundColor(Color.rgb(63,81+pro,181+pro));
                view2.setBackgroundColor(Color.rgb(236-progress,4,244));
                view3.setBackgroundColor(Color.rgb(233,30+pro,99+progress));
                view4.setBackgroundColor(Color.rgb(244,67+pro,54+progress));
                view5.setBackgroundColor(Color.rgb(255,255-pro,255-progress));
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
                        .setTitle("Th??ng Tin Nh??m")
                        //set message
                        .setMessage("V?? V??n Hu???n\nTr????ng V??n C??ng\nHu???nh Phan Qu???c Huy\nL??? Ng???c H???p")
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