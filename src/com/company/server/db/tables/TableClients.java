package com.company.server.db.tables;

import com.company.common.datatools.DataStorage;
import com.company.common.entities.Client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class TableClients
{
    private String url;
    private String login;
    private String password;

    public TableClients(String url, String login, String password) {
        this.url = url;
        this.login = login;
        this.password = password;
    }

    public Client GetClientByLoginAndPassword(String clientLogin, String clientPassword) throws Exception {

        try {
            Class.forName("org.postgresql.Driver");

            Properties props = new Properties();
            props.setProperty("user", login);
            props.setProperty("password", password);
            props.setProperty("ssl", "false");

            Connection connection = DriverManager.getConnection(url, props);

            Statement statement = connection.createStatement();

            String query = String.format("SELECT * FROM library.clients WHERE login='%s' AND Password='%s'", clientLogin, clientPassword);

            ResultSet resultSet = statement.executeQuery(query);

            boolean findClientResult = resultSet.next();
            Client client = null;

            if (findClientResult == false) {
                DataStorage.Add("my_exception", "Ошибка. Клиент не найден");//1
                throw new Exception("Ошибка. Клиент не найден");
            } else {
                client = new Client(
                        resultSet.getInt("id"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("login"),
                        resultSet.getString("password")
                );
            }

            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
            }

            connection.close();

            return client;
        }
        catch (Exception e)
        {
            throw e;
        }
    }
}
