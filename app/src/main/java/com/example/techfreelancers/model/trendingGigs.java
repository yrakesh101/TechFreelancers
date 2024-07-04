package com.example.techfreelancers.model;

public class trendingGigs {
    private String projectTitle;
    private String cost;
    private String timeSpan;
    private String descriptionDetails;

    // Constructor
    public trendingGigs(String projectTitle, String cost, String timeSpan, String descriptionDetails) {
        this.projectTitle = projectTitle;
        this.cost = cost;
        this.timeSpan = timeSpan;
        this.descriptionDetails = descriptionDetails;
    }

    // Getters
    public String getProjectTitle() {
        return projectTitle;
    }

    public String getCost() {
        return cost;
    }

    public String getTimeSpan() {
        return timeSpan;
    }

    public String getDescriptionDetails() {
        return descriptionDetails;
    }
}
