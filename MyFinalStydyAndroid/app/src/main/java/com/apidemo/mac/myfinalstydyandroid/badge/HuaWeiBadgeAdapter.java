package com.apidemo.mac.myfinalstydyandroid.badge;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by cqj on 2018/2/8.
 */

public class HuaWeiBadgeAdapter extends BaseBadgeAdapter {

    @Override
    protected void updateBadge() {
        try {
            if (launcherName == null) {
                return;
            }
            Context context = contextWeakReference.get();
            if (context == null)
                return;
            Bundle localBundle = new Bundle();
            localBundle.putString("package", packageName);
            localBundle.putString("class", launcherName);
            localBundle.putInt("badgenumber", num);
            context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, localBundle);
            return;
        } catch (Throwable paramContext) {
        }
    }
}
