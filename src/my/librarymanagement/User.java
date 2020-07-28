/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.librarymanagement;

/**
 *
 * @author Suraj Upadhyay
 */
public class User {
    private String UserName,FullName;
    public User(String UserName,String FullName){
        this.UserName=UserName;
        this.FullName=FullName;
    }
    String getUserName(){return UserName;}
    String getFullName(){return FullName;}
}
