package com.Backend.Skill_Sync.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String bio;
    private Date JoinedDate;

}
