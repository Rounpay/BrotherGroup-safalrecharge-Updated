package com.solution.brothergroup.AddMoney.modelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.brothergroup.AddMoney.CashFreeData;
import com.solution.brothergroup.Api.Object.AggrePayRequest;
import com.solution.brothergroup.Api.Object.UPIGatewayRequest;

public class PGModelForApp {

    @SerializedName("statuscode")
    @Expose
    private int statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("pgid")
    @Expose
    private int pgid;
    @SerializedName("tid")
    @Expose
    private int tid;
    @SerializedName("transactionID")
    @Expose
    private String transactionID;
    @SerializedName("requestPTM")
    @Expose
    private RequestPTM requestPTM;
    @SerializedName("rPayRequest")
    @Expose
    private RPayRequest rPayRequest;
    @SerializedName("aggrePayRequest")
    @Expose
    private AggrePayRequest aggrePayRequest;

    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("cashFreeResponse")
    @Expose
    CashFreeData cashFreeResponse;

    public CashFreeData getCashFreeResponse() {
        return cashFreeResponse;
    }

    public void setCashFreeResponse(CashFreeData cashFreeResponse) {
        this.cashFreeResponse = cashFreeResponse;
    }

    @SerializedName("upiGatewayRequest")
    @Expose
    UPIGatewayRequest upiGatewayRequest ;
    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getPgid() {
        return pgid;
    }

    public void setPgid(int pgid) {
        this.pgid = pgid;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public RequestPTM getRequestPTM() {
        return requestPTM;
    }

    public void setRequestPTM(RequestPTM requestPTM) {
        this.requestPTM = requestPTM;
    }

    public AggrePayRequest getAggrePayRequest() {
        return aggrePayRequest;
    }

    public String getToken() {
        return token;
    }

    public UPIGatewayRequest getUpiGatewayRequest() {
        return upiGatewayRequest;
    }

    public RPayRequest getrPayRequest() {
        return rPayRequest;
    }

    public void setrPayRequest(RPayRequest rPayRequest) {
        this.rPayRequest = rPayRequest;
    }
}
