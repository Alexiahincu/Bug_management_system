package org.example.Service;


import org.example.Domain.Bug;
import org.example.Domain.BugStatus;
import org.example.Domain.Programmer;
import org.example.Domain.Tester;
import org.example.Repository.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service implements IService {

    TesterRepository testerRepository;
    ProgrammerRepository programmerRepository;
    BugRepository bugRepository;

    //private Map<String, IObserver> loggedClients = new HashMap<>();
    //ExecutorService executorService = Executors.newFixedThreadPool(5);


    public Service(TesterRepository testerRepository, ProgrammerRepository programmerRepository, BugRepository bugDBRepository) {
        this.testerRepository = testerRepository;
        this.programmerRepository = programmerRepository;
        this.bugRepository = bugDBRepository;
    }

    public Tester tLogin(String username, String password, IObserver iobs){
        Tester tester = testerRepository.getByCredentials(username, password);
        if(tester != null) {
            //loggedClients.put(angajat.getUsername(), iobs);
            return tester;
        }
        return null;
    }

    public Programmer pLogin(String username, String password, IObserver iobs){
        Programmer programmer = programmerRepository.getByCredentials(username, password);
        if(programmer != null) {
            //loggedClients.put(angajat.getUsername(), iobs);
            return programmer;
        }
        return null;
    }

    public List<Bug> getAllBugs(){
        return bugRepository.getAll();
    }

    @Override
    public void SolveBug(Integer id){
        Bug oldb = BugDBRepository.find(id);
        Bug newb = new Bug(id, oldb.getName(), oldb.getDescription(), BugStatus.SOLVED);
        bugRepository.update(id, newb);

        //        executorService.submit(() -> {
//            for (IObserver client : loggedClients.values()) {
//                client.update();
//            }
//        });

    }

    @Override
    public void AddBug(String name, String desc) {

        int id = bugRepository.getAll().size() + 1;

        Bug bug = new Bug(id, name, desc, BugStatus.PENDING);
        bugRepository.add(bug);

//        executorService.submit(() -> {
//            for (IObserver client : loggedClients.values()) {
//                client.update();
//            }
//        });

    }

//    @Override
//    public void logout(Angajat Angajat, IObserver client) throws Exception {}

}
