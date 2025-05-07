package com.Backend.Skill_Sync.Services;

import com.Backend.Skill_Sync.Model.Enrollment;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class EnrollmentService {

    private static final String COLLECTION_NAME = "enrollments";

    public Enrollment enrollStudent(String userEmail, String courseId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        // Check if the enrollment already exists
        List<QueryDocumentSnapshot> existingEnrollments = db.collection(COLLECTION_NAME)
                .whereEqualTo("userEmail", userEmail)
                .whereEqualTo("courseId", courseId)
                .get()
                .get()
                .getDocuments();

        if (!existingEnrollments.isEmpty()) {
            // Enrollment already exists, return the existing one
            return existingEnrollments.get(0).toObject(Enrollment.class);
        }

        // Proceed with new enrollment
        DocumentReference docRef = db.collection(COLLECTION_NAME).document();
        Enrollment enrollment = new Enrollment(docRef.getId(), userEmail, courseId, new Date());
        docRef.set(enrollment).get();
        return enrollment;
    }


    public List<Enrollment> getEnrollmentsByUserEmail(String userEmail) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        List<QueryDocumentSnapshot> documents = db.collection(COLLECTION_NAME)
                .whereEqualTo("userEmail", userEmail)
                .get()
                .get()
                .getDocuments();

        List<Enrollment> enrollments = new ArrayList<>();
        for (QueryDocumentSnapshot doc : documents) {
            enrollments.add(doc.toObject(Enrollment.class));
        }
        return enrollments;
    }

    public List<Enrollment> getAllEnrollments() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        List<QueryDocumentSnapshot> documents = db.collection("enrollments").get().get().getDocuments();

        List<Enrollment> enrollments = new ArrayList<>();
        for (QueryDocumentSnapshot doc : documents) {
            enrollments.add(doc.toObject(Enrollment.class));
        }
        return enrollments;
    }

    public long countEnrollmentsByCourseId(String courseId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        List<QueryDocumentSnapshot> documents = db.collection(COLLECTION_NAME)
                .whereEqualTo("courseId", courseId)
                .get()
                .get()
                .getDocuments();
        return documents.size();  // return the count
    }




    public List<Enrollment> getEnrollmentsByCourseId(String courseId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        List<QueryDocumentSnapshot> documents = db.collection(COLLECTION_NAME)
                .whereEqualTo("courseId", courseId)
                .get()
                .get()
                .getDocuments();

        List<Enrollment> enrollments = new ArrayList<>();
        for (QueryDocumentSnapshot doc : documents) {
            enrollments.add(doc.toObject(Enrollment.class));
        }
        return enrollments;
    }

}
