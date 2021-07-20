package com.company.server.db.tables;

import com.company.common.entities.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class TableBooks {
    private String url;
    private String login;
    private String password;

    public TableBooks(String url, String login, String password) {
        this.url = url;
        this.login = login;
        this.password = password;
    }

    public ArrayList<Book> GetBooksByIdClient(int idClient) throws Exception
    {
        try {
            Class.forName("org.postgresql.Driver");

            Properties props = new Properties();
            props.setProperty("user", login);
            props.setProperty("password", password);
            props.setProperty("ssl", "false");

            Connection connection = DriverManager.getConnection(url, props);

            Statement statement = connection.createStatement();

            String query = String.format("SELECT * FROM library.books WHERE id IN(SELECT idbook FROM library.clientsbooks WHERE idclient=%d)", idClient);

            ResultSet resultSet = statement.executeQuery(query);

            ArrayList<Book> books = new ArrayList<>();

            while (resultSet.next() == true) {
                Book card = new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("genre"),
                        resultSet.getString("author"),
                        resultSet.getInt("raiting")
                );

                books.add(card);
            }

            connection.close();

            return books;
        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<Book> GetBooksNotInClientsBooks(int idClient) throws Exception
    {
        try {
            Class.forName("org.postgresql.Driver");

            Properties props = new Properties();
            props.setProperty("user", login);
            props.setProperty("password", password);
            props.setProperty("ssl", "false");

            Connection connection = DriverManager.getConnection(url, props);

            Statement statement = connection.createStatement();

            String query = String.format("SELECT * FROM library.books WHERE id NOT IN(SELECT idbook FROM library.clientsbooks)", idClient);

            ResultSet resultSet = statement.executeQuery(query);

            ArrayList<Book> books = new ArrayList<>();

            while (resultSet.next() == true) {
                Book card = new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("genre"),
                        resultSet.getString("author"),
                        resultSet.getInt("raiting")
                );

                books.add(card);
            }

            connection.close();

            return books;
        } catch (Exception e) {
            throw e;
        }

        public int GetBookById() throws Exception
        {
            try {
                Class.forName("org.postgresql.Driver");

                Properties props = new Properties();
                props.setProperty("user", login);
                props.setProperty("password", password);
                props.setProperty("ssl", "false");

                Connection connection = DriverManager.getConnection(url, props);

                Statement statement = connection.createStatement();

                String query = String.format("SELECT * FROM sberbank.books");

                ResultSet resultSet = statement.executeQuery(query);

                resultSet.next();
                int idBook = resultSet.getInt(1);

                connection.close();

                return idBook;
            } catch (Exception e) {
                throw e;
            }
        }
    }
}
