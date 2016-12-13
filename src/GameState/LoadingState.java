package GameState;

import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

public class LoadingState extends GameState {
	
	private Background bg;
	
	

	
	private Color titleColor;
	private Font titleFont;
	
	private Font font;
	
	public LoadingState(GameStateManager gsm) {
		
		this.gsm = gsm;
		
		try {
			
			bg = new Background("/Backgrounds/menubg.gif", 1);
			bg.setVector(-0.1, 0);
			
			titleColor = new Color(128, 0, 0);
			titleFont = new Font(
					"Century Gothic",
					Font.PLAIN,
					28);
			
			font = new Font("Arial", Font.PLAIN, 12);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void init() {}
	
	public void update() {
		bg.update();
	}
	
	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		
		// draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("PRESS ENTER ", 70, 80);
		g.drawString("BUTTON FOR ", 70, 110);
		g.drawString("NEW LEVEL", 70, 140);
		// draw menu options
		g.setFont(font);
		
			
		
		
	}
	
	private void select() {
		gsm.setState(GameStateManager.LEVEL2STATE);
		
		
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			select();
		}

	}
	public void keyReleased(int k) {}
	
}










