package com.test.bi;

import android.content.Context;
import android.text.TextUtils;

import com.test.bi.cache.BiDbManager;
import com.test.bi.cache.models.BiData;
import com.test.bi.cache.models.BiSessionData;
import com.test.bi.config.BiConfig;
import com.test.bi.session.SessionConfig;
import com.test.bi.utils.BiLogUtils;
import com.test.bi.utils.BiNetUtils;
import com.test.bi.utils.BiSPUtil;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import bolts.Task;
/**
 * 1、sessionId的生成规则：
 * sessionId的生成规则： md5（设备ID+用户Id+时间戳）
 * 2、sessionId的生命周期：
 * 进入生成新的sessionId
 * 进入后台sessionId生命周期结束
 * 杀进程sessionId生命周期结束
 * 3、上传规则：
 * 进入上传
 * 退出上传
 * 日志积累到40条上传
 * 程序切换到后台上传
 */
public class BiCollector {
    private static final String TAG = "BiCollector";


    //################################################################Begin############################################################

    private static OnBiUploadListener mUploadListener = null;

    /**
     * 上传的回调，回调到主业务工程中做上传工作
     *
     * @param listener
     */
    public static void setBiUploadListene(OnBiUploadListener listener) {
        BiCollector.mUploadListener = listener;
    }

    public static interface OnBiUploadListener {
        public boolean onUploadSessionData(List<BiSessionData> dataList);

        public boolean onUploadBiData(List<BiData> dataList);
    }


    //##################Session begin####################

    /**
     * 创建一条Session数据
     *
     * @param context
     * @param uid
     * @param token      签名
     * @param cid        客户端的cid设备id
     * @param mac        设备的mac地址
     * @param ip         设备的ip地址
     * @param lon        经度
     * @param lat        维度
     * @param network    网络类型
     * @param operator   运营商
     * @param brand      品牌
     * @param model      设备型号
     * @param hw         屏幕宽高
     * @param gid        用的设备id
     * @param imei       设备编号
     * @param imsi       通讯卡编号
     * @param appVersion app版本号
     * @param cname      渠道名称
     * @param cversion   渠道版本号
     */
    public static void updateSession(final Context context,
                                     String uid,
                                     String token,
                                     String cid,
                                     String mac,
                                     String ip,
                                     String lon,
                                     String lat,
                                     String network,
                                     String operator,
                                     String brand,
                                     String model,
                                     String hw,
                                     String gid,
                                     String imei,
                                     String imsi,
                                     String appVersion,
                                     String cname,
                                     String cversion) {
        // 更新一条session
        String sessionId = SessionConfig.updateSessionId(cid, uid);
        // 保存一条sessison数据
        BiCollector.saveSession(context, sessionId, uid, token, cid, mac, ip, lon, lat, network, operator, brand, model, hw, gid, imei, imsi, appVersion, cname, cversion);
    }


