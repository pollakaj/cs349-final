package bizarreAdventures;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

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
    BufferedSoundFactory buffFactory = new BufferedSoundFactory(rf);

    BufferedSound backgroundMusic;
    Clip clip = null;
    
    try {
		backgroundMusic = buffFactory.createBufferedSound("background_music.mp3");
		backgroundMusic.render(clip);
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (UnsupportedAudioFileException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (LineUnavailableException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    

    ContentFactory factory = new ContentFactory(rf);
    Stage stage = new Stage(50);
    
    
    Bernie b = new Bernie();
    Content background = factory.createContent("349 Background.png", 3, false);
    stage.add(background);
    stage.add(b);
    VisualizationView stageView = stage.getView(); 
    stageView.setBounds(0, 0, WIDTH, HEIGHT);
    stageView.addKeyListener(b);
    Content platform = factory.createContent("large_platform.png", 4, false);
    platform.setLocation(200, 600);
    stage.add(platform);
    
    JPanel contentPane = (JPanel)this.getContentPane();
    contentPane.add(stageView);
    stage.start();
  }

}
