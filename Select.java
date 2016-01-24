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
	int pointer;
	int file_num;
	boolean flag;
	JFrame mainFrame;
	Select() {
		this.setBackground(Color.BLACK);
		this.setVisible(true);
		this.setFocusable(true);
		this.addKeyListener(this);
		System.out.println(mainFrame);
		musics = new ArrayList<MusicData>();
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
		buffer.setFont(new Font("TimesRoman", Font.BOLD, 50)); 
    buffer.drawString(musics.get(pointer).title, 50, 70);

		buffer.setColor(Color.GRAY);
		buffer.setFont(new Font("TimesRoman", Font.BOLD, 40)); 
		for (int i = 0; i < Math.min(4, file_num - pointer - 1); i++) {
			buffer.drawString(musics.get(pointer + i + 1).title, 130 + 80 * i, 120 + 50 * i);
		}
		g.drawImage(back, 0, 0, this);
		requestFocusInWindow();
	}
	public void keyPressed(KeyEvent e){
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_ENTER:
			DataServer.setSelectedFileName(musics.get(pointer).name);
			flag = false;
			System.out.println("Selected");
			System.out.println(mainFrame);
			mainFrame = (JFrame)SwingUtilities.getAncestorOfClass(Menu.class, this);
			System.out.println(mainFrame);
			mainFrame.setSize(800, 700);
			Game gamePanel = new Game(DataServer.getSelectedFileName());
			mainFrame.getContentPane().add(gamePanel);
			gamePanel.setVisible(true);
			new Thread(gamePanel).start();
			break;
		case KeyEvent.VK_DOWN:
			if (pointer < file_num - 1) {
				pointer ++;
			}
			break;
		case KeyEvent.VK_UP:
			if (pointer > 0) {
				pointer --;
			}
			break;
		}		
		repaint();
  }

  public void keyReleased(KeyEvent e){
  }

  public void keyTyped(KeyEvent e){
	
  }
}
