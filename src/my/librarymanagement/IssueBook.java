/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.librarymanagement;
import java.sql.*;
import javax.swing.JOptionPane;
import java.util.Calendar;
/**
 *
 * @author Suraj Upadhyay
 */
public class IssueBook extends javax.swing.JFrame {
Connection conn;
PreparedStatement pst;
ResultSet rs;
String ScholarNo;
java.sql.Date Today;
java.sql.Date End;
    /**
     * Creates new form IssueBook
     */
    public IssueBook() {
        super("Issue Book");
        initComponents();
        try{
            Class.forName(LoginPage.DRIVER);
            conn=DriverManager.getConnection(LoginPage.JDBC_URL,"Root","root");
        }catch(ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        getScholarNo();
    }
    int getScholarNo(){
        String sql="SELECT * FROM PASSVALUE";
        try{
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if(!rs.next()){
                JOptionPane.showMessageDialog(null,"Data not found in PassValue");
                return -1;
            }else{
                ScholarNo=rs.getString(1);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        return 1;
    }
    int Issued(String ISBN){
        String sql="SELECT * FROM BOOK WHERE ISBN = '"+ISBN+"'";
        try{
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if(!rs.next())  return -1;
            sql="SELECT * FROM BOOK WHERE ISBN = '"+ISBN+"' AND SCHOLARNO IS NULL";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if(rs.next()) return 0;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
            return 2;
        }
        return 1;
    }
    int getDays(){
        if(jrd3months.isSelected()) return 90;
        else if(jrd6months.isSelected()) return 180;
        return -1;
    }
    int getEmptyFieldinStudentBook(){
        try{
            String sql="SELECT * FROM STUDENT WHERE SCHOLARNO = '"+ScholarNo+"' AND BOOK1 IS NULL";
            if(conn.prepareStatement(sql).executeQuery().next()) return 1;
            sql="SELECT * FROM STUDENT WHERE SCHOLARNO = '"+ScholarNo+"' AND BOOK2 IS NULL AND BOOK1 IS NOT NULL";
            if(conn.prepareStatement(sql).executeQuery().next()) return 2;
            sql="SELECT * FROM STUDENT WHERE SCHOLARNO = '"+ScholarNo+"' AND BOOK3 IS NULL AND BOOK2 IS NOT NULL AND BOOK1 IS NOT NULL";
            if(conn.prepareStatement(sql).executeQuery().next()) return 3;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
            return 0;
        }
        return 0;
    }
    int IssueBook(String ISBN){
        int DaystoAdd=getDays();
        int EmptyField=getEmptyFieldinStudentBook();
        Today = new java.sql.Date(new java.util.Date().getTime());
        java.util.Date utilDate=new java.util.Date();
        Calendar c=Calendar.getInstance();
        c.setTime(utilDate);
        if(DaystoAdd==-1){JOptionPane.showMessageDialog(null,"Issue Period not selected");return 0;}
        else{ 
            c.add(Calendar.DATE,DaystoAdd);
            utilDate=c.getTime();
            End=new java.sql.Date(utilDate.getTime());
        }
        if(EmptyField==0) return 0;
        String sql="UPDATE BOOK SET SCHOLARNO = '"+ScholarNo+"', ISSUDATE ='"
                +Today+"' , SUBMITDATE = '"+End+"' WHERE ISBN = '"+ISBN+"'";
        String studentsql="UPDATE STUDENT SET BOOK"+EmptyField+" = '"+ISBN+"' WHERE"
                + " SCHOLARNO = '"+ScholarNo+"'";
        try{
            pst=conn.prepareStatement(sql);
            pst.executeUpdate();
            pst=conn.prepareStatement(studentsql);
            pst.executeUpdate();
            if(UpdateTransaction(ScholarNo,ISBN)==-1)
                {JOptionPane.showMessageDialog(null,"Some Error Occured in recording Transactions "); return 0;}
            return 1;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        return 0;
    }
    int genereteTransactionNo(){
        int i=0;
        String sql="SELECT * FROM TRANS";
        try{
            rs=conn.prepareStatement(sql).executeQuery();
            while(rs.next()){
                i++;
            }
            return i++;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
            return -1;
        }
    }
    int UpdateTransaction(String ScholarNo,String ISBN){
        //Enter Data Records in Transactions table 
        //with ScholarNo , ISBN , ISSUEDATE and SUBMITDATE leaving SUBMITTED NULL to be 
        //filled by UpdateTransaction in submitBook frame
        int transno=genereteTransactionNo();
        if(transno==-1) return -1;
        String sql="INSERT INTO TRANS (TRANSNO,BOOK,STUDENT,ISSUEDATE,SUBMITDATE)"
                + " VALUES ("+transno+",'"+ISBN+"','"+ScholarNo+"','"+Today+"','"+End+"')";
        try{
            conn.prepareStatement(sql).executeUpdate();
            return 1;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
            return -1;
        }
    }
    void clear(){
        jtxtISBN.setText("");
    }
    void IssuedSuccessfully(){
        JOptionPane.showMessageDialog(null,"Issued Successfully From "+Today+" To "+End+" ");
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
        jrd3months = new javax.swing.JRadioButton();
        jrd6months = new javax.swing.JRadioButton();
        jbtnIssue = new javax.swing.JButton();

        setTitle("Issue Book");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 204, 204), 2, true), "Issue Book", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(204, 204, 255))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Book ISBN :");

        jrd3months.setText("90 Days");
        jrd3months.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrd3monthsActionPerformed(evt);
            }
        });

        jrd6months.setText("180 Days");
        jrd6months.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrd6monthsActionPerformed(evt);
            }
        });

        jbtnIssue.setText("Issue");
        jbtnIssue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnIssueActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jrd3months)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jrd6months)
                .addGap(75, 75, 75))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel1)
                        .addGap(69, 69, 69)
                        .addComponent(jtxtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(157, 157, 157)
                        .addComponent(jbtnIssue)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtxtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrd3months)
                    .addComponent(jrd6months))
                .addGap(18, 18, 18)
                .addComponent(jbtnIssue)
                .addContainerGap(22, Short.MAX_VALUE))
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

    private void jbtnIssueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnIssueActionPerformed
        String ISBN=jtxtISBN.getText();
        int n=Issued(ISBN);
        if(n==-1){
            JOptionPane.showMessageDialog(null,"Book not found in Records");
            clear();
        }
        else if(n==1){
            JOptionPane.showMessageDialog(null,"Book is already Issued");
            clear();
        }
        else if(n==0){
            int p=IssueBook(ISBN);
            if(p==1) IssuedSuccessfully();
            else if(p==0) JOptionPane.showMessageDialog(null,"Cannot Issue Book Error Occured");
            clear();
            try{
            rs.close();
            conn.close();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null,e);
            }
            this.setVisible(false);
        }
        else{
            JOptionPane.showMessageDialog(null,"Some Error Occured Can't find book in records");
            clear();
        }
    }//GEN-LAST:event_jbtnIssueActionPerformed

    private void jrd3monthsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrd3monthsActionPerformed
        jrd6months.setSelected(false);
    }//GEN-LAST:event_jrd3monthsActionPerformed

    private void jrd6monthsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrd6monthsActionPerformed
        jrd3months.setSelected(false);
    }//GEN-LAST:event_jrd6monthsActionPerformed

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
            java.util.logging.Logger.getLogger(IssueBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IssueBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IssueBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IssueBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IssueBook().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbtnIssue;
    private javax.swing.JRadioButton jrd3months;
    private javax.swing.JRadioButton jrd6months;
    private javax.swing.JTextField jtxtISBN;
    // End of variables declaration//GEN-END:variables
}
