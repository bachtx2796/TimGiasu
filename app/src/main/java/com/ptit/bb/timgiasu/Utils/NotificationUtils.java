package com.ptit.bb.timgiasu.Utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.ptit.bb.timgiasu.R;

public class NotificationUtils {

    public static void showNotification(Context context, String title, CharSequence msg, PendingIntent pendingIntent) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        NotificationCompat.Builder mBuilder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_tutor)
                    .setContentTitle(title)
                    .setAutoCancel(true)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(defaultSoundUri)
                    .setColor(context.getResources().getColor(R.color.green))
                    .setContentIntent(pendingIntent)
                    .setContentText(msg);


        } else {
            mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_tutor)
                    .setContentTitle(title)
                    .setAutoCancel(true)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
                    .setContentText(msg);
        }

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (mNotificationManager != null) {
            mNotificationManager.notify(GlobalStuff.getFreshInt(), mBuilder.build());
        }
    }

}
