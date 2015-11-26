/* 編集テスト */

import java.awt.*;
import javax.swing.*;


class Menu extends JFrame {
	Menu() {
		this.setSize(480, 620);
		this.setTitle("Odoru Odoru Kakumei");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}

class Main {
	public static void main(String argv[]) {
		new Menu();
	}
}
