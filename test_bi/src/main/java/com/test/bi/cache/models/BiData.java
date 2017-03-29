package com.test.bi.cache.models;

import java.io.Serializable;

/**
 * BI
 */
public class BiData implements Serializable {
    private static final long serialVersionUID = 1L;

    // 上传时间戳
    public Long timeId;
    // 用户session编号
    public String sessionId;
    // 用户标识(可以是一个HashMap)
    public Object uid;
    // 用户行为标识
    public String key;
    // 行为参数(可以是一个HashMap)
    public Object parameters;
    // session内操作顺序
    public String sequence;


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("timeId: ");
        sb.append(timeId);
        sb.append(" sessionId: ");
        sb.append(sessionId);
        sb.append(" uid: ");
        sb.append(uid);
        sb.append(" key: ");
        sb.append(key);
        sb.append(" parameters: ");
        sb.append(parameters);
        sb.append(" sequence: ");
        sb.append(sequence);
        return sb.toString();
    }
}
