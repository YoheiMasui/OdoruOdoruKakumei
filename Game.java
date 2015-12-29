import java.awt.*;
import javax.swing.*;
import java.util.*;

class Game extends JPanel {
	Game() {
		this.setBackground(Color.BLACK);
		this.setLayout(null);
		JLabel up_arrow = new JLabel(new ImageIcon("./img/u.png"));
		JLabel right_arrow = new JLabel(new ImageIcon("./img/r.png"));
		JLabel down_arrow = new JLabel(new ImageIcon("./img/d.png"));
		JLabel left_arrow = new JLabel(new ImageIcon("./img/l.png"));
 
		up_arrow.setBounds(20, 10, 128, 128);
		right_arrow.setBounds(158, 10, 128, 128);
		down_arrow.setBounds(296, 10, 128, 128);
		left_arrow.setBounds(424, 10, 128, 128);

		this.add(up_arrow);
		this.add(right_arrow);
		this.add(down_arrow);
		this.add(left_arrow);
	}
}
