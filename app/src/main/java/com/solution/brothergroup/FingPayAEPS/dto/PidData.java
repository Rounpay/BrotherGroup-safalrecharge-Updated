package com.solution.brothergroup.FingPayAEPS.dto;

public class PidData {

    public Resp resp;

    public DeviceInfo deviceInfo;

    public Skey skey;

    public String  hmac;

    public Data data;

    public Resp getResp() {
        return resp;
    }

    public void setResp(Resp resp) {
        this.resp = resp;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public Skey getSkey() {
        return skey;
    }

    public void setSkey(Skey skey) {
        this.skey = skey;
    }

    public String getHmac() {
        return hmac;
    }

    public void setHmac(String hmac) {
        this.hmac = hmac;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
