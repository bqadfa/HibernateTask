package dao;

import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtils;

import java.util.List;

public interface FromOrToDB <E> {


    default void toDB(List<E> list) {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            for (E i : list)
                session.save(i);
            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
        }
        finally {
            if (session != null) session.close();
        }
    }

    default List<E> allFromDB(E e) {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = null;
        Transaction transaction = null;
        List<E> list = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from " + e.getClass().getCanonicalName());
            list = query.list();
            transaction.commit();
        } catch (Exception exp) {
            transaction.rollback();
        } finally {
            if (session != null)
                session.close();
        }
        return list;
    }
}
