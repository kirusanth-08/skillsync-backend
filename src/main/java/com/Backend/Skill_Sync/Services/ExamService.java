package com.Backend.Skill_Sync.Services;

import com.Backend.Skill_Sync.Model.Exam;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class ExamService {
    private static final String COLLECTION_NAME = "Exams";

    public List<Exam> getAllExams() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        List<Exam> exams = new ArrayList<>();

        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
        for (DocumentSnapshot doc : future.get().getDocuments()) {
            try {
                Exam exam = doc.toObject(Exam.class);
                if (exam != null) {
                    exam.setId(doc.getId());
                    exams.add(exam);
                }
            } catch (Exception e) {
                System.err.println("🔥 Error mapping Firestore document: " + doc.getId());
                e.printStackTrace();
            }
        }
        return exams;
    }

    public Exam getExam(String id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentSnapshot document = db
                .collection(COLLECTION_NAME)
                .document(id)
                .get()
                .get();

        if (!document.exists()) {
            return null;
        }
        Exam exam = document.toObject(Exam.class);
        if (exam != null) exam.setId(document.getId());
        return exam;
    }

    public Exam addExam(Exam exam) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document();
        exam.setId(docRef.getId());
        docRef.set(exam).get();
        return exam;
    }

    public Exam updateExam(String id, Exam exam) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        exam.setId(id);
        db.collection(COLLECTION_NAME).document(id).set(exam).get();
        return exam;
    }

    public String deleteExam(String id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        db.collection(COLLECTION_NAME).document(id).delete().get();
        return "Deleted";
    }
}
