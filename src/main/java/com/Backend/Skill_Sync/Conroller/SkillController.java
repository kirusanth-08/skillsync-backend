package com.Backend.Skill_Sync.Conroller;

import com.Backend.Skill_Sync.Dto.SkillDTO;
import com.Backend.Skill_Sync.Model.Skill;
import com.Backend.Skill_Sync.Services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin(origins = "http://localhost:5173")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @GetMapping
    public ResponseEntity<List<Skill>> getAllSkills() {
        try {
            List<Skill> skills = skillService.getAllSkills();
            return new ResponseEntity<>(skills, HttpStatus.OK);
        } catch (InterruptedException | ExecutionException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Skill> createSkill(@RequestBody SkillDTO skillDTO) {
        try {
            Skill createdSkill = skillService.createSkill(skillDTO);
            return new ResponseEntity<>(createdSkill, HttpStatus.CREATED);
        } catch (InterruptedException | ExecutionException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET skill by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getSkillById(@PathVariable String id) {
        try {
            Skill skill = skillService.getSkillById(id);
            if (skill != null) {
                return ResponseEntity.ok(skill);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/user")
    public ResponseEntity<List<Skill>> getSkillsByUser(@RequestParam String user) {
        try {
            List<Skill> skills = skillService.getAllSkillsByUser(user);
            return new ResponseEntity<>(skills, HttpStatus.OK);
        } catch (InterruptedException | ExecutionException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT (update) skill by ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSkill(@PathVariable String id, @RequestBody SkillDTO skillDTO) {
        try {
            Skill updated = skillService.updateSkill(id, skillDTO);
            if (updated != null) {
                return ResponseEntity.ok(updated);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    // DELETE skill by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSkill(@PathVariable String id) {
        try {
            boolean deleted = skillService.deleteSkill(id);
            if (deleted) {
                return ResponseEntity.ok("Skill deleted successfully.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}