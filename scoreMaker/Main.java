import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.*;
import java.io.*;

class ScoreMaker extends JFrame {
	int lineNum = 0;
	JTable table = new JTable();
	final int Add1s = 1;
	final int Add10s = 2;
	final int DeleteLine = 3;
	final int Preview = 4;
	ArrayList<int[]> status = new ArrayList<int[]>();
	DefaultTableModel tableModel;
	String name;

	ScoreMaker() {
		this.setTitle("Score Maker");
		this.setSize(400, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menubar = new JMenuBar();

		JMenu menu1 = new JMenu("File");

		menubar.add(menu1);

		JMenuItem menuitem1 = new JMenuItem("open");
		JMenuItem menuitem2 = new JMenuItem("save as");

		menu1.add(menuitem1);
		menu1.add(menuitem2);

		setJMenuBar(menubar);

		menuitem1.addActionListener(new fileOpenItem());
		menuitem2.addActionListener(new itemClicked(this));
		
		String[] columnNames = {"f","left","down","up","right"};
		tableModel = new DefaultTableModel(columnNames, 0) {
				public boolean isCellEditable(int row, int columen) {
					return false;
				}
			};
		table = new JTable(tableModel);
		//JTable table = new JTable(tabledata, columnNames);
		/*JTable table = new JTable(new DefaultTableModel(tabledata, columnNames) {
			public boolean isCellEditable(int row, int columen) {
			return false;
			}
			});*/

	
		table.addMouseListener(new tableClicked());
		JScrollPane sp = new JScrollPane(table);
		sp.setPreferredSize(new Dimension(380,600));
		JPanel p = new JPanel();
		p.add(sp);
		this.add(p, BorderLayout.CENTER);
		//ボタン部分

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 4));
		JButton buttonAdd1s = new JButton("add 1s");
		JButton buttonAdd10s = new JButton("add 10s");
		JButton buttonDeleteLine = new JButton("delete Line");
		JButton buttonPreview = new JButton("preview");
		buttonPanel.add(buttonAdd1s);
		buttonPanel.add(buttonAdd10s);
		buttonPanel.add(buttonDeleteLine);
		buttonPanel.add(buttonPreview);

		buttonAdd1s.addActionListener(new ButtonClickListener(Add1s, table));
		buttonAdd10s.addActionListener(new ButtonClickListener(Add10s, table));
		buttonDeleteLine.addActionListener(new ButtonClickListener(DeleteLine, table));
		buttonPreview.addActionListener(new ButtonClickListener(Preview, table));

