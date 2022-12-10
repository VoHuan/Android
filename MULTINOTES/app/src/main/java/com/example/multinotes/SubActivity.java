package com.example.multinotes;

import androidx.annotation.Dimension;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.multinotes.SQLite.NoteDAO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
//import java.sql.Date;

import com.example.multinotes.Model.Note;

public class SubActivity extends AppCompatActivity {
    EditText edtContent,edtTitle;
    TextView tvLBLThoiGian,edtTimeNotification;
    ImageView imgHinhAnh;
    View menuItem_image;
    Switch swtCheck;
    int REQUEST_CODE = 123;
    public int id;
    public int hour,minute;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    List<Note> listAlarm;
    NoteDAO dao;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

//        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.FOREGROUND_SERVICE}, PackageManager.PERMISSION_GRANTED);
//        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        addControls();
        addEvents();


    }

    private void addEvents() {
        getItemListView(); //Nhận dữ liệu khi click item
        setTimeNotification();

        swtCheck.setOnCheckedChangeListener((compoundButton, b) -> {
            if(swtCheck.isChecked()==true){
                showTimePickerDialog();
                tvLBLThoiGian.setVisibility(View.VISIBLE);
                edtTimeNotification.setVisibility(View.VISIBLE);

            }
            else{
                tvLBLThoiGian.setVisibility(View.INVISIBLE);
                edtTimeNotification.setVisibility(View.INVISIBLE);
                showDialog();
            }
        });

    }

    private void addControls() {
        tvLBLThoiGian = findViewById(R.id.tvLBLThoiGian);
        swtCheck = findViewById(R.id.swtCheck);
        edtTimeNotification = findViewById(R.id.edtTimeNotification);
        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        imgHinhAnh = findViewById(R.id.imgHinhAnh);
        menuItem_image = findViewById(R.id.menuItem_image);

        //hide
        tvLBLThoiGian.setVisibility(View.INVISIBLE);
        edtTimeNotification.setVisibility(View.INVISIBLE);

    }



    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_sub_activity, menu);

        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return true;
    }

    public void openMainActivity(){
        Intent intent = new Intent(SubActivity.this,MainActivity.class);
        startActivity(intent);
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItem_image:
                Intent intent =new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE);
                return true;
            case R.id.menuItem_save:

                NoteDAO dao = new NoteDAO(this);
                Note note = new Note();

                //get data note on UI
                note.setId(id);
                note.setTitle(edtTitle.getText().toString());
                java.util.Date date = new java.util.Date();
                note.setTime(new Timestamp(date.getTime()));
                note.setContent(edtContent.getText().toString());

                if(swtCheck.isChecked()==true){
                    note.setHour(hour);
                    note.setMinute(minute);
                    note.setStatus(1);
                }
                else {
                    note.setStatus(0);
                }
                if(imgHinhAnh.getDrawable() != null){
                    note.setImage(ConverttoArrayByte(imgHinhAnh));
                }

                if(id != 0){
                    if(swtCheck.isChecked()){
                        dao.updateItem(note);
                        Toast.makeText(this,"Cập nhật thành công !", Toast.LENGTH_LONG).show();
                        openMainActivity();

                        final Intent intentService = new Intent(this, MyService.class);
                        ServiceCaller(intentService); //open service
                        return true;
                    }
                    else{
                        dao.updateInformation(note);
                        Toast.makeText(this,"Cập nhật thành công !", Toast.LENGTH_LONG).show();
                        openMainActivity();

                        final Intent intentService = new Intent(this, MyService.class);
                        ServiceCaller(intentService); //open service
                        return true;
                    }

                }
                else{
                    dao.insert(note);
                    Toast.makeText(this,"Thêm mới thành công !", Toast.LENGTH_LONG).show();
                    openMainActivity();
                    return true;

                }
               // return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void showTimePickerDialog(){
        TimePickerDialog.OnTimeSetListener callback = (timePicker, i, i1) -> {
            calendar.set(Calendar.HOUR,i);
            calendar.set(Calendar.MINUTE,i1);
            edtTimeNotification.setText(sdf.format(calendar.getTime()));

            hour = i;
            minute = i1;

        };
        TimePickerDialog dialog = new TimePickerDialog(
                SubActivity.this,
                callback,
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                true
        );
        dialog.show();

    }
    public void getItemListView() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {

            Note note = (Note) bundle.getSerializable("note");

            id = note.getId();
            String title = note.getTitle();
            String content = note.getContent();
            int hour = note.getHour();
            int minute = note.getMinute();
            int status = note.getStatus();

//            if(note.getImage()!= null){
//                Bitmap bitmap= BitmapFactory.decodeByteArray(note.getImage(), 0, note.getImage().length);
//                imgHinhAnh.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 700, 800, false));
//            }

            //get Image from note
            dao = new NoteDAO(SubActivity.this);
            byte [] image = dao.getImageNote(String.valueOf(note.getId()));

            if(image != null){
                Bitmap bitmap= BitmapFactory.decodeByteArray(image, 0, image.length);
                imgHinhAnh.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 700, 800, false));
            }

            edtTitle.setText(title);
            edtContent.setText(content);

            if(status == 1){
                tvLBLThoiGian.setVisibility(View.VISIBLE);
                edtTimeNotification.setVisibility(View.VISIBLE);
                swtCheck.setChecked(true);
                if(minute<10)
                    edtTimeNotification.setText(hour+":0"+minute);
                else
                    edtTimeNotification.setText(hour+":"+minute);
            }

        }
    }
    public void showDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                //set icon
                .setIcon(android.R.drawable.ic_dialog_alert)
                //set title
                .setTitle("Hủy thông báo")
                //set message
                .setMessage("Bạn có muốn hủy hẹn giờ thông báo ?")
                //set positive button
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    //set what would happen when positive button is clicked

                    NoteDAO dao = new NoteDAO(this);
                    dao.updateStatus_0(id+""); //update status note

                    final Intent intentService = new Intent(this, MyService.class);
                    ServiceCaller(intentService); //open service

                    openMainActivity();
                })
                //set negative button
                .setNegativeButton("No", (dialogInterface, i) -> {
                    //set what should happen when negative button is clicked
                    swtCheck.setChecked(true);

                })
                .show();
    }
    public void setTimeNotification(){
        edtTimeNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_CODE && resultCode==RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgHinhAnh.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 700, 800, false));
            }catch( FileNotFoundException e){
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public byte[] ConverttoArrayByte(ImageView img)
    {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap=bitmapDrawable.getBitmap();

        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public void ServiceCaller(Intent intent) {

        dao = new NoteDAO(SubActivity.this);
        listAlarm = dao.getNoteAlarm();
        int size = listAlarm.size();
        if (size != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                stopService(intent);

                intent.putExtra("size", size);
                for (int i = 0; i < size; i++) {
                    String key = "note" + i;
                    Note note = listAlarm.get(i);
                    intent.putExtra(key, note);
                }
            }
            startService(intent);
        }
        else
            stopService(intent);
    }
}


