package characters;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Components.Platform;
import auditory.sampled.BufferedSound;
import auditory.sampled.BufferedSoundFactory;
import io.ResourceFinder;
import visual.dynamic.described.RuleBasedSprite;
import visual.dynamic.described.SampledSprite;
import visual.dynamic.described.Sprite;
import visual.dynamic.described.TweeningSprite;
import visual.dynamic.sampled.RectangleWipe;
import visual.statik.sampled.*;

public class Bernie extends RuleBasedSprite implements KeyListener, ActionListener
{
  private static final int SPEED = 9;
  private static final double GRAVITY = 4.0;
  private static final double INIT_JUMP_SPD = -45.0;
  
  private Content content1;
  private Content leftContent;
  private ResourceFinder finder;
  private ContentFactory factory;
  private BufferedImage bernie = null;
  private BufferedImage leftBernie = null;
  private BufferedImage slice1 = null;
  private BufferedImage slice2 = null;
  private double prevX;
  private double prevY;
  
  private double jumpSpeed = INIT_JUMP_SPD;
  private boolean isJumping = false;
  private boolean movingLeft = false;
  private boolean movingRight = false;
  private boolean isTouchingPlatform = false;
  private int startY = 650;
  private SampledSprite slicingBern;
  private Content content2;
  private Content content3;

  public Bernie()
  {
    super(new Content());
    try
    {
      bernie = ImageIO.read(getClass().getResourceAsStream("/resources/Bern.png"));
      bernie = resizeImage(bernie, 125, 150);
      
      leftBernie = ImageIO.read(getClass().getResourceAsStream("/resources/bern_left.png"));
      leftBernie = resizeImage(leftBernie, 125, 150);

      slice1 = ImageIO.read(getClass().getResourceAsStream("/resources/Bern(slice1).png"));
      slice1 = resizeImage(slice1, 125, 150);
      
      slice2 = ImageIO.read(getClass().getResourceAsStream("/resources/Bern(slice2).png"));
      slice2 = resizeImage(slice2, 125, 150);
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    finder = ResourceFinder.createInstance(new resources.Marker());
    factory = new ContentFactory(finder);
    content1 = factory.createContent(bernie);
    content2 = factory.createContent(slice1);
    content3 = factory.createContent(slice2);
    leftContent = factory.createContent(leftBernie);
    
    slicingBern = new SampledSprite();

    this.content = content1;
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
      this.content = leftContent;
    }
    if (code == KeyEvent.VK_RIGHT)
    {
      movingRight = true;
      this.content = content1;
    }  
    if (code == KeyEvent.VK_SPACE && !isJumping && this.y == startY)
    {
      BufferedSoundFactory buffFactory = new BufferedSoundFactory(finder);
      
      try
      {
        BufferedSound music = buffFactory.createBufferedSound("boing.wav");
        Clip clip = javax.sound.sampled.AudioSystem.getClip();
        music.render(clip);
        
      }
      catch (IOException exc)
      {
        // TODO Auto-generated catch block
        exc.printStackTrace();
      }
      catch (UnsupportedAudioFileException exc)
      {
        // TODO Auto-generated catch block
        exc.printStackTrace();
      }
      catch (LineUnavailableException exc)
      {
        // TODO Auto-generated catch block
        exc.printStackTrace();
      }
      isJumping = true;
    }
    if (code == KeyEvent.VK_X)
    {
      slicingBern.setLocation(this.x, this.y);
      slicingBern.addKeyTime(0, new Point2D.Double(this.x, this.y), null, null,
          content1);
      slicingBern.addKeyTime(200, new Point2D.Double(this.x, this.y), null,
          null, content2);
      slicingBern.addKeyTime(400, new Point2D.Double(this.x, this.y), null,
          null, content3);
      slicingBern.setEndState(TweeningSprite.REMOVE);
      slicingBern.setVisible(true);
      this.content = slicingBern;
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
    prevX = getX();
    prevY = getY();

    if (movingLeft) this.x -= SPEED;
    if (movingRight) this.x += SPEED;
    
    if (this.x <= 0) {
    	x = 1919;
    }
    
    if (this.x >= 1920) {
    	x = 1;
    }

    if (!isTouchingPlatform) 
    {
      if (isJumping) 
      {
        jumpSpeed += GRAVITY;
        this.y += jumpSpeed;

        if (this.y >= startY) 
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

          if (this.y >= startY)
          {
            this.y = startY;
            jumpSpeed = INIT_JUMP_SPD;
          }
        }
      }
    } else
    {
      boolean onPlatform = false;
      for (Sprite platform : antagonists)
      {
        if (platform instanceof Platform)
        {
          Platform p = (Platform) platform;
          if (p.intersects(this))
          {
            onPlatform = true;

            if (getY() + this.getBounds2D().getHeight() <= p.getY())
            {
              double newY = p.getY() - this.getBounds2D().getHeight();
              setY(newY);
              setJumping(false);
              isTouchingPlatform = true;
            } else
            {
              double newY = p.getY()
                  + p.getContent().getBounds2D(false).getHeight();
              setY(newY);
              jumpSpeed = 0;
            }
          }
        }
      }
      if (!onPlatform)
      {
        isTouchingPlatform = false;
      }
    }
  }

  public double getX()
  {
    return this.x;
  }
  
  public double getY()
  {
    return this.y;
  }
  
  public void setY(double yLocal)
  {
    this.y = yLocal;
  }
  
  public void setX(double xLocal)
  {
    this.x = xLocal;
  }
  
  public double getPrevX()
  {
    return prevX;
  }
  
  public double getPrevY()
  {
    return prevY;
  }
  
  public boolean isJumping() {
    return this.isJumping;
  }

  public void setJumping(boolean jumping) {
    isJumping = jumping;
  }
  
  public void setTouchingPlatform(boolean touching) {
    isTouchingPlatform = touching;
  }

  public void die() {
    setLocation(100, 650);
  }
}