package bizarreAdventures;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import app.JApplication;
import io.ResourceFinder;
import resources.Marker;
import visual.VisualizationView;
import visual.dynamic.described.Stage;
import visual.statik.sampled.Content;
import visual.statik.sampled.ContentFactory;

public class BizarreAdventuresApplication extends JApplication implements ActionListener
{
  public static final int WIDTH = 1920;
  public static final int HEIGHT = 1080;

  public BizarreAdventuresApplication(final String[] args)
  {
    super(args, WIDTH, HEIGHT);
  }
  
  public static void main(String[] args) {
    JApplication mainWindow = new BizarreAdventuresApplication(args);
    invokeInEventDispatchThread(mainWindow);
 }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void init()
  {
    ResourceFinder rf = ResourceFinder.createInstance(new Marker());
    ContentFactory factory = new ContentFactory(rf);
    Stage stage = new Stage(50);
    Content background = factory.createContent("349 Background.png", 3, false);
    stage.add(background);
    VisualizationView stageView = stage.getView(); 
    stageView.setBounds(0, 0, WIDTH, HEIGHT);
    Content platform = factory.createContent("large_platform.png", 4, false);
    stage.add(platform);
    JPanel contentPane = (JPanel)this.getContentPane();
    contentPane.add(stageView);
    stage.start();
  }
}
