package com.solution.brothergroup.PSA.UI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PanDetail {

    @SerializedName("psaId")
    @Expose
    private String psaId;
    @SerializedName("psaRequestId")
    @Expose
    private String psaRequestId;
    @SerializedName("outletId")
    @Expose
    private String outletId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("emailId")
    @Expose
    private String emailId;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("listPanCharge")
    @Expose
    private List<ListPanCharge> listPanCharge = null;

    public List<ListPanCharge> getListPanCharge() {
        return listPanCharge;
    }

    public void setListPanCharge(List<ListPanCharge> listPanCharge) {
        this.listPanCharge = listPanCharge;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPsaId() {
        return psaId;
    }

    public void setPsaId(String psaId) {
        this.psaId = psaId;
    }

    public String getPsaRequestId() {
        return psaRequestId;
    }

    public void setPsaRequestId(String psaRequestId) {
        this.psaRequestId = psaRequestId;
    }


    public String getOutletId() {
        return outletId;
    }

    public void setOutletId(String outletId) {
        this.outletId = outletId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
