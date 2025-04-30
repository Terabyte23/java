package com.example.MusicalInstrumentStoreFX.controller;

import com.example.MusicalInstrumentStoreFX.MusicalInstrumentStoreFxApplication;
import com.example.MusicalInstrumentStoreFX.loaders.MainFormLoader;
import com.example.MusicalInstrumentStoreFX.loaders.RegistrationFormLoader;
import com.example.MusicalInstrumentStoreFX.service.AppUserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginFormController {
    private final MainFormLoader mainFormLoader;
    private final RegistrationFormLoader registrationFormLoader;
    private final AppUserService appUserService;

    @FXML
    private Label lbInfo;
    @FXML private TextField tfUsername;
    @FXML private PasswordField pfPassword;

    public LoginFormController(AppUserService appUserService, MainFormLoader mainFormLoader, RegistrationFormLoader registrationFormLoader) {
        this.appUserService = appUserService;
        this.mainFormLoader = mainFormLoader;
        this.registrationFormLoader = registrationFormLoader;
    }

    @FXML private void login(){
        if(appUserService.authentication(tfUsername.getText(),pfPassword.getText())){
            mainFormLoader.loadMainForm(MusicalInstrumentStoreFxApplication.primaryStage);
        }else{
            lbInfo.setText("Нет такого пользователя, или неправильный пароль");
        }
    }
    @FXML private void showRegistrationForm(){
        registrationFormLoader.loadRegistrationForm("Создание нового пользователя", MusicalInstrumentStoreFxApplication.primaryStage);
    }


}