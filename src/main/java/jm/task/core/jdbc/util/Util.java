package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    //private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/new_schema";
    private static final String USER = "root";
    private static final String PASS = "root";


    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    private static Util instance;

    private Util() {};

    public static Util getInstance() {
        if (instance == null) {
            instance = new Util();
        }
        return instance;
    }

    public static SessionFactory getSessionFactory() throws Exception {
        if (sessionFactory == null) {
            Properties hibernateConfig = new Properties();
            hibernateConfig.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/new_schema");
            hibernateConfig.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            hibernateConfig.setProperty("hibernate.connection.username", "root");
            hibernateConfig.setProperty("hibernate.connection.password", "root");
            hibernateConfig.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
            hibernateConfig.setProperty("show_sql", "true");

            Configuration configuration = new Configuration();
            configuration.setProperties(hibernateConfig);
            configuration.addAnnotatedClass(User.class);

            serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }

}
