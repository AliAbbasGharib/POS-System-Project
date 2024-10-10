/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package StatementOfAccount;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sarepta
 */
public class Form extends javax.swing.JFrame {

    /**
     * Creates new form Form
     */
    
    private String s, f, t,ts;
    private void centerFrame()
    {
     Toolkit tk = Toolkit.getDefaultToolkit();
     Dimension sc = tk.getScreenSize();
     Dimension fr = getSize();
     int x = (sc.width - fr.width)/2;
     int y = (sc.height - fr.height)/2;
     setLocation(x, y);
    }
    public Form() {
        initComponents();
        centerFrame();
    }
    public Form(String s, String f, String t,String ts){
        
        initComponents();
        centerFrame();
        System.out.println(ts);
        this.s=s;
        this.f=f;
        this.t=t;
        this.ts=ts;
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        try{
            model.setRowCount(0);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:                3306/trainingproject","root","");
            Statement st = con.createStatement();
            if(ts == "customer")
            {
            ResultSet rs = st.executeQuery("SELECT `ReceiptID`, `customerID`, `Date`, `USDAmount`, `LBPAmount`, `Description` FROM `receipt` WHERE customerID='"+s+"' AND Date BETWEEN '"+f+"' AND '"+t+"' ORDER BY Date");
            while(rs.next()){
                String receiptid = rs.getString("ReceiptID");
                String date = rs.getString("Date");
                String description = rs.getString("Description");
                String credit = rs.getString("USDAmount");
                model.addRow(
                new Object[] {date,receiptid,description,"0",credit,"0"});
            }
            
            ResultSet rs1 = st.executeQuery("SELECT `ID`, `Name`, `Phone`, `Address` FROM `customers` WHERE ID='"+s+"'");
            while(rs1.next()){
                String id = rs1.getString("ID");
                String name = rs1.getString("Name");
                String phone = rs1.getString("Phone");
                String address = rs1.getString("Address");
                jTextField1.setText(id);
                jTextField2.setText(name);
                jTextField6.setText(phone);
                jTextField3.setText(address);
            }
            ResultSet rs2 = st.executeQuery("SELECT `ID`, `ClientID`, `InvoiceDate`, `TotalAmount` FROM `saleinvoice2` WHERE ClientID='"+s+"' AND InvoiceDate BETWEEN '"+f+"' AND '"+t+"'  ORDER BY InvoiceDate");
            while(rs2.next()){
                String invoiceid = rs2.getString("ID");
                String invoicedate = rs2.getString("InvoiceDate");
                String debit = rs2.getString("TotalAmount");
                model.addRow(
                new Object[] {invoicedate,invoiceid,"",debit,"0","0"});
            }
            int rowcount = model.getRowCount();
            int row = 1;
            Object depit0 = model.getValueAt(0, 3);
            String depitString = depit0.toString();
            Object credit0 = model.getValueAt(0, 4);
            String creditString = credit0.toString();
            double depitValue0 = Double.parseDouble((String) depitString);
            double creditValue0 = Double.parseDouble((String) creditString);
            double balance0 = depitValue0 - creditValue0;
            Object balanceObject = (Object) balance0;
            model.setValueAt(balanceObject, 0, 5);
            while(row<rowcount){
                Object depit = model.getValueAt(row, 3);
                String depitstring = depit.toString();
                Object credit = model.getValueAt(row, 4);
                String creditstring = credit.toString();
                Object balanceminus1 = model.getValueAt(row-1, 5);
                String balancestring = balanceminus1.toString();
                double balminus1 = Double.parseDouble((String)balancestring);
                double depitValue = Double.parseDouble((String) depitstring);
                double creditValue = Double.parseDouble((String) creditstring);
                balance0 = balminus1+depitValue-creditValue;
                Object finalBalance = (Object) balance0;
                model.setValueAt(finalBalance, row, 5);
                row++;
            }
            String total = Double.toString(balance0);
            jTextField7.setText(total);
            jTextField4.setText(f);
            jTextField5.setText(t);
            }
            else if(ts == "supplier"){
                ResultSet rs = st.executeQuery("SELECT `PaymentID`, `SupplierID`, `Date`, `USDAmount`, `LBPAmount`, `Description` FROM `payment` WHERE SupplierID='"+s+"' AND Date BETWEEN '"+f+"' AND '"+t+"' ORDER BY Date");
                while(rs.next()){
                    String paymentid = rs.getString("PaymentID");
                    String date = rs.getString("Date");
                    String description = rs.getString("Description");
                    String credit = rs.getString("USDAmount");
                    model.addRow(
                    new Object[] {date,paymentid,description,"0",credit,"0"});
            }
            
            ResultSet rs1 = st.executeQuery("SELECT `ID`, `Name`, `Phone`, `Address` FROM `suppliers` WHERE ID='"+s+"'");
            while(rs1.next()){
                String id = rs1.getString("ID");
                String name = rs1.getString("Name");
                String phone = rs1.getString("Phone");
                String address = rs1.getString("Address");
                jTextField1.setText(id);
                jTextField2.setText(name);
                jTextField6.setText(phone);
                jTextField3.setText(address);
            }
            ResultSet rs2 = st.executeQuery("SELECT `ID`, `SupplierID`, `InvoiceDate`, `TotalAmount` FROM `purshaseinvoice` WHERE SupplierID='"+s+"' AND InvoiceDate BETWEEN '"+f+"' AND '"+t+"'  ORDER BY InvoiceDate");
            while(rs2.next()){
                String invoiceid = rs2.getString("ID");
                String invoicedate = rs2.getString("InvoiceDate");
                String debit = rs2.getString("TotalAmount");
                model.addRow(
                new Object[] {invoicedate,invoiceid,"",debit,"0","0"});
            }
            int rowcount = model.getRowCount();
            int row = 1;
            Object depit0 = model.getValueAt(0, 3);
            String depitString = depit0.toString();
            Object credit0 = model.getValueAt(0, 4);
            String creditString = credit0.toString();
            double depitValue0 = Double.parseDouble((String) depitString);
            double creditValue0 = Double.parseDouble((String) creditString);
            double balance0 = depitValue0 - creditValue0;  
            Object balanceObject = (Object) balance0;
            model.setValueAt(balanceObject, 0, 5);
            while(row<rowcount){
                Object depit = model.getValueAt(row, 3);
                String depitstring = depit.toString();
                Object credit = model.getValueAt(row, 4);
                String creditstring = credit.toString();
                Object balanceminus1 = model.getValueAt(row-1, 5);
                String balancestring = balanceminus1.toString();
                double balminus1 = Double.parseDouble((String)balancestring);
                double depitValue = Double.parseDouble((String) depitstring);
                double creditValue = Double.parseDouble((String) creditstring);
                balance0 = balminus1+depitValue-creditValue;
                Object finalBalance = (Object) balance0;
                model.setValueAt(finalBalance, row, 5);
                row++;
            }
            String total = Double.toString(balance0);
            jTextField7.setText(total);
            jTextField4.setText(f);
            jTextField5.setText(t);        
            }
            
        }
       
        catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Statment Of Account");

        jLabel1.setText("ClientID");

        jLabel2.setText("Address");

        jLabel3.setText("From");

        jLabel4.setText("To");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel5.setText("Phone");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Date", "ID", "Description", "Debit", "Credit", "Balance"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel6.setText("Total");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/logout_16dp_5985E1_FILL0_wght400_GRAD0_opsz20.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(189, 189, 189)
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextField2))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextField4)
                                        .addGap(54, 54, 54)
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(316, 316, 316))))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(185, 185, 185)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
        StateofAccount st = new StateofAccount();
        st.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            String s, f, t,ts;
            public void run() {
                new Form(s, f, t,ts).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables
}
