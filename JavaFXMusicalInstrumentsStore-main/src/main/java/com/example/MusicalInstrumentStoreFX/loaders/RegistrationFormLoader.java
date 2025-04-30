package com.example.MusicalInstrumentStoreFX.loaders;

import com.example.MusicalInstrumentStoreFX.tools.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RegistrationFormLoader {
    private final SpringFXMLLoader springFXMLLoader;

    public RegistrationFormLoader(SpringFXMLLoader springFXMLLoader) {
        this.springFXMLLoader = springFXMLLoader;
    }

        public void loadRegistrationForm(String title, Stage primaryStage){ {
            FXMLLoader fxmlLoader = springFXMLLoader.load("/user/registrationForm.fxml");
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle(title); // Меняем заголовок окна
        }
    }
}
