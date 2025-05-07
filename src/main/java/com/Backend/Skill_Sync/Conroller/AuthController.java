package com.Backend.Skill_Sync.Conroller;

import com.Backend.Skill_Sync.Model.*;
import com.Backend.Skill_Sync.Services.*;
import com.Backend.Skill_Sync.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sign")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserProfileService userProfileService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            registrationService.registerUser(user);
            userProfileService.createProfile(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            User user = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
            String token = JwtUtil.generateToken(user.getUsername());

            // 🍪 Create cookie
            ResponseCookie jwtCookie = ResponseCookie.from("jwt", token)
                    .httpOnly(true)           // ⚠️ prevents JS access (secure)
                    .secure(false)            // Set to true in production (HTTPS)
                    .path("/")                // Send with all requests
                    .maxAge(24 * 60 * 60)     // 1 day
                    .sameSite("Lax")          // Avoids CSRF in most cases
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body("Login successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
        }
    }

}
