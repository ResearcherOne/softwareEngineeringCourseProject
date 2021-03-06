package GameState;

import Main.GamePanel;
import TileMap.*;
import Entity.*;
import Entity.Enemies.*;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Level2State extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	private Boss b;
	private Player player;
	private Door d ;
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
	
	private HUD hud;

	public Level2State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	
	public void init() {
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset.gif");
		tileMap.loadMap("/Maps/Levell.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/grassbg1.gif", 0.1);
		
		player = new Player(tileMap);
		player.setPosition(100, 100);
		
		populateEnemies();
		
		explosions = new ArrayList<Explosion>();
		
		hud = new HUD(player);
		
	
	}
	
	private void populateEnemies() {
		
		enemies = new ArrayList<Enemy>();
		 d =new Door(tileMap);
		d.setPosition(3545,180);
	
		
		Point[] points = new Point[] {
			new Point(200, 100),
		
		
		};
		for(int i = 0; i < points.length; i++) {
			b = new Boss(tileMap);
			b.setPosition(points[i].x, points[i].y);
			enemies.add(b);
		}
		
	}
	
	public void update() {
	if(player.intersects(d))gsm.setState(3);
		if((240-player.gety())<15)player.setDead(true);
		if(player.isDead()){Level1State.Leaderboards=Level1State.points;
		Level1State.points=0;
			Level1State.bgMusic.close();
			gsm.setState(2);}
		// update player
		player.update();
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);
		
		// set background
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
		// attack enemies
		player.checkAttack(enemies);
		
		// update all enemies
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(
					new Explosion(e.getx(), e.gety()));
			}
		}
		
		// update explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}
		
	}
	
	int counter =0;
	public void draw(Graphics2D g) {
	counter=counter+5;
		if(counter==1000){         
			Slugger s=new Slugger(tileMap);
			s.setPosition(b.getx(),b.gety());
			enemies.add(s);
			counter=counter-1000;
		
			s.setLeft(true);
		
		}
		
		
		// draw bg
		bg.draw(g);
		d.draw(g);
		// draw tilemap
		tileMap.draw(g);
		g.drawString("Points ="+Level1State.points, 240, 20);
		// draw player
		player.draw(g);
		
		// draw enemies
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		
		// draw explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition(
				(int)tileMap.getx(), (int)tileMap.gety());
			explosions.get(i).draw(g);
		}
		
		// draw hud
		hud.draw(g);
		
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_UP) player.setUp(true);
		if(k == KeyEvent.VK_DOWN) player.setDown(true);
		if(k == KeyEvent.VK_W) player.setJumping(true);
		if(k == KeyEvent.VK_E) player.setGliding(true);
		if(k == KeyEvent.VK_R) player.setScratching();
		if(k == KeyEvent.VK_F) player.setFiring();
	}
	
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_UP) player.setUp(false);
		if(k == KeyEvent.VK_DOWN) player.setDown(false);
		if(k == KeyEvent.VK_W) player.setJumping(false);
		if(k == KeyEvent.VK_E) player.setGliding(false);
	}
	
}

















