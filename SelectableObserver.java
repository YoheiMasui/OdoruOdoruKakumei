import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.awt.event.*;


class SelectableObserver extends JLabel implements Observer {
	private CursorObservable cursorObservable;
	private int index;
	public SelectableObserver(CursorObservable c, String name, int index) {
		cursorObservable = c;
		this.index = index;
		cursorObservable.addObserver(this);
		this.setForeground(Color.GRAY);
		
		this.setFont(new Font(MENU_INDEX.Font, Font.PLAIN, 32));
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
