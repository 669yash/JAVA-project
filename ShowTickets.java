import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowTickets {
    public void show() {
        JFrame frame = new JFrame("Show Tickets");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT ticket_id, train_id, passenger_name FROM tickets";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int ticketId = rs.getInt("ticket_id");
                int trainId = rs.getInt("train_id");
                String passengerName = rs.getString("passenger_name");

                JLabel ticketLabel = new JLabel("Ticket ID: " + ticketId + ", Train ID: " + trainId + ", Passenger Name: " + passengerName);
                panel.add(ticketLabel);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
