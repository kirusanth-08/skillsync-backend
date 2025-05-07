package com.Backend.Skill_Sync.Conroller;

import com.Backend.Skill_Sync.Dto.ProfileDTO;
import com.Backend.Skill_Sync.Model.UserProfile;
import com.Backend.Skill_Sync.Services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/profiles")
@CrossOrigin(origins = "*")
public class ProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/{username}")
    public ResponseEntity<UserProfile> getProfileByUsername(@PathVariable("username") String userName) {
        try {
            UserProfile userProfile = userProfileService.getProfileByUsername(userName);
            return new ResponseEntity<>(userProfile, HttpStatus.OK);
        } catch (InterruptedException | ExecutionException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{username}")
    public ResponseEntity<UserProfile> updateProfileByUsername(@PathVariable("username") String userName, @RequestBody ProfileDTO profileDTO) {
        try {
            UserProfile userProfile = userProfileService.updateProfile(userName, profileDTO);
            return new ResponseEntity<>(userProfile, HttpStatus.OK);
        } catch (InterruptedException | ExecutionException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
