package com.example.cloud.config;

import com.example.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration // Этот класс используется спрингом для конфигурирования приложения
@EnableWebSecurity // Отключение стандартных настроек безопасности
@EnableGlobalMethodSecurity(prePostEnabled = true) // Разрешение настроек безопасности через методы
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired // автоматическое связывание класса и поля средствами Spring
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests() // Авторизация пользователя
                // полный доступ к registration, все файлы в папке static раздаются без авторизации
                .antMatchers("/", "/registration", "/static/**").permitAll()
                .anyRequest().authenticated() // Требование авторизации для всех остальных запросов
            .and()
                .formLogin() // Создание формы логина
                .loginPage("/login") // Указание пути, по которому можно найти эту форму
                .permitAll() // Свободный доступ для всех
            .and()
                .logout()
                .permitAll(); // Свободный доступ для всех
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
