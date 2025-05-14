package com.example.MusicalInstrumentStoreFX.controller;

import com.example.MusicalInstrumentStoreFX.loaders.ListBuyersFormLoader;
import com.example.MusicalInstrumentStoreFX.model.entity.AppUser;
import com.example.MusicalInstrumentStoreFX.service.AppUserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.*;

@Component
public class EditBuyerFormController implements Initializable {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private ListBuyersFormLoader listBuyersFormLoader;

    private AppUser editBuyer;

    @FXML private TextField tfId;
    @FXML private TextField tfLogin;
    @FXML private TextField tfFirstName;
    @FXML private TextField tfLastName;
    @FXML private ComboBox<String> rolesComboBox;

    @FXML
    private void saveBuyer() {
        if (editBuyer == null) {
            System.out.println("Редактируемый пользователь не установлен!");
            return;
        }

        editBuyer.setUsername(tfLogin.getText());
        editBuyer.setFirstname(tfFirstName.getText());
        editBuyer.setLastname(tfLastName.getText());
        editBuyer.setRoles(new HashSet<>(Collections.singletonList(
                rolesComboBox.getValue() != null ? rolesComboBox.getValue() : "USER"
        )));

        appUserService.save(editBuyer);
        listBuyersFormLoader.load(); // Теперь используем обобщённый метод
    }

    @FXML
    private void cancelEdit() {
        listBuyersFormLoader.load();
    }

    public void setEditBuyer(AppUser editBuyer) {
        this.editBuyer = editBuyer;

        tfId.setText(String.valueOf(editBuyer.getId()));
        tfLogin.setText(editBuyer.getUsername());
        tfFirstName.setText(editBuyer.getFirstname());
        tfLastName.setText(editBuyer.getLastname());

        rolesComboBox.getItems().setAll("USER", "ADMIN");

        if (!editBuyer.getRoles().isEmpty()) {
            rolesComboBox.getSelectionModel().select(editBuyer.getRoles().iterator().next());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rolesComboBox.getItems().setAll("USER", "ADMIN");
    }
}
