package com.Backend.Skill_Sync.Services;

import com.Backend.Skill_Sync.Model.Post;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    private static final String COLLECTION_NAME = "posts";

    @Override
    public Post createPost(Post post) {
        Firestore db = FirestoreClient.getFirestore();
        post.setId(UUID.randomUUID().toString());
        db.collection(COLLECTION_NAME).document(post.getId()).set(post);
        return post;
    }

    @Override
    public Post getPostById(String id) {
        try {
            return FirestoreClient.getFirestore()
                    .collection(COLLECTION_NAME)
                    .document(id)
                    .get()
                    .get()
                    .toObject(Post.class);
        } catch (Exception e) {
            throw new RuntimeException("Post not found with ID: " + id);
        }
    }

    @Override
    public List<Post> getAllPosts() {
        try {
            List<QueryDocumentSnapshot> documents = FirestoreClient.getFirestore()
                    .collection(COLLECTION_NAME)
                    .get()
                    .get()
                    .getDocuments();

            List<Post> posts = new ArrayList<>();
            for (QueryDocumentSnapshot doc : documents) {
                posts.add(doc.toObject(Post.class));
            }
            return posts;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving posts", e);
        }
    }

    @Override
    public Post updatePost(String id, Post post) {
        post.setId(id); // Ensure the ID remains consistent
        FirestoreClient.getFirestore().collection(COLLECTION_NAME).document(id).set(post);
        return post;
    }

    @Override
    public void deletePost(String id) {
        FirestoreClient.getFirestore().collection(COLLECTION_NAME).document(id).delete();
    }
}
