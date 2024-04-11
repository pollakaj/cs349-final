package characters;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Path2D;

import visual.statik.described.CompositeContent;

public class Bernie extends CompositeContent
{
  public Bernie()
  {
    super();
    Color black = Color.BLACK;
    Color gold = new Color(207, 181, 59);
    Color silver = new Color(165, 169, 180);
    Color red = Color.RED;
    
    BasicStroke stroke = new BasicStroke();
    createBernie();
  }
  
  private void createBernie()
  {
    Path2D.Float shape = new Path2D.Float();
    shape.moveTo(20, 50);
    shape.moveTo(20, 100);
    
  }

}
