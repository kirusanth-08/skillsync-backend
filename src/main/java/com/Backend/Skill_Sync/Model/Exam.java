package com.Backend.Skill_Sync.Model;

public class Exam {
    private String id;
    private String name;
    private String level;
    private String dueDate;    // stored as "2025-04-04" in your screenshot
    private String status;
    private String avgTime;    // stored as "5h" (or any other text)
    private String formLink;   // may be null
    private String timeLimit;  // may be null

    public Exam() {}

    public Exam(String id, String name, String level, String dueDate,
                String status, String avgTime, String formLink,
                String timeLimit) {
        this.id        = id;
        this.name      = name;
        this.level     = level;
        this.dueDate   = dueDate;
        this.status    = status;
        this.avgTime   = avgTime;
        this.formLink  = formLink;
        this.timeLimit = timeLimit;
    }

    // —— getters & setters —— //

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

    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }

    public String getDueDate() {
        return dueDate;
    }
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvgTime() {
        return avgTime;
    }
    public void setAvgTime(String avgTime) {
        this.avgTime = avgTime;
    }

    public String getFormLink() {
        return formLink;
    }
    public void setFormLink(String formLink) {
        this.formLink = formLink;
    }

    public String getTimeLimit() {
        return timeLimit;
    }
    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }
}
