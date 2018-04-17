package com.apidemo.mac.myfinalstydyandroid.badge;

import android.content.Context;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

/**
 * Created by cqj on 2018/2/8.
 */

public abstract class BaseBadgeAdapter implements BadgeInterface {
    protected static String launcherName;
    protected static String packageName;
    protected WeakReference<Context> contextWeakReference;
    protected int num;

    @Override
    public void addBadgeNum(int addNum) {
        num += addNum;
        updateBadge();
    }

    @Override
    public void removeBadge() {
        num = 0;
        updateBadge();
    }

    @Override
    public void setBadgeNum(int num) {
        this.num = num;
        updateBadge();
    }

    @Override
    public void setLauncherClassName(@NonNull String name) {
        this.launcherName = name;
    }

    @Override
    public void setContext(@NonNull Context context) {
        contextWeakReference = new WeakReference<Context>(context);
    }

    /**
     * 更新小红点
     */
    protected abstract void updateBadge();

}
