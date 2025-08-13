package com.solution.brothergroup.Api.Object;

import com.solution.brothergroup.AddMoney.modelClass.RequestPTM;

/**
 * Created by Vishnu Agarwal on 22,January,2020
 */
public class PGModelForApp {
    int statuscode;
    String msg;
    int pgid;
    String tid;
    String transactionID;
    RequestPTM requestPTM;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public int getPgid() {
        return pgid;
    }

    public String getTid() {
        return tid;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public RequestPTM getRequestPTM() {
        return requestPTM;
    }
}
