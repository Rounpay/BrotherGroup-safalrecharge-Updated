package com.solution.brothergroup.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AreaMaster implements Serializable
{
        @SerializedName("areaID")
        @Expose
        private int areaID;
        @SerializedName("userID")
        @Expose
        private int userID;
        @SerializedName("area")
        @Expose
        private String area;
        @SerializedName("entryDate")
        @Expose
        private String entryDate;
        @SerializedName("modifyDate")
        @Expose
        private String modifyDate;

        public int getAreaID() {
            return areaID;
        }

        public void setAreaID(int areaID) {
            this.areaID = areaID;
        }

        public int getUserID() {
            return userID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getEntryDate() {
            return entryDate;
        }

        public void setEntryDate(String entryDate) {
            this.entryDate = entryDate;
        }

        public String getModifyDate() {
            return modifyDate;
        }

        public void setModifyDate(String modifyDate) {
            this.modifyDate = modifyDate;
        }

    }

