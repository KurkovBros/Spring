package com.example.cloud.controller;

import com.example.cloud.domain.Role;
import com.example.cloud.domain.User;
import com.example.cloud.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller // Говорим спрингу, что этот класс является бином и его нужно использовать при создании контроллера
@PreAuthorize("hasAuthority('ADMIN')") // класс активен только если пользователь зашел как админ
@RequestMapping("/user") // контроллер должен обрабатывать запрос с указанным адресом
public class UserController {

    @Autowired // автоматическое связывание класса и поля средствами Spring
    private UserRepo userRepo;

    @GetMapping // Метод возвращает страницу, описанную в файле userList.ftl
    public String userList(Model model) {
        model.addAttribute("users", userRepo.findAll()); // добавление в модель всех пользователей из БД
        return "userList";
    }

    @GetMapping("{user}") // Метод изменяет настройки пользователя
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user); // добавление в модель пользователя
        model.addAttribute("roles", Role.values()); // и всех его ролей
        return "userEdit";
    }


    @PostMapping // Метод сохраняет нового пользователя
    public String userSave(
            @RequestParam String username, // запрос имени пользователя
            @RequestParam Map <String, String> form,
            @RequestParam("userId") User user
    ) {
        user.setUsername(username); // установка имени пользователя
        Set <String> roles = new HashSet <>(); // создание множества ролей пользователя
        for (Role role : Role.values()) { // для всех значений enum Role
            String name = role.name(); // имя равно названию роли
            roles.add(name); // добавляем имя роли в множество ролей пользователя
        }
        user.getRoles().clear(); // на всякий случай очищаем множество ролей нового пользователя
        for (String key : form.keySet()) { // для всех ролей в множестве всех ключей form
            if (roles.contains(key)) { // если в множестве ролей пользователя есть такая роль
                user.getRoles().add(Role.valueOf(key)); // добавляем в множество ролей пользователя эту роль
            }
        }
        userRepo.save(user); // сохраняем нового пользователя в БД
        return "redirect:/user"; // переходим на страницу user
    }
}
