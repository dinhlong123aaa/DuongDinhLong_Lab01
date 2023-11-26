package fit.iuh.duongdinhlong20122841_week1.models;

import fit.iuh.duongdinhlong20122841_week1.enums.RoleStatus;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @Column(name = "role_id")
    private String id;
    private String name;
    private String description ;
    private RoleStatus status;
    @OneToMany(mappedBy = "role")
    private List<GrantAccess> list;

    public Role() {
    }

    public Role(String id, String name, String description, RoleStatus status, List<GrantAccess> list) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.list = list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RoleStatus getStatus() {
        return status;
    }

    public void setStatus(RoleStatus status) {
        this.status = status;
    }

    public List<GrantAccess> getList() {
        return list;
    }

    public void setList(List<GrantAccess> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", list=" + list +
                '}';
    }
}
