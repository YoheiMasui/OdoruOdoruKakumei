import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.awt.event.*;

class MENU_INDEX {
	static final public int Single = 0;
	static final public int Network = 1;
	static final public int Config = 2;
	static final public int Exit = 3;
}

class CursorObservable extends Observable {
	int cursor = 0;
	public int getValue() {
		return cursor;
	}
	public void setValue(int d) {
		cursor += d;
		if (cursor < 0) cursor = 0;
		if (cursor > 3) cursor = 3;
		setChanged();
		notifyObservers();
	}
}

class SelectableObserver extends JLabel implements Observer {
	private CursorObservable cursorObservable;
	private int index;
	public SelectableObserver(CursorObservable c, String name, int index) {
		cursorObservable = c;
		this.index = index;
		cursorObservable.addObserver(this);
		this.setForeground(Color.GRAY);
		
		this.setFont(new Font("PakTypeNaqsh", Font.PLAIN, 32));
		this.setPreferredSize(new Dimension(300, 100));
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setText(name);
		this.setAlignmentX(0.15f);
	}
	public void update(Observable o, Object arg) {
		int cursor = cursorObservable.getValue();
		if (index == cursor) {
			this.setForeground(Color.RED);
		} else {
			this.setForeground(Color.GRAY);
		}
	}
}

class Menu extends JFrame implements KeyListener {
	int color_red, color_green;
	int v = 1;
	JLabel titleLogo;
	CursorObservable cursorObservable;
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
		cursorObservable = new CursorObservable();
		this.setSize(710, 620);
		this.setTitle("Odoru Odoru Kakumei");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		/* ロゴ */
		this.getContentPane().setBackground(Color.BLACK);
		titleLogo = new JLabel("Odoru Odoru Kakumei",JLabel.CENTER);
		titleLogo.setFont(new Font("PakTypeNaqsh", Font.PLAIN, 52));
		this.add(titleLogo, BorderLayout.NORTH);
		/* メニューの要素 */
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
		this.add(Selectable, BorderLayout.CENTER);

		this.setVisible(true);
		this.addKeyListener(this);
		/* フルスクリーン
			 GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().
			 getDefaultScreenDevice();
			 device.setFullScreenWindow(this);
		*/
		Start();
	}

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
				break;
			case MENU_INDEX.Network :
				break;
			case MENU_INDEX.Config :
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

class Main {
	public static void main(String argv[]) {
		new Menu();
	}
}
