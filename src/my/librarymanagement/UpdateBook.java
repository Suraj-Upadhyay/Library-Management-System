/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.librarymanagement;
import java.awt.GridLayout;
import java.sql.*;
import javax.swing.*;
/**
 *
 * @author Suraj Upadhyay
 */
public class UpdateBook extends javax.swing.JFrame {
Connection conn;
PreparedStatement pst;
ResultSet rs;
String ISBN;
static Thread t;
    /**
     * Creates new form UpdateBook
     */
    public UpdateBook() {
        super("Update Book");
        initComponents();
        try{
            Class.forName(LoginPage.DRIVER);
            conn=DriverManager.getConnection(LoginPage.JDBC_URL,"Root","root");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    int Login() {
        String UserName,Password;
        String sql="SELECT * FROM ACCOUNT WHERE STATUS = TRUE";
        try{
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if(rs.next()){
                UserName=rs.getString(1);
                Password=rs.getString(2);
            }
            else{
                JOptionPane.showMessageDialog(null,"Not currently signed in ....");
                return 0;
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
            return 0;
        }
        JTextField text0=new JTextField(20);
        text0.setText(UserName);
        JPasswordField pswd=new JPasswordField(20);
        pswd.setText("");
        JPanel Panel=new JPanel(new GridLayout(0,2));
        Panel.add(new JLabel("UserName : "));
        Panel.add(text0);
        Panel.add(new JLabel("Password : "));
        Panel.add(pswd);
        int result=JOptionPane.showConfirmDialog(null,Panel,"Confirmation",JOptionPane.OK_CANCEL_OPTION);
        if(result==JOptionPane.OK_OPTION){
            if(Password.equals(String.valueOf(pswd.getPassword()))) {
                JOptionPane.showMessageDialog(null,"ARE you SURE you want to CHANGE THE DETAILS ???");
                return 1;
            }
            else {
                JOptionPane.showMessageDialog(null,"Wrong Password");
                return 0;
            }
        }
        else  return 0;
    }
    int fillFields(String ISBN){
        String sql="SELECT * FROM BOOK WHERE ISBN = '"+ISBN+"'";
        try{
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if(rs.next()){
                jtxtName.setText(rs.getString(2));
                jtxtAuthor.setText(rs.getString(3));
                jtxtPrice.setText(rs.getString(4));
                jtxtPages.setText(rs.getString(5));
                jtxtScholarNo.setText(rs.getString(6));
                if(rs.wasNull()) jtxtStatus.setText("Not Issued");
                else jtxtStatus.setText("Issued");
                jtxtDate.setText(rs.getString(7));
                jtxtStart.setText(rs.getString(9));
                jtxtEnd.setText(rs.getString(10));
                return 1;
            }else{
                JOptionPane.showMessageDialog(null,"Book not in Records");
                return -1;
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
            return 0;
        }
    }
    void clear(){
        jtxtISBN.setText("");
        jtxtName.setText("");
        jtxtAuthor.setText("");
        jtxtPrice.setText("");
        jtxtPages.setText("");
        jtxtScholarNo.setText("");
        jtxtDate.setText("");
        jtxtStart.setText("");
        jtxtEnd.setText("");
    }
    int UpdateStudentBase(){
        String sql="SELECT * FROM BOOK WHERE ISBN = '"+ISBN+"'";
        String ISBN1=jtxtISBN.getText();
        String UserName="";
        try{
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if(rs.next()) { 
                UserName=rs.getString(6);
                if(rs.wasNull()) return 1;
            }
            else return 0;
            sql="SELECT * FROM STUDENT WHERE SCHOLARNO = '"+UserName+"' AND BOOK1 = '"+ISBN+"'";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if(rs.next()) {
                sql="UPDATE STUDENT SET BOOK1 = '"+ISBN1+"' WHERE SCHOLARNO = '"+UserName+"'";
                pst=conn.prepareStatement(sql);
                pst.executeUpdate();
                return 1;
            }
            sql="SELECT * FROM STUDENT WHERE SCHOLARNO = '"+UserName+"' AND BOOK2 = '"+ISBN+"'";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if(rs.next()){
                sql="UPDATE STUDENT SET BOOK2 = '"+ISBN1+"' WHERE SCHOLARNO = '"+UserName+"'";
                pst=conn.prepareStatement(sql);
                pst.executeUpdate();
                return 1;
            }
            sql="SELECT * FROM STUDENT WHERE SCHOLARNO = '"+UserName+"' AND BOOK3 = '"+ISBN+"'";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if(rs.next()) {
                sql="UPDATE STUDENT SET BOOK3 = '"+ISBN1+"' WHERE SCHOLARNO= '"+UserName+"'";
                pst=conn.prepareStatement(sql);
                pst.executeUpdate();
                return 1;
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
            return 0;
        }
        return 1;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtxtISBN = new javax.swing.JTextField();
        jbtnSearch = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jbtnSave = new javax.swing.JButton();
        jtxtName = new javax.swing.JTextField();
        jtxtAuthor = new javax.swing.JTextField();
        jtxtPrice = new javax.swing.JTextField();
        jtxtPages = new javax.swing.JTextField();
        jtxtDate = new javax.swing.JTextField();
        jtxtStatus = new javax.swing.JTextField();
        jtxtStart = new javax.swing.JTextField();
        jtxtEnd = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jtxtScholarNo = new javax.swing.JTextField();

        setTitle("Update Book");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 153, 0), 2, true), "Update Book", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(0, 153, 0))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("ISBN :");

        jbtnSearch.setText("Search");
        jbtnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSearchActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Book Name :");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Author :");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Price :");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Pages :");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Addition Date :");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Status :");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Issued By : ");

