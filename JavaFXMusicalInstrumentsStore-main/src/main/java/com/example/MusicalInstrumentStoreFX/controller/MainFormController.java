package com.example.MusicalInstrumentStoreFX.controller;

import com.example.MusicalInstrumentStoreFX.MusicalInstrumentStoreFxApplication;
import com.example.MusicalInstrumentStoreFX.loaders.EditInstrumentFormLoader;
import com.example.MusicalInstrumentStoreFX.loaders.MainFormLoader;
import com.example.MusicalInstrumentStoreFX.loaders.MenuFormLoader;
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
import java.util.stream.Collectors;

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

    @FXML private void showEditInstrumentForm() {
        editInstrumentFormLoader.loadEditInstrumentForm(MusicalInstrumentStoreFxApplication.primaryStage, tvListInstruments.getSelectionModel().getSelectedItem());
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

    // Изменим метод для использования SpringFXMLLoader
    public void openInstrumentsDetails(Instruments instrument) throws IOException {
        // Используем SpringFXMLLoader для загрузки FXML
        FXMLLoader loader = springFXMLLoader.load("/instrument/selectedInstrumentForm.fxml");

        Parent root = loader.load();

        // Получаем контроллер из загруженного FXML
        SelectedInstrumentFormController selectedInstrumentFormController = loader.getController();
        selectedInstrumentFormController.setInstruments(instrument);

        Stage stage = new Stage();
        stage.setTitle("Информация о инструменте");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vbMainFormRoot.getChildren().addFirst(menuFormLoader.loadMenuForm());
        tvListInstruments.setItems(FXCollections.observableArrayList(instrumentsService.getListInstruments()));

        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        // Привязка для брендов
        tcBrands.setCellValueFactory(cellData -> {
            Instruments instrument = cellData.getValue();
            return new SimpleStringProperty(instrument.getBrand() != null ? instrument.getBrand().toString() : "");
        });

        tcPublicationYear.setCellValueFactory(new PropertyValueFactory<>("publicationYear"));
        tcQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tcCount.setCellValueFactory(new PropertyValueFactory<>("count"));

        // Привязка для цены
        tcPrice.setCellValueFactory(cellData -> {
            Instruments instrument = cellData.getValue();
            return new SimpleStringProperty(String.format("%.2f", instrument.getPrice()));
        });

        tvListInstruments.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Instruments>() {
            @Override
            public void changed(ObservableValue<? extends Instruments> observable, Instruments oldValue, Instruments newValue) {
                if (newValue != null) {
                    if (AppUserService.currentUser.getRoles().contains(AppUserService.ROLES.MANAGER.toString())) {
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
                    openInstrumentsDetails(selectedInstrument);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    private void goBackToMenu() {
        // Загружаем главный экран
        mainFormLoader.loadMainForm(MusicalInstrumentStoreFxApplication.primaryStage);
    }


    public void updateTable() {
        tvListInstruments.setItems(instrumentsService.getListInstruments().filtered(instrument -> instrument.getCount() > 0));
    }
}
