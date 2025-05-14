package com.example.MusicalInstrumentStoreFX;

import com.example.MusicalInstrumentStoreFX.loaders.LoginFormLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MusicalInstrumentStoreFxApplication extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		LoginFormLoader loginFormLoader = SpringApplication.run(MusicalInstrumentStoreFxApplication.class).getBean(LoginFormLoader.class);
		loginFormLoader.setPrimaryStage(primaryStage);
		loginFormLoader.load();
	}
}
