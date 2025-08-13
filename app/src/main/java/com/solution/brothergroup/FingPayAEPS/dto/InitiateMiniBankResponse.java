package com.solution.brothergroup.FingPayAEPS.dto;

public class InitiateMiniBankResponse {
    private int statuscode;
    private String msg, tid;
    private boolean isVersionValid;

    public int getStatuscode() {
        return statuscode;
    }

    public MiniBankData data;

    public String getMsg() {
        return msg;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public String getTid() {
        return tid;
    }

    public MiniBankData getData() {
        return data;
    }
}
