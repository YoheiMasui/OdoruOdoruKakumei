import java.awt.*;
import javax.swing.*;
import java.util.*;

class GameGUI extends JComponent {
  GameGUI() {
    this.setPreferredSize(new Dimension(800, 700));
  }
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
    g.setColor(Color.BLUE);
    g.drawString("DEBUG", 600, 30);
  }
}

class Game extends JPanel implements Runnable {
	int frame_count;
  int speed = 5;
	JLabel frame_count_label;
  Score score;
  
	Game(String fileName, Graphics g) {
    this.setBackground(Color.BLACK);
    this.add(new GameGUI());
    this.setVisible(true);
	}

	public void run() {
    long nextFrame = System.currentTimeMillis() * 6 + 100;
    while (true) {
      Update();
      repaint();
      long now = System.currentTimeMillis() * 6;
      if (now < nextFrame) {
        now = System.currentTimeMillis() * 6;
        if (now < nextFrame) {
          try {
            Thread.sleep((nextFrame - now) / 6);
          } catch (InterruptedException e) { }
        }
      }
      nextFrame += 100;
    }
    /*
		long error = 0;
		int fps = 60;
		long idealSleep = (1000 << 16) / fps;
		long oldTime;
		long newTime = System.currentTimeMillis() << 16;
		while (true) {
			oldTime = newTime;
			Update();
			newTime = System.currentTimeMillis() << 16;
			long sleepTime = idealSleep - (newTime - oldTime) - error;
			if (sleepTime < 0x20000) sleepTime = 0x2000;
			oldTime = newTime;
			try {
				Thread.sleep(sleepTime >> 16);
			} catch (InterruptedException e) { }
			newTime = System.currentTimeMillis() << 16;
			error = newTime - oldTime - sleepTime;
      }	*/
	}
	
	void Update() {
		frame_count++;
	}
}
