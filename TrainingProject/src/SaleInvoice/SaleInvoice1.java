/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package SaleInvoice;

import Receipt.NewReceipt;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ali Gharib
 */
public class SaleInvoice1 extends javax.swing.JFrame {

     private static String clientID;
    private static String clientName;
    private static String Amount;
    /**
     * Creates new form SaleInvoice1
     */
      private void centerFrame()
    {
     Toolkit tk = Toolkit.getDefaultToolkit();
     Dimension sc = tk.getScreenSize();
     Dimension fr = getSize();
     int x = (sc.width - fr.width)/2;
     int y = (sc.height - fr.height)/2;
     setLocation(x, y);
    }
     
          
    public void AutomaticTableFiller() {
        int Discount=0;
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection to the database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trainingproject", "root", "");

            // Create a Statement object
            Statement s = con.createStatement();

            // Get the invoice ID with the maximum value
            ResultSet rs = s.executeQuery("SELECT * FROM saleinvoice2 WHERE ID = (SELECT MAX(ID) FROM saleinvoice2)");

            // Check if there is a result
            if (rs.next()) {
                // Retrieve the ID
                int invoiceID = rs.getInt("ID");
                int ClientID = rs.getInt("ClientID");
                String ClientName = rs.getString("ClientName");
                  Discount = rs.getInt("Discount");
                Date invoiceDate = rs.getDate("InvoiceDate");


                jTextField1.setText(String.valueOf(invoiceID));
                jTextField3.setText(String.valueOf(ClientID));
                jTextField4.setText(String.valueOf(ClientName));
                jSpinner1.setValue(invoiceDate);

                // Query for the invoice items
                String query = "SELECT * FROM invoice_items WHERE InvoiceID = ?";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, invoiceID);

                // Execute the query
                ResultSet items = pstmt.executeQuery();

                double totalPrice = 0;
                model.setRowCount(0); // Clear existing rows

                // Process the results and populate the table
                while (items.next()) {
                    int itemID = items.getInt("ItemID");
                    String description = items.getString("ItemDescription");
                    double quantity = items.getDouble("Quantity");
                    double unitPrice = items.getDouble("UnitPrice");
                    double itemTotalPrice = items.getDouble("TotalPrice");

                    // Update the total price
                    totalPrice += itemTotalPrice;

                    // Add the row to the table model
                    model.addRow(new Object[] {itemID, description, quantity, unitPrice, itemTotalPrice});
                }

                // Set the total price in the text field
                jTextField6.setText(String.valueOf((totalPrice-totalPrice*Discount/100)));
                jTextField5.setText(String.valueOf(Discount));


                // Close the PreparedStatement
                pstmt.close();
            }

            // Close the Statement and Connection
            rs.close();
            s.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     public void AutomaticTableFiller(int SearchedInvID) {
        int Discount=0;
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection to the database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trainingproject", "root", "");

            // Create a Statement object
            Statement s = con.createStatement();

            // Get the invoice ID with the maximum value
            ResultSet rs = s.executeQuery("SELECT * FROM saleinvoice2 WHERE ID = "+SearchedInvID+"");

            // Check if there is a result
            if (rs.next()) {
                // Retrieve the ID
                int invoiceID = rs.getInt("ID");
                int SupplierID = rs.getInt("ClientID");
                String SupplierName = rs.getString("ClientName");
                 Discount = rs.getInt("Discount");
                double totalAmount = rs.getDouble("TotalAmount");
                Date invoiceDate = rs.getDate("InvoiceDate");


                jTextField1.setText(String.valueOf(invoiceID));
                jTextField3.setText(String.valueOf(SupplierID));
                jTextField4.setText(String.valueOf(SupplierName));
                jTextField5.setText(String.valueOf(Discount));
                jTextField6.setText(String.valueOf(totalAmount));
                jSpinner1.setValue(invoiceDate);

                // Query for the invoice items
                String query = "SELECT * FROM `invoice_items` WHERE `ItemID` = ?";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, invoiceID);
                
                // Execute the query
                ResultSet items = pstmt.executeQuery();

                double totalPrice = 0;
                model.setRowCount(0); // Clear existing rows

                // Process the results and populate the table
                while (items.next()) {
                    int itemID = items.getInt("ItemID");
                    String description = items.getString("ItemDescription");
                    double quantity = items.getDouble("Quantity");
                    double unitPrice = items.getDouble("UnitPrice");
                    double itemTotalPrice = items.getDouble("Total");

                    // Update the total price
                    totalPrice += itemTotalPrice;

                    // Add the row to the table model
                    model.addRow(new Object[] {itemID, description, quantity, unitPrice, itemTotalPrice});
                }

                // Set the total price in the text field
                jTextField6.setText(String.valueOf((totalPrice-totalPrice*Discount/100)));
                jTextField5.setText(String.valueOf(Discount));


                // Close the PreparedStatement
                pstmt.close();
            }

