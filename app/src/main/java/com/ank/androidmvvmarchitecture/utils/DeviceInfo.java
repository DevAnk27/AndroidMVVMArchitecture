package com.ank.androidmvvmarchitecture.utils;

import android.os.Build;
import android.text.TextUtils;

import java.lang.reflect.Field;

/**
 * Created by CIS Dev 816 on 12/7/17.
 */

public class DeviceInfo {

    private static final String TAG = DeviceInfo.class.getSimpleName();

    /**
     * Returns the os name of device
     */
    public static String getOS() {
        String osName = "";
        try {
            Field[] fields = Build.VERSION_CODES.class.getFields();
            if (Build.VERSION.SDK_INT == 19) {
                osName = fields[Build.VERSION.SDK_INT].getName();
            } else {
                osName = fields[Build.VERSION.SDK_INT + 1].getName();
            }
        } catch (Exception e) {

        }

        return osName;
    }

    /**
     * Returns the os sdk version code of device
     */
    public static String getOSVersion() {
        int sdkVersion = Build.VERSION.SDK_INT;
        return "" + sdkVersion;
    }

    /**
     * Returns the manufacturer company name of device
     */
    public static String getManufacturer() {
        String manufacturer = Build.MANUFACTURER;
        return capitalize(manufacturer);
    }

    /**
     * Returns the consumer friendly device name
     */
    public static String getModel() {
        String model = Build.MODEL;
        return capitalize(model);
    }

    /**
     * Returns the unique serial number of device
     */
    public static String getSerialNumber() {
        String serialNumber = Build.SERIAL;
        return serialNumber;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }
}
