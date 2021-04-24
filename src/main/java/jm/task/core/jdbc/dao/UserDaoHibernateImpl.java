package jm.task.core.jdbc.dao;



import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    Session session;

    public UserDaoHibernateImpl() {
        Util utilInstance = Util.getInstance();
        try {
            session = utilInstance.getSessionFactory().openSession();
            session.beginTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void createUsersTable() {
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (\n" +
                "  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NULL,\n" +
                "  `lastName` VARCHAR(45) NULL,\n" +
                "  `age` TINYINT(3) NULL,\n" +
                "  PRIMARY KEY (`id`));").executeUpdate();
    }

    @Override
    public void dropUsersTable() {
        session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        session.save(new User(name, lastName, age));
    }

    @Override
    public void removeUserById(long id) {
        Object user = session.load(User.class, id);
        session.delete(user);

    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) session.createCriteria(User.class).list();
    }

    @Override
    public void cleanUsersTable() {
        session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
    }

    public void setManualCommit() {

    }

    public void commit()  {
        if (session != null) {
            try {
                session.getTransaction().commit();
                this.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        try {
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
