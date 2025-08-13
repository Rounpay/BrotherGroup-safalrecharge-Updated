package com.solution.brothergroup.Api.Response;

import com.solution.brothergroup.Api.Object.OperatorObject;

import java.util.ArrayList;

/**
 * Created by Vishnu on 08-04-2017.
 */

public class GetOperatorResponse {

    private String RESPONSESTATUS;
    private String message;
    private ArrayList<OperatorObject> prepaidOperator;
    private ArrayList<OperatorObject> postpaidOperator;
    private ArrayList<OperatorObject> dthOperator;
    private ArrayList<OperatorObject> landlineOperator;
    private ArrayList<OperatorObject> electricityOperator;
    private ArrayList<OperatorObject> gasOperator;
    private ArrayList<OperatorObject> Insurance;
    private ArrayList<OperatorObject> Water;

    public ArrayList<OperatorObject> getWater() {
        return Water;
    }

    public void setWater(ArrayList<OperatorObject> water) {
        Water = water;
    }

    public String getRESPONSESTATUS() {
        return RESPONSESTATUS;
    }

    public void setRESPONSESTATUS(String RESPONSESTATUS) {
        this.RESPONSESTATUS = RESPONSESTATUS;
    }

    public ArrayList<OperatorObject> getPrepaidOperator() {
        return prepaidOperator;
    }

    public void setPrepaidOperator(ArrayList<OperatorObject> prepaidOperator) {
        this.prepaidOperator = prepaidOperator;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<OperatorObject> getDthOperator() {
        return dthOperator;
    }

    public void setDthOperator(ArrayList<OperatorObject> dthOperator) {
        this.dthOperator = dthOperator;
    }

    public ArrayList<OperatorObject> getPostpaidOperator() {
        return postpaidOperator;
    }

    public void setPostpaidOperator(ArrayList<OperatorObject> postpaidOperator) {
        this.postpaidOperator = postpaidOperator;
    }

    public ArrayList<OperatorObject> getLandlineOperator() {
        return landlineOperator;
    }

    public void setLandlineOperator(ArrayList<OperatorObject> landlineOperator) {
        this.landlineOperator = landlineOperator;
    }

    public ArrayList<OperatorObject> getElectricityOperator() {
        return electricityOperator;
    }

    public void setElectricityOperator(ArrayList<OperatorObject> electricityOperator) {
        this.electricityOperator = electricityOperator;
    }

    public ArrayList<OperatorObject> getGasOperator() {
        return gasOperator;
    }

    public void setGasOperator(ArrayList<OperatorObject> gasOperator) {
        this.gasOperator = gasOperator;
    }

    public ArrayList<OperatorObject> getInsuranceOperator() {
        return Insurance;
    }

    public void setInsuranceOperator(ArrayList<OperatorObject> insuranceOperator) {
        Insurance = insuranceOperator;
    }
}
