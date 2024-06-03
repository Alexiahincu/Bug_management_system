package org.example.Repository;

import org.example.Domain.Bug;

public interface BugRepository extends Repository<Integer, Bug> {
    void update(Integer id, Bug elem);
    void delete(Integer id);
}
