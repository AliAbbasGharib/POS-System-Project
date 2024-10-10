/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package PurshaseInvoice;




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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ali Gharib
 */
public class NewInvoicePur extends javax.swing.JFrame {

    /**
     * Creates new form NewPurInvoice
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
    private PurshaseInvoice parent;
    
    private String ID, Name;
    
     private Object[][] tableData;
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
    
        public NewInvoicePur() {
        initComponents();
        AutomaticIDFiller();
        setupTableModelListener();
        setupDiscountListener();
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        model.setRowCount(0);
        centerFrame();
    }
    public NewInvoicePur(String ID, String Name) {
        initComponents();
        AutomaticIDFiller();
        centerFrame();
      tableData = new Object[10][5]; // Change the size as needed
    DefaultTableModel model = new DefaultTableModel(tableData, columnNames);
        jTable1.setModel(model);
        model.setRowCount(0);
        setupTableModelListener();
        setupDiscountListener();
        try{
        this.ID = ID;
        this.Name = Name;
        jTextField2.setText(ID);
        jTextField3.setText(Name);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
        public NewInvoicePur(PurshaseInvoice parent) {
        this.parent=parent;
        initComponents();
        AutomaticIDFiller();
        setupTableModelListener();
        setupDiscountListener();
    }
        
        
        
    
         public int row = 0;
    public NewInvoicePur(String ItemID, String ItemName, String costPrice){
        initComponents();
        AutomaticIDFiller();
        centerFrame();


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
              dispose();


        }
        catch(Exception e){
            System.out.println(e);
        }
    }
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
            String discountStr = jTextField4.getText();
            discount = Double.parseDouble(discountStr);
        } catch (NumberFormatException e) {
            // Handle invalid discount format
            System.out.println("Invalid discount format: " + e.getMessage());
        }

        double finalAmount = totalAmount - (totalAmount * discount / 100);

        // Update the total amount field
        jTextField5.setText(String.format("%.2f", finalAmount));
    }
    // Method to add data to the table
    
    public void addDataToTable(String itemId, String itemName, String qty, String unitPrice,           String totalPrice) {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    
    // Add a new row to the table
    model.addRow(new Object[]{itemId, itemName, qty, unitPrice, totalPrice});
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Add Purchase Invoice");
        setResizable(false);

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
        jScrollPane1.setViewportView(jTable1);

        jPanel1.setBackground(new java.awt.Color(51, 51, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setForeground(new java.awt.Color(51, 255, 255));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("SupplierID");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Date");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setText("Discount");

        jLabel5.setText("TotalAmount");

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
                .addContainerGap(34, Short.MAX_VALUE))
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
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jSpinner1.setModel(new javax.swing.SpinnerDateModel());

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Add Item");

        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("QtyAvailable");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(20, 20, 20)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(74, 74, 74)
                                .addComponent(jLabel3)
                                .addGap(27, 27, 27)
                                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(218, 218, 218))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
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
                            .addComponent(jLabel6)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );

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
        jButton2.setActionCommand("");
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
                .addGap(20, 20, 20)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addGap(20, 20, 20)))
                .addGap(20, 20, 20))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 828, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(300, 300, 300)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        String SearchID = jTextField2.getText();
        String SearchByName = jTextField3.getText();
        SupplierSearch si = new SupplierSearch(SearchID,SearchByName);
        si.setVisible(true);
        dispose();
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
      String SearchByItem = jTextField6.getText();
        try{
            ItemSearch2 itSearch = new ItemSearch2(this,SearchByItem);
            itSearch.setVisible(true);

        }
        catch(Exception e){
            System.out.println(e);
        }
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
           // Gather invoice data
        String invoiceID = jTextField1.getText();
        String supplierID = jTextField2.getText();
        String supplierName = jTextField3.getText();
        Date invoiceDate = ((SpinnerDateModel) jSpinner1.getModel()).getDate();

        // Initialize discount and totalAmount
        double discount = 0.0;
        double totalAmount = 0.0;

        // Validate and parse discount
        try {
            String discountStr = jTextField4.getText();
            if (!discountStr.isEmpty()) {
                discount = Double.parseDouble(discountStr);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid discount format", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate and parse totalAmount
        try {
            String totalAmountStr = jTextField5.getText();
            if (!totalAmountStr.isEmpty()) {
                totalAmount = Double.parseDouble(totalAmountStr);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid total amount format", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trainingproject", "root", "")) {
            con.setAutoCommit(false); // Start transaction

            // Insert invoice header data
            String insertInvoiceSQL = "INSERT INTO purshaseinvoice (SupplierID, SupplierName, InvoiceDate, Discount, TotalAmount) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertInvoiceSQL, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, supplierID);
                pstmt.setString(2, supplierName);
                pstmt.setDate(3, new java.sql.Date(invoiceDate.getTime()));
                pstmt.setDouble(4, discount);
                pstmt.setDouble(5, totalAmount);
                pstmt.executeUpdate();

                // Get the generated invoice ID
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedInvoiceID = generatedKeys.getInt(1);

                    // Insert invoice items data
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    String insertItemSQL = "INSERT INTO invoice_items2 (`ID`,`InvoiceIDPur`, `ItemDescription`, `Quantity`, `CostPrice`, `Total`"
                            + ") VALUES (?, ?, ?, ?, ?,?)";
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
                            pstmtItems.setInt(2, generatedInvoiceID);
                            pstmtItems.setString(3, itemDescription);
                            pstmtItems.setInt(4, quantity);
                            pstmtItems.setDouble(5, unitPrice);
                            pstmtItems.setDouble(6, totalPrice);
                            pstmtItems.addBatch();
                        }
                        pstmtItems.executeBatch();
                    }

                    con.commit(); // Commit transaction
                    JOptionPane.showMessageDialog(this, "Invoice saved successfully!");
                    // Dispose both the current window and the parent window
                    PurshaseInvoice s1=new PurshaseInvoice();
                    s1.setVisible(true);
                    dispose();
                }
                try{
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/trainingproject","root","");
                        Statement s = con1.createStatement();
                    for(int i=0;i<model.getRowCount();i++){
                        String itemid = (String)model.getValueAt(i, 0);
                        double availqty = getAvailableQty(itemid);
                        double purchasedQty = Double.parseDouble((String)model.getValueAt(i, 2));
                        double newQty = availqty + purchasedQty;
                        int updateQty = s.executeUpdate("UPDATE `item` SET `Quantity`= "+newQty+" WHERE `ID` = '"+itemid+"'");
                    }
                }
                catch(Exception e){
                    System.out.println(e);
                }
            } catch (SQLException e) {
                con.rollback(); // Rollback transaction in case of error
                JOptionPane.showMessageDialog(this, "Failed to save invoice: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            
            int selectedRow = jTable1.getSelectedRow();
            if(selectedRow != -1)
            {
                model.removeRow(selectedRow);
            }                                      
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(NewInvoicePur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewInvoicePur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewInvoicePur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewInvoicePur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewInvoicePur().setVisible(true);
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
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    public javax.swing.JTextField jTextField6;
    public javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables
}
