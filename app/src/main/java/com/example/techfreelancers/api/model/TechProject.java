package com.example.techfreelancers.api.model;

import java.math.BigDecimal;

public class TechProject {

    /**
     * Project id
     */
    private int projectId;

    /**
     * Project title
     */
    private String projectTitle;

    /**
     * Money cost
     */
    private BigDecimal projectCost;

    /**
     * Project category
     */
    private String projectCategory;

    /**
     * Time spend
     */
    private String timeSpan;

    /**
     * Detail about the project
     */
    private String projectDetail;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public BigDecimal getProjectCost() {
        return projectCost;
    }

    public void setProjectCost(BigDecimal projectCost) {
        this.projectCost = projectCost;
    }

    public String getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
    }

    public String getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
    }

    public String getProjectDetail() {
        return projectDetail;
    }

    public void setProjectDetail(String projectDetail) {
        this.projectDetail = projectDetail;
    }
}
