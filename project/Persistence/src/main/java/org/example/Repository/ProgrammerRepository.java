package org.example.Repository;

import org.example.Domain.Programmer;

public interface ProgrammerRepository extends Repository<Integer, Programmer> {
    Programmer getByCredentials(String username, String password);

}
