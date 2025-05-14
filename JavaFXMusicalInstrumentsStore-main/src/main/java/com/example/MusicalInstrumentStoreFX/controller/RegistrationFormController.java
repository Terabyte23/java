package com.example.MusicalInstrumentStoreFX.controller;

import com.example.MusicalInstrumentStoreFX.MusicalInstrumentStoreFxApplication;
import com.example.MusicalInstrumentStoreFX.loaders.LoginFormLoader;
import com.example.MusicalInstrumentStoreFX.loaders.MainFormLoader;
import com.example.MusicalInstrumentStoreFX.model.entity.AppUser;
import com.example.MusicalInstrumentStoreFX.service.AppUserService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class RegistrationFormController {
    private final MainFormLoader mainFormLoader;
    private final AppUserService appUserService;
    private final LoginFormLoader loginFormLoader;

    @FXML private TextField tfLastName;
    @FXML private TextField tfFirstName;
    @FXML private TextField tfUserName;
    @FXML private TextField pfPassword;


    public RegistrationFormController(AppUserService appUserService, MainFormLoader mainFormLoader, LoginFormLoader loginFormLoader) {
        this.appUserService = appUserService;
        this.mainFormLoader = mainFormLoader;
        this.loginFormLoader = loginFormLoader;
    }

    @FXML private void registration() {
        try{
            AppUser newUser = new AppUser();
            newUser.setFirstname(tfFirstName.getText());
            newUser.setLastname(tfLastName.getText());
            newUser.setUsername(tfUserName.getText());
            newUser.setPassword(pfPassword.getText());
            newUser.getRoles().add(AppUserService.ROLES.USER.toString());
            appUserService.add(newUser);
            loginFormLoader.load();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        }

        @FXML private void goToMainForm() {
            mainFormLoader.load();
        }
    }

