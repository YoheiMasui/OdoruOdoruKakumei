import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.awt.event.*;


class CursorObservable extends Observable {
	int cursor = 0;
	public int getValue() {
		return cursor;
	}
	public void setValue(int d) {
		cursor += d;
		if (cursor < 0) cursor = 0;
		if (cursor > 3) cursor = 3;
		setChanged();
		notifyObservers();
	}
}
