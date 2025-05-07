package com.Backend.Skill_Sync.Conroller;

import com.Backend.Skill_Sync.Model.Enrollment;
import com.Backend.Skill_Sync.Services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/enroll")
@CrossOrigin(origins = "*")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    //  Enroll a student in a course
    @PostMapping("/enrolled")
    public Enrollment enrollStudent(@RequestParam String userEmail, @RequestParam String courseId) throws ExecutionException, InterruptedException {
        return enrollmentService.enrollStudent(userEmail, courseId);
    }

    //  Get all enrollments by user email
    @GetMapping("/enrollments/{userEmail}")
    public List<Enrollment> getEnrollmentsByUser(@PathVariable String userEmail) throws ExecutionException, InterruptedException {
        return enrollmentService.getEnrollmentsByUserEmail(userEmail);
    }


    // Get all enrollments (for analytics, admin, etc.)
    @GetMapping("/enrollments")
    public List<Enrollment> getAllEnrollments() throws ExecutionException, InterruptedException {
        return enrollmentService.getAllEnrollments();
    }

}
