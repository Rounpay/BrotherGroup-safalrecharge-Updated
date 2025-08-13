package com.solution.brothergroup.Util.DropdownDialog;

public class DropDownModel {

    String name;
    Object dataObject;

    public DropDownModel(String name, Object dataObject) {
        this.name = name;
        this.dataObject = dataObject;
    }

    public String getName() {
        return name;
    }

    public Object getDataObject() {
        return dataObject;
    }
}
