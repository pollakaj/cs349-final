package characters;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;

import Components.GameOver;
import Components.Victory;
import auditory.sampled.BufferedSound;
import auditory.sampled.BufferedSoundFactory;
import io.ResourceFinder;
import visual.dynamic.described.RuleBasedSprite;
import visual.dynamic.described.Sprite;
import visual.dynamic.described.Stage;
import visual.statik.sampled.*;

/**
 * Bernstein main character class for design and functionality.
 *
 * @author Adam Pollak and Cole Glaccum
 * @version 1.0
 * 
 * This code complies with the JMU Honor Code.
 */

public class Bernie extends RuleBasedSprite implements KeyListener, ActionListener
{
  private static final int SPEED = 11;
  private static final double GRAVITY = 4.0;
  private static final double INIT_JUMP_SPD = -50.0;
  private static final int MAX_HEALTH = 10;
  private static final String RESOURCE_PATH = "/resources";

  private int health = MAX_HEALTH;
  private Content content1;
  private Content leftContent;
  private ResourceFinder finder;
  private ContentFactory factory;
  private BufferedImage bernie = null;
  private BufferedImage leftBernie = null;
  private BufferedImage slice1 = null;
  private BufferedImage slice2 = null;
  private BufferedImage slice1Left = null;
  private BufferedImage slice2Left = null;
  private double prevX;
  private double prevY;
  
  private boolean isDead = false;
  private boolean didWin = false;
  private double jumpSpeed = INIT_JUMP_SPD;
  private boolean isJumping = false;
  private boolean movingLeft = false;
  private boolean facingLeft = false;
  private boolean movingRight = false;
  private boolean isTouchingPlatform = false;
  private int killCounter = 0;
  private int startY = 650;
  private Content content2;
  private Content content3;
  private Content content4;
  private Content content5;
  private boolean slicing;
  private boolean currSlicing;
  private Stage stage;

  /**
   * Bernie constructor for multiple image construction and initializations.
   *
   * @param stage Stage object linked with the character and application
   */

