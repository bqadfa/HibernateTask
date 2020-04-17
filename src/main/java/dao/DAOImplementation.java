package dao;

import entities.Discipline;
import entities.Roles;
import entities.User;
import enums.Status;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtils;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static java.util.Arrays.asList;

public class DAOImplementation implements FromOrToDB {

    private static SessionFactory sessionFactory = HibernateUtils.getSessionFactory();

    public List<User> getUserByRole(String role) {
        Session session = null;
        Transaction transaction = null;
        List<User> listOfUsers = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("FROM User u JOIN FETCH u.roles r WHERE r.nameOfRole =: role");
            query.setString("role", String.valueOf(role));
            listOfUsers = query.list();
            transaction.commit();
        }
        catch (Exception e){
            transaction.rollback();
        } finally {
            if (session != null)
                session.close();
        }
        return listOfUsers;
    }

    public List<User> getUsersByDiscipline (String discipline) {
        Session session = null;

        Transaction transaction = null;

        List<User> listOfUsers = null;
        try {
            session = sessionFactory.openSession();

            transaction = session.beginTransaction();

            //Query query = session.createQuery("FROM User u JOIN Discipline d WHERE d.name =: discipline");
            Query query = session.createQuery("FROM User u JOIN FETCH u.discipline d WHERE d.name =: discipline");
            //Query query = session.createQuery("FROM User u JOIN FETCH u.discipline d WHERE d.name =: discipline");

            query.setString("discipline", String.valueOf(discipline));
            listOfUsers = query.list();
            transaction.commit();
        }
        catch (Exception e) {
            transaction.rollback();
        } finally {
            if (session != null)
                session.close();
        }
        return listOfUsers;
    }

    public List<User> getUsersWithTODO() {
        Session session = null;
        Transaction transaction = null;
        List<User> listOfUsers = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("SELECT t.user FROM Tasks t WHERE t.status = 'TODO'");
            //query.setString("status", String.valueOf(Status.TODO));
            listOfUsers = query.list();
            transaction.commit();
        }
        catch (Exception e) {
            transaction.rollback();
        } finally {
            if (session != null)
                session.close();
        }
        return listOfUsers;
    }

    public void updateHeadOfDiscipline(User newHeadUser, Discipline commitedDiscipline) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            commitedDiscipline.setHeadOfDiscipline(newHeadUser);
            session.update(commitedDiscipline);
            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
        } finally {
            if (session != null)
                session.close();
        }
    }

    public void deleteUser(User user){
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            if (session != null)
                session.close();
        }
    }

}
