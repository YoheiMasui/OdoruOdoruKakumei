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
