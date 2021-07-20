package com.company.client.controllers;

import com.company.client.Main;
import com.company.client.api.ApiWorker;
import com.company.common.communication.General;
import com.company.common.communication.Response;
import com.company.common.datatools.DataStorage;
import com.company.common.entities.Book;
import com.company.common.entities.Client;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class WorkPageController {

    @FXML
    Label labelFIO;

    @FXML
    ListView listViewClientsBooks;

    ApiWorker apiWorker;
    Client client;

    private void ShowDialog(String message) {
        new Alert(Alert.AlertType.CONFIRMATION, message).showAndWait();
    }

    @FXML
    public void initialize() {
        apiWorker = new ApiWorker(General.SERVER_PORT);
        client = (Client) DataStorage.Get("current_client");
        labelFIO.setText("Добро пожаловать " + client.FirstName + " " + client.LastName);

        LoadClientBooks();
    }

    private ObservableList<String> ClientBooksToStrings() {
        ObservableList<String> strings = FXCollections.observableArrayList();

        for (int i = 0; i < client.Books.size(); i++) {
            String string = "Название: " + client.Books.get(i).Name + " Жанр: " + client.Books.get(i).Genre + " Автор: " + client.Books.get(i).Author;
            strings.add(string);
        }

        return strings;
    }

    private void LoadClientBooks() {
        try {
            Response response = apiWorker.BooksGetAllBooksForClient(client.Id);

            switch (response.Status) {
                case Response.STATUS_OK:

                    Type listType = new TypeToken<ArrayList<Book>>() {
                    }.getType();
                    client.Books = new Gson().fromJson(response.Message, listType);

                    listViewClientsBooks.setItems(ClientBooksToStrings());
                    break;
                case Response.STATUS_ERROR:
                    ShowDialog("Ошибка сервера: " + response.Message);
                    break;
            }
        } catch (Exception e) {
            ShowDialog("Ошибка отправки на сервер: " + e.toString());
        }
    }

    public void buttonLoadBooksClick(MouseEvent mouseEvent) {
        LoadClientBooks();
    }

    public void buttonGoToStorageClick(MouseEvent mouseEvent) {
        try {
        Main.GoToPage(Main.STORAGE_PAGE);

    } catch (Exception e) {
        ShowDialog("Ошибка отправки на сервер: " + e.toString());
    }
        /*try {
            Response response = ;

            switch (response.Status){
                case Response.STATUS_OK:
                    Main.GoToPage(Main.STORAGE_PAGE);
                    break;
                case Response.STATUS_ERROR:
                    ShowDialog("Ошибка сервера: " + response.Message);
                    break;
            }

        } catch (Exception e) {
            ShowDialog("Ошибка отправки на сервер: " + e.toString());
        }*/

    }
}