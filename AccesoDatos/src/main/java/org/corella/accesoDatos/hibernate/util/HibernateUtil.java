package org.corella.accesoDatos.hibernate.util;

import org.hibernate.*;
import org.hibernate.cfg.*;

import java.io.File;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    // Código estático. Sólo se ejecuta una vez, como un Singleton
    static {
        try {
            // Creamos es SessionFactory desde el fichero hibernate.cfg.xml 
            sessionFactory = new Configuration()
                .configure(new File("Hibernate.cfg.xml")).buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Error en la inicialización.  " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
