package com.example.MusicalInstrumentStoreFX.controller;

import com.example.MusicalInstrumentStoreFX.loaders.MainFormLoader;
import com.example.MusicalInstrumentStoreFX.loaders.RegistrationFormLoader;
import com.example.MusicalInstrumentStoreFX.service.AppUserService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginFormController {

    @Autowired private AppUserService appUserService;
    @Autowired private MainFormLoader mainFormLoader;
    @Autowired private RegistrationFormLoader registrationFormLoader;

    @FXML private Label lbInfo;
    @FXML private TextField tfUsername;
    @FXML private PasswordField pfPassword;

    @FXML
    private void login() {
        boolean isAuthenticated = appUserService.authentication(tfUsername.getText(), pfPassword.getText());
        if (isAuthenticated) {
            mainFormLoader.load();
        } else {
            lbInfo.setText("Нет такого пользователя или неправильный пароль");
        }
    }

    @FXML
    private void showRegistrationForm() {
        registrationFormLoader.load("Создание нового пользователя");
    }
}
