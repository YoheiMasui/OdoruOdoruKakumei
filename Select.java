import java.awt.*;
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

class Select extends JPanel implements Runnable {
	SelectGUI sGUI;
	ArrayList<MusicData> musics;
	Select() {
		this.setBackground(Color.BLACK);
		sGUI = new SelectGUI();
		this.setVisible(true);
		this.setFocusable(true);
		musics = new ArrayList<MusicData>();

		File dir = new File("./music");
		File[] files = dir.listFiles();
		try {
			for (int i = 0; i < files.length; i++) {
				String name = files[i].getName();
				BufferedReader br = new BufferedReader(new FileReader(files[i] + "/" + name + ".dat"));
				String title = br.readLine();
				br.close();
				musics.add(new MusicData(name, title));
				System.out.println(name);
			}
		} catch (IOException e) { }
		System.out.println(musics.size());
	}
	
	public void run() {
	  long error = 0;
		int fps = 60;
		long idealSleep = (1000 << 16) / fps;
		long oldTime;
		long newTime = System.currentTimeMillis() << 16;
		while (true) {
			oldTime = newTime;
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
    }
	}

	public void paintComponent(Graphics g) {
		Dimension size = getSize();
		Image back = createImage(size.width, size.height);
		Graphics buffer = back.getGraphics();
		super.paintComponent(buffer);
		
		buffer.setColor(Color.WHITE);
		buffer.setFont(new Font("TimesRoman", Font.BOLD, 50)); 
    buffer.drawString(musics.get(0).title, 50, 70);

		buffer.setColor(Color.GRAY);
		buffer.setFont(new Font("TimesRoman", Font.BOLD, 40)); 
    buffer.drawString(musics.get(1).title, 130, 120);
		buffer.drawString(musics.get(2).title, 210, 170);
		buffer.drawString(musics.get(3).title, 290, 220);
		buffer.drawString(musics.get(4).title, 370, 270);
		
		g.drawImage(back, 0, 0, this);
	}
}
