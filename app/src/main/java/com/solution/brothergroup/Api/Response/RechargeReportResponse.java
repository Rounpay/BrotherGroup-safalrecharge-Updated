package com.solution.brothergroup.Api.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.brothergroup.Api.Object.LedgerObject;
import com.solution.brothergroup.Api.Object.MoveToWalletReportData;
import com.solution.brothergroup.Api.Object.RechargeStatus;

import java.util.ArrayList;

public class RechargeReportResponse {

    private String optional2;

    public String getOptional2() {
        return optional2;
    }

    public void setOptional2(String optional2) {
        this.optional2 = optional2;
    }

    private String statuscode;
    private String msg;
    private String isVersionValid;
    private String isAppValid;
    private String groupID;
    private String TXN;
    private String o15;
    private boolean isOTPRequired;
    private boolean isResendAvailable;
    private ArrayList<MoveToWalletReportData> moveToWalletReport;
    private String beneName;
    private String chargedAmount;
    private ArrayList<SlabCommtObject> slabCommissions;
    private ArrayList<DmtReportObject> dmtReport;
    private ArrayList<RechargeStatus> rechargeReport;
    private ArrayList<RechargeStatus> recentRecharge;
    private ArrayList<RechargeStatus> aePsDetail;
//beneficiaries
    @SerializedName(value = "benis",alternate = "beneficiaries")
    @Expose
    private ArrayList<BenisObject> benis;
    private ArrayList<LedgerObject> ledgerReport;
    private ArrayList<FundDCObject> fundDCReport;
    private ArrayList<FundOrderReportObject> fundOrderReport;
    @SerializedName(value = "sid",alternate = "SID")
    @Expose
    private String  SID;
    private int orderID;
    public String getIsVersionValid() {
        return isVersionValid;
    }

    public String getIsAppValid() {
        return isAppValid;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getSID() {
        return SID;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    public void setGroupID(String groupID) {
        groupID = groupID;
    }

    public String getTXN() {
        return TXN;
    }

    public void setTXN(String TXN) {
        this.TXN = TXN;
    }

    public String getBeneName() {
        return beneName;
    }

    public void setBeneName(String beneName) {
        this.beneName = beneName;
    }

    public String getO15() {
        return o15;
    }

    public void setO15(String o15) {
        this.o15 = o15;
    }

    public String getChargedAmount() {
        return chargedAmount;
    }

    public void setChargedAmount(String chargedAmount) {
        this.chargedAmount = chargedAmount;
    }

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public ArrayList<RechargeStatus> getAePsDetail() {
        return aePsDetail;
    }

    public String getMsg() {
        return msg;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<RechargeStatus> getRechargeReport() {
        return rechargeReport;
    }

    public void setRechargeReport(ArrayList<RechargeStatus> rechargeReport) {
        this.rechargeReport = rechargeReport;
    }

    public ArrayList<BenisObject> getBenis() {
        return benis;
    }

    public void setBenis(ArrayList<BenisObject> benis) {
        this.benis = benis;
    }

    public ArrayList<LedgerObject> getLedgerReport() {
        return ledgerReport;
    }

    public void setLedgerReport(ArrayList<LedgerObject> ledgerReport) {
        this.ledgerReport = ledgerReport;
    }


    public ArrayList<FundDCObject> getFundDCReport() {
        return fundDCReport;
    }

    public void setFundDCReport(ArrayList<FundDCObject> fundDCReport) {
        this.fundDCReport = fundDCReport;
    }

    public ArrayList<RechargeStatus> getRecentRecharge() {
        return recentRecharge;
    }

    public ArrayList<FundOrderReportObject> getFundOrderReport() {
        return fundOrderReport;
    }

    public void setFundOrderReport(ArrayList<FundOrderReportObject> fundOrderReport) {
        this.fundOrderReport = fundOrderReport;
    }

    public boolean isOTPRequired() {
        return isOTPRequired;
    }

    public boolean isResendAvailable() {
        return isResendAvailable;
    }

    public ArrayList<SlabCommtObject> getSlabCommissions() {
        return slabCommissions;
    }

    public void setSlabCommissions(ArrayList<SlabCommtObject> slabCommissions) {
        this.slabCommissions = slabCommissions;
    }

    public ArrayList<MoveToWalletReportData> getMoveToWalletReport() {
        return moveToWalletReport;
    }

    public ArrayList<DmtReportObject> getDmtReport() {
        return dmtReport;
    }

    public void setDmtReport(ArrayList<DmtReportObject> dmtReport) {
        this.dmtReport = dmtReport;
    }


}
