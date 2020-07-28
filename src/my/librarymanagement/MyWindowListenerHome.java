/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.librarymanagement;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Suraj Upadhyay
 */
public class MyWindowListenerHome implements WindowListener{
Connection conn;    
    public MyWindowListenerHome(){
        try{
            Class.forName(LoginPage.DRIVER);
            conn=DriverManager.getConnection(LoginPage.JDBC_URL,"Root","root");
        }catch(ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        String sql="UPDATE ACCOUNT SET STATUS = FALSE";
        try{
            conn.prepareStatement(sql).executeUpdate();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,ex+" :MyWindowListenerHome.java");
        }
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
}
