/*
 * Klasa odpowiadajaca za tworzenie fabryki sesji
 */
package com.mycompany.mytasklist;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 *
 * @author marcin
 */
public class HibernateUtil {
    //prywatne pole przechowujace sesje polaczsenia z baza danych
    private static final SessionFactory sessionFactory = buildSessionFactory();
    
    //prywatny konstruktor, aby nikt niepotrzebnie nie tworzyl obiektu klasy
    private HibernateUtil() {
        
    }

    //"budowanie fabryki sesji", co ma sie dziac przy tworzeniu obiektu,
    //laczenie z baza danych
    private static SessionFactory buildSessionFactory() {
            // A SessionFactory is set up once for an application!
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                            .configure() // configures settings from hibernate.cfg.xml
                            .build();
            try {
                    return new MetadataSources( registry ).buildMetadata().buildSessionFactory();
            }
            catch (Exception e) {
                    // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
                    // so destroy it manually.
                    StandardServiceRegistryBuilder.destroy( registry );
                    throw e;
            }
    }

    //zamykanie sesji, zamykanie polaczenia z baza danych
    public static void close() {
            if ( sessionFactory != null ) {
                    sessionFactory.close();
            }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
