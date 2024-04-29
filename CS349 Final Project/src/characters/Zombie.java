package characters;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import io.ResourceFinder;
import visual.dynamic.described.RuleBasedSprite;
import visual.statik.sampled.Content;
import visual.statik.sampled.ContentFactory;

public class Zombie extends RuleBasedSprite {
	
	private static final int SPEED = 5;
	private int maxX = 1920;
	private Content content1;
	private Content content2;
	private ResourceFinder finder;
	private ContentFactory factory;
	private BufferedImage zombie = null;
	private BufferedImage leftZombie = null;
	private Bernie b;
	
	public Zombie(Bernie b) {
		super(new Content());
		this.b = b;
		try
	    {
			zombie = ImageIO.read(getClass().getResourceAsStream("/resources/zombie_right.png"));
			zombie = resizeImage(zombie, 125, 150);
			
			leftZombie = ImageIO.read(getClass().getResourceAsStream("/resources/zombie_left.png"));
			leftZombie = resizeImage(leftZombie, 125, 150);
	    }
		
		catch (IOException e)
		{
		      // TODO Auto-generated catch block
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
	public void handleTick(int arg0) {
		if (content1.getBounds2D().intersects(b.getBounds2D())) {
			b.die();
		}
		updateLocation();
	}
	
	public void updateLocation() {
		double diff = b.getX() - this.x;
		if (diff < 0) {
			this.x -= SPEED;
			this.content = content2;
		} else {
			this.x += SPEED;
		}
		
		if (this.x >= maxX) {
			this.x = 0;
		}
	}
	
}
