import java.awt.*;
import javax.swing.*;
import java.util.*;

class GameGUI extends JComponent {
  int frame_count;
  Dimension size;
  Image back;
  Graphics buffer;
  
  GameGUI() {
    this.setPreferredSize(new Dimension(800, 700));
  }

  void setValues(int frame_count) {
    this.frame_count = frame_count;
  }
  
  public void paintComponent(Graphics g) {
    Dimension size = getSize();
    Image back = createImage(size.width, size.height);
    Graphics buffer = back.getGraphics();

    super.paintComponent(buffer);
    buffer.setColor(Color.BLACK);
    buffer.fillRect(0, 0, size.width, size.height);
    
    buffer.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
    buffer.setColor(Color.BLUE);
    buffer.drawString("DEBUG", 600, 30);
    buffer.setFont(new Font("TimesRoman", Font.PLAIN, 15)); 
    buffer.setColor(Color.WHITE);
    buffer.drawString("frame_count: " + frame_count, 600, 60);

    g.drawImage(back, 0, 0, this);
  }
}

class Game extends JPanel implements Runnable {
	int frame_count;
  int speed = 5;
	JLabel frame_count_label;
  Score score;
  GameGUI gGUI;
  
	Game(String fileName, Graphics g) {
    this.setBackground(Color.BLACK);
    gGUI = new GameGUI();
    this.add(gGUI);
    this.setVisible(true);
	}

	public void run() {
		long error = 0;
		int fps = 60;
		long idealSleep = (1000 << 16) / fps;
		long oldTime;
		long newTime = System.currentTimeMillis() << 16;
		while (true) {
			oldTime = newTime;
			Update();
      setValues();
      repaint();
			newTime = System.currentTimeMillis() << 16;
			long sleepTime = idealSleep - (newTime - oldTime) - error;
			//if (sleepTime < 0x20000) sleepTime = 0x2000;
			oldTime = newTime;
			try {
				Thread.sleep(sleepTime >> 16);
			} catch (InterruptedException e) { }
			newTime = System.currentTimeMillis() << 16;
			error = newTime - oldTime - sleepTime;
    }
	}

  void setValues() {
    gGUI.setValues(frame_count);
  }
	void Update() {
		frame_count++;
	}
}
