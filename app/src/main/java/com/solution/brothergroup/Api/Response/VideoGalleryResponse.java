package com.solution.brothergroup.Api.Response;


import com.solution.brothergroup.Api.Object.MotivationalObject;
import com.solution.brothergroup.Api.Object.VideoObject;

import java.util.ArrayList;

/**
 * Created by Microsoft on 01-09-2017.
 */

public class VideoGalleryResponse {

    private String RESPONSESTATUS;
    private String message;
    private ArrayList<Banner> data;
    private ArrayList<VideoObject> Table;
    //  private ArrayList<GalleryObject> Table1;
    private ArrayList<MotivationalObject> Table2;

    public String getRESPONSESTATUS() {
        return RESPONSESTATUS;
    }

    public void setRESPONSESTATUS(String RESPONSESTATUS) {
        this.RESPONSESTATUS = RESPONSESTATUS;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<VideoObject> getTable() {
        return Table;
    }

    public void setTable(ArrayList<VideoObject> table) {
        Table = table;
    }


    public ArrayList<MotivationalObject> getTable2() {
        return Table2;
    }

    public void setTable2(ArrayList<MotivationalObject> table2) {
        Table2 = table2;
    }

    public ArrayList<Banner> getData() {
        return data;
    }

    public void setData(ArrayList<Banner> data) {
        this.data = data;
    }
}
