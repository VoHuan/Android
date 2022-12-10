package com.example.multinotes;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;

import com.example.multinotes.Model.Note;
import com.example.multinotes.SQLite.NoteDAO;

import java.io.Serializable;
import java.security.Provider;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    List<Note> list = new ArrayList<Note>();

    public int onStartCommand(Intent intent, int flags, int startId) {

        int size = intent.getIntExtra("size", 0);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < size; i++) {
                    String key = "note" + i;
                    Note n = (Note) intent.getSerializableExtra(key);
                    list.add(n);
                }
            }
        }).start();



        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (list.size() > 0) {
                        for (Note i : list) {
                            //System.out.println(i);
                            System.out.println(i.getHour()+":"+i.getMinute()+"NOTE");
                            System.out.println(Calendar.getInstance().getTime().getHours()+":"+Calendar.getInstance().getTime().getMinutes() +"ÍSNTANCE");
                            if (Calendar.getInstance().getTime().getHours() == i.getHour() &&
                                    Calendar.getInstance().getTime().getMinutes() == i.getMinute()) {

                                sendNotification(i);
                            }
                        }
                    }
                    try {
                        Thread.sleep(50000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        stopSelf();
        super.onDestroy();
    }

    public void sendNotification(Note note) {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(this, SubActivity.class);
        intent.putExtra("note",note);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(note.getId(), PendingIntent.FLAG_CANCEL_CURRENT);
        Notification notification = new NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
                .setContentTitle(note.getTitle())
                .setContentText(note.getContent())
                .setSmallIcon(R.drawable.launcher)
                .setSound(uri)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(note.getContent()))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(note.getId(), notification);

    }

}
