package com.apidemo.mac.myfinalstydyandroid.MyView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by cqj on 2018/3/5.
 */

public class ScrollDismissLayout extends LinearLayout {
    private int rawY = 0;
    private int lastY = 0;

    public ScrollDismissLayout(Context context) {
        super(context);
    }

    public ScrollDismissLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollDismissLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) getY();
        Log.e("height", getHeight() + "");
        Log.e("getTranslationY", getTranslationY() + "");
        Log.e("getY", getY() + "");
        Log.e("getScrollY", getScrollY() + "");
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            rawY = (int) (event.getRawY());
            lastY = rawY;
            Log.e("lastY：", "" + lastY);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            rawY = (int) event.getRawY();
            int offsetY = rawY - lastY;
            if (y + offsetY > 0) {
                layout(getLeft(),
                        getTop() - y,
                        getRight(),
                        getBottom() + -y);
                return true;
            }
            Log.e("offset:", "" + offsetY);
            layout(getLeft(),
                    getTop() + offsetY,
                    getRight(),
                    getBottom() + offsetY);
            lastY = rawY;
            Log.e("mvoew lastY：", "" + lastY);
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (-getY() > getHeight() / 3) {
                //滑出去
                int offest = getHeight() + (int) getY();
                layout(getLeft(), getTop() - offest, getRight(), getBottom() - offest);
            } else {
                //滑回来
                layout(getLeft(), getTop() - (int) getY(), getRight(), getBottom() - (int) getY());
            }
        }
        return true;
    }
}
