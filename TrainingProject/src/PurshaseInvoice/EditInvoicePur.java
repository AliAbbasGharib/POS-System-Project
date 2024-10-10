/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package PurshaseInvoice;



import SaleInvoice.clientSearch;
import SaleInvoice.itemSearch;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ali Gharib
 */
public class EditInvoicePur extends javax.swing.JFrame {

    /**
     * Creates new form EditInvoicePur
     */
     private PurshaseInvoice parent;
    private String invoiceID;
    private String ID, Name;
    private Object[][] tableData;
        private void centerFrame()
    {
     Toolkit tk = Toolkit.getDefaultToolkit();
     Dimension sc = tk.getScreenSize();
     Dimension fr = getSize();
     int x = (sc.width - fr.width)/2;
     int y = (sc.height - fr.height)/2;
     setLocation(x, y);
    }
    
    
    private String[] columnNames = {"ID", "Description", "Qty", "CostPrice", "TotalPrice"};
    
    public double getAvailableQty(String itemID){
           
        double availableQty=0;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trainingproject", "root", "");
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT `Quantity`FROM `item` WHERE `ID` = '"+itemID+"'");
            while(rs.next()){
                availableQty = rs.getDouble("Quantity");
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return availableQty;
    }
    public EditInvoicePur()
    {
         initComponents();
         centerFrame();
    }
     public EditInvoicePur(PurshaseInvoice parent, String invoiceID) {
        this.parent = parent;
        this.invoiceID = invoiceID;
        initComponents();
        populateInvoiceData();
        setupTableModelListener();
        setupDiscountListener();
        centerFrame();
    }
      private void populateInvoiceData() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trainingproject", "root", "")) {
            // Fetch invoice data
            String selectInvoiceSQL = "SELECT * FROM purshaseInvoice WHERE ID = ?";
            try (PreparedStatement pstmt = con.prepareStatement(selectInvoiceSQL)) {
                pstmt.setString(1, invoiceID);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    jTextField1.setText(rs.getString("ID"));
                    jTextField2.setText(rs.getString("SupplierID"));
                    jTextField3.setText(rs.getString("SupplierName"));
                    jTextField5.setText(String.valueOf(rs.getDouble("Discount")));
                    jTextField6.setText(String.valueOf(rs.getDouble("TotalAmount")));
                    jSpinner1.setValue(rs.getDate("InvoiceDate"));
                }
            }

            // Fetch invoice items
            String selectItemsSQL = "SELECT * FROM invoice_items2 WHERE InvoiceIDPur = ?";
            try (PreparedStatement pstmt = con.prepareStatement(selectItemsSQL)) {
                pstmt.setString(1, invoiceID);
                ResultSet rs = pstmt.executeQuery();
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setRowCount(0); // Clear existing rows
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getString("ID"),
                            rs.getString("ItemDescription"),
                            rs.getInt("Quantity"),
                            rs.getDouble("CostPrice"),
                            rs.getDouble("Total")
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
        public int row = 0;
    private void setupTableModelListener() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();

                // Check if the changed cell is in the quantity column (index 2)
                if (column == 2) {
                    // Get the table model
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

                    // Get the updated quantity and unit price
                    String qtyStr = model.getValueAt(row, 2).toString();
                    String unitPriceStr = model.getValueAt(row, 3).toString();

                    try {
                        int qty = Integer.parseInt(qtyStr);
                        double unitPrice = Double.parseDouble(unitPriceStr);

                        // Calculate the total price
                        double totalPrice = qty * unitPrice;


                        // Update the total price cell
                        model.setValueAt(totalPrice, row, 4);
                        // Recalculate and update the total amount
                        updateTotalAmount();
                    } catch (NumberFormatException ex) {
                        // Handle invalid input
                        System.out.println("Invalid number format: " + ex.getMessage());
                    }
                }
            }
        });
    }
  private void setupDiscountListener() {
        jTextField4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Recalculate and update the total amount when discount changes
                updateTotalAmount();
            }
        });
    }
    private void updateTotalAmount() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        double totalAmount = 0.0;

        // Sum up the total prices of all items
        for (int row = 0; row < model.getRowCount(); row++) {
            Object totalPriceObj = model.getValueAt(row, 4);
            if (totalPriceObj != null) {
                try {
                    totalAmount += Double.parseDouble(totalPriceObj.toString());
                } catch (NumberFormatException e) {
                    // Handle invalid number format
                    System.out.println("Invalid number format in total price: " + e.getMessage());
                }
            }
        }

        // Apply discount
        double discount = 0.0;
        try {
            String discountStr = jTextField5.getText();
            discount = Double.parseDouble(discountStr);
        } catch (NumberFormatException e) {
            // Handle invalid discount format
            System.out.println("Invalid discount format: " + e.getMessage());
        }

        double finalAmount = totalAmount - (totalAmount * discount / 100);

        // Update the total amount field
        jTextField6.setText(String.format("%.2f", finalAmount));
    }
    // Method to add data to the table
    public void addDataToTable(String itemId, String itemName, String qty, String unitPrice,           String totalPrice) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        // Add a new row to the table
        model.addRow(new Object[]{itemId, itemName, qty, unitPrice, totalPrice});
    }

    public void AutomaticIDFiller(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:                              3306/trainingproject","root","");
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT MAX(ID) FROM `purshaseinvoice`");
            if(rs.next()){
                int newID =  rs.getInt(1)+1;
                String str = Integer.toString(newID);

                jTextField1.setText(str);
            }
            else{
                jTextField1.setText("1");
            }


            s.close();
            con.close();
//              dispose();


        }
        catch(Exception e){
            System.out.println(e);
        }
    }


