package com.solution.brothergroup.DMRNEW.dto;

import java.util.ArrayList;

/**
 * Created by Administrator on 29-12-2017.
 */

public class TABLE {

    private String RESPONSESTATUS;
    private String MESSAGE;
    private String Remaining;
    private String OTP_RefId;
    private String isOTP;
    //////////////////////////////////
    private String KYC;
    private String NAME;
    private String CURRENCY;
    private String REMAINING;
    private String USED;
    //////////////////////////////////

    private Responsedeta Response;

    //////////////////////////////////
    private ArrayList<com.solution.brothergroup.DMRNEW.dto.LIMIT> LIMIT;
    private ArrayList<com.solution.brothergroup.DMRNEW.dto.BENEFICIARY> BENEFICIARY;
    private ArrayList<com.solution.brothergroup.DMRNEW.dto.BENEFICIARY> Account_Detail;
    //////////////////////////////////
    private String ApiBAL;
    //////////////////////////////////
    private String RECIPIENTID;
    private String RECIPIENTNAME;


    private String Recipient;

    public String getRemaining() {
        return Remaining;
    }

    public void setRemaining(String remaining) {
        Remaining = remaining;
    }

    public String getOTP_RefId() {
        return OTP_RefId;
    }

    public void setOTP_RefId(String OTP_RefId) {
        this.OTP_RefId = OTP_RefId;
    }

    public String getIsOTP() {
        return isOTP;
    }

    public void setIsOTP(String isOTP) {
        this.isOTP = isOTP;
    }

    public Responsedeta getResponse() {
        return Response;
    }

    public void setResponse(Responsedeta response) {
        Response = response;
    }

    public ArrayList<com.solution.brothergroup.DMRNEW.dto.BENEFICIARY> getAccount_Detail() {
        return Account_Detail;
    }

    public void setAccount_Detail(ArrayList<com.solution.brothergroup.DMRNEW.dto.BENEFICIARY> account_Detail) {
        Account_Detail = account_Detail;
    }

    /////////////////////////////////
    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public ArrayList<com.solution.brothergroup.DMRNEW.dto.BENEFICIARY> getBENEFICIARY() {
        return BENEFICIARY;
    }

    public void setBENEFICIARY(ArrayList<com.solution.brothergroup.DMRNEW.dto.BENEFICIARY> BENEFICIARY) {
        this.BENEFICIARY = BENEFICIARY;
    }

    public String getRESPONSESTATUS() {
        return RESPONSESTATUS;
    }

    public void setRESPONSESTATUS(String RESPONSESTATUS) {
        this.RESPONSESTATUS = RESPONSESTATUS;
    }


    public String getKYC() {
        return KYC;
    }

    public void setKYC(String KYC) {
        this.KYC = KYC;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getCURRENCY() {
        return CURRENCY;
    }

    public void setCURRENCY(String CURRENCY) {
        this.CURRENCY = CURRENCY;
    }

    public String getREMAINING() {
        return REMAINING;
    }

    public void setREMAINING(String REMAINING) {
        this.REMAINING = REMAINING;
    }

    public String getUSED() {
        return USED;
    }

    public void setUSED(String USED) {
        this.USED = USED;
    }

    public ArrayList<com.solution.brothergroup.DMRNEW.dto.LIMIT> getLIMIT() {
        return LIMIT;
    }

    public void setLIMIT(ArrayList<com.solution.brothergroup.DMRNEW.dto.LIMIT> LIMIT) {
        this.LIMIT = LIMIT;
    }

    public String getApiBAL() {
        return ApiBAL;
    }

    public void setApiBAL(String apiBAL) {
        ApiBAL = apiBAL;
    }

    public String getRECIPIENTID() {
        return RECIPIENTID;
    }

    public void setRECIPIENTID(String RECIPIENTID) {
        this.RECIPIENTID = RECIPIENTID;
    }

    public String getRECIPIENTNAME() {
        return RECIPIENTNAME;
    }

    public void setRECIPIENTNAME(String RECIPIENTNAME) {
        this.RECIPIENTNAME = RECIPIENTNAME;
    }

    public String getRecipient() {
        return Recipient;
    }

    public void setRecipient(String recipient) {
        Recipient = recipient;
    }
}
