package com.solution.brothergroup.Api.Response;

import com.solution.brothergroup.Api.Object.NumberList;
import com.solution.brothergroup.Api.Object.OperatorList;

import java.util.ArrayList;

public class NumberlistData {
    private ArrayList<OperatorList> operators;
    private ArrayList<NumberList> numSeries;
    private ArrayList<CircleList> cirlces;
    private String name;

    private int opType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOpType() {
        return opType;
    }

    public void setOpType(int opType) {
        this.opType = opType;
    }

    public ArrayList<NumberList> getNumSeries() {
        return numSeries;
    }

    public void setNumSeries(ArrayList<NumberList> numSeries) {
        this.numSeries = numSeries;
    }

    public ArrayList<OperatorList> getOperators() {
        return operators;
    }

    public void setOperators(ArrayList<OperatorList> operators) {
        this.operators = operators;
    }

    public ArrayList<CircleList> getCirlces() {
        return cirlces;
    }

    public void setCirlces(ArrayList<CircleList> cirlces) {
        this.cirlces = cirlces;
    }
}
