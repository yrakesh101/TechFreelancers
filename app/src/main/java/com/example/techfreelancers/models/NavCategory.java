package com.example.techfreelancers.models;

public class NavCategory {
    private String name;
    private int iconResourceId;
    private String id;

    public NavCategory(String id, String name, int iconResourceId) {
        this.id = id;
        this.name = name;
        this.iconResourceId = iconResourceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }

    public void setIconResourceId(int iconResourceId) {
        this.iconResourceId = iconResourceId;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", iconResourceId=" + iconResourceId +
                '}';
    }
}