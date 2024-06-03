package org.example.Repository;

import org.example.Domain.Bug;
import org.hibernate.Session;

import java.util.List;
import java.util.Objects;

public class BugHibernateRepository implements BugRepository{
    @Override
    public void add(Bug bug) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(bug));
    }

    @Override
    public void delete(Integer id) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Bug message=session.createQuery("from Bug where id=?1",Bug.class).
                    setParameter(1,id).uniqueResult();
            System.out.println("In delete am gasit mesajul "+message);
            if (message!=null) {
                session.remove(message);
                session.flush();
            }
        });

    }

    public static Bug find(Integer id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
        return session.createSelectionQuery("from Bug where id=:idM ", Bug.class)
                .setParameter("idM", id)
                .getSingleResultOrNull();
      }
    }

    @Override
    public void update(Integer id, Bug bug) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if (!Objects.isNull(session.find(Bug.class, id))) {
                System.out.println("In update, am gasit mesajul cu id-ul "+id);
                session.merge(bug);
                session.flush();
            }
        });

    }

    @Override
    public List<Bug> getAll() {
       try( Session session=HibernateUtils.getSessionFactory().openSession()) {
           return session.createQuery("from Bug ", Bug.class).getResultList();
       }
    }

}
