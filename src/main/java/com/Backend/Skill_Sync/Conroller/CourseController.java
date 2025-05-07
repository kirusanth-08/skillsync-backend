package com.Backend.Skill_Sync.Conroller;

import com.Backend.Skill_Sync.Dto.CourseEnrollmentDTO;
import com.Backend.Skill_Sync.Model.Course;
import com.Backend.Skill_Sync.Model.Enrollment;
import com.Backend.Skill_Sync.Services.CourseService;
import com.Backend.Skill_Sync.Services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/course")
@CrossOrigin(origins = "*")
public class CourseController {
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private CourseService courseService;

    @PostMapping
    public Course addCourse(@RequestBody Course course) throws ExecutionException, InterruptedException {
        return courseService.addCourse(course);
    }

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable("id") String courseId) throws ExecutionException, InterruptedException {
        return courseService.getCourse(courseId);
    }

    @GetMapping("/featured")
    public List<Course> getAllFeaturedCourses() throws ExecutionException, InterruptedException {
        return courseService.getAllFeaturedCourses();
    }

    @GetMapping
    public List<Course> getAllCourses() throws ExecutionException, InterruptedException {
        return courseService.getAllCourses();
    }

    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable("id") String courseId, @RequestBody Course course)
            throws ExecutionException, InterruptedException {
        course.setId(courseId);
        return courseService.updateCourse(course);
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable("id") String courseId) throws ExecutionException, InterruptedException {
        return courseService.deleteCourse(courseId);
    }

    // ✅ Weekly courses added stats
    @GetMapping("/weekly-added")
    public ResponseEntity<int[]> getWeeklyAddedStats() throws ExecutionException, InterruptedException {
        int[] weeklyData = courseService.getWeeklyCourseAdditionStats();
        return ResponseEntity.ok(weeklyData);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCourseCount() {
        try {
            int count = courseService.getCourseCount();
            return ResponseEntity.ok(count);
        } catch (InterruptedException | ExecutionException e) {
            return ResponseEntity.status(500).build();
        }
    }





    @GetMapping("/with-enrollments")
    public ResponseEntity<List<CourseEnrollmentDTO>> getCoursesWithEnrollments() throws ExecutionException, InterruptedException {
        List<Course> courses = courseService.getAllCourses();
        List<CourseEnrollmentDTO> response = new ArrayList<>();

        for (Course course : courses) {
            List<Enrollment> enrollments = enrollmentService.getEnrollmentsByCourseId(course.getId());
            CourseEnrollmentDTO dto = new CourseEnrollmentDTO(
                    course.getName(),
                    course.getCreatedAt(),
                    enrollments
            );
            response.add(dto);
        }

        return ResponseEntity.ok(response);
    }


}
