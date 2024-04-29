package Components;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GameOver extends JPanel
{
  private static final long serialVersionUID = 1L;
  
  private BufferedImage gameOver;
  
  public GameOver(BufferedImage gameOver)
  {
    this.gameOver = gameOver;
  }
  
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.drawImage(gameOver, 0, 0, this.getWidth(), this.getHeight(), this);
  }

}
