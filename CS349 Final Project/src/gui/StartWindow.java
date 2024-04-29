 package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

import app.JApplication;
import bizarreAdventures.BizarreAdventuresApplication;
import io.ResourceFinder;
import resources.Marker;
import visual.VisualizationView;
import visual.dynamic.described.Stage;
import visual.statik.sampled.Content;
import visual.statik.sampled.ContentFactory;

/**
 * MainWindow class to create the main window of the videogame.
 * @author Cole Glaccum
 * @version 1.0
 */

public class StartWindow extends JApplication implements KeyListener, ActionListener
{
  private static final long serialVersionUID = 1L;
  private Stage stage;
  
  public static void main(String[] args) {
    StartWindow start = new StartWindow();
    invokeInEventDispatchThread(start);
  }
  
  public StartWindow() {
    super(600, 600);
  }
  
  @Override
  public void actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub
    
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
    if (code == KeyEvent.VK_SPACE)
    {
      new BizarreAdventuresApplication(args).run();
      stage.clear();
      stage.stop();
      destroy();
    }
  }

  @Override
  public void keyReleased(KeyEvent e)
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void init()
  {
    
    ResourceFinder rf = ResourceFinder.createInstance(new Marker());
    ContentFactory factory = new ContentFactory(rf);
    
    Content background = factory.createContent("start.png", 3, false);
    
    stage = new Stage(50);
    stage.add(background);
    VisualizationView stageView = stage.getView(); 
    stageView.setBounds(0, 0, 600, 600);
    stageView.addKeyListener(this);
    
    JPanel contentPane = (JPanel)this.getContentPane();
    contentPane.add(stageView);
    stage.start();
  }
  
}
