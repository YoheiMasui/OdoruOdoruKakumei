import java.awt.*;
import javax.swing.*;
import java.util.*;

class Position {
  public boolean enable;
  public int x, y;
  Position(int x, int y) {
    enable = true;
    this.x = x;
    this.y = y;
  }
}

class GameGUI extends JComponent {
  int frame_count;
  Dimension size;
  Image back;
  Graphics buffer;
  Position[] left_arrows_pos, down_arrows_pos, up_arrows_pos, right_arrows_pos;
  Score score;
  int speed = 5;
  final Image left_arrow_img = Toolkit.getDefaultToolkit().getImage("./img/l.png");
  final Image left_arrow_n_img = Toolkit.getDefaultToolkit().getImage("./img/l_n.png");
  final Image down_arrow_img = Toolkit.getDefaultToolkit().getImage("./img/d.png");
  final Image down_arrow_n_img = Toolkit.getDefaultToolkit().getImage("./img/d_n.png");
  final Image up_arrow_img = Toolkit.getDefaultToolkit().getImage("./img/u.png");
  final Image up_arrow_n_img = Toolkit.getDefaultToolkit().getImage("./img/u_n.png");
  final Image right_arrow_img = Toolkit.getDefaultToolkit().getImage("./img/r.png");
  final Image right_arrow_n_img = Toolkit.getDefaultToolkit().getImage("./img/r_n.png");
  final Position[][] allArrows_pos = {    left_arrows_pos, down_arrows_pos, up_arrows_pos, right_arrows_pos };
  
  GameGUI(String fileName) {
    this.setPreferredSize(new Dimension(800, 700));
    score = new Score(fileName);
    left_arrows_pos = new Position[score.left_arrows.length];
    down_arrows_pos = new Position[score.down_arrows.length];
    up_arrows_pos = new Position[score.up_arrows.length];
    right_arrows_pos = new Position[score.right_arrows.length];
    for (int i = 0; i < left_arrows_pos.length; i++) {
      left_arrows_pos[i] = new Position(20, 10 + speed * Integer.valueOf(score.left_arrows[i]));
    }
    for (int i = 0; i < down_arrows_pos.length; i++) {
      down_arrows_pos[i] = new Position(168, 10 + speed * Integer.valueOf(score.down_arrows[i]));
    }
    for (int i = 0; i < up_arrows_pos.length; i++) {
      up_arrows_pos[i] = new Position(316, 10 + speed * Integer.valueOf(score.up_arrows[i]));
    }
    for (int i = 0; i < right_arrows_pos.length; i++) {
      right_arrows_pos[i] = new Position(464, 10 + speed * Integer.valueOf(score.right_arrows[i]));
    }
  }

  void setValues(int frame_count) {
    this.frame_count = frame_count;
 }

  public void Update() {
    for (int i = 0; i < left_arrows_pos.length; i++) {
      left_arrows_pos[i].y -= speed;
    }
    for (int i = 0; i < down_arrows_pos.length; i++) {
      down_arrows_pos[i].y -= speed;
    }
    for (int i = 0; i < up_arrows_pos.length; i++) {
      up_arrows_pos[i].y -= speed;
    }
    for (int i = 0; i < right_arrows_pos.length; i++) {
      right_arrows_pos[i].y -= speed;
    }
  }
  
  public void paintComponent(Graphics g) {
    Dimension size = getSize();
    Image back = createImage(size.width, size.height);
    Graphics buffer = back.getGraphics();

    super.paintComponent(buffer);
    buffer.setColor(Color.BLACK);
    buffer.fillRect(0, 0, size.width, size.height);
    buffer.drawImage(left_arrow_n_img, 20, 10, this);
    buffer.drawImage(down_arrow_n_img, 168, 10, this);
    buffer.drawImage(up_arrow_n_img, 316, 10, this);
    buffer.drawImage(right_arrow_n_img, 464, 10, this);

    for (int i = 0; i < left_arrows_pos.length; i++) {
      buffer.drawImage(left_arrow_img, left_arrows_pos[i].x, left_arrows_pos[i].y, this);
    }
    for (int i = 0; i < down_arrows_pos.length; i++) {
      buffer.drawImage(down_arrow_img, down_arrows_pos[i].x, down_arrows_pos[i].y, this);
    }
    for (int i = 0; i < up_arrows_pos.length; i++) {
      buffer.drawImage(up_arrow_img, up_arrows_pos[i].x, up_arrows_pos[i].y, this);
    }
    for (int i = 0; i < right_arrows_pos.length; i++) {
      buffer.drawImage(right_arrow_img, right_arrows_pos[i].x, right_arrows_pos[i].y, this);
    }
    // DEBUG
    // BEGIN //////////////////////////////////////////////////////
    buffer.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
    buffer.setColor(Color.BLUE);
    buffer.drawString("DEBUG", 620, 30);
    buffer.setFont(new Font("TimesRoman", Font.PLAIN, 15)); 
    buffer.setColor(Color.WHITE);
    buffer.drawString("frame_count: " + frame_count, 620, 60);
    // END ///////////////////////////////////////////////////////
    g.drawImage(back, 0, 0, this);
  }
}

class Game extends JPanel implements Runnable {
	int frame_count;
  int speed = 5;
	JLabel frame_count_label;
  GameGUI gGUI;
  
	Game(String fileName) {
    this.setBackground(Color.BLACK);
    gGUI = new GameGUI(fileName);
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
			if (sleepTime < 0x20000) sleepTime = 0x2000;
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
    gGUI.Update();
	}
}
