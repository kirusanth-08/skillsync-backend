package com.Backend.Skill_Sync.Conroller;

import com.Backend.Skill_Sync.Model.Course;
import com.Backend.Skill_Sync.Services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/course")
@CrossOrigin(origins = "*")
public class CourseController {

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

}
