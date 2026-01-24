package ui;

import javax.swing.*;
import java.awt.*;

// The SplashScreen class represents a graphical splash screen displayed at the launch of an application.
// The splash screen has a progress loading board and displays an image and some text to give the application a more
// aesthetic appearance.
public class SplashScreen {

    private static final int SPLASHSCREEN_TIME = 3000;
    private static final int PROGRESSBAR_TIME = 20;

    private static JFrame splashScreen;
    private static JProgressBar progressBar;

    // EFFECTS: starts the SplashScreen
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SplashScreen());
    }

    // EFFECTS: calls the methods to display the splash screen properly
    public SplashScreen() {
        initSplashScreen();
        initProgressBar();
        startSplashScreenTimer();
    }

    // MODIFIES: this
    // EFFECTS: initializes the splash screen
    public static void initSplashScreen() {
        splashScreen = new JFrame();
        splashScreen.setSize(1000, 500);
        splashScreen.setUndecorated(true);
        JPanel panel = createSplashScreenComponents();
        splashScreen.setLocationRelativeTo(null);
        splashScreen.getContentPane().add(panel);
        splashScreen.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Initializes the components of the splash screen such as header text, image, and progress bar.
    //          Sets design parameters for each component as well.
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public static JPanel createSplashScreenComponents() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        JLabel welcomeLabel = new JLabel("Hotel Management Software", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Verdana", Font.BOLD, 40));
        welcomeLabel.setForeground(Color.RED);
        ImageIcon imageIcon = new ImageIcon("./data/splashScreenImage.png");
        Image image = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon resizedImageIcon = new ImageIcon(image);
        JLabel imageLabel = new JLabel(resizedImageIcon);
        progressBar = new JProgressBar(SwingConstants.VERTICAL);
        progressBar.setPreferredSize(new Dimension(20, 200));
        progressBar.setStringPainted(true);
        progressBar.setForeground(Color.RED);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(welcomeLabel, gbc);
        gbc.gridy = 1;
        panel.add(imageLabel, gbc);
        gbc.gridy = 2;
        panel.add(progressBar, gbc);
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: Increases progressBar length and status periodically
    private void initProgressBar() {
        Timer timer = new Timer(PROGRESSBAR_TIME, e -> {
            int value = progressBar.getValue();
            if (value < progressBar.getMaximum()) {
                progressBar.setValue(value + 1);
            }
        });
        timer.start();
    }

    // MODIFIES: this
    // EFFECTS: Keeps track of how long the splashScreen should be displayed and open HotelManagementGUI after.
    private void startSplashScreenTimer() {
        Timer timer = new Timer(SPLASHSCREEN_TIME, e -> {
            splashScreen.dispose();
            SwingUtilities.invokeLater(() -> new HotelManagementAppGUI());
        });
        timer.setRepeats(false);
        timer.start();
    }

}
