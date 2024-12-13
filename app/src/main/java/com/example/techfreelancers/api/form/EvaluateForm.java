package com.example.techfreelancers.api.form;

public class EvaluateForm {

    private Integer projectId;

    private Integer rateStar;

    private String evaluationDetail;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getRateStar() {
        return rateStar;
    }

    public void setRateStar(Integer rateStar) {
        this.rateStar = rateStar;
    }

    public String getEvaluationDetail() {
        return evaluationDetail;
    }

    public void setEvaluationDetail(String evaluationDetail) {
        this.evaluationDetail = evaluationDetail;
    }
}
