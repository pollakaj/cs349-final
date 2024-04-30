package Components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;


import app.JApplication;
import helpers.TextContent;
import io.ResourceFinder;
import resources.Marker;
import visual.VisualizationView;
import visual.dynamic.described.Stage;
import visual.statik.sampled.Content;
import visual.statik.sampled.ContentFactory;

/**
 * Victory application that appears when the player's character wins.
 *
 * @author Adam Pollak and Cole Glaccum
 * @version 1.0
 * 
 * This code complies with the JMU Honor Code.
 */
public class Victory extends JApplication implements KeyListener, ActionListener
{
  @SuppressWarnings("unused")
  private static final long serialVersionUID = 1L;
  private Stage stage;
  
  /**
   * Victory constructor that passes window dimensions to parent.
   */
  public Victory()
  {
    super(1920, 1080);
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
    if (code == KeyEvent.VK_SPACE)
    {
      stopGame();
    }
    if (code == KeyEvent.VK_ESCAPE)
    {
      System.exit(0);
    }
  }

  @Override
  public void keyReleased(final KeyEvent e)
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void init()
  {
    ResourceFinder rf = ResourceFinder.createInstance(new Marker());
    ContentFactory factory = new ContentFactory(rf);
    
    Content background = factory.createContent("Untitled design.png", 3, false);
    TextContent victoryTxt = new TextContent("VICTORY!", new Point(100, 100));
    
    victoryTxt.setColor(Color.GREEN);
    victoryTxt.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
    
    stage = new Stage(50);
    stage.add(background);
    stage.add(victoryTxt);
    
    VisualizationView stageView = stage.getView(); 
    stageView.setBounds(0, 0, 1920, 1080);
    stageView.addKeyListener(this);
    
    JPanel contentPane = (JPanel)this.getContentPane();
    contentPane.add(stageView);
    stage.start();
  }

  @Override
  public void actionPerformed(final ActionEvent e)
  {
    // TODO Auto-generated method stub
    
  }
  
  /**
   * Method to stop the game from running.
   */
  private void stopGame()
  {
    stage.stop();
    destroy();
  }

}
