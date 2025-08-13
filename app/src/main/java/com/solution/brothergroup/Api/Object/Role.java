package com.solution.brothergroup.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Role implements Serializable {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("role")
    @Expose
    public String role;
    @SerializedName("ind")
    @Expose
    public Integer ind;

    public Integer getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public Integer getInd() {
        return ind;
    }
}
