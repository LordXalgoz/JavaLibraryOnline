package com.company.common.dto;

public class TakeBookDto
{
    public int IdClient;
    public int IdBook;

    public TakeBookDto(int idClient, int idBook) {
        IdClient = idClient;
        IdBook = idBook;
    }
}