  public Bernie(final Stage stage)
  {
    super(new Content());
    this.stage = stage;
    try
    {
      bernie = ImageIO.read(getClass().getResourceAsStream(RESOURCE_PATH
          + "/Bern.png"));
      bernie = resizeImage(bernie, 125, 150);
      
      leftBernie = ImageIO.read(getClass().getResourceAsStream(RESOURCE_PATH
          + "/bern_left.png"));
      leftBernie = resizeImage(leftBernie, 125, 150);

      slice1 = ImageIO.read(getClass().getResourceAsStream(RESOURCE_PATH
          + "/Bern(slice1).png"));
      slice1Left = ImageIO.read(getClass().getResourceAsStream(RESOURCE_PATH
          + "/bern(slice1_left).png"));
      slice1 = resizeImage(slice1, 125, 150);
      slice1Left = resizeImage(slice1Left, 125, 150);

      slice2 = ImageIO.read(getClass().getResourceAsStream(RESOURCE_PATH
          + "/Bern(slice2).png"));
      slice2Left = ImageIO.read(getClass().getResourceAsStream(RESOURCE_PATH
          + "/bern(slice2_left).png"));
      
      slice2 = resizeImage(slice2, 125, 150);
      slice2Left = resizeImage(slice2Left, 125, 150);

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
    
    content4 = factory.createContent(slice1Left);
    content5 = factory.createContent(slice2Left);
    leftContent = factory.createContent(leftBernie);
    
    this.content = content1;
    this.setLocation(100, 650);
    this.setVisible(true);
  }

  /**
   * Resize image method to adjust the size of different orientations.
   *
   * @param originalImage BufferedImage of image to be resized
   * @param targetWidth int value of desired width
   * @param targetHeight int value of desired height
   * @return BufferedImage resized image
   */
  private BufferedImage resizeImage(final BufferedImage originalImage,
      final int targetWidth, final int targetHeight)
  {
    BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight,
        BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = resizedImage.createGraphics();
    g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
    g2d.dispose();
    return resizedImage;
  }

  @Override
  public void actionPerformed(final ActionEvent e)
  {
  }


  @Override
  public void keyTyped(final KeyEvent e)
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void keyPressed(final KeyEvent e)
  {
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_LEFT)
    {
      if (!movingLeft)
      {
        movingLeft = true;
        this.content = leftContent;
        facingLeft = true;
      }
    }
    if (code == KeyEvent.VK_RIGHT)
    {
      movingRight = true;
      this.content = content1;
    }  
    if (code == KeyEvent.VK_SPACE && !isJumping)
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
      if (!isSlicing() && !currSlicing)
      {
        setSlicing(true);
        currSlicing = true;
        performSlice();
      }
    }
  }
  
  /**
   * Perform slice method for animating the slicing motion of Bernie.
   */
  private void performSlice()
  {
    Content slice1Img = facingLeft ? content4 : content2;
    Content slice2Img = facingLeft ? content5 : content3;
    Timer timer1 = new Timer(0, new ActionListener() 
    {
      public void actionPerformed(final ActionEvent e)
      {
        content = slice1Img;
      }
    });
    timer1.setRepeats(false);
    timer1.start();
  
    Timer timer2 = new Timer(100, new ActionListener() 
    {
      public void actionPerformed(final ActionEvent e)
      {
        content = slice2Img;
      }
    });
    timer2.setRepeats(false);
    timer2.start();
  
    Timer timer3 = new Timer(300, new ActionListener() 
    {
      public void actionPerformed(final ActionEvent e)
      {
        content = facingLeft ? leftContent : content1;
        for (Sprite zombie : antagonists)
        {
          if (zombie instanceof Zombie && ((Zombie) zombie).getBounds2D().intersects(getBounds2D()))
          {
            ((Zombie) zombie).checkSliced();
            if (((Zombie) zombie).die()) killCounter++;
          }
        }
        setSlicing(false);
        currSlicing = false;
      }
    });
    timer3.setRepeats(false);
    timer3.start();
    
    if (killCounter == 5 && !didWin) victory();
  }
  
  /**
   * Setter for the slicing boolean.
   *
   * @param slicing boolean value if the character is in the slicing motion
   */
  private void setSlicing(final boolean slicing)
  {
    this.slicing = slicing;
  }
  
  /**
   * Getter for the slicing boolean to check if character is in slicing motion.
   *
   * @return boolean value if the character is slicing
   */
  public boolean isSlicing()
  {
    return slicing;
  }

  @Override
  public void keyReleased(final KeyEvent e)
  {
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_LEFT)
    {
      movingLeft = false;
      facingLeft = false;
    }
    if (code == KeyEvent.VK_RIGHT) movingRight = false;
  }

  @Override
  public void handleTick(final int arg0)
  {
    prevX = getX();
    prevY = getY();

    if (movingLeft) 
    {
      facingLeft = true;
      this.x -= SPEED;
    }
    if (movingRight) 
    {
      facingLeft = false;
      this.x += SPEED;
    }
    
    if (this.x <= 0) x = 1919;
    
    if (this.x >= 1920) x = 1;

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

          if (this.y >= startY)
          {
            this.y = startY; 
            jumpSpeed = INIT_JUMP_SPD;
          }
        }
      }
    }
  }

  /**
   * Getter for the character's x value.
   *
   * @return double x value of Bernie
   */
  public double getX()
  {
    return this.x;
  }
  
  /**
   * Getter for the character's y value.
   *
   * @return double y value of Bernie
   */
  public double getY()
  {
    return this.y;
  }
  
  /**
   * Setter for the character's y value.
   *
   * @param yLocal Y value desired for Bernie to be updated to
   */
  public void setY(final double yLocal)
  {
    this.y = yLocal;
  }
  
  /**
   * Setter for the character's x value.
   *
   * @param xLocal X value desired for Bernie to be updated to
   */
  public void setX(final double xLocal)
  {
    this.x = xLocal;
  }
  
  /**
   * Getter for Bernie's previous x value.
   *
   * @return Double Bernie's previous x coordinate value
   */
  public double getPrevX()
  {
    return prevX;
  }
  
  /**
   * Getter for Bernie's previous y value.
   *
   * @return Double Bernie's previous y coordinate value
   */
  public double getPrevY()
  {
    return prevY;
  }
  
  /**
   * Getter for boolean value to check if Bernie is in the jumping motion.
   *
   * @return Boolean value if Bernie is jumping
   */
  public boolean isJumping() 
  {
    return this.isJumping;
  }

  /**
   * Setter for the jumping boolean to declare if Bernie is jumping.
   *
   * @param jumping boolean value to set jumping boolean to
   */
  public void setJumping(final boolean jumping) 
  {
    isJumping = jumping;
  }
  
  /**
   * Setter for the isTouchingPlatform boolean to check if Bernie is on a
   *  platform.
   *
   * @param touching boolean value to set isTouchingPlatform boolean to
   */
  public void setTouchingPlatform(final boolean touching) 
  {
    isTouchingPlatform = touching;
  }
  
  /**
   * Getter for the isDead boolean.
   *
   * @return boolean to check if Bernie is dead
   */
  public boolean checkDead()
  {
    return this.isDead;
  }
  
  /**
   * Getter for if the player won.
   *
   * @return boolean to check if player won
   */
  public boolean didWin() 
  {
    return this.didWin;
  }
  
  /**
   * Getter for Bernie's health.
   *
   * @return int to check Bernie's Health amount
   */
  public int getHealth() 
  {
    return this.health;
  }
  
  /**
   * Method to decrease the health.
   *
   * @param damage int amount of damage received from a zombie
   */
  public void takeDamage(final int damage)
  {
    health -= damage;
    if (health <= 0) isDead();
  }
  
  /**
   * If Bernie "dies" play the death sound and call the GameOver application,
   *  stops the current stage.
   */
  public void isDead() 
  {
    BufferedSoundFactory buffFactory = new BufferedSoundFactory(finder);
    
    try
    {
      BufferedSound music = buffFactory.createBufferedSound("womp womp.wav");
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
    
    isDead = true;
    stage.stop();
    new GameOver().run();
  }
  
  /**
   * Victory method called if criteria for beating the game is met, stops the
   *  stage and runs the Victory application.
   */
  public void victory()
  {
    didWin = true;
    stage.stop();
    new Victory().run();
  }
}
