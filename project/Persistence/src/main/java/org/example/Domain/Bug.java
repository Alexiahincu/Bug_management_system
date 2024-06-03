package org.example.Domain;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@jakarta.persistence.Entity
@Table( name = "Bugs" )
public class Bug implements Entity<Integer>, Serializable {
    private Integer id;
    private String name;
    private String description;
    private BugStatus status;
    @Serial
    private static final long serialVersionUID = 100L;

    public Bug() {
    }

    public Bug(Integer id, String name, String description, BugStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    @Override
    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    @Column(name = "status")
    @Convert(converter = BugStatusConverter.class)
    public BugStatus getStatus() {
        return status;
    }

    public void setStatus(BugStatus status) {
        this.status = status;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString(){
        return "Bug { id: " + id
                + ", name:" + name
                + ", description: " + description
                + ", status: " + status.toString()
                + " }";
    }
}
