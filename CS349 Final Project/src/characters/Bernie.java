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
  private static final int SPEED = 9;
  private static final double GRAVITY = 4.0;
  private static final double INIT_JUMP_SPD = -40.0;
  
  private double jumpSpeed = INIT_JUMP_SPD;
  private boolean isJumping = false;
  private boolean movingLeft = false;
  private boolean movingRight = false;
  private int startY = 650;
  private SampledSprite slicingBern;
  private Content content2;
  private Content content3;
  private Point2D local;

  public Bernie()
  {
    super(new Content());
    BufferedImage bernie = null;
    BufferedImage slice1 = null;
    BufferedImage slice2 = null;
    try
    {
      bernie = ImageIO.read(new File("src/resources/Bern.png"));
      bernie = resizeImage(bernie, 125, 150);
      
      slice1 = ImageIO.read(new File("src/resources/Bern(slice1).png"));
      slice1 = resizeImage(bernie, 125, 150);
      
      slice2 = ImageIO.read(new File("src/resources/Bern(slice2).png"));
      slice2 = resizeImage(bernie, 125, 150);
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
    content2 = factory.createContent(slice1);
    content3 = factory.createContent(slice2);
    
    local = new Point2D.Double(this.x, this.y);
    slicingBern = new SampledSprite();
    slicingBern.addKeyTime(0, local, null, null, content);
    
    this.content = content;
    slicingBern.setVisible(true);
    this.addAntagonist(slicingBern);
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
      movingLeft = true;
    }
    if (code == KeyEvent.VK_RIGHT)
    {
      movingRight = true;
    }  
    if (code == KeyEvent.VK_SPACE && !isJumping && this.y == startY)
    {
      isJumping = true;
    }
    if (code == KeyEvent.VK_X)
    {
      slicingBern.addKeyTime(200, local, null, null, content2);
      slicingBern.addKeyTime(400, local, null, null, content3);
    }
  }

  @Override
  public void keyReleased(KeyEvent e)
  {
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_LEFT) movingLeft = false;
    if (code == KeyEvent.VK_RIGHT) movingRight = false;
  }

  @Override
  public void handleTick(int arg0)
  {
    if (movingLeft) this.x -= SPEED;
    if (movingRight) this.x += SPEED;

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