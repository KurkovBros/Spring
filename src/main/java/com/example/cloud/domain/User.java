package com.example.cloud.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity // данный класс является сущностью и Hibernate должен её создать
@Table(name = "usr") // указываем Hibernate, с какой таблицей надо связать этот класс
public class User implements UserDetails {
    @Id // указываем первичный ключ
    @GeneratedValue(strategy = GenerationType.AUTO) // первичный ключ будет генерироваться автоматически
    private Long id;
    @NotBlank(message = "Имя пользователя не может быть пустым") // сообщение при попытке регистрации пользователя без имени
    private String username;
    @NotBlank(message = "Пароль пользователя не может быть пустым") // сообщение при попытке регистрации пользователя без пароля
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
    private Set<Role> roles; // создание множества ролей пользователя

    // Один автор соответствует множеству контактов
    // mappedBy - обратная сторона связи сущности: поле не сохраняется в БД, но доступно по запросу
    // cascade - связь пользователя с его контактами: если удалим пользователя, удалятся и все его контакты
    // fetch = FetchType.LAZY - контакты пользователя будут загружаться только при обращении к ним, а не при загрузке пользователя
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Contact> contacts;

    // метод сгенерирован автоматически по полю id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    // метод сгенерирован автоматически по полю id
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

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

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> messages) {
        this.contacts = contacts;
    }
}
