package fit.iuh.duongdinhlong20122841_week1.connection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Connection {
    private final EntityManager em;
    private static Connection connection;

    private Connection() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DinhLong_01");
        em = emf.createEntityManager();
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public static Connection getInstance() {
        if (connection == null)
            connection = new Connection();
        return connection;
    }
}
