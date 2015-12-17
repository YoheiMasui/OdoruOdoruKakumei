// 2015/12/17

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;

class ScoreMaker extends JFrame {
	int lineNum = 0;
	JTable table;
	final int Add1s = 1;
	final int Add10s = 2;
	final int DeleteLine = 3;
	final int Preview = 4;
	DefaultTableModel tableModel;
	ScoreMaker() {
		this.setTitle("Score Maker");
		this.setSize(400, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		String[][] tabledata ={
			{"0","","","",""},
			{"15","","","",""},
			{"30","","","",""},
			{"45","","","",""}
		};

		String[] columnNames = {"f","left","down","up","right"};
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);
		//JTable table = new JTable(tabledata, columnNames);
		/* table = new JTable(new DefaultTableModel(tabledata, columnNames) {
			 public boolean isCellEditable(int row, int columen) {
			 return false;
			 }
			 }); */

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
	class ButtonClickListener implements ActionListener {
		int type;
		ButtonClickListener(int type, JTable table) {
			this.type = type;
		}
		public void actionPerformed(ActionEvent e) {

			switch(type) {
			case Add1s:  
				for (int i = 0; i < 4; i++) {
					String[] new_column = { Integer.toString(15 * lineNum++), "","","",""};
					tableModel.addRow(new_column);
				}
				break;
			case Add10s:
				for (int i = 0; i < 40; i++) {
					String[] new_column = { Integer.toString(15 * lineNum++), "","","",""};
					tableModel.addRow(new_column);
				}

				break;
			case DeleteLine:
				int selectedLine = table.getSelectedRow();
				if (selectedLine >= 0) {					
					tableModel.removeRow(selectedLine);
					lineNum--;
					for (int i = selectedLine; i < lineNum; i++){
						tableModel.setValueAt(15 * i, i, 0);
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
