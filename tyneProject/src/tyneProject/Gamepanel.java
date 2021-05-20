package tyneProject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class Gamepanel extends JPanel implements Runnable, KeyListener{
	
	
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 500, HEIGHT = 500;
	
	private Thread thread;
	
	private boolean running;
	
	private boolean right = true, left = false, up = false, down = false;
	
	private BodyPiece b;
	private ArrayList<BodyPiece> snake;
	
	private Fruit fruit;
	private ArrayList<Fruit> fruits;
	
	private Random r;
	
	private int xCoor = 10, yCoor = 10, size = 15;
	private int ticks = 0;
	
	

	public Gamepanel() {
		setFocusable(true);
		
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		addKeyListener(this);
		
		snake = new ArrayList<BodyPiece>();
		fruits = new ArrayList<Fruit>();
		
		r = new Random();
		
		start();
	}

	public void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
		
	}
	public void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void tick() {
		if(snake.size() ==0) {
			b = new BodyPiece(xCoor, yCoor, 10);
			snake.add(b);
		}
		ticks++;
		if(ticks > 250000) {
			if(right) xCoor++;
			if(left) xCoor--;
			if(up) yCoor--;
			if(down) yCoor++;
			
			ticks = 0;
			
			b = new BodyPiece(xCoor, yCoor, 10);
			snake.add(b);
			
			if(snake.size() > size) {
				snake.remove(0);
			}
		}
		if(fruits.size() ==0) {
			int xCoor = r.nextInt(49);
			int yCoor = r.nextInt(49);
			
			fruit = new Fruit(xCoor, yCoor, 10);
			fruits.add(fruit);
		}
		
		for(int i = 0 ; i < fruits.size(); i++) {
			if(xCoor == fruits.get(i).getxCoor() && yCoor == fruits.get(i).getyCoor()) {
				size++;
				fruits.remove(i);
				i++;
			}
		}
		//collision on the snake body piece
		for(int i = 0 ; i < snake.size(); i++) {
			if(xCoor == snake.get(i).getxCoor() && yCoor == snake.get(i).getyCoor()) {
				if(i != snake.size()- 1) {
					System.out.println("Game 0ver");
					stop();
				}
			}
		}
		
		//collision on the border
		if(xCoor < 0 || xCoor > 49 || yCoor < 0 || yCoor > 49) {
			System.out.println("Game 0ver");
			stop();
		}
		
	}
	public void paint(Graphics g) {
		g.clearRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		for(int i = 0 ; i < WIDTH/10 ; i++) {
			g.drawLine(i * 10, 0, i * 10, HEIGHT);
		}
		for(int i = 0 ; i < HEIGHT/10 ; i++) {
			g.drawLine(0, i * 10, HEIGHT, i * 10);
		}
		for(int i = 0 ; i < snake.size() ; i++) {
			snake.get(i).draw(g);
		}
		for(int i = 0 ; i < fruits.size(); i++) {
			fruits.get(i).draw(g);
		}
		
	}

	@Override
	public void run() {
		while(running) {
			tick();
			repaint();
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_RIGHT && !left) {
			right = true;
			up = false;
			down = false;	
		}
		if(key == KeyEvent.VK_LEFT && !right) {
			left = true;
			up = false;
			down = false;	
		}
		if(key == KeyEvent.VK_UP && !down) {
			up = true;
			left = false;
			right = false;	
		}
		if(key == KeyEvent.VK_DOWN && !up) {
			down = true;
			left = false;
			right = false;	
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}