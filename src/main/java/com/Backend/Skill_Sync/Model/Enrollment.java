package com.Backend.Skill_Sync.Model;


import java.util.Date;

public class Enrollment {
    private String id;
    private String userEmail;  // changed from userId
    private String courseId;
    private Date enrolledAt;

    public Enrollment() {}

    public Enrollment(String id, String userEmail, String courseId, Date enrolledAt) {
        this.id = id;
        this.userEmail = userEmail;
        this.courseId = courseId;
        this.enrolledAt = enrolledAt;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }

    public Date getEnrolledAt() { return enrolledAt; }
    public void setEnrolledAt(Date enrolledAt) { this.enrolledAt = enrolledAt; }
}
