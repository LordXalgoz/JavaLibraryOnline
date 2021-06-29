package com.company.server.db.tables;

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
}
