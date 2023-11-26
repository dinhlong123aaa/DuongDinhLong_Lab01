package fit.iuh.duongdinhlong20122841_week1.repositories;

import fit.iuh.duongdinhlong20122841_week1.connection.Connection;
import fit.iuh.duongdinhlong20122841_week1.models.Account;
import fit.iuh.duongdinhlong20122841_week1.models.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoleRepository {
    private EntityManager em;

    public RoleRepository(){
        this.em = Connection.getInstance().getEntityManager();
    }

    public Optional<Role> findRoleById(String roleId){
        EntityTransaction tr = em.getTransaction();
        try{
            Role role = new Role();
            tr.begin();
            role = em.find(Role.class, roleId);
            tr.commit();
            return Optional.of(role);
        } catch (Exception e){
            tr.rollback();
        }
        return Optional.empty();
    }

    public List<Role> getAll(){
        EntityTransaction tr = em.getTransaction();
        try {
            List<Role> list = new ArrayList<>();
            tr.begin();
            list = em.createNativeQuery("select * from Role").getResultList();
            tr.commit();
            return list;
        } catch (Exception e) {
            tr.rollback();
        }
        return null;
    }
    public void addRole(Role role){
        EntityTransaction tr = em.getTransaction();
        try{
            tr.begin();
            em.persist(role);
            tr.commit();
        } catch (Exception e){
            tr.rollback();
        }
    }
}
