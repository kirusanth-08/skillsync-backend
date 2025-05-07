package com.Backend.Skill_Sync.Conroller;

import com.Backend.Skill_Sync.Model.ResetPasswordRequest;
import com.Backend.Skill_Sync.Model.PasswordResetToken;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/router")
@CrossOrigin(origins = "*")
public class PasswordResetController {

    private static final String USER_COLLECTION = "users";
    private static final String TOKEN_COLLECTION = "password_tokens";

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        // Step 1: Validate token
        DocumentSnapshot tokenDoc = db.collection(TOKEN_COLLECTION).document(request.getToken()).get().get();
        if (!tokenDoc.exists()) {
            return ResponseEntity.status(400).body("Invalid or expired token.");
        }

        PasswordResetToken token = tokenDoc.toObject(PasswordResetToken.class);
        if (token == null || !token.getEmail().equals(request.getEmail())) {
            return ResponseEntity.status(400).body("Invalid token or email mismatch.");
        }

        if (token.getExpiryDate().before(new Date())) {
            return ResponseEntity.status(400).body("Token has expired.");
        }

        // Step 2: Update password (hashed)
        ApiFuture<QuerySnapshot> userQuery = db.collection(USER_COLLECTION)
                .whereEqualTo("email", request.getEmail())
                .get();

        QuerySnapshot userSnap = userQuery.get();
        if (userSnap.isEmpty()) {
            return ResponseEntity.status(404).body("User not found.");
        }

        DocumentReference userRef = userSnap.getDocuments().get(0).getReference();
        String hashedPassword = passwordEncoder.encode(request.getNewPassword());

        userRef.update("password", hashedPassword);

        // Step 3: Remove token after use
        db.collection(TOKEN_COLLECTION).document(request.getToken()).delete();

        return ResponseEntity.ok("Password updated successfully.");
    }
}
