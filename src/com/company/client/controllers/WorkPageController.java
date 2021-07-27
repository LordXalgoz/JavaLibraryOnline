package com.company.client.controllers;

import com.company.client.Main;
import com.company.client.api.ApiWorker;
import com.company.common.communication.General;
import com.company.common.communication.Response;
import com.company.common.datatools.DataStorage;
import com.company.common.dto.TakeBookDto;
import com.company.common.entities.Book;
import com.company.common.entities.Client;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class WorkPageController {

    @FXML
    Label labelFIO;

    @FXML
    ListView listViewClientsBooks;

    @FXML
    TextField textFieldClientBookName;

    @FXML
    TextField textFieldClientBookAuthor;

    ApiWorker apiWorker;
    Client client;

    Book selectedBook;

    private void ShowDialog(String message) {
        new Alert(Alert.AlertType.CONFIRMATION, message).showAndWait();
    }

    @FXML
    public void initialize() {
        apiWorker = new ApiWorker(General.SERVER_PORT);
        client = (Client) DataStorage.Get("current_client");
        labelFIO.setText("Добро пожаловать " + client.FirstName + " " + client.LastName);

        LoadClientBooks();

        listViewClientsBooks.getSelectionModel().selectedItemProperty().addListener(listViewClientBooksSelectedItemListener);
    }

    ChangeListener<String> listViewClientBooksSelectedItemListener = new ChangeListener<String>(){
        public void changed(ObservableValue<? extends String> changed, String oldValue, String newValue){
            try {
                int index = listViewClientsBooks.getSelectionModel().getSelectedIndex();
                selectedBook = client.Books.get(index);
                textFieldClientBookName.setText(selectedBook.Name);
                textFieldClientBookAuthor.setText(selectedBook.Author);
            }
            catch (Exception e)
            {
                return;
            }
        }
    };

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

    public void buttonGoToStorageClick(MouseEvent mouseEvent) throws Exception{
        Main.GoToPage(Main.STORAGE_PAGE);
    }

    public void buttonReturnBookToLibrary(MouseEvent mouseEvent) {
        TakeBookDto takeBookDto = new TakeBookDto(client.Id, selectedBook.Id);

        try {
            Response response = apiWorker.BooksReturnBookToLibrary(takeBookDto);

            switch (response.Status){
                case Response.STATUS_OK:
                    LoadClientBooks();
                    textFieldClientBookName.clear();
                    textFieldClientBookAuthor.clear();
                    ShowDialog("Успешна вернута книга");
                    break;
                case Response.STATUS_ERROR:
                    ShowDialog("Ошибка сервера: " + response.Message);
                    break;
            }

        } catch (Exception e) {
            ShowDialog("Ошибка отправки на сервер: " + e.toString());
        }
    }
}