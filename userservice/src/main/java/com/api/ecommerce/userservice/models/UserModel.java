package com.api.ecommerce.userservice.models;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TB_USER")
public class UserModel implements Serializable, UserDetails { // UserDetails -> classe que o Spring Security entende que sera utilizada como login

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private UUID userId;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "full_name", nullable = false, length = 120)
    private String fullName;

    @Column(name = "user_password", nullable = false)
    private String password;

    @CPF
    @Column(name = "cpf", nullable = false, length = 14, unique = true)
    private String cpf;

    @Column(name = "rg", nullable = false, length = 20)
    private String rg;

    @ManyToOne
    @JoinColumn(
            name = "sex_id",
            referencedColumnName = "sex_id")
    private SexModel sex;

    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @OneToMany(mappedBy = "userModel")
    private List<AddressModel> addresses;

    @ManyToMany
    @JoinTable(
            name = "TB_USER_ROLE",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<RoleModel> roles;

    public UserModel(){}

    public UserModel(String email, String fullName, String password, List<RoleModel> roles, boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired, boolean isEnabled){
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.roles = roles;
    }

    //region Getters/Setters

    public UUID getUserId() {
        return userId;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }
    public void setRg(String rg) {
        this.rg = rg;
    }

    public SexModel getSex() {
        return sex;
    }
    public void setSex(SexModel sex) {
        this.sex = sex;
    }

    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<AddressModel> getAddresses() {
        return addresses;
    }
    public void setAddresses(List<AddressModel> addresses) {
        this.addresses = addresses;
    }

    public List<RoleModel> getRoles() {
        return roles;
    }
    public void setRoles(List<RoleModel> roles) {
        this.roles = roles;
    }

    //endregion

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }
}
