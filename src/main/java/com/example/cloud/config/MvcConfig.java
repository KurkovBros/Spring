package com.example.cloud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${upload.path}") // создаем переменную пути к файлам, которые нужно будет грузить на сервер
    private String uploadPath;

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login"); // добавление формы логин/пароль
    }

    // Метод для загрузки файлов
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**") // все запросы к серверу по указанному адресу будут перенаправляться
                .addResourceLocations("file://" + uploadPath + "/"); // в файловую систему по пути uploadPath
        registry.addResourceHandler("/static/**") // все файлы в папке static раздаются без авторизации
                .addResourceLocations("classpath:/static/"); // ресурсы ищутся в дереве проекта в папке static
    }
}
