package com.solution.brothergroup.BrowsePlan.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 21,November,2019
 */
public class PlanDataRecords {
    @SerializedName(value = "Data Pack", alternate = {"DataPack","Data-Pack","data pack","data-pack","datapack","DATA PACK"})
    @Expose
    public ArrayList<PlanDataDetails> dataPack;
    @SerializedName(value = "smart recharge", alternate = {"Smart Recharge","smartrecharge","SmartRecharge","Smart-Recharge","SMART RECHARGE","SMART-RECHARGE"})
    @Expose
    public ArrayList<PlanDataDetails> smartRecharge;
    @SerializedName(value = "frcsrc", alternate = {"FRCSRC","FrcSrc","Frc-Src","frc src","Frc Src","FRC SRC"})
    @Expose
    public ArrayList<PlanDataDetails> frcsrc;
    @SerializedName(value = "unlimited", alternate = {"Unlimited","UNLIMITED"})
    @Expose
    public ArrayList<PlanDataDetails> unlimited;
    @SerializedName(value = "all", alternate = {"All","ALL"})
    @Expose
    public ArrayList<PlanDataDetails> all;
    @SerializedName(value = "FRCNon-Prime", alternate = {"FRCNonPrime","FRC Non-Prime","FRC Non Prime","FRCNon Prime","frcnon-prime","FRCNON-PRIME"})
    @Expose
    public ArrayList<PlanDataDetails> FRCNonPrime;
    @SerializedName(value = "Cricket Pack", alternate = {"cricket pack","cricketPack","CricketPack","cricketpack","Cricket-Pack"})
    @Expose
    public ArrayList<PlanDataDetails> cricketPack;
    @SerializedName(value = "jioPrime", alternate = {"Jio Prime","jio prime","JioPrime","JIOPRIME"})
    @Expose
    public ArrayList<PlanDataDetails> jioPrime;
    @SerializedName(value = "jioPhone", alternate = {"JioPhone","jio Phone","Jio Phone","jiophone"})
    @Expose
    public ArrayList<PlanDataDetails> jioPhone;
    @SerializedName(value = "All in One", alternate = {"AllinOne","all in one","All-in-One","ALL-IN-ONE"})
    @Expose
    public ArrayList<PlanDataDetails> allInOne;
    @SerializedName(value = "NEW ALL-IN-ONE", alternate = {"new all-in-one","NEWALLINONE","New All-In-One"})
    @Expose
    public ArrayList<PlanDataDetails> newAllInOne;
    @SerializedName(value = "IUC Topup", alternate = {"iuc topup","IUCTopup","IUC-Topup"})
    @Expose
    public ArrayList<PlanDataDetails> IUCTopup;
    @SerializedName(value = "frc", alternate = "FRC")
    @Expose
    public ArrayList<PlanDataDetails> frc;
    @SerializedName(value = "isd", alternate = "ISD")
    @Expose
    public ArrayList<PlanDataDetails> isd;
    @SerializedName(value = "talktime", alternate = {"TALK TIME", "talk time"})
    @Expose
    public ArrayList<PlanDataDetails> talktime = null;
    @SerializedName(value = "stv", alternate = "STV")
    @Expose
    public ArrayList<PlanDataDetails> stv = null;
    @SerializedName(value = "data", alternate = "DATA")
    @Expose
    public ArrayList<PlanDataDetails> data = null;
    @SerializedName(value = "international roaming", alternate = "INTERNATIONAL ROAMING")
    @Expose
    public ArrayList<PlanDataDetails> internationalRoaming = null;
    @SerializedName(value = "2G3G DATA", alternate = {"2g3g data", "2g/3g data", "2G/3G DATA"})
    @Expose
    public ArrayList<PlanDataDetails> _2g3gData;
    @SerializedName(value = "validity extension", alternate = "VALIDITY EXTENSION")
    @Expose
    public ArrayList<PlanDataDetails> validityExtension;
    @SerializedName(value = "combo vouchers", alternate = "COMBO VOUCHERS")
    @Expose
    public ArrayList<PlanDataDetails> comboVouchers;
    @SerializedName(value = "data plans", alternate = "DATA PLANS")
    @Expose
    public ArrayList<PlanDataDetails> dataPlans;
    @SerializedName(value = "unlimited plans", alternate = "UNLIMITED PLANS")
    @Expose
    public ArrayList<PlanDataDetails> unlimitedPlans;
    @SerializedName(value = "MBLAZE ULTRA", alternate = "mblaze ultra")
    @Expose
    public ArrayList<PlanDataDetails> mblazeUltra;
    @SerializedName(value = "wifi ultra recharges", alternate = "WIFI ULTRA RECHARGES")
    @Expose
    public ArrayList<PlanDataDetails> wifiUltraRecharges;
    @SerializedName(value = "topup", alternate = "TOPUP")
    @Expose
    public ArrayList<PlanDataDetails> topup;
    @SerializedName(value = "FULLTT", alternate = "fulltt")
    @Expose
    private ArrayList<PlanDataDetails> fulltt = null;
    @SerializedName(value = "3G/4G", alternate = {"3g/4g", "3g 4g"})
    @Expose
    private ArrayList<PlanDataDetails> _3g4gG = null;
    @SerializedName(value = "RATE CUTTER", alternate = {"rate cutter", "ratecutter"})
    @Expose
    private ArrayList<PlanDataDetails> ratecutter = null;
    @SerializedName(value = "2G", alternate = "2g")
    @Expose
    private ArrayList<PlanDataDetails> _2g = null;
    @SerializedName(value = "SMS", alternate = "sms")
    @Expose
    private ArrayList<PlanDataDetails> sms = null;
    @SerializedName(value = "Romaing", alternate = "romaing")
    @Expose
    private ArrayList<PlanDataDetails> romaing = null;
    @SerializedName(value = "COMBO", alternate = "combo")
    @Expose
    private ArrayList<PlanDataDetails> combo = null;

