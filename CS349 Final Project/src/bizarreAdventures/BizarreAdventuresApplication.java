package bizarreAdventures;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.JApplication;
import io.ResourceFinder;
import resources.Marker;

public class BizarreAdventuresApplication extends JApplication implements ActionListener
{
  public static final int WIDTH = 600;
  public static final int HEIGHT = 800;

  public BizarreAdventuresApplication(final String[] args)
  {
    super(args, WIDTH, HEIGHT);
    ResourceFinder rf = ResourceFinder.createInstance(new Marker());
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
    // TODO Auto-generated method stub
    
  }
}
