package Components;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
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
      platform = ImageIO.read(getClass().getResourceAsStream("/resources/large_platform.png"));
      platform = resizeImage(platform, 100, 100);
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
    if (content1.getBounds2D().intersects(b.getBounds2D())) {
      // Player is touching the platform
      // Implement what happens when they touch the platform
      b.setTouchingPlatform(true);
      b.setLocation(b.getX(), getY() - 150);
      b.setJumping(false);
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
