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
public class DeleteBook extends javax.swing.JFrame {
Connection conn;
PreparedStatement pst;
ResultSet rs;
    /**
     * Creates new form DeleteBook
     */
    public DeleteBook() {
        super("Delete Book");
        initComponents();
        try{
            Class.forName(LoginPage.DRIVER);
            conn=DriverManager.getConnection(LoginPage.JDBC_URL,"Root","root");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    void UpdateTransaction(){
        String ISBN=jtxtISBN.getText();
        String sql="UPDATE TRANS SET SUBMITTED = 'N/A' WHERE BOOK='"+ISBN+"' AND SUBMITTED IS NULL";
        try{
            conn.prepareStatement(sql).executeUpdate();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    int ConfirmPassword(){
        String sql="SELECT * FROM ACCOUNT WHERE STATUS = TRUE";
        String UserName,Password;
        try{
            rs=conn.prepareStatement(sql).executeQuery();
            if(rs.next()){
                UserName=rs.getString(1);
                Password=rs.getString(2);
                JTextField Text=new JTextField();
                JPasswordField Pswd=new JPasswordField();
                Text.setText(UserName);
                Pswd.setText("");
                JPanel Panel=new JPanel(new GridLayout(0,2));
                Panel.add(new JLabel("UserName : "));
                Panel.add(Text);
                Panel.add(new JLabel("Enter Password : "));
                Panel.add(Pswd);
                int result=JOptionPane.showConfirmDialog(null,Panel,"Confirm It's You ",JOptionPane.OK_CANCEL_OPTION);
                if(result==JOptionPane.OK_OPTION){
                    if(String.valueOf(Pswd.getPassword()).equals(Password))
                                {JOptionPane.showMessageDialog(null,"Are you SURE ??"); return 1;}
                    else {JOptionPane.showMessageDialog(null,"Wrong Password"); return 0;}
                }else{
                    JOptionPane.showMessageDialog(null,"Wrong Password"); return 0;
                }
            }else{
                JOptionPane.showMessageDialog(null,"Cannot Access Account info");
                return 0;
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        return 0;
    }
    boolean checkISBN(){
        String ISBN=jtxtISBN.getText();
        String sql="SELECT * FROM BOOK WHERE ISBN = '"+ISBN+"'";
        try{
            if(conn.prepareStatement(sql).executeQuery().next()) return true;
            else JOptionPane.showMessageDialog(null,"Book Not in Records");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        return false;
    }
    int UpdateStudent(){
        String ISBN=jtxtISBN.getText();
        String ScholarNo,sql;
        try{
            sql="select * from book where isbn='"+ISBN+"' and scholarno is not null";
            rs=conn.prepareStatement(sql).executeQuery();
            if(rs.next()){
                ScholarNo=rs.getString("SCHOLARNO");
                int field=getBookField(ISBN,ScholarNo);
                if(field==-1) return 0;
                int n=UpdateStudent(field,ScholarNo);
                if(n==-1) return 0;
                return 1;
            }else return 1;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        return 0;
    }
    private int getBookField(String ISBN,String ScholarNo){
        String sql;
        try{
            sql="SELECT * FROM STUDENT WHERE SCHOLARNO='"+ScholarNo+"' AND BOOK1 = '"+ISBN+"'";
            if(conn.prepareStatement(sql).executeQuery().next()) return 1;
            sql="SELECT * FROM STUDENT WHERE SCHOLARNO='"+ScholarNo+"' AND BOOK2 = '"+ISBN+"'";
            if(conn.prepareStatement(sql).executeQuery().next()) return 2;
            sql="SELECT * FROM STUDENT WHERE SCHOLARNO='"+ScholarNo+"' AND BOOK3 = '"+ISBN+"'";
            if(conn.prepareStatement(sql).executeQuery().next()) return 3;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e);
            return -1;
        }
        return -1;
    }
    private int UpdateStudent(int field,String ScholarNo){
        String sql2;
        switch (field) {
            case 1:
                sql2="UPDATE STUDENT SET BOOK1= NULL WHERE SCHOLARNO = '"+ScholarNo+"'";
                try{
                    String isbn1,isbn2;
                    conn.prepareStatement(sql2).executeUpdate();
                    sql2="SELECT * FROM STUDENT WHERE SCHOLARNO = '"+ScholarNo+"'";
                    rs=conn.prepareStatement(sql2).executeQuery();
                    if(rs.next()){
                        isbn1=rs.getString(9);
                        if(rs.wasNull()) isbn1="NULL";
                        else isbn1="'"+isbn1+"'";
                        isbn2=rs.getString(10);
                        if(rs.wasNull()) isbn2="NULL";
                        else isbn2="'"+isbn2+"'";
                        sql2="UPDATE STUDENT SET BOOK1 = "+isbn1+", BOOK2 = "+isbn2+" WHERE SCHOLARNO = '"+ScholarNo+"'";
                        conn.prepareStatement(sql2).executeUpdate();
                        conn.prepareStatement("UPDATE STUDENT SET BOOK3=NULL WHERE SCHOLARNO = '"
                                +ScholarNo+"'").executeUpdate();
                    }else return -1;
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(null,e);
                    return -1;
                }
                break;
            case 2:
                sql2="UPDATE STUDENT SET BOOK2 = NULL WHERE SCHOLARNO = '"+ScholarNo+"'";
                try{
                    String isbn;
                    conn.prepareStatement(sql2).executeUpdate();
                    sql2="SELECT * FROM STUDENT WHERE SCHOLARNO = '"+ScholarNo+"'";
                    rs=conn.prepareStatement(sql2).executeQuery();
                    if(rs.next()){
                        isbn=rs.getString(10);
                        if(rs.wasNull()) isbn="NULL";
                        else isbn="'"+isbn+"'";
                        sql2="UPDATE STUDENT SET BOOK2 = "+isbn+" WHERE SCHOLARNO = '"+ScholarNo+"'";
                        conn.prepareStatement(sql2).executeUpdate();
                        conn.prepareStatement("UPDATE STUDENT SET BOOK3=NULL WHERE SCHOLARNO = '"
                                +ScholarNo+"'").executeUpdate();
                    }else return -1;
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(null,e);
                    return -1;
                }
                break;
            default:
                sql2="UPDATE STUDENT SET BOOK3 = NULL WHERE SCHOLARNO = '"+ScholarNo+"'";
                try{
                    conn.prepareStatement(sql2).executeUpdate();
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(null,e);
                    return -1;
                }
                break;
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

        jToggleButton1 = new javax.swing.JToggleButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtxtISBN = new javax.swing.JTextField();
        jbtnDelete = new javax.swing.JButton();

        jToggleButton1.setText("jToggleButton1");

        setTitle("Delete Book");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 0), 2, true), "Delete Book", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(255, 0, 0))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("ISBN :");

        jbtnDelete.setText("Delete");
        jbtnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jLabel1)
                .addGap(63, 63, 63)
                .addComponent(jtxtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(109, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtnDelete)
                .addGap(217, 217, 217))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(jbtnDelete)
                .addGap(35, 35, 35))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDeleteActionPerformed
        if(!checkISBN())return ;
        if(ConfirmPassword()==0) return;
        if(UpdateStudent()==0) {JOptionPane.showMessageDialog(null,"Some Error Occured");return;}
        String sql="DELETE FROM BOOK WHERE ISBN = '"+jtxtISBN.getText()+"'";
        try{
            conn.prepareStatement(sql).executeUpdate();
            JOptionPane.showMessageDialog(null,"Book Erased from RECORDS");
            rs.close();
            conn.close();
            this.setVisible(false);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e);
        }
        UpdateTransaction();
        this.setVisible(false);
    }//GEN-LAST:event_jbtnDeleteActionPerformed

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
            java.util.logging.Logger.getLogger(DeleteBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DeleteBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DeleteBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DeleteBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DeleteBook().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JButton jbtnDelete;
    private javax.swing.JTextField jtxtISBN;
    // End of variables declaration//GEN-END:variables
}