    /**
     * 保存一条session数据
     *
     * @param context
     * @param sessionId
     * @param uid
     * @param token
     * @param cid
     * @param mac
     * @param ip
     * @param lon
     * @param lat
     * @param network
     * @param operator
     * @param brand
     * @param model
     * @param hw
     * @param gid
     * @param imei
     * @param imsi
     * @param appVersion
     * @param cname
     * @param cversion
     */
    private static void saveSession(final Context context,
                                    String sessionId,
                                    String uid,
                                    String token,
                                    String cid,
                                    String mac,
                                    String ip,
                                    String lon,
                                    String lat,
                                    String network,
                                    String operator,
                                    String brand,
                                    String model,
                                    String hw,
                                    String gid,
                                    String imei,
                                    String imsi,
                                    String appVersion,
                                    String cname,
                                    String cversion) {
        BiLogUtils.d(TAG, "---save---");
        if (context == null) {
            return;
        }
        final BiSessionData biData = new BiSessionData();
        biData.timeId = System.currentTimeMillis();
        biData.sessionId = sessionId;
        biData.uid = uid;
        biData.token = token;
        biData.cid = cid;
        biData.mac = mac;
        biData.ip = ip;
        biData.lon = lon;
        biData.lat = lat;
        biData.network = network;
        biData.operator = operator;
        biData.os = "Android";
        biData.brand = brand;
        biData.model = model;
        biData.hw = hw;
        biData.gid = gid;
        biData.idfa = "";
        biData.imei = imei;
        biData.imsi = imsi;
        biData.appVersion = appVersion;
        biData.cname = cname;
        biData.cversion = cversion;

        //
        Task.call(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                //设置线程优先级为后台
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                // 插入数据
                BiDbManager.getInstance().insertBiSessionData(context, biData);
                return null;
            }
        }, Task.BACKGROUND_EXECUTOR);
    }
    //##################Session end####################


    public static void saveBiData(final Context context,
                                  HashMap<String, String> userInfo,
                                  String key,
                                  HashMap<String, String> parameters) {

        BiLogUtils.d(TAG, "---saveBiData---");
        if (context == null || TextUtils.isEmpty(key)) {
            return;
        }
        final BiData biData = new BiData();
        //
        biData.timeId = System.currentTimeMillis();
        //
        biData.sessionId = SessionConfig.getSessionId();
        //
        if (userInfo == null || userInfo.size() == 0) {
            biData.uid = "";
        } else {
            biData.uid = userInfo;
        }
        //
        biData.key = key;
        //
        if (parameters == null || parameters.size() == 0) {
            biData.parameters = "";
        } else {
            biData.parameters = parameters;
        }
        //
        biData.sequence = SessionConfig.getSequence() + "";

        //
        Task.call(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                //设置线程优先级为后台
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                // 插入数据
                BiDbManager.getInstance().insertBiData(context, biData);

                // -----超过40条上传---------
                int count = BiDbManager.getInstance().getBiDataCount(context);
                if (count >= BiConfig.UPLOAD_COUNT) {
                    BiCollector.checkUpload(context);
                }
                return null;
            }
        }, Task.BACKGROUND_EXECUTOR);
    }


    /**
     * 检查是否上传
     * <p>
     * wifi 直接上传，不判断
     * 非wifi 判断一天
     *
     * @param context
     */
    public static void checkUpload(final Context context) {
        // wifi
        if (BiNetUtils.isWifiEthernetConnect(context)) {
            upload_Async(context);
        }
        // 非wifi
        else {
            // 获取上次的上传时间
            BiSPUtil.getInstance().init(context);
            Long preUploadTime = BiSPUtil.getInstance().getLongValue("uploadTime", 0);
            if (System.currentTimeMillis() - preUploadTime >= BiConfig.ONE_DAY_INMillS) {
                upload_Async(context);
            }
        }
    }

    /**
     * 上传日志(直接上传)
     *
     * @param context
     */
    private static void upload_Async(final Context context) {
        BiLogUtils.d(TAG, "---upload_Async---");

        // 1、查询session数据、上传、删除
        // 2、查询bidata数据、上传、删除
        Task.call(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                //设置线程优先级为后台
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                upload_Sync(context);
                return null;
            }
        }, Task.BACKGROUND_EXECUTOR);

    }

    /**
     * 同步该上传方法，防止多线程出错
     */
    private static synchronized void upload_Sync(final Context context) {

        // 存入上传时间
        BiSPUtil.getInstance().init(context);
        BiSPUtil.getInstance().putLongValue("uploadTime", System.currentTimeMillis());

        //--------------------------------------------
        // 1、查询session数据、上传、删除
        List<BiSessionData> mSessionDataList = BiDbManager.getInstance().queryBiSessionData(context);
        BiLogUtils.d(TAG, "mSessionDataList: " + mSessionDataList);
        // 上传session数据
        if (mUploadListener != null && mSessionDataList != null && mSessionDataList.size() > 0) {
            BiLogUtils.d(TAG, "uploadListener != null");
            // 调用主工程中的方法进行上传
            boolean isUpLoaded = mUploadListener.onUploadSessionData(mSessionDataList);
            // 回调结束后置空
            BiLogUtils.d(TAG, "isUpLoaded: " + isUpLoaded);
            if (isUpLoaded) {
                // 删除已上传日志
                if (mSessionDataList != null && mSessionDataList.size() > 0) {
                    Long startTime = mSessionDataList.get(0).timeId;
                    boolean deleteOldFlag = BiDbManager.getInstance().deleteUploadSessionData(context, startTime);
                    BiLogUtils.d(TAG, "delete upload_Async Data: " + deleteOldFlag);
                }
            }
        }
        //--------------------------------------------
        // 2、查询bidata数据、上传、删除
        List<BiData> mBiDataList = BiDbManager.getInstance().queryBiData(context);
        BiLogUtils.d(TAG, "mBiDataList: " + mBiDataList);
        if (mUploadListener == null) {
            BiLogUtils.d(TAG, "uploadListener == null");
        }
        // 上传session数据
        if (mUploadListener != null && mBiDataList != null && mBiDataList.size() > 0) {
            BiLogUtils.d(TAG, "uploadListener != null");
            // 调用主工程中的方法进行上传
            boolean isUpLoaded = mUploadListener.onUploadBiData(mBiDataList);
            // 回调结束后置空
            BiLogUtils.d(TAG, "isUpLoaded: " + isUpLoaded);
            if (isUpLoaded) {
                // 删除已上传日志
                if (mBiDataList != null && mBiDataList.size() > 0) {
                    Long startTime = mBiDataList.get(0).timeId;
                    boolean deleteOldFlag = BiDbManager.getInstance().deleteUploadBiData(context, startTime);
                    BiLogUtils.d(TAG, "delete upload_Async Data: " + deleteOldFlag);
                }
            }
        }
    }


    //################################################################end############################################################

}
