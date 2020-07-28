/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.librarymanagement;
import java.sql.*;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author Suraj Upadhyay
 */
public class SubmitBook extends javax.swing.JFrame {
Connection conn;
PreparedStatement pst;
ResultSet rs;
String ISBN;
int fine;
    /**
     * Creates new form SubmitBook
     */
    public SubmitBook() {
        super("Submit Book");
        initComponents();
        try{
            Class.forName(LoginPage.DRIVER);
            conn=DriverManager.getConnection(LoginPage.JDBC_URL,"Root","root");
        }catch(ClassNotFoundException e){
            JOptionPane.showMessageDialog(null,e);
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null,e);
        }
        getISBN();
        fine=calculateFine();
        if(fine==-1){JOptionPane.showMessageDialog(null, "Some Error has Ocured ");}
        int n=fillFields(fine);
        if(n==-1){JOptionPane.showMessageDialog(null, "Some Error has Ocured ");}
    }
    void getISBN(){
        String sql="SELECT * FROM PASSVALUE";
        try{
            rs=conn.prepareStatement(sql).executeQuery();
            if(!rs.next()){
                JOptionPane.showMessageDialog(null,"Cannot retrive value from PASSVALUE");
                return ;
            }
            ISBN=rs.getString(1);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    int fillFields(int fine){
        String ScholarNo;
        String sql="SELECT * FROM BOOK WHERE ISBN = '"+ISBN+"' AND SCHOLARNO IS NOT NULL";
        try{
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if(!rs.next()){return -1;}
            jtxtISBN.setText(rs.getString(1));
            ScholarNo=rs.getString(6);
            jtxtScholarNo.setText(rs.getString(6));
            jtxtIssued.setText(rs.getString(9));
            jtxtSubmission.setText(rs.getString(10));
            jtxtToday.setText(""+ new java.sql.Date(new java.util.Date().getTime()));
            sql="SELECT * FROM STUDENT WHERE SCHOLARNO = '"+ScholarNo+"'";
            jtxtFine.setText(fine+"");
            rs=conn.prepareStatement(sql).executeQuery();
            if(!rs.next()){return -1;}
            jtxtName.setText(rs.getString(2));
            pst=null;
            rs=null;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
            return -1;
        }
        return 1;
    }
    int calculateFine(){
        int fine=0;
        java.util.Date EndDate;
        java.util.Date Today=new java.util.Date();
        String sql="SELECT * FROM BOOK WHERE ISBN = '"+ISBN+"'";
        try{
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if(rs.next()){
                EndDate = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(10));
                if(Today.after(EndDate)){
                    long duration=Today.getTime()-EndDate.getTime();
                    long diffInDays=TimeUnit.DAYS.toDays(duration);
                    fine=(int) (2*diffInDays);
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
            return -1;
        }
        return fine;
    }
    void clear(){
        jtxtScholarNo.setText("");
        jtxtName.setText("");
        jtxtIssued.setText("");
        jtxtSubmission.setText("");
        jtxtToday.setText("");
        jtxtFine.setText("");
        jrdPaid.setSelected(false);
    }
    int getBookField(){
        String ScholarNo=jtxtScholarNo.getText();
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
    int SubmitBook(){
        int field=getBookField();
        String ScholarNo=jtxtScholarNo.getText();
        if(field==-1) {JOptionPane.showMessageDialog(null,"Some error has Occured");return -1;}
        int a=UpdateBook();
        int b= UpdateStudent(field,ScholarNo);
        int c=UpdateTransaction(ScholarNo,ISBN);
        return a*b*c;
    }
    int UpdateBook(){
        String sql1="UPDATE BOOK SET SCHOLARNO=NULL , FINE=NULL , ISSUDATE=NULL ,SUBMITDATE=NULL WHERE ISBN ='"+ISBN+"'";
        try{
            conn.prepareStatement(sql1).executeUpdate();
            return 1;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e);
            return -1;
        }
    }
    int UpdateStudent(int field,String ScholarNo){
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
    int UpdateTransaction(String ScholarNo,String ISBN){
        String sql="SELECT * FROM TRANS WHERE STUDENT = '"+ScholarNo+"' AND BOOK = '"+ISBN+"' AND SUBMITTED IS NULL";
        try{
            rs=conn.prepareStatement(sql).executeQuery();
            if(rs.next()){
                sql="UPDATE TRANS SET SUBMITTED = '"+jtxtToday.getText()+"' "
                        + "WHERE STUDENT = '"+ScholarNo+"' AND BOOK = '"+ISBN+"' AND SUBMITTED IS NULL";
                conn.prepareStatement(sql).executeUpdate();
            }else return -1;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e);
            return -1;
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtxtScholarNo = new javax.swing.JTextField();
        jtxtName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtxtIssued = new javax.swing.JTextField();
        jtxtSubmission = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jtxtToday = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jtxtFine = new javax.swing.JTextField();
        jbtnSubmit = new javax.swing.JButton();
        jrdPaid = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        jtxtISBN = new javax.swing.JTextField();
        jtxtBack = new javax.swing.JButton();

        setTitle("Submit Book");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true), "Submit Book", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Scholar No :");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Student Name :");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Issued by :");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Issued on :");

        jtxtScholarNo.setEditable(false);

        jtxtName.setEditable(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Submission Date :");

        jtxtIssued.setEditable(false);

        jtxtSubmission.setEditable(false);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Date Submitting :");

        jtxtToday.setEditable(false);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Fine :");

        jtxtFine.setEditable(false);

        jbtnSubmit.setText("Submit");
        jbtnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSubmitActionPerformed(evt);
            }
        });

        jrdPaid.setText("Paid");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Book ISBN :");

        jtxtISBN.setEditable(false);

        jtxtBack.setText("Back");
        jtxtBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jtxtFine, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(jrdPaid))
                            .addComponent(jtxtToday, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnSubmit)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jtxtIssued, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(18, 18, 18)
                            .addComponent(jtxtSubmission, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1)))
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtxtScholarNo)
                            .addComponent(jtxtName)
                            .addComponent(jtxtISBN, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)))
                    .addComponent(jtxtBack))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jtxtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtScholarNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtIssued, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jtxtSubmission, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jtxtToday, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jtxtFine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jrdPaid))
                .addGap(33, 33, 33)
                .addComponent(jbtnSubmit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtxtBack)
                .addGap(12, 12, 12))
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

    private void jbtnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSubmitActionPerformed
        int n;
        if(fine!=0&&!jrdPaid.isSelected())
        {JOptionPane.showMessageDialog(null, "Cannot submit : FINE UNPAID ");clear();return ;}
        else n=SubmitBook();
        if(n==-1) {JOptionPane.showMessageDialog(null,"Cannot Submit : Some error has occured");return ;}
        JOptionPane.showMessageDialog(null,"Submitted Successfully");
        try{
            rs.close();
            conn.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e);
        }
        this.setVisible(false);
    }//GEN-LAST:event_jbtnSubmitActionPerformed

    private void jtxtBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtBackActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jtxtBackActionPerformed

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
            java.util.logging.Logger.getLogger(SubmitBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SubmitBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SubmitBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SubmitBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SubmitBook().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbtnSubmit;
    private javax.swing.JRadioButton jrdPaid;
    private javax.swing.JButton jtxtBack;
    private javax.swing.JTextField jtxtFine;
    private javax.swing.JTextField jtxtISBN;
    private javax.swing.JTextField jtxtIssued;
    private javax.swing.JTextField jtxtName;
    private javax.swing.JTextField jtxtScholarNo;
    private javax.swing.JTextField jtxtSubmission;
    private javax.swing.JTextField jtxtToday;
    // End of variables declaration//GEN-END:variables
}