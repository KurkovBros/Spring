package com.example.cloud.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity // данный класс является сущностью и Hibernate должен её создать
@Table(name = "usr") // указываем Hibernate, с какой таблицей надо связать этот класс
public class User implements UserDetails {
    @Id // указываем первичный ключ
    @GeneratedValue(strategy = GenerationType.AUTO) // первичный ключ будет генерироваться автоматически
    private Long id;
    private String username;
    private String password;
    private boolean active;

    // формирование дополнительной таблицы для хранения ролей, при запросе пользователя Hibernate будет сразу загружать все его роли
    // если fetch = FetchType.LAZY - роли загружаются только при обращении к этому полю
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    // поле будет храниться в отдельной таблице, для которой мы не описывали мапинг
    // соединение текущей таблички с табличкой ролей через поле user_id
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    // данное поле - enum и хранить его мы будем в виде строки
    @Enumerated(EnumType.STRING)
    private Set<Role> roles; // создание множества ролей (админ, юзер)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
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
        return isActive();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
