package com.solution.brothergroup.Api.Response;

import java.io.Serializable;

public class OperatorListResponse implements Serializable {
    boolean isTakeCustomerNo;
    int statuscode;
    String msg;
    boolean isVersionValid;
    boolean isAppValid;


    //    ArrayList<OperatorList> operators;
    NumberlistData data;

    public NumberlistData getData() {
        return data;
    }

    public void setData(NumberlistData data) {
        this.data = data;
    }

    public boolean isTakeCustomerNo() {
        return isTakeCustomerNo;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isAppValid() {
        return isAppValid;
    }

//    public ArrayList<OperatorList> getOperators() {
//        return operators;
//    }
//}
}