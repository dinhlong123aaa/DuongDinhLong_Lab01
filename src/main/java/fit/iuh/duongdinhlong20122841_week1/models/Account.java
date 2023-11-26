package fit.iuh.duongdinhlong20122841_week1.models;

import fit.iuh.duongdinhlong20122841_week1.enums.AccountStatus;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Account {
    @Id
    @Column(name = "account_id")
    private String id;
    private String fullName;
    private String password;
    private String email;
    private String phone;
    private AccountStatus status;
    private List<Role> roles;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GrantAccess> list;
    public Account() {
    }

    public Account(String id, String fullName, String password, String email, String phone, AccountStatus status, List<GrantAccess> list) {
        this.id = id;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.list = list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public List<GrantAccess> getList() {
        return list;
    }

    public void setList(List<GrantAccess> list) {
        this.list = list;
    }

    public void addRole(Role role){
        this.role.add(role);
    }
    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                ", list=" + list +
                '}';
    }
}
