package Components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import app.JApplication;
import bizarreAdventures.BizarreAdventuresApplication;
import gui.StartWindow;
import io.ResourceFinder;
import resources.Marker;
import visual.VisualizationView;
import visual.dynamic.described.Stage;
import visual.statik.sampled.Content;
import visual.statik.sampled.ContentFactory;

public class GameOver extends JApplication implements KeyListener, ActionListener
{
  private static final long serialVersionUID = 1L;
  private Stage stage;
  
  public GameOver()
  {
    super(1920, 1080);
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
      stop();
      destroy();
    }
    if (code == KeyEvent.VK_ESCAPE)
    {
      System.exit(0);
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
    
    Content background = factory.createContent("deathScreen.png", 3, false);
    
    stage = new Stage(50);
    stage.add(background);
    VisualizationView stageView = stage.getView(); 
    stageView.setBounds(0, 0, 1920, 1080);
    stageView.addKeyListener(this);
    
    JPanel contentPane = (JPanel)this.getContentPane();
    contentPane.add(stageView);
    stage.start();
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub
    
  }

}
