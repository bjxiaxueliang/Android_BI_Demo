package com.test.bi.cache.models;

import java.io.Serializable;

/**
 * session
 */
public class BiSessionData implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long timeId;
    public String sessionId;
    public String uid;
    public String token;
    public String cid;
    public String mac;
    public String ip;
    public String lon;
    public String lat;
    public String network;
    public String operator;
    public String os;
    public String brand;
    public String model;
    public String hw;
    public String gid;
    public String idfa;
    public String imei;
    public String imsi;
    public String appVersion;
    public String cname;
    public String cversion;


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("timeId: ");
        sb.append(timeId);
        sb.append(" sessionId: ");
        sb.append(sessionId);
        sb.append(" uid: ");
        sb.append(uid);
        sb.append(" token: ");
        sb.append(token);
        sb.append(" mac: ");
        sb.append(mac);
        sb.append(" ip: ");
        sb.append(ip);
        sb.append(" lon: ");
        sb.append(lon);
        sb.append(" lat: ");
        sb.append(lat);
        sb.append(" network: ");
        sb.append(network);
        sb.append(" operator: ");
        sb.append(operator);
        sb.append(" os: ");
        sb.append(os);
        sb.append(" brand: ");
        sb.append(brand);
        sb.append(" model: ");
        sb.append(model);
        sb.append(" idfa: ");
        sb.append(idfa);
        sb.append(" imei: ");
        sb.append(imei);
        sb.append(" imsi: ");
        sb.append(imsi);
        sb.append(" idfa: ");
        sb.append(idfa);
        sb.append(" appVersion: ");
        sb.append(appVersion);
        sb.append(" cname: ");
        sb.append(cname);
        sb.append(" cversion: ");
        sb.append(cversion);
        //
        return sb.toString();
    }
}
