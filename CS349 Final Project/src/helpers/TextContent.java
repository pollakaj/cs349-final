package helpers;

import java.awt.*;

import visual.statik.*;

/**
 * A SimpleContent object that is described by a String.
 * 
 * @author Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public class TextContent implements SimpleContent
{
  private Color color;
  private Font font;
  private Point location;
  private String text;
  
  /**
   * Explicit Value Constructor.
   * 
   * @param text The text
   * @param baselineLeft The registration point (in VisualizationView coordinates)
   */
  public TextContent(String text, Point baselineLeft)
  {
    setLocation(baselineLeft);
    setText(text);
    setColor(Color.BLACK);
    setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
  }
  
  /**
   * Render this SimpleContent.
   * 
   * @param g  The rendering engine to use
   */
  public void render(Graphics g)
  {
    Graphics2D g2 = (Graphics2D)g;
    Color oldColor = g2.getColor();
    Font oldFont = g2.getFont();
    
    g2.setColor(color);
    g2.setFont(font);
    g2.drawString(text, location.x, location.y);
    
    g2.setColor(oldColor);
    g2.setFont(oldFont);
  }

  /**
   * Set the Color of the text.
   * 
   * @param color The Color
   */
  public void setColor(Color color)
  {
    this.color = color;
  }
  
  /**
   * Set the Font to use.
   * 
   * @param font The Font
   */
  public void setFont(Font font)
  {
    this.font = font;
  }
  
  /**
   * Set the registration point (i.e., location)
   * 
   * @param baselineLeft The registration point (in VisualizationView coordinates)
   */
  public void setLocation(Point baselineLeft)
  {
    this.location = baselineLeft;
  }
  
  /**
   * Set the text.
   * 
   * @param text The text
   */
  public void setText(String text)
  {
    this.text = text;
  }
}