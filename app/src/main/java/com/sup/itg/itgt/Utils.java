package com.sup.itg.itgt;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils {


    public static String readAssertDir(Context context) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open("home_item_list.json");
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader bfr = new BufferedReader(isr);
            StringBuilder stringBuilder = new StringBuilder();
            for (String str = bfr.readLine(); str != null; str = bfr.readLine()) {
                stringBuilder.append(str);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
