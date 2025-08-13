package com.solution.brothergroup.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DataOpType {
    @SerializedName("assignedOpTypes")
    @Expose
    private List<AssignedOpType> assignedOpTypes = null;

    public List<AssignedOpType> getAssignedOpTypes() {
        return assignedOpTypes;
    }

    public void setAssignedOpTypes(List<AssignedOpType> assignedOpTypes) {
        this.assignedOpTypes = assignedOpTypes;
    }


    public List<AssignedOpType> getShowableActiveSerive(boolean isAddMoneyEnable) {
        /*boolean isDmtEnable = false;
        for (int i = 0; i < assignedOpTypes.size(); i++) {

            if (assignedOpTypes.get(i).getServiceID() == 14 || assignedOpTypes.get(i).getName().equalsIgnoreCase("DMT")) {
                assignedOpTypes.remove(i);
                isDmtEnable = true;
                break;
            }

        }
        if (isDmtEnable) {
            assignedOpTypes.add(new AssignedOpType(14, "DMT",true, true));
        }*/
        boolean isUPIPay = false;
        for (int i = 0; i < assignedOpTypes.size(); i++) {

            if (assignedOpTypes.get(i).getServiceID() == 62) {
                isUPIPay = true;
            }
        }
        assignedOpTypes.add(new AssignedOpType(100, "Fund Request", true, true));
        assignedOpTypes.add(new AssignedOpType(116, "Call Back\nRequest", true, true));
        // assignedOpTypes.add(new AssignedOpType(117, "Scan & Pay",true, true));
        if (isAddMoneyEnable) {
            assignedOpTypes.add(new AssignedOpType(37, "Add Money", true, true));
        }
        if (isUPIPay) {
            assignedOpTypes.add(new AssignedOpType(63, "Scan & Pay", true, true));
        }

        /* //For Testing
        assignedOpTypes.add(new AssignedOpType(24, "PSA",true, true));
        assignedOpTypes.add(new AssignedOpType(22, "AEPS",true, true));
        assignedOpTypes.add(new AssignedOpType(25, "Loan Repaiment",true, true));
        assignedOpTypes.add(new AssignedOpType(26, "Gas Cylinder Booking",true, true));
        assignedOpTypes.add(new AssignedOpType(27, "Life Insurance Premium",true, true));
        assignedOpTypes.add(new AssignedOpType(6, "Gas Pipe Line",true, true));
        assignedOpTypes.add(new AssignedOpType(17, "Water",true, true));
        assignedOpTypes.add(new AssignedOpType(16, "Broadband",true, true));
        assignedOpTypes.add(new AssignedOpType(4, "Landline",true, true));*/
        return assignedOpTypes;
    }

    public List<AssignedOpType> getShowableOtherReportSerive(boolean isAddMoneyEnable,boolean isAccountStatement) {
        boolean isDmtEnable = false;
        for (int i = 0; i < assignedOpTypes.size(); i++) {

            if (assignedOpTypes.get(i).getServiceID() == 14 || assignedOpTypes.get(i).getName().equalsIgnoreCase("DMT")) {
                isDmtEnable = true;
            }

        }
        List<AssignedOpType> reportSerive = new ArrayList<>();
        reportSerive.add(new AssignedOpType(109, "Fund Order Pending", true, true));
        reportSerive.add(new AssignedOpType(112, "App Users List", true, true));
        reportSerive.add(new AssignedOpType(111, "Create\nUser", true, true));
        // reportSerive.add(new AssignedOpType(119, "Create\nFos",true, true));
        reportSerive.add(new AssignedOpType(110, "Fund\nRequest", true, true));
        reportSerive.add(new AssignedOpType(1001, "Recharge\nReport", true, true));
        reportSerive.add(new AssignedOpType(118, "Specific Recharge\nReport", true, true));
        reportSerive.add(new AssignedOpType(102, "Ledger\nReport", true, true));
        reportSerive.add(new AssignedOpType(103, "Fund Order\nReport", true, true));
        reportSerive.add(new AssignedOpType(1004, "Complaint\nReport", true, true));
        if (isDmtEnable) {
            reportSerive.add(new AssignedOpType(105, "DMT\nReport", true, true));
        }
        // reportSerive.add(new ActiveSerive(106, "Fund Tran\nReport",true, true));
        reportSerive.add(new AssignedOpType(107, "Fund Debit Credit", true, true));
        reportSerive.add(new AssignedOpType(108, "User Daybook", true, true));
        reportSerive.add(new AssignedOpType(135, "MoveToBank Report", true, true));
        reportSerive.add(new AssignedOpType(113, "Commission Slab", true, true));
        reportSerive.add(new AssignedOpType(114, "W2R\nReport", true, true));
        reportSerive.add(new AssignedOpType(115, "Daybook\nDMT", true, true));
        reportSerive.add(new AssignedOpType(120, "AEPS\nReport", true, true));
        reportSerive.add(new AssignedOpType(116, "Call Back\nRequest", true, true));
        reportSerive.add(new AssignedOpType(117, "Scan & Pay", true, true));
        if (isAddMoneyEnable) {
            reportSerive.add(new AssignedOpType(37, "Add Money", true, true));
        }
        if(isAccountStatement) {
            reportSerive.add(new AssignedOpType(121, "Account Statement", true, true));
            reportSerive.add(new AssignedOpType(122, "FOS Outstanding Report", true, true));
            reportSerive.add(new AssignedOpType(123, "Channel Outstanding Report", true, true));
            reportSerive.add(new AssignedOpType(125, "Voucher Entry", true, true));

        }
        return reportSerive;
    }

    public List<AssignedOpType> getShowableRTReportSerive(boolean isAccountStatement) {
        boolean isDmtEnable = false;
        for (int i = 0; i < assignedOpTypes.size(); i++) {

            if (assignedOpTypes.get(i).getServiceID() == 14) {
                isDmtEnable = true;
            }

        }
        List<AssignedOpType> reportSerive = new ArrayList<>();
        reportSerive.add(new AssignedOpType(1001, "Recharge\nReport", true, true));
        reportSerive.add(new AssignedOpType(118, "Specific Recharge\nReport", true, true));
        reportSerive.add(new AssignedOpType(102, "Ledger\nReport", true, true));
        reportSerive.add(new AssignedOpType(103, "Fund Order\nReport", true, true));
        reportSerive.add(new AssignedOpType(120, "AEPS\nReport", true, true));
        reportSerive.add(new AssignedOpType(1004, "Complaint\nReport", true, true));
        if (isDmtEnable) {
            reportSerive.add(new AssignedOpType(105, "DMT\nReport", true, true));
        }
        // reportSerive.add(new AssignedOpType(106, "Fund Tran\nReport",true, true));
        reportSerive.add(new AssignedOpType(107, "Fund\nDebit Credit", true, true));
        reportSerive.add(new AssignedOpType(108, "User\nDaybook", true, true));
        // reportSerive.add(new AssignedOpType(109, "Fund Order Pending",true, true));
        // reportSerive.add(new AssignedOpType(110, "Fund\nRequest",true, true));
        //reportSerive.add(new AssignedOpType(111, "Create\nUser",true, true));
        // reportSerive.add(new AssignedOpType(119, "Create\nFos",true, true));
        //reportSerive.add(new AssignedOpType(112, "App Users List",true, true));
        reportSerive.add(new AssignedOpType(113, "Commission\nSlab", true, true));
        reportSerive.add(new AssignedOpType(114, "W2R\nReport", true, true));
        reportSerive.add(new AssignedOpType(115, "Daybook\nDMT", true, true));
        if(isAccountStatement) {
            reportSerive.add(new AssignedOpType(124, "Account Statement", true, true));
        }
        reportSerive.add(new AssignedOpType(135, "MoveToBank Report", true, true));

        return reportSerive;
    }

    public ArrayList<AssignedOpType> getShowableFOSSerive(boolean isAccountStatement) {

        ArrayList<AssignedOpType> reportSerive = new ArrayList<>();
        reportSerive.add(new AssignedOpType(112, "App Users List", true, true));
        reportSerive.add(new AssignedOpType(102, "Ledger\nReport", true, true));
        if (isAccountStatement) {
            reportSerive.add(new AssignedOpType(123, "Channel Outstanding Report", true, true));
            reportSerive.add(new AssignedOpType(128, "Areas", true, true));
        }

        return reportSerive;
    }

}
