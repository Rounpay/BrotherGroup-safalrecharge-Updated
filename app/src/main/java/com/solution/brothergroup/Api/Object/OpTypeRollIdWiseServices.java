package com.solution.brothergroup.Api.Object;

import java.util.List;

public class OpTypeRollIdWiseServices {

    List<AssignedOpType> getShowableActiveSerive;
    List<AssignedOpType> getShowableOtherReportSerive;
    List<AssignedOpType> getShowableRTReportSerive;
    List<AssignedOpType> getShowableFOSSerive;

    public OpTypeRollIdWiseServices(List<AssignedOpType> getShowableActiveSerive, List<AssignedOpType> getShowableOtherReportSerive,
                                    List<AssignedOpType> getShowableRTReportSerive, List<AssignedOpType> getShowableFOSSerive) {
        this.getShowableActiveSerive = getShowableActiveSerive;
        this.getShowableOtherReportSerive = getShowableOtherReportSerive;
        this.getShowableRTReportSerive = getShowableRTReportSerive;
        this.getShowableFOSSerive = getShowableFOSSerive;
    }

    public List<AssignedOpType> getGetShowableActiveSerive() {
        return getShowableActiveSerive;
    }

    public List<AssignedOpType> getGetShowableOtherReportSerive() {
        return getShowableOtherReportSerive;
    }

    public List<AssignedOpType> getGetShowableRTReportSerive() {
        return getShowableRTReportSerive;
    }

    public List<AssignedOpType> getGetShowableFOSSerive() {
        return getShowableFOSSerive;
    }
}
