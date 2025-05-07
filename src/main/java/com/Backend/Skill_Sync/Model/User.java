package com.Backend.Skill_Sync.Model;

public class User {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String resetPassword;

    public User() {}

    public User(String username, String email, String password, String firstName, String lastName, String resetPassword) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.resetPassword = resetPassword;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getResetPassword() { return resetPassword; }
    public void setResetPassword(String resetPassword) { this.resetPassword = resetPassword; }
}
