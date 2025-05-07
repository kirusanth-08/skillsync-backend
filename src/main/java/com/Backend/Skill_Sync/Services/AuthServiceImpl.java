package com.Backend.Skill_Sync.Services;

import com.Backend.Skill_Sync.Model.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User authenticate(String username, String password) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        DocumentSnapshot document = db.collection("users").document(username).get().get();

        if (!document.exists()) {
            throw new Exception("User not found");
        }

        User user = document.toObject(User.class);

        System.out.println("Entered password: " + password);
        System.out.println("Stored password hash: " + user.getPassword());

        // 🧠 This is the KEY line
        if (!(password.equals(user.getResetPassword()))){
            throw new Exception("Invalid credentials");
        }

        return user;
    }
}

