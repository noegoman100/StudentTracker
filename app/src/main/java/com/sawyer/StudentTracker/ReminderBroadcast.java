package com.sawyer.StudentTracker;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent newIntent = new Intent(context, HomePageActivity.class);
        //todo receive getExtras to use for message.
//        int termId = intent.getIntExtra("termId", 0);
//        int courseId = intent.getIntExtra("courseId", 0);
//        int assessmentId = intent.getIntExtra("assessmentId", 0);
//        newIntent.putExtra("termId", termId);
//        newIntent.putExtra("courseId", courseId);
//        newIntent.putExtra("assessmentId", assessmentId);
        PendingIntent newPendingIntent = PendingIntent.getActivity(context, 0, newIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "studentTracker")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(intent.getStringExtra("title"))
                .setContentText(intent.getStringExtra("message"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(newPendingIntent)
                .setAutoCancel(true); //todo create Intent and Pending Intent for onClick.


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, builder.build());
    }
}
