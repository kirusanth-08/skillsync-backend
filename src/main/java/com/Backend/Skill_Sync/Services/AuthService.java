package com.Backend.Skill_Sync.Services;

import com.Backend.Skill_Sync.Model.User;

public interface AuthService {
    User authenticate(String username, String password) throws Exception;
}
