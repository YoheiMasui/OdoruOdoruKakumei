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
	int maxFrame;
	int[] pressed;
	
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

	int HP;
	boolean gameOver;
	boolean gameClear;
  public int left_pressed, down_pressed, up_pressed, right_pressed;

	String fileName;

	Clip line;
	FloatControl control;
	float volume;
	int scoring;
	Menu menu;
	float vol;
  GameGUI(String fileName, Menu menu, float vol) {
		this.vol = vol;
		this.fileName = fileName;
    this.setPreferredSize(new Dimension(800, 700));
    score = new Score(fileName);
		HP = 30;
		pressed = new int[5];
		this.menu = menu;
		volume = 1.0f;
		scoring = 0;
		gameOver = false;
		gameClear = false;
    left_arrows_pos = new Position[score.left_arrows.length];
    down_arrows_pos = new Position[score.down_arrows.length];
    up_arrows_pos = new Position[score.up_arrows.length];
    right_arrows_pos = new Position[score.right_arrows.length];
    for (int i = 0; i < left_arrows_pos.length; i++) {
			int f = Integer.valueOf(score.left_arrows[i]);
      left_arrows_pos[i] = new Position(20, 10 + speed * (300 + f));
			maxFrame = Math.max(maxFrame, f);
    }
    for (int i = 0; i < down_arrows_pos.length; i++) {
			int f = Integer.valueOf(score.down_arrows[i]);
      down_arrows_pos[i] = new Position(168, 10 + speed * (300 + f));
			maxFrame = Math.max(maxFrame, f);
    }
    for (int i = 0; i < up_arrows_pos.length; i++) {
			int f = Integer.valueOf(score.up_arrows[i]);
      up_arrows_pos[i] = new Position(316, 10 + speed * (300 + f));
			maxFrame = Math.max(maxFrame, f);
    }
    for (int i = 0; i < right_arrows_pos.length; i++) {
			int f = Integer.valueOf(score.right_arrows[i]);
      right_arrows_pos[i] = new Position(464, 10 + speed * (300 + f));
			maxFrame = Math.max(maxFrame, f);
    }

    left_miss = down_miss = up_miss = right_miss = 0;

		try{
			File mp3File = new File("./music/" + fileName + "/" + fileName + ".wav");
			
			AudioFormat format = AudioSystem.getAudioFileFormat(mp3File).getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			line = (Clip)AudioSystem.getLine(info);
			line.open(AudioSystem.getAudioInputStream(mp3File));
			control = (FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN);
			System.out.println("WAV Started.");
			
		} catch(Exception e) { 
			e.printStackTrace();
		}
  }

	void Play() {
		// play mp3
		control.setValue((float)Math.log10(vol) * 20);
		line.start();
	}
	
  void setValues(int frame_count) {
    this.frame_count = frame_count;
	}

  public void Update() {
		float v = Math.min(volume, vol);
		if (v <= 0) v = 0;
		control.setValue((float)Math.log10(v) * 20);
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
		
    if (HP <= 0) {
			gameOver = true;
		}
		if (gameOver) {
			volume -= 0.01;
			if (volume <= 0) volume = 0;
			return;
		}
		if (maxFrame < frame_count && line.getMicrosecondLength() == line.getMicrosecondPosition()) {
			gameClear = true;
			return;
		}
    for (int i = 0; i < left_arrows_pos.length; i++) {
      if (left_arrows_pos[i].visible && left_arrows_pos[i].enable && left_arrows_pos[i].y < 10 - 4 * speed) {
        left_arrows_pos[i].enable = false;
        left_miss = 10;
				HP --;
				pressed[4] ++;
      }
      if (left_arrows_pos[i].y > -200) {
        left_arrows_pos[i].y -= speed;
      }
    }
    for (int i = 0; i < down_arrows_pos.length; i++) {
      if (down_arrows_pos[i].visible && down_arrows_pos[i].enable && down_arrows_pos[i].y < 10 - 4 * speed) {
        down_arrows_pos[i].enable = false;
        down_miss = 10;
				HP --;
				pressed[4] ++;
      }
      if (down_arrows_pos[i].y > -200) {
        down_arrows_pos[i].y -= speed;
      }
    }
    for (int i = 0; i < up_arrows_pos.length; i++) {
      if (up_arrows_pos[i].visible && up_arrows_pos[i].enable && up_arrows_pos[i].y < 10 - 4 * speed) {
        up_arrows_pos[i].enable = false;
        up_miss = 10;
				HP --;
				pressed[4] ++;
      }
      if (up_arrows_pos[i].y > -200) {
        up_arrows_pos[i].y -= speed;
      }
    }
    for (int i = 0; i < right_arrows_pos.length; i++) {
      if (right_arrows_pos[i].visible && right_arrows_pos[i].enable && right_arrows_pos[i].y < 10 - 4 * speed) {
        right_arrows_pos[i].enable = false;
        right_miss = 10;
				HP --;
				pressed[4] ++;
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
            left_marvelous = 10;
            left_arrows_pos[i].visible = false;
						HP ++;
						pressed[0] ++;
            break;
          } else if (diff <= speed * 3) {
            left_perfect = 10;
            left_arrows_pos[i].visible = false;
						pressed[1] ++;
            break;
          } else if (diff <= speed * 5) {
            left_great = 10;
            left_arrows_pos[i].visible = false;
						pressed[2] ++;
            break;
          } else if (diff <= speed * 7) {
            left_good = 10;
            left_arrows_pos[i].visible = false;
						pressed[3] ++;
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
            down_marvelous = 10;
            down_arrows_pos[i].visible = false;
						HP ++;
						pressed[0] ++;
            break;
          } else if (diff <= speed * 3) {
            down_perfect = 10;
            down_arrows_pos[i].visible = false;
						pressed[1] ++;
            break;
          } else if (diff <= speed * 5) {
            down_great = 10;
            down_arrows_pos[i].visible = false;
						pressed[2] ++;
            break;
          } else if (diff <= speed * 7) {
            down_good = 10;
            down_arrows_pos[i].visible = false;
						pressed[3] ++;
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
            up_marvelous = 10;
            up_arrows_pos[i].visible = false;
						HP ++;
						pressed[0] ++;
            break;
          } else if (diff <= speed * 3) {
            up_perfect = 10;
            up_arrows_pos[i].visible = false;
						pressed[1] ++;
            break;
          } else if (diff <= speed * 5) {
            up_great = 10;
            up_arrows_pos[i].visible = false;
						pressed[2] ++;
            break;
          } else if (diff <= speed * 7) {
            up_good = 10;
            up_arrows_pos[i].visible = false;
						pressed[3] ++;
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
            right_marvelous = 10;
            right_arrows_pos[i].visible = false;
						HP ++;
						pressed[0] ++;
            break;
          } else if (diff <= speed * 3) {
            right_perfect = 10;
            right_arrows_pos[i].visible = false;
						pressed[1] ++;
            break;
          } else if (diff <= speed * 5) {
            right_great = 10;
            right_arrows_pos[i].visible = false;
						pressed[2] ++;
            break;
          } else if (diff <= speed * 7) {
            right_good = 10;
            right_arrows_pos[i].visible = false;
						pressed[3] ++;
            break;
          }
        }
      }
      break;
		case 4:
			if (gameOver || gameClear) {
				// To ugly...
				menu.setVisible(false);
				new Thread(new Menu()).start();
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
		if (gameOver) {
			buffer.setColor(Color.RED);
			buffer.setFont(new Font("TimesRoman", Font.PLAIN, 70));
			buffer.drawString("GAME OVER", 200, 300);
			g.drawImage(back, 0, 0, this);
			return;
		}
		if (gameClear) {
			buffer.setColor(Color.ORANGE);
			buffer.setFont(new Font("TimesRoman", Font.PLAIN, 70));
			buffer.drawString("GAME CLEAR", 200, 100);
			buffer.setFont(new Font("TimesRoman", Font.PLAIN, 50));
			buffer.drawString("MARVELOUS: " + pressed[0], 200, 200);
			buffer.setColor(Color.YELLOW);
			buffer.drawString("PERFECT  : " + pressed[1], 200, 250);
			buffer.setColor(Color.GREEN);
			buffer.drawString("GREAT    : " + pressed[2], 200, 300);
			buffer.setColor(Color.CYAN);
			buffer.drawString("GOOD     : " + pressed[3], 200, 350);
			buffer.setColor(Color.RED);
			buffer.drawString("MISS     : " + pressed[4], 200, 400);
			g.drawImage(back, 0, 0, this);
			return;
		}
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
			if (down_arrows_pos[i].visible) {
				if (down_arrows_pos[i].enable) {
					buffer.drawImage(down_arrow_img, down_arrows_pos[i].x, down_arrows_pos[i].y, this);
				} else {
					buffer.drawImage(down_arrow_n_img, down_arrows_pos[i].x, down_arrows_pos[i].y, this);
				}
			}
		}
    for (int i = 0; i < up_arrows_pos.length; i++) {
			if (up_arrows_pos[i].visible) {
				if (up_arrows_pos[i].enable) {
					buffer.drawImage(up_arrow_img, up_arrows_pos[i].x, up_arrows_pos[i].y, this);
				} else {
					buffer.drawImage(up_arrow_n_img, up_arrows_pos[i].x, up_arrows_pos[i].y, this);
				}
			}
    }
    for (int i = 0; i < right_arrows_pos.length; i++) {
			if (right_arrows_pos[i].visible) {
				if (right_arrows_pos[i].enable) {
					buffer.drawImage(right_arrow_img, right_arrows_pos[i].x, right_arrows_pos[i].y, this);
				} else {
					buffer.drawImage(right_arrow_n_img, right_arrows_pos[i].x, right_arrows_pos[i].y, this);
				}
			}
    }

    buffer.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
    buffer.setColor(Color.RED);
    if (left_miss > 0) {
			buffer.drawString("Miss...", 20, 120);
		}
    if (down_miss > 0) {
			buffer.drawString("Miss...", 168, 120);
		}
    if (up_miss > 0) {
			buffer.drawString("Miss...", 316, 120);
		}
		if (right_miss > 0) {
			buffer.drawString("Miss...", 464, 120);
		}
		buffer.setColor(Color.CYAN);
    if (left_good > 0) {
			buffer.drawString("GOOD", 20, 120);
			scoring += 1;
		}
    if (down_good > 0) {
			buffer.drawString("GOOD", 168, 120);
			scoring += 1;
		}
		if (up_good > 0) {
			buffer.drawString("GOOD", 316, 120);
			scoring += 1;
		}
		if (right_good > 0) {
			buffer.drawString("GOOD", 464, 120);
			scoring += 1;
		}
		buffer.setColor(Color.GREEN);
    if (left_great > 0) {
			buffer.drawString("GREAT!", 20, 120);
			scoring += 2;
		}
    if (down_great > 0) {
			buffer.drawString("GREAT!", 168, 120);
			scoring += 2;
		}
    if (up_great > 0) {
			buffer.drawString("GREAT!", 316, 120);
			scoring += 2;
		}
    if (right_great > 0) {
			buffer.drawString("GREAT!", 464, 120);
			scoring += 2;
		}
    buffer.setColor(Color.YELLOW);
    if (left_perfect > 0) {
			buffer.drawString("PERFECT!!", 20, 120);
			scoring += 3;
		}
    if (down_perfect > 0) {
			buffer.drawString("PERFECT!!", 168, 120);
			scoring += 3;
		}
    if (up_perfect > 0) {
			buffer.drawString("PERFECT!!", 316, 120);
			scoring += 3;
		}
    if (right_perfect > 0) {
			buffer.drawString("PERFECT!!", 464, 120);
			scoring += 3;
		}
    buffer.setColor(Color.ORANGE);
    if (left_marvelous > 0) {
			buffer.drawString("MARVELOUS!!", 20, 120);
			scoring += 4;
		}
		if (down_marvelous > 0) {
			buffer.drawString("MARVELOUS!!", 168, 120);
			scoring += 4;
		}
		if (up_marvelous > 0) {
			buffer.drawString("MARVELOUS!!", 316, 120);
			scoring += 4;
		}
		if (right_marvelous > 0) {
			buffer.drawString("MARVELOUS!!", 464, 120);
			scoring += 4;
		}
		if (HP > 30) HP = 30;
		// SCOCRE
		// BEGIN //////////////////////////////////////////////////////
		buffer.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		buffer.setColor(Color.GREEN);
		buffer.drawString("Score", 630, 30);
		buffer.setColor(Color.WHITE);
		buffer.drawString(String.format("%1$09d", scoring), 630, 70);
    // END ///////////////////////////////////////////////////////
    // DEBUG
    // BEGIN //////////////////////////////////////////////////////
    buffer.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
    buffer.setColor(Color.BLUE);
    buffer.drawString("DEBUG", 620, 530);
    buffer.setFont(new Font("TimesRoman", Font.PLAIN, 15)); 
    buffer.setColor(Color.WHITE);
    buffer.drawString("frame_count: " + frame_count, 620, 560);
    // END ///////////////////////////////////////////////////////
		// HP
    // BEGIN //////////////////////////////////////////////////////
		buffer.setColor(Color.RED);
		for (int i = 0; i < Math.min(8, HP); i++) {
			buffer.fillRect(10 + 22 * i, 610, 15, 30);
		}
		if (HP > 8) {
			buffer.setColor(Color.GREEN);
			for (int i = 8; i < HP; i++) {
				buffer.fillRect(10 + 22 * i, 610, 15, 30);
			}
		}
    // END ///////////////////////////////////////////////////////
    g.drawImage(back, 0, 0, this);
  }
}

