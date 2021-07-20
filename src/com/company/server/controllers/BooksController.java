package com.company.server.controllers;

import com.company.common.communication.Response;
import com.company.common.datatools.DataStorage;
import com.company.common.entities.Book;
import com.company.server.db.DbManager;
import com.google.gson.Gson;

import java.util.ArrayList;

public class BooksController
{
    public static Response GetAllBooksForClient(String parameters) throws Exception {
        try {
            int idClient = Integer.parseInt(parameters);

            DbManager db = DbManager.GetInstance();

            ArrayList<Book> books = db.TableBooks.GetBooksByIdClient(idClient);

            String status = Response.STATUS_OK;
            String message = new Gson().toJson(books);

            return new Response(status, message);
        } catch (Exception e) {
            DataStorage.Add("my_exception", e.getMessage());
            throw e;
        }
    }

    public static Response GetAllBooksFromLibrary(String parameters) throws Exception {
        try {
            int idClient = Integer.parseInt(parameters);

            DbManager db = DbManager.GetInstance();

            ArrayList<Book> books = db.TableBooks.GetBooksNotInClientsBooks(idClient);

            String status = Response.STATUS_OK;
            String message = new Gson().toJson(books);

            return new Response(status, message);
        } catch (Exception e) {
            DataStorage.Add("my_exception", e.getMessage());
            throw e;
        }
    }

    public static Response AddNewCardForClient(String parameters) throws Exception {
        try {
            int idClient = Integer.parseInt(parameters);

            DbManager db = DbManager.GetInstance();

            int idBook = db.TableBooks.GetLastInserted;

            db.TableCardsClients.InsertNewBookForClient(idClient, idBook);

            String status = Response.STATUS_OK;
            String message = "";

            return new Response(status, message);
        } catch (Exception e) {
            DataStorage.Add("my_exception", e.getMessage());
            throw e;
        }
    }
}
