package org.example.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "Testers")
public class Tester extends User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1000L;
    public Tester() {
    }

    public Tester(Integer id, String name, String username, String password, String email) {
        super(id, name, username, password, email);
    }

    @Override
    public void setId(Integer integer) {
        this.id = id;
    }

    @Override
    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Override
    @Column(name = "name")
    public String getName() {
        return super.getName();
    }

    @Override
    @Column(name = "username")
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    @Column(name = "password")
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    @Column(name = "email")
    public String getEmail() {
        return super.getEmail();
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
