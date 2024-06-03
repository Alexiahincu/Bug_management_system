package org.example.Repository;

import org.example.Domain.Programmer;
import org.hibernate.Session;

import java.util.List;

public class ProgrammerHibernateRepository implements ProgrammerRepository{
    @Override
    public void add(Programmer programmer) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(programmer));
    }

//    public Programmer findOne(Integer id) {
//        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
//        return session.createSelectionQuery("from Programmer where id=:idM ", Programmer.class)
//                .setParameter("idM", id)
//                .getSingleResultOrNull();
//      }
//    }

    @Override
    public List<Programmer> getAll() {
       try( Session session=HibernateUtils.getSessionFactory().openSession()) {
           return session.createQuery("from Programmer ", Programmer.class).getResultList();
       }
    }

    @Override
    public Programmer getByCredentials(String username, String password) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from Programmer a where a.username like: usern and a.password like: passw ", Programmer.class)
                    .setParameter("usern", username)
                    .setParameter("passw", password)
                    .getSingleResultOrNull();
        }
    }

}
