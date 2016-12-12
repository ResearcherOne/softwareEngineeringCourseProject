package Audio;

import java.io.File;
import java.net.URL;

import javax.sound.sampled.*;


public class AudioPlayer {
	
	private  Clip clip;
	
	public AudioPlayer(String s) {
		
		try {
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














