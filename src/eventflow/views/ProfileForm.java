package eventflow.views;

import eventflow.models.User;
import eventflow.models.Profile;
import eventflow.services.ProfileService;

import javax.swing.JFrame;

public class ProfileForm extends javax.swing.JFrame {

    private final User user;

    public ProfileForm(User user) {
        this.user = user;
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        loadUserData();
        setupNavigation();
    }

    private void loadUserData() {
        // Set main user details
        sideLabel.setText(user.getFullname());
        fullname.setText(user.getFullname());
        email.setText(user.getEmail());
        jTextField1.setText("EventInfo");
        Profile profile = ProfileService.getProfileByUserId(user.getId()); // Load user's bio from profile
        if (profile != null) {
            bio.setText(profile.getBio());
            location.setText(profile.getLocation()); // <-- Set location label here
        } else {
            bio.setText("");
            location.setText("");
        }

        // Load user's event count using controller
        int eventCount = eventflow.controllers.ProfileController.getUserEventCount(user.getId());
        tEvents.setText(String.valueOf(eventCount));

    }

    private void setupNavigation() {
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

        editProfile.addActionListener(evt -> {
            eventflow.controllers.ProfileController.goToEditProfile(user, this);
        }); // Add action listener for delete account button
        
        changePass.addActionListener(evt -> {
            eventflow.controllers.ProfileController.goToChangePass(user, this);
        });

        deleteAcc.addActionListener(evt -> {
            eventflow.controllers.ProfileController.deleteAccount(user, this);
        });
        logBut.addActionListener(evt -> {
            eventflow.controllers.DashboardController.goToSignup();
            dispose();
        });
        

    }

    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        fullname = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        tEvents = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jTextField11 = new javax.swing.JTextField();
        bio = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jTextField12 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        email = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        location = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        dashBut = new javax.swing.JToggleButton();
        myEventsbut = new javax.swing.JToggleButton();
        jToggleButton3 = new javax.swing.JToggleButton();
        myTicketsbut = new javax.swing.JToggleButton();
        walletBut = new javax.swing.JToggleButton();
        profileBut = new javax.swing.JToggleButton();
        sideLabel = new javax.swing.JLabel();
        logBut = new javax.swing.JToggleButton();
        editProfile = new javax.swing.JToggleButton();
        deleteAcc = new javax.swing.JToggleButton();
        changePass = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(58, 58, 98));

        jPanel3.setBackground(new java.awt.Color(66, 66, 116));

        jPanel2.setBackground(new java.awt.Color(0, 204, 153));

        jTextField1.setBackground(new java.awt.Color(0, 204, 153));
        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setText("KA");
        jTextField1.setBorder(null);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        fullname.setBackground(new java.awt.Color(66, 66, 116));
        fullname.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        fullname.setForeground(new java.awt.Color(255, 255, 255));
        fullname.setText("s");
        fullname.setBorder(null);
        fullname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fullnameActionPerformed(evt);
            }
        });

        jTextField4.setBackground(new java.awt.Color(66, 66, 116));
        jTextField4.setForeground(new java.awt.Color(255, 255, 255));
        jTextField4.setText("Events\n");
        jTextField4.setBorder(null);
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        tEvents.setBackground(new java.awt.Color(66, 66, 116));
        tEvents.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tEvents.setForeground(new java.awt.Color(255, 255, 255));
        tEvents.setText("15");
        tEvents.setBorder(null);
        tEvents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tEventsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tEvents, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(110, 110, 110)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(90, 90, 90)
                            .addComponent(fullname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(202, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(fullname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(tEvents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(83, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(66, 66, 116));

        jTextField11.setBackground(new java.awt.Color(66, 66, 116));
        jTextField11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField11.setForeground(new java.awt.Color(255, 255, 255));
        jTextField11.setText("About Me");
        jTextField11.setBorder(null);
        jTextField11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField11ActionPerformed(evt);
            }
        });

        bio.setBackground(new java.awt.Color(66, 66, 116));
        bio.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        bio.setForeground(new java.awt.Color(255, 255, 255));
        bio.setText("bio");
        bio.setBorder(null);
        bio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(bio, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bio, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(66, 66, 116));

        jTextField12.setBackground(new java.awt.Color(66, 66, 116));
        jTextField12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTextField12.setForeground(new java.awt.Color(255, 255, 255));
        jTextField12.setText("Contact Info");
        jTextField12.setBorder(null);
        jTextField12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField12ActionPerformed(evt);
            }
        });

        jTextField17.setBackground(new java.awt.Color(66, 66, 116));
        jTextField17.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jTextField17.setForeground(new java.awt.Color(255, 255, 255));
        jTextField17.setText("Email");
        jTextField17.setBorder(null);
        jTextField17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField17ActionPerformed(evt);
            }
        });

        email.setBackground(new java.awt.Color(66, 66, 116));
        email.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        email.setForeground(new java.awt.Color(255, 255, 255));
        email.setText("kismatahi@gmail.com");
        email.setBorder(null);
        email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailActionPerformed(evt);
            }
        });

        jTextField18.setBackground(new java.awt.Color(66, 66, 116));
        jTextField18.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jTextField18.setForeground(new java.awt.Color(255, 255, 255));
        jTextField18.setText("Location");
        jTextField18.setBorder(null);
        jTextField18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField18ActionPerformed(evt);
            }
        });

        location.setBackground(new java.awt.Color(66, 66, 116));
        location.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        location.setForeground(new java.awt.Color(255, 255, 255));
        location.setText("location");
        location.setBorder(null);
        location.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locationActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(106, 106, 106)))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(location, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(83, 83, 83))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(location, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(65, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Profile");

        jPanel13.setBackground(new java.awt.Color(52, 52, 86));

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

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(walletBut, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(myTicketsbut, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                                    .addComponent(jToggleButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(myEventsbut, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(profileBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(logBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(30, 30, 30))
                            .addComponent(sideLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(dashBut, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
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

        editProfile.setBackground(new java.awt.Color(1, 200, 167));
        editProfile.setForeground(new java.awt.Color(255, 255, 255));
        editProfile.setText("Edit Profile");

        deleteAcc.setBackground(new java.awt.Color(1, 200, 167));
        deleteAcc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        deleteAcc.setForeground(new java.awt.Color(255, 255, 255));
        deleteAcc.setText("Delete Account");

        changePass.setBackground(new java.awt.Color(1, 200, 167));
        changePass.setForeground(new java.awt.Color(255, 255, 255));
        changePass.setText("Change Pass");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(139, 139, 139)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(changePass)
                                        .addGap(36, 36, 36)
                                        .addComponent(editProfile))
                                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 447, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addComponent(deleteAcc, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(210, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(editProfile)
                    .addComponent(changePass))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(deleteAcc, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(287, Short.MAX_VALUE))
            .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextField7ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextField4ActionPerformed

    private void fullnameActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_fullnameActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_fullnameActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField18ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField18ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextField18ActionPerformed

    private void locationActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_locationActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_locationActionPerformed

    private void logButActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_logButActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_logButActionPerformed

    private void dashButActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_dashButActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_dashButActionPerformed

    private void myTicketsbutActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_myTicketsbutActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_myTicketsbutActionPerformed

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField11ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextField11ActionPerformed

    private void bioFieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bioFieldActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_bioFieldActionPerformed

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField12ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextField12ActionPerformed

    private void jTextField17ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField17ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jTextField17ActionPerformed

    private void emailActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_emailActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_emailActionPerformed

    private void tEventsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tEventsActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_tEventsActionPerformed

    public void refreshProfileData() {
        loadUserData(); // This will reload the bio and location from database
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField bio;
    private javax.swing.JToggleButton changePass;
    private javax.swing.JToggleButton dashBut;
    private javax.swing.JToggleButton deleteAcc;
    private javax.swing.JToggleButton editProfile;
    private javax.swing.JTextField email;
    private javax.swing.JTextField fullname;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JTextField location;
    private javax.swing.JToggleButton logBut;
    private javax.swing.JToggleButton myEventsbut;
    private javax.swing.JToggleButton myTicketsbut;
    private javax.swing.JToggleButton profileBut;
    private javax.swing.JLabel sideLabel;
    private javax.swing.JTextField tEvents;
    private javax.swing.JToggleButton walletBut;
    // End of variables declaration//GEN-END:variables
}
