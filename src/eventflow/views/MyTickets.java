/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */package eventflow.views;

import eventflow.dao.TicketDao.TicketWithEventDetails;
import eventflow.models.User;
import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MyTickets extends javax.swing.JFrame {

    private final User user;

    public MyTickets(User user) {
        initComponents();
         setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.user = user;
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
        });        myTicketsbut.addActionListener(evt -> {
            eventflow.controllers.TicketController.goToMyTickets(user);
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
        logBut.addActionListener(evt -> {
            eventflow.controllers.DashboardController.goToSignup();
            dispose();
        });

        double balance = eventflow.controllers.TicketController.getUserBalance(user);
        balanceLabel.setText("Balance: RS" + balance);        // ✅ Button to navigate to Create.java
        
        // You can now use user.getFullname(), user.getId(), etc.
    }

    public void loadTickets(List<TicketWithEventDetails> tickets) {
        myTickets.removeAll();
        myTickets.setLayout(new BoxLayout(myTickets, BoxLayout.Y_AXIS));

        if (tickets == null || tickets.isEmpty()) {
            JLabel noTickets = new JLabel("No tickets purchased yet");
            noTickets.setForeground(Color.WHITE);
            noTickets.setAlignmentX(Component.LEFT_ALIGNMENT);
            myTickets.add(noTickets);
            myTickets.revalidate();
            myTickets.repaint();
            return;
        }

        for (TicketWithEventDetails ticket : tickets) {
            JPanel card = new JPanel(new GridBagLayout());
            card.setBackground(new Color(66, 66, 116));
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(90, 90, 140), 1, true),
                    BorderFactory.createEmptyBorder(15, 20, 15, 20)
            ));
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
            card.setAlignmentX(Component.LEFT_ALIGNMENT);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;

            // Row 0 - Ticket ID
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            JLabel ticketIdLabel = new JLabel("Ticket #" + ticket.getTicketId());
            ticketIdLabel.setForeground(new Color(180, 180, 255));
            ticketIdLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            card.add(ticketIdLabel, gbc);

            // Row 1 - Event Title
            gbc.gridy++;
            JLabel titleLabel = new JLabel(ticket.getEventTitle());
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            card.add(titleLabel, gbc);

            // Row 2 - Event Details
            gbc.gridy++;
            gbc.gridwidth = 1;

            JLabel dateLabel = new JLabel("Date:");
            dateLabel.setForeground(Color.LIGHT_GRAY);
            gbc.gridx = 0;
            card.add(dateLabel, gbc);

            JLabel dateValue = new JLabel(ticket.getEventDate() + " at " + ticket.getEventTime());
            dateValue.setForeground(Color.LIGHT_GRAY);
            gbc.gridx = 1;
            card.add(dateValue, gbc);

            // Row 3 - Location
            gbc.gridy++;
            gbc.gridx = 0;
            JLabel locationLabel = new JLabel("Location:");
            locationLabel.setForeground(Color.LIGHT_GRAY);
            card.add(locationLabel, gbc);

            gbc.gridx = 1;
            JLabel locationValue = new JLabel(ticket.getEventLocation());
            locationValue.setForeground(Color.LIGHT_GRAY);
            card.add(locationValue, gbc);

            // Row 4 - Price Paid
            gbc.gridy++;
            gbc.gridx = 0;
            JLabel priceLabel = new JLabel("Price Paid:");
            priceLabel.setForeground(new Color(0, 255, 153));
            priceLabel.setFont(priceLabel.getFont().deriveFont(Font.BOLD));
            card.add(priceLabel, gbc);

            gbc.gridx = 1;
            JLabel priceValue = new JLabel("Rs. " + ticket.getPricePaid());
            priceValue.setForeground(new Color(0, 255, 153));
            priceValue.setFont(priceValue.getFont().deriveFont(Font.BOLD));
            card.add(priceValue, gbc);

            // Row 5 - Purchase Date
            gbc.gridy++;
            gbc.gridx = 0;
            JLabel purchaseLabel = new JLabel("Purchased:");
            purchaseLabel.setForeground(Color.LIGHT_GRAY);
            card.add(purchaseLabel, gbc);

            gbc.gridx = 1;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            JLabel purchaseValue = new JLabel(ticket.getPurchaseDate().format(formatter));
            purchaseValue.setForeground(Color.LIGHT_GRAY);
            card.add(purchaseValue, gbc);

            // Add vertical spacing between cards
            myTickets.add(card);
            myTickets.add(Box.createVerticalStrut(15));
        }

        myTickets.revalidate();
        myTickets.repaint();
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
        logBut = new javax.swing.JToggleButton();
        jPanel21 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        myTickets = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        balanceLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        logBut.setBackground(new java.awt.Color(58, 58, 98));
        logBut.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        logBut.setForeground(new java.awt.Color(160, 160, 178));
        logBut.setText("Logout");
        logBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logButActionPerformed(evt);
            }
        });

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
                                    .addComponent(profileBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(logBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(logBut, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sideLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel21.setBackground(new java.awt.Color(58, 58, 98));

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel43.setForeground(java.awt.Color.white);
        jLabel43.setText("MY TICKETS");

        myTickets.setBackground(new java.awt.Color(66, 66, 116));
        myTickets.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel23.setBackground(new java.awt.Color(31, 110, 153));
        jPanel23.setForeground(new java.awt.Color(51, 204, 255));

        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel46.setForeground(java.awt.Color.white);
        jLabel46.setText("Time");

        jLabel3.setText("Date");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel46)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel47.setForeground(java.awt.Color.white);
        jLabel47.setText("Summer Music Festival 2023");

        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel48.setForeground(java.awt.Color.white);
        jLabel48.setText("Location");

        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel49.setForeground(java.awt.Color.white);
        jLabel49.setText("Description");

        jLabel52.setForeground(java.awt.Color.white);
        jLabel52.setText("Price");

        jLabel53.setForeground(java.awt.Color.white);
        jLabel53.setText("Purchase Date");

        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel56.setForeground(java.awt.Color.white);
        jLabel56.setText("2500");

        jLabel57.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel57.setForeground(java.awt.Color.white);
        jLabel57.setText("July 28,  2023");

        javax.swing.GroupLayout myTicketsLayout = new javax.swing.GroupLayout(myTickets);
        myTickets.setLayout(myTicketsLayout);
        myTicketsLayout.setHorizontalGroup(
            myTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(myTicketsLayout.createSequentialGroup()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(myTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(myTicketsLayout.createSequentialGroup()
                        .addGap(265, 265, 265)
                        .addGroup(myTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel52))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addGroup(myTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel57)
                            .addComponent(jLabel53))
                        .addGap(26, 26, 26))
                    .addGroup(myTicketsLayout.createSequentialGroup()
                        .addGroup(myTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel47)
                            .addComponent(jLabel48)
                            .addComponent(jLabel49))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        myTicketsLayout.setVerticalGroup(
            myTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(myTicketsLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(myTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(jLabel53))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(myTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(jLabel57))
                .addContainerGap())
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTextField6.setBackground(new java.awt.Color(6, 200, 164));
        jTextField6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField6.setForeground(java.awt.Color.white);
        jTextField6.setText("All Tickets");
        jTextField6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        balanceLabel.setForeground(new java.awt.Color(255, 255, 255));
        balanceLabel.setText("Balance..");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel21Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel21Layout.createSequentialGroup()
                                .addComponent(jLabel43)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(balanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(myTickets, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(balanceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(11, 11, 11)
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(myTickets, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(208, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dashButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashButActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dashButActionPerformed

    private void myTicketsbutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myTicketsbutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_myTicketsbutActionPerformed

    private void logButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logButActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_logButActionPerformed

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
            java.util.logging.Logger.getLogger(MyTickets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MyTickets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MyTickets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MyTickets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel balanceLabel;
    private javax.swing.JToggleButton dashBut;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JToggleButton logBut;
    private javax.swing.JToggleButton myEventsbut;
    private javax.swing.JPanel myTickets;
    private javax.swing.JToggleButton myTicketsbut;
    private javax.swing.JToggleButton profileBut;
    private javax.swing.JLabel sideLabel;
    private javax.swing.JToggleButton walletBut;
    // End of variables declaration//GEN-END:variables
}
