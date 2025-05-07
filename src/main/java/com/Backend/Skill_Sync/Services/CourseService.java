package com.Backend.Skill_Sync.Services;

import com.Backend.Skill_Sync.Model.Course;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class CourseService {

    private static final String COLLECTION_NAME = "Courses";

    public Course addCourse(Course course) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document();
        course.setId(docRef.getId());
        course.setCreatedAt(new Date()); // Save creation timestamp
        docRef.set(course).get();
        return course;
    }

    public Course getCourse(String courseId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentSnapshot document = db.collection(COLLECTION_NAME).document(courseId).get().get();
        return document.exists() ? document.toObject(Course.class) : null;
    }

    public List<Course> getAllFeaturedCourses() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        // Calculate the timestamp for 7 days ago
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date oneWeekAgo = calendar.getTime();

        // Query for documents with createdAt >= oneWeekAgo
        List<QueryDocumentSnapshot> documents = db.collection(COLLECTION_NAME)
                .whereGreaterThanOrEqualTo("createdAt", oneWeekAgo)
                .get()
                .get()
                .getDocuments();

        List<Course> courses = new ArrayList<>();
        for (QueryDocumentSnapshot doc : documents) {
            courses.add(doc.toObject(Course.class));
        }

        return courses;
    }

    public List<Course> getAllCourses() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        List<QueryDocumentSnapshot> documents = db.collection(COLLECTION_NAME).get().get().getDocuments();
        List<Course> courses = new ArrayList<>();
        for (QueryDocumentSnapshot doc : documents) {
            courses.add(doc.toObject(Course.class));
        }
        return courses;
    }

    public Course updateCourse(Course course) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        db.collection(COLLECTION_NAME).document(course.getId()).set(course, SetOptions.merge()).get();
        return course;
    }

    public String deleteCourse(String courseId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = db.collection(COLLECTION_NAME).document(courseId).delete();
        return "Deleted at: " + writeResult.get().getUpdateTime();
    }

    // ✅ Weekly Added Chart Data
    public int[] getWeeklyCourseAdditionStats() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        List<QueryDocumentSnapshot> documents = db.collection(COLLECTION_NAME).get().get().getDocuments();
        int[] weeklyAdded = new int[7]; // Monday=0

        Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        int currentYear = calendar.get(Calendar.YEAR);

        for (DocumentSnapshot doc : documents) {
            Course course = doc.toObject(Course.class);
            if (course.getCreatedAt() != null) {
                calendar.setTime(course.getCreatedAt());
                int courseWeek = calendar.get(Calendar.WEEK_OF_YEAR);
                int courseYear = calendar.get(Calendar.YEAR);

                if (courseWeek == currentWeek && courseYear == currentYear) {
                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // Sunday=1
                    int index = (dayOfWeek + 5) % 7; // Shift to Monday=0
                    weeklyAdded[index]++;
                }
            }
        }

        return weeklyAdded;
    }


    public int getCourseCount() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        return db.collection(COLLECTION_NAME).get().get().size();
    }

}
