package org.example.Repository;

import org.example.Domain.Tester;
import org.hibernate.Session;

import java.util.List;
import java.util.Objects;

public class TesterHibernateRepository implements TesterRepository{
    @Override
    public void add(Tester tester) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(tester));
    }

//    public Tester findOne(Integer id) {
//        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
//        return session.createSelectionQuery("from Tester where id=:idM ", Tester.class)
//                .setParameter("idM", id)
//                .getSingleResultOrNull();
//      }
//    }

    @Override
    public List<Tester> getAll() {
       try( Session session=HibernateUtils.getSessionFactory().openSession()) {
           return session.createQuery("from Tester ", Tester.class).getResultList();
       }
    }

    @Override
    public Tester getByCredentials(String username, String password) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from Tester a where a.username like: usern and a.password like: passw ", Tester.class)
                    .setParameter("usern", username)
                    .setParameter("passw", password)
                    .getSingleResultOrNull();
        }
    }

}
