package ru.tiunov.homeworkapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
    @GetMapping("/")
    public String getGreeting(){
        return "Приложение запущено";
    }

    @GetMapping("/info")
    public String getInfoAboutMe(){
        return "Тиунов Георгий\n" +
                "Учебный проект\n" +
                "08.12.2022\n" +
                "Описание: учебный проект, где я изучаю спригн, тестирую инструменты, разбираюсь с maven)";
    }
}
