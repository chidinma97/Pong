package pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.util.Random;

import javax.swing.Timer;
import javax.swing.JFrame;

public class Pong implements ActionListener, KeyListener{
	public static Pong pong;
	private final int WIDTH = 800, HEIGHT=800;
	private Ellipse2D ball;
	private Renderer renderer;
	private Rectangle rightRect, leftRect;
	private int score, dX = 5, dY = randomInt(1, 5);
	private double bX = WIDTH/2, bY = HEIGHT/2;
	private boolean gameOver, started;


	public Pong(){
		JFrame jframe = new JFrame();
		Timer timer = new Timer(20, this);

		renderer = new Renderer();
		jframe.add(renderer);
		jframe.addKeyListener(this);

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(WIDTH, HEIGHT);
		jframe.getContentPane().setBackground(Color.black);
		jframe.setResizable(false);
		jframe.setVisible(true);

		ball = new Ellipse2D.Double(bX, bY, 12.5, 12.5);
		rightRect = new Rectangle(100, HEIGHT/2-50, 12, 100);
		leftRect = new Rectangle(WIDTH-100, HEIGHT/2-50, 12, 100);



		timer.start();
	}

	public static void main(String[] args){
		pong = new Pong();
	}

	public int randomInt(int min, int max){
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	void draw() {
		if(!started){
			started = true;
		}

		if(gameOver){
			bX = WIDTH/2;
			bY = HEIGHT/2;
			ball = new Ellipse2D.Double(bX, bY, 12.5, 12.5);

		}

		if(started){
			ball = new Ellipse2D.Double(bX, bY, 12.5, 12.5);

			if (ball.intersects(rightRect) || ball.intersects(leftRect) || 
					ball.getY() == 0 || ball.getY() == HEIGHT) {
				dX = -dX;
				dY = randomInt(-4, 4);
				dY = -dY;
				score++;
			}
			if(ball.getX() > WIDTH - 100 ){
				gameOver = true;
				bX = WIDTH/2;
				bY = HEIGHT/2;
			}
			if(ball.getX() < 100 ){
				gameOver = true;
				bX = WIDTH/2;
				bY = HEIGHT/2;
			}

			bX = bX + dX;
			bY = bY + dY;
		}
	}

	public void repaint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.WHITE);
		g.fillOval((int)ball.getX(), (int)ball.getY(), (int)ball.getWidth(), (int)ball.getHeight());

		g.setColor(Color.blue);
		g.fillRect(rightRect.x, rightRect.y, rightRect.width, rightRect.height);

		g.setColor(Color.red);
		g.fillRect(leftRect.x, leftRect.y, leftRect.width, leftRect.height);

		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 38));

		if(!started){
			g.drawString("Hit 'space' to start", WIDTH/2-175, 100);
		}else if(started && !gameOver){
			g.drawString(String.valueOf(score), WIDTH/2, 200);
			draw();
		}

		if(gameOver ){
			g.drawString("Game Over", WIDTH/2 - 100, 100);
		}

	}



	@Override
	public void actionPerformed(ActionEvent e) {
		renderer.repaint();


	}


	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			if(gameOver){
				gameOver = false;
			}
			draw();
		}
		if(e.getKeyCode() == KeyEvent.VK_W && rightRect.y - 50 > 0){
			rightRect.y -= 50;
		}
		if(e.getKeyCode() == KeyEvent.VK_S  && rightRect.y + 200 < HEIGHT){
			rightRect.y += 50;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP && leftRect.y - 50 > 0){
			leftRect.y -= 50;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN && leftRect.y + 200 < HEIGHT){
			leftRect.y += 50;
		}



	}


	@Override
	public void keyReleased(KeyEvent e) {

	}



	@Override
	public void keyTyped(KeyEvent arg0) {

	}



}
