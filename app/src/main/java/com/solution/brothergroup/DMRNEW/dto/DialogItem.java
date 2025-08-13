package com.solution.brothergroup.DMRNEW.dto;

import android.graphics.drawable.Drawable;

public class DialogItem {
    public String app = "";
    public Drawable icon;

    public DialogItem(String name, Drawable drawable) {
        app = name;
        icon = drawable;
    }
}
