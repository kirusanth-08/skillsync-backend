package com.Backend.Skill_Sync.Services;

import com.Backend.Skill_Sync.Dto.ProfileDTO;
import com.Backend.Skill_Sync.Model.User;
import com.Backend.Skill_Sync.Model.UserProfile;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class UserProfileService {
    private final Firestore firestore = FirestoreClient.getFirestore();
    private final String COLLECTION_NAME = "profiles";

    public UserProfile getProfileByUsername(String userName) throws ExecutionException, InterruptedException {
        DocumentSnapshot document = firestore.collection(COLLECTION_NAME).document(userName).get().get();

        return document.toObject(UserProfile.class);
    }

    public UserProfile createProfile(User user) throws ExecutionException, InterruptedException {
        String id = UUID.randomUUID().toString();

        // Create a new UserProfile object
        UserProfile userProfile = new UserProfile();
        // Set properties using direct field assignment instead of setters
        userProfile.setUsername(user.getUsername());
        userProfile.setEmail(user.getEmail());
        userProfile.setFirstName(user.getFirstName());
        userProfile.setLastName(user.getLastName());
        userProfile.setBio("--------Update your bio description-------");
        userProfile.setJoinedDate(new Date());

        // Save to Firestore
        firestore.collection(COLLECTION_NAME).document(user.getUsername()).set(userProfile);

        return userProfile;
    }

    public UserProfile updateProfile(String username, ProfileDTO profileDTO) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(username);
        DocumentSnapshot snapshot = docRef.get().get();

        if (snapshot.exists()) {
            Map<String, Object> updates = new HashMap<>();
            updates.put("bio", profileDTO.getBio());
            updates.put("firstName", profileDTO.getFirstName());
            updates.put("lastName", profileDTO.getLastName());

            docRef.update(updates);
            return getProfileByUsername(username);
        } else {
            return null;
        }
    }
}