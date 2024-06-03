package org.example.Repository;

import org.example.Domain.Entity;

import java.util.List;

public interface Repository<ID, T extends Entity<ID>> {
    void add(T elem);
    List<T> getAll();
}
