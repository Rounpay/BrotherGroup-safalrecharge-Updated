package com.solution.brothergroup.Api.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.brothergroup.Api.Object.OperatorOptional;

import java.util.List;

/**
 * Created by Administrator on 15-03-2018.
 */

public class Data {
    int wid;
    @SerializedName("operatorOptionals")
    @Expose
    public List<OperatorOptional> operatorOptionals = null;
    /*@SerializedName("balance")
    @Expose
    public Double balance;*/
    @SerializedName("isBalance")
    @Expose
    public Boolean isBalance;
    /*@SerializedName("uBalance")
    @Expose
    public Double uBalance;*/
    @SerializedName("isUBalance")
    @Expose
    public Boolean isUBalance;
    /*@SerializedName("bBalance")
    @Expose
    public Double bBalance;*/
    @SerializedName("isBBalance")
    @Expose
    public Boolean isBBalance;
    /*@SerializedName("cBalance")
    @Expose
    public Double cBalance;*/
    @SerializedName("isCBalance")
    @Expose
    public Boolean isCBalance;
    @SerializedName("idBalnace")
    @Expose
    public Double idBalnace;
    @SerializedName("isIDBalance")
    @Expose
    public Boolean isIDBalance;
    @SerializedName("aepsBalnace")
    @Expose
    public Double aepsBalnace;
    @SerializedName("isAEPSBalance")
    @Expose
    public Boolean isAEPSBalance;
    @SerializedName("packageBalnace")
    @Expose
    public Double packageBalnace;
    @SerializedName("isPacakgeBalance")
    @Expose
    public Boolean isPacakgeBalance;
    @SerializedName("isP")
    @Expose
    public Boolean isP;
    @SerializedName("isPN")
    @Expose
    public Boolean isPN;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("emailID")
    @Expose
    private String emailID;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("loginTypeID")
    @Expose
    private String loginTypeID;

    /*Changed on 01-10-19*/

    /*@SerializedName("isDTHInfo")
    @Expose
    private boolean isDTHInfo;
    @SerializedName("isRoffer")
    @Expose
    private boolean isRoffer;
    @SerializedName("isHeavyRefresh")
    @Expose
    private boolean isHeavyRefresh;*/
    @SerializedName(value = "canDebit",alternate = "isDebitAllowed")
    @Expose
    private boolean canDebit;
    @SerializedName("lt")
    @Expose
    private String lt;
    @SerializedName("sessionID")
    @Expose
    private String sessionID;
    @SerializedName("roleName")
    @Expose
    private String roleName;
    @SerializedName("roleID")
    @Expose
    private String roleID;
    @SerializedName("slabID")
    @Expose
    private String slabID;
    @SerializedName("session")
    @Expose
    private String session;

    /*public boolean getIsDTHInfo() {
        return isDTHInfo;
    }

    public void setIsDTHInfo(boolean isDTHInfo) {
        this.isDTHInfo = isDTHInfo;
    }

    public boolean getIsRoffer() {
        return isRoffer;
    }

    public void setIsRoffer(boolean isRoffer) {
        this.isRoffer = isRoffer;
    }

    public boolean getIsHeavyRefresh() {
        return isHeavyRefresh;
    }

    public void setIsHeavyRefresh(boolean isHeavyRefresh) {
        this.isHeavyRefresh = isHeavyRefresh;
    }*/
    @SerializedName("outletID")
    @Expose
    private String outletID;
    @SerializedName("outletName")
    @Expose
    private String outletName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("isDoubleFactor")
    @Expose
    private boolean isDoubleFactor;
    @SerializedName("isPinRequired")
    @Expose
    private boolean isPinRequired;
    private String balance;
    private String uBalance;
    private String bBalance;
    private String cBalance;
    private String idbalance;

    public String getOutletName() {
        return outletName;
    }

    public String getAddress() {
        return address;
    }

    public String getLt() {
        return lt;
    }

    public boolean getIsDoubleFactor() {
        return isDoubleFactor;
    }

    public void setIsDoubleFactor(boolean isDoubleFactor) {
        this.isDoubleFactor = isDoubleFactor;
    }

