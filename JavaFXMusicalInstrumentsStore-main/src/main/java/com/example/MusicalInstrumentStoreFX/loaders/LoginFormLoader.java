package com.example.MusicalInstrumentStoreFX.loaders;

import com.example.MusicalInstrumentStoreFX.tools.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginFormLoader extends AbstractFormLoader{
    public LoginFormLoader(SpringFXMLLoader springFXMLLoader) {
        super(springFXMLLoader);
    }

    @Override
    public Parent load(){
        FXMLLoader fxmlLoader = getSpringFXMLLoader().load("/user/loginForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setResizable(false);//Делаем окно с неизменяемым размером
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Nptv23JavaFX вход пользователя");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
        return root;
    }
}
