package com.example.GreenAtom.models.entities;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Tribushko Danil
 *
 * Сущность пользователя
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false))
    private Set<Role> roles;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public User(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(roles.size(), user.roles.size());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, roles.size());
    }

    public boolean isAdmin(){
        AtomicBoolean isAdmin = new AtomicBoolean(false);
        roles.forEach(r -> {
            if (r.getName().equals("ADMIN")){
                isAdmin.set(true);
            }
        });
        return isAdmin.get();
    }
}