class Game extends JPanel implements Runnable, KeyListener {
	int frame_count;
  int speed = 5;
	JLabel frame_count_label;
  GameGUI gGUI;

	float[] volumes = { 1.0f, 1.0f };
	int[] KeyVals = { 37, 40, 38, 39 };
	
	Game(String fileName, Menu menu) {
		frame_count = -300;
    this.setBackground(Color.BLACK);
		try {
			FileReader fr = new FileReader("./ook.cfg");
			BufferedReader br = new BufferedReader(fr);
			for (int i = 0; i < 2; i++) {
				volumes[i] = Integer.valueOf(br.readLine()) / 100.0f;
			}
			for (int i = 0; i < 4; i++) {
				KeyVals[i] = Integer.valueOf(br.readLine());
			}
		} catch (Exception ex) { }
		gGUI = new GameGUI(fileName, menu, volumes[0]);
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
			if (frame_count == 0) gGUI.Play();
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
			requestFocusInWindow();
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
		for (int i = 0; i < 4; i++) {
			if (keyCode == KeyVals[i]) {
				gGUI.Pressed(i);
				return ;
			}
		}
		if (keyCode == KeyEvent.VK_ENTER) {			
			gGUI.Pressed(4);
    }
		
  }

  public void keyReleased(KeyEvent e){
  }
  public void keyTyped(KeyEvent e) {
  }
}
