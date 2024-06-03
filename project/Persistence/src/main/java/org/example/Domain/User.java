package org.example.Domain;

public abstract class User implements Entity<Integer> {
    protected Integer id;
    protected String name;
    protected String username;
    protected String password;
    protected String email;

    public User() {
    }

    public User(Integer id, String name, String username, String password, String email) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
