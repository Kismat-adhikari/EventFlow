/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package eventflow.views;

import eventflow.models.User;
import eventflow.models.Event;
import eventflow.controllers.CreateController;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class Create extends javax.swing.JFrame {


    public Create(User user) {
        initComponents();

        sideLabel.setText(user.getFullname());

        dashBut.addActionListener(evt -> {
            eventflow.controllers.DashboardController.goToDashboard(user);
            dispose();
        });

        jToggleButton3.addActionListener(evt -> {
            eventflow.controllers.DashboardController.goToCreate(user);
            dispose();
        });

        myEventsbut.addActionListener(evt -> {
            eventflow.controllers.DashboardController.goToMyEvents(user);
            dispose();
        });

        myTicketsbut.addActionListener(evt -> {
            eventflow.controllers.DashboardController.goToMyTickets(user);
            dispose();
        });

        profileBut.addActionListener(evt -> {
            eventflow.controllers.DashboardController.goToProfile(user);
            dispose();
        });
        
        walletBut.addActionListener(evt -> {
            eventflow.controllers.DashboardController.goToWallet(user);
            dispose();
        });

        // Create button logic
        toggleCreate.addActionListener(evt -> {
            try {
                String title = eventTitle.getText().trim();
                String desc = eventDesc.getText().trim();
                int tickets = Integer.parseInt(eventTickets.getText().trim());
                double price = Double.parseDouble(eventPrice.getText().trim());
                String date = eventDate.getText().trim();
                String time = eventTime.getText().trim();
                String location = eventLocation.getText().trim();

                Event newEvent = new Event();
                newEvent.setEventTitle(title);
                newEvent.setEventDesc(desc);
                newEvent.setEventTickets(tickets);
                newEvent.setEventPrice(price);
                newEvent.setEventDate(date);
                newEvent.setEventTime(time);
                newEvent.setEventLocation(location);
                newEvent.setUserId(user.getId()); // <-- Make sure your Event class has userId field!

                boolean success = CreateController.createEvent(newEvent);

                if (success) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Event created successfully!");

                    // Clear fields after success
                    eventTitle.setText("");
                    eventDesc.setText("");
                    eventTickets.setText("");
                    eventPrice.setText("");
                    eventDate.setText("");
                    eventTime.setText("");
                    eventLocation.setText("");
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this, "Failed to create event. Please try again.");
                }
            } catch (NumberFormatException e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Please enter valid numbers for tickets and price.");
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage());
            }
        });

        // Navigation buttons wiring
       

        // ====== Setup placeholder and input validation ======

        setupPlaceholder(eventTitle, "Enter Event Title");
        setupPlaceholder(eventDesc, "Enter Event Description");
        setupPlaceholder(eventTickets, "Number of Tickets");
        setupPlaceholder(eventPrice, "Ticket Price ($)");
        setupPlaceholder(eventDate, "Event Date (MM/DD/YYYY)");
        setupPlaceholder(eventTime, "Event Time (HH:MM)");
        setupPlaceholder(eventLocation, "Event Location");

        // Only digits allowed for tickets
        ((AbstractDocument) eventTickets.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr)
                    throws BadLocationException {
                if (text.matches("\\d*")) super.insertString(fb, offset, text, attr);
            }
            @Override public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text.matches("\\d*")) super.replace(fb, offset, length, text, attrs);
            }
        });

        // Digits and optionally one decimal point for price
        ((AbstractDocument) eventPrice.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr)
                    throws BadLocationException {
                String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
                if (newText.matches("^\\d*\\.?\\d*$")) super.insertString(fb, offset, text, attr);
            }
            @Override public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
                if (newText.matches("^\\d*\\.?\\d*$")) super.replace(fb, offset, length, text, attrs);
            }
        });
    }

    private void setupPlaceholder(javax.swing.JTextField field, String placeholder) {
        field.setText(placeholder);
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override public void focusGained(java.awt.event.FocusEvent evt) {
                if (field.getText().equals(placeholder)) field.setText("");
            }
            @Override public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getText().isEmpty()) field.setText(placeholder);
            }
        });
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
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        dashBut = new javax.swing.JToggleButton();
        myEventsbut = new javax.swing.JToggleButton();
        jToggleButton3 = new javax.swing.JToggleButton();
        myTicketsbut = new javax.swing.JToggleButton();
        walletBut = new javax.swing.JToggleButton();
        profileBut = new javax.swing.JToggleButton();
        sideLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        roundedPanelcreate = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        eventDesc = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        eventTickets = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        eventTitle = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        eventLocation = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        eventPrice = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        eventDate = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        eventTime = new javax.swing.JTextField();
        toggleCreate = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(58, 58, 98));

        jPanel3.setBackground(new java.awt.Color(59, 58, 98));
        jPanel3.setForeground(new java.awt.Color(58, 58, 98));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(52, 52, 86));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("EVENTFLOW");

        dashBut.setBackground(new java.awt.Color(58, 58, 98));
        dashBut.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        dashBut.setForeground(new java.awt.Color(160, 160, 178));
        dashBut.setText("Dashboard");
        dashBut.setPreferredSize(new java.awt.Dimension(200, 40));
        dashBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dashButActionPerformed(evt);
            }
        });

        myEventsbut.setBackground(new java.awt.Color(58, 58, 98));
        myEventsbut.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        myEventsbut.setForeground(new java.awt.Color(160, 160, 178));
        myEventsbut.setText("My Events");
        myEventsbut.setPreferredSize(new java.awt.Dimension(200, 40));

        jToggleButton3.setBackground(new java.awt.Color(58, 58, 98));
        jToggleButton3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jToggleButton3.setForeground(new java.awt.Color(160, 160, 178));
        jToggleButton3.setText("Create");

        myTicketsbut.setBackground(new java.awt.Color(58, 58, 98));
        myTicketsbut.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        myTicketsbut.setForeground(new java.awt.Color(160, 160, 178));
        myTicketsbut.setText("Tickets");
        myTicketsbut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myTicketsbutActionPerformed(evt);
            }
        });

        walletBut.setBackground(new java.awt.Color(58, 58, 98));
        walletBut.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        walletBut.setForeground(new java.awt.Color(160, 160, 178));
        walletBut.setText("Wallet");

        profileBut.setBackground(new java.awt.Color(58, 58, 98));
        profileBut.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        profileBut.setForeground(new java.awt.Color(160, 160, 178));
        profileBut.setText("Profile");

        sideLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        sideLabel.setForeground(new java.awt.Color(255, 255, 255));
        sideLabel.setText("Username");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(walletBut, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(myTicketsbut, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                                    .addComponent(jToggleButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(myEventsbut, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(profileBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(30, 30, 30))
                            .addComponent(sideLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(dashBut, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dashBut, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(myEventsbut, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(myTicketsbut, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(walletBut, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(profileBut, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sideLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Create New Event ");

        roundedPanelcreate.setBackground(new java.awt.Color(58, 58, 98));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Event Details ");

        jPanel2.setBackground(new java.awt.Color(66, 66, 116));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(45, 156, 219), null));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Drag and drop an image or click to browse ");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText(" Recommended size");

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText(" Recommended size");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(308, 308, 308)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(203, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(101, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addGap(23, 23, 23))
        );

        jLabel6.setForeground(new java.awt.Color(123, 115, 145));
        jLabel6.setText("Event Title");

        eventDesc.setBackground(new java.awt.Color(66, 66, 116));
        eventDesc.setForeground(new java.awt.Color(255, 255, 255));
        eventDesc.setText("Write your Event Description.");
        eventDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventDescActionPerformed(evt);
            }
        });

        jLabel7.setForeground(new java.awt.Color(123, 115, 145));
        jLabel7.setText("Event Description ");

        eventTickets.setBackground(new java.awt.Color(66, 66, 116));
        eventTickets.setForeground(new java.awt.Color(255, 255, 255));
        eventTickets.setText("Tickets");

        jLabel8.setForeground(new java.awt.Color(123, 115, 145));
        jLabel8.setText("Total TIckets");

        eventTitle.setBackground(new java.awt.Color(66, 66, 116));
        eventTitle.setForeground(new java.awt.Color(255, 255, 255));
        eventTitle.setText("Write your Event Title.");
        eventTitle.setCaretColor(new java.awt.Color(45, 156, 219));
        eventTitle.setDisabledTextColor(new java.awt.Color(45, 156, 219));

        jLabel9.setForeground(new java.awt.Color(123, 115, 145));
        jLabel9.setText("Ticket Price ");

        eventLocation.setBackground(new java.awt.Color(66, 66, 116));
        eventLocation.setForeground(new java.awt.Color(255, 255, 255));
        eventLocation.setText("Location");
        eventLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventLocationActionPerformed(evt);
            }
        });

        jLabel10.setForeground(new java.awt.Color(123, 115, 145));
        jLabel10.setText("Event Date");

        eventPrice.setBackground(new java.awt.Color(66, 66, 116));
        eventPrice.setForeground(new java.awt.Color(255, 255, 255));
        eventPrice.setText("Price");

        jLabel11.setForeground(new java.awt.Color(123, 115, 145));
        jLabel11.setText("Event Location");

        eventDate.setBackground(new java.awt.Color(66, 66, 116));
        eventDate.setForeground(new java.awt.Color(255, 255, 255));
        eventDate.setText("MM/DD/YY");

        jLabel12.setForeground(new java.awt.Color(123, 115, 145));
        jLabel12.setText("Event Time");

        eventTime.setBackground(new java.awt.Color(66, 66, 116));
        eventTime.setForeground(new java.awt.Color(255, 255, 255));
        eventTime.setText("HH:MM");

        toggleCreate.setBackground(new java.awt.Color(0, 200, 166));
        toggleCreate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        toggleCreate.setForeground(new java.awt.Color(255, 255, 255));
        toggleCreate.setText("  CREATE EVENT");

        javax.swing.GroupLayout roundedPanelcreateLayout = new javax.swing.GroupLayout(roundedPanelcreate);
        roundedPanelcreate.setLayout(roundedPanelcreateLayout);
        roundedPanelcreateLayout.setHorizontalGroup(
            roundedPanelcreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanelcreateLayout.createSequentialGroup()
                .addGroup(roundedPanelcreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundedPanelcreateLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundedPanelcreateLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(roundedPanelcreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(eventDesc)
                            .addComponent(eventTitle)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(roundedPanelcreateLayout.createSequentialGroup()
                                .addGroup(roundedPanelcreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(eventTickets, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(41, 41, 41)
                                .addGroup(roundedPanelcreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(eventPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(23, 23, 23)
                                .addGroup(roundedPanelcreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(eventDate, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)
                                .addGroup(roundedPanelcreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(eventTime, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addGroup(roundedPanelcreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(eventLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(roundedPanelcreateLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(toggleCreate)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        roundedPanelcreateLayout.setVerticalGroup(
            roundedPanelcreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanelcreateLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(eventTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(eventDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundedPanelcreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundedPanelcreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(eventLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(roundedPanelcreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(eventTickets, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(eventPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(eventDate, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(eventTime, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(toggleCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(roundedPanelcreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(roundedPanelcreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void myTicketsbutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myTicketsbutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_myTicketsbutActionPerformed

    private void dashButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashButActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dashButActionPerformed

    private void eventDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventDescActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_eventDescActionPerformed

    private void eventLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventLocationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_eventLocationActionPerformed

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
            java.util.logging.Logger.getLogger(Create.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Create.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Create.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Create.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Create().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton dashBut;
    private javax.swing.JTextField eventDate;
    private javax.swing.JTextField eventDesc;
    private javax.swing.JTextField eventLocation;
    private javax.swing.JTextField eventPrice;
    private javax.swing.JTextField eventTickets;
    private javax.swing.JTextField eventTime;
    private javax.swing.JTextField eventTitle;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JToggleButton myEventsbut;
    private javax.swing.JToggleButton myTicketsbut;
    private javax.swing.JToggleButton profileBut;
    private javax.swing.JPanel roundedPanelcreate;
    private javax.swing.JLabel sideLabel;
    private javax.swing.JToggleButton toggleCreate;
    private javax.swing.JToggleButton walletBut;
    // End of variables declaration//GEN-END:variables
}