            // Close the Statement and Connection
            rs.close();
            s.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SaleInvoice1() {
        initComponents();
        AutomaticTableFiller();
        centerFrame();
 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jTextField8 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jLabel4.setText("jLabel4");

        jTextField2.setText("jTextField2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Sale Invoice");
        setResizable(false);

        jLabel7.setText("IDSearch");

        jButton7.setText("Seach");
        jButton7.setActionCommand("Search");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));

        jLabel5.setText("Discount");

        jLabel6.setText("TotalAmount");

        jTextField5.setEditable(false);

        jTextField6.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(41, 41, 41)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(39, 39, 39))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setText("ID");

        jTextField1.setEditable(false);
        jTextField1.setToolTipText("");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel2.setText("CLient");

        jTextField3.setEditable(false);
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jTextField4.setEditable(false);

        jLabel3.setText("Date");

        jSpinner1.setModel(new javax.swing.SpinnerDateModel());

        jTextField8.setEditable(false);
        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });

        jLabel8.setText("QtyAvailable");

        jButton8.setText("Receipt");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap(40, Short.MAX_VALUE)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(133, 133, 133)
                                .addComponent(jLabel3))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jButton8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 231, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/arrow_back_16dp_2854C5_FILL0_wght400_GRAD0_opsz20.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/arrow_forward_16dp_2854C5_FILL0_wght400_GRAD0_opsz20.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add_16dp_75FB4C_FILL0_wght400_GRAD0_opsz20 (1).png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/edit_16dp_0000F5_FILL0_wght400_GRAD0_opsz20.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete_16dp_EA3323_FILL0_wght400_GRAD0_opsz20.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/logout_16dp_5985E1_FILL0_wght400_GRAD0_opsz20.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ItemID", "Description", "Qty", "unitPrice", "Total"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 832, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton7))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed
