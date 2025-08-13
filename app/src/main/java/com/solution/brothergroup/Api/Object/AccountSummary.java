package com.solution.brothergroup.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountSummary {
    @SerializedName("statusCode")
    @Expose
    private String statusCode;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("opening")
    @Expose
    private Double opening;
    @SerializedName("purchase")
    @Expose
    private Double purchase;
    @SerializedName("fundDeducted")
    @Expose
    private Double fundDeducted;
    @SerializedName("return")
    @Expose
    private Double _return;
    @SerializedName("requested")
    @Expose
    private Double requested;
    @SerializedName("debited")
    @Expose
    private Double debited;
    @SerializedName("refunded")
    @Expose
    private Double refunded;
    @SerializedName("commission")
    @Expose
    private Double commission;
    @SerializedName("surcharge")
    @Expose
    private Double surcharge;
    @SerializedName("fundTransfered")
    @Expose
    private Double fundTransfered;
    @SerializedName("otherCharge")
    @Expose
    private Double otherCharge;
    @SerializedName("ccfCommDebited")
    @Expose
    private Double ccfCommDebited;
    @SerializedName("expected")
    @Expose
    private Double expected;
    @SerializedName("todaySuccess")
    @Expose
    private Double todaySuccess;
    @SerializedName("todayFailed")
    @Expose
    private Double todayFailed;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getOpening() {
        return opening;
    }

    public void setOpening(Double opening) {
        this.opening = opening;
    }

    public Double getPurchase() {
        return purchase;
    }

    public void setPurchase(Double purchase) {
        this.purchase = purchase;
    }

    public Double getFundDeducted() {
        return fundDeducted;
    }

    public void setFundDeducted(Double fundDeducted) {
        this.fundDeducted = fundDeducted;
    }

    public Double get_return() {
        return _return;
    }

    public void set_return(Double _return) {
        this._return = _return;
    }

    public Double getRequested() {
        return requested;
    }

    public void setRequested(Double requested) {
        this.requested = requested;
    }

    public Double getDebited() {
        return debited;
    }

    public void setDebited(Double debited) {
        this.debited = debited;
    }

    public Double getRefunded() {
        return refunded;
    }

    public void setRefunded(Double refunded) {
        this.refunded = refunded;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Double getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(Double surcharge) {
        this.surcharge = surcharge;
    }

    public Double getFundTransfered() {
        return fundTransfered;
    }

    public void setFundTransfered(Double fundTransfered) {
        this.fundTransfered = fundTransfered;
    }

    public Double getOtherCharge() {
        return otherCharge;
    }

    public void setOtherCharge(Double otherCharge) {
        this.otherCharge = otherCharge;
    }

    public Double getCcfCommDebited() {
        return ccfCommDebited;
    }

    public void setCcfCommDebited(Double ccfCommDebited) {
        this.ccfCommDebited = ccfCommDebited;
    }

    public Double getExpected() {
        return expected;
    }

    public void setExpected(Double expected) {
        this.expected = expected;
    }

    public Double getTodaySuccess() {
        return todaySuccess;
    }

    public void setTodaySuccess(Double todaySuccess) {
        this.todaySuccess = todaySuccess;
    }

    public Double getTodayFailed() {
        return todayFailed;
    }

    public void setTodayFailed(Double todayFailed) {
        this.todayFailed = todayFailed;
    }
}
