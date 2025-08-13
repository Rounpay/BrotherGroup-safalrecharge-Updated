package com.solution.brothergroup.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountOpData {
    @SerializedName("oid")
    @Expose
    public int oid;
    @SerializedName("redirectURL")
    @Expose
    public String  redirectURL;
    @SerializedName("content")
    @Expose
    public String content;
    @SerializedName("name")
    @Expose
    public String name;

    public int getOid() {
        return oid;
    }

    public String getRedirectURL() {
        return redirectURL;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }
}