private void handleSaveButtonClick() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trainingproject", "root", "")) {
            // Prepare SQL query for updating the invoice item
            String query = "UPDATE invoice_items SET ItemDescription=?, Quantity=?, UnitPrice=?, TotalPrice=? WHERE ItemID=?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, jTextField2.getText().trim());

                // Correctly handle the value from the JSpinner
                Number quantityValue = (Number) jSpinner1.getValue();
                pstmt.setDouble(2, quantityValue.doubleValue());

                // Parse text fields to Double
                double unitPrice;
                double totalPrice;
                try {
                    unitPrice = Double.parseDouble(jTextField3.getText().trim());
                    totalPrice = Double.parseDouble(jTextField4.getText().trim());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid number format for price", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                pstmt.setDouble(3, unitPrice);
                pstmt.setDouble(4, totalPrice);

                // Set the ID for the update
                pstmt.setString(5, jTextField1.getText().trim());
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Item updated successfully.");
                parent.AutomaticTableFiller();
                dispose();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jSpinner1 = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Edit Purchase Invoice");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 102, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Supplier");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Date");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jSpinner1.setModel(new javax.swing.SpinnerDateModel());

        jLabel5.setText("Discount");

        jLabel6.setText("TotalAmount");

        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField5))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("QtyAvailable");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Item");

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(139, 139, 139)))
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(21, 21, 21)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel3)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(151, 151, 151))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Item", "Description", "Qty", "CostPice", "Total"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton3.setText("DeleteRow");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/save_16dp_0000F5_FILL0_wght400_GRAD0_opsz20.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/logout_16dp_5985E1_FILL0_wght400_GRAD0_opsz20.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 778, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(272, 272, 272))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
       String SearchByItem = jTextField4.getText();
        try{
            itemSearch3 itSearch = new itemSearch3(this,SearchByItem);
            itSearch.setVisible(true);

        }
        catch(Exception e){
            System.out.println(e);
        }
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String invoiceID = jTextField1.getText();
        String clientID = jTextField2.getText();
        String clientName = jTextField3.getText();
        Date invoiceDate = ((SpinnerDateModel) jSpinner1.getModel()).getDate();

        double discount = 0.0;
        double totalAmount = 0.0;

        try {
            String discountStr = jTextField5.getText();
            if (!discountStr.isEmpty()) {
                discount = Double.parseDouble(discountStr);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid discount format", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String totalAmountStr = jTextField6.getText();
            if (!totalAmountStr.isEmpty()) {
                totalAmount = Double.parseDouble(totalAmountStr);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid total amount format", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
         

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trainingproject", "root", "")) {
            con.setAutoCommit(false);
            // Delete items from invoice_items
            String deleteItemsQuery = "DELETE FROM invoice_items2 WHERE InvoiceIDPur = ?";
            PreparedStatement pstmtDeleteItems = con.prepareStatement(deleteItemsQuery);
            pstmtDeleteItems.setString(1, invoiceID);
            pstmtDeleteItems.executeUpdate();

            // Delete existing invoice
            String deleteInvoiceSQL = "DELETE FROM purshaseInvoice WHERE ID = ?";
            try (PreparedStatement pstmt = con.prepareStatement(deleteInvoiceSQL)) {
                pstmt.setString(1, invoiceID);
                pstmt.executeUpdate();
            }

            // Insert new invoice data
            String insertInvoiceSQL = "INSERT INTO purshaseInvoice (ID,SupplierID, SupplierName, InvoiceDate, Discount, TotalAmount) VALUES (?,?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertInvoiceSQL)) {
                pstmt.setString(1, invoiceID);
                pstmt.setString(2, clientID);
                pstmt.setString(3, clientName);
                pstmt.setDate(4, new java.sql.Date(invoiceDate.getTime()));
                pstmt.setDouble(5, discount);
                pstmt.setDouble(6, totalAmount);
                pstmt.executeUpdate();
            }
            
            // Insert new invoice items data
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            String insertItemSQL = "INSERT INTO invoice_items2 (ID,InvoiceIDPur,ItemDescription, Quantity, CostPrice, Total) VALUES (?, ?, ?, ?, ?,?)";
            try (PreparedStatement pstmtItems = con.prepareStatement(insertItemSQL)) {
                
                for (int i = 0; i < model.getRowCount(); i++) {
                     int itemID = Integer.parseInt( model.getValueAt(i, 0).toString());
                    String itemDescription = model.getValueAt(i, 1).toString();
                    int quantity = 0;
                    double unitPrice = 0.0;
                    double totalPrice = 0.0;

                    try {
                        quantity = Integer.parseInt(model.getValueAt(i, 2).toString());
                        unitPrice = Double.parseDouble(model.getValueAt(i, 3).toString());
                        totalPrice = Double.parseDouble(model.getValueAt(i, 4).toString());
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Invalid number format in table data", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                     pstmtItems.setInt(1, itemID);
                    pstmtItems.setString(2, invoiceID);
                    pstmtItems.setString(3, itemDescription);
                    pstmtItems.setInt(4, quantity);
                    pstmtItems.setDouble(5, unitPrice);
                    pstmtItems.setDouble(6, totalPrice);
                    pstmtItems.addBatch();
                }
                pstmtItems.executeBatch();
            }
            

            con.commit();
            JOptionPane.showMessageDialog(this, "Invoice updated successfully!");
            PurshaseInvoice s1 = new PurshaseInvoice();
            s1.setVisible(true);
            dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to update invoice: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }  
                     try{
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/trainingproject","root","");
                        Statement s = con1.createStatement();
                    for(int i=0;i<model.getRowCount();i++){
                        String itemid = (String) model.getValueAt(i, 0);
                        double availqty = getAvailableQty(itemid);
                        double purchasedQty = Double.parseDouble((String)model.getValueAt(i, 2));
                        double newQty = availqty + purchasedQty;
                        int updateQty = s.executeUpdate("UPDATE `item` SET `Quantity`= "+newQty+" WHERE `ID` = '"+itemid+"'");
                    }
                }
                catch(Exception e){
                    System.out.println(e);
                }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       dispose();
       PurshaseInvoice pr = new PurshaseInvoice();
       pr.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        String SearchByName = jTextField2.getText();
        String SearchByID = jTextField2.getText();
        SupplierSearch si = new SupplierSearch(SearchByID,SearchByName);
        si.setVisible(true);
        dispose();        
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        int selectedRow = jTable1.getSelectedRow();
//        Object selectedRowObj = (Object) selectedRow;
        Object qtytoReturn = model.getValueAt(selectedRow, 2);
        double qtyToReturn = ((Number) qtytoReturn).doubleValue();
        Object ItemIDToReturn = model.getValueAt(selectedRow, 0);
        String ItemIDtoReturn = (String) ItemIDToReturn;
        
        if(selectedRow != -1)
        {
            double initialQty = getAvailableQty(ItemIDtoReturn);
            double finalQty = initialQty - qtyToReturn;
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trainingproject", "root", "");
                Statement s = con.createStatement();
                int update = s.executeUpdate("UPDATE `item` SET `Quantity`="+finalQty+" WHERE `ID` = '"+ItemIDtoReturn+"'");
                s.close();
                con.close();
            }
            catch(Exception e){
                System.out.println(e);
            }
            model.removeRow(selectedRow);
        }                                    
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

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
            java.util.logging.Logger.getLogger(EditInvoicePur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditInvoicePur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditInvoicePur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditInvoicePur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditInvoicePur().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
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
    public javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    public javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables
}
