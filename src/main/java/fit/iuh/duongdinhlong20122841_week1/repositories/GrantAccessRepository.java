package fit.iuh.duongdinhlong20122841_week1.repositories;

import fit.iuh.duongdinhlong20122841_week1.connection.Connection;
import fit.iuh.duongdinhlong20122841_week1.models.Account;
import fit.iuh.duongdinhlong20122841_week1.models.GrantAccess;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GrantAccessRepository {
    private EntityManager em;

    public GrantAccessRepository(){
        this.em = Connection.getInstance().getEntityManager();
    }

    public void addGrantAccess(GrantAccess grantAccess){
        EntityTransaction tr = em.getTransaction();
        try{
            tr.begin();
            em.persist(grantAccess);
            tr.commit();
        } catch (Exception e){
            tr.rollback();
        }
    }
    public List<GrantAccess> getGrantAccessByAccountId(String accountId){
        EntityTransaction tr = em.getTransaction();
        try{
            List<GrantAccess> grantAccesses = new ArrayList<>();
            tr.begin();
            Query query =em.createNativeQuery("SELECT * FROM grant_access where account_id = :accountId");
            query.setParameter("accountId", accountId);
            grantAccesses =  query.getResultList();
            tr.commit();
            return grantAccesses;
        } catch (Exception e){
            tr.rollback();
        }
        return null;
    }
}
