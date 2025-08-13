package com.solution.brothergroup.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActiveSerive {
    @SerializedName("serviceID")
    @Expose
    public int serviceID;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("isActive")
    @Expose
    public boolean isActive;

    public ActiveSerive(int serviceID, String name, boolean isActive) {
        this.serviceID = serviceID;
        this.name = name;
        this.isActive = isActive;
    }

    public int getServiceID() {
        return serviceID;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return isActive;
    }
}
