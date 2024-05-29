import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CancelTicket {
    public void show() {
        JFrame frame = new JFrame("Cancel Ticket");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel ticketLabel = new JLabel("Ticket ID");
        ticketLabel.setBounds(10, 20, 80, 25);
        panel.add(ticketLabel);

        JTextField ticketText = new JTextField(20);
        ticketText.setBounds(150, 20, 165, 25);
        panel.add(ticketText);

        JButton cancelButton = new JButton("Cancel Ticket");
        cancelButton.setBounds(10, 50, 150, 25);
        panel.add(cancelButton);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ticketId = Integer.parseInt(ticketText.getText());

                try (Connection conn = DatabaseConnection.getConnection()) {
                    String query = "SELECT train_id FROM tickets WHERE ticket_id = ?";
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, ticketId);
                    ResultSet rs = pstmt.executeQuery();

                    if (rs.next()) {
                        int trainId = rs.getInt("train_id");

                        query = "DELETE FROM tickets WHERE ticket_id = ?";
                        pstmt = conn.prepareStatement(query);
                        pstmt.setInt(1, ticketId);
                        pstmt.executeUpdate();

                        query = "UPDATE trains SET available_seats = available_seats + 1 WHERE train_id = ?";
                        pstmt = conn.prepareStatement(query);
                        pstmt.setInt(1, trainId);
                        pstmt.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Ticket canceled successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Ticket ID not found!");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
