package com.example.MusicalInstrumentStoreFX.controller;

import com.example.MusicalInstrumentStoreFX.loaders.EditBuyersFormLoader;
import com.example.MusicalInstrumentStoreFX.loaders.MainFormLoader;
import com.example.MusicalInstrumentStoreFX.loaders.WalletDialogLoader;
import com.example.MusicalInstrumentStoreFX.model.entity.AppUser;
import com.example.MusicalInstrumentStoreFX.service.AppUserService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListBuyersFormController {

    @Autowired private AppUserService appUserService;
    @Autowired private MainFormLoader mainFormLoader;
    @Autowired private EditBuyersFormLoader editBuyersFormLoader;
    @Autowired private WalletDialogLoader walletDialogLoader;

    @FXML private TableView<AppUser> tvListBuyers;
    @FXML private TableColumn<AppUser, Long> tcIdBuyers;
    @FXML private TableColumn<AppUser, String> tcUsernameBuyers;
    @FXML private TableColumn<AppUser, String> tcFirstnameBuyers;
    @FXML private TableColumn<AppUser, String> tcLastnameBuyers;
    @FXML private TableColumn<AppUser, String> tcRolesBuyers;
    @FXML private TableColumn<AppUser, Double> tcBalanceBuyers;
    @FXML private Button editBuyerButton;

    @FXML
    public void initialize() {
        tcIdBuyers.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());
        tcUsernameBuyers.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        tcFirstnameBuyers.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstname()));
        tcLastnameBuyers.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastname()));
        tcRolesBuyers.setCellValueFactory(cellData -> new SimpleStringProperty(String.join(", ", cellData.getValue().getRoles())));
        tcBalanceBuyers.setCellValueFactory(new PropertyValueFactory<>("balance"));

        loadBuyers();

        if (editBuyerButton != null) {
            editBuyerButton.setVisible(false);
            tvListBuyers.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                editBuyerButton.setVisible(newVal != null);
            });
        }

        tvListBuyers.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !tvListBuyers.getSelectionModel().isEmpty()) {
                AppUser selectedUser = tvListBuyers.getSelectionModel().getSelectedItem();
                openWalletDialog(selectedUser);
                tvListBuyers.refresh();
            }
        });
    }

    private void loadBuyers() {
        List<AppUser> buyers = appUserService.getAllUsers();
        tvListBuyers.getItems().setAll(buyers);
    }

    @FXML
    private void showEditBuyersForm(ActionEvent event) {
        AppUser selectedBuyer = tvListBuyers.getSelectionModel().getSelectedItem();
        if (selectedBuyer != null) {
            editBuyersFormLoader.load(selectedBuyer);
        }
    }

    @FXML
    private void onEditButtonClick() {
        AppUser selectedUser = tvListBuyers.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            editBuyersFormLoader.load(selectedUser);
        }
    }

    @FXML
    private void showDeleteBuyersForm(ActionEvent event) {
        System.out.println("Удаление покупателя — логика не реализована.");
    }

    @FXML
    private void goBackToMainForm() {
        mainFormLoader.load();
    }

    public void updateBuyer(AppUser updatedBuyer) {
        loadBuyers();
    }

    private void openWalletDialog(AppUser appUser) {
        walletDialogLoader.load(appUser);
    }
}
