package Components;

import java.awt.Graphics2D;
import java.awt.Image;
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
 * Spike class to encapsulate any methods the Spike objects might need.
 *
 * @author Cole Glaccum and Adam Pollak
 * @version 1.0
 *
 * This code complies with the JMU Honor Code.
 */
public class Spike extends RuleBasedSprite
{
  
  private ResourceFinder finder;
  private ContentFactory factory;
  private BufferedImage platform;
  private Content content1;
  private Bernie b;
  private int x;
  private int y;

  /**
   * Spike constructor to create spikes at a specific position in the
   *  application.
   *
   * @param x int x coordinate for the spike
   * @param y int y coordinate for the spike
   * @param b Bernie character from application that will interact
   */
  public Spike(final int x, final int y, final Bernie b)
  {
    super(new Content());
    
    this.b = b;
    this.x = x;
    this.y = y;
    
    try
    {
      platform = ImageIO.read(getClass().getResourceAsStream("/resources"
          + "/spike.png"));
      platform = resizeImage(platform, 100, 100);
    } catch (IOException e)
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
  public void handleTick(final int arg0)
  {
    if (content1.getBounds2D().intersects(b.getBounds2D())) b.isDead();
  }
  
  /**
   * Resize image method to adjust the size of spike objects.
   *
   * @param originalImage BufferedImage of image to be resized
   * @param targetWidth int value of desired width
   * @param targetHeight int value of desired height
   * @return BufferedImage resized image
   */
  private BufferedImage resizeImage(final BufferedImage originalImage,
      final int targetWidth, final int targetHeight)
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
  
  /**
   * Getter for spike's x coordinate value.
   * 
   * @return int value of spike's x coordinate
   */
  public int getX() 
  {
    return x;
  }
  
  /**
   * Getter for spike's y coordinate value.
   * 
   * @return int value of spike's y coordinate
   */
  public int getY()
  {
    return y;
  }

}
