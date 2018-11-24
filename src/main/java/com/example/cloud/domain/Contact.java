package com.example.cloud.domain;

import javax.persistence.*;

@Entity // данный класс является сущностью и Hibernate должен её создать
public class Contact {
    @Id // указываем первичный ключ
    @GeneratedValue(strategy=GenerationType.AUTO) // первичный ключ будет генерироваться автоматически
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    // Много контактов соответствует одному автору
    @ManyToOne(fetch = FetchType.EAGER) // получаем контакт - вместе с ним сразу получаем автора
    @JoinColumn(name = "user_id") // пристыковываем к таблице контактов колонку с user_id
    private User author;

    private String filename; // имя файла, который мы прикладываем к контакту (фото)

    public Contact() {
    }

    public Contact(String firstName, String lastName, String email, String phone, User user) {
        this.author = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
