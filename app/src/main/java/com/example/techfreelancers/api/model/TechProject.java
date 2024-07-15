package com.example.techfreelancers.api.model;

import java.math.BigDecimal;

public class TechProject extends BaseBean {

    /**
     * Project id
     */
    private Integer projectId;

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

    /**
     * project vote
     */
    private Integer projectVote;

    /**
     * project publish user id
     */
    private Integer publishUserId;

    /**
     * the user id of whom accept the project
     */
    private Integer acceptUserId;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
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

    public Integer getProjectVote() {
        return projectVote;
    }

    public void setProjectVote(Integer projectVote) {
        this.projectVote = projectVote;
    }

    public Integer getPublishUserId() {
        return publishUserId;
    }

    public void setPublishUserId(Integer publishUserId) {
        this.publishUserId = publishUserId;
    }

    public Integer getAcceptUserId() {
        return acceptUserId;
    }

    public void setAcceptUserId(Integer acceptUserId) {
        this.acceptUserId = acceptUserId;
    }

    private String publishUserName;

    public String getPublishUserName() {
        return publishUserName;
    }

    public void setPublishUserName(String publishUserName) {
        this.publishUserName = publishUserName;
    }
}
