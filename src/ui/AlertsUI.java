package ui;
import javax.swing.*;
import java.awt.*;

public class AlertsUI extends JFrame {
    private JTextArea alertsArea;

    public AlertsUI() {
        setTitle("Alerts");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Başlık
        JLabel headerLabel = new JLabel("Alerts", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(headerLabel, BorderLayout.NORTH);

        // Uyarı Alanı
        alertsArea = new JTextArea();
        alertsArea.setEditable(false);
        alertsArea.setText("Low stock alerts will be displayed here...\n\nExample:\nProduct A: Only 5 units left!\nProduct B: Out of stock!");
        add(new JScrollPane(alertsArea), BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
