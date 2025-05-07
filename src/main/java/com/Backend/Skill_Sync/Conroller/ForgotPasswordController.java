package com.Backend.Skill_Sync.Conroller;

import com.Backend.Skill_Sync.Model.PasswordResetToken;
import com.Backend.Skill_Sync.Services.EmailService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/router")
@CrossOrigin(origins = "*")
public class ForgotPasswordController {

    private static final String USER_COLLECTION = "users";
    private static final String TOKEN_COLLECTION = "password_tokens";

    @Autowired
    private EmailService emailService; // ✅ Inject EmailService

    @PostMapping("/request-password-reset")
    public ResponseEntity<?> requestReset(@RequestParam String email) throws Exception {
        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> future = db.collection(USER_COLLECTION)
                .whereEqualTo("email", email)
                .get();
        QuerySnapshot snapshot = future.get();

        if (snapshot.isEmpty()) {
            return ResponseEntity.status(404).body("User with this email does not exist.");
        }

        // ✅ Generate token
        String token = UUID.randomUUID().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 15);
        Date expiry = calendar.getTime();

        PasswordResetToken resetToken = new PasswordResetToken(email, token, expiry);
        db.collection(TOKEN_COLLECTION).document(token).set(resetToken).get();

        // ✅ Send the token via email
        emailService.sendResetToken(email, token);

        return ResponseEntity.ok("Reset token has been sent to your email.");
    }
}
