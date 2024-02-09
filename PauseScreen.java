
import java.awt.*;
import javax.swing.*;

public class PauseScreen extends JPanel {
    public PauseScreen() {
        super.setLayout(new BorderLayout());
        super.setPreferredSize(new Dimension(GameBoardPanel.BOARD_WIDTH, GameBoardPanel.BOARD_HEIGHT));
        JLabel PauseLabel = new JLabel("Game is Paused");
        PauseLabel.setFont(new Font("Helvetica", Font.BOLD, 58));
        PauseLabel.setForeground(new Color(255, 147, 97));
        PauseLabel.setHorizontalAlignment(SwingConstants.CENTER);
        PauseLabel.setVerticalAlignment(SwingConstants.CENTER);

        JPanel PausePanel = new JPanel(new BorderLayout());
        PausePanel.add(PauseLabel, BorderLayout.CENTER);
        PausePanel.setBackground(new Color(255, 210, 156));
        super.add(PausePanel, BorderLayout.CENTER);
    }
}
