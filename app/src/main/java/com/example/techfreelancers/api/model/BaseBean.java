package com.example.techfreelancers.api.model;

public class BaseBean {
    /**
     * Timestamp when the user was created.
     */
    private String createTime;

    /**
     * Timestamp when the user was last updated.
     */
    private String updateTime;

    /**
     * Status of the user: 0 - inactive; 1 - active.
     */
    private String status;

    /**
     * Additional notes about the user.
     */
    private String note;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
