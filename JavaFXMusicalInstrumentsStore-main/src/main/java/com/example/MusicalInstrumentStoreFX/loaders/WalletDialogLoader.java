package com.example.MusicalInstrumentStoreFX.loaders;

import com.example.MusicalInstrumentStoreFX.controller.WalletDialogController;
import com.example.MusicalInstrumentStoreFX.model.entity.AppUser;
import com.example.MusicalInstrumentStoreFX.tools.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WalletDialogLoader extends AbstractFormLoader {

    @Autowired
    private ApplicationContext applicationContext;

    // Конструктор с внедрением зависимости Spring
    public WalletDialogLoader(SpringFXMLLoader springFXMLLoader) {
        super(springFXMLLoader);
    }

    @Override
    public Parent load() {
        // Реализация без параметров, можно оставить как заглушку
        System.out.println("Load without parameters");
        return null;
    }

    public void load(AppUser appUser) {
        try {
            FXMLLoader loader = getSpringFXMLLoader().load("/buyers/walletDialog.fxml");
            loader.setControllerFactory(applicationContext::getBean);  // Spring инжектирует зависимости
            Parent root = loader.load();

            // Получаем контроллер и устанавливаем пользователя
            WalletDialogController controller = loader.getController();
            controller.setAppUser(appUser);

            // Используем getPrimaryStage() для установки сцены на основной Stage
            getPrimaryStage().setTitle("Пополнение баланса");
            getPrimaryStage().setScene(new Scene(root));
            getPrimaryStage().show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

