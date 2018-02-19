package pe.anthony.androidfcmpicture.FirebaseService;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.logging.Handler;

import pe.anthony.androidfcmpicture.Config.Config;
import pe.anthony.androidfcmpicture.Display;
import pe.anthony.androidfcmpicture.Helper.NotificationHelper;
import pe.anthony.androidfcmpicture.R;

/**
 * Created by ANTHONY on 18/02/2018.
 */

public class MyFirebaseMessasingService extends FirebaseMessagingService {

    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                showNotificationWithImageLevel26(bitmap);
            else 
            showNotificationWithImage(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotificationWithImageLevel26(Bitmap bitmap) {
        NotificationHelper helper = new NotificationHelper(getBaseContext());

        Notification.Builder builder =  helper.getChannel(Config.title,Config.message,bitmap);
        helper.getManager().notify(0,builder.build());
    }

    private void showNotificationWithImage(Bitmap bitmap) {
        NotificationCompat.BigPictureStyle style = new  NotificationCompat.BigPictureStyle();
        style.setSummaryText(Config.message);
        style.bigPicture(bitmap);
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(getApplicationContext(), Display.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK );
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,0);

        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder)new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(Config.title)
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentIntent(pendingIntent)
                .setStyle(style);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,notificationBuilder.build());
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getData() != null)
            getImage(remoteMessage);
    }

    private void getImage(final RemoteMessage remoteMessage) {
        //Configura mensaje y el titulo
        Config.message = remoteMessage.getNotification().getBody();
        Config.title = remoteMessage.getNotification().getTitle();
        //Crea un hilo para guardar la imagen de notificacion
        if(remoteMessage.getData() != null){
            /*Esto es para guardar la imagen*/
            Config.imageLink = remoteMessage.getData().get("image");
            /**/
            android.os.Handler uiHandler = new android.os.Handler(Looper.getMainLooper());
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                //Configurar la imgagen de la notificacion
                    Picasso.with(getApplicationContext())
                            .load(remoteMessage.getData().get("image"))
                            .into(target);
                }
            });

        }
    }
}
