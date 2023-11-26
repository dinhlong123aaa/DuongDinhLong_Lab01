package fit.iuh.duongdinhlong20122841_week1.repositories;

import fit.iuh.duongdinhlong20122841_week1.connection.Connection;
import fit.iuh.duongdinhlong20122841_week1.models.Log;
import fit.iuh.duongdinhlong20122841_week1.models.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.sql.Timestamp;
import java.util.Optional;

public class LogRepository {
    private EntityManager em;

    public LogRepository(){
        this.em = Connection.getInstance().getEntityManager();
    }
    public void insertLog(Log log){
        EntityTransaction tr = em.getTransaction();
        try{
            tr.begin();
            em.persist(log);
            tr.commit();
        } catch (Exception e){
            tr.rollback();
        }
    }
    public Optional<Log> getLogById(long logId){
        EntityTransaction tr = em.getTransaction();
        try{
            Log log = new Log();
            tr.begin();
            log = em.find(Log.class, logId);
            tr.commit();
            return Optional.of(log);
        } catch (Exception e){
            tr.rollback();
        }
        return Optional.empty();
    }
    public void updateLogoutTime(long logId){
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            Query query = em.createNativeQuery("UPDATE log l set l.logout_time = :logoutTime where e.id = :logId");
            query.setParameter("logoutTime", new Timestamp(System.currentTimeMillis()));
            query.setParameter("logId", logId);
            query.executeUpdate();
            tr.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
        }
    }

}
