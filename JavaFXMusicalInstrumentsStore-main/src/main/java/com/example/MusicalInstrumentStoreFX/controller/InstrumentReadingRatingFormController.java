package com.example.MusicalInstrumentStoreFX.controller;

import com.example.MusicalInstrumentStoreFX.service.SalesService;
import com.example.MusicalInstrumentStoreFX.viewmodel.InstrumentRatingViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Controller
public class InstrumentReadingRatingFormController implements Initializable {

    private final SalesService salesService;

    @FXML
    private Label lbInfo;
    @FXML
    private DatePicker dpBeforeDate;
    @FXML
    private DatePicker dpAfterDate;
    @FXML
    private TableView<InstrumentRatingViewModel> tvInstrumentRatingView;
    @FXML
    private TableColumn<InstrumentRatingViewModel, String> tcId;
    @FXML
    private TableColumn<InstrumentRatingViewModel, String> tcTitle;
    @FXML
    private TableColumn<InstrumentRatingViewModel, String> tcType;
    @FXML
    private TableColumn<InstrumentRatingViewModel, String> tcPrice;
    @FXML
    private TableColumn<InstrumentRatingViewModel, String> tcRating;

    public InstrumentReadingRatingFormController(SalesService salesService) {
        this.salesService = salesService;
    }

    @FXML
    private void calculateRating() {
        LocalDate from = dpBeforeDate.getValue();
        LocalDate to = dpAfterDate.getValue();
        if (from == null || to == null) {
            lbInfo.setText("Пожалуйста, выберите обе даты.");
            return;
        }
        lbInfo.setText("Рейтинг инструментов за выбранный период:");
        // Здесь вызываем метод из сервиса для получения данных по инструментам
        var ratings = salesService.getInstrumentSalesRating(from, to);
        tvInstrumentRatingView.setItems(FXCollections.observableArrayList(ratings));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Настроим отображение столбцов в таблице
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInstrument().getId().toString()));
        tcTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        tcType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInstrument().getType()));
        tcPrice.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getInstrument().getPrice())));
        tcRating.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCount().toString()));

        // Настроим ограничение выбора дат
        dpAfterDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (dpBeforeDate.getValue() != null && item.isBefore(dpBeforeDate.getValue())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });
    }
}
