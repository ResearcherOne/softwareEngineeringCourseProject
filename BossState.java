package GameState;

import Main.GamePanel;
import TileMap.*;
import Entity.*;
import Entity.Enemies.*;
import Audio.AudioPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class BossState extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	public static int points=0; 
	public static int Leaderboards=0;
	private Player player;
	private Door d ;
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
	
	private HUD hud;
	
	public static AudioPlayer bgMusic;
	
	public BossState(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	
	public void init() {
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset.gif");
		tileMap.loadMap("/Maps/level1-1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/grassbg1.gif", 0.1);
		
		player = new Player(tileMap);
		player.setPosition(100, 100);
		
		populateEnemies();
		
		explosions = new ArrayList<Explosion>();
		
		hud = new HUD(player);
		
		bgMusic = new AudioPlayer("/Music/level1-1.mp3");		
		
		bgMusic.play();
		
	}
	
	private void populateEnemies() {
		
		enemies = new ArrayList<Enemy>();
		 d =new Door(tileMap);
		d.setPosition(3145,180);
	
		Slugger s;
		Point[] points = new Point[] {
			new Point(200, 100),
			new Point(860, 200),
			new Point(1525, 200),
			new Point(1680, 200),
			new Point(1800, 200)
		
		};
		for(int i = 0; i < points.length; i++) {
			s = new Slugger(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}
		
	}
	
	public void update() {
	if(player.intersects(d)){points=points+player.getHealth()*5;
		gsm.setState(3);};
		if((240-player.gety())<15)player.setDead(true);
		if(player.isDead()){bgMusic.close();
			Leaderboards=points;
		points=0;
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
				points=points+5;
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
	
	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		d.draw(g);
		// draw tilemap
		tileMap.draw(g);
		g.drawString("Points ="+points, 250, 20);
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

class Level2State extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	
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
	
		Slugger s;
		Point[] points = new Point[] {
			new Point(200, 100),
		
		
		};
		for(int i = 0; i < points.length; i++) {
			s = new Slugger(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}
		
	}
	
	public void update() {
		/*		if(player.intersects(d))gsm.setState(3);
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
		*/
	}
	
	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		d.draw(g);
		// draw tilemap
		tileMap.draw(g);
		g.drawString("Points =", 250, 20);
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
	
	class  MenuStat extends GameState {
		
		private Background bg;
		
		private int currentChoice = 0;
		private String[] options = {
			"Start",
			"Help",
			"Quit"
		};
		
		private Color titleColor;
		private Font titleFont;
		
		private Font font;
		
		 MenuStat(GameStateManager gsm) {
			
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
			g.drawString("Dragon Tale", 80, 70);
			
			// draw menu options
			g.setFont(font);
			for(int i = 0; i < options.length; i++) {
				if(i == currentChoice) {
					g.setColor(Color.BLACK);
				}
				else {
					g.setColor(Color.RED);
				}
				g.drawString(options[i], 145, 140 + i * 15);
			}
			
		}
		
		private void select() {
			if(currentChoice == 0) {
				gsm.setState(GameStateManager.LEVEL1STATE);
			}
			if(currentChoice == 1) {
				// help
			}
			if(currentChoice == 2) {
				System.exit(0);
			}
		}
		
		public void keyPressed(int k) {
			if(k == KeyEvent.VK_ENTER){
				select();
			}
			if(k == KeyEvent.VK_UP) {
				currentChoice--;
				if(currentChoice == -1) {
					currentChoice = options.length - 1;
				}
			}
			if(k == KeyEvent.VK_DOWN) {
				currentChoice++;
				if(currentChoice == options.length) {
					currentChoice = 0;
				}
			}
		}
		public void keyReleased(int k) {}
		
	}

}


 





























