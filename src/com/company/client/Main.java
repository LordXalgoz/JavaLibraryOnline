package com.company.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    public static AnchorPane root;
    public static ArrayList<FXMLLoader> loaders = new ArrayList<>();

    public static final int AUTH_PAGE = 0;
    public static final int WORK_PAGE = 1;
    public static final int STORAGE_PAGE = 2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("main.fxml"));

        loaders.add(new FXMLLoader(getClass().getResource("views/AuthPage.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("views/WorkPage.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("views/StoragePage.fxml")));

        GoToPage(AUTH_PAGE);

        primaryStage.setTitle("Библиотека Онлайн ЕЕЕЕЕЕ");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }
    /////////////////
    public static void GoToPage(int page) throws IOException {
        root.getChildren().clear();
        if (loaders.get(page).getRoot() == null) {
            root.getChildren().add(loaders.get(page).load());
        }
        else {
            root.getChildren().add(loaders.get(page).getRoot());
        }
    }
}