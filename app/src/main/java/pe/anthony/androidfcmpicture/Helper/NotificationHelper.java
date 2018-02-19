package pe.anthony.androidfcmpicture.Helper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import pe.anthony.androidfcmpicture.Config.Config;
import pe.anthony.androidfcmpicture.R;

/**
 * Created by ANTHONY on 18/02/2018.
 */

public class NotificationHelper extends ContextWrapper{
    private static final String ANTHO_ID = "pe.anthony.androidfcmpicture";
    private static final String ANTHO_NAME = "anthony";
    private NotificationManager manager;

    public NotificationHelper(Context base) {
       super(base);
       if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
           createChannel();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(ANTHO_ID,ANTHO_NAME,NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableVibration(true);
        channel.enableLights(true);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if(manager == null)
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getChannel(String title, String body, Bitmap bitmap){
      Notification.Style style = new  Notification.BigPictureStyle().bigPicture(bitmap);

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        return new Notification.Builder(getApplicationContext(),ANTHO_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(Config.title)
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setStyle(style);
    }
}
