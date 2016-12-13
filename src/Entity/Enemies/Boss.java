package Entity.Enemies;
import Entity.*;
import TileMap.TileMap;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.imageio.ImageIO;

public class Boss extends Enemy{
	
	private BufferedImage[] sprites;

	public Boss(TileMap tm) {
		super(tm);
		
		moveSpeed = 0.3;
		maxSpeed = 0.3;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		
		health = maxHealth = 200;
		damage = 5;
		
		// load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
					"/Sprites/Enemies/slugger.gif"
				)
			);
			
			sprites = new BufferedImage[3];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(
					i * width,
					0,
					width,
					height
				);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(300);
		
		right = true;
		facingRight = true;
	}
	
	private void getNextPosition() {
		
		// movement
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		
		// falling
		if(falling) {
			dy += fallSpeed;
		}
		
	}
	
	public void update() {
		
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// check flinching
		if(flinching) {
			long elapsed =
				(System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 400) {
				flinching = false;
			}
		}
		
		// if it hits a wall, go other direction
		if(right && dx == 0) {
			right = false;
			left = true;
			facingRight = false;
		}
		else if(left && dx == 0) {
			right = true;
			left = false;
			facingRight = true;
		}
		// update animation
		animation.update();
	}
	
	
	
	public void spawnSlugger(){
		try {
			while(health > 0){
				Thread.sleep(5000);			//spawn slugger every 5 sec.
				Slugger s = new Slugger(tileMap);
				s.setPosition(dx + 100, dy + 100);
				System.out.println("Running");
			}
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}

	}
	
	public void draw(Graphics2D g) {
		
		//if(notOnScreen()) return;
		
		setMapPosition();
		
		super.draw(g);
		
	}
	
}