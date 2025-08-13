package com.solution.brothergroup.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Slabs {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("slab")
    @Expose
    public String slab;
    @SerializedName("isRealSlab")
    @Expose
    public Boolean isRealSlab;
    @SerializedName("entryDate")
    @Expose
    public Object entryDate;
    @SerializedName("modifyDate")
    @Expose
    public Object modifyDate;
    @SerializedName("isActive")
    @Expose
    public Boolean isActive;
    @SerializedName("remark")
    @Expose
    public Object remark;
    @SerializedName("isAdminDefined")
    @Expose
    public Boolean isAdminDefined;

    public Integer getId() {
        return id;
    }

    public String getSlab() {
        return slab;
    }

    public Boolean getRealSlab() {
        return isRealSlab;
    }

    public Object getEntryDate() {
        return entryDate;
    }

    public Object getModifyDate() {
        return modifyDate;
    }

    public Boolean getActive() {
        return isActive;
    }

    public Object getRemark() {
        return remark;
    }

    public Boolean getAdminDefined() {
        return isAdminDefined;
    }
}
