package com.company.server.db;

import com.company.server.db.tables.TableBooks;
import com.company.server.db.tables.TableBooksClients;
import com.company.server.db.tables.TableClients;

public class DbManager
{
    private static DbManager instance = null;

    private String URL = "jdbc:postgresql://localhost:5432/postgres";
    private String LOGIN = "postgres";
    private String PASSWORD = "P!ssword12345";

    public TableBooks TableBooks;
    public TableClients TableClients;
    public TableBooksClients TableBooksClients;

    private static DbManager GetInstance()
    {
        if(instance==null)
        {
            instance = new DbManager();
        }
        return instance;
    }

}
