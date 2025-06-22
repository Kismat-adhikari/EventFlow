/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package eventflow.views;

import eventflow.models.User;
import eventflow.dao.EventDao.EventWithUser;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;

public class Dashboard extends javax.swing.JFrame {

    private final User user;
    // Add fields for search functionality
    private List<EventWithUser> allEvents;
    private JLabel noResultsLabel;
    
    public Dashboard(User user) {
        initComponents(); // NetBeans GUI setup
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.user = user;

        // Set welcome labels
        jLabel3.setText("Welcome " + user.getFullname());
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
        
        // Initialize search functionality after components are created
        initializeSearchFunctionality();

    }
    
        public void loadEvents(List<EventWithUser> events) {
        // Store all events for search functionality
        this.allEvents = events;
        
        eventsPanel.removeAll();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));

        if (events == null || events.isEmpty()) {
            JLabel noEvents = new JLabel("No events available");
            noEvents.setForeground(Color.WHITE);
            noEvents.setAlignmentX(Component.LEFT_ALIGNMENT);
            eventsPanel.add(noEvents);
            eventsPanel.revalidate();
            eventsPanel.repaint();
            return;
        }

        for (EventWithUser event : events) {
            JPanel card = new JPanel(new GridBagLayout());
            card.setBackground(new Color(66, 66, 116)); // lighter panel bg
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(90, 90, 140), 1, true),
                    BorderFactory.createEmptyBorder(15, 20, 15, 20)));
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
            card.setAlignmentX(Component.LEFT_ALIGNMENT);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;

            // Row 0 - Uploader Name (bold)
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            JLabel nameLabel = new JLabel(event.getUploaderFullname());
            nameLabel.setForeground(new Color(180, 180, 255));
            nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            card.add(nameLabel, gbc);

            // Row 1 - Event Title (bigger, bold)
            gbc.gridy++;
            JLabel titleLabel = new JLabel(event.getEventTitle());
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            card.add(titleLabel, gbc);

            // Row 2 - Description (wrap with HTML)
            gbc.gridy++;
            JLabel descLabel = new JLabel(
                    "<html><body style='width:650px;'>" + event.getEventDesc() + "</body></html>");
            descLabel.setForeground(new Color(190, 190, 210));
            descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            card.add(descLabel, gbc);

            // Row 3 - Info labels in 2 columns: label and value pairs
            gbc.gridy++;
            gbc.gridwidth = 1;

            JLabel timeLabel = new JLabel("Time:");
            timeLabel.setForeground(Color.LIGHT_GRAY);
            gbc.gridx = 0;
            card.add(timeLabel, gbc);

            JLabel timeValue = new JLabel(event.getEventTime());
            timeValue.setForeground(Color.LIGHT_GRAY);
            gbc.gridx = 1;
            card.add(timeValue, gbc);

            gbc.gridy++;
            gbc.gridx = 0;
            JLabel dateLabel = new JLabel("Date:");
            dateLabel.setForeground(Color.LIGHT_GRAY);
            card.add(dateLabel, gbc);

            gbc.gridx = 1;
            JLabel dateValue = new JLabel(event.getEventDate());
            dateValue.setForeground(Color.LIGHT_GRAY);
            card.add(dateValue, gbc);

            gbc.gridy++;
            gbc.gridx = 0;
            JLabel locationLabel = new JLabel("Location:");
            locationLabel.setForeground(Color.LIGHT_GRAY);
            card.add(locationLabel, gbc);

            gbc.gridx = 1;
            JLabel locationValue = new JLabel(event.getEventLocation());
            locationValue.setForeground(Color.LIGHT_GRAY);
            card.add(locationValue, gbc);

            gbc.gridy++;
            gbc.gridx = 0;
            JLabel priceLabel = new JLabel("Price:");
            priceLabel.setForeground(new Color(0, 255, 153));
            priceLabel.setFont(priceLabel.getFont().deriveFont(Font.BOLD));
            card.add(priceLabel, gbc);
            gbc.gridx = 1;
            JLabel priceValue = new JLabel("Rs. " + event.getEventPrice());
            priceValue.setForeground(new Color(0, 255, 153));
            priceValue.setFont(priceValue.getFont().deriveFont(Font.BOLD));
            card.add(priceValue, gbc);

            // Row 6 - Ticket Availability
            gbc.gridy++;
            gbc.gridx = 0;
            JLabel ticketLabel = new JLabel("Tickets:");
            ticketLabel.setForeground(Color.LIGHT_GRAY);
            card.add(ticketLabel, gbc);

            gbc.gridx = 1;
            // Get ticket availability for this event
            eventflow.dao.EventDao.TicketAvailability ticketInfo = eventflow.controllers.DashboardController
                    .getTicketAvailability(event.getId());

            JLabel ticketValue = new JLabel(ticketInfo.getAvailabilityText());
            if (ticketInfo.isSoldOut()) {
                ticketValue.setForeground(new Color(255, 100, 100)); // Red for sold out
                ticketValue.setFont(ticketValue.getFont().deriveFont(Font.BOLD));
            } else {
                ticketValue.setForeground(new Color(100, 200, 255)); // Blue for available
            }
            card.add(ticketValue, gbc);

            // Row 7 - Button area spanning 2 columns, left aligned
            gbc.gridy++;
            gbc.gridx = 0;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;
            // Create button panel to hold multiple buttons side by side
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
            buttonPanel.setBackground(new Color(66, 66, 116)); // Match card background
            buttonPanel.setOpaque(false); // Make transparent to show card background
            buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Show delete button for own events OR admin users
            if (event.getCreatorUserId() == user.getId() || user.isAdmin()) {
                JButton deleteButton = new JButton("Delete");
                deleteButton.setBackground(new Color(220, 53, 69)); // Red color
                deleteButton.setForeground(Color.WHITE);
                deleteButton.setFocusPainted(false);
                deleteButton.setPreferredSize(new Dimension(80, 30));
                deleteButton.setMaximumSize(new Dimension(80, 30)); // Prevent stretching
                deleteButton.setFocusPainted(false);
                deleteButton.setPreferredSize(new Dimension(80, 30));
                deleteButton.addActionListener(actionEvent -> {
                    // Get refund information first
                    eventflow.dao.EventDao.RefundInfo refundInfo = eventflow.controllers.DashboardController
                            .getRefundInfo(event.getId());

                    // Different confirmation messages for admin vs owner
                    String confirmMessage;
                    if (user.isAdmin() && event.getCreatorUserId() != user.getId()) {
                        confirmMessage = "As an ADMIN, are you sure you want to delete '" + event.getEventTitle() +
                                "' created by " + event.getUploaderFullname() + "?\n\n" +
                                "REFUND DETAILS:\n" +
                                "• " + refundInfo.getTicketCount() + " ticket(s) will be refunded\n" +
                                "• Total refund amount: Rs " + String.format("%.2f", refundInfo.getTotalRefund())
                                + "\n\n" +
                                "This action cannot be undone.";
                    } else {
                        confirmMessage = "Are you sure you want to delete the event '" + event.getEventTitle()
                                + "'?\n\n" +
                                "REFUND DETAILS:\n" +
                                "• " + refundInfo.getTicketCount() + " ticket(s) will be refunded\n" +
                                "• Total refund amount: Rs " + String.format("%.2f", refundInfo.getTotalRefund())
                                + "\n\n" +
                                "This action cannot be undone.";
                    }

                    // Confirm deletion dialog
                    int confirm = JOptionPane.showConfirmDialog(
                            this,
                            confirmMessage,
                            "Confirm Deletion & Refunds",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);

                    if (confirm == JOptionPane.YES_OPTION) {
                        // Delete event using controller (now includes automatic refunds)
                        boolean deleteSuccess = eventflow.controllers.DashboardController.deleteEvent(event.getId());

                        if (deleteSuccess) {
                            String successMessage;
                            if (user.isAdmin() && event.getCreatorUserId() != user.getId()) {
                                successMessage = "Event '" + event.getEventTitle() + "' by " +
                                        event.getUploaderFullname()
                                        + " has been deleted successfully! (Admin Action)\n\n" +
                                        "REFUNDS PROCESSED:\n" +
                                        "• " + refundInfo.getTicketCount() + " user(s) refunded\n" +
                                        "• Total amount refunded: Rs "
                                        + String.format("%.2f", refundInfo.getTotalRefund);
                            } else {
                                successMessage = "Event '" + event.getEventTitle()
                                        + "' has been deleted successfully!\n\n" +
                                        "REFUNDS PROCESSED:\n" +
                                        "• " + refundInfo.getTicketCount() + " user(s) refunded\n" +
                                        "• Total amount refunded: Rs "
                                        + String.format("%.2f", refundInfo.getTotalRefund);
                            }

                            JOptionPane.showMessageDialog(
                                    this,
                                    successMessage,
                                    "Event Deleted",
                                    JOptionPane.INFORMATION_MESSAGE);

                            // Remove the event card from UI immediately
                            Container parent = card.getParent();
                            parent.remove(card);

                            // Also remove the spacing component that follows this card
                            Component[] components = parent.getComponents();
                            for (int i = 0; i < components.length - 1; i++) {
                                if (components[i] == card && components[i + 1] instanceof Box.Filler) {
                                    parent.remove(components[i + 1]);
                                    break;
                                }
                            }

                            parent.revalidate();
                            parent.repaint();
                        } else {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Failed to delete the event. Please try again.",
                                    "Deletion Failed",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                buttonPanel.add(deleteButton);

                // Add spacing between buttons if both will be shown
                if (event.getCreatorUserId() != user.getId()) {
                    buttonPanel.add(Box.createHorizontalStrut(10)); // 10px spacing
                }
            } // Show pay button for events NOT created by the user (including admin users)
            if (event.getCreatorUserId() != user.getId()) {
                JButton payButton;

                // Check if event is sold out
                if (ticketInfo.isSoldOut()) {
                    // Create disabled "Sold Out" button
                    payButton = new JButton("Sold Out");
                    payButton.setBackground(new Color(128, 128, 128)); // Gray color
                    payButton.setForeground(Color.WHITE);
                    payButton.setEnabled(false);
                    payButton.setFocusPainted(false);
                    payButton.setPreferredSize(new Dimension(80, 30));
                    payButton.setMaximumSize(new Dimension(80, 30));
                    // No action listener needed since button is disabled
                } else {
                    // Create regular "Pay" button
                    payButton = new JButton("Pay");
                    payButton.setBackground(new Color(6, 200, 164));
                    payButton.setForeground(Color.WHITE);
                    payButton.setFocusPainted(false);
                    payButton.setPreferredSize(new Dimension(80, 30));
                    payButton.setMaximumSize(new Dimension(80, 30));

                    payButton.addActionListener(actionEvent -> {
                        // Double-check ticket availability at payment time (in case it sold out since
                        // UI loaded)
                        eventflow.dao.EventDao.TicketAvailability currentTicketInfo = eventflow.controllers.DashboardController
                                .getTicketAvailability(event.getId());

                        if (currentTicketInfo.isSoldOut()) {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Sorry, this event is now sold out! All tickets have been purchased.",
                                    "Event Sold Out",
                                    JOptionPane.WARNING_MESSAGE);

                            // Update button to reflect sold out status
                            payButton.setText("Sold Out");
                            payButton.setBackground(new Color(128, 128, 128));
                            payButton.setEnabled(false);
                            return;
                        }

                        // Check if user has sufficient balance
                        if (user.getBalance() < event.getEventPrice()) {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Insufficient balance! You need Rs. " + event.getEventPrice()
                                            + " but only have Rs. "
                                            + user.getBalance(),
                                    "Insufficient Funds",
                                    JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        // Confirm payment dialog
                        int confirm = JOptionPane.showConfirmDialog(
                                this,
                                "Are you sure you want to pay Rs. " + event.getEventPrice() + " for "
                                        + event.getEventTitle() + "?\n" +
                                        "Your current balance: Rs. " + user.getBalance(),
                                "Confirm Payment",
                                JOptionPane.YES_NO_OPTION);

                        if (confirm == JOptionPane.YES_OPTION) {
                            // Process payment using controller
                            boolean paymentSuccess = eventflow.controllers.DashboardController.payForEvent(
                                    user.getId(),
                                    event.getId(),
                                    event.getEventPrice());                            if (paymentSuccess) {
                                // Update user's balance in real-time
                                user.setBalance(user.getBalance() - event.getEventPrice());

                                // Get updated ticket availability after purchase
                                eventflow.dao.EventDao.TicketAvailability updatedTicketInfo = 
                                    eventflow.controllers.DashboardController.getTicketAvailability(event.getId());

                                JOptionPane.showMessageDialog(
                                        this,
                                        "Payment successful! You have purchased a ticket for " + event.getEventTitle()
                                                + "\n" +
                                                "Amount paid: Rs. " + event.getEventPrice() + "\n" +
                                                "New balance: Rs. " + user.getBalance() + "\n" +
                                                "Remaining tickets: " + updatedTicketInfo.getAvailabilityText(),
                                        "Payment Success",
                                        JOptionPane.INFORMATION_MESSAGE);

                                // Update the ticket availability display in real-time
                                ticketValue.setText(updatedTicketInfo.getAvailabilityText());
                                if (updatedTicketInfo.isSoldOut()) {
                                    ticketValue.setForeground(new Color(255, 100, 100)); // Red for sold out
                                    ticketValue.setFont(ticketValue.getFont().deriveFont(Font.BOLD));
                                    
                                    // NOW disable the button since event is sold out
                                    payButton.setEnabled(false);
                                    payButton.setText("Sold Out");
                                    payButton.setBackground(new Color(128, 128, 128)); // Gray color
                                } else {
                                    ticketValue.setForeground(new Color(100, 200, 255)); // Blue for available
                                    // Keep the Pay button active - user can buy more tickets!
                                }
                                
                                // Refresh the display
                                card.revalidate();
                                card.repaint();
                            } else {
                                JOptionPane.showMessageDialog(
                                        this,
                                        "Payment failed! Please check your balance and try again.",
                                        "Payment Failed",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });
                }

                buttonPanel.add(payButton);
            }

            // Add glue to push buttons to the left
            buttonPanel.add(Box.createHorizontalGlue());

            // Add the button panel to the card
            card.add(buttonPanel, gbc);

            // Add vertical spacing between cards
            eventsPanel.add(card);
            eventsPanel.add(Box.createVerticalStrut(20));
        }

        eventsPanel.revalidate();
        eventsPanel.repaint();
        
        // Activate search functionality now that we have events
        activateSearch();
    }

    public void activateSearch() {
        // Setup search field placeholder behavior
        searchField.setText("🔎Search");
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchField.getText().equals("🔎Search")) {
                    searchField.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("🔎Search");
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jLabel2 = new javax.swing.JLabel();
        jFrame1 = new javax.swing.JFrame();
        jScrollPane4 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        eventsPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();

        jScrollPane1.setViewportView(jEditorPane1);

        jLabel2.setText("jLabel2");

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(58, 58, 98));

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
        sideLabel.setText("username");

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
                    .addComponent(dashBut, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addGap(36, 36, 36))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(sideLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 259, Short.MAX_VALUE)
                .addComponent(sideLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Welcome Username");

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(160, 160, 178));
        jLabel4.setText("Here's what's happening in your events today");

        eventsPanel.setBackground(new java.awt.Color(66, 66, 116));

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Please click on any one of the navigation in the sidebar and come back in Dashboard for the content to load");

        javax.swing.GroupLayout eventsPanelLayout = new javax.swing.GroupLayout(eventsPanel);
        eventsPanel.setLayout(eventsPanelLayout);
        eventsPanelLayout.setHorizontalGroup(
            eventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(eventsPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        eventsPanelLayout.setVerticalGroup(
            eventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(eventsPanelLayout.createSequentialGroup()
                .addGroup(eventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(eventsPanelLayout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(jLabel9))
                    .addGroup(eventsPanelLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(76, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Recent activities");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 153, 255));
        jLabel12.setText("View  all");

        searchField.setText("🔎Search");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(eventsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 725, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 20, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(157, 157, 157)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 587, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel12))
                        .addGap(65, 65, 65)
                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(eventsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jScrollPane3.setViewportView(jPanel1);

        jScrollPane4.setViewportView(jScrollPane3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 959, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logButActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_logButActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_logButActionPerformed

    private void myTicketsbutActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_myTicketsbutActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_myTicketsbutActionPerformed

    private void dashButActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_dashButActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_dashButActionPerformed    // Search functionality methods - UI layer only, business logic is in DashboardController
    public void initializeSearchFunctionality() {
        // Initialize the noResultsLabel
        noResultsLabel = new JLabel("No events found");
        noResultsLabel.setForeground(Color.WHITE);
        noResultsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        noResultsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        noResultsLabel.setVisible(false);
        
        // Add DocumentListener to searchField for real-time search
        // The actual search logic is handled by DashboardController (MVC pattern)
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                performSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                performSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                performSearch();
            }
        });
    }private void performSearch() {
        String searchText = searchField.getText();
        
        // Use controller to validate and process search query
        String validatedQuery = eventflow.controllers.DashboardController.validateSearchQuery(searchText);
        
        // If validation returns null, don't perform search (invalid query or placeholder text)
        if (validatedQuery == null || allEvents == null) {
            return;
        }
        
        // Use controller to search events - this follows MVC pattern
        List<EventWithUser> filteredEvents = eventflow.controllers.DashboardController.searchEventsByTitle(allEvents, validatedQuery);
        
        // Update the display based on filtered results
        updateEventsDisplay(filteredEvents);
    }

    private void updateEventsDisplay(List<EventWithUser> events) {
        eventsPanel.removeAll();
        
        if (events.isEmpty()) {
            // Hide events panel content and show no results label
            eventsPanel.setLayout(new BorderLayout());
            eventsPanel.add(noResultsLabel, BorderLayout.CENTER);
            noResultsLabel.setVisible(true);
        } else {
            // Show filtered events using the same logic as loadEvents
            noResultsLabel.setVisible(false);
            eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
            
            for (EventWithUser event : events) {
                // Recreate the same card structure as in loadEvents
                JPanel card = new JPanel(new GridBagLayout());
                card.setBackground(new Color(66, 66, 116));
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(90, 90, 140), 1, true),
                        BorderFactory.createEmptyBorder(15, 20, 15, 20)));
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
                card.setAlignmentX(Component.LEFT_ALIGNMENT);

                // Add all the same content as the original loadEvents method
                createEventCardContent(card, event);
                
                eventsPanel.add(card);
                eventsPanel.add(Box.createVerticalStrut(20));
            }
        }
        
        eventsPanel.revalidate();
        eventsPanel.repaint();
    }    private void createEventCardContent(JPanel card, EventWithUser event) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Row 0 - Uploader Name (bold)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel nameLabel = new JLabel(event.getUploaderFullname());
        nameLabel.setForeground(new Color(180, 180, 255));
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        card.add(nameLabel, gbc);

        // Row 1 - Event Title (bigger, bold)
        gbc.gridy++;
        JLabel titleLabel = new JLabel(event.getEventTitle());
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        card.add(titleLabel, gbc);

        // Row 2 - Description (wrap with HTML)
        gbc.gridy++;
        JLabel descLabel = new JLabel(
                "<html><body style='width:650px;'>" + event.getEventDesc() + "</body></html>");
        descLabel.setForeground(new Color(190, 190, 210));
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        card.add(descLabel, gbc);

        // Row 3 - Info labels in 2 columns: label and value pairs
        gbc.gridy++;
        gbc.gridwidth = 1;

        JLabel timeLabel = new JLabel("Time:");
        timeLabel.setForeground(Color.LIGHT_GRAY);
        gbc.gridx = 0;
        card.add(timeLabel, gbc);

        JLabel timeValue = new JLabel(event.getEventTime());
        timeValue.setForeground(Color.LIGHT_GRAY);
        gbc.gridx = 1;
        card.add(timeValue, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setForeground(Color.LIGHT_GRAY);
        card.add(dateLabel, gbc);

        gbc.gridx = 1;
        JLabel dateValue = new JLabel(event.getEventDate());
        dateValue.setForeground(Color.LIGHT_GRAY);
        card.add(dateValue, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setForeground(Color.LIGHT_GRAY);
        card.add(locationLabel, gbc);

        gbc.gridx = 1;
        JLabel locationValue = new JLabel(event.getEventLocation());
        locationValue.setForeground(Color.LIGHT_GRAY);
        card.add(locationValue, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setForeground(new Color(0, 255, 153));
        priceLabel.setFont(priceLabel.getFont().deriveFont(Font.BOLD));
        card.add(priceLabel, gbc);
        gbc.gridx = 1;
        JLabel priceValue = new JLabel("Rs. " + event.getEventPrice());
        priceValue.setForeground(new Color(0, 255, 153));
        priceValue.setFont(priceValue.getFont().deriveFont(Font.BOLD));
        card.add(priceValue, gbc);

        // Row 6 - Ticket Availability
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel ticketLabel = new JLabel("Tickets:");
        ticketLabel.setForeground(Color.LIGHT_GRAY);
        card.add(ticketLabel, gbc);

        gbc.gridx = 1;
        // Get ticket availability for this event
        eventflow.dao.EventDao.TicketAvailability ticketInfo = eventflow.controllers.DashboardController
                .getTicketAvailability(event.getId());

        JLabel ticketValue = new JLabel(ticketInfo.getAvailabilityText());
        if (ticketInfo.isSoldOut()) {
            ticketValue.setForeground(new Color(255, 100, 100)); // Red for sold out
            ticketValue.setFont(ticketValue.getFont().deriveFont(Font.BOLD));
        } else {
            ticketValue.setForeground(new Color(100, 200, 255)); // Blue for available
        }
        card.add(ticketValue, gbc);

        // Row 7 - Button area (simplified for search results)
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add basic Info button for search results
        JButton infoButton = new JButton("Info");
        infoButton.setBackground(new Color(70, 130, 180));
        infoButton.setForeground(Color.WHITE);
        infoButton.setFocusPainted(false);
        infoButton.setPreferredSize(new Dimension(80, 30));
        infoButton.setMaximumSize(new Dimension(80, 30));
        buttonPanel.add(infoButton);
        
        buttonPanel.add(Box.createHorizontalGlue());
        card.add(buttonPanel, gbc);
    }

    // Variables declaration - add the essential ones needed for search functionality
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JToggleButton dashBut;
    private javax.swing.JPanel eventsPanel;
    private javax.swing.JToggleButton logBut;
    private javax.swing.JToggleButton myEventsbut;
    private javax.swing.JToggleButton myTicketsbut;
    private javax.swing.JToggleButton profileBut;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel sideLabel;
    private javax.swing.JToggleButton walletBut;
    // End of variables declaration
}