package Frames;
import Classes.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame implements ActionListener, MouseListener {
    JPanel panel;
    JButton[] movieButtons = new JButton[6];
    JLabel[] movieLabels = new JLabel[6];
    JButton logoutButton;
    Font myFont1, myFont2;
    Color myColor1, myColor2;
    TicketSellerList seller;
    TicketDetailsList ticketDetailsList;
    MovieFileReader movieFileReader;

    public Home(TicketSellerList seller, MovieFileReader movieFileReader) {
        super("Aiub Cineplex");
        this.setSize(800, 645);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.seller = seller;
        this.movieFileReader = movieFileReader;
        Movie[] movies = movieFileReader.getMovieList(); // Retrieve movies from MovieFileReader
        if (movies == null || movies.length == 0) {
            System.out.println("No movies available.");
            return; // Exit the constructor if no movies are found
        }
        

        myColor1 = new Color(206, 185, 224);
        myColor2 = Color.decode("#a5d8ff");
        myFont1 = new Font("Arial", Font.BOLD, 20);
        myFont2 = new Font("Arial", Font.PLAIN, 16);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(myColor1);

        JLabel welcomeLabel = new JLabel("Select Movie -", SwingConstants.CENTER);
        welcomeLabel.setFont(myFont1);
        welcomeLabel.setBackground(myColor1);
        welcomeLabel.setForeground(Color.BLACK);
        welcomeLabel.setBounds(0, 0, 210, 100);
        panel.add(welcomeLabel);

        /// Log out Button
        logoutButton = new JButton("Logout");
        logoutButton.setBounds(600, 30, 100, 30);
        logoutButton.setBackground(Color.decode("#FF6666")); 
        logoutButton.setForeground(Color.BLACK);
        logoutButton.setFont (new Font("Arial", Font.BOLD, 14));
        logoutButton.setBorderPainted(false);
        logoutButton.addActionListener(this);
        logoutButton.addMouseListener(this);
        logoutButton.setOpaque(true);
        panel.add (logoutButton);

        // Modify the button creation in your for-loop like this
        for (int i = 0; i < movies.length && i < movieButtons.length; i++) {
            Movie movie = movies[i];

            // Use ImageIcon to load the image from the provided path
            ImageIcon movieIcon = new ImageIcon(movie.getImagePath());
            Image img = movieIcon.getImage();
            Image scaledImg = img.getScaledInstance(140, 180, Image.SCALE_SMOOTH);
            movieIcon = new ImageIcon(scaledImg);

            movieButtons[i] = new JButton(movieIcon); // Add the image as the button icon
            movieButtons[i].setBounds(145 + (i % 3) * 180, 80 + (i / 3) * 250, 140, 180);
            movieButtons[i].setBackground(Color.decode("#A5D8FF"));
            movieButtons[i].addActionListener(this);
            movieButtons[i].setActionCommand(movie.getMovieName()); // Set command for action
            // Store the imagePath in the button
            movieButtons[i].putClientProperty("imagePath", movie.getImagePath()); 

            panel.add(movieButtons[i]);

            movieLabels[i] = new JLabel(movie.getMovieName());
            movieLabels[i].setBounds(185 + (i % 3) * 180, 200 + (i / 3) * 250, 140, 180);
            movieLabels[i].setBackground(Color.decode("#CEB9E0"));
            movieLabels[i].setForeground(Color.BLACK);
            movieLabels[i].setFont(new Font("Arial", Font.PLAIN, 16));
            panel.add(movieLabels[i]);
        }


        this.add(panel);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if ("Logout".equals(command)) {
            Login login = new Login (seller, movieFileReader);
            login.setVisible(true);
            this.setVisible(false);
        }
        else {
            for (int i = 0; i < movieButtons.length; i++) {
                if (movieButtons[i] != null && movieButtons[i].getActionCommand().equals(command)) {
                    String imagePath = (String) movieButtons[i].getClientProperty("imagePath");
                    String movieName = movieButtons[i].getActionCommand();

                    ticketDetailsList = new TicketDetailsList();
                    MovieTicketBooking movieTicketBooking = new MovieTicketBooking(seller, imagePath, movieName,
                            ticketDetailsList, movieFileReader);
                    movieTicketBooking.setVisible(true);
                    this.setVisible(false);
                    break;
                }
            }
        }
    }

    public void mouseEntered(MouseEvent me) {
        if (me.getSource() == logoutButton) 
        {
            logoutButton.setBackground(Color.RED);
        }
    }
    public void mouseExited(MouseEvent me) {
        if (me.getSource() == logoutButton) 
        {
            logoutButton.setBackground(Color.decode("#FF6666"));
        }
    }
    public void mouseClicked(MouseEvent me) {}
    public void mousePressed(MouseEvent me) {}
    public void mouseReleased(MouseEvent me) {}
}
