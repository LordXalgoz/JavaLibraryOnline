package com.company.server.controllers;

import com.company.common.communication.Response;
import com.company.common.datatools.DataStorage;
import com.company.common.dto.TakeBookDto;
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

    public static Response GetAllFreeBooksFromLibrary(String parameters) throws Exception {
        try {

            DbManager db = DbManager.GetInstance();

            ArrayList<Book> books = db.TableBooks.GetBooksNotInClientsBooks();

            String status = Response.STATUS_OK;
            String message = new Gson().toJson(books);

            return new Response(status, message);
        } catch (Exception e) {
            DataStorage.Add("my_exception", e.getMessage());
            throw e;
        }
    }



    public static Response AddNewBookForClient(String parameters) throws Exception {
        try {
            TakeBookDto takeBookDto = new Gson().fromJson(parameters, TakeBookDto.class);

            DbManager db = DbManager.GetInstance();

            db.TableBooksClients.InsertNewBookForClient(takeBookDto.IdClient, takeBookDto.IdBook);

            String status = Response.STATUS_OK;
            String message = "";

            return new Response(status, message);
        } catch (Exception e) {
            DataStorage.Add("my_exception", e.getMessage());
            throw e;
        }
    }


}
