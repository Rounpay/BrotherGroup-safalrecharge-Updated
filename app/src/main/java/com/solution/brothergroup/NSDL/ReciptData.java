

package com.solution.brothergroup.NSDL;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


 public class ReciptData {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("p_order_id")
    @Expose
    private String pOrderId;
    @SerializedName("ack_no")
    @Expose
    private String ackNo;
    @SerializedName("error_message")
    @Expose
    private String errorMessage;
    @SerializedName("date")
    @Expose
    private String date;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ReciptData() {
    }

    /**
     * 
     * @param pOrderId
     * @param date
     * @param orderId
     * @param errorMessage
     * @param ackNo
     */
    public ReciptData(String orderId, String pOrderId, String ackNo, String errorMessage, String date) {
        super();
        this.orderId = orderId;
        this.pOrderId = pOrderId;
        this.ackNo = ackNo;
        this.errorMessage = errorMessage;
        this.date = date;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getpOrderId() {
        return pOrderId;
    }

    public void setpOrderId(String pOrderId) {
        this.pOrderId = pOrderId;
    }

    public String getAckNo() {
        return ackNo;
    }

    public void setAckNo(String ackNo) {
        this.ackNo = ackNo;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
