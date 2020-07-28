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
public class Transaction {
    private int Trans;
    private String Book,Student,IssueDate,SubmitDate,Submitted;
    public Transaction(int Trans,String Book,String Student,String IssueDate,String SubmitDate,String Submitted){
        this.Trans=Trans;
        this.Book=Book;
        this.Student=Student;
        this.IssueDate=IssueDate;
        this.SubmitDate=SubmitDate;
        this.Submitted=Submitted;
    }
    int getTrans(){return Trans;}
    String getBook(){return Book;}
    String getStudent(){return Student;}
    String getIssueDate(){return IssueDate;}
    String getSubmitDate(){return SubmitDate;}
    String getSubmitted(){return Submitted;}
}
