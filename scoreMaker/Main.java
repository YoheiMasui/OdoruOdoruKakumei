import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
class ScoreMaker extends JFrame {
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

		//JTable table = new JTable(tabledata, columnNames);
		JTable table = new JTable(new DefaultTableModel(tabledata, columnNames) {
				public boolean isCellEditable(int row, int columen) {
					return false;
				}
			});

		JScrollPane sp = new JScrollPane(table);
		sp.setPreferredSize(new Dimension(380,600));
		JPanel p = new JPanel();
		p.add(sp);
		this.add(p, BorderLayout.CENTER);
		this.setVisible(true);	
	}
	
}

class Main {
	public static void main(String argv[]) {
		new ScoreMaker();
	}
}
