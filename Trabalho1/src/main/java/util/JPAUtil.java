package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("MeuPU");

    public static EntityManager getEntityManager() {
        EntityManager em = entityManagerFactory.createEntityManager();
        return em;
    }
}
