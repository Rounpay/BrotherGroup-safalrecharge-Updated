package com.solution.brothergroup.Aeps.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.brothergroup.Api.Object.BcResponse;

import java.io.Serializable;
import java.util.List;

public class SdkDetail implements Serializable {

    @SerializedName("apiOutletID")
    @Expose
    private String apiOutletID;
    @SerializedName("apiOutletPassword")
    @Expose
    private String apiOutletPassword;
    @SerializedName("apiPartnerID")
    @Expose
    private String apiPartnerID;
    @SerializedName("apiOutletMob")
    @Expose
    private String apiOutletMob;

    @SerializedName("bcResponse")
    @Expose
    private List<BcResponse> bcResponse = null;
    @SerializedName("secretKey")
    @Expose
    private String secretKey;

    @SerializedName("emailID")
    @Expose
    private String emailID;

    public String getEmailID() {
        return emailID;
    }

    @SerializedName("outletName")
    @Expose
    private String outletName;

    public String getOutletName() {
        return outletName;
    }

    @SerializedName("serviceOutletPIN")
    @Expose
    private String serviceOutletPIN;

    public String getServiceOutletPIN() {
        return serviceOutletPIN;
    }

    public String getApiOutletID() {
        return apiOutletID;
    }

    public String getApiOutletPassword() {
        return apiOutletPassword;
    }

    public String getApiPartnerID() {
        return apiPartnerID;
    }

    public String getApiOutletMob() {
        return apiOutletMob;
    }

    public List<BcResponse> getBcResponse() {
        return bcResponse;
    }

    public String getSecretKey() {
        return secretKey;
    }


}
