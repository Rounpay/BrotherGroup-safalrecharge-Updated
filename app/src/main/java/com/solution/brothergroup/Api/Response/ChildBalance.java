package com.solution.brothergroup.Api.Response;

/**
 * Created by Vishnu on 17-04-2017.
 */

public class ChildBalance {

    private String ID;
    private String Name;
    private String PrepaidWallet;
    private String UtilityWallet;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrepaidWallet() {
        return PrepaidWallet;
    }

    public void setPrepaidWallet(String prepaidWallet) {
        PrepaidWallet = prepaidWallet;
    }

    public String getUtilityWallet() {
        return UtilityWallet;
    }

    public void setUtilityWallet(String utilityWallet) {
        UtilityWallet = utilityWallet;
    }
}
