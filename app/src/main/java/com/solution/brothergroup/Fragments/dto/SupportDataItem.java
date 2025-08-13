package com.solution.brothergroup.Fragments.dto;

/**
 * Created by Vishnu Agarwal on 12,December,2019
 */
public class SupportDataItem {

   /* type 1=Mobile;
    type 2= Phone
    type 3= Whatsapp
    type 4= Email*/
    int type;
    String value;
    int icon;

    public SupportDataItem(int type, String value, int icon) {
        this.type = type;
        this.value = value;
        this.icon = icon;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
