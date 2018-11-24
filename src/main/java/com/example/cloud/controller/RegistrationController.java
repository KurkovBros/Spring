package com.example.cloud.controller;

import com.example.cloud.domain.Role;
import com.example.cloud.domain.User;
import com.example.cloud.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller // Говорим спрингу, что этот класс является бином и его нужно использовать при создании контроллера
public class RegistrationController {

    @Autowired // автоматическое связывание класса и поля средствами Spring
    private UserRepo userRepo;

    // Метод возвращает страницу, описанную в файле registration.ftl
    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    // Метод добавляет пользователя
    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        // поиск в БД пользователя с заданным именем и создание в приложении такого пользователя
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb != null) { // если такой пользователь уже есть,
            model.put("message", "User exists!"); // выводим сообщение о том, что такой пользователь уже есть
            return "registration"; // переходим на страницу регистрации
        }
        user.setActive(true); // иначе активируем нового пользователя
        user.setRoles(Collections.singleton(Role.USER)); // устанавливаем его роль (сет с одним значением)
        userRepo.save(user); // заливаем нового пользователя в БД
        return "redirect:/login"; // переходим на страницу авторизации
    }
}
