package com.solution.brothergroup.DthPlan.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PlanRs implements Serializable {
    @SerializedName(value = "1 MONTHS",alternate = {"1 months","monthly"})
    @Expose
    private String _1MONTHS;
    @SerializedName(value = "3 MONTHS",alternate = {"3 months","quarterly"})
    @Expose
    private String _3MONTHS;
    @SerializedName(value = "6 MONTHS",alternate = {"6 months","halfYearly"})
    @Expose
    private String _6MONTHS;
    @SerializedName(value = "9 MONTHS",alternate = "9 months")
    @Expose
    private String _9MONTHS;
    @SerializedName(value = "1 YEAR",alternate = {"1 year","yearly"})
    @Expose
    private String _1YEAR;
    @SerializedName(value = "5 YEAR",alternate = "5 year")
    @Expose
    private String _5YEAR;

    public String get1MONTHS() {
        return _1MONTHS;
    }

    public void set1MONTHS(String _1MONTHS) {
        this._1MONTHS = _1MONTHS;
    }

    public String get3MONTHS() {
        return _3MONTHS;
    }

    public void set3MONTHS(String _3MONTHS) {
        this._3MONTHS = _3MONTHS;
    }

    public String get6MONTHS() {
        return _6MONTHS;
    }

    public void set6MONTHS(String _6MONTHS) {
        this._6MONTHS = _6MONTHS;
    }

    public String get9MONTHS() {
        return _9MONTHS;
    }

    public void set9MONTHS(String _9MONTHS) {
        this._9MONTHS = _9MONTHS;
    }

    public String get1YEAR() {
        return _1YEAR;
    }

    public void set1YEAR(String _1YEAR) {
        this._1YEAR = _1YEAR;
    }

    public String get5YEAR() {
        return _5YEAR;
    }

    public void set5YEAR(String _5YEAR) {
        this._5YEAR = _5YEAR;
    }
}
