package com.example.MusicalInstrumentStoreFX.controller;

import com.example.MusicalInstrumentStoreFX.loaders.SelectedInstrumentFormModalityLoader;
import com.example.MusicalInstrumentStoreFX.model.entity.AppUser;
import com.example.MusicalInstrumentStoreFX.model.entity.Instruments;
import com.example.MusicalInstrumentStoreFX.model.entity.Sale;
import com.example.MusicalInstrumentStoreFX.service.AppUserService;
import com.example.MusicalInstrumentStoreFX.service.InstrumentsService;
import com.example.MusicalInstrumentStoreFX.service.SalesService;
import com.example.MusicalInstrumentStoreFX.service.UserInstrumentService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

@Component
public class SelectedInstrumentFormController {

    @Autowired private MainFormController mainFormController;
    @Autowired private AppUserService appUserService;
    @Autowired private SelectedInstrumentFormModalityLoader selectedInstrumentFormModalityLoader;
    @Autowired private InstrumentsService instrumentsService;
    @Autowired private UserInstrumentService userInstrumentService;
    @Autowired private SalesService salesService;

    @FXML private VBox vbSelectedInstrumentRoot;
    @FXML private TextField tfQuantity;

    private Instruments instruments;
    private AppUser appUser;

    public SelectedInstrumentFormController() {}

    public SelectedInstrumentFormController(InstrumentsService instrumentsService,
                                            SelectedInstrumentFormModalityLoader selectedInstrumentFormModalityLoader,
                                            UserInstrumentService userInstrumentService) {
        this.instrumentsService = instrumentsService;
        this.selectedInstrumentFormModalityLoader = selectedInstrumentFormModalityLoader;
        this.userInstrumentService = userInstrumentService;
    }

    public void setInstruments(Instruments instruments) {
        this.instruments = instruments;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public void setMainFormController(MainFormController mainFormController) {
        this.mainFormController = mainFormController;
    }

    @FXML
    private void takeOnInstrument() {
        int quantityToBuy;
        try {
            quantityToBuy = Integer.parseInt(tfQuantity.getText());
            if (quantityToBuy <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Введите корректное количество", Alert.AlertType.ERROR);
            return;
        }

        if (instruments.getCount() < quantityToBuy) {
            showAlert("Ошибка", "Недостаточно на складе. Доступно: " + instruments.getCount(), Alert.AlertType.ERROR);
            return;
        }

        double totalCost = quantityToBuy * instruments.getPrice();
        if (appUser.getBalance() < totalCost) {
            showAlert("Ошибка", "Недостаточно средств. Ваш баланс: " + appUser.getBalance(), Alert.AlertType.ERROR);
            return;
        }

        // Оформляем покупку
        instruments.setCount(instruments.getCount() - quantityToBuy);
        appUser.setBalance(appUser.getBalance() - totalCost);

        instrumentsService.update(instruments);
        appUserService.update(appUser);
        userInstrumentService.saveOrUpdate(appUser, instruments, quantityToBuy);

        // Добавляем запись о продаже
        Sale sale = new Sale();
        sale.setSaleDate(LocalDate.now());
        sale.setAmount(totalCost);
        salesService.addSale(sale);

        showAlert("Успех", String.format("Вы купили инструмент! Списано: %.2f €", totalCost), Alert.AlertType.INFORMATION);
        closeWindow();
    }

    @FXML
    private void returnInstrument() {
        int quantityToReturn;
        try {
            quantityToReturn = Integer.parseInt(tfQuantity.getText());
            if (quantityToReturn <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Введите корректное количество", Alert.AlertType.ERROR);
            return;
        }

        try {
            userInstrumentService.returnInstrument(appUser, instruments, quantityToReturn);
        } catch (IllegalArgumentException e) {
            showAlert("Ошибка", e.getMessage(), Alert.AlertType.ERROR);
            return;
        }

        instruments.setCount(instruments.getCount() + quantityToReturn);
        instrumentsService.update(instruments);

        double refund = quantityToReturn * instruments.getPrice();
        appUser.setBalance(appUser.getBalance() + refund);
        appUserService.update(appUser);

        showAlert("Успех", String.format("Вы вернули инструмент. Возврат: %.2f €", refund), Alert.AlertType.INFORMATION);
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) vbSelectedInstrumentRoot.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void openInstrumentsDetails(Instruments instrument, AppUser appUser) throws IOException {
        if (instrument == null || appUser == null) {
            showAlert("Ошибка", "Инструмент или пользователь не найдены", Alert.AlertType.ERROR);
            return;
        }
        selectedInstrumentFormModalityLoader.load(instrument, appUser);
    }
}
