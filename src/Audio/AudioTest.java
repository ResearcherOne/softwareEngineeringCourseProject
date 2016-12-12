package Audio;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import javax.sound.sampled.*;

import org.omg.CORBA.portable.InputStream;

public class AudioTest {
	
	private  Clip clip;
	
	public AudioTest(String s) {
		
		try {
			//DocFlavor.URL urlToHot = this.getClass().getResource("resource/level1-1.wav");
			//clip.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("Music/level1-1.wav")));
			//System.out.println(urlToHot);

			//File f = new File("C:\\Users\\qual\\workspace\\softwareEngineeringCourseProject\\Resources\\Music\\level1-1.wav");

			//File f = new File("C:\\Users\\qual\\workspace\\softwareEngineeringCourseProject\\Resources\\Music\\level1-1.wav");

			//InputStream input = (InputStream) getClass().getResourceAsStream(s);

			
			
			//File f = new File(".Resources/Music/level1-1.wav");
			
			
			//URL url = getClass().getResource("/Music/level1-1.wav");
			//File f = new File(url.toURI());
			
			URL url = getClass().getResource(s);
			File f = new File(url.getPath());
			
	        AudioInputStream audio = AudioSystem.getAudioInputStream(f);
	        AudioFormat format = audio.getFormat();
	        AudioFormat baseFormat = audio.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED,
				baseFormat.getSampleRate(),
				16,
				baseFormat.getChannels(),
				baseFormat.getChannels() * 2,
				baseFormat.getSampleRate(),
				false
			);
			AudioInputStream dais =
				AudioSystem.getAudioInputStream(
					f);
			clip = AudioSystem.getClip();
			clip.open(dais);
	    } catch (Exception E) {
	        System.out.println("Exception: "+E.getMessage());
	    }
	}

	
	public void play() {
		if(clip == null) return;
		stop();
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void stop() {
		if(clip.isRunning()) clip.stop();
	}
	
	public void close() {
		stop();
		clip.close();
	}
	
}














