package bizarreAdventures;

import java.awt.event.ActionListener;

import app.JApplication;
import io.ResourceFinder;
import resources.Marker;

public abstract class BizarreAdventuresApplication extends JApplication implements ActionListener
{
  public static final int WIDTH = 600;
  public static final int HEIGHT = 800;

  public BizarreAdventuresApplication(final String[] args)
  {
    super(args, WIDTH, HEIGHT);
    ResourceFinder rf = ResourceFinder.createInstance(new Marker());
  }
}
