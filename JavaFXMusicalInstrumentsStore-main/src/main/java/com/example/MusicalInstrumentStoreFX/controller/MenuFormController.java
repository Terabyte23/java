package com.example.MusicalInstrumentStoreFX.controller;

import com.example.MusicalInstrumentStoreFX.MusicalInstrumentStoreFxApplication;
import com.example.MusicalInstrumentStoreFX.loaders.*;
import com.example.MusicalInstrumentStoreFX.service.AppUserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.MusicalInstrumentStoreFX.MusicalInstrumentStoreFxApplication.primaryStage;

@Component
public class MenuFormController implements Initializable {
    private final BrandFormLoader brandFormLoader;
    private final LoginFormLoader loginFormLoader;
    private final RegistrationFormLoader registrationFormLoader;
    private final ListBuyersFormLoader listBuyersFormLoader;
    private final NewInstrumentFormLoader newInstrumentFormLoader;
    private final IncomeReportPageLoader incomeReportPageLoader;

   @FXML private Menu mInstruments;
   @FXML private Menu mAdmin;
   @FXML private Menu mUsers;
   @FXML private MenuItem miEnter;
   @FXML private MenuItem miProfile;
   @FXML private MenuItem miLogout;
   @FXML private Menu mBuyers;

   public MenuFormController(BrandFormLoader brandFormLoader, LoginFormLoader loginFormLoader, NewInstrumentFormLoader newInstrumentFormLoader, RegistrationFormLoader registrationFormLoader, ListBuyersFormLoader listBuyersFormLoader, IncomeReportPageLoader incomeReportPageLoader) {
       this.brandFormLoader = brandFormLoader;
       this.loginFormLoader = loginFormLoader;
       this.newInstrumentFormLoader = newInstrumentFormLoader;
       this.registrationFormLoader = registrationFormLoader;
       this.listBuyersFormLoader = listBuyersFormLoader;
       this.incomeReportPageLoader = incomeReportPageLoader;
   }

   @FXML private void showBrandForm(){
       brandFormLoader.loadBrandForm(primaryStage);
   }
   @FXML private void showInstrumentForm(){
       newInstrumentFormLoader.loadNewInstrumentForm(primaryStage);
   }
   @FXML private void showLoginForm(){
       loginFormLoader.loadLoginForm(primaryStage);
   }
   @FXML private void logout(){
       AppUserService.currentUser = null;
       loginFormLoader.loadLoginForm(primaryStage);
   }
   @FXML private void miAddBuyer(){
        registrationFormLoader.loadRegistrationForm("Добавление покупателя", primaryStage);
   }
    @FXML private void listBuyers() {
        listBuyersFormLoader.loadListBuyersForm(primaryStage);
    }

    @FXML
    private void openIncomeReport() {
        incomeReportPageLoader.loadIncomeReportPage(primaryStage);
    }

    private void initMenuVisible(){
        if (AppUserService.currentUser.getRoles().contains(AppUserService.ROLES.ADMINISTRATOR.toString())) {
            mInstruments.setVisible(true);
            mAdmin.setVisible(true);
            mUsers.setVisible(true);
            miEnter.setVisible(false);
            miProfile.setVisible(true);
            miLogout.setVisible(true);
            mBuyers.setVisible(true);
        } else if(AppUserService.currentUser.getRoles().contains(AppUserService.ROLES.MANAGER.toString())){
            mInstruments.setVisible(true);
            mAdmin.setVisible(false);
            mUsers.setVisible(true);
            miEnter.setVisible(false);
            miProfile.setVisible(true);
            miLogout.setVisible(true);
            mBuyers.setVisible(false);
        } else if(AppUserService.currentUser.getRoles().contains(AppUserService.ROLES.USER.toString())){
            mInstruments.setVisible(false);
            mAdmin.setVisible(false);
            mUsers.setVisible(true);
            miEnter.setVisible(false);
            miProfile.setVisible(true);
            miLogout.setVisible(true);
            mBuyers.setVisible(false);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initMenuVisible();
    }
}
