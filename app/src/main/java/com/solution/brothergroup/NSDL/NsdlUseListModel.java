package com.solution.brothergroup.NSDL;

public class NsdlUseListModel {
    String name;
    String oid;
    String opType;
    String accountName;

    public NsdlUseListModel(String name, String oid, String opType, String accountName) {
        this.name = name;
        this.oid = oid;
        this.opType = opType;
        this.accountName = accountName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
