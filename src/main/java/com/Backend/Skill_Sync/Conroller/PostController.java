package com.Backend.Skill_Sync.Conroller;

import com.Backend.Skill_Sync.Model.Comment;
import com.Backend.Skill_Sync.Model.Post;
import com.Backend.Skill_Sync.Services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody Post post) {
        postService.createPost(post);
        return ResponseEntity.ok("Post successfully created");
    }


    @GetMapping("/allPost")
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }


    @GetMapping("/user")
    public ResponseEntity<Post> getPostByEmail(@RequestParam String email) {
        return ResponseEntity.ok(postService.getPostByEmail(email));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody Post post) {
        return ResponseEntity.ok(postService.updatePost(id, post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/like")
    public ResponseEntity<Void> likePost(@PathVariable String id) {
        postService.likePost(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Void> addComment(@PathVariable String id, @RequestBody Comment comment) {
        postService.addComment(id, comment);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/top")
    public ResponseEntity<List<Post>> getTopPosts(@RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(postService.getTopPosts(limit));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getPostCount() {
        return ResponseEntity.ok(postService.getPostCount());
    }


}