		this.add(buttonPanel, BorderLayout.SOUTH);

	
		this.setVisible(true);	
	}
	class fileOpenItem implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();

			int selected = fileChooser.showOpenDialog(ScoreMaker.this);
			if (selected == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				name = file.getName().replace(".score", "");
				System.out.println(name);
				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					String[] left_arrows = br.readLine().split(",", 0);
					String[] down_arrows = br.readLine().split(",", 0);
					String[] up_arrows = br.readLine().split(",", 0);
					String[] right_arrows = br.readLine().split(",", 0);
					for (int i = 0; i < lineNum; i++) {
						tableModel.removeRow(0);
					}
					lineNum = 0;

					int line_max = 0;
					for (int i = 0; i < left_arrows.length; i++) {
						line_max = Math.max(line_max, Integer.valueOf(left_arrows[i]));
					}
					for (int i = 0; i < down_arrows.length; i++) {
						line_max = Math.max(line_max, Integer.valueOf(down_arrows[i]));	 
					}
					for (int i = 0; i < up_arrows.length; i++) {
						line_max = Math.max(line_max, Integer.valueOf(up_arrows[i]));
					}
					for (int i = 0; i < right_arrows.length; i++) {
						line_max = Math.max(line_max, Integer.valueOf(right_arrows[i]));
					}
					
					for (int i = 0; i < line_max; i++) {
						String[] new_column = { Integer.toString(lineNum++), "","","",""};
						status.add(new int[5]);
						tableModel.addRow(new_column);
					}
					for (int i = 0; i < left_arrows.length; i++) {
						int r = Integer.valueOf(left_arrows[i]);
						status.get(r)[1] = 1;
						table.setValueAt(status.get(r)[1] == 0 ? "" : status.get(r)[1], r, 1);
					}
					for (int i = 0; i < down_arrows.length; i++) {
						int r = Integer.valueOf(down_arrows[i]);
						status.get(r)[2] = 1;
						table.setValueAt(status.get(r)[2] == 0 ? "" : status.get(r)[2], r, 2);						
					}
					for (int i = 0; i < up_arrows.length; i++) {
						int r = Integer.valueOf(up_arrows[i]);
						status.get(r)[3] = 1;
						table.setValueAt(status.get(r)[3] == 0 ? "" : status.get(r)[3], r, 3);						
					}
					for (int i = 0; i < right_arrows.length; i++) {
						int r = Integer.valueOf(right_arrows[i]);
						status.get(r)[4] = 1;
						table.setValueAt(status.get(r)[4] == 0 ? "" : status.get(r)[4], r, 4);						
					}
				} catch (Exception ex) { }
			}
		}
	}
	
	class itemClicked implements ActionListener {
		JFrame f;
		itemClicked(JFrame f) {
			this.f = f;
		}
		public void actionPerformed(ActionEvent e) {
			JFileChooser filechooser = new JFileChooser();
			filechooser.setDialogTitle("名前を付けて保存");

			int selected = filechooser.showSaveDialog(f);
			if (selected == JFileChooser.APPROVE_OPTION){
				File file = filechooser.getSelectedFile();
			
				try{
					if (file.createNewFile()){
						FileWriter filewriter = new FileWriter(file);
						System.out.println("ファイルの作成に成功しました");

						for (int i = 1; i <= 4; i++) {
							for (int j = 0; j < lineNum; j++) {
								if (status.get(j)[i] > 0) {
									filewriter.write(Integer.toString(15 * j));
									if (j != lineNum - 1) filewriter.write(",");
								}
							}
							filewriter.write("\n");
						}
						
						filewriter.close();
					}else{
						System.out.println("ファイルの作成に失敗しました");
					}
				}catch(IOException ex){
					System.out.println(e);
				}
			}
		}
	}
	
	class tableClicked implements MouseListener {
		int prev_r = -1;
		int prev_c = -1;
		public void mouseClicked(MouseEvent e) {
			int r = table.getSelectedRow();
			int c = table.getSelectedColumn();
			if (c == 0) return;
			if (prev_r == prev_c) {
				status.get(r)[c]++;
				status.get(r)[c] %= 3;
				table.setValueAt(status.get(r)[c] == 0 ? "" : status.get(r)[c], r, c);
			}
		}
		public void mouseEntered(MouseEvent e) { }
		public void mouseExited(MouseEvent e) { }
		public void mousePressed(MouseEvent e) { }
		public void mouseReleased(MouseEvent e) { }
	}
		 

	class ButtonClickListener implements ActionListener {
		int type;
		ButtonClickListener(int type, JTable table) {
			this.type = type;
		}
		public void actionPerformed(ActionEvent e) {

			switch(type) {
			case Add1s:  
				for (int i = 0; i < 60; i++) {
					String[] new_column = { Integer.toString(lineNum++), "","","",""};
					tableModel.addRow(new_column);
					status.add(new int[5]);
					
				}
				break;
			case Add10s:
				for (int i = 0; i < 600; i++) {
					String[] new_column = { Integer.toString(lineNum++), "","","",""};
					tableModel.addRow(new_column);
					status.add(new int[5]);
				}

				break;
			case DeleteLine:
				int selectedLine = table.getSelectedRow();
				if (selectedLine >= 0) {					
					tableModel.removeRow(selectedLine);
					lineNum--;
					for (int i = selectedLine; i < lineNum; i++){
						tableModel.setValueAt(i, i, 0);
					}
				}
				break;
			case Preview:
				File file = new File("../music/" + name + "/preview.tmp");
			
				try{
					FileWriter filewriter = new FileWriter(file);
					for (int i = 1; i <= 4; i++) {
						for (int j = 0; j < lineNum; j++) {
							if (status.get(j)[i] > 0) {
								filewriter.write(Integer.toString(j));
								filewriter.write(",");
							}
						}
						filewriter.write("\n");
					}
					
					filewriter.close();
				}catch(IOException ex){
					System.out.println(e);
				}
				Game game = new Game(name, null);
				JFrame prevFrame = new JFrame();
				prevFrame.setSize(800, 700);
				prevFrame.add(game);
				prevFrame.setVisible(true);
				new Thread(game).start();
				break;
			}
		}
	}
}



class Main {
	public static void main(String argv[]) {
		new ScoreMaker();
	}
	
}
