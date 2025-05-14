package com.example.MusicalInstrumentStoreFX.controller;

import com.example.MusicalInstrumentStoreFX.loaders.MainFormLoader;
import com.example.MusicalInstrumentStoreFX.model.entity.Brand;
import com.example.MusicalInstrumentStoreFX.model.entity.Instruments;
import com.example.MusicalInstrumentStoreFX.service.BrandService;
import com.example.MusicalInstrumentStoreFX.service.InstrumentsService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class EditInstrumentsFormController implements Initializable {

    @Autowired private InstrumentsService instrumentsService;
    @Autowired private BrandService brandService;
    @Autowired private MainFormLoader mainFormLoader;

    private Instruments editInstruments;

    @FXML private TextField tfId;
    @FXML private TextField tfTitle;
    @FXML private ListView<Brand> lvBrands;
    @FXML private TextField tfPublicationYear;
    @FXML private TextField tfQuantity;
    @FXML private TextField tfCount;

    @FXML
    private void goEdit() {
        if (editInstruments == null) return;

        editInstruments.setTitle(tfTitle.getText());
        editInstruments.getBrand().clear();
        editInstruments.getBrand().addAll(lvBrands.getSelectionModel().getSelectedItems());
        editInstruments.setPublicationYear(Integer.parseInt(tfPublicationYear.getText()));
        editInstruments.setQuantity(Integer.parseInt(tfQuantity.getText()));
        editInstruments.setCount(editInstruments.getQuantity());

        instrumentsService.create(editInstruments);
        mainFormLoader.load();
    }

    @FXML
    private void deleteInstrument() {
        if (tfId.getText() != null && !tfId.getText().isEmpty()) {
            Long instrumentId = Long.parseLong(tfId.getText());
            instrumentsService.deleteInstrumentAndResortIds(instrumentId);
            mainFormLoader.load();
        }
    }

    @FXML
    private void goToMainForm() {
        mainFormLoader.load();
    }

    public void setEditInstruments(Instruments editInstruments) {
        this.editInstruments = editInstruments;

        tfId.setText(String.valueOf(editInstruments.getId()));
        tfTitle.setText(editInstruments.getTitle());
        tfPublicationYear.setText(String.valueOf(editInstruments.getPublicationYear()));
        tfQuantity.setText(String.valueOf(editInstruments.getQuantity()));
        tfCount.setText(String.valueOf(editInstruments.getCount()));

        // Отмечаем связанные бренды
        lvBrands.getSelectionModel().clearSelection();
        for (Brand brand : editInstruments.getBrand()) {
            int index = lvBrands.getItems().indexOf(brand);
            if (index >= 0) {
                lvBrands.getSelectionModel().select(index);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lvBrands.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        List<Brand> brands = brandService.getListBrand();
        lvBrands.setItems(FXCollections.observableArrayList(brands));

        lvBrands.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Brand brand, boolean empty) {
                super.updateItem(brand, empty);
                setText(empty || brand == null ? null : "ID: " + brand.getId() + " - " + brand.getFirstName());
            }
        });
    }
}
