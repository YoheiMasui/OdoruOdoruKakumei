import java.awt.*;
import javax.swing.*;

class Menu extends JFrame {
	double colorIdx = 0;
	JLabel titleLogo;
	void Start() {
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
		titleLogo.setText("<html><span style='font-size:36pt; color:" + vivgyorColor() + ";'><I>Odoru Odoru Kakumei</I></span></html>");
	}

	String vivgyorColor() {
		final String colors[] =  {
			"#E60012", "#DE6641", "#F5B090", "#C7000B", 
			"#F39800", "#E8AC51", "#FCD7A1", "#D28300",
			"#FFF100", "#F2E55C", "#C7000B", "#DFD000",
			"#009944", "#39A869", "#A5D4AD", "#00873C",
			"#0068B7", "#4784BF", "#A3BCE2", "#005AA0",
			"#1D2088", "#5D5099", "#A59ACA", "#181878",
			"#920783", "#A55B9A", "#CFA7CD", "#800073", 
		};
		if (colorIdx > 27) {
			colorIdx = 0;
		}
		colorIdx += 0.1;
		return colors[(int)colorIdx];
	}

	Menu() {
		this.setSize(710, 620);
		this.setTitle("Odoru Odoru Kakumei");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.getContentPane().setBackground(Color.BLACK);
		titleLogo = titleLogo = new JLabel("<html><span style='font-size:36pt; color:#000000;'><I>Odoru Odoru Kakumei</I></span></html>",JLabel.CENTER);
		this.add(titleLogo, BorderLayout.NORTH);
		this.setVisible(true);

		Start();
	}
}

class Main {
	public static void main(String argv[]) {
		new Menu();
	}
}
