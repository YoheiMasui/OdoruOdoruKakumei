import java.awt.*;
import javax.swing.*;
import java.util.*;

class Game extends JPanel implements Runnable{
	int frame_count;
  int speed = 5;
	JLabel frame_count_label;
  Score score;
  ArrayList<JLabel> left_arrows;
  ArrayList<JLabel> down_arrows;
  ArrayList<JLabel> up_arrows;
  ArrayList<JLabel> right_arrows;
  
	Game(String fileName) {
		this.setBackground(Color.BLACK);
		this.setLayout(null);
    JLabel left_arrow = new JLabel(new ImageIcon("./img/l.png"));
    JLabel down_arrow = new JLabel(new ImageIcon("./img/d.png"));
		JLabel up_arrow = new JLabel(new ImageIcon("./img/u.png"));
		JLabel right_arrow = new JLabel(new ImageIcon("./img/r.png"));
		JLabel debug_label = new JLabel("DEBUG");
		frame_count_label = new JLabel("frame_count_label");
		
		debug_label.setForeground(Color.BLUE);
		frame_count_label.setForeground(Color.WHITE);
		debug_label.setFont(new Font("Serif", Font.PLAIN, 30));
		left_arrow.setBounds(20, 10, 128, 128);
		down_arrow.setBounds(158, 10, 128, 128);
    up_arrow.setBounds(296, 10, 128, 128);
		right_arrow.setBounds(424, 10, 128, 128);
    
		debug_label.setBounds(610, 10, 128, 30);
		frame_count_label.setBounds(610, 50, 128, 30);
		this.add(up_arrow);
		this.add(right_arrow);
		this.add(down_arrow);
		this.add(left_arrow);
		this.add(debug_label);
		this.add(frame_count_label);
		frame_count = 0;
    score = new Score(fileName);
    Placement();
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
	
	void Update() {
		frame_count++;
    java.util.List<ArrayList<JLabel>> all_arrows = Arrays.asList(left_arrows, down_arrows, up_arrows, right_arrows);
    for (ArrayList<JLabel> arrows : all_arrows) {
      for (JLabel arrow : arrows) {
        Rectangle r = arrow.getBounds();
        r.y -= speed;
        arrow.setBounds(r);
      }
    }
		//repaint();
		/* disp */
		frame_count_label.setText("frame_count: " + frame_count);
	}

  void Placement() {
    left_arrows = new ArrayList<JLabel>();
    down_arrows = new ArrayList<JLabel>();
    up_arrows = new ArrayList<JLabel>();
    right_arrows = new ArrayList<JLabel>();
    for (String left_arrow : score.left_arrows) {
      JLabel new_arrow = new JLabel(new ImageIcon("./img/l.png"));
      int delay = Integer.valueOf(left_arrow);
      new_arrow.setBounds(20, 10 + speed * delay, 128, 128);
      left_arrows.add(new_arrow);
      this.add(new_arrow);
    }
    for (String down_arrow : score.down_arrows) {
      JLabel new_arrow = new JLabel(new ImageIcon("./img/d.png"));
      int delay = Integer.valueOf(down_arrow);
      new_arrow.setBounds(158, 10 + speed * delay, 128, 128);
      down_arrows.add(new_arrow);
      this.add(new_arrow);
    }
    for (String up_arrow : score.up_arrows) {
      JLabel new_arrow = new JLabel(new ImageIcon("./img/u.png"));
      int delay = Integer.valueOf(up_arrow);
      new_arrow.setBounds(296, 10 + speed * delay, 128, 128);
      up_arrows.add(new_arrow);
      this.add(new_arrow);
    }
    for (String right_arrow : score.right_arrows) {
      JLabel new_arrow = new JLabel(new ImageIcon("./img/r.png"));
      int delay = Integer.valueOf(right_arrow);
      new_arrow.setBounds(424, 10 + speed * delay, 128, 128);
      right_arrows.add(new_arrow);
      this.add(new_arrow);
    }
  }
}
