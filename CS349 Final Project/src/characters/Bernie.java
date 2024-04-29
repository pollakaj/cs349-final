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
import auditory.sampled.BufferedSound;
import auditory.sampled.BufferedSoundFactory;
import io.ResourceFinder;
import visual.dynamic.described.RuleBasedSprite;
import visual.dynamic.described.Sprite;
import visual.dynamic.described.Stage;
import visual.statik.sampled.*;

public class Bernie extends RuleBasedSprite implements KeyListener, ActionListener
{
  private static final int SPEED = 9;
  private static final double GRAVITY = 4.0;
  private static final double INIT_JUMP_SPD = -50.0;
  
  private Content content1;
  private Content leftContent;
  private ResourceFinder finder;
  private ContentFactory factory;
  private BufferedImage bernie = null;
  private BufferedImage leftBernie = null;
  private BufferedImage slice1 = null;
  private BufferedImage slice2 = null;
  private BufferedImage slice1_left = null;
  private BufferedImage slice2_left = null;
  private double prevX;
  private double prevY;
  
  private double jumpSpeed = INIT_JUMP_SPD;
  private boolean isJumping = false;
  public boolean isDead = false;
  private boolean movingLeft = false;
  private boolean facingLeft = false;
  private boolean movingRight = false;
  private boolean isTouchingPlatform = false;
  private int startY = 650;
  private Content content2;
  private Content content3;
  private Content content4;
  private Content content5;
  private boolean slicing;
  private Stage stage;

  public Bernie(Stage stage)
  {
    super(new Content());
    this.stage = stage;
    try
    {
      bernie = ImageIO.read(getClass().getResourceAsStream("/resources/BernHair.png"));
      bernie = resizeImage(bernie, 125, 150);
      
      leftBernie = ImageIO.read(getClass().getResourceAsStream("/resources/Bern(HairFlip).png"));
      leftBernie = resizeImage(leftBernie, 125, 150);

      slice1 = ImageIO.read(getClass().getResourceAsStream("/resources"
          + "/Bern(slice1).png"));
      slice1_left = ImageIO.read(getClass().getResourceAsStream("/resources/bern(slice1_left).png"));
      slice1 = resizeImage(slice1, 125, 150);
      slice1_left = resizeImage(slice1_left, 125, 150);

      slice2 = ImageIO.read(getClass().getResourceAsStream("/resources"
          + "/Bern(slice2).png"));
      slice2_left = ImageIO.read(getClass().getResourceAsStream("/resources/bern(slice2_left).png"));
      
      slice2 = resizeImage(slice2, 125, 150);
      slice2_left = resizeImage(slice2_left, 125, 150);

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
    
    content4 = factory.createContent(slice1_left);
    content5 = factory.createContent(slice2_left);
    leftContent = factory.createContent(leftBernie);
    
    this.content = content1;
    this.setLocation(100, 650);
    this.setVisible(true);
  }

  private BufferedImage resizeImage(BufferedImage originalImage,
      int targetWidth, int targetHeight)
  {
    BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = resizedImage.createGraphics();
    g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
    g2d.dispose();
    return resizedImage;
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
      if (!isSlicing())
      {
        setSlicing(true);
        performSlice();
      }
    }
  }
  
  private void performSlice()
  {
    Timer timer1 = new Timer(0, new ActionListener() 
    {
      public void actionPerformed(ActionEvent e)
      {
        if (facingLeft) content = content4;
        else content = content2;
      }
    });
    timer1.setRepeats(false);
    timer1.start();
  
    Timer timer2 = new Timer(100, new ActionListener() 
    {
      public void actionPerformed(ActionEvent e)
      {
        if (facingLeft) content = content5;
        else content = content3;
      }
    });
    timer2.setRepeats(false);
    timer2.start();
  
    Timer timer3 = new Timer(300, new ActionListener() 
    {
      public void actionPerformed(ActionEvent e)
      {
        if (facingLeft) content = leftContent;
        else content = content1;
        for (Sprite zombie : antagonists)
        {
          if (zombie instanceof Zombie)
          {
            ((Zombie) zombie).checkSliced();
          }
        }
        setSlicing(false);
      }
    });
    timer3.setRepeats(false);
    timer3.start();
  }
  
  private void setSlicing(boolean slicing)
  {
    this.slicing = slicing;
  }
  
  public boolean isSlicing()
  {
    return slicing;
  }

  @Override
  public void keyReleased(KeyEvent e)
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

          if (this.y >= startY)
          {
            this.y = startY; 
            jumpSpeed = INIT_JUMP_SPD;
          }
        }
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
}