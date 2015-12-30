import java.awt.*;
import javax.swing.*;
import java.util.*;

class Game extends JPanel implements Runnable{
	int frame_count;
	JLabel frame_count_label;
	
	Game() {
		this.setBackground(Color.BLACK);
		this.setLayout(null);
		JLabel up_arrow = new JLabel(new ImageIcon("./img/u.png"));
		JLabel right_arrow = new JLabel(new ImageIcon("./img/r.png"));
		JLabel down_arrow = new JLabel(new ImageIcon("./img/d.png"));
		JLabel left_arrow = new JLabel(new ImageIcon("./img/l.png"));
		JLabel debug_label = new JLabel("DEBUG");
		frame_count_label = new JLabel("frame_count_label");
		
		debug_label.setForeground(Color.BLUE);
		frame_count_label.setForeground(Color.WHITE);
		debug_label.setFont(new Font("Serif", Font.PLAIN, 30));
		up_arrow.setBounds(20, 10, 128, 128);
		right_arrow.setBounds(158, 10, 128, 128);
		down_arrow.setBounds(296, 10, 128, 128);
		left_arrow.setBounds(424, 10, 128, 128);
		debug_label.setBounds(610, 10, 128, 30);
		frame_count_label.setBounds(610, 50, 128, 30);
		this.add(up_arrow);
		this.add(right_arrow);
		this.add(down_arrow);
		this.add(left_arrow);
		this.add(debug_label);
		this.add(frame_count_label);
		frame_count = 0;
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
			} catch (Exception e) { }
			newTime = System.currentTimeMillis() << 16;
			error = newTime - oldTime - sleepTime;
		}	
	}
	
	void Update() {
		frame_count++;
		System.out.println(frame_count);
		repaint();
		/* disp */
		frame_count_label.setText("frame_count: " + frame_count);
	}
}
