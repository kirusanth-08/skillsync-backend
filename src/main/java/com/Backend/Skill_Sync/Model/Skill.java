// src/main/java/com/Backend/Skill_Sync/Model/Skill.java
package com.Backend.Skill_Sync.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    private String id;
    private String skillName;
    private String proficiency_level;
    private Integer experience;
    private String user;
    
    // Constructors, getters, setters
}