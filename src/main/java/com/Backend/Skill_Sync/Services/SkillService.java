package com.Backend.Skill_Sync.Services;

import com.Backend.Skill_Sync.Dto.SkillDTO;
import com.Backend.Skill_Sync.Model.Skill;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.*;

@Service
public class SkillService {

    private final Firestore firestore = FirestoreClient.getFirestore();
    private final String COLLECTION_NAME = "skills";

    public List<Skill> getAllSkills() throws ExecutionException, InterruptedException {
        List<Skill> skillList = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        for (QueryDocumentSnapshot document : documents) {
            Skill skill = document.toObject(Skill.class);
            skill.setId(document.getId());
            skillList.add(skill);
        }

        return skillList;
    }

    public List<Skill> getAllSkillsByUser(String userName) throws ExecutionException, InterruptedException {
        List<Skill> skillList = new ArrayList<>();

        // Add filter: where user == userId
        ApiFuture<QuerySnapshot> future = firestore
                .collection(COLLECTION_NAME)
                .whereEqualTo("user", userName)
                .get();

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        for (QueryDocumentSnapshot document : documents) {
            Skill skill = document.toObject(Skill.class);
            skill.setId(document.getId());
            skillList.add(skill);
        }

        return skillList;
    }


    public List<Skill> getSkillsByCategory(String categoryId) throws ExecutionException, InterruptedException {
        List<Skill> skillList = new ArrayList<>();

        // Create a query to filter by categoryId
        Query query = firestore.collection(COLLECTION_NAME).whereEqualTo("categoryId", categoryId);
        ApiFuture<QuerySnapshot> future = query.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        for (QueryDocumentSnapshot document : documents) {
            Skill skill = document.toObject(Skill.class);
            skill.setId(document.getId());
            skillList.add(skill);
        }

        return skillList;
    }

    public List<Skill> getSkillsByFilters(String categoryId, String searchText, String sortBy)
            throws ExecutionException, InterruptedException {
        // Start with a reference to the collection
        CollectionReference skillsRef = firestore.collection(COLLECTION_NAME);

        // Build the query based on filters
        Query query = skillsRef;

        // Apply category filter if provided
        if (categoryId != null && !categoryId.isEmpty()) {
            query = query.whereEqualTo("categoryId", categoryId);
        }

        // Apply search filter if provided
        // Note: This is a simple prefix search
        if (searchText != null && !searchText.isEmpty()) {
            // FireStore doesn't support native full-text search
            // This is a simple approach using range queries
            query = query.orderBy("title")
                    .startAt(searchText)
                    .endAt(searchText + "\uf8ff");
        }

        // Apply sorting
        if ("rating".equals(sortBy)) {
            query = query.orderBy("rating", Query.Direction.DESCENDING);
        } else if ("popular".equals(sortBy)) {
            query = query.orderBy("popularity", Query.Direction.DESCENDING);
        } else {
            // Default to newest
            query = query.orderBy("createdAt", Query.Direction.DESCENDING);
        }

        // Execute the query
        ApiFuture<QuerySnapshot> future = query.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        // Convert the documents to Skill objects
        List<Skill> skillList = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            Skill skill = document.toObject(Skill.class);
            skill.setId(document.getId());
            skillList.add(skill);
        }

        return skillList;
    }

    public Skill createSkill(SkillDTO skillDTO) throws ExecutionException, InterruptedException {
        // Generate a new ID
        String id = UUID.randomUUID().toString();

        // Create a new Skill object
        Skill skill = new Skill();
        skill.setId(id);
        skill.setSkillName(skillDTO.getSkillName());
        skill.setProficiency_level(skillDTO.getProficiency_level());
        skill.setExperience(skillDTO.getExperience());
        skill.setUser(skillDTO.getUser());

        // Save to Firestore
        firestore.collection(COLLECTION_NAME).document(id).set(skill);

        return skill;
    }
    // Get a skill by ID
    public Skill getSkillById(String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(id);
        DocumentSnapshot snapshot = docRef.get().get();
        if (snapshot.exists()) {
            Skill skill = snapshot.toObject(Skill.class);
            if (skill != null) skill.setId(snapshot.getId());
            return skill;
        }
        return null;
    }

    // Update a skill by ID
    public Skill updateSkill(String id, SkillDTO skillDTO) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(id);
        DocumentSnapshot snapshot = docRef.get().get();

        if (snapshot.exists()) {
            Map<String, Object> updates = new HashMap<>();
            updates.put("skillName", skillDTO.getSkillName());
            updates.put("proficiency_level", skillDTO.getProficiency_level());
            updates.put("experience", skillDTO.getExperience());
            updates.put("user", skillDTO.getUser());

            docRef.update(updates);
            return getSkillById(id);
        } else {
            return null;
        }
    }

    // Delete a skill by ID
    public boolean deleteSkill(String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(id);
        DocumentSnapshot snapshot = docRef.get().get();

        if (snapshot.exists()) {
            docRef.delete();
            return true;
        }
        return false;
    }
}