package PurshaseInvoice;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class itemSearch1 extends javax.swing.JFrame {
         private void centerFrame()
    {
     Toolkit tk = Toolkit.getDefaultToolkit();
     Dimension sc = tk.getScreenSize();
     Dimension fr = getSize();
     int x = (sc.width - fr.width)/2;
     int y = (sc.height - fr.height)/2;
     setLocation(x, y);
    }
int found=0;
    private NewInvoicePur parentInvoice; // Reference to the NewInvoice instance
    private EditInvoicePur updateInvoiceParent; // Reference to the NewInvoice instance

    private String searchQuery;

    public itemSearch1(NewInvoicePur parent, String searchQuery) {
        initComponents();
        centerFrame();
        this.parentInvoice = parent;
        this.searchQuery = searchQuery;
        populateTable();
    }
    public itemSearch1(EditInvoicePur parent, String searchQuery) {
        found=1;
        initComponents();
        centerFrame();
        this.updateInvoiceParent = parent;
        this.searchQuery = searchQuery;
        populateTable();

    }
    
    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trainingproject", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT `ID`, `Description`, `Quantity`, `CostPrice` FROM item WHERE ID LIKE '%" + searchQuery + "%' OR Description LIKE '%" + searchQuery + "%'");
            while (rs.next()) {
                int itemID = rs.getInt("ID");
                String description = rs.getString("Description");
                double quantity = rs.getDouble("Quantity");
                double costPrice = rs.getDouble("CostPrice");
                model.addRow(new Object[]{itemID, description, quantity, costPrice});
            }
            st.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {
        // Ensure a row is selected
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow >= 0) {
            String itemId = jTable1.getValueAt(selectedRow, 0).toString();
            String itemName = jTable1.getValueAt(selectedRow, 1).toString();
            String unitPrice = jTable1.getValueAt(selectedRow, 3).toString();

            // Use the reference to call a method in NewInvoice
            if (parentInvoice != null) {
                parentInvoice.addDataToTable(itemId, itemName,"0" ,unitPrice,"0");
            }
            parentInvoice.jTextField6.setText("");
            dispose();
        }
    }
    private void jTable1KeyPressed2(java.awt.event.KeyEvent evt) {
        // Ensure a row is selected
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow >= 0) {
            String itemId = jTable1.getValueAt(selectedRow, 0).toString();
            String itemName = jTable1.getValueAt(selectedRow, 1).toString();
            String unitPrice = jTable1.getValueAt(selectedRow, 3).toString();

            // Use the reference to call a method in NewInvoice
            if (updateInvoiceParent != null) {
                updateInvoiceParent.addDataToTable(itemId, itemName,"0" ,unitPrice,"0");
            }
            updateInvoiceParent.jTextField4.setText("");
            dispose();
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] {
                        "ID", "Description", "Quantity", "SellingPrice"
                }
        ));
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if(found==0)
                    jTable1KeyPressed(evt);
                else
                    jTable1KeyPressed2(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
}
