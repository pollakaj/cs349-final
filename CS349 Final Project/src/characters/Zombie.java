package characters;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import io.ResourceFinder;
import visual.dynamic.described.RuleBasedSprite;
import visual.dynamic.described.Stage;
import visual.statik.sampled.Content;
import visual.statik.sampled.ContentFactory;

/**
 * Zombie character class for design and functionality of enemies.
 *
 * @author Adam Pollak and Cole Glaccum
 * @version 1.0
 * 
 * This code complies with the JMU Honor Code.
 */
public class Zombie extends RuleBasedSprite 
{
	
  private static final String RESOURCE_PATH = "/resources";
  private static final int DAMAGE = 1;
  private static final int DAMAGE_COOLDOWN = 1000;
  private static final int SPEED = 5;
  private long lastDamageTime = 0;
	private int maxX = 1920;
	private Content content1;
	private Content content2;
	private ResourceFinder finder;
	private ContentFactory factory;
	private BufferedImage zombie = null;
	private BufferedImage leftZombie = null;
	private Bernie b;
	private Stage stage;	
	/**
	 * Zombie constructor being passed the stage and Bernie in application.
	 *
	 * @param b Bernie character for current game
	 * @param stage Stage for current application
	 */
	public Zombie(final Bernie b, final Stage stage) 
	{
		super(new Content());
		this.b = b;
		this.stage = stage;
		try
	  {
			zombie = ImageIO.read(getClass().getResourceAsStream(RESOURCE_PATH
			    + "/zombie_right.png"));
			zombie = resizeImage(zombie, 125, 150);
			
			leftZombie = ImageIO.read(getClass().getResourceAsStream(RESOURCE_PATH
			    + "/zombie_left.png"));
			leftZombie = resizeImage(leftZombie, 125, 150);
	  }
		
		catch (IOException e)
		{
		  e.printStackTrace();
		}
		
		finder = ResourceFinder.createInstance(new resources.Marker());
	  factory = new ContentFactory(finder);
	  content1 = factory.createContent(zombie);
	  content2 = factory.createContent(leftZombie);
	  this.content = content1;
	  this.setLocation(1400, 650);
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
	public void handleTick(final int arg0) 
	{
		if (this.getBounds2D().intersects(b.getBounds2D())) 
		{
		  long currTime = System.currentTimeMillis();
		  if (currTime - lastDamageTime >= DAMAGE_COOLDOWN)
		  {
		    b.takeDamage(DAMAGE);
		    lastDamageTime = currTime;
		  }
		}
		updateLocation();
	}
	
	/**
	 * UpdateLocation method to set movement and active tracking.
	 */
	public void updateLocation() 
	{
		double diff = b.getX() - this.x;
		if (diff < 0) 
		{
			this.x -= SPEED;
			this.content = content2;
		} else this.x += SPEED;
		
		if (this.x >= maxX) this.x = 0;
	}
	
	/**
	 * Method to check if the zombie has been sliced, if so, zombie is killed.
	 */
	public void checkSliced()
	{
	  if (b.isSlicing() && this.getBounds2D().intersects(b.getBounds2D())) die();
	}
	
	/**
	 * Method to kill the zombie and spawn a new one, returns true if zombie died.
	 *
	 * @return boolean to let Bernie know the zombie is dead
	 */
	public boolean die()
	{
	  stage.remove(this);
	  spawnZombie();
	  return true;
	}
	
	/**
	 * SpawnZombie method to randomly spawn in zombies throughout the game.
	 */
	private void spawnZombie()
	{
	  Zombie newZombie = new Zombie(b, stage);
	  newZombie.setLocation(1930, 650);
	  stage.add(newZombie);
	  
	  Timer timer = new Timer(100, new ActionListener() 
	  {
	    public void actionPerformed(final ActionEvent e)
	    {
	      b.addAntagonist(newZombie);
	    }
	  });
	  timer.setRepeats(false);
	  timer.start();
	}
	
}