    public boolean getIsPinRequired() {
        return isPinRequired;
    }

    public void setIsPinRequired(boolean isPinRequired) {
        this.isPinRequired = isPinRequired;
    }

    public String getSession() {
        if (session != null && !session.isEmpty()) {
            return session;
        } else {
            return "";
        }
    }

    public int getWid() {
        return wid;
    }

    public boolean isCanDebit() {
        return canDebit;
    }

    public String getOutletID() {
        return outletID;
    }

    public void setOutletID(String outletID) {
        this.outletID = outletID;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLoginTypeID() {
        return loginTypeID;
    }

    public void setLoginTypeID(String loginTypeID) {
        this.loginTypeID = loginTypeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getSlabID() {
        return slabID;
    }


    /*getBalance*/

    public void setSlabID(String slabID) {
        this.slabID = slabID;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setBalance(Boolean balance) {
        isBalance = balance;
    }

    public String getuBalance() {
        return uBalance;
    }

    public void setuBalance(String uBalance) {
        this.uBalance = uBalance;
    }

    public String getbBalance() {
        return bBalance;
    }

    public void setbBalance(String bBalance) {
        this.bBalance = bBalance;
    }

    public String getcBalance() {
        return cBalance;
    }

    public void setcBalance(String cBalance) {
        this.cBalance = cBalance;
    }

    public String getIdbalance() {
        return idbalance;
    }

    public void setIdbalance(String idbalance) {
        this.idbalance = idbalance;
    }

    /*public Double getBalance() {
        return balance;
    }*/

    public List<OperatorOptional> getOperatorOptionals() {
        return operatorOptionals;
    }

    public Boolean getIsbalance() {
        return isBalance;
    }

    /*public Double getuBalance() {
        return uBalance;
    }*/

    /*public void setuBalance(Double uBalance) {
        this.uBalance = uBalance;
    }*/

    public Boolean getUBalance() {
        return isUBalance;
    }

    public void setUBalance(Boolean UBalance) {
        isUBalance = UBalance;
    }

    /*public Double getbBalance() {
        return bBalance;
    }

    public void setbBalance(Double bBalance) {
        this.bBalance = bBalance;
    }*/

    public Boolean getBBalance() {
        return isBBalance;
    }

    public void setBBalance(Boolean BBalance) {
        isBBalance = BBalance;
    }

    /* public Double getcBalance() {
         return cBalance;
     }

     public void setcBalance(Double cBalance) {
         this.cBalance = cBalance;
     }
 */
    public Boolean getCBalance() {
        return isCBalance;
    }

    public void setCBalance(Boolean CBalance) {
        isCBalance = CBalance;
    }

    public Double getIdBalnace() {
        return idBalnace;
    }

    public void setIdBalnace(Double idBalnace) {
        this.idBalnace = idBalnace;
    }

    public Boolean getIDBalance() {
        return isIDBalance;
    }

    public void setIDBalance(Boolean IDBalance) {
        isIDBalance = IDBalance;
    }

    public Double getAepsBalnace() {
        return aepsBalnace;
    }

    public void setAepsBalnace(Double aepsBalnace) {
        this.aepsBalnace = aepsBalnace;
    }

    public Boolean getAEPSBalance() {
        return isAEPSBalance;
    }

    public void setAEPSBalance(Boolean AEPSBalance) {
        isAEPSBalance = AEPSBalance;
    }

    public Double getPackageBalnace() {
        return packageBalnace;
    }

    public void setPackageBalnace(Double packageBalnace) {
        this.packageBalnace = packageBalnace;
    }

    public Boolean getPacakgeBalance() {
        return isPacakgeBalance;
    }

    public void setPacakgeBalance(Boolean pacakgeBalance) {
        isPacakgeBalance = pacakgeBalance;
    }

    public Boolean getP() {
        return isP;
    }

    public void setP(Boolean p) {
        isP = p;
    }

    public Boolean getPN() {
        return isPN;
    }

    public void setPN(Boolean PN) {
        isPN = PN;
    }

    /*public void setBalance(Double balance) {
        this.balance = balance;
    }*/

}
