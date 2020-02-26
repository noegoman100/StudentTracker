package com.sawyer.StudentTracker;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) { //todo receive getExtras to use for message.
        Intent newIntent = new Intent(context, EditAssessmentActivity.class);
        PendingIntent newPendingIntent = PendingIntent.getActivity(context, 0, newIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "studentTracker")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("You set an alarm earlier")
                .setContentText("Here is some more text about the alarm that was set. Thanks!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(newPendingIntent)
                .setAutoCancel(true); //todo create Intent and Pending Intent for onClick.


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, builder.build());
    }
}
