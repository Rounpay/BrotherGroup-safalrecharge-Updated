package com.solution.brothergroup.Api.Response;

import java.util.ArrayList;

/**
 * Created by Vishnu on 16-04-2017.
 */

public class BalanceCheckResponse {

    private String RESPONSESTATUS;
    private String message;
    private String PrepaidWallet;
    private String UtilityWallet;
    private String IsLogin;
    private String version;
    private String AepsBalance;
    private String Userstatus;
    private String dmrPermission;
    private String rechargePermission;
    private String Aepssts;
    private String psasts;
    private String Apiuserid;
    private String ApiPassword;
    private String AepsApiuserid;
    private String AepsPassword;
    private ArrayList<ChildBalance> ChildBalance;

    public String getAepsBalance() {
        return AepsBalance;
    }

    public void setAepsBalance(String aepsBalance) {
        AepsBalance = aepsBalance;
    }

    public String getAepssts() {
        return Aepssts;
    }

    public void setAepssts(String aepssts) {
        Aepssts = aepssts;
    }

    public String getPsasts() {
        return psasts;
    }

    public void setPsasts(String psasts) {
        this.psasts = psasts;
    }

    public String getApiuserid() {
        return Apiuserid;
    }

    public void setApiuserid(String apiuserid) {
        Apiuserid = apiuserid;
    }

    public String getApiPassword() {
        return ApiPassword;
    }

    public void setApiPassword(String apiPassword) {
        ApiPassword = apiPassword;
    }

    public String getAepsApiuserid() {
        return AepsApiuserid;
    }

    public void setAepsApiuserid(String aepsApiuserid) {
        AepsApiuserid = aepsApiuserid;
    }

    public String getAepsPassword() {
        return AepsPassword;
    }

    public void setAepsPassword(String aepsPassword) {
        AepsPassword = aepsPassword;
    }

    public String getUserstatus() {
        return Userstatus;
    }

    public void setUserstatus(String userstatus) {
        Userstatus = userstatus;
    }

    public String getDmrPermission() {
        return dmrPermission;
    }

    public void setDmrPermission(String dmrPermission) {
        this.dmrPermission = dmrPermission;
    }

    public String getRechargePermission() {
        return rechargePermission;
    }

    public void setRechargePermission(String rechargePermission) {
        this.rechargePermission = rechargePermission;
    }

    public ArrayList<ChildBalance> getChildBalance() {
        return ChildBalance;
    }

    public void setChildBalance(ArrayList<ChildBalance> childBalance) {
        ChildBalance = childBalance;
    }

    public String getRESPONSESTATUS() {
        return RESPONSESTATUS;
    }

    public void setRESPONSESTATUS(String RESPONSESTATUS) {
        this.RESPONSESTATUS = RESPONSESTATUS;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPrepaidWallet() {
        return PrepaidWallet;
    }

    public void setPrepaidWallet(String prepaidWallet) {
        PrepaidWallet = prepaidWallet;
    }

    public String getUtilityWallet() {
        return UtilityWallet;
    }

    public void setUtilityWallet(String utilityWallet) {
        UtilityWallet = utilityWallet;
    }

    public String getIsLogin() {
        return IsLogin;
    }

    public void setIsLogin(String isLogin) {
        IsLogin = isLogin;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
