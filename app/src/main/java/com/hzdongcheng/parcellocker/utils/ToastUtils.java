package com.hzdongcheng.parcellocker.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by dcdz on 2017/10/25.
 */

public class ToastUtils {
    public static Toast makeText(Context context, CharSequence text, int duration)
    {
        Toast toast = Toast.makeText(context, text, duration);
        // 这里给了一个1/4屏幕高度的y轴偏移量
        toast.setGravity(Gravity.TOP, 0, 80);
        return toast;
    }
}
