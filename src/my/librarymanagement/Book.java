/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.librarymanagement;
import java.sql.*;
/**
 *
 * @author Suraj Upadhyay
 */
class Book {
    private String ISBN,Name,Author,AdditionDate;
    private int price,pages;
    public Book(String ISBN,String Name,String Author,String AdditionDate,int price,int pages){
        this.AdditionDate=AdditionDate;
        this.Author=Author;
        this.ISBN=ISBN;
        this.Name=Name;
        this.price=price;
        this.pages=pages;
    }
    String getISBN(){
        return ISBN;
    }
    String getName(){
        return Name;
    }
    String getAuthor(){
        return Author;
    }
    String getAdditionDate(){
        return AdditionDate;
    }
    int getPrice(){return price;}
    int getPages(){return pages;}
}
