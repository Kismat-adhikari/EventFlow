package eventflow.views;

import eventflow.models.User;
import eventflow.models.Profile;
import eventflow.services.ProfileService;
import eventflow.controllers.InfoController;

import javax.swing.*;

public class InfoForm extends javax.swing.JFrame {
    private final User user;
    private final ProfileForm profileForm; // reference to currently open ProfileForm

    public InfoForm(User user, ProfileForm profileForm) {
        this.user = user;
        this.profileForm = profileForm;
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); // Close only this window
        loadUserProfile();
        setupListeners();
    }

    private void loadUserProfile() {
        Profile profile = ProfileService.getProfileByUserId(user.getId());
        if (profile != null) {
            userBio.setText(profile.getBio());
            userLocation.setText(profile.getLocation());  // load location too
        }
    }

    private void setupListeners() {
        makeBut.addActionListener(evt -> {
            String newBio = userBio.getText().trim();
            String newLocation = userLocation.getText().trim();

            InfoController.updateUserBio(user, newBio);
            InfoController.updateUserLocation(user, newLocation);

            JOptionPane.showMessageDialog(this, "Profile updated successfully!");

            // Close the old ProfileForm window
            profileForm.dispose();

            // Open a new ProfileForm with updated info
            ProfileForm newProfile = new ProfileForm(user);
            newProfile.setVisible(true);

            // Close this InfoForm window
            dispose();
        });
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        userLocation = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        userBio = new javax.swing.JTextArea();
        makeBut = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Location");

        userLocation.setText("jTextField1");

        jLabel2.setText("Bio");

        userBio.setColumns(20);
        userBio.setRows(5);
        jScrollPane1.setViewportView(userBio);

        makeBut.setText("Chnage changes");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(userLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(46, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(makeBut)
                .addGap(116, 116, 116))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(userLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addComponent(makeBut)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton makeBut;
    private javax.swing.JTextArea userBio;
    private javax.swing.JTextField userLocation;
    // End of variables declaration//GEN-END:variables
}
