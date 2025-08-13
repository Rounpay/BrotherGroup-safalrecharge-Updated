package com.solution.brothergroup.Api.Response;

import com.solution.brothergroup.Api.Object.DMRTransactions;

import java.util.ArrayList;

/**
 * Created by Vishnu on 18-04-2017.
 */

public class DMRReportResponse {

    private String RESPONSESTATUS;
    private String message;
    private ArrayList<com.solution.brothergroup.Api.Object.DMRTransactions> DMRTransactions;

    public String getRESPONSESTATUS() {
        return RESPONSESTATUS;
    }

    public void setRESPONSESTATUS(String RESPONSESTATUS) {
        this.RESPONSESTATUS = RESPONSESTATUS;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<DMRTransactions> getDMRTransactions() {
        return DMRTransactions;
    }

    public void setDMRTransactions(ArrayList<DMRTransactions> DMRTransactions) {
        this.DMRTransactions = DMRTransactions;
    }
}
