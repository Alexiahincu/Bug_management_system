package org.example.Domain;


public interface Entity<ID> {
    void setId(ID id);
    ID getId();
}
