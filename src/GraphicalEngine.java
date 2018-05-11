import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class GraphicalEngine extends JPanel implements Runnable, MouseListener{
	
	public final int WIDTH = 1280;
	public final int HEIGHT = 720;
	private Thread thread;
	private boolean running;
	private final int fps = 60;
	private final long targetTime = 1000/fps;

	public InfinityTest game = new InfinityTest();

	/**
	 * 
	 */
	public GraphicalEngine(){
		initComponents();
	}

	/**
	 * 
	 */
	public void initComponents(){
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.addMouseListener(this);
		this.setFocusable(true);
		window.add(this);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		start();
	}

	private void start(){
		game.onCreate();
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * 
	 */
	public void run(){
		while(running){
			Long start = System.nanoTime();
			tick();
			repaint();
			Long elapsed = System.nanoTime() - start;
			Long wait = targetTime - elapsed/1000000;
			if(wait <= 0){
				wait = (long) 5;
			}
			try{
				Thread.sleep(wait);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 */
	public void tick(){
		game.update();
	}

	/**
	 * 
	 * @param g 
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		game.draw(g2);
	}

	public void mousePressed(MouseEvent event){
		game.mousePressed(event);
	}
	public void	mouseClicked(MouseEvent event){}
	public void mouseEntered(MouseEvent event){}
	public void mouseExited(MouseEvent event){}
	public void mouseReleased(MouseEvent event){}


	public static void main(String[] args){
		GraphicalEngine engine = new GraphicalEngine();
	}

}