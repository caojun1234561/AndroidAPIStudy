package com.apidemo.mac.myfinalstydyandroid.badge;

import android.content.Context;
import android.os.Build;

/**
 * Created by cqj on 2018/2/8.
 */

public class BadgeManager {
    private static BadgeManager instance = null;
    private BaseBadgeAdapter badgeAdapter;

    public BadgeManager getInstance() {
        if (instance == null)
            instance = new BadgeManager();
        return instance;
    }

    public void initBadgeManager(Context context) {
//        if (Build.MANUFACTURER.equalsIgnoreCase("samsung")){
//            badgeAdapter
//        }

    }

}
