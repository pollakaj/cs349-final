package characters;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import io.ResourceFinder;
import visual.dynamic.described.RuleBasedSprite;
import visual.statik.sampled.*;

public class Bernie extends RuleBasedSprite implements KeyListener, ActionListener
{
  private static final int SPEED = 8;
  private static final double GRAVITY = 1.5;
  private static final double INIT_JUMP_SPD = -25.0;
  
  private double jumpSpeed = INIT_JUMP_SPD;
  private boolean isJumping = false;
  private int startY = 650;
  private int startX = 100;

  public Bernie()
  {
    super(new Content());
    BufferedImage bernie = null;
    try
    {
      bernie = ImageIO.read(new File("src/resources/Bern.png"));
      bernie = resizeImage(bernie, 125, 150);
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    Content content;
    ResourceFinder finder;
    ContentFactory factory;
    finder = ResourceFinder.createInstance(new resources.Marker());
    factory = new ContentFactory(finder);
    content = factory.createContent(bernie);
    this.content = content;
    this.setLocation(100, 650);
    this.setVisible(true);
  }

  private BufferedImage resizeImage(BufferedImage originalImage,
      int targetWidth, int targetHeight)
  {
    Image resultingImage = originalImage.getScaledInstance(targetWidth,
        targetHeight, Image.SCALE_DEFAULT);
    BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight,
        BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics2D = outputImage.createGraphics();
    graphics2D.drawImage(resultingImage, 0, 0, null);
    graphics2D.dispose();
    return outputImage;
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
  }


  @Override
  public void keyTyped(KeyEvent e)
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void keyPressed(KeyEvent e)
  {
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_LEFT)
    {
      this.x -= SPEED;
    }
    if (code == KeyEvent.VK_RIGHT)
    {
      this.x += SPEED;
    }  
    if (code == KeyEvent.VK_SPACE && !isJumping && this.y == startY)
    {
      isJumping = true;
    }
  }

  @Override
  public void keyReleased(KeyEvent e)
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void handleTick(int arg0)
  {
    if (isJumping)
    {
      jumpSpeed += GRAVITY;
      this.y += jumpSpeed;
      
      if (jumpSpeed >= 0 && this.y >= startY)
      {
        isJumping = false;
        this.y = startY;
        jumpSpeed = INIT_JUMP_SPD;
      }
    } else
    {
      if (this.y < startY)
      {
        jumpSpeed += GRAVITY;
        this.y += jumpSpeed;
      } else
      {
        this.y = startY;
        jumpSpeed = INIT_JUMP_SPD;
      }
    }
    
  }
}