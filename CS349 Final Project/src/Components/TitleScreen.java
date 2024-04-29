package Components;

import javax.swing.*;
import java.awt.*;

public class TitleScreen extends JDialog 
{

  private static final long serialVersionUID = 1L;
  private JButton startButton;

  public TitleScreen(JFrame parent) {
        super(parent, "Bizarre Adventures - Title Screen", true);
        initialize();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(parent);
  }

  private void initialize() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Welcome to Bizarre Adventures!");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titleLabel, BorderLayout.CENTER);

        startButton = new JButton("Start");
        panel.add(startButton, BorderLayout.SOUTH);

        add(panel);
  }

  public JButton getStartButton() {
        return startButton;
  }
}