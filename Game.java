import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.*;

class Position {
  public boolean enable;
  public boolean visible;
  public int x, y;
  Position(int x, int y) {
    this.enable = true;
    this.visible = true;
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

  int left_miss, down_miss, up_miss, right_miss;
  int left_good, down_good, up_good, right_good;
  int left_great, down_great, up_great, right_great;
  int left_perfect, down_perfect, up_perfect, right_perfect;
  int left_marvelous, down_marvelous, up_marvelous, right_marvelous;
  
  public int left_pressed, down_pressed, up_pressed, right_pressed;
  
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

    left_miss = down_miss = up_miss = right_miss = 0;

		// play mp3
		System.out.println("DEBUG__X : " + fileName);
		try{
			File mp3File = new File("./music/" + fileName + "/" + fileName + ".wav");
			
			AudioFormat format = AudioSystem.getAudioFileFormat(mp3File).getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip line = (Clip)AudioSystem.getLine(info);
			line.open(AudioSystem.getAudioInputStream(mp3File));
			line.start();
			System.out.println("MP3 Started.");
			
		} catch(Exception e) { 
			e.printStackTrace();
		}
				
  }

  void setValues(int frame_count) {
    this.frame_count = frame_count;
 }

  public void Update() {
    if (left_miss > 0) left_miss --;
    if (down_miss > 0) down_miss --;
    if (up_miss > 0) up_miss --;
    if (right_miss > 0) right_miss --;
    if (left_good > 0) left_good --;
    if (down_good > 0) down_good --;
    if (up_good > 0) up_good --;
    if (right_good > 0) right_good --;
    if (left_great > 0) left_great --;
    if (down_great > 0) down_great --;
    if (up_great > 0) up_great --;
    if (right_great > 0) right_great --;
    if (left_perfect > 0) left_perfect --;
    if (down_perfect > 0) down_perfect --;
    if (up_perfect > 0) up_perfect --;
    if (right_perfect > 0) right_perfect --;
    if (left_marvelous > 0) left_marvelous --;
    if (down_marvelous > 0) down_marvelous --;
    if (up_marvelous > 0) up_marvelous --;
    if (right_marvelous > 0) right_marvelous --;
    
    for (int i = 0; i < left_arrows_pos.length; i++) {
      if (left_arrows_pos[i].visible && left_arrows_pos[i].enable && left_arrows_pos[i].y < 10 - 4 * speed) {
        left_arrows_pos[i].enable = false;
        left_miss = 10;
      }
      if (left_arrows_pos[i].y > -200) {
        left_arrows_pos[i].y -= speed;
      }
    }
    for (int i = 0; i < down_arrows_pos.length; i++) {
      if (down_arrows_pos[i].visible && down_arrows_pos[i].enable && down_arrows_pos[i].y < 10 - 4 * speed) {
        down_arrows_pos[i].enable = false;
        down_miss = 10;
      }
      if (down_arrows_pos[i].y > -200) {
        down_arrows_pos[i].y -= speed;
      }
    }
    for (int i = 0; i < up_arrows_pos.length; i++) {
      if (up_arrows_pos[i].visible && up_arrows_pos[i].enable && up_arrows_pos[i].y < 10 - 4 * speed) {
        up_arrows_pos[i].enable = false;
        up_miss = 10;
      }
      if (up_arrows_pos[i].y > -200) {
        up_arrows_pos[i].y -= speed;
      }
    }
    for (int i = 0; i < right_arrows_pos.length; i++) {
      if (right_arrows_pos[i].visible && right_arrows_pos[i].enable && right_arrows_pos[i].y < 10 - 4 * speed) {
        right_arrows_pos[i].enable = false;
        right_miss = 10;
      }
      if (right_arrows_pos[i].y > -200) {
        right_arrows_pos[i].y -= speed;
      }
    }
  }

