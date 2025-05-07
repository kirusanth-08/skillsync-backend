package com.Backend.Skill_Sync.Dto;

import lombok.Data;

@Data
public class SkillDTO {
    private String skillName;
    private String proficiency_level;
    private Integer experience;
    private String user;

    // Default constructor
    public SkillDTO() {}

    // All-args constructor
    public SkillDTO(String skillName, String proficiency_level, Integer experience, String user) {
        this.skillName = skillName;
        this.proficiency_level = proficiency_level;
        this.experience = experience;
        this.user = user;
    }

    // Getters & Setters
}
