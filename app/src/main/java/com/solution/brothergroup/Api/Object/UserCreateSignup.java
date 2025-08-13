package com.solution.brothergroup.Api.Object;

/**
 * Created by Vishnu Agarwal on 20,December,2019
 */
public class UserCreateSignup {

    String address;
    String mobileNo;
    String name;
    String outletName;
    String emailID;
    int roleID;
    String pincode;
    String referalID;
    String OTP;
    String GenerateOTP;

    public UserCreateSignup(String address, String mobileNo, String name, String outletName, String emailID, int roleID, String pincode,String referalID,String OTP,String GenerateOTP) {
        this.address = address;
        this.mobileNo = mobileNo;
        this.name = name;
        this.outletName = outletName;
        this.emailID = emailID;
        this.roleID = roleID;
        this.pincode = pincode;
        this.referalID = referalID;
        this.OTP = OTP;
        this.GenerateOTP = GenerateOTP;
    }



}
