import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

class MusicData {
	String name;
	String title;
	String imgPath;
	
	MusicData(String name, String title) {
		this.name = name;
		this.title = title;
	}
}

class Select extends JPanel implements Runnable, KeyListener {
	ArrayList<MusicData> musics;
	ArrayList<Image> imgs;
	int pointer;
	int file_num;
	boolean flag;
	JFrame mainFrame;
  JFrame menu;
	Select(JFrame menu) {
		this.setBackground(Color.BLACK);
		this.setVisible(true);
		this.setFocusable(true);
		this.addKeyListener(this);
		System.out.println(mainFrame);
		this.menu = menu;
		musics = new ArrayList<MusicData>();
		imgs = new ArrayList<Image>();
		flag = true;
		File dir = new File("./music");
		File[] files = dir.listFiles();
		file_num = files.length;
		try {
			for (int i = 0; i < file_num; i++) {
				String name = files[i].getName();
				BufferedReader br = new BufferedReader(new FileReader(files[i] + "/" + name + ".dat"));
				String title = br.readLine();
				br.close();
				musics.add(new MusicData(name, title));
				imgs.add(Toolkit.getDefaultToolkit().getImage(files[i] + "/" + name + ".png"));
				System.out.println(name);
			}
		} catch (IOException e) { }
		System.out.println(musics.size());
		pointer = 0;
	}
	
	public void run() {
	  repaint();
	}

	public void paintComponent(Graphics g) {
		Dimension size = getSize();
		Image back = createImage(size.width, size.height);
		Graphics buffer = back.getGraphics();
		super.paintComponent(buffer);
		buffer.setColor(Color.WHITE);
		buffer.drawImage(imgs.get(pointer), 10, 100, null);

		if (musics.get(pointer).title.length() > 15) {
			buffer.setFont(new Font("TimesRoman", Font.BOLD, 34)); 
		} else {
			buffer.setFont(new Font("TimesRoman", Font.BOLD, 50)); 
		}
    buffer.drawString(musics.get(pointer).title, 50, 70);

		buffer.setColor(Color.LIGHT_GRAY);
		buffer.setFont(new Font("TimesRoman", Font.BOLD, 40)); 
		for (int i = 0; i < 4; i++) {
			buffer.drawString(musics.get((pointer + i + 1) % file_num).title, 130 + 80 * i, 120 + 50 * i);
		}
		g.drawImage(back, 0, 0, this);
		requestFocusInWindow();
		repaint(); //...?? This is necessary
	}
	public void keyPressed(KeyEvent e){
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_ENTER:
			DataServer.setSelectedFileName(musics.get(pointer).name);
			flag = false;
			mainFrame = (JFrame)SwingUtilities.getAncestorOfClass(Menu.class, this);
			mainFrame.setSize(800, 700);
			Game gamePanel = new Game(DataServer.getSelectedFileName(), menu);
			mainFrame.getContentPane().add(gamePanel);
			gamePanel.setVisible(true);
			this.setVisible(false);
			new Thread(gamePanel).start();
			break;
		case KeyEvent.VK_DOWN:
			pointer = (pointer + 1) % file_num;
			break;
		case KeyEvent.VK_UP:
		  pointer --;
			if (pointer < 0) pointer = file_num - 1;
			break;
		}		
		repaint();
  }

  public void keyReleased(KeyEvent e){
  }

  public void keyTyped(KeyEvent e){
	
  }
}
