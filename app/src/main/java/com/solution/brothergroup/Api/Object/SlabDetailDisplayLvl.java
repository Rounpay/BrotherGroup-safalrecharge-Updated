package com.solution.brothergroup.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SlabDetailDisplayLvl {

    @SerializedName("min")
    @Expose
    public double min;

    @SerializedName("max")
    @Expose
    public double max;
    @SerializedName("commSettingType")
    @Expose
    public int commSettingType;

    @SerializedName("oid")
    @Expose
    public int oid;
    @SerializedName("operator")
    @Expose
    public String operator;
    @SerializedName("opType")
    @Expose
    public String opType;
    @SerializedName("roleCommission")
    @Expose
    public ArrayList<RoleCommission> roleCommission = null;
    String Header;
    @SerializedName(value = "opTypeID", alternate = "opTypeId")
    @Expose
    public int opTypeId;
    public SlabDetailDisplayLvl(String header, int oid, String operator, String opType,int opTypeId,double min,double max,int commSettingType, ArrayList<RoleCommission> roleCommission) {
        Header = header;
        this.oid = oid;
        this.operator = operator;
        this.opType = opType;
        this.opTypeId = opTypeId;
        this.roleCommission = roleCommission;
        this.max=max;
        this.min=min;
        this.commSettingType=commSettingType;
    }

    public int getOid() {
        return oid;
    }

    public String getOperator() {
        return operator;
    }

    public String getOpType() {
        return opType;
    }

    public String getHeader() {
        return Header;
    }

    public void setHeader(String header) {
        Header = header;
    }

    public double getMin() {
        return min;
    }

    public int getCommSettingType() {
        return commSettingType;
    }

    public double getMax() {
        return max;
    }

    public int getOpTypeId() {
        return opTypeId;
    }

    public ArrayList<RoleCommission> getRoleCommission() {
        return roleCommission;
    }
}
