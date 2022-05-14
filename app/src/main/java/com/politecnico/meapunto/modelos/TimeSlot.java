package com.politecnico.meapunto.modelos;

public class TimeSlot {
    private int id;
    private String Description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public TimeSlot(int id, String description) {
        this.id = id;
        Description = description;
    }
}
