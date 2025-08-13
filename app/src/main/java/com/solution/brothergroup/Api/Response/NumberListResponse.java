package com.solution.brothergroup.Api.Response;

/**
 * Created by Vishnu on 15-02-2017.
 */

public class NumberListResponse {

    private String statuscode;
    private String msg;
    private String isVersionValid;
    private String isAppValid;
    private NumberlistData data;
boolean isTakeCustomerNo;


    public String getIsVersionValid() {
        return isVersionValid;
    }

    public String getIsAppValid() {
        return isAppValid;
    }

    public NumberlistData getData() {
        return data;
    }

    public void setData(NumberlistData data) {
        this.data = data;
    }

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isTakeCustomerNo() {
        return isTakeCustomerNo;
    }
}
