package com.solution.brothergroup.Api.Object;

/**
 * Created by Vishnu on 17-05-2017.
 */

public class BankDetailUser {

    private String id;
    private String IntroducerID;
    private String SNo;
    private String Role;
    private String RoleId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntroducerID() {
        return IntroducerID;
    }

    public void setIntroducerID(String introducerID) {
        IntroducerID = introducerID;
    }

    public String getSNo() {
        return SNo;
    }

    public void setSNo(String SNo) {
        this.SNo = SNo;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getRoleId() {
        return RoleId;
    }

    public void setRoleId(String roleId) {
        RoleId = roleId;
    }
}
