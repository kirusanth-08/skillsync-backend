package com.Backend.Skill_Sync.Model;

public class LoginResponse {
    private String token;

    public LoginResponse() {
    }

    public LoginResponse(String token) {
        this.token = token;
    }

    // 👉 Getter
    public String getToken() {
        return token;
    }

    // 👉 Setter
    public void setToken(String token) {
        this.token = token;
    }
}
