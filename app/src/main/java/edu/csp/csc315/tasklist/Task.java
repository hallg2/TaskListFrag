package edu.csp.csc315.tasklist;

import java.util.Date;

public class Task {
    private Integer id;
    private String description;
    private Date dueDate;

    public Task(int id, String description, Date dueDate) {
        this.id = id;
        this.description = description;
        this.dueDate = dueDate;
    }

    public int getId() { return id; }
    public String getDescription() { return description; }
    public Date getDueDate() { return dueDate; }

    public void setId() {
        this.id = id;
    }
    public void setDescription() {
        this.description = description;
    }
    public void setDueDate() {
        this.dueDate = dueDate;
    }
}
