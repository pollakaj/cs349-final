package bizarreAdventures;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

import com.sun.tools.javac.Main;

import Components.Platform;
import app.JApplication;
import auditory.sampled.BufferedSound;
import auditory.sampled.BufferedSoundFactory;
import characters.Bernie;
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
  private Stage stage;

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
    
    
    // Music Related Things
    BufferedSoundFactory buffFactory = new BufferedSoundFactory(rf);
      
    try
    {
      BufferedSound music = buffFactory.createBufferedSound("background_music.wav");
      Clip clip = javax.sound.sampled.AudioSystem.getClip();
      music.render(clip);
      
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (UnsupportedAudioFileException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (LineUnavailableException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
   
 
    ContentFactory factory = new ContentFactory(rf);
    stage = new Stage(50);
    
    Bernie b = new Bernie();
    Content background = factory.createContent("349 Background.png", 3, false);
    stage.add(background);
    stage.add(b);
    VisualizationView stageView = stage.getView(); 
    stageView.setBounds(0, 0, WIDTH, HEIGHT);
    stageView.addKeyListener(b);
    
    Platform platform = new Platform(700, 600, b);
    stage.add(platform);
    Platform platform1 = new Platform(300, 600, b);
    stage.add(platform1);
    Platform platform2 = new Platform(1200, 600, b);
    stage.add(platform2);
    Platform platform3 = new Platform(1500, 600, b);
    stage.add(platform3);
    Platform platform4 = new Platform(900, 400, b);
    stage.add(platform4);
    Platform platform5 = new Platform(500, 400, b);
    stage.add(platform5);
    Platform platform6 = new Platform(1500, 400, b);
    stage.add(platform6);
    Platform platform7 = new Platform(1700, 400, b);
    stage.add(platform7);
    Platform platform8 = new Platform(100, 200, b);
    stage.add(platform8);
    Platform platform9 = new Platform(200, 300, b);
    stage.add(platform9);
    
    JPanel contentPane = (JPanel)this.getContentPane();
    contentPane.add(stageView);
    stage.start();
  }

}
