
package com.solution.brothergroup.NSDL;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Reciptpojo {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ReciptData data;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Reciptpojo() {
    }

    /**
     * 
     * @param data
     * @param message
     * @param status
     */
    public Reciptpojo(String status, String message, ReciptData data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ReciptData getData() {
        return data;
    }

    public void setData(ReciptData data) {
        this.data = data;
    }

}
