package com.sawyer.StudentTracker;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;

public class ReminderBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent incomingIntent) {
        Log.d("ReminderBroadcast", "Title Received From Intent: " + incomingIntent.getStringExtra("title"));
        Log.d("ReminderBroadcast", "Message Received From Intent: " + incomingIntent.getStringExtra("message"));
        Log.d("ReminderBroadcast", "NotificationID Received From Intent: " + incomingIntent.getIntExtra("notificationID", 667));
        Calendar calendar = Calendar.getInstance();
        Intent newIntent = new Intent(context, HomePageActivity.class);
        PendingIntent newPendingIntent = PendingIntent.getActivity(context, 0, newIntent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "studentTracker")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(incomingIntent.getStringExtra("title"))
                .setContentText(incomingIntent.getStringExtra("message"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(newPendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(incomingIntent.getStringExtra("message")))
                .setAutoCancel(true);

        //int notificationID = (int) ((Math.random() * 100.0) + 100);
        int notificationID = incomingIntent.getIntExtra("notificationID", 666);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationID, builder.build());
        //notificationManager.notify(200, builder.build());
        Log.d("ReminderBroadcast", "NotificationID: " + notificationID);
    }
}
