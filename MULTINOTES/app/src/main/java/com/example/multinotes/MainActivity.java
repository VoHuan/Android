package com.example.multinotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.multinotes.SQLite.DBHelper;
import com.example.multinotes.SQLite.NoteDAO;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.multinotes.Adapter.NoteAdapter;
import com.example.multinotes.Model.Note;

public class MainActivity extends AppCompatActivity {

    private NoteAdapter noteAdapter;
    private ListView lvNote;
    NoteDAO dao;
    List<Note> list,listAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.FOREGROUND_SERVICE}, PackageManager.PERMISSION_GRANTED);
//        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);


        addControl();
        addEvents();


        DBHelper DBHelper = new DBHelper(this);
        SQLiteDatabase database = DBHelper.getReadableDatabase();
        database.close();

    }

    private void addControl() {

        lvNote = findViewById(R.id.lvNote);
    }

    private void addEvents() {
        loadItemOnListView ();
        clickItemListView();
        deleteItem();


    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_activity, menu);

        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuItem_information)
        {
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

                    })
                    .show();

        }
        if(item.getItemId() == R.id.menuItem_add)
        {
            openSubActivity();
        }
        return true;
    }
    public void openSubActivity(){
        Intent intent = new Intent(MainActivity.this,SubActivity.class);
        startActivity(intent);
    }

    public void loadItemOnListView(){
        //lấy toàn bộ note trong db vào list
        dao = new NoteDAO(MainActivity.this);
        list = dao.getNoteAll();

        //truyền danh sách note lên listview
        noteAdapter= new NoteAdapter(MainActivity.this, list); //truyền list vào adapter
        lvNote.setAdapter(noteAdapter); //truyền adapter vào listview
    }
    public void clickItemListView(){
        lvNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                try{
                    Note note = list.get(position);

                    Intent intent = new Intent(MainActivity.this,SubActivity.class);
                    Bundle bundle = new Bundle();

                    bundle.putSerializable("note", (Serializable) note);

                    intent.putExtras(bundle);

                    startActivity(intent);
                }catch(Exception E){
                    System.out.println(E);
                }
            }
        });
    }
    public void deleteItem(){
                lvNote.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        //set icon
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        //set title
                        .setTitle("Xóa thông báo ?")
                        //set message
                        .setMessage("Bạn Muốn Xóa Thông Báo ?")
                        //set positive button
                        .setPositiveButton("Yes", (dialogInterface, i1) -> {
                            //set what would happen when positive button is clicked
                            Note note = (Note) noteAdapter.getItem(position);
                            int idd = note.getId(); //get id
                            dao.deleteItem(idd+""); //delete
                            Toast.makeText(MainActivity.this,"Xóa thành công !",Toast.LENGTH_LONG).show();

                            loadItemOnListView();

                        })
                        //set negative button
                        .setNegativeButton("No", (dialogInterface, i1) -> {
                        })
                        .show();

                return true;
            }
        });
    }


//    public void ServiceCaller(Intent intent) {
//        dao = new NoteDAO(MainActivity.this);
//        listAlarm = dao.getNoteAlarm();
//        int size = listAlarm.size();
//        if(size != 0){
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//                stopService(intent);
//
//                intent.putExtra("size", size);
//
//                for (int i = 0; i < size; i++) {
//                    String key = "note" + i;
//                    System.out.println(key);
//                    Note note = listAlarm.get(i);
//                    System.out.println(note.getId());
//                    intent.putExtra(key, note);
//                }
//            }
//
//            startService(intent);
//        }
//    }
}