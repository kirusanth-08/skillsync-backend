package com.Backend.Skill_Sync.Dto;


import com.Backend.Skill_Sync.Model.Enrollment;
import java.util.Date;
import java.util.List;

public class CourseEnrollmentDTO {
    private String courseName;
    private Date createdAt;
    private List<Enrollment> enrollments;

    // Constructors
    public CourseEnrollmentDTO() {}

    public CourseEnrollmentDTO(String courseName, Date createdAt, List<Enrollment> enrollments) {
        this.courseName = courseName;
        this.createdAt = createdAt;
        this.enrollments = enrollments;
    }

    // Getters and Setters
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }
}
