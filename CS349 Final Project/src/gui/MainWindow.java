package gui;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * MainWindow class to create the main window of the videogame.
 * @author Cole Glaccum
 * @version 1.0
 */


public class MainWindow extends JFrame implements KeyListener, ComponentListener
{
  private static final long serialVersionUID = 1L;
  private static final int HEIGHT = 600;
  private static final int WIDTH = 800;
    
  private BufferedImage backgroundImage;
  private BufferedImage bernie;
  private int bernieX;
  private int bernieY;
  private int bernieVel = 5;
  private int jumpHeight = 50;
  private int jumpSpeed = 8;
  private boolean isJumping = false;
    
  public MainWindow() throws IOException 
  {
    super("Bernstein's Bizarre Adventure");
    setSize(WIDTH, HEIGHT);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    // Image IO
    backgroundImage = ImageIO.read(new File("src/resources/349 Background.png"));
    bernie = ImageIO.read(new File("src/resources/Bern.png"));
    
    bernieX = getWidth() / 8;
    bernieY = getHeight() / 2;
    // Create a JPanel subclass to use as the content pane and draw the background image
    setContentPane(new JPanel() 
    {
      private static final long serialVersionUID = 1L;

      @Override
      protected void paintComponent(Graphics g)
      {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(bernie, bernieX, bernieY, 100, 150, this);
        
        if (isJumping) {
          bernieY -= jumpSpeed;
          jumpHeight -= jumpSpeed;

          if (jumpHeight <= 0) {
              jumpSpeed = -8;
          }

          if (bernieY >= getHeight() - 150) {
              isJumping = false;
              jumpHeight = 50; // Reset jump height
              jumpSpeed = 8; // Reset jump speed
              bernieY = getHeight() - 200;
          }
      }
      }
            
      @Override
      public Dimension getPreferredSize()
      {
        return new Dimension(WIDTH, HEIGHT);
      }
    });
    addKeyListener(this);
    addComponentListener(this);
    setFocusable(true);
  }
  public static void main(String[] args) throws IOException
  {
    MainWindow mainWindow = new MainWindow();
    mainWindow.setVisible(true);
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
    if (code == KeyEvent.VK_LEFT) {
        bernieX -= bernieVel;
    } else if (code == KeyEvent.VK_RIGHT) {
        bernieX += bernieVel;
    } else if (code == KeyEvent.VK_SPACE && !isJumping) {
        isJumping = true;
        jumpHeight = 50; // Reset jump height
        jumpSpeed = 8; // Reset jump speed
    }

    if (bernieX < 0) {
        bernieX = 0;
    } else if (bernieX + bernie.getWidth() > getWidth()) {
        bernieX = getWidth() - bernie.getWidth();
    }
    repaint();
    
  }

  @Override
  public void keyReleased(KeyEvent e)
  {
    
  }
  @Override
  public void componentResized(ComponentEvent e)
  {
    bernieX = getWidth() / 8;
    bernieY = getHeight() / 2 + 50;
    repaint(); 
  }

  @Override
  public void componentMoved(ComponentEvent e)
  {
    // TODO Auto-generated method stub
    
  }
  @Override
  public void componentShown(ComponentEvent e)
  {
    // TODO Auto-generated method stub
    
  }
  @Override
  public void componentHidden(ComponentEvent e)
  {
    // TODO Auto-generated method stub
    
  }
}


/**
public class MainWindow extends JApplication implements KeyListener
{
  private Bernie bernie;
  private int bernieX;
  private int bernieY;
  private int bernieVel = 5;
  private int jumpHeight = 100;
  private boolean isJumping = false;
  private int jumpCount = 0;
  private static final int HEIGHT = 600;
  private static final int WIDTH = 800;
  public MainWindow(String[] args)
  {
    super(args, WIDTH, HEIGHT);
  }

  @Override
  public void init()
  {
    BufferedImageOpFactory opFact;
    ResourceFinder finder;
    Visualization model;
    VisualizationRenderer renderer;
    VisualizationView view;
    
    finder = ResourceFinder.createInstance(new resources.Marker());
    ContentFactory factory = new ContentFactory(finder);
    opFact = BufferedImageOpFactory.createFactory();
    
    Content background = factory.createContent("349 background.png", 3);
    background.setLocation(0, 0);
    background.setBufferedImageOp(opFact.createBlurOp(3));
    
    BufferedImage bernImg = null;
    try
    {
      bernImg = ImageIO.read(new File("Bern.png"));
    } catch (IOException ioe)
    {
      ioe.printStackTrace();
    }
    bernieX = 200;
    bernieY = 550;
    bernie = new Bernie(bernImg, bernieX, bernieY);
    
    model = new Visualization();
    model.add(background);
    model.add(bernie);
    
    view = model.getView();
    renderer = view.getRenderer();
    view.setRenderer(new ScaledVisualizationRenderer(renderer, 1250, 1000));
    view.setBounds(0, 0, WIDTH, HEIGHT);
    view.setSize(WIDTH, HEIGHT);
    view.addKeyListener(this);
    
    JPanel contentPane = (JPanel)this.getContentPane();
    contentPane.add(view);
  }
  
  
  public static void main(String[] args)
  {
    JApplication game = new MainWindow(args);
    invokeInEventDispatchThread(game);
  }
*/
