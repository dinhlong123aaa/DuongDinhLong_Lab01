package fit.iuh.duongdinhlong20122841_week1.models;

import fit.iuh.duongdinhlong20122841_week1.enums.GrantStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "grant_access")
public class GrantAccess {
    private GrantStatus isGrant;
    private String note;

    @Id
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Role role;

    @Id
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;

    public GrantAccess() {
    }

    public GrantAccess(GrantStatus isGrant, String note, Role role, Account account) {
        this.isGrant = isGrant;
        this.note = note;
        this.role = role;
        this.account = account;
    }

    public GrantStatus getIsGrant() {
        return isGrant;
    }

    public void setIsGrant(GrantStatus isGrant) {
        this.isGrant = isGrant;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "GrantAccess{" +
                "isGrant=" + isGrant +
                ", note='" + note + '\'' +
                ", role=" + role +
                ", account=" + account +
                '}';
    }
}
