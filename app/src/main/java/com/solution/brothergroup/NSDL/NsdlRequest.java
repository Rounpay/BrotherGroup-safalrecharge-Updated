package com.solution.brothergroup.NSDL;

public class NsdlRequest {
    String Version;
    String APPID;
    String UserID;
    String loginTypeID;
    String SessionID;
    String Session;
    String Name;
    String Gender;
    String Mobile;
    String email;
    String OID;
    String imei;
    String ApplicationType;
    String ack_no;
    String transactionID;
    String tid;

    public NsdlRequest(String version, String APPID, String userID, String loginTypeID, String sessionID, String session, String name, String gender, String mobile, String email, String OID, String imei, String applicationType) {
        Version = version;
        this.APPID = APPID;
        UserID = userID;
        this.loginTypeID = loginTypeID;
        SessionID = sessionID;
        Session = session;
        Name = name;
        Gender = gender;
        Mobile = mobile;
        this.email = email;
        this.OID = OID;
        this.imei = imei;
        ApplicationType = applicationType;
    }

    public NsdlRequest(String version, String APPID, String userID, String loginTypeID, String sessionID, String session, String imei, String tid,String transactionID) {
        Version = version;
        this.APPID = APPID;
        UserID = userID;
        this.loginTypeID = loginTypeID;
        SessionID = sessionID;
        Session = session;
        this.imei = imei;
        this.tid = tid;
        this.transactionID = transactionID;
    }

    public String getAck_no() {
        return ack_no;
    }

    public void setAck_no(String ack_no) {
        this.ack_no = ack_no;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getAPPID() {
        return APPID;
    }

    public void setAPPID(String APPID) {
        this.APPID = APPID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getLoginTypeID() {
        return loginTypeID;
    }

    public void setLoginTypeID(String loginTypeID) {
        this.loginTypeID = loginTypeID;
    }

    public String getSessionID() {
        return SessionID;
    }

    public void setSessionID(String sessionID) {
        SessionID = sessionID;
    }

    public String getSession() {
        return Session;
    }

    public void setSession(String session) {
        Session = session;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOID() {
        return OID;
    }

    public void setOID(String OID) {
        this.OID = OID;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getApplicationType() {
        return ApplicationType;
    }

    public void setApplicationType(String applicationType) {
        ApplicationType = applicationType;
    }
}
