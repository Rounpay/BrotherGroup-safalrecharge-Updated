package com.solution.brothergroup.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDox {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("docTypeID")
    @Expose
    public Integer docTypeID;
    @SerializedName("docName")
    @Expose
    public String docName;
    @SerializedName("isOptional")
    @Expose
    public Boolean isOptional;
    @SerializedName("remark")
    @Expose
    public String remark;
    @SerializedName("modifyDate")
    @Expose
    public String modifyDate;
    @SerializedName("userId")
    @Expose
    public Integer userId;
    @SerializedName("statusCode")
    @Expose
    public Integer statusCode;
    @SerializedName("entryDate")
    @Expose
    public String entryDate;
    @SerializedName("docUrl")
    @Expose
    public String docUrl;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("verifyStatus")
    @Expose
    public Integer verifyStatus;
    @SerializedName("loginId")
    @Expose
    public Integer loginId;
    @SerializedName("dRemark")
    @Expose
    public String dRemark;
    @SerializedName("isOutlet")
    @Expose
    public Integer isOutlet;
    @SerializedName("loginTypeID")
    @Expose
    public Integer loginTypeID;
    @SerializedName("outletID")
    @Expose
    public Integer outletID;
    @SerializedName("kycStatus")
    @Expose
    public Integer kycStatus;
    @SerializedName("msg")
    @Expose
    public String msg;

    public Integer getId() {
        return id;
    }

    public Integer getDocTypeID() {
        return docTypeID;
    }

    public String getDocName() {
        return docName;
    }

    public Boolean getOptional() {
        return isOptional;
    }

    public String getRemark() {
        return remark;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public String getDescription() {
        return description;
    }

    public Integer getVerifyStatus() {
        return verifyStatus;
    }

    public Integer getLoginId() {
        return loginId;
    }

    public String getdRemark() {
        if (dRemark != null && !dRemark.isEmpty()) {
            return dRemark;
        } else {
            return "";
        }
    }

    public Integer getIsOutlet() {
        return isOutlet;
    }

    public Integer getLoginTypeID() {
        return loginTypeID;
    }

    public Integer getOutletID() {
        return outletID;
    }

    public Integer getKycStatus() {
        return kycStatus;
    }

    public String getMsg() {
        return msg;
    }
}
