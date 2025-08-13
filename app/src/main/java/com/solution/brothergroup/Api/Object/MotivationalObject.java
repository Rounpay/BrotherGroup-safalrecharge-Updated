package com.solution.brothergroup.Api.Object;

/**
 * Created by Microsoft on 01-09-2017.
 */

public class MotivationalObject {

    private String Id;
    private String VideoUrl;
    private String Entrydate;
    private String Tital;
    private String Type;
    private String ToTime;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

    public String getEntrydate() {
        return Entrydate;
    }

    public void setEntrydate(String entrydate) {
        Entrydate = entrydate;
    }

    public String getTital() {
        return Tital;
    }

    public void setTital(String tital) {
        Tital = tital;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getToTime() {
        return ToTime;
    }

    public void setToTime(String toTime) {
        ToTime = toTime;
    }

}
