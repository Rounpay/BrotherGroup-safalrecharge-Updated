package com.solution.brothergroup.Fragments.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.brothergroup.Api.Object.Role;

import java.io.Serializable;
import java.util.List;

public class UserDetailInfo implements Serializable {
    @SerializedName("commRate")
    @Expose
    public Double commRate;
    @SerializedName("stateID")
    @Expose
    public Integer stateID;
    @SerializedName("profilePic")
    @Expose
    public String profilePic;
    @SerializedName("kycStatus")
    @Expose
    public Integer kycStatus;
    @SerializedName("aadhar")
    @Expose
    public String aadhar;
    @SerializedName("pan")
    @Expose
    public String pan;
    @SerializedName("gstin")
    @Expose
    public String gstin;

    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("loginID")
    @Expose
    public Integer loginID;
    @SerializedName("lt")
    @Expose
    public Integer lt;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("stateName")
    @Expose
    public String stateName;
    @SerializedName("roles")
    @Expose
    public List<Role> roles = null;
    @SerializedName("slabs")
    @Expose
    public String slabs;
    @SerializedName("states")
    @Expose
    public String states;
    @SerializedName("ip")
    @Expose
    public String ip;
    @SerializedName("browser")
    @Expose
    public String browser;
    @SerializedName("resultCode")
    @Expose
    public Integer resultCode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("userID")
    @Expose
    public Integer userID;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("outletName")
    @Expose
    public String outletName;
    @SerializedName("emailID")
    @Expose
    public String emailID;
    @SerializedName("roleID")
    @Expose
    public Integer roleID;
    @SerializedName("role")
    @Expose
    public String role;
    @SerializedName("referalID")
    @Expose
    public Integer referalID;
    @SerializedName("slabID")
    @Expose
    public Integer slabID;
    @SerializedName("isGSTApplicable")
    @Expose
    public Boolean isGSTApplicable;
    @SerializedName("isTDSApplicable")
    @Expose
    public Boolean isTDSApplicable;
    @SerializedName("isCCFGstApplicable")
    @Expose
    public Boolean isCCFGstApplicable;
    @SerializedName("isVirtual")
    @Expose
    public Boolean isVirtual;
    @SerializedName("isWebsite")
    @Expose
    public Boolean isWebsite;
    @SerializedName("isAdminDefined")
    @Expose
    public Boolean isAdminDefined;
    @SerializedName("isSurchargeGST")
    @Expose
    public Boolean isSurchargeGST;
    @SerializedName("prefix")
    @Expose
    public String prefix;
    @SerializedName("outletID")
    @Expose
    public Integer outletID;
    @SerializedName("pincode")
    @Expose
    public String pincode;
    @SerializedName("wid")
    @Expose
    public Integer wid;
    @SerializedName("isDoubleFactor")
    @Expose
    public boolean isDoubleFactor;

    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("shopType")
    @Expose
    private String shopType;
    @SerializedName("qualification")
    @Expose
    private String qualification;
    @SerializedName("poupulation")
    @Expose
    private String poupulation;
    @SerializedName("locationType")
    @Expose
    private String locationType;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("alternateMobile")
    @Expose
    private String alternateMobile;
    @SerializedName("bankName")
    @Expose
    private String bankName;
    @SerializedName("ifsc")
    @Expose
    private String ifsc;
    @SerializedName("accountNumber")
    @Expose
    private String accountNumber;
    @SerializedName("accountName")
    @Expose
    private String accountName;

    @SerializedName("branchName")
    @Expose
    private String branchName;
    @SerializedName("isRealAPI")
    @Expose
    private boolean isRealAPI;

    public boolean isRealAPI() {
        return isRealAPI;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getShoptype() {
        return shopType;
    }

    public void setShoptype(String shoptype) {
        this.shopType = shoptype;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getPoupulation() {
        return poupulation;
    }

    public void setPoupulation(String poupulation) {
        this.poupulation = poupulation;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getAlternateMobile() {
        return alternateMobile;
    }

    public void setAlternateMobile(String alternateMobile) {
        this.alternateMobile = alternateMobile;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Double getCommRate() {
        return commRate;
    }

    public void setCommRate(Double commRate) {
        this.commRate = commRate;
    }

    public Integer getStateID() {
        return stateID;
    }

    public void setStateID(Integer stateID) {
        this.stateID = stateID;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public boolean isDoubleFactor() {
        return isDoubleFactor;
    }

    public void setDoubleFactor(boolean doubleFactor) {
        isDoubleFactor = doubleFactor;
    }

    public Integer getKycStatus() {
        return kycStatus;
    }

    public void setKycStatus(Integer kycStatus) {
        this.kycStatus = kycStatus;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getLoginID() {
        return loginID;
    }

    public void setLoginID(Integer loginID) {
        this.loginID = loginID;
    }

    public Integer getLt() {
        return lt;
    }

    public void setLt(Integer lt) {
        this.lt = lt;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getSlabs() {
        return slabs;
    }

    public void setSlabs(String slabs) {
        this.slabs = slabs;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getReferalID() {
        return referalID;
    }

    public void setReferalID(Integer referalID) {
        this.referalID = referalID;
    }

    public Integer getSlabID() {
        return slabID;
    }

    public void setSlabID(Integer slabID) {
        this.slabID = slabID;
    }

    public Boolean getGSTApplicable() {
        return isGSTApplicable;
    }

    public void setGSTApplicable(Boolean GSTApplicable) {
        isGSTApplicable = GSTApplicable;
    }

    public Boolean getTDSApplicable() {
        return isTDSApplicable;
    }

    public void setTDSApplicable(Boolean TDSApplicable) {
        isTDSApplicable = TDSApplicable;
    }

    public Boolean getCCFGstApplicable() {
        return isCCFGstApplicable;
    }

    public void setCCFGstApplicable(Boolean CCFGstApplicable) {
        isCCFGstApplicable = CCFGstApplicable;
    }

    public Boolean getVirtual() {
        return isVirtual;
    }

    public void setVirtual(Boolean virtual) {
        isVirtual = virtual;
    }

    public Boolean getWebsite() {
        return isWebsite;
    }

    public void setWebsite(Boolean website) {
        isWebsite = website;
    }

    public Boolean getAdminDefined() {
        return isAdminDefined;
    }

    public void setAdminDefined(Boolean adminDefined) {
        isAdminDefined = adminDefined;
    }

    public Boolean getSurchargeGST() {
        return isSurchargeGST;
    }

    public void setSurchargeGST(Boolean surchargeGST) {
        isSurchargeGST = surchargeGST;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Integer getOutletID() {
        return outletID;
    }

    public void setOutletID(Integer outletID) {
        this.outletID = outletID;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Integer getWid() {
        return wid;
    }

    public void setWid(Integer wid) {
        this.wid = wid;
    }
}
