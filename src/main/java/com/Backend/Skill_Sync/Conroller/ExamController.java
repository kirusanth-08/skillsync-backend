package com.Backend.Skill_Sync.Conroller;

import com.Backend.Skill_Sync.Model.Exam;
import com.Backend.Skill_Sync.Services.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/exams")
@CrossOrigin(origins = "*")
public class ExamController {

    @Autowired
    private ExamService examService;

    // GET all exams + auto‑hide expired ones
    @GetMapping
    public List<Exam> getAllExams() throws ExecutionException, InterruptedException {
        List<Exam> exams = examService.getAllExams();
        LocalDate today = LocalDate.now();

        for (Exam exam : exams) {
            String dueStr = exam.getDueDate();             // e.g. "2025-04-04"
            if (dueStr != null && "Active".equalsIgnoreCase(exam.getStatus())) {
                try {
                    LocalDate dueDate = LocalDate.parse(dueStr);
                    if (dueDate.isBefore(today)) {
                        exam.setStatus("Hidden");
                        examService.updateExam(exam.getId(), exam);
                    }
                } catch (DateTimeParseException e) {
                    System.err.println("⚠️ Invalid dueDate for exam "
                            + exam.getId() + ": " + dueStr);
                }
            }
        }

        return exams;
    }

    // GET an exam by ID
    @GetMapping("/{id}")
    public ResponseEntity<Exam> getExam(@PathVariable("id") String id) {
        try {
            Exam exam = examService.getExam(id);
            if (exam == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(exam);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // POST a new exam
    @PostMapping
    public ResponseEntity<Exam> addExam(@RequestBody Exam exam) {
        try {
            Exam saved = examService.addExam(exam);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // PUT (update) an existing exam
    @PutMapping("/{id}")
    public ResponseEntity<Exam> updateExam(
            @PathVariable("id") String id,
            @RequestBody Exam exam
    ) {
        try {
            Exam updated = examService.updateExam(id, exam);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DELETE an exam by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExam(@PathVariable("id") String id) {
        try {
            String msg = examService.deleteExam(id);
            return ResponseEntity.ok(msg);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting exam");
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getExamCount() {
        try {
            int count = examService.getExamCount();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
