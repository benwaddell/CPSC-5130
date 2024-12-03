package edu.au.cpsc.checkpoint4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;

import java.io.IOException;

public class QueryApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(QueryApplication.class.getResource("query-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 720);
        stage.setResizable(false);
        stage.setTitle("Hospital Database Query");
        stage.setScene(scene);
        stage.show();
    }

        public static void main(String[] args) {
        launch();
    }
}