import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.awt.event.*;


class Menu extends JFrame implements Runnable {
	int color_red, color_green;
	int v = 1;
	JLabel titleLogo;
	JPanel menuPanel;
	Select selectPanel;
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
		menuPanel.addKeyListener(new menuKeyPressed());
		menuPanel.setFocusable(true);
		this.add(menuPanel);
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
          selectPanel = new Select(Menu.this, null, null);
					//setSize(800, 700);
					menuPanel.setVisible(false);
					getContentPane().add(selectPanel);
					add(selectPanel);
					selectPanel.setVisible(true);
						//setSize(800, 700);
						//gamePanel = new Game(DataServer.getSelectedFileName());
						//getContentPane().add(gamePanel);
						//gamePanel.setVisible(true);
																
					break;
				case MENU_INDEX.Network :
					setVisible(false);
					new Network();
					break;
				case MENU_INDEX.Config :
					JFrame configFrame = new JFrame();
					configFrame.setSize(640, 480);
					ConfigPanel configPanel = new ConfigPanel(configFrame);
					setVisible(false);
					configFrame.add(configPanel);
					configFrame.setVisible(true);
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
