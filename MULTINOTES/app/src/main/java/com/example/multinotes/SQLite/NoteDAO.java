package com.example.multinotes.SQLite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

//import java.util.Date;
//import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.example.multinotes.Model.Note;

public class NoteDAO {
    private SQLiteDatabase db;
    private List<Note> list;

    public NoteDAO() {
    }
    DBHelper dbHelper;
    public NoteDAO(Context context) {
        dbHelper  = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @SuppressLint("Range")
    public List<Note> getNote(String sql, String... selectionArgs) {

        list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);

        while (cursor.moveToNext()) {
            Note note = new Note();
            note.setId(cursor.getInt(cursor.getColumnIndex("id")));
            note.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            note.setTime(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("time"))));
            note.setContent(cursor.getString(cursor.getColumnIndex("content")));
            note.setHour(cursor.getInt(cursor.getColumnIndex("hour")));
            note.setMinute(cursor.getInt(cursor.getColumnIndex("minute")));
            note.setImage(cursor.getBlob(cursor.getColumnIndex("image")));
            note.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
            list.add(note);

        }
        cursor.close();
        return list;

    }
    @SuppressLint("Range")
    public List<Note> getNoteWithoutImage(String sql, String... selectionArgs) {

        list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);

        while (cursor.moveToNext()) {
            Note note = new Note();
            note.setId(cursor.getInt(cursor.getColumnIndex("id")));
            note.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            note.setTime(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("time"))));
            note.setContent(cursor.getString(cursor.getColumnIndex("content")));
            note.setHour(cursor.getInt(cursor.getColumnIndex("hour")));
            note.setMinute(cursor.getInt(cursor.getColumnIndex("minute")));
            note.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
            list.add(note);

        }
        cursor.close();
        return list;

    }

    public List<Note> getNoteAll() {
        String sql = "SELECT * FROM Note";
        return getNote(sql);
    }
    public List<Note> getNoteAlarm() {
        String sql = "SELECT * FROM Note where status = 1";
        return getNoteWithoutImage(sql);
    }

    public Note getNoteTitle(String title) {
        String sql = "SELECT * FROM Note WHERE title=?";
        List<Note> list = getNote(sql, title);
        return list.get(0);
    }
    public byte[] getImageNote(String id){
        String sql = "SELECT * FROM Note WHERE id=?";
        List<Note> list = getNote(sql, id);
        return list.get(0).getImage();
    }

    public long insert (Note note) {
        ContentValues values = new ContentValues();
        values.put ("title", ( note.title));
        values.put ("time", String.valueOf(( note.time)));
        values.put ("content", note.content);
        values.put("hour",note.getHour());
        values.put("minute",note.getMinute());
        values.put("image",note.image);
        values.put ("status", ( note.status));
        return db. insert("note", null, values);

    }
    public void deleteItem(String id) {
        db.delete("note", "id=?", new String[]{id});
        db.close();
    }

    public long updateItem(Note note){
        ContentValues values = new ContentValues();
        values.put ("title", ( note.title));
        values.put ("time", String.valueOf(( note.time)));
        values.put ("content", note.content);
        values.put("hour",note.getHour());
        values.put("minute",note.getMinute());
        values.put("image",note.image);
        values.put ("status", ( note.status));
        return db.update("note", values,"id=?",new String[]{String.valueOf(note.getId())});
    }
    public long updateInformation(Note note){
        ContentValues values = new ContentValues();
        values.put ("title", ( note.title));
        values.put ("time", String.valueOf(( note.time)));
        values.put ("content", note.content);
        values.put("image",note.image);
        return db.update("note", values,"id=?",new String[]{String.valueOf(note.getId())});
    }

    public void updateStatus_0(String id){
        ContentValues values = new ContentValues();
        values.put ("status", 0);
        db.update("note", values,"id=?",new String[]{id});
        db.close();
    }
    public void updateStatus_1(String id, String time){
        ContentValues values = new ContentValues();
        values.put ("noticeTime", String.valueOf(time));
        values.put ("status", 1);
        db.update("note", values,"id="+id, null);
        db.close();
    }

}