    @SerializedName(value = "VAS", alternate = "vas")
    @Expose
    public ArrayList<PlanDataDetails> vas;

    @SerializedName(value = "HOTSTAR", alternate = "Hotstar")
    @Expose
    public ArrayList<PlanDataDetails> hotstar;

    @SerializedName(value = "ALL ROUNDER", alternate = "All Rounder")
    @Expose
    public ArrayList<PlanDataDetails> allRounder;

    @SerializedName(value = "VALIDITY" , alternate = "Validity")
    @Expose
    public ArrayList<PlanDataDetails> validity;



    public ArrayList<PlanDataDetails> getVas() {
        return vas;
    }

    public ArrayList<PlanDataDetails> getHotstar() {
        return hotstar;
    }

    public ArrayList<PlanDataDetails> getAllRounder() {
        return allRounder;
    }

    public ArrayList<PlanDataDetails> getValidity() {
        return validity;
    }

    public ArrayList<PlanDataDetails> getFulltt() {
        return fulltt;
    }

    public ArrayList<PlanDataDetails> get_3g4gG() {
        return _3g4gG;
    }

    public ArrayList<PlanDataDetails> getRatecutter() {
        return ratecutter;
    }

    public ArrayList<PlanDataDetails> get_2g() {
        return _2g;
    }

    public ArrayList<PlanDataDetails> getSms() {
        return sms;
    }

    public ArrayList<PlanDataDetails> getRomaing() {
        return romaing;
    }

    public ArrayList<PlanDataDetails> getCombo() {
        return combo;
    }

    public ArrayList<PlanDataDetails> getFrc() {
        return frc;
    }

    public ArrayList<PlanDataDetails> getIsd() {
        return isd;
    }

    public ArrayList<PlanDataDetails> getTalktime() {
        return talktime;
    }

    public ArrayList<PlanDataDetails> getStv() {
        return stv;
    }

    public ArrayList<PlanDataDetails> getData() {
        return data;
    }

    public ArrayList<PlanDataDetails> getInternationalRoaming() {
        return internationalRoaming;
    }

    public ArrayList<PlanDataDetails> get_2g3gData() {
        return _2g3gData;
    }

    public ArrayList<PlanDataDetails> getValidityExtension() {
        return validityExtension;
    }

    public ArrayList<PlanDataDetails> getComboVouchers() {
        return comboVouchers;
    }

    public ArrayList<PlanDataDetails> getDataPlans() {
        return dataPlans;
    }

    public ArrayList<PlanDataDetails> getUnlimitedPlans() {
        return unlimitedPlans;
    }

    public ArrayList<PlanDataDetails> getMblazeUltra() {
        return mblazeUltra;
    }

    public ArrayList<PlanDataDetails> getWifiUltraRecharges() {
        return wifiUltraRecharges;
    }

    public ArrayList<PlanDataDetails> getTopup() {
        return topup;
    }

 public ArrayList<PlanDataDetails> getDataPack() {
  return dataPack;
 }

 public ArrayList<PlanDataDetails> getSmartRecharge() {
  return smartRecharge;
 }

 public ArrayList<PlanDataDetails> getFrcsrc() {
  return frcsrc;
 }

 public ArrayList<PlanDataDetails> getUnlimited() {
  return unlimited;
 }

 public ArrayList<PlanDataDetails> getAll() {
  return all;
 }

 public ArrayList<PlanDataDetails> getFRCNonPrime() {
  return FRCNonPrime;
 }

 public ArrayList<PlanDataDetails> getCricketPack() {
  return cricketPack;
 }

 public ArrayList<PlanDataDetails> getJioPrime() {
  return jioPrime;
 }

 public ArrayList<PlanDataDetails> getJioPhone() {
  return jioPhone;
 }

 public ArrayList<PlanDataDetails> getAllInOne() {
  return allInOne;
 }

 public ArrayList<PlanDataDetails> getNewAllInOne() {
  return newAllInOne;
 }

 public ArrayList<PlanDataDetails> getIUCTopup() {
  return IUCTopup;
 }
}
