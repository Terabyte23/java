package com.example.MusicalInstrumentStoreFX.loaders;

import com.example.MusicalInstrumentStoreFX.controller.WalletDialogController;
import com.example.MusicalInstrumentStoreFX.model.entity.AppUser;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WalletDialogLoader {

    @Autowired
    private ApplicationContext applicationContext;

    public void showWalletDialog(AppUser appUser) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/buyers/walletDialog.fxml"));
            loader.setControllerFactory(applicationContext::getBean);  // Spring инжектирует зависимости
            Parent root = loader.load();

            // Получаем контроллер и устанавливаем пользователя
            WalletDialogController controller = loader.getController();
            controller.setAppUser(appUser);

            // Создаем и показываем окно
            Stage stage = new Stage();
            stage.setTitle("Пополнение баланса");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Обработка ошибок
        }
    }
}
