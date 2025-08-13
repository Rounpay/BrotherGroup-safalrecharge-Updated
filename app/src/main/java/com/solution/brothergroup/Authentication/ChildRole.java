package com.solution.brothergroup.Authentication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChildRole {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("role")
    @Expose
    public String role;
    @SerializedName("ind")
    @Expose
    public int ind;

    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("prefix")
    @Expose
    private String prefix;


    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public int getInd() {
        return ind;
    }
}
