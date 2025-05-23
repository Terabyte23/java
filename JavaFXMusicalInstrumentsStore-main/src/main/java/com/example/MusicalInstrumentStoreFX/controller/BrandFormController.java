package com.example.MusicalInstrumentStoreFX.controller;

import com.example.MusicalInstrumentStoreFX.loaders.BrandFormLoader;
import com.example.MusicalInstrumentStoreFX.loaders.MainFormLoader;
import com.example.MusicalInstrumentStoreFX.model.entity.Brand;
import com.example.MusicalInstrumentStoreFX.service.BrandService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class BrandFormController {

    private final BrandService brandService;
    private final BrandFormLoader brandFormLoader;
    private final MainFormLoader mainFormLoader;

    @FXML private TextField tfFirstName;

    public BrandFormController(BrandService brandService, BrandFormLoader brandFormLoader, MainFormLoader mainFormLoader) {
        this.brandService = brandService;
        this.brandFormLoader = brandFormLoader;
        this.mainFormLoader = mainFormLoader;
    }

    @FXML
    private void create() {
        Brand brand = new Brand();
        brand.setFirstName(tfFirstName.getText());
        brandService.add(brand);
        brandFormLoader.load();
    }

    @FXML
    private void goToMainForm() {
        mainFormLoader.load();
    }
}
