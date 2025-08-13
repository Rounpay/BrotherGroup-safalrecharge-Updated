package com.solution.brothergroup.NSDL;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NSDLData {

    @SerializedName("apiStatus")
    @Expose
    private String apiStatus;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("isRedirectToKYC")
    @Expose
    private boolean isRedirectToKYC;
    @SerializedName("redirectURL")
    @Expose
    private String redirectURL;
    @SerializedName("errorCode")
    @Expose
    private int errorCode;
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("tid")
    @Expose
    private Integer tid;
    @SerializedName("transactionID")
    @Expose
    private String transactionID;
    @SerializedName("apiRequestID")
    @Expose
    private String apiRequestID;
    @SerializedName("statuscode")
    @Expose
    private int statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;


    public String getApiStatus() {
        return apiStatus;
    }

    public String getToken() {
        return token;
    }

    public boolean isRedirectToKYC() {
        return isRedirectToKYC;
    }

    public String getRedirectURL() {
        return redirectURL;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public Integer getTid() {
        return tid;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public String getApiRequestID() {
        return apiRequestID;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }
}
