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
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class StoragePageController
{
    @FXML
    ListView listViewCurrentBooks;

    ApiWorker apiWorker;
    Client client;

    ArrayList<Book> books;

    @FXML
    public void initialize() {
        apiWorker = new ApiWorker(General.SERVER_PORT);
        client = (Client) DataStorage.Get("current_client");

        LoadCurrentBooks();
    }

    private ObservableList<String> CurrentBooksToStrings() {
        ObservableList<String> strings = FXCollections.observableArrayList();

        for (int i = 0; i < books.size(); i++) {
            String string = "Название: " + books.get(i).Name + " Жанр: " + books.get(i).Genre + " Автор: " + books.get(i).Author;
            strings.add(string);
        }

        return strings;
    }

    private void LoadCurrentBooks() {
        try {
            Response response = apiWorker.BooksGetAllBooksFromLibrary(client.Id);

            switch (response.Status) {
                case Response.STATUS_OK:

                    Type listType = new TypeToken<ArrayList<Book>>() {
                    }.getType();
                    books = new Gson().fromJson(response.Message, listType);

                    listViewCurrentBooks.setItems(CurrentBooksToStrings());
                    break;
                case Response.STATUS_ERROR:
                    ShowDialog("Ошибка сервера: " + response.Message);
                    break;
            }
        } catch (Exception e) {
            ShowDialog("Ошибка отправки на сервер: " + e.toString());
        }
    }

    private void ShowDialog(String message) {
        new Alert(Alert.AlertType.CONFIRMATION, message).showAndWait();
    }

    public void buttonBackToClientClick(MouseEvent mouseEvent)
    {
        try {
            Main.GoToPage(Main.WORK_PAGE);

        } catch (Exception e) {
            ShowDialog("Ошибка отправки на сервер: " + e.toString());
        }
    }

    public void buttonAddBookClick(MouseEvent mouseEvent)
    {

    }

    public void buttonLoadBookClick(MouseEvent mouseEvent)
    {
        LoadCurrentBooks();
    }
}
