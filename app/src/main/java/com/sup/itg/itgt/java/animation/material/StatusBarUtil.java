package com.sup.itg.itgt.java.animation.material;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class StatusBarUtil {

    /**
     * 为Activity状态栏设置颜色
     *
     * @param color 状态栏的颜色值
     */
    public static void setStatusBarColor(Activity activity, int color) {
        int currentOsVerson = Build.VERSION.SDK_INT;
        if (currentOsVerson >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(color);
        } else if (currentOsVerson >= Build.VERSION_CODES.KITKAT) {
            //这里使用了一个技巧，首先把界面弄成全屏，然后在加入一个状态栏
            //WindowManager.LayoutParams.FLAG_FULLSCREEN 此全屏会把状态栏上面的，时间，电量都给覆盖了
            //WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS 这个会让activity 的window变成同一个颜色，到大状态栏部分
            //会稍alpha，但是window的颜色
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            View view = new View(activity);
            view.setBackgroundColor(color);
            ViewGroup.LayoutParams viewLayout = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusHeight(activity));
            view.setLayoutParams(viewLayout);
            ViewGroup decorView = (ViewGroup) window.getDecorView();
            decorView.addView(view);
            View contentView = activity.findViewById(android.R.id.content);
            contentView.setPadding(0,getStatusHeight(activity),0,0);

        } else {//4.4以下，暂时没办法

        }
    }


    public static int getStatusHeight(Activity activity) {
        //先获取资源的ID,根据ID获取高度
        Resources resources = activity.getResources();
        int statusBarHeightId = resources.getIdentifier("status", "dimen", "android");
        return resources.getDimensionPixelOffset(statusBarHeightId);
    }

    public static void setActivityTransluction(Activity activity){
        int currentOsVerson = Build.VERSION.SDK_INT;
        if (currentOsVerson >= Build.VERSION_CODES.LOLLIPOP) {
            View view = activity.getWindow().getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (currentOsVerson >= Build.VERSION_CODES.KITKAT) {
           activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
