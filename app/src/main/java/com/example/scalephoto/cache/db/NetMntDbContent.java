package com.example.scalephoto.cache.db;

/**
 * create by xiaxl on 2017.03.07.
 */
public class NetMntDbContent {

    /**
     * 数据库名称
     */
    public static final String DATABASE_NAME = "snsnetmnt.db";

    /**
     * 数据库版本
     */
    public static final int DATABASE_VERSION = 1;

    /**
     * @author
     */
    public static class SnsBiTable {
        /**
         * 表名
         */
        public static final String TABLE_NAME = "sns_netmnt_table";
        //Integer	毫秒级别的时间戳，13位
        public static final String CTIME = "ctime";
        //string	埋点所在的页面，详细参见
        public static final String PAGE = "pos";
        //string	事件名
        public static final String EVENT = "event";
        //string 访问ip地址
        public static final String IP = "ip";
        //string	wifi、3G等
        public static final String NETWORK_TYPE = "network";
        //string app版本
        public static final String APP_VERSION = "app_version";
        //string	运营商
        public static final String OPERATOR = "operator";
        // sequence	string	日志的唯一标识：event+ctime
        public static final String SEQUENCE = "sequence";
        // data	object	具体事件的参数
        public static final String BI_DATA = "data";

    }
}
