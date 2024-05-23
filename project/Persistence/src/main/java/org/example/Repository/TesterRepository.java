package org.example.Repository;

import org.example.Domain.Tester;

public interface TesterRepository extends Repository<Integer, Tester> {
    Tester getByCredentials(String username, String password);

}
