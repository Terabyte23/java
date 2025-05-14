package com.example.MusicalInstrumentStoreFX.controller;

import com.example.MusicalInstrumentStoreFX.loaders.ListBuyersFormLoader;
import com.example.MusicalInstrumentStoreFX.model.entity.AppUser;
import com.example.MusicalInstrumentStoreFX.service.AppUserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WalletDialogController {

    @FXML
    private Label lblBuyerName;
    @FXML
    private TextField tfAmount;
    @FXML
    private Label lblCurrentBalance;

    private AppUser appUser;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private ListBuyersFormLoader listBuyersFormLoader;  // Внедряем ListBuyersFormLoader

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
        lblBuyerName.setText("Покупатель: " + appUser.getFirstname() + " " + appUser.getLastname());
        lblCurrentBalance.setText("Баланс: " + appUser.getBalance());
    }

    @FXML
    private void onTopUp() {
        try {
            double amount = Double.parseDouble(tfAmount.getText());
            System.out.println("Попытка пополнения на сумму: " + amount);

            if (amount > 0) {
                if (appUser.getBalance() == null) {
                    appUser.setBalance(0.0);  // Проверяем, если баланс null, устанавливаем 0
                }

                Double currentBalance = appUser.getBalance();
                System.out.println("Текущий баланс: " + currentBalance);

                appUser.setBalance(currentBalance + amount);
                System.out.println("Новый баланс: " + appUser.getBalance());

                appUserService.update(appUser);  // Обновляем данные о пользователе в базе
                lblCurrentBalance.setText("Баланс: " + appUser.getBalance());

                // Закрываем окно пополнения и открываем список покупателей
                closeWindowAndReturnToBuyersList();
            } else {
                tfAmount.setStyle("-fx-border-color: red;");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка при вводе суммы пополнения: " + e.getMessage());
            tfAmount.setStyle("-fx-border-color: red;");
        }
    }

    private void closeWindowAndReturnToBuyersList() {
        // Закрываем окно пополнения баланса
        Stage stage = (Stage) tfAmount.getScene().getWindow();
        stage.close();

        // Открываем окно списка покупателей
        listBuyersFormLoader.load();  // Загружаем список покупателей
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
