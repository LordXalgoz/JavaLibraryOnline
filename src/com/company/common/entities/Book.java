package com.company.common.entities;

public class Book
{
    public int Id;
    public String Name;
    public String Genre;
    public String Author;
    public int Raiting;

    public Book(int id, String name, String genre, String author, int raiting) {
        Id = id;
        Name = name;
        Genre = genre;
        Author = author;
        Raiting = raiting;
    }
}
