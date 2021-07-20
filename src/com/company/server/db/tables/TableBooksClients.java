package com.company.server.db.tables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class TableBooksClients
{
    private String url;
    private String login;
    private String password;

    public TableBooksClients(String url, String login, String password) {
        this.url = url;
        this.login = login;
        this.password = password;
    }

    public void InsertNewBookForClient(int idClient, int idBook) throws Exception
    {
        try {
            Class.forName("org.postgresql.Driver");

            Properties props = new Properties();
            props.setProperty("user", login);
            props.setProperty("password", password);
            props.setProperty("ssl", "false");

            Connection connection = DriverManager.getConnection(url, props);

            Statement statement = connection.createStatement();

            String query = String.format("INSERT INTO library.clientsbooks (idclient, idbook) VALUES (%d,%d)", idClient, idBook);

            statement.executeUpdate(query);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
}
