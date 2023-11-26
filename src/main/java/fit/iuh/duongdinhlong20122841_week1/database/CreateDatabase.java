package fit.iuh.duongdinhlong20122841_week1.database;

import fit.iuh.duongdinhlong20122841_week1.connection.Connection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

public class CreateDatabase {
    public static void main(String[] args) {
        EntityManagerFactory emf = Connection.getInstance().getEntityManager().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }
}
