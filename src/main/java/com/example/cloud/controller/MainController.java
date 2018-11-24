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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Controller // Говорим спрингу, что этот класс является бином и его нужно использовать при создании контроллера
public class MainController {

    @Autowired // автоматическое связывание класса и поля средствами Spring
    private ContactRepo contactRepo;

    @Value("${upload.path}") // Spring найдет в property файле эту переменную и подставит её значение в uploadPath
    private String uploadPath;

    // Метод возвращает страницу, описанную в файле greeting.ftl
    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    // Метод возвращает страницу, описанную в файле main.ftl
    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Contact> contacts = contactRepo.findAll(); // Получение всех контактов и запись их contacts
        if (filter != null && !filter.isEmpty()) { // если фильтр есть и он не пустой,
            contacts = contactRepo.findByLastName(filter); // то найти контакты с заданным тегом
        } else {
            contacts = contactRepo.findAll(); // иначе найти все контакты
        }
        model.addAttribute("contacts", contacts); // добавление в модель всех контактов, которые должны быть отображены
        model.addAttribute("filter", filter); // добавление в модель фильтра
        return "main"; // отображение модели
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

    // Метод возвращает все конаткты пользователя
    @GetMapping("/user-contacts/{user}")
    public String userContacts(
            @AuthenticationPrincipal User currentUser, // загрузка пользователя из текущей сессии
            @PathVariable User user, // Spring подставляет переменную из мапинга в указанное поле
            Model model,
            @RequestParam(required = false) Contact contact // запрос контакта
    ) {
        Set<Contact> contacts = user.getContacts(); // создание множества контактов пользователя
        model.addAttribute("contacts", contacts); // добавление в модель всех контактов, которые должны быть отображены
        model.addAttribute("contact", contact); // добавление контакта в модель
        model.addAttribute("isCurrentUser", currentUser.equals(user)); // отображение контактов только текущего пользователя
        return "userContacts";
    }

    // Метод изменяет контакт
    @PostMapping("/user-contacts/{user}")
    public String updateContact(
            @AuthenticationPrincipal User currentUser, // загрузка пользователя из текущей сессии
            @PathVariable Long user, // Spring подставляет переменную из мапинга (её поле id<long>) в указанное поле
            @RequestParam("id") Contact contact, // запрос id контакта
            @RequestParam("lastName") String lastName, // запрос фамилии контакта
            @RequestParam("firstName") String firstName, // запрос имени контакта
            @RequestParam("email") String email, // запрос email контакта
            @RequestParam("phone") String phone, // запрос телефонного номера контакта
            @RequestParam("file") MultipartFile file // запрос сложного файла (не строки)
    ) throws IOException {
        if (contact.getAuthor().equals(currentUser)) { // редактировать можно только свои контакты
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
        }
        return "redirect:/user-contacts/" + user; // переходим на страницу контактов пользователя (user - id пользователя)
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
