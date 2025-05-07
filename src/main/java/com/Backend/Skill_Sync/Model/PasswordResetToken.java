package com.Backend.Skill_Sync.Model;

import java.util.Date;

public class PasswordResetToken {
    private String email;
    private String token;
    private Date expiryDate;

    public PasswordResetToken() {}

    public PasswordResetToken(String email, String token, Date expiryDate) {
        this.email = email;
        this.token = token;
        this.expiryDate = expiryDate;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }
}
