package com.solution.brothergroup.Api.Object;

import java.io.Serializable;

/**
 * Created by Vishnu on 18-01-2017.
 */

public class OperatorList implements Serializable {


    String header;
    private String name;
    private int oid;
    private int opType;
    private boolean isBBPS;
    private boolean isBilling;
    private int min;
    private int max;
    private int length;
    private String startWith;
    private String image;
    private boolean isPartial;
    private String accountName;
    private String accountRemak;
    private String regExAccount;
    private String planDocName;
    private boolean isAccountNumeric;
    private int lengthMax;
    int stateID;
    String tollFree;
    double charge;
    boolean chargeAmtType;
    boolean isActive;
    boolean isTakeCustomerNum;

    public double getCharge() {
        return charge;
    }

    public boolean isChargeAmtType() {
        return chargeAmtType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOid() {
        return oid;
    }


    public int getOpType() {
        return opType;
    }

    public boolean getBBPS() {
        return isBBPS;
    }

    public boolean getIsBilling() {
        return isBilling;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }


    public int getLength() {
        if (length > 0) {
            return length;
        } else {
            return 1;
        }
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getStartWith() {
        return startWith;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean getIsPartial() {
        return isPartial;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountRemak() {
        return accountRemak;
    }

    public boolean getIsAccountNumeric() {
        return isAccountNumeric;
    }

    public int getLengthMax() {
        if (lengthMax > 0) {
            return lengthMax;
        } else {
            return 50;
        }

    }

    public boolean isTakeCustomerNum() {
        return isTakeCustomerNum;
    }

    public String getTollFree() {
        return tollFree != null && !tollFree.isEmpty() ? tollFree : "";
    }

    public int getStateID() {
        return stateID;
    }

    public String getRegExAccount() {
        return regExAccount;
    }

    public String getPlanDocName() {
        return planDocName;
    }

    public String getHeader() {
        return header;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
