package webprog.anhphat_24162091.configs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaConfig_24162091 {
    private static EntityManagerFactory factory;

    public static synchronized EntityManager getEntityManager() {
        if (factory == null || !factory.isOpen()) {
            factory = Persistence.createEntityManagerFactory("ServletJpa");
        }
        return factory.createEntityManager();
    }

    public static synchronized void close() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }
}

