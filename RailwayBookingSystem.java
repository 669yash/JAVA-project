import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RailwayBookingSystem {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Railway Ticket Booking System");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JButton bookButton = new JButton("Book Ticket");
        bookButton.setBounds(50, 50, 150, 25);
        panel.add(bookButton);
        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BookTicket bookTicket = new BookTicket();
                bookTicket.show();
            }
        });

        JButton cancelButton = new JButton("Cancel Ticket");
        cancelButton.setBounds(50, 100, 150, 25);
        panel.add(cancelButton);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CancelTicket cancelTicket = new CancelTicket();
                cancelTicket.show();
            }
        });

        JButton showButton = new JButton("Show Tickets");
        showButton.setBounds(50, 150, 150, 25);
        panel.add(showButton);
        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShowTickets showTickets = new ShowTickets();
                showTickets.show();
            }
        });

        JButton availabilityButton = new JButton("Show Availability");
        availabilityButton.setBounds(50, 200, 150, 25);
        panel.add(availabilityButton);
        availabilityButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShowAvailability showAvailability = new ShowAvailability();
                showAvailability.show();
            }
        });
    }
}
