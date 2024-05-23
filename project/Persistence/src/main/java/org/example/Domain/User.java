package org.example.Domain;

public abstract class User extends Entity<Integer> {
    private String name;
    private String username;
    private String password;
    private String email;

    public User(Integer id, String name, String username, String password, String email) {
        super(id);
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
