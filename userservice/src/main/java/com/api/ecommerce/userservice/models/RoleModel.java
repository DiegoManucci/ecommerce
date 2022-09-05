package com.api.ecommerce.userservice.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_ROLE")
public class RoleModel implements Serializable, GrantedAuthority {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private UUID roleId;

    @Column(name = "description", nullable = false, unique = true, length = 255)
    private String description;

    public RoleModel() {

    }

    public RoleModel(UUID roleId, String description) {
        this.roleId = roleId;
        this.description = description;
    }

    //region Getters/Setters

    public UUID getRoleId() {
        return roleId;
    }
    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    //endregion

    @Override
    public String getAuthority() {
        return description;
    }
}
