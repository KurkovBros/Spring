package com.example.cloud.controller;

import com.example.cloud.domain.Contact;
import com.example.cloud.domain.User;
import com.example.cloud.repos.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller // Говорим спрингу, что этот класс является бином и его нужно использовать при создании контроллера
@RequestMapping("/contact") // контроллер должен обрабатывать запрос с указанным адресом
public class ContactController {

    @Autowired // автоматическое связывание класса и поля средствами Spring
    private ContactRepo contactRepo;

    @Value("${upload.path}") // Spring найдет в property файле эту переменную и подставит её значение в uploadPath
    private String uploadPath;

    // Метод изменяет контакт
    @GetMapping("{contact}")
    public String updateContact(
            @PathVariable Contact contact, Model model, // автозаполнение переменных по пути запроса
            @RequestParam("lastName") String lastName, // запрос фамилии контакта
            @RequestParam("firstName") String firstName, // запрос имени контакта
            @RequestParam("email") String email, // запрос email контакта
            @RequestParam("phone") String phone, // запрос телефонного номера контакта
            @RequestParam("file") MultipartFile file // запрос сложного файла (не строки)
    ) throws IOException {
        model.addAttribute("contact", contact); // добавление в модель пользователя
        if (!StringUtils.isEmpty(lastName)) { // если фамилия не пустая
            contact.setLastName(lastName); // устанавливаем фамилию
        }
        if (!StringUtils.isEmpty(firstName)) { // если имя не пустое
            contact.setFilename(firstName); // устанавливаем имя
        }
        if (!StringUtils.isEmpty(email)) { // если email не пустой
            contact.setEmail(email); // устанавливаем email
        }
        if (!StringUtils.isEmpty(phone)) { // если телефонный номер не пустой
            contact.setPhone(phone); // устанавливаем номер телефона
        }
        saveFile(contact, file); // сохраняем контакт и картинку (если приложена)
        contactRepo.save(contact); // сохраняем контакт в БД
        return "contact";
    }

    // Метод добавляет контакт
    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user, // загрузка пользователя из текущей сессии
            @RequestParam String firstName, // запрос имени
            @RequestParam String email, // запрос email
            @RequestParam String phone, // запрос телефона
            @RequestParam String lastName, Map<String, Object> model, // запрос фамилии и модели
            @RequestParam("file") MultipartFile file // запрос сложного файла (не строки)
    ) throws IOException {
        Contact contact = new Contact(firstName, lastName, email, phone, user); // создаем контакт
        contact.setAuthor(user); // устанавливаем для этого контакта автора
        saveFile(contact, file); // сохраняем контакт и картинку (если приложена)
        contactRepo.save(contact); // запись созданного контакта в БД
        Iterable<Contact> contacts = contactRepo.findAll(); // получение всех контактов из БД
        model.put("contacts", contacts); // запись полученных контактов в модель
        return "main"; // отображение модели
    }

    private void saveFile(@Valid Contact contact, @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) { // если файл есть и его имя не пустое
            File uploadDir = new File(uploadPath); // создание папки с указанным адресом
            if (!uploadDir.exists()) { // если такой папки нет,
                uploadDir.mkdir(); // то мы её создаем
            }
            String uuidFile = UUID.randomUUID().toString(); // создание уникальной строки
            String resultFilename = uuidFile + "." + file.getOriginalFilename(); // создание уникального имени файла
            file.transferTo(new File(uploadPath + "/" + resultFilename)); // загрузка файла по заданному пути и имени
            contact.setFilename(resultFilename); // установка имени файла в сообщении
        }
    }
}
