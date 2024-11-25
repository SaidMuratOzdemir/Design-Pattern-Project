package com.designpatterns.database;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        try {
            // Hibernate yapılandırma dosyasını kullanarak SessionFactory oluşturuyoruz
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // SessionFactory'yi kapatarak kaynakları serbest bırakıyoruz
        getSessionFactory().close();
    }
}
