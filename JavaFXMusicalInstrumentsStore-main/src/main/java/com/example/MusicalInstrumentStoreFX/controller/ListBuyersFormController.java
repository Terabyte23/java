package com.example.MusicalInstrumentStoreFX.controller;

import com.example.MusicalInstrumentStoreFX.MusicalInstrumentStoreFxApplication;
import com.example.MusicalInstrumentStoreFX.loaders.EditBuyersFormLoader;
import com.example.MusicalInstrumentStoreFX.loaders.MainFormLoader;
import com.example.MusicalInstrumentStoreFX.loaders.WalletDialogLoader;
import com.example.MusicalInstrumentStoreFX.model.entity.AppUser;
import com.example.MusicalInstrumentStoreFX.service.AppUserService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ListBuyersFormController {

    private final AppUserService appUserService;
    private final MainFormLoader mainFormLoader;
    private final EditBuyersFormLoader editBuyersFormLoader;
    private final WalletDialogLoader walletDialogLoader;

    @FXML
    private TableView<AppUser> tvListBuyers;
    @FXML
    private TableColumn<AppUser, Long> tcIdBuyers;
    @FXML
    private TableColumn<AppUser, String> tcUsernameBuyers;
    @FXML
    private TableColumn<AppUser, String> tcFirstnameBuyers;
    @FXML
    private TableColumn<AppUser, String> tcLastnameBuyers;
    @FXML
    private TableColumn<AppUser, String> tcRolesBuyers;
    @FXML
    private Button editBuyerButton;
    @FXML
    private TableColumn<AppUser, Double> tcBalanceBuyers;

    public ListBuyersFormController(AppUserService appUserService, MainFormLoader mainFormLoader, EditBuyersFormLoader editBuyersFormLoader, WalletDialogLoader walletDialogLoader) {
        this.appUserService = appUserService;
        this.mainFormLoader = mainFormLoader;
        this.editBuyersFormLoader = editBuyersFormLoader;
        this.walletDialogLoader = walletDialogLoader;
    }

    @FXML
    public void initialize() {
        tcIdBuyers.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());
        tcUsernameBuyers.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        tcFirstnameBuyers.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstname()));
        tcLastnameBuyers.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastname()));
        tcRolesBuyers.setCellValueFactory(cellData -> new SimpleStringProperty(String.join(", ", cellData.getValue().getRoles())));
        loadBuyers();

        if (editBuyerButton != null) {
            editBuyerButton.setVisible(false);
            tvListBuyers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                editBuyerButton.setVisible(newValue != null);
            });
        }
        tcBalanceBuyers.setCellValueFactory(new PropertyValueFactory<>("balance"));
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
            editBuyersFormLoader.loadEditBuyersForm(selectedBuyer, MusicalInstrumentStoreFxApplication.primaryStage);
        }
    }

    @FXML
    private void showDeleteBuyersForm(ActionEvent event) {
        System.out.println("Удаление покупателя");
    }

    @FXML
    private void goBackToMainForm() {
        mainFormLoader.loadMainForm(MusicalInstrumentStoreFxApplication.primaryStage);
    }

    public void updateBuyer(AppUser updatedBuyer) {
        List<AppUser> users = appUserService.getAllUsers();
        tvListBuyers.getItems().setAll(users);
    }

    @FXML
    private void onEditButtonClick() {
        AppUser selectedUser = tvListBuyers.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            editBuyersFormLoader.loadEditBuyersForm(selectedUser, MusicalInstrumentStoreFxApplication.primaryStage);
        }
    }

    private void openWalletDialog(AppUser appUser) {
        walletDialogLoader.showWalletDialog(appUser);
    }

}
