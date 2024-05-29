import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookTicket {
    public void show() {
        JFrame frame = new JFrame("Book Ticket");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Passenger Name");
        userLabel.setBounds(10, 20, 150, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(150, 20, 165, 25);
        panel.add(userText);

        JLabel trainLabel = new JLabel("Train ID");
        trainLabel.setBounds(10, 50, 80, 25);
        panel.add(trainLabel);

        JTextField trainText = new JTextField(20);
        trainText.setBounds(150, 50, 165, 25);
        panel.add(trainText);

        JButton bookButton = new JButton("Book Ticket");
        bookButton.setBounds(10, 80, 150, 25);
        panel.add(bookButton);
        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String passengerName = userText.getText();
                int trainId = Integer.parseInt(trainText.getText());

                try (Connection conn = DatabaseConnection.getConnection()) {
                    String query = "SELECT available_seats FROM trains WHERE train_id = ?";
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, trainId);
                    ResultSet rs = pstmt.executeQuery();

                    if (rs.next()) {
                        int availableSeats = rs.getInt("available_seats");
                        if (availableSeats > 0) {
                            query = "INSERT INTO tickets (train_id, passenger_name) VALUES (?, ?)";
                            pstmt = conn.prepareStatement(query);
                            pstmt.setInt(1, trainId);
                            pstmt.setString(2, passengerName);
                            pstmt.executeUpdate();

                            query = "UPDATE trains SET available_seats = available_seats - 1 WHERE train_id = ?";
                            pstmt = conn.prepareStatement(query);
                            pstmt.setInt(1, trainId);
                            pstmt.executeUpdate();

                            JOptionPane.showMessageDialog(null, "Ticket booked successfully!");
                        } else {
                            JOptionPane.showMessageDialog(null, "No available seats!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Train ID not found!");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
