package com.Backend.Skill_Sync.Services;

import com.Backend.Skill_Sync.Model.Post;

import java.util.List;

public interface PostService {
    Post createPost(Post post);
    Post getPostById(String id);
    List<Post> getAllPosts();
    Post updatePost(String id, Post post);
    void deletePost(String id);
}
