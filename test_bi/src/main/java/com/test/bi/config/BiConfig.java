package com.test.bi.config;

/**
 * Created by xueliangxia.
 */

public class BiConfig {


    // 非wifi情况下，上传时间间隔为一天
    public static final long ONE_DAY_INMillS = 24 * 60 * 60 * 1000;
    // 积累到40条上传
    public static final int UPLOAD_COUNT = 40;
}
