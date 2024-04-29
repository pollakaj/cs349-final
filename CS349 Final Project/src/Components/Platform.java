package Components;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import characters.Bernie;
import io.ResourceFinder;
import resources.Marker;
import visual.dynamic.described.RuleBasedSprite;
import visual.statik.sampled.Content;
import visual.statik.sampled.ContentFactory;


/**
 * Platform class to encapsulate any methods the Platforms might need
 * @author Cole Glaccum
 *
 */
public class Platform extends RuleBasedSprite
{
  
  private ResourceFinder finder;
  private ContentFactory factory;
  private BufferedImage platform;
  private Content content1;
  private Bernie b;
  private int x;
  private int y;

  public Platform(int x, int y, Bernie b)
  {
    super(new Content());
    
    this.b = b;
    this.x = x;
    this.y = y;
    
    try
    {
      platform = ImageIO.read(getClass().getResourceAsStream("/resources/larger_platform.png"));
      platform = resizeImage(platform, 150, 15);
    }
     catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    
    finder = ResourceFinder.createInstance(new Marker());
    factory = new ContentFactory(finder);
    
    content1 = factory.createContent(platform);
    this.content = content1;
    this.setLocation(x, y);
    this.setVisible(true);
  }

  @Override
  public void handleTick(int arg0)
  {
    Rectangle2D bernieBounds = b.getBounds2D();
    Rectangle2D platformBounds = new Rectangle2D.Double(getX(), getY(), 
        content1.getBounds2D().getWidth(), content1.getBounds2D().getHeight());

    if (bernieBounds.intersects(platformBounds)) 
    {
      double bernTop = bernieBounds.getY();
      double platformTop = platformBounds.getY();

      if (bernieBounds.getMaxY() >= platformTop && bernTop <= platformTop) 
      {
        double newY = platformTop - bernieBounds.getHeight();
        b.setY(newY);
        b.setJumping(false);
        b.setTouchingPlatform(true);
      }
    } else 
    {
      b.setTouchingPlatform(false);
    }
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
  
  public int getX() {
    return x;
  }
  
  public int getY() {
    return y;
  }
  
}
