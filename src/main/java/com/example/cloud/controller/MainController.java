package com.example.cloud.controller;

import com.example.cloud.domain.Contact;
import com.example.cloud.repos.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // Говорим спрингу, что этот класс является бином и его нужно использовать при создании контроллера
public class MainController {

    @Autowired // автоматическое связывание класса и поля средствами Spring
    private ContactRepo contactRepo;

    @Value("${upload.path}") // Spring найдет в property файле эту переменную и подставит её значение в uploadPath
    private String uploadPath;

    // Метод возвращает страницу, описанную в файле greeting.ftl
    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    // Метод возвращает страницу, описанную в файле main.ftl
    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Contact> contacts;
        if (filter != null && !filter.isEmpty()) { // если фильтр есть и он не пустой,
            contacts = contactRepo.findByLastName(filter); // то найти контакты с заданным тегом
        } else {
            contacts = contactRepo.findAll(); // иначе найти все контакты
        }
        model.addAttribute("contacts", contacts); // добавление в модель всех контактов, которые должны быть отображены
        model.addAttribute("filter", filter); // добавление в модель фильтра
        return "main"; // отображение модели
    }
}
