package com.solution.brothergroup.Api.Object;

public class SupportListItem {
    String name, image, number, msg;

    public SupportListItem(String name, String image, String number, String msg) {
        this.name = name;
        this.image = image;
        this.number = number;
        this.msg = msg;
    }

    public String getImage() {
        return image;
    }

    public String getNumber() {
        return number;
    }

    public String getMsg() {
        return msg;
    }

    public String getName() {
        return name;
    }
}
