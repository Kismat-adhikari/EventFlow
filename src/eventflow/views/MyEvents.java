/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package eventflow.views;

import eventflow.controllers.MyEventsController;
import eventflow.dao.EventDao;
import eventflow.dao.EventDao.EventWithUser;
import eventflow.models.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class MyEvents extends javax.swing.JFrame {

    private final User user;
    private MyEventsController controller;
    private boolean isEditMode = false;
    private List<EventCard> eventCards = new ArrayList<>();

    public MyEvents(User user) {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.user = user;
        this.controller = new MyEventsController();

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

        // Add edit button functionality
        if (jButton1 != null) {
            jButton1.setText("Edit");
            jButton1.addActionListener(evt -> toggleEditMode());
        }

        loadUserEvents();
    }

    private void toggleEditMode() {
        isEditMode = !isEditMode;

        if (isEditMode) {
            jButton1.setText("Cancel");
        } else {
            jButton1.setText("Edit");
        }

        // Toggle edit mode for all event cards
        for (EventCard card : eventCards) {
            card.setEditMode(isEditMode);
        }

        controller.toggleEditMode(isEditMode);
    }

    private void loadUserEvents() {
        EventDao dao = new EventDao();
        List<EventWithUser> events = dao.getEventsByUploader(user.getId());

        // Safely handle null before updating event count label
        int eventCount = (events != null) ? events.size() : 0;
        tEvents.setText("Events: " + eventCount);

        myEventsPanel.removeAll();
        eventCards.clear();
        myEventsPanel.setLayout(new BoxLayout(myEventsPanel, BoxLayout.Y_AXIS));

        if (eventCount == 0) {
            JLabel noEvents = new JLabel("You have not created any events.");
            noEvents.setForeground(Color.WHITE);
            noEvents.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            noEvents.setAlignmentX(Component.LEFT_ALIGNMENT);
            myEventsPanel.add(noEvents);
        } else {
            for (EventWithUser event : events) {
                EventCard eventCard = new EventCard(event);
                eventCards.add(eventCard);
                myEventsPanel.add(eventCard.getPanel());
                myEventsPanel.add(Box.createVerticalStrut(20));
            }
        }

        myEventsPanel.revalidate();
        myEventsPanel.repaint();
    } // Inner class to represent an editable event card

    private class EventCard {
        private JPanel cardPanel;
        private JLabel titleLabel;
        private JLabel descLabel;
        private JLabel timeLabel;
        private JLabel dateLabel;
        private JLabel locationLabel;
        private JLabel priceLabel;

        private JTextField titleField;
        private JTextArea descField;
        private JScrollPane descScrollPane;
        private JTextField timeField;
        private JTextField dateField;
        private JTextField locationField;
        private JTextField priceField;

        private JPanel timePanel;
        private JPanel datePanel;
        private JPanel locationPanel;
        private JPanel pricePanel;

        private JPanel buttonPanel;
        private JButton saveButton;
        private JButton cancelButton;

        private EventWithUser event;
        private boolean editMode = false;

        public EventCard(EventWithUser event) {
            this.event = event;
            createCard();
        }

        private void createCard() {
            cardPanel = new JPanel();
            cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
            cardPanel.setBackground(new Color(66, 66, 116));
            cardPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
            cardPanel.setMaximumSize(new Dimension(700, 300));
            cardPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Create display labels
            titleLabel = new JLabel(event.getEventTitle());
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

            descLabel = new JLabel("<html><body style='width:650px'>" + event.getEventDesc() + "</body></html>");
            descLabel.setForeground(new Color(200, 200, 200));
            descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));

            timeLabel = new JLabel("Time: " + event.getEventTime());
            dateLabel = new JLabel("Date: " + event.getEventDate());
            locationLabel = new JLabel("Location: " + event.getEventLocation());
            priceLabel = new JLabel("Price: Rs. " + event.getEventPrice());

            for (JLabel label : new JLabel[] { timeLabel, dateLabel, locationLabel }) {
                label.setForeground(Color.LIGHT_GRAY);
                label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            }

            priceLabel.setForeground(new Color(0, 255, 153));
            priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));

            // Create edit fields (initially hidden)
            titleField = new JTextField(event.getEventTitle());
            titleField.setFont(new Font("Segoe UI", Font.BOLD, 16));
            titleField.setVisible(false);
            descField = new JTextArea(event.getEventDesc());
            descField.setRows(3);
            descField.setWrapStyleWord(true);
            descField.setLineWrap(true);
            descField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            descScrollPane = new JScrollPane(descField);
            descScrollPane.setVisible(false);
            descScrollPane.setMaximumSize(new Dimension(650, 60));
            timeField = new JTextField(event.getEventTime());
            timeField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            timeField.setPreferredSize(new Dimension(200, 25));

            dateField = new JTextField(event.getEventDate());
            dateField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            dateField.setPreferredSize(new Dimension(200, 25));

            locationField = new JTextField(event.getEventLocation());
            locationField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            locationField.setPreferredSize(new Dimension(200, 25));

            priceField = new JTextField(String.valueOf(event.getEventPrice()));
            priceField.setFont(new Font("Segoe UI", Font.BOLD, 13));
            priceField.setPreferredSize(new Dimension(200, 25));

            // Create field panels and store references
            timePanel = createFieldPanel("Time: ", timeField);
            datePanel = createFieldPanel("Date: ", dateField);
            locationPanel = createFieldPanel("Location: ", locationField);
            pricePanel = createFieldPanel("Price: Rs. ", priceField);

            // Create buttons (initially hidden)
            buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            buttonPanel.setBackground(new Color(66, 66, 116));
            buttonPanel.setVisible(false);

            saveButton = new JButton("Save");
            saveButton.setBackground(new Color(0, 255, 153));
            saveButton.setForeground(Color.WHITE);
            saveButton.addActionListener(evt -> saveEvent());

            cancelButton = new JButton("Cancel");
            cancelButton.setBackground(new Color(255, 100, 100));
            cancelButton.setForeground(Color.WHITE);
            cancelButton.addActionListener(evt -> cancelEdit());

            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton); // Add all components to card
            cardPanel.add(titleLabel);
            cardPanel.add(titleField);
            cardPanel.add(Box.createVerticalStrut(5));
            cardPanel.add(descLabel);
            cardPanel.add(descScrollPane);
            cardPanel.add(Box.createVerticalStrut(5));
            cardPanel.add(timeLabel);
            cardPanel.add(timePanel);
            cardPanel.add(dateLabel);
            cardPanel.add(datePanel);
            cardPanel.add(locationLabel);
            cardPanel.add(locationPanel);
            cardPanel.add(priceLabel);
            cardPanel.add(pricePanel);
            cardPanel.add(Box.createVerticalStrut(10));
            cardPanel.add(buttonPanel);
        }

        private JPanel createFieldPanel(String labelText, JTextField field) {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            panel.setBackground(new Color(66, 66, 116));
            panel.setVisible(false);

            JLabel label = new JLabel(labelText);
            label.setForeground(Color.LIGHT_GRAY);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 12));

            panel.add(label);
            panel.add(field);
            return panel;
        }

        public void setEditMode(boolean editMode) {
            this.editMode = editMode;

            // Toggle visibility of labels vs fields
            titleLabel.setVisible(!editMode);
            titleField.setVisible(editMode);

            descLabel.setVisible(!editMode);
            descScrollPane.setVisible(editMode);

            timeLabel.setVisible(!editMode);
            timePanel.setVisible(editMode);

            dateLabel.setVisible(!editMode);
            datePanel.setVisible(editMode);

            locationLabel.setVisible(!editMode);
            locationPanel.setVisible(editMode);

            priceLabel.setVisible(!editMode);
            pricePanel.setVisible(editMode);

            buttonPanel.setVisible(editMode);

            if (!editMode) {
                // Reset fields to original values when canceling
                resetFields();
            }

            cardPanel.revalidate();
            cardPanel.repaint();
        }

        private void resetFields() {
            titleField.setText(event.getEventTitle());
            descField.setText(event.getEventDesc());
            timeField.setText(event.getEventTime());
            dateField.setText(event.getEventDate());
            locationField.setText(event.getEventLocation());
            priceField.setText(String.valueOf(event.getEventPrice()));
        }

        private void saveEvent() {
            // Get the updated values from the fields
            String newTitle = titleField.getText().trim();
            String newDesc = descField.getText().trim();
            String newTime = timeField.getText().trim();
            String newDate = dateField.getText().trim();
            String newLocation = locationField.getText().trim();
            String newPrice = priceField.getText().trim();

            // Call the controller to save the event
            boolean success = controller.saveEvent(event.getId(), newTitle, newDesc,
                    newTime, newDate, newLocation, newPrice);

            if (success) {
                // Update the event object with new values
                event.setEventTitle(newTitle);
                event.setEventDesc(newDesc);
                event.setEventTime(newTime);
                event.setEventDate(newDate);
                event.setEventLocation(newLocation);
                try {
                    event.setEventPrice(Double.parseDouble(newPrice));
                } catch (NumberFormatException e) {
                    // This shouldn't happen if validation worked
                }

                // Update the display labels with new values
                updateDisplayLabels();

                // Show success message
                JOptionPane.showMessageDialog(cardPanel, "Event updated successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);

                // Switch back to view mode
                toggleEditMode();
            } else {
                JOptionPane.showMessageDialog(cardPanel, "Failed to update event. Please check your input.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void updateDisplayLabels() {
            titleLabel.setText(event.getEventTitle());
            descLabel.setText("<html><body style='width:650px'>" + event.getEventDesc() + "</body></html>");
            timeLabel.setText("Time: " + event.getEventTime());
            dateLabel.setText("Date: " + event.getEventDate());
            locationLabel.setText("Location: " + event.getEventLocation());
            priceLabel.setText("Price: Rs. " + event.getEventPrice());
        }

        private void cancelEdit() {
            // Switch back to view mode via the main toggle
            toggleEditMode();
        }

        public JPanel getPanel() {
            return cardPanel;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel11 = new javax.swing.JPanel();
        myEventsPanel = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        eventDate = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        tEvents = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel10.setBackground(new java.awt.Color(59, 58, 98));

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
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dashBut, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
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
                .addGap(18, 18, 18)
                .addComponent(logBut, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sideLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel11.setBackground(new java.awt.Color(58, 58, 98));

        myEventsPanel.setBackground(new java.awt.Color(66, 66, 116));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Title");

        jLabel13.setForeground(new java.awt.Color(160, 160, 178));
        jLabel13.setText("Description of the Event");

        eventDate.setForeground(new java.awt.Color(160, 160, 178));
        eventDate.setText("Date ");

        jLabel15.setForeground(new java.awt.Color(51, 255, 153));
        jLabel15.setText("Price");

        jLabel17.setForeground(new java.awt.Color(160, 160, 178));
        jLabel17.setText("Total attendees");

        jLabel18.setForeground(new java.awt.Color(160, 160, 178));
        jLabel18.setText("Location");

        jLabel2.setForeground(new java.awt.Color(160, 160, 178));
        jLabel2.setText("Time");

        javax.swing.GroupLayout myEventsPanelLayout = new javax.swing.GroupLayout(myEventsPanel);
        myEventsPanel.setLayout(myEventsPanelLayout);
        myEventsPanelLayout.setHorizontalGroup(
            myEventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(myEventsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(myEventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addGroup(myEventsPanelLayout.createSequentialGroup()
                        .addComponent(eventDate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(myEventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, myEventsPanelLayout.createSequentialGroup()
                            .addComponent(jLabel17)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel18))
                        .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(301, Short.MAX_VALUE))
        );
        myEventsPanelLayout.setVerticalGroup(
            myEventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(myEventsPanelLayout.createSequentialGroup()
                .addGap(123, 123, 123)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(myEventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(eventDate)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(myEventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("My Events");

        jButton1.setBackground(new java.awt.Color(6, 200, 164));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Create New Event");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(66, 66, 116));

        jLabel4.setForeground(new java.awt.Color(160, 160, 178));
        jLabel4.setText("Total Events");

        tEvents.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        tEvents.setForeground(new java.awt.Color(255, 255, 255));
        tEvents.setText("Number");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(tEvents))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(tEvents)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(myEventsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel11Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1))
                        .addGroup(jPanel11Layout.createSequentialGroup()
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(441, 441, 441))))
                .addContainerGap(197, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jButton1))
                .addGap(28, 28, 28)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(myEventsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(230, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel11);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 796, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dashButActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_dashButActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_dashButActionPerformed

    private void myTicketsbutActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_myTicketsbutActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_myTicketsbutActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButton1ActionPerformed

    private void logButActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_logButActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_logButActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MyEvents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MyEvents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MyEvents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MyEvents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton dashBut;
    private javax.swing.JLabel eventDate;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JToggleButton logBut;
    private javax.swing.JPanel myEventsPanel;
    private javax.swing.JToggleButton myEventsbut;
    private javax.swing.JToggleButton myTicketsbut;
    private javax.swing.JToggleButton profileBut;
    private javax.swing.JLabel sideLabel;
    private javax.swing.JLabel tEvents;
    private javax.swing.JToggleButton walletBut;
    // End of variables declaration//GEN-END:variables
}
