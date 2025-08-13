package com.solution.brothergroup.AddMoney.modelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RPayRequest {

    @SerializedName("key_id")
    @Expose
    private String keyId;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("prefill_name")
    @Expose
    private String prefillName;
    @SerializedName("prefill_contact")
    @Expose
    private String prefillContact;
    @SerializedName("prefill_email")
    @Expose
    private String prefillEmail;
    @SerializedName("callback_url")
    @Expose
    private String callbackUrl;
    @SerializedName("cancel_url")
    @Expose
    private String cancelUrl;
    @SerializedName("amount")
    @Expose
    private Integer amount;

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrefillName() {
        return prefillName;
    }

    public void setPrefillName(String prefillName) {
        this.prefillName = prefillName;
    }

    public String getPrefillContact() {
        return prefillContact;
    }

    public void setPrefillContact(String prefillContact) {
        this.prefillContact = prefillContact;
    }

    public String getPrefillEmail() {
        return prefillEmail;
    }

    public void setPrefillEmail(String prefillEmail) {
        this.prefillEmail = prefillEmail;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