        jbtnSave.setText("Update");
        jbtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSaveActionPerformed(evt);
            }
        });

        jtxtStatus.setEditable(false);

        jtxtStart.setEditable(false);

        jtxtEnd.setEditable(false);

        jLabel10.setText("From");

        jLabel11.setText("To");

        jtxtScholarNo.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(61, 61, 61)
                                        .addComponent(jtxtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jtxtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(40, 40, 40)
                                                .addComponent(jLabel5)
                                                .addGap(18, 18, 18)
                                                .addComponent(jtxtPages, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jtxtName)
                                            .addComponent(jtxtAuthor))))
                                .addGap(18, 18, 18)
                                .addComponent(jbtnSearch))
                            .addComponent(jLabel6))
                        .addContainerGap(61, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(jtxtStart, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtxtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtxtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(28, 28, 28))
                            .addComponent(jtxtScholarNo, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jtxtEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(178, 178, 178)
                .addComponent(jbtnSave)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtxtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnSearch))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtxtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtxtAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jtxtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtPages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jtxtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jtxtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jtxtScholarNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jtxtStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jtxtEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addComponent(jbtnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSearchActionPerformed
        //Take Password and confirm it .
        //Update all the details in the book database 
        //Update details in the student database 
        //Update details in the transaction database
        ISBN=jtxtISBN.getText();
        int n=fillFields(ISBN);
        if(n==-1){
            JOptionPane.showMessageDialog(null,"Enter Valid ISBN no.");
        }
        else if(n==0){
            JOptionPane.showMessageDialog(null,"Some Error Occured Cannot Access Book Database.....");
        }
        else{
            JOptionPane.showMessageDialog(null,"You can UPDATE BOOK RECORDS");
        }
    }//GEN-LAST:event_jbtnSearchActionPerformed

    private void jbtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSaveActionPerformed
        if(Login()==0) return;
        String ISBN1=jtxtISBN.getText();
        String Name=jtxtName.getText();
        String Author=jtxtAuthor.getText();
        String Price=jtxtPrice.getText();
        String Pages=jtxtPages.getText();
        String AdditionDate=jtxtDate.getText();
        String sql="UPDATE BOOK SET ISBN = '"+ISBN1+"' , BOOKNAME = '"+Name+"' , AUTHOR = '"+Author+"' , "
                + "PRICE= "+Price+" , PAGES = "+Pages+" , "
                + "ADDITIONDATE = '"+AdditionDate+"' WHERE "
                + "ISBN = '"+ISBN+"'";
        int n=UpdateStudentBase();
        if(n==0) { JOptionPane.showMessageDialog(null,"Cannot Update Book Database : Error Occured"); return ;}
        try{
            pst=conn.prepareStatement(sql);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Book Updated Successfully in Database");
            rs.close();
            pst.close();
            conn.close();
            this.setVisible(false);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }//GEN-LAST:event_jbtnSaveActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UpdateBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater((Runnable) () -> {
            new UpdateBook().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbtnSave;
    private javax.swing.JButton jbtnSearch;
    private javax.swing.JTextField jtxtAuthor;
    private javax.swing.JTextField jtxtDate;
    private javax.swing.JTextField jtxtEnd;
    private javax.swing.JTextField jtxtISBN;
    private javax.swing.JTextField jtxtName;
    private javax.swing.JTextField jtxtPages;
    private javax.swing.JTextField jtxtPrice;
    private javax.swing.JTextField jtxtScholarNo;
    private javax.swing.JTextField jtxtStart;
    private javax.swing.JTextField jtxtStatus;
    // End of variables declaration//GEN-END:variables
}
