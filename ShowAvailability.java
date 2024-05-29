import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowAvailability {
    public void show() {
        JFrame frame = new JFrame("Show Availability");
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
            String query = "SELECT train_id, name, total_seats, available_seats FROM trains";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int trainId = rs.getInt("train_id");
                String name = rs.getString("name");
                int totalSeats = rs.getInt("total_seats");
                int availableSeats = rs.getInt("available_seats");

                JLabel trainLabel = new JLabel("Train ID: " + trainId + ", Name: " + name + ", Total Seats: " + totalSeats + ", Available Seats: " + availableSeats);
                panel.add(trainLabel);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
