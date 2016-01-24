import java.awt.*;
import javax.swing.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.io.*;
import java.awt.datatransfer.*;
import java.util.*;
import javax.sound.sampled.*;

class Main {
	public static void main(String args[]) {
		new scoreMaker();
	}
}

class scoreMaker extends JFrame {
	boolean start_flag;
	int frame_count;
	ArrayList<Integer> left_frames, down_frames, up_frames, right_frames;
	File file;
	
	scoreMaker() {
		this.setTitle("Score Maker");
		this.setSize(400, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setFocusable(true);
		start_flag = false;
		frame_count = 0;
		JLabel path = new JLabel();
		JButton button = new JButton("Start");
		left_frames = new ArrayList<Integer>();
		down_frames = new ArrayList<Integer>();
		up_frames = new ArrayList<Integer>();
		right_frames = new ArrayList<Integer>();

		button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (start_flag == false) {
						start_flag = true;
						button.setText("Stop");
						new Thread(new Runnable() {
								public void run() {
									try {
									AudioFormat format = AudioSystem.getAudioFileFormat(file).getFormat();
									DataLine.Info info = new DataLine.Info(Clip.class, format);
									Clip line = (Clip)AudioSystem.getLine(info);
									line.open(AudioSystem.getAudioInputStream(file));
									line.start();
									} catch (Exception ex) { }
									long error = 0;
									int fps = 60;
									long idealSleep = (1000 << 16) / fps;
									long oldTime;
									long newTime = System.currentTimeMillis() << 16;
									while (true) {
										oldTime = newTime;
										repaint();
										newTime = System.currentTimeMillis() << 16;
										frame_count++;
										long sleepTime = idealSleep - (newTime - oldTime) - error;
										if (sleepTime < 0x20000) sleepTime = 0x2000;
										oldTime = newTime;
										try {
											Thread.sleep(sleepTime >> 16);
										} catch (InterruptedException e) { }
										newTime = System.currentTimeMillis() << 16;
										error = newTime - oldTime - sleepTime;
										requestFocusInWindow();
									}
								}
							}).start();
					} else {
						try {
							start_flag = false;
							button.setText("Start");
							File save = new File(path.getText() + "/" + "scoreMaker2.score");
							PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(save)));
							for (int i = 0; i < left_frames.size(); i++) {
								pw.print(left_frames.get(i) + ",");
							}
							pw.println("");
							for (int i = 0; i < down_frames.size(); i++) {
								pw.print(down_frames.get(i) + ",");
							}
							pw.println("");
							for (int i = 0; i < up_frames.size(); i++) {
								pw.print(up_frames.get(i) + ",");
							}
							pw.println("");
							for (int i = 0; i < right_frames.size(); i++) {
								pw.print(right_frames.get(i) + ",");
							}
							pw.println("");
							pw.close();
						} catch (Exception ex) { }
					}
				}
			});
		
		this.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					int keyCode = e.getKeyCode();
					if (start_flag) {
						switch (keyCode) {
						case KeyEvent.VK_LEFT :
							left_frames.add(frame_count);
							break;
							
						case KeyEvent.VK_DOWN :
							down_frames.add(frame_count);
							
							break;
						case KeyEvent.VK_UP :
							up_frames.add(frame_count);
							break;
						case KeyEvent.VK_RIGHT :
							right_frames.add(frame_count);
							break;
						}
					}
				}
					
				public void keyReleased(KeyEvent e) {
				}
				public void keyTyped(KeyEvent e) {
				}
			});
		this.add(path, BorderLayout.NORTH);
		this.add(button, BorderLayout.SOUTH);
		this.setDropTarget(new DropTarget(this, new DropTargetAdapter() {
				public void drop(DropTargetDropEvent dtde) {
					Transferable t = dtde.getTransferable();
					dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
					try {
						file = ((ArrayList<File>)t.getTransferData(DataFlavor.javaFileListFlavor)).get(0);
						path.setText(file.getParent().toString());

					} catch (Exception e) { }
				}
			}));
		this.setVisible(true);
	}
}
