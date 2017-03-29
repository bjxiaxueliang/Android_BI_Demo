package com.test.bi.cache.db;

/**
 * create by xiaxl.
 */
public class BiDbContent {

    /**
     * 数据库名称
     */
    public static final String DATABASE_NAME = "testbi.db";

    /**
     * 数据库版本
     */
    public static final int DATABASE_VERSION = 1;

    /**
     * session 表
     *
     * @author xiaxl
     */
    public static class BiSessionTable {
        /**
         * 表名
         */
        public static final String TABLE_NAME = "test_session_table";
        //---------
        // session开始时间
        public static final String TIMEID = "timeId";
        // session编号
        public static final String SESSIONID = "sessionId";
        // userid
        public static final String UID = "uid";
        // 	签名
        public static final String TOKEN = "token";
        // 客户端的cid设备id
        public static final String CID = "cid";
        // 设备的mac地址
        public static final String MAC = "mac";
        // 	设备的ip地址
        public static final String IP = "ip";
        // 经度
        public static final String LON = "lon";
        // 维度
        public static final String LAT = "lat";
        // 	网络类型
        public static final String NETWORK = "network";
        // 运营商
        public static final String OPERATOR = "operator";
        // 操作系统
        public static final String OS = "os";
        // 	品牌
        public static final String BRAND = "brand";
        // 	设备型号
        public static final String MODEL = "model";
        // 宽高
        public static final String HW = "hw";
        // Gid
        public static final String GID = "gid";
        // 	苹果的唯一标示
        public static final String IDFA = "idfa";
        // 设备编号
        public static final String IMEI = "imei";
        // 	通讯卡编号
        public static final String IMSI = "imsi";
        // app版本号
        public static final String APP_VERSION = "appVersion";
        // 渠道名称
        public static final String CNAME = "cname";
        // 渠道版本号
        public static final String CVERSION = "cversion";
    }


    /**
     * BI 表
     *
     * @author xiaxl
     */
    public static class BiTable {
        /**
         * 表名
         */
        public static final String TABLE_NAME = "test_bi_table";
        //---------
        // 上传时间戳
        public static final String TIMEID = "timeId";
        // 用户session编号
        public static final String SESSIONID = "sessionId";
        // 用户标识
        // {"uid":"","pid":""}
        public static final String UID = "uid";
        // 	用户行为标识
        public static final String KEY = "key";
        // 行为参数
        // {"fid":"XXXXXXXX","uid":"xxxx"}
        public static final String PARAMETERS = "parameters";
        // session内操作顺序
        public static final String SEQUENCE = "sequence";
    }


}
