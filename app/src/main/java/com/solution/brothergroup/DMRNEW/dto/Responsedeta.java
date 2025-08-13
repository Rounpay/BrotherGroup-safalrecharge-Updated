package com.solution.brothergroup.DMRNEW.dto;

public class Responsedeta {

    private String status;
    private String ErrorCode;
    private String msg;
    private String SessionKey;
    private String OutletId;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSessionKey() {
        return SessionKey;
    }

    public void setSessionKey(String sessionKey) {
        SessionKey = sessionKey;
    }

    public String getOutletId() {
        return OutletId;
    }

    public void setOutletId(String outletId) {
        OutletId = outletId;
    }
}
