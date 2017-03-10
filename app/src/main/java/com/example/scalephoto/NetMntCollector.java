package com.example.scalephoto;

import android.content.Context;
import android.text.TextUtils;

import com.example.scalephoto.cache.NetMntDbManager;
import com.example.scalephoto.cache.models.NetMntLogData;
import com.example.scalephoto.config.NetMConfig;
import com.example.scalephoto.utils.NetMntLogUtils;
import com.example.scalephoto.utils.NetMntSPUtil;
import com.example.scalephoto.utils.NetUtils;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import bolts.Task;

/**
 * 统一网络监控相关类
 * create by xiaxl on 2017.03.07.
 * <p>
 * 这里是所有的异步添加日志的方法
 * <p>
 */
public class NetMntCollector {
    private static final String TAG = "NetMntCollector";


    //#####################################################begin#######################################################################

    private static OnNetMntUploadListener mUploadListener = null;

    /**
     * 上传的回调，回调到主业务工程中做上传工作
     *
     * @param listener
     */
    public static void setNetMntUploadListener(OnNetMntUploadListener listener) {
        NetMntCollector.mUploadListener = listener;
    }

    public static interface OnNetMntUploadListener {
        public boolean onUpload(List<NetMntLogData> dataList);
    }

    /**
     * 保存数据,将一条数据存入数据库
     *
     * @param app_version app版本
     * @param ip          访问ip地址
     * @param network     wifi、3G等
     * @param operator    运营商
     * @param page        埋点所在页面
     * @param event       事件名
     * @param mapData
     */
    private static void save(final Context context, String app_version, String ip, String network, String operator, String page, String event, HashMap<String, String> mapData) {
        NetMntLogUtils.d(TAG, "---save---");
        if (context == null || TextUtils.isEmpty(event) || mapData == null || mapData.size() == 0) {
            return;
        }
        final NetMntLogData biData = new NetMntLogData();
        // -----App版本-----
        biData.setApp_version(app_version);
        // -----网络信息----
        biData.setEvent(event);
        biData.setIp(ip);
        biData.setNetwork(network);
        biData.setOperator(operator);
        // -----当前时间戳-----
        Long currentTime = System.currentTimeMillis();
        biData.setCtime(currentTime);
        // -----埋点所在页面----
        biData.setPos(page);
        // -----事件名------
        biData.setEvent(event);
        biData.setSequence(event + currentTime);
        // 埋点对应的数据
        biData.setData(mapData);


        //
        Task.call(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                //设置线程优先级为后台
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                // 插入数据
                NetMntDbManager.getInstance().insert(context, biData);
                return null;
            }
        }, Task.BACKGROUND_EXECUTOR);


    }


    /**
     * 一天上传一次；
     * 数据最多保留三天；
     * Wifi环境上传
     *
     * @param context
     */
    public static void checkUpload(Context context) {
        NetMntLogUtils.d(TAG, "---checkUpload---");
        //-----非WIFI返回----
        if (NetUtils.isWifiEthernetConnect(context) == false) {
            NetMntLogUtils.d(TAG, "---isWifiEthernetConnect==false---");
            return;
        }
        //-----上传间隔小于24小时 返回----
        NetMntSPUtil.getInstance().init(context);
        long preUploadTime = NetMntSPUtil.getInstance().getLongValue("uploadTime", 0);
        //
        if ((System.currentTimeMillis() - preUploadTime) < NetMConfig.ONE_DAY_INMillS) {
            NetMntLogUtils.d(TAG, "---preUploadTime==false---");
            return;
        }
        // 上传日志
        upload(context);
    }

    /**
     * 上传日志(直接上传，不判断)
     *
     * @param context
     */
    public static void upload(final Context context) {
        NetMntLogUtils.d(TAG, "---upload---");

        // 存入上传时间
        NetMntSPUtil.getInstance().init(context);
        NetMntSPUtil.getInstance().putLongValue("uploadTime", System.currentTimeMillis());

        //  1、删除三天前日志
        //  2、上传全部日志
        //  3、删除已上传日志
        //
        Task.call(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                //设置线程优先级为后台
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                // 1、删除三天前日志
                Long startTime = System.currentTimeMillis() - NetMConfig.THREE_DAY_INMillS;
                boolean deleteOldFlag = NetMntDbManager.getInstance().deleteOldData(context, startTime);
                NetMntLogUtils.d(TAG, "deleteOldFlag: " + deleteOldFlag);
                // 2、上传全部日志
                List<NetMntLogData> mDataList = NetMntDbManager.getInstance().query(context);
                NetMntLogUtils.d(TAG, "mDataList: " + mDataList);
                if (mUploadListener == null) {
                    NetMntLogUtils.d(TAG, "uploadListener == null");
                }
                if (mUploadListener != null && mDataList != null && mDataList.size() > 0) {
                    NetMntLogUtils.d(TAG, "uploadListener != null");
                    // 调用主工程中的方法进行上传
                    boolean isUpLoaded = mUploadListener.onUpload(mDataList);
                    // 回调结束后置空
                    NetMntLogUtils.d(TAG, "isUpLoaded: " + isUpLoaded);
                    if (isUpLoaded) {
                        // 3、删除已上传日志
                        if (mDataList != null && mDataList.size() > 0) {
                            startTime = mDataList.get(0).getCtime();
                            deleteOldFlag = NetMntDbManager.getInstance().deleteOldData(context, startTime);
                            NetMntLogUtils.d(TAG, "delete upload Data: " + deleteOldFlag);
                        }
                    }
                }
                return null;
            }
        }, Task.BACKGROUND_EXECUTOR);

    }

    //#####################################################end#######################################################################


    /**
     * server_monitor
     *
     * @param context
     * @param app_version app版本
     * @param ip          访问ip地址
     * @param network     wifi、3G等
     * @param operator    运营商
     * @param s_url       // 服务地址
     * @param s_time      // 请求时间
     * @param r_time      //响应时间
     * @param req_size    // 请求大小
     * @param res_size    // 返回数据大小
     */
    public static void server_monitor(final Context context,
                                      // -----设备信息-----
                                      String app_version,
                                      String ip,
                                      String network,
                                      String operator,
                                      // ------埋点数据------
                                      String s_url,     // 服务地址
                                      String s_time,    // 请求时间
                                      String r_time,    // 响应时间
                                      String req_size,  // 请求大小
                                      String res_size) {// 返回数据大小
        // ------埋点数据------
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("s_url", TextUtils.isEmpty(s_url) ? "-" : s_url);
        data.put("s_time", TextUtils.isEmpty(s_time) ? "-" : s_time);
        data.put("r_time", TextUtils.isEmpty(r_time) ? "-" : r_time);
        data.put("req_size", TextUtils.isEmpty(req_size) ? "-" : req_size);
        data.put("res_size", TextUtils.isEmpty(res_size) ? "-" : res_size);
        //
        NetMntCollector.save(context, app_version, ip, network, operator, "tl", "server_monitor", data);
    }


    /**
     * online_monitor
     *
     * @param context
     * @param app_version app版本
     * @param ip          访问ip地址
     * @param network     wifi、3G等
     * @param operator    运营商
     * @param page        页面
     * @param type        0:上线 1:下线
     */
    public static void online_monitor(final Context context,
                                      // -----设备信息-----
                                      String app_version,
                                      String ip,
                                      String network,
                                      String operator,
                                      // ------埋点数据------
                                      String page,      // page数据
                                      String type

    ) {
        // ------埋点数据------
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("type", TextUtils.isEmpty(type) ? "-" : type);
        //
        NetMntCollector.save(context, app_version, ip, network, operator, page, "online_monitor", data);
    }


    /**
     * message_receive
     *
     * @param context
     * @param app_version app版本
     * @param ip          访问ip地址
     * @param network     wifi、3G等
     * @param operator    运营商
     * @param page        页面
     * @param m_id        私信编号
     * @param ms_time     私信发送时间
     * @param s_time      服务器时间
     * @param mr_time     私信接收时间
     * @param ms_type     私信发送类型
     * @param s_version   服务版本号
     * @param mqtt_status mqtt状态
     */
    public static void message_receive(final Context context,
                                       // -----设备信息-----
                                       String app_version,
                                       String ip,
                                       String network,
                                       String operator,
                                       // ------埋点数据------
                                       String page,      // page数据
                                       String m_id,
                                       String ms_time,
                                       String s_time,
                                       String mr_time,
                                       String ms_type,
                                       String s_version,
                                       String mqtt_status

    ) {// 返回数据大小
        // ------埋点数据------
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("m_id", TextUtils.isEmpty(m_id) ? "-" : m_id);
        data.put("ms_time", TextUtils.isEmpty(ms_time) ? "-" : ms_time);
        data.put("s_time", TextUtils.isEmpty(s_time) ? "-" : s_time);
        data.put("mr_time", TextUtils.isEmpty(mr_time) ? "-" : mr_time);
        data.put("ms_type", TextUtils.isEmpty(ms_type) ? "-" : ms_type);
        data.put("s_version", TextUtils.isEmpty(s_version) ? "-" : s_version);
        data.put("mqtt_status", TextUtils.isEmpty(mqtt_status) ? "-" : mqtt_status);
        //
        NetMntCollector.save(context, app_version, ip, network, operator, page, "message_receive", data);
    }


}
