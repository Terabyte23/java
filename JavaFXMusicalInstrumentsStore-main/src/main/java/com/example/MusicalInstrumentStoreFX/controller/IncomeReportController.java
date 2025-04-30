package com.example.MusicalInstrumentStoreFX.controller;

import com.example.MusicalInstrumentStoreFX.loaders.MainFormLoader;
import com.example.MusicalInstrumentStoreFX.service.SalesService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class IncomeReportController {

    private final SalesService salesService;
    private final MainFormLoader mainFormLoader;

    @FXML
    private Label lblIncome;
    @FXML
    private Label lblAmount;

    public IncomeReportController(SalesService salesService, MainFormLoader mainFormLoader) {
        this.mainFormLoader = mainFormLoader;
        this.salesService = salesService;
    }

    @FXML
    private void goBackToMainMenu() {
        Stage primaryStage = (Stage) lblAmount.getScene().getWindow();
        mainFormLoader.loadMainForm(primaryStage);
    }


    @FXML
    private void selectWeek() {
        double weeklyIncome = salesService.getIncomeForPeriod(LocalDate.now().minusWeeks(1), LocalDate.now());
        lblAmount.setText(String.format("%.2f", weeklyIncome));
    }

    @FXML
    private void selectMonth() {
        double monthlyIncome = salesService.getIncomeForPeriod(LocalDate.now().minusMonths(1), LocalDate.now());
        lblAmount.setText(String.format("%.2f", monthlyIncome));
    }

    @FXML
    private void selectYear() {
        double yearlyIncome = salesService.getIncomeForPeriod(LocalDate.now().minusYears(1), LocalDate.now());
        lblAmount.setText(String.format("%.2f", yearlyIncome));
    }
}
