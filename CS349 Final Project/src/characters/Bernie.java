package characters;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import io.ResourceFinder;
import visual.dynamic.described.RuleBasedSprite;
import visual.dynamic.described.SampledSprite;
import visual.statik.sampled.*;

public class Bernie extends RuleBasedSprite implements KeyListener, ActionListener
{
  private static final int SPEED = 5;
  private static final int JUMP_HEIGHT = 100;
  private boolean isJumping = false;
  private int jumpCounter = 0;
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
    } else if (code == KeyEvent.VK_RIGHT)
    {
      this.x += SPEED;
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
    // TODO Auto-generated method stub
    
  }
}