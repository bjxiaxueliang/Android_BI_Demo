package com.example.scalephoto.cache.models;

import java.io.Serializable;

/**
 * create by xiaxl on 2017.03.07.
 * <p>
 * <p>
 * <p>
 */
public class NetMntLogData implements Serializable {
    private static final long serialVersionUID = 1L;


    private Long ctime;//	毫秒级别的时间戳，13位
    private String pos;//string	埋点所在的页面，详细参见
    private String event;    //string	事件名
    private String ip;//string 访问ip地址
    private String network;//string	wifi、3G等
    private String app_version;//string app版本
    private String operator;//string	运营商
    // 必须实现Serializable接口(用于对象序列化)
    private Object data;// data	object	具体事件的参数
    // 注：这个字段在数据库中没有存，sequence由event+ctime共同组成，只要event+ctime有数据sequence就有数据
    private String sequence;// sequence	string	日志的唯一标识：event+ctime

    //-------------------------------------


    //------------------------------------------------get and set----------------------------------------------------
    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    //------------------------------------------------get and set----------------------------------------------------

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("ctime: ");
        sb.append(ctime);
        sb.append(" pos: ");
        sb.append(pos);
        sb.append(" event: ");
        sb.append(event);
        sb.append(" ip: ");
        sb.append(ip);
        sb.append(" network: ");
        sb.append(network);
        sb.append(" app_version: ");
        sb.append(app_version);
        sb.append(" operator: ");
        sb.append(operator);
        sb.append(" sequence: ");
        sb.append(sequence);
        sb.append(" data: ");
        sb.append(data.toString());
        return sb.toString();
    }
}
