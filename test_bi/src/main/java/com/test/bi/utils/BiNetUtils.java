package com.test.bi.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

/**
 * 网络判断工具类
 *
 * @author xiaxl
 */
public final class BiNetUtils {
    private static final String TAG = "BiNetUtils";


    /**
     * WIFI,ETHERNET链接正常
     *
     * @return
     */
    public static boolean isWifiEthernetConnect(Context context) {

        if (context == null) {
            return false;
        }
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            return false;
        }
        if (ni.getState() == State.CONNECTED
                && (ni.getType() == ConnectivityManager.TYPE_WIFI || ni
                .getType() == ConnectivityManager.TYPE_ETHERNET)) {
            return true;
        }

        return false;
    }

}
