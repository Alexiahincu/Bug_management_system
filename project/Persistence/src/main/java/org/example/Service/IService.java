package org.example.Service;


import org.example.Domain.Bug;
import org.example.Domain.Programmer;
import org.example.Domain.Tester;

import java.util.List;

public interface IService {
    Tester tLogin(String username, String password, IObserver iobs) throws Exception;
    Programmer pLogin(String username, String password, IObserver iobs) throws Exception;
    List<Bug> getAllBugs() throws Exception;
    public void AddBug(String name, String description) throws Exception;
    public void SolveBug(Integer id) throws Exception;
    //void logout(Angajat Angajat, IObserver client) throws Exception;
}
