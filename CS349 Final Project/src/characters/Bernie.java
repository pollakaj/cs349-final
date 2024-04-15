package characters;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;

import visual.statik.SimpleContent;

public class Bernie implements SimpleContent
{
  private BufferedImage image;
  private int x;
  private int y;
  
  public Bernie(BufferedImage image, int x, int y)
  {
    this.image = image;
    this.x = x;
    this.y = y;
  }

  
  @Override
  public void render(Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    double imageY = y - image.getHeight() / 2;
    double imageX = x - image.getWidth() / 2;
    g2.drawImage(image, (int) imageX, (int) imageY, null);
  }
  
  public void setX(int updateX)
  {
    x = updateX;
  }
  
  public void setY(int updateY)
  {
    y = updateY;
  }
  
  public int getX()
  {
    return x;
  }
  
  public int getY()
  {
    return y;
  }

}
