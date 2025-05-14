package com.example.MusicalInstrumentStoreFX.service;

import com.example.MusicalInstrumentStoreFX.model.entity.AppUser;
import com.example.MusicalInstrumentStoreFX.model.repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {

    // Сделаем currentUser доступным через геттер и сеттер
    private static AppUser currentUser;

    // Роли
    public enum ROLES {USER, MANAGER, ADMINISTRATOR};

    // Репозиторий пользователей
    private final AppUserRepository repository;

    // Конструктор сервиса
    public AppUserService(AppUserRepository repository) {
        this.repository = repository;
        initSuperUser();  // Инициализируем суперпользователя, если база пустая
    }

    // Обновление пользователя в базе
    public void update(AppUser user) {
        repository.save(user);
    }

    // Инициализация суперпользователя
    private void initSuperUser() {
        if (repository.count() > 0) {
            return;
        }
        AppUser admin = new AppUser();
        admin.setUsername("admin");
        admin.setPassword("12345");
        admin.setFirstname("Admin");
        admin.setLastname("SuperAdmin");
        admin.getRoles().add(ROLES.ADMINISTRATOR.toString());
        admin.getRoles().add(ROLES.MANAGER.toString());
        admin.getRoles().add(ROLES.USER.toString());
        repository.save(admin);
    }

    // Добавление нового пользователя
    public void add(AppUser user) {
        repository.save(user);
    }

    // Аутентификация пользователя
    public boolean authentication(String username, String password) {
        Optional<AppUser> optionalAppUser = repository.findByUsername(username);
        if (optionalAppUser.isEmpty()) {
            return false;
        }
        AppUser loginUser = optionalAppUser.get();
        if (!loginUser.getPassword().equals(password)) {
            return false;
        }
        currentUser = loginUser;  // Устанавливаем текущего пользователя
        return true;
    }

    // Получение всех пользователей
    public List<AppUser> getAllUsers() {
        return repository.findAll();
    }

    // Сохранение пользователя
    public void save(AppUser appUser) {
        repository.save(appUser);
    }

    // Получение текущего пользователя
    public static AppUser getCurrentUser() {
        return currentUser;
    }

    // Установка текущего пользователя (при необходимости)
    public static void setCurrentUser(AppUser user) {
        currentUser = user;
    }
}
