package com.Backend.Skill_Sync.Dto;

import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
public class ProfileDTO {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String bio;
    private Date JoinedDate;

}
