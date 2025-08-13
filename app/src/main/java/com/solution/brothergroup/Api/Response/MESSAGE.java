package com.solution.brothergroup.Api.Response;

/**
 * Created by Administrator on 15-03-2018.
 */

public class MESSAGE {
    private String Id;
    private String WhiteLabelId;
    private String News;
    private String Entry;
    private String DelFlag;
    private String Type;
    private String Title;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getWhiteLabelId() {
        return WhiteLabelId;
    }

    public void setWhiteLabelId(String whiteLabelId) {
        WhiteLabelId = whiteLabelId;
    }

    public String getNews() {
        return News;
    }

    public void setNews(String news) {
        News = news;
    }

    public String getEntry() {
        return Entry;
    }

    public void setEntry(String entry) {
        Entry = entry;
    }

    public String getDelFlag() {
        return DelFlag;
    }

    public void setDelFlag(String delFlag) {
        DelFlag = delFlag;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
