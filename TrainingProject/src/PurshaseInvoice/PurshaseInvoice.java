/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package PurshaseInvoice;

import Payment.NewPayment;
import PurshaseInvoice.EditInvoicePur;
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
public class PurshaseInvoice extends javax.swing.JFrame {

    /**
     * Creates new form PurshaseInvoice
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
            ResultSet rs = s.executeQuery("SELECT * FROM purshaseinvoice WHERE ID = (SELECT MAX(ID) FROM purshaseinvoice)");

            // Check if there is a result
            if (rs.next()) {
                // Retrieve the ID
                int invoiceID = rs.getInt("ID");
                int SupplierID = rs.getInt("SupplierID");
                String SupplierName = rs.getString("SupplierName");
                  Discount = rs.getInt("Discount");
                Date invoiceDate = rs.getDate("InvoiceDate");


                jTextField1.setText(String.valueOf(invoiceID));
                jTextField2.setText(String.valueOf(SupplierID));
                jTextField3.setText(String.valueOf(SupplierName));
                jSpinner1.setValue(invoiceDate);

                // Query for the invoice items
                String query = "SELECT * FROM invoice_items2 WHERE InvoiceIDPur = ?";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, invoiceID);

                // Execute the query
                ResultSet items = pstmt.executeQuery();

                double totalPrice = 0;
                model.setRowCount(0); // Clear existing rows

                // Process the results and populate the table
                while (items.next()) {
                    int itemID = items.getInt("ID");
                    String description = items.getString("ItemDescription");
                    double quantity = items.getDouble("Quantity");
                    double costPrice = items.getDouble("CostPrice");
                    double itemTotalPrice = items.getDouble("Total");

                    // Update the total price
                    totalPrice += itemTotalPrice;

                    // Add the row to the table model
                    model.addRow(new Object[] {itemID, description, quantity, costPrice, itemTotalPrice});
                }

                // Set the total price in the text field
                jTextField5.setText(String.valueOf((totalPrice-totalPrice*Discount/100)));
                jTextField4.setText(String.valueOf(Discount));


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
            ResultSet rs = s.executeQuery("SELECT * FROM purshaseinvoice WHERE ID = "+SearchedInvID+"");

            // Check if there is a result
            if (rs.next()) {
                // Retrieve the ID
                int invoiceID = rs.getInt("ID");
                int SupplierID = rs.getInt("SupplierID");
                String SupplierName = rs.getString("SupplierName");
                  Discount = rs.getInt("Discount");
                Date invoiceDate = rs.getDate("InvoiceDate");


                jTextField1.setText(String.valueOf(invoiceID));
                jTextField2.setText(String.valueOf(SupplierID));
                jTextField3.setText(String.valueOf(SupplierName));
                jSpinner1.setValue(invoiceDate);

                // Query for the invoice items
                String query = "SELECT * FROM invoice_items2 WHERE InvoiceIDPur = ?";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, invoiceID);

                // Execute the query
                ResultSet items = pstmt.executeQuery();

                double totalPrice = 0;
                model.setRowCount(0); // Clear existing rows

                // Process the results and populate the table
                while (items.next()) {
                    int itemID = items.getInt("ID");
                    String description = items.getString("ItemDescription");
                    double quantity = items.getDouble("Quantity");
                    double costPrice = items.getDouble("CostPrice");
                    double itemTotalPrice = items.getDouble("Total");

                    // Update the total price
                    totalPrice += itemTotalPrice;

                    // Add the row to the table model
                    model.addRow(new Object[] {itemID, description, quantity, costPrice, itemTotalPrice});
                }

                // Set the total price in the text field
                jTextField5.setText(String.valueOf((totalPrice-totalPrice*Discount/100)));
                jTextField4.setText(String.valueOf(Discount));


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

    
    public PurshaseInvoice() {
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Purchase Invoice");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(51, 51, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setForeground(new java.awt.Color(51, 255, 255));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("SupplierID");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Date");

        jTextField1.setEditable(false);

        jTextField2.setEditable(false);

        jTextField3.setEditable(false);

        jButton1.setText("Payment");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));

        jLabel4.setText("Discount");

        jLabel5.setText("TotalAmount");

        jTextField4.setEditable(false);

        jTextField5.setEditable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField5)
                    .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jSpinner1.setModel(new javax.swing.SpinnerDateModel());

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("QtyAvailable");

        jTextField7.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(25, 25, 25)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(165, 165, 165)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ItemID", "Description", "Qty", "CostPrice", "TotalPrice"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel6.setText("IDSearch");

        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        jButton8.setText("Search");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(242, 242, 242)));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add_16dp_75FB4C_FILL0_wght400_GRAD0_opsz20 (1).png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/arrow_back_16dp_2854C5_FILL0_wght400_GRAD0_opsz20.png"))); // NOI18N
        jButton3.setToolTipText("");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/arrow_forward_16dp_2854C5_FILL0_wght400_GRAD0_opsz20.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/edit_16dp_0000F5_FILL0_wght400_GRAD0_opsz20.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete_16dp_EA3323_FILL0_wght400_GRAD0_opsz20.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/logout_16dp_5985E1_FILL0_wght400_GRAD0_opsz20.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton8))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 742, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(84, 84, 84)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        NewPayment np = new NewPayment();
        np.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        int SearchedInvID = Integer.parseInt(jTextField6.getText());
        AutomaticTableFiller(SearchedInvID);
        
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        NewInvoicePur np = new NewInvoicePur();
        np.setVisible(true);
        dispose();
                
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
            ResultSet rs = s.executeQuery("SELECT * FROM purshaseinvoice WHERE ID < "+CurrentInvoiceID+" order by ID desc limit 1 ");

            // Check if there is a result
            if (rs.next()) {
                // Retrieve the ID
                int invoiceID = rs.getInt("ID");
                int SupplierID = rs.getInt("SupplierID");
                String SupplierName = rs.getString("SupplierName");
                Discount = rs.getInt("Discount");
                Date invoiceDate = rs.getDate("InvoiceDate");


                jTextField1.setText(String.valueOf(invoiceID));
                jTextField2.setText(String.valueOf(SupplierID));
                jTextField3.setText(String.valueOf(SupplierName));
                jSpinner1.setValue(invoiceDate);

                // Query for the invoice items
                String query = "SELECT * FROM invoice_items2 WHERE InvoiceIDPur = (SELECT `ID` FROM purshaseinvoice WHERE ID < ? ORDER BY ID DESC LIMIT 1)";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, CurrentInvoiceID);

                // Execute the query
                ResultSet items = pstmt.executeQuery();

                double totalPrice = 0;
                model.setRowCount(0); // Clear existing rows

                // Process the results and populate the table
                while (items.next()) {
                    int itemID = items.getInt("ID");
                    String description = items.getString("ItemDescription");
                    double quantity = items.getDouble("Quantity");
                    double CostPrice = items.getDouble("CostPrice");
                    double itemTotalPrice = items.getDouble("Total");

                    // Update the total price
                    totalPrice += itemTotalPrice;

                    // Add the row to the table model
                    model.addRow(new Object[] {itemID, description, quantity, CostPrice, itemTotalPrice});
                }

                // Set the total price in the text field
                jTextField5.setText(String.valueOf((totalPrice-totalPrice*Discount/100)));
                jTextField4.setText(String.valueOf(Discount));


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

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
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
            ResultSet rs = s.executeQuery("SELECT * FROM purshaseinvoice WHERE ID > "+CurrentInvoiceID+" order by ID asc limit 1 ");

            // Check if there is a result
            if (rs.next()) {
                // Retrieve the ID
                int invoiceID = rs.getInt("ID");
                int SupplierID = rs.getInt("SupplierID");
                String SupplierName = rs.getString("SupplierName");
                Discount = rs.getInt("Discount");
                Date invoiceDate = rs.getDate("InvoiceDate");


                jTextField1.setText(String.valueOf(invoiceID));
                jTextField2.setText(String.valueOf(SupplierID));
                jTextField3.setText(String.valueOf(SupplierName));
                jSpinner1.setValue(invoiceDate);

                // Query for the invoice items
                String query = "SELECT * FROM invoice_items2 WHERE InvoiceIDPur = (SELECT `ID` FROM purshaseinvoice WHERE ID > ? ORDER BY ID ASC LIMIT 1)";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, CurrentInvoiceID);

                // Execute the query
                ResultSet items = pstmt.executeQuery();

                double totalPrice = 0;
                model.setRowCount(0); // Clear existing rows

                // Process the results and populate the table
                while (items.next()) {
                    int itemID = items.getInt("ID");
                    String description = items.getString("ItemDescription");
                    double quantity = items.getDouble("Quantity");
                    double CostPrice = items.getDouble("CostPrice");
                    double itemTotalPrice = items.getDouble("Total");

                    // Update the total price
                    totalPrice += itemTotalPrice;

                    // Add the row to the table model
                    model.addRow(new Object[] {itemID, description, quantity, CostPrice, itemTotalPrice});
                }

                // Set the total price in the text field
                jTextField5.setText(String.valueOf((totalPrice-totalPrice*Discount/100)));
                jTextField4.setText(String.valueOf(Discount));


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
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
       dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
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
            String deleteItemsQuery = "DELETE FROM invoice_items2 WHERE InvoiceIDPur = ?";
            PreparedStatement pstmtDeleteItems = con.prepareStatement(deleteItemsQuery);
            pstmtDeleteItems.setInt(1, invoiceID);
            pstmtDeleteItems.executeUpdate();

            // Delete invoice from saleinvoice2
            String deleteInvoiceQuery = "DELETE FROM purshaseinvoice WHERE ID = ?";
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
            PurshaseInvoice si =new PurshaseInvoice();
            si.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting invoice. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);

    }//GEN-LAST:event_jButton6ActionPerformed
}
    private void handleEditButtonClick() {
        int selectedRow = jTable1.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        String invoiceID =jTextField1.getText();
        if (selectedRow != -1) {
            // Get selected item from table
             int itemID = (Integer) model.getValueAt(selectedRow, 0);
            // Pass the ID to the EditInvoice form
            EditInvoicePur editInvoice = new EditInvoicePur(this,String.valueOf(itemID));
            editInvoice.setVisible(true);
        } else {
//            JOptionPane.showMessageDialog(this, "Please select an item to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
        EditInvoicePur update =new EditInvoicePur(this,invoiceID);
        update.setVisible(true);
        }
    }
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
      handleEditButtonClick();
      dispose();


    }//GEN-LAST:event_jButton5ActionPerformed

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
                jTextField7.setText(Double.toString(Qty));
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
            java.util.logging.Logger.getLogger(PurshaseInvoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PurshaseInvoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PurshaseInvoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PurshaseInvoice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PurshaseInvoice().setVisible(true);
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner1;
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
