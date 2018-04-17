package com.apidemo.mac.myfinalstydyandroid.badge;

import android.content.Context;

/**
 * Created by cqj on 2018/2/8.
 */

public interface BadgeInterface {
    void addBadgeNum(int addNum);

    void removeBadge();

    void setBadgeNum(int num);

    void setLauncherClassName(String name);

    void setContext(Context context);
}
