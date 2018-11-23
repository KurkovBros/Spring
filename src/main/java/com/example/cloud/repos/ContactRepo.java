package com.example.cloud.repos;

import com.example.cloud.domain.Contact;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContactRepo extends CrudRepository<Contact, Long> {
    List<Contact> findByLastName(String lastName);
}
