package com.example.scalephoto;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;


import com.test.bi.BiCollector;
import com.test.bi.cache.models.BiData;
import com.test.bi.cache.models.BiSessionData;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xueliangxia
 */

public class TestBiAgent {
    private static final String TAG = "TestBiAgent";

    //##################################################################################################

    /**
     * 更新Session
     * <p>
     * 进入时更新Session(杀进程和Activity结束，下次进来又是一个新的session)
     * 进入后台时，再次回来，又是一个新的session
     *
     * @param mContext
     */
    public static void updateBiSession(Context mContext) {
        if (mContext == null) {
            return;
        }
        String location = "";
        String lon = "";
        String lat = "";
        if (TextUtils.isEmpty(location) == false && location.split(",").length > 1) {
            lon = location.split(",")[0];
            lat = location.split(",")[1];
        }
        BiCollector.updateSession(mContext,
                "userId", "token", "cid",
                "macaddress",
                "ipaddress",
                lon, lat,
                "networkclass",
                "NetworkOperators",
                Build.PRODUCT,
                Build.MODEL,
                "ScreenSize",
                "cid",
                "DeviceId",
                "Imsi",
                "VersionName",
                "cname", "cversion");
    }

    /**
     * 上传Callback
     */
    public static void addUploadBiCallback() {
        BiCollector.setBiUploadListene(new BiCollector.OnBiUploadListener() {
            @Override
            public boolean onUploadSessionData(List<BiSessionData> dataList) {

                return false;
            }

            @Override
            public boolean onUploadBiData(List<BiData> dataList) {
                return false;
            }
        });
    }

    /**
     * 上传bi
     *
     * @param mContext
     */
    public static void uploadBi(Context mContext) {
        if (mContext == null) {
            return;
        }
        BiCollector.checkUpload(mContext);
    }

    /**
     * 添加test埋点
     *
     * @param context
     * @param key
     * @param parameters
     */
    private static void addSnsBI(final Context context, String key, HashMap<String, String> parameters) {
        //---------------
        HashMap<String, String> userInfo = new HashMap<String, String>();
        userInfo.put("uid", "userid");
        userInfo.put("pid", "userid");
        //---------------
        //
        BiCollector.saveBiData(context, userInfo, key, parameters);
    }
    //##################################################################################################


    /**
     *
     * @param context
     * @param refeed_action
     */
    public static void mdOneTest(final Context context, int refeed_action) {
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("mdOneTest", refeed_action + "");
        //
        TestBiAgent.addSnsBI(context, "001", parameters);
    }

}
