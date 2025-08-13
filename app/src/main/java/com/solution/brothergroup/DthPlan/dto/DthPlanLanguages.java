package com.solution.brothergroup.DthPlan.dto;

import java.io.Serializable;

public class DthPlanLanguages implements Serializable {
    String language;
    String opname;
    int packageCount;

    public String getLanguage() {
        return language;
    }

    public String getOpname() {
        return opname;
    }

    public int getPackageCount() {
        return packageCount;
    }
}
