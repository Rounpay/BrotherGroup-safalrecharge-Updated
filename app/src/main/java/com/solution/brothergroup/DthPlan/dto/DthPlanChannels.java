package com.solution.brothergroup.DthPlan.dto;

public class DthPlanChannels {

    String name;
    String genre;
    String logo;
    String header;

    public DthPlanChannels(String header, String name, String genre, String logo) {
        this.header = header;
        this.name = name;
        this.genre = genre;
        this.logo = logo;
    }

    public String getHeader() {
        return header;
    }

    public String getName() {
        return name+"";
    }

    public String getGenre() {
        return genre != null && !genre.isEmpty() ? genre : "Channels";
    }

    public String getLogo() {
        return logo;
    }
}
