import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.awt.event.*;


class Menu extends JFrame implements Runnable {
	int color_red, color_green;
	int v = 1;
	JLabel titleLogo;
	JPanel menuPanel, configPanel;
	Game gamePanel;
	CursorObservable cursorObservable;
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
		titleLogo.setForeground(vivgyorColor());
		
	}

	Color vivgyorColor() {
		if (color_red == 0) v = 1; 
		if (color_red == 255) v = -1;
		color_red += v;
		color_green += v;
	
		return new Color(color_red, color_green, 255);
	}

	Menu() {
		menuPanel = new JPanel();
		gamePanel = new Game();
		configPanel = new ConfigPanel();
		cursorObservable = new CursorObservable();
		this.setSize(620, 480);
		this.setTitle("Odoru Odoru Kakumei");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);

		menuPanel.setBackground(Color.BLACK);
		titleLogo = new JLabel("Odoru Odoru Kakumei",JLabel.CENTER);
		titleLogo.setFont(new Font(MENU_INDEX.Font, Font.ITALIC, 52));
		menuPanel.add(titleLogo, BorderLayout.NORTH);
		JPanel Selectable = new JPanel();
		Selectable.setLayout(new BoxLayout(Selectable, BoxLayout.PAGE_AXIS));
		//Selectable.setLayout(new FlowLayout());
		SelectableObserver menu_single = 
			new SelectableObserver(cursorObservable, "Single", 0);
		SelectableObserver menu_network = 
			new SelectableObserver(cursorObservable, "Network", 1);
		SelectableObserver menu_config = 
			new SelectableObserver(cursorObservable, "Config", 2);
		SelectableObserver menu_exit = 
			new SelectableObserver(cursorObservable, "Exit", 3);
		Selectable.add(menu_single);
		Selectable.add(menu_network);
		Selectable.add(menu_config);
		Selectable.add(menu_exit);
		cursorObservable.setValue(0);
		Selectable.setOpaque(false);
		menuPanel.add(Selectable, BorderLayout.CENTER);
		{
			configPanel.setBackground(Color.BLACK);
			JPanel volumePanel = new JPanel();
			volumePanel.setLayout(new BoxLayout(volumePanel, BoxLayout.PAGE_AXIS));
			configPanel.add(volumePanel, BorderLayout.NORTH);
			JLabel volumeLabel = new JLabel("Volume");
			volumeLabel.setFont(new Font(MENU_INDEX.Font, Font.PLAIN, 36));
			volumeLabel.setForeground(Color.MAGENTA);
			volumeLabel.setAlignmentX(0.0f);
			volumePanel.add(volumeLabel);
			volumePanel.setOpaque(false);
			/* music volume */
			JPanel music_volumePanel = new JPanel();
			music_volumePanel.setLayout(new GridLayout(1, 2));
			JLabel music_volumeLabel = new JLabel("Music");
			music_volumeLabel.setHorizontalAlignment(JLabel.CENTER);
			music_volumeLabel.setFont(new Font(MENU_INDEX.Font, Font.PLAIN, 28));
			music_volumeLabel.setForeground(Color.GREEN);
			music_volumePanel.add(music_volumeLabel);
			JSlider music_volumeSlider = new JSlider();
			music_volumeSlider.
				setLabelTable(music_volumeSlider.createStandardLabels(20));
			music_volumeSlider.setPreferredSize(new Dimension(300, 20));
			music_volumeSlider.setPaintLabels(true);
			music_volumeSlider.setOpaque(false);
			music_volumeSlider.setFont(new Font(MENU_INDEX.Font, Font.PLAIN, 15));
			music_volumeSlider.setForeground(Color.WHITE);
			music_volumePanel.add(music_volumeSlider);
			music_volumePanel.setOpaque(false);
			volumePanel.add(music_volumePanel);
			/* se volume */
			JPanel se_volumePanel = new JPanel();
			se_volumePanel.setLayout(new GridLayout(1, 2));
			JLabel se_volumeLabel = new JLabel("SE");
			se_volumeLabel.setHorizontalAlignment(JLabel.CENTER);
			se_volumeLabel.setFont(new Font(MENU_INDEX.Font, Font.PLAIN, 28));
			se_volumeLabel.setForeground(Color.GREEN);
			se_volumePanel.add(se_volumeLabel);
			JSlider se_volumeSlider = new JSlider();
			se_volumeSlider.
				setLabelTable(se_volumeSlider.createStandardLabels(20));
			se_volumeSlider.setPreferredSize(new Dimension(300, 100));
			se_volumeSlider.setPaintLabels(true);
			se_volumeSlider.setOpaque(false);
			se_volumeSlider.setFont(new Font(MENU_INDEX.Font, Font.PLAIN, 15));
			se_volumeSlider.setForeground(Color.WHITE);
			se_volumePanel.add(se_volumeSlider);
			se_volumePanel.setOpaque(false);
			volumePanel.add(se_volumePanel);
			JPanel keyConfigPanel = new JPanel();
			JPanel keyConfigDetailPanel = new JPanel();
			keyConfigDetailPanel.setLayout(new GridLayout(2, 2));
			JPanel[] keyConfigIndividualPanel = new JPanel[4];
			JLabel[] keyConfigIndividualPanel_Arrow = new JLabel[4];
			JButton[] keyConfigIndividualPanel_Key = new JButton[4];
			final String[] arrows = { "UP", "RIGHT", "DOWN", "LEFT" };
			for (int i = 0; i < 4; i++) {
				keyConfigIndividualPanel[i] = new JPanel();
				keyConfigIndividualPanel[i].setLayout(new GridLayout(1, 2));
				keyConfigIndividualPanel[i].setOpaque(false);
				keyConfigIndividualPanel_Arrow[i] = new JLabel();
				keyConfigIndividualPanel_Arrow[i].setFont(
																									new Font(MENU_INDEX.Font, Font.PLAIN, 32));
				keyConfigIndividualPanel_Arrow[i].setForeground(Color.WHITE);
				keyConfigIndividualPanel_Arrow[i].setText(arrows[i]);
				keyConfigIndividualPanel_Key[i] = new JButton();
			 
				keyConfigIndividualPanel[i].add(keyConfigIndividualPanel_Arrow[i]);
				keyConfigIndividualPanel[i].add(keyConfigIndividualPanel_Key[i]);
				keyConfigDetailPanel.add(keyConfigIndividualPanel[i]);
			}
			JLabel keyConfigLabel = new JLabel("KeyConfig", JLabel.CENTER);
			keyConfigLabel.setFont(new Font(MENU_INDEX.Font, Font.PLAIN, 36));
			keyConfigLabel.setForeground(Color.MAGENTA);
			keyConfigPanel.setLayout(new BorderLayout());
			keyConfigPanel.add(keyConfigLabel, BorderLayout.NORTH);
			keyConfigDetailPanel.setOpaque(false);
			keyConfigPanel.add(keyConfigDetailPanel, BorderLayout.SOUTH);
			keyConfigPanel.setOpaque(false);
			configPanel.add(keyConfigPanel, BorderLayout.SOUTH);
		}

		menuPanel.addKeyListener(new menuKeyPressed());
		menuPanel.setFocusable(true);
		//this.add(configPanel);
		this.add(menuPanel);
		//configPanel.setVisible(true);
		menuPanel.setVisible(true);

		this.setVisible(true);
		/* full screen
		GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().
			getDefaultScreenDevice();
		device.setFullScreenWindow(this);
		*/
	}

	class menuKeyPressed implements KeyListener {
		public void keyPressed(KeyEvent e){
			int keyCode = e.getKeyCode();
			switch(keyCode) {
			case KeyEvent.VK_UP :
				cursorObservable.setValue(-1);
				break;
			case KeyEvent.VK_DOWN :
				cursorObservable.setValue(+1);
				break;
			case KeyEvent.VK_ENTER :
				int idx = cursorObservable.getValue();
				switch (idx) {
				case MENU_INDEX.Single :
					setSize(800, 700);
					menuPanel.setVisible(false);
					add(gamePanel);
					gamePanel.setVisible(true);
					new Thread(gamePanel).start();
					break;
				case MENU_INDEX.Network :
					break;
				case MENU_INDEX.Config :
					//getContentPane().removeAll();
					menuPanel.setVisible(false);
					add(configPanel);
					configPanel.setVisible(true);
					break;
				case MENU_INDEX.Exit :
					System.exit(0);
					break;
				}
			}
		}
		public void keyReleased(KeyEvent e){
		}

		public void keyTyped(KeyEvent e){
		}
	}


}
