package com.Backend.Skill_Sync.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private String userMail;
    private String content;
    private List<Comment> comments;
    private int likes;
    private Date createdAt;
}
