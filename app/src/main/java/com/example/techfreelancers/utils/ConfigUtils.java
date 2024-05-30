package com.example.techfreelancers.utils;

import android.content.Context;
import android.content.res.Resources;

import com.example.techfreelancers.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {

    public static Properties loadConfig(Context context) {
        Properties properties = new Properties();
        try {
            Resources res = context.getResources();
            InputStream is = res.openRawResource(R.raw.config);
            properties.load(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
