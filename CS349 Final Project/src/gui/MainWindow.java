package gui;
import java.awt.Dimension;
import java.awt.Graphics;
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

public class MainWindow extends JFrame {
    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;
    
    private BufferedImage backgroundImage;
    
    public MainWindow() throws IOException {
        super("Bernstein's Bizarre Adventure");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Image IO
        backgroundImage = ImageIO.read(new File("src/resources/349 Background.png"));
        
        // Create a JPanel subclass to use as the content pane and draw the background image
        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
            
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(WIDTH, HEIGHT);
            }
        });
    }
    

    public static void main(String[] args) throws IOException {
    	MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);
    }
}
