package fit.iuh.duongdinhlong20122841_week1.repositories;

import fit.iuh.duongdinhlong20122841_week1.connection.Connection;
import fit.iuh.duongdinhlong20122841_week1.models.Account;
import fit.iuh.duongdinhlong20122841_week1.models.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountRepository {
    private EntityManager em;

    public AccountRepository(){
        this.em = Connection.getInstance().getEntityManager();
    }
    public List<Account> getAll(){
        EntityTransaction tr = em.getTransaction();
        try {
            List<Account> list = new ArrayList<>();
            tr.begin();
            list = em.createNativeQuery("select * from Account").getResultList();
            tr.commit();
            return list;
        } catch (Exception e) {
            tr.rollback();
        }
        return null;
    }

    public List<Role> grantAccess(String accountId){
        EntityTransaction tr = em.getTransaction();
        try{
            List<Role> roles = new ArrayList<>();
            tr.begin();
            roles = em.createNativeQuery("SELECT r.role_id, r.role_name, r.description, r.status FROM grant_access g " +
                    "JOIN role r ON g.role_id = r.role_id WHERE g.account_id = " + accountId).getResultList();
            tr.commit();
            return roles;
        } catch (Exception e){
            tr.rollback();
        }
        return null;
    }

    public boolean addAccount(Account account){
        EntityTransaction tr = em.getTransaction();
        try{
            tr.begin();
            em.persist(account);
            tr.commit();
            return true;
        } catch (Exception e){
            tr.rollback();
        }
        return false;
    }

    public Optional<Account> findByEmailAndPassword(String email, String password){
        EntityTransaction tr = em.getTransaction();
        try{
            Account account = new Account();
            tr.begin();
            Query query =em.createNativeQuery("SELECT * FROM account where email = :email and password = :password");
            query.setParameter("email", email);
            query.setParameter("password", password);
            account = (Account) query.getSingleResult();
            tr.commit();
            return Optional.of(account);
        } catch (Exception e){
            tr.rollback();
        }
        return Optional.empty();
    }
    public Optional<Account>findById(String accountId){
        EntityTransaction tr = em.getTransaction();
        try{
            Account account = new Account();
            tr.begin();
            account = em.find(Account.class, accountId);
            tr.commit();
            return Optional.of(account);
        } catch (Exception e){
            tr.rollback();
        }
        return Optional.empty();
    }

    public void deleteAccount(String accountId){
        EntityTransaction tr = em.getTransaction();
        try{
            tr.begin();
            em.remove(findById(accountId));
            tr.commit();
        } catch (Exception e){
            tr.rollback();
        }
    }
    public void updateAccount(String accountId){
        EntityTransaction tr = em.getTransaction();
        try{
            tr.begin();
            em.merge(findById(accountId));
            tr.commit();
        } catch (Exception e){
            tr.rollback();
        }
    }
}
