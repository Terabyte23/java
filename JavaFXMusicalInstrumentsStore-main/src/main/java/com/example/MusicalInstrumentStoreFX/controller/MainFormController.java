package com.example.MusicalInstrumentStoreFX.controller;

import com.example.MusicalInstrumentStoreFX.loaders.EditInstrumentFormLoader;
import com.example.MusicalInstrumentStoreFX.loaders.MainFormLoader;
import com.example.MusicalInstrumentStoreFX.loaders.MenuFormLoader;
import com.example.MusicalInstrumentStoreFX.model.entity.AppUser;
import com.example.MusicalInstrumentStoreFX.model.entity.Instruments;
import com.example.MusicalInstrumentStoreFX.service.AppUserService;
import com.example.MusicalInstrumentStoreFX.service.InstrumentsService;
import com.example.MusicalInstrumentStoreFX.tools.SpringFXMLLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


@Component
public class MainFormController implements Initializable {

    @Autowired
    private EditInstrumentFormLoader editInstrumentFormLoader;
    @Autowired
    private InstrumentsService instrumentsService;
    @Autowired
    private SpringFXMLLoader springFXMLLoader;
    @Autowired
    private MenuFormLoader menuFormLoader;
    @Autowired
    private AppUserService appUserService; // Для получения текущего пользователя
    private AppUser currentAppUser;

    @FXML private VBox vbMainFormRoot;
    @FXML private TableView<Instruments> tvListInstruments;
    @FXML private TableColumn<Instruments, String> tcId;
    @FXML private TableColumn<Instruments, String> tcTitle;
    @FXML private TableColumn<Instruments, String> tcBrands;
    @FXML private TableColumn<Instruments, String> tcPublicationYear;
    @FXML private TableColumn<Instruments, String> tcQuantity;
    @FXML private TableColumn<Instruments, String> tcCount;
    @FXML private TableColumn<Instruments, String> tcPrice;
    @FXML private HBox hbEditInstruments;
    @Autowired
    private MenuFormController menuFormController;
    @Autowired
    private MainFormLoader mainFormLoader;

    // Устанавливаем текущего пользователя
    public void setCurrentAppUser(AppUser appUser) {
        this.currentAppUser = appUser;
    }

    @FXML
    private void showEditInstrumentForm() {
        editInstrumentFormLoader.load(tvListInstruments.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void showDeleteInstrumentForm() {
        Instruments selectedInstrument = tvListInstruments.getSelectionModel().getSelectedItem();

        if (selectedInstrument != null) {
            instrumentsService.deleteInstrumentAndResortIds(selectedInstrument.getId());
            refreshInstrumentsTable();
        } else {
            System.out.println("Ничего не выбрано для удаления.");
        }
    }

    private void refreshInstrumentsTable() {
        tvListInstruments.setItems(FXCollections.observableArrayList(instrumentsService.getListInstruments()));
    }

    public void openInstrumentsDetails(Instruments instrument, AppUser appUser) throws IOException {
        FXMLLoader loader = springFXMLLoader.load("/instrument/selectedInstrumentForm.fxml");
        Parent root = loader.load();

        SelectedInstrumentFormController selectedInstrumentFormController = loader.getController();
        selectedInstrumentFormController.setInstruments(instrument);
        selectedInstrumentFormController.setAppUser(appUser);

        Stage stage = new Stage();
        stage.setTitle("Информация о инструменте");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Пример установки текущего пользователя (можно заменить на другой способ получения пользователя)
        currentAppUser = appUserService.getCurrentUser(); // Получаем текущего пользователя из сервиса

        vbMainFormRoot.getChildren().addFirst(menuFormLoader.load());
        tvListInstruments.setItems(FXCollections.observableArrayList(instrumentsService.getListInstruments()));

        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tcBrands.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBrand() != null ? cellData.getValue().getBrand().toString() : ""));
        tcPublicationYear.setCellValueFactory(new PropertyValueFactory<>("publicationYear"));
        tcQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tcCount.setCellValueFactory(new PropertyValueFactory<>("count"));
        tcPrice.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("%.2f", cellData.getValue().getPrice())));

        tvListInstruments.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Instruments>() {
            @Override
            public void changed(ObservableValue<? extends Instruments> observable, Instruments oldValue, Instruments newValue) {
                if (newValue != null) {
                    if (currentAppUser != null && currentAppUser.getRoles().contains(AppUserService.ROLES.MANAGER.toString())) {
                        hbEditInstruments.setVisible(true);
                    } else {
                        hbEditInstruments.setVisible(false);
                    }
                }
            }
        });

        tvListInstruments.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !tvListInstruments.getSelectionModel().isEmpty()) {
                Instruments selectedInstrument = tvListInstruments.getSelectionModel().getSelectedItem();
                try {
                    // Убедитесь, что текущий пользователь не null
                    if (currentAppUser != null) {
                        openInstrumentsDetails(selectedInstrument, currentAppUser); // Передаем правильного пользователя
                    } else {
                        showAlert("Ошибка", "Пользователь не найден. Пожалуйста, войдите в систему.", Alert.AlertType.ERROR);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    private void goBackToMenu() {
        // Загружаем главный экран
        mainFormLoader.load();
    }

    public void updateTable() {
        tvListInstruments.setItems(instrumentsService.getListInstruments().filtered(instrument -> instrument.getCount() > 0));
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