  public void Pressed(int key) {
    switch (key) {
    case 0:
      left_pressed = frame_count;
      for (int i = 0; i < left_arrows_pos.length; i++) {
        if (left_arrows_pos[i].visible && left_arrows_pos[i].enable) {
          int diff = Math.abs(left_arrows_pos[i].y - 10);
          if (diff <= speed) {
            System.out.println("MARVELOUS!!!");
            left_marvelous = 10;
            left_arrows_pos[i].visible = false;
            break;
          } else if (diff <= speed * 3) {
            System.out.println("PERFECT!!");
            left_perfect = 10;
            left_arrows_pos[i].visible = false;
            break;
          } else if (diff <= speed * 5) {
            System.out.println("GREAT!");
            left_great = 10;
            left_arrows_pos[i].visible = false;
            break;
          } else if (diff <= speed * 7) {
            System.out.println("GOOD!");
            left_good = 10;
            left_arrows_pos[i].visible = false;
            break;
          }
        }
      }
      break;
    case 1:
      down_pressed = frame_count;
      for (int i = 0; i < down_arrows_pos.length; i++) {
        if (down_arrows_pos[i].visible && down_arrows_pos[i].enable) {
          int diff = Math.abs(down_arrows_pos[i].y - 10);
          if (diff <= speed) {
            System.out.println("MARVELOUS!!!");
            down_marvelous = 10;
            down_arrows_pos[i].visible = false;
            break;
          } else if (diff <= speed * 3) {
            System.out.println("PERFECT!!");
            down_perfect = 10;
            down_arrows_pos[i].visible = false;
            break;
          } else if (diff <= speed * 5) {
            System.out.println("GREAT!");
            down_great = 10;
            down_arrows_pos[i].visible = false;
            break;
          } else if (diff <= speed * 7) {
            System.out.println("GOOD!");
            down_good = 10;
            down_arrows_pos[i].visible = false;
            break;
          }
        }
      }
      break;
    case 2:
      up_pressed = frame_count;
      for (int i = 0; i < up_arrows_pos.length; i++) {
        if (up_arrows_pos[i].visible && up_arrows_pos[i].enable) {
          int diff = Math.abs(up_arrows_pos[i].y - 10);
          if (diff <= speed) {
            System.out.println("MARVELOUS!!!");
            up_marvelous = 10;
            up_arrows_pos[i].visible = false;
            break;
          } else if (diff <= speed * 3) {
            System.out.println("PERFECT!!");
            up_perfect = 10;
            up_arrows_pos[i].visible = false;
            break;
          } else if (diff <= speed * 5) {
            System.out.println("GREAT!");
            up_great = 10;
            up_arrows_pos[i].visible = false;
            break;
          } else if (diff <= speed * 7) {
            System.out.println("GOOD!");
            up_good = 10;
            up_arrows_pos[i].visible = false;
            break;
          }
        }
      }
      break;
    case 3:
      right_pressed = frame_count;
      for (int i = 0; i < right_arrows_pos.length; i++) {
        if (right_arrows_pos[i].visible && right_arrows_pos[i].enable) {
          int diff = Math.abs(right_arrows_pos[i].y - 10);
          if (diff <= speed) {
            System.out.println("MARVELOUS!!!");
            right_marvelous = 10;
            right_arrows_pos[i].visible = false;
            break;
          } else if (diff <= speed * 3) {
            System.out.println("PERFECT!!");
            right_perfect = 10;
            right_arrows_pos[i].visible = false;
            break;
          } else if (diff <= speed * 5) {
            System.out.println("GREAT!");
            right_great = 10;
            right_arrows_pos[i].visible = false;
            break;
          } else if (diff <= speed * 7) {
            System.out.println("GOOD!");
            right_good = 10;
            right_arrows_pos[i].visible = false;
            break;
          }
        }
      }
      break;
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
      if (left_arrows_pos[i].visible) {
        if (left_arrows_pos[i].enable) {
          buffer.drawImage(left_arrow_img, left_arrows_pos[i].x, left_arrows_pos[i].y, this);
        } else {
          buffer.drawImage(left_arrow_n_img, left_arrows_pos[i].x, left_arrows_pos[i].y, this);
        }
      }
    }
    for (int i = 0; i < down_arrows_pos.length; i++) {
      if (down_arrows_pos[i].enable) {
        buffer.drawImage(down_arrow_img, down_arrows_pos[i].x, down_arrows_pos[i].y, this);
      } else {
        buffer.drawImage(down_arrow_n_img, down_arrows_pos[i].x, down_arrows_pos[i].y, this);
      }
    }
    for (int i = 0; i < up_arrows_pos.length; i++) {
      if (up_arrows_pos[i].enable) {
        buffer.drawImage(up_arrow_img, up_arrows_pos[i].x, up_arrows_pos[i].y, this);
      } else {
        buffer.drawImage(up_arrow_n_img, up_arrows_pos[i].x, up_arrows_pos[i].y, this);
      }
    }
    for (int i = 0; i < right_arrows_pos.length; i++) {
      if (right_arrows_pos[i].enable) {
        buffer.drawImage(right_arrow_img, right_arrows_pos[i].x, right_arrows_pos[i].y, this);
      } else {
        buffer.drawImage(right_arrow_n_img, right_arrows_pos[i].x, right_arrows_pos[i].y, this);
      }
    }

    buffer.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
    buffer.setColor(Color.RED);
    if (left_miss > 0) buffer.drawString("Miss...", 20, 120);
    if (down_miss > 0) buffer.drawString("Miss...", 168, 120);
    if (up_miss > 0) buffer.drawString("Miss...", 316, 120);
    if (right_miss > 0) buffer.drawString("Miss...", 464, 120);
    buffer.setColor(Color.CYAN);
    if (left_good > 0) buffer.drawString("GOOD", 20, 120);
    if (down_good > 0) buffer.drawString("GOOD", 168, 120);
    if (up_good > 0) buffer.drawString("GOOD", 316, 120);
    if (right_good > 0) buffer.drawString("GOOD", 464, 120);
    buffer.setColor(Color.GREEN);
    if (left_great > 0) buffer.drawString("GREAT!", 20, 120);
    if (down_great > 0) buffer.drawString("GREAT!", 168, 120);
    if (up_great > 0) buffer.drawString("GREAT!", 316, 120);
    if (right_great > 0) buffer.drawString("GREAT!", 464, 120);
    buffer.setColor(Color.YELLOW);
    if (left_perfect > 0) buffer.drawString("PERFECT!!", 20, 120);
    if (down_perfect > 0) buffer.drawString("PERFECT!!", 168, 120);
    if (up_perfect > 0) buffer.drawString("PERFECT!!", 316, 120);
    if (right_perfect > 0) buffer.drawString("PERFECT!!", 464, 120);
    buffer.setColor(Color.ORANGE);
    if (left_marvelous > 0) buffer.drawString("MARVELOUS!!", 20, 120);
    if (down_marvelous > 0) buffer.drawString("MARVELOUS!!", 168, 120);
    if (up_marvelous > 0) buffer.drawString("MARVELOUS!!", 316, 120);
    if (right_marvelous > 0) buffer.drawString("MARVELOUS!!", 464, 120);
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

class Game extends JPanel implements Runnable, KeyListener {
	int frame_count;
  int speed = 5;
	JLabel frame_count_label;
  GameGUI gGUI;
  
	Game(String fileName) {
    this.setBackground(Color.BLACK);
    gGUI = new GameGUI(fileName);
    this.add(gGUI);
    this.setVisible(true);
    this.addKeyListener(this);
    this.setFocusable(true);
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

  public void keyPressed(KeyEvent e) {
    int keyCode = e.getKeyCode();
    switch (keyCode) {
    case KeyEvent.VK_SHIFT :
      System.out.println("SHIFT PRESSED");
      break;
    case KeyEvent.VK_LEFT :
      gGUI.Pressed(0);
      break;
    case KeyEvent.VK_DOWN :
      gGUI.Pressed(1);
      break;
    case KeyEvent.VK_UP :
      gGUI.Pressed(2);
      break;
    case KeyEvent.VK_RIGHT :
      gGUI.Pressed(3);
      break;
    }
  }

  public void keyReleased(KeyEvent e){
  }
  public void keyTyped(KeyEvent e) {
  }
}
