package com.solution.brothergroup.CMS.Api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CmsApiResponse {

    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("isVersionValid")
    @Expose
    public boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    public boolean isAppValid;
    @SerializedName("receiptDetail")
    @Expose
    public ReceiptDetail receiptDetail;

    public ReceiptDetail getReceiptDetail() {
        return receiptDetail;
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
}
