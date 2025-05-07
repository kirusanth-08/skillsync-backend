//package com.Backend.Skill_Sync.Services;
//import com.Backend.Skill_Sync.Model.User;
//import com.google.api.core.ApiFuture;
//import com.google.cloud.firestore.Firestore;
//import com.google.cloud.firestore.WriteResult;
//import com.google.firebase.cloud.FirestoreClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class RegistrationServiceImpli implements RegistrationService{
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public void registerUser(User user) throws Exception {
//        Firestore db = FirestoreClient.getFirestore();
//
//        // ✅ Hash password before saving
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//        ApiFuture<WriteResult> future = db.collection("users")
//                .document(user.getUsername())
//                .set(user);
//        future.get();
//    }
//}



package com.Backend.Skill_Sync.Services;

import com.Backend.Skill_Sync.Model.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegistrationServiceImpli implements RegistrationService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String COLLECTION_NAME = "users";

    @Override
    public void registerUser(User user) throws Exception {
        Firestore db = FirestoreClient.getFirestore();

        // ✅ Hash password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        ApiFuture<WriteResult> future = db.collection(COLLECTION_NAME)
                .document(user.getUsername())
                .set(user);
        future.get(); // Wait for the write to complete
    }

    // ✅ New method to get all registered users
    public List<User> getAllUsers() throws Exception {
        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<User> users = new ArrayList<>();
        for (QueryDocumentSnapshot doc : documents) {
            users.add(doc.toObject(User.class));
        }
        return users;
    }
}
