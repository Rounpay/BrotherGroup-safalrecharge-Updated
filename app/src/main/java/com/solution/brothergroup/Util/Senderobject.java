package com.solution.brothergroup.Util;

public class Senderobject {

    private String mobileNo;
    private String name;
    private String sid;
    private String lastName;
    private String pincode;
    private String address;
    private String otp;
    private String dob;


    public Senderobject(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Senderobject(String mobileNo, String name, String lastName, String pincode, String address, String otp, String dob) {
        this.mobileNo = mobileNo;
        this.name = name;
        this.lastName = lastName;
        this.pincode = pincode;
        this.address = address;
        this.otp = otp;
        this.dob = dob;
    }
    public Senderobject(String mobileNo, String otp) {
        this.mobileNo = mobileNo;
        this.otp = otp;
    }
    public Senderobject(String mobileNo, String otp, String sid) {
        this.mobileNo = mobileNo;
        this.otp = otp;
        this.sid = sid;
    }


}
