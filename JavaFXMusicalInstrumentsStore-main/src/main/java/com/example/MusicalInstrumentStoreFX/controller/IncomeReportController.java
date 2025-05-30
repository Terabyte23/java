package com.example.MusicalInstrumentStoreFX.controller;

import com.example.MusicalInstrumentStoreFX.loaders.MainFormLoader;
import com.example.MusicalInstrumentStoreFX.service.SalesService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

@Component
public class IncomeReportController implements Initializable {

    @Autowired private SalesService salesService;
    @Autowired private MainFormLoader mainFormLoader;

    @FXML private Label lblIncome;
    @FXML private Label lblAmount;

    @FXML
    private void goBackToMainMenu() {
        mainFormLoader.load();
    }

    @FXML
    private void selectWeek() {
        updateIncomeLabel(LocalDate.now().minusWeeks(1), LocalDate.now());
    }

    @FXML
    private void selectMonth() {
        updateIncomeLabel(LocalDate.now().minusMonths(1), LocalDate.now());
    }

    @FXML
    private void selectYear() {
        updateIncomeLabel(LocalDate.now().minusYears(1), LocalDate.now());
    }

    private void updateIncomeLabel(LocalDate from, LocalDate to) {
        double income = salesService.getIncomeForPeriod(from, to);
        lblAmount.setText(String.format("%.2f €", income));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // При открытии сразу показываем доход за текущий месяц
        selectMonth();
    }
}
