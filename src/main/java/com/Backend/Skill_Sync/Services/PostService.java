package com.Backend.Skill_Sync.Services;

import com.Backend.Skill_Sync.Model.Comment;
import com.Backend.Skill_Sync.Model.Post;

import java.util.List;

public interface PostService {
    Post createPost(Post post);
    Post getPostByEmail(String userMail);
    List<Post> getAllPosts();
    Post updatePost(String id, Post post);
    void deletePost(String id);
    void likePost(String postId);
    void addComment(String postId, Comment comment);
    List<Post> getTopPosts(int limit); // For analytics
    long getPostCount();

}
