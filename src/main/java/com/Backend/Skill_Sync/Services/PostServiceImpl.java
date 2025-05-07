package com.Backend.Skill_Sync.Services;

import com.Backend.Skill_Sync.Model.Comment;
import com.Backend.Skill_Sync.Model.Post;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    public Post getPostByEmail(String userMail) {
        try {
            var documents = FirestoreClient.getFirestore()
                    .collection(COLLECTION_NAME)
                    .whereEqualTo("userMail", userMail)
                    .get()
                    .get()
                    .getDocuments();

            if (!documents.isEmpty()) {
                return documents.get(0).toObject(Post.class); // Return first post found
            } else {
                throw new RuntimeException("No posts found for email: " + userMail);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching post by email", e);
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

    @Override
    public void likePost(String postId) {
        Firestore db = FirestoreClient.getFirestore();
        var docRef = db.collection(COLLECTION_NAME).document(postId);
        try {
            Post post = docRef.get().get().toObject(Post.class);
            if (post != null) {
                post.setLikes(post.getLikes() + 1);
                docRef.set(post);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to like post", e);
        }
    }

    @Override
    public void addComment(String postId, Comment comment) {
        Firestore db = FirestoreClient.getFirestore();
        var docRef = db.collection(COLLECTION_NAME).document(postId);
        try {
            Post post = docRef.get().get().toObject(Post.class);
            if (post != null) {
                if (post.getComments() == null) {
                    post.setComments(new ArrayList<>());
                }
                comment.setTimestamp(new Date());
                post.getComments().add(comment);
                docRef.set(post);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to comment on post", e);
        }
    }

    @Override
    public List<Post> getTopPosts(int limit) {
        try {
            List<QueryDocumentSnapshot> documents = FirestoreClient.getFirestore()
                    .collection(COLLECTION_NAME)
                    .orderBy("likes", com.google.cloud.firestore.Query.Direction.DESCENDING)
                    .limit(limit)
                    .get()
                    .get()
                    .getDocuments();

            List<Post> posts = new ArrayList<>();
            for (QueryDocumentSnapshot doc : documents) {
                posts.add(doc.toObject(Post.class));
            }
            return posts;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving top posts", e);
        }
    }

    @Override
    public long getPostCount() {
        try {
            return FirestoreClient.getFirestore()
                    .collection(COLLECTION_NAME)
                    .get()
                    .get()
                    .getDocuments()
                    .size();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving post count", e);
        }
    }


}