//
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
//        // Get the invoice ID from the text field
        String invoiceIDText = jTextField1.getText();

        if (invoiceIDText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Invoice ID is required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int invoiceID = Integer.parseInt(invoiceIDText);

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection to the database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trainingproject", "root", "");

            // Begin transaction
            con.setAutoCommit(false);

            // Delete items from invoice_items
            String deleteItemsQuery = "DELETE FROM invoice_items WHERE InvoiceID = ?";
            PreparedStatement pstmtDeleteItems = con.prepareStatement(deleteItemsQuery);
            pstmtDeleteItems.setInt(1, invoiceID);
            pstmtDeleteItems.executeUpdate();

            // Delete invoice from saleinvoice2
            String deleteInvoiceQuery = "DELETE FROM saleinvoice2 WHERE ID = ?";
            PreparedStatement pstmtDeleteInvoice = con.prepareStatement(deleteInvoiceQuery);
            pstmtDeleteInvoice.setInt(1, invoiceID);
            pstmtDeleteInvoice.executeUpdate();

            // Commit transaction
            con.commit();

            // Close PreparedStatements
            pstmtDeleteItems.close();
            pstmtDeleteInvoice.close();
            con.close();

            // Notify user and close the window
            JOptionPane.showMessageDialog(this, "Invoice deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            SaleInvoice1 si =new SaleInvoice1();
            si.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting invoice. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);

        }       
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       NewInvoice1 ninv = new NewInvoice1();
       ninv.setVisible(true);
       dispose();
      
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       int Discount=0;
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection to the database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trainingproject", "root", "");

            // Create a Statement object
            Statement s = con.createStatement();

            // Get the invoice ID with the maximum value
            String currentInvoiceID = jTextField1.getText();
            int CurrentInvoiceID = Integer.parseInt(currentInvoiceID);
            ResultSet rs = s.executeQuery("SELECT * FROM saleinvoice2 WHERE ID < "+CurrentInvoiceID+" order by ID desc limit 1 ");

            // Check if there is a result
            if (rs.next()) {
                // Retrieve the ID
                int invoiceID = rs.getInt("ID");
                int ClientID = rs.getInt("ClientID");
                String ClientName = rs.getString("ClientName");
                Discount = rs.getInt("Discount");
                Date invoiceDate = rs.getDate("InvoiceDate");


                jTextField1.setText(String.valueOf(invoiceID));
                jTextField3.setText(String.valueOf(ClientID));
                jTextField4.setText(String.valueOf(ClientName));
                jSpinner1.setValue(invoiceDate);

                // Query for the invoice items
                String query = "SELECT * FROM invoice_items WHERE InvoiceID = (SELECT `ID` FROM saleinvoice2 WHERE ID < ? ORDER BY ID DESC LIMIT 1)";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, CurrentInvoiceID);

                // Execute the query
                ResultSet items = pstmt.executeQuery();

                double totalPrice = 0;
                model.setRowCount(0); // Clear existing rows

                // Process the results and populate the table
                while (items.next()) {
                    int itemID = items.getInt("ItemID");
                    String description = items.getString("ItemDescription");
                    double quantity = items.getDouble("Quantity");
                    double unitPrice = items.getDouble("UnitPrice");
                    double itemTotalPrice = items.getDouble("TotalPrice");

                    // Update the total price
                    totalPrice += itemTotalPrice;

                    // Add the row to the table model
                    model.addRow(new Object[] {itemID, description, quantity, unitPrice, itemTotalPrice});
                }

                // Set the total price in the text field
                jTextField6.setText(String.valueOf((totalPrice-totalPrice*Discount/100)));
                jTextField5.setText(String.valueOf(Discount));


                // Close the PreparedStatement
                pstmt.close();
            }

            // Close the Statement and Connection
            rs.close();
            s.close();
            con.close();
            System.out.println("Hello");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
         int Discount=0;
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection to the database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trainingproject", "root", "");

            // Create a Statement object
            Statement s = con.createStatement();

            // Get the invoice ID with the maximum value
            String currentInvoiceID = jTextField1.getText();
            int CurrentInvoiceID = Integer.parseInt(currentInvoiceID);
            ResultSet rs = s.executeQuery("SELECT * FROM saleinvoice2 WHERE ID > "+CurrentInvoiceID+" order by ID asc limit 1 ");

            // Check if there is a result
            if (rs.next()) {
                // Retrieve the ID
                int invoiceID = rs.getInt("ID");
                int ClientID = rs.getInt("ClientID");
                String ClientName = rs.getString("ClientName");
                Discount = rs.getInt("Discount");
                Date invoiceDate = rs.getDate("InvoiceDate");


                jTextField1.setText(String.valueOf(invoiceID));
                jTextField3.setText(String.valueOf(ClientID));
                jTextField4.setText(String.valueOf(ClientName));
                jSpinner1.setValue(invoiceDate);

                // Query for the invoice items
                String query = "SELECT * FROM invoice_items WHERE InvoiceID = (SELECT `ID` FROM saleinvoice2 WHERE ID > ? ORDER BY ID ASC LIMIT 1)";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, CurrentInvoiceID);

                // Execute the query
                ResultSet items = pstmt.executeQuery();

                double totalPrice = 0;
                model.setRowCount(0); // Clear existing rows

                // Process the results and populate the table
                while (items.next()) {
                    int itemID = items.getInt("ItemID");
                    String description = items.getString("ItemDescription");
                    double quantity = items.getDouble("Quantity");
                    double unitPrice = items.getDouble("UnitPrice");
                    double itemTotalPrice = items.getDouble("TotalPrice");

                    // Update the total price
                    totalPrice += itemTotalPrice;

                    // Add the row to the table model
                    model.addRow(new Object[] {itemID, description, quantity, unitPrice, itemTotalPrice});
                }

                // Set the total price in the text field
                jTextField6.setText(String.valueOf((totalPrice-totalPrice*Discount/100)));
                jTextField5.setText(String.valueOf(Discount));


                // Close the PreparedStatement
                pstmt.close();
            }

            // Close the Statement and Connection
            rs.close();
            s.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton3ActionPerformed
   
    private void handleEditButtonClick() {
        int selectedRow = jTable1.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        String invoiceID =jTextField1.getText();
        if (selectedRow != -1) {
            // Get selected item from table
             int itemID = (Integer) model.getValueAt(selectedRow, 0);
            // Pass the ID to the EditInvoice form
            UpdateInvoice1 editInvoice = new UpdateInvoice1(this,String.valueOf(itemID));
            editInvoice.setVisible(true);
        } else {
//            JOptionPane.showMessageDialog(this, "Please select an item to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
        UpdateInvoice1 update =new UpdateInvoice1(this,invoiceID);
        update.setVisible(true);
        }
    }
    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    handleEditButtonClick();
    dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
      dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed

       NewReceipt nr = new NewReceipt();
       nr.setVisible(true);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
           int SearchedInvID = Integer.parseInt(jTextField7.getText());
            AutomaticTableFiller(SearchedInvID);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        int selectedRow = jTable1.getSelectedRow();
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection to the database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trainingproject", "root", "");

            // Create a Statement object
            Statement s = con.createStatement();

            // Get the invoice ID with the maximum value
            
            String itemid = model.getValueAt(selectedRow, 0).toString();
            ResultSet rs = s.executeQuery("SELECT `Quantity` FROM `item` WHERE `ID` = '"+itemid+"' ");
            
           if(rs.next())
           {
              double Qty = rs.getDouble("Quantity");
                jTextField8.setText(Double.toString(Qty));
            }
          
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }//GEN-LAST:event_jTable1MouseClicked

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
            java.util.logging.Logger.getLogger(SaleInvoice1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SaleInvoice1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SaleInvoice1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SaleInvoice1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SaleInvoice1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    // End of variables declaration//GEN-END:variables
}
