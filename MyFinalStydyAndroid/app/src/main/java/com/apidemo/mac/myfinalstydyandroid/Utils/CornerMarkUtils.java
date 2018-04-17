package com.apidemo.mac.myfinalstydyandroid.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import com.apidemo.mac.myfinalstydyandroid.MainActivity;
import com.apidemo.mac.myfinalstydyandroid.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by cqj on 2018/2/5.
 */

public class CornerMarkUtils {

    /**
     * 在垃圾手机上亲测有效
     */
    private void vivo(Context context) {
        Intent intent = new Intent("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
        intent.putExtra("packageName", context.getPackageName());
        String launchClassName = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).getComponent().getClassName();
        intent.putExtra("className", launchClassName);
        intent.putExtra("notificationNum", 1);
        context.sendBroadcast(intent);
    }

    private void xiaomi(Context context) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder builder = new Notification.Builder(context)

                .setContentTitle("title").setContentText("text").setSmallIcon(R.mipmap.ic_launcher);

        Notification notification = builder.build();

        try {

            Field field = notification.getClass().getDeclaredField("extraNotification");

            Object extraNotification = field.get(notification);

            Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);

            method.invoke(extraNotification, 1);

        } catch (Exception e) {

            e.printStackTrace();

        }

        mNotificationManager.notify(0, notification);
    }

    private void fuck2(Context context) {
        Intent intent = new Intent("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
        intent.putExtra("packageName", context.getPackageName());
        intent.putExtra("className", MainActivity.class.getName());
        intent.putExtra("notificationNum", 1);
        context.sendBroadcast(intent);
    }

}
