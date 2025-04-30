package com.example.MusicalInstrumentStoreFX.controller;

import com.example.MusicalInstrumentStoreFX.MusicalInstrumentStoreFxApplication;
import com.example.MusicalInstrumentStoreFX.loaders.BrandFormLoader;
import com.example.MusicalInstrumentStoreFX.loaders.MainFormLoader;
import com.example.MusicalInstrumentStoreFX.model.entity.Brand;
import com.example.MusicalInstrumentStoreFX.service.BrandService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class BrandFormController implements Initializable {
    private final BrandService brandService;
    private final MainFormLoader mainFormLoader;
    @FXML private TextField tfFirstName;

    public BrandFormController(BrandService brandService, MainFormLoader mainFormLoader) {
        this.brandService = brandService;
        this.mainFormLoader = mainFormLoader;
    }

    @FXML private void create() throws IOException {
        Brand brand = new Brand();
        brand.setFirstName(tfFirstName.getText());
        brandService.add(brand);
        mainFormLoader.loadMainForm(MusicalInstrumentStoreFxApplication.primaryStage);
    }
    @FXML private void goToMainForm() throws IOException {
        mainFormLoader.loadMainForm(MusicalInstrumentStoreFxApplication.primaryStage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
