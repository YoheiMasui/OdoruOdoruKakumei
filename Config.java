import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.awt.event.*;


class ConfigPanel extends JPanel {
	ConfigPanel() {
		this.setBackground(Color.BLACK);
		JPanel volumePanel = new JPanel();
		volumePanel.setLayout(new BoxLayout(volumePanel, BoxLayout.PAGE_AXIS));
		//this.add(volumePanel, BorderLayout.NORTH);
		JLabel volumeLabel = new JLabel("Volume");
		volumeLabel.setFont(new Font(MENU_INDEX.Font, Font.PLAIN, 36));
		volumeLabel.setForeground(Color.MAGENTA);
		volumeLabel.setAlignmentX(0.0f);
		volumePanel.add(volumeLabel);
		volumePanel.setOpaque(false);
		/* music volume */
		JPanel music_volumePanel = new JPanel();
		music_volumePanel.setLayout(new GridLayout(1, 2));
		JLabel music_volumeLabel = new JLabel("Music");
		music_volumeLabel.setHorizontalAlignment(JLabel.CENTER);
		music_volumeLabel.setFont(new Font(MENU_INDEX.Font, Font.PLAIN, 28));
		music_volumeLabel.setForeground(Color.GREEN);
		music_volumePanel.add(music_volumeLabel);
		JSlider music_volumeSlider = new JSlider();
		music_volumeSlider.
			setLabelTable(music_volumeSlider.createStandardLabels(20));
		music_volumeSlider.setPreferredSize(new Dimension(300, 20));
		music_volumeSlider.setPaintLabels(true);
		music_volumeSlider.setOpaque(false);
		music_volumeSlider.setFont(new Font(MENU_INDEX.Font, Font.PLAIN, 15));
		music_volumeSlider.setForeground(Color.WHITE);
		music_volumePanel.add(music_volumeSlider);
		music_volumePanel.setOpaque(false);
		volumePanel.add(music_volumePanel);
		/* se volume */
		JPanel se_volumePanel = new JPanel();
		se_volumePanel.setLayout(new GridLayout(1, 2));
		JLabel se_volumeLabel = new JLabel("SE");
		se_volumeLabel.setHorizontalAlignment(JLabel.CENTER);
		se_volumeLabel.setFont(new Font(MENU_INDEX.Font, Font.PLAIN, 28));
		se_volumeLabel.setForeground(Color.GREEN);
		se_volumePanel.add(se_volumeLabel);
		JSlider se_volumeSlider = new JSlider();
		se_volumeSlider.
			setLabelTable(se_volumeSlider.createStandardLabels(20));
		se_volumeSlider.setPreferredSize(new Dimension(300, 100));
		se_volumeSlider.setPaintLabels(true);
		se_volumeSlider.setOpaque(false);
		se_volumeSlider.setFont(new Font(MENU_INDEX.Font, Font.PLAIN, 15));
		se_volumeSlider.setForeground(Color.WHITE);
		se_volumePanel.add(se_volumeSlider);
		se_volumePanel.setOpaque(false);
		volumePanel.add(se_volumePanel);
		JPanel keyConfigPanel = new JPanel();
		JPanel keyConfigDetailPanel = new JPanel();
		keyConfigDetailPanel.setLayout(new GridLayout(2, 2));
		JPanel[] keyConfigIndividualPanel = new JPanel[4];
		JLabel[] keyConfigIndividualPanel_Arrow = new JLabel[4];
		JButton[] keyConfigIndividualPanel_Key = new JButton[4];
		final String[] arrows = { "UP", "RIGHT", "DOWN", "LEFT" };
		for (int i = 0; i < 4; i++) {
			keyConfigIndividualPanel[i] = new JPanel();
			keyConfigIndividualPanel[i].setLayout(new GridLayout(1, 2));
			keyConfigIndividualPanel[i].setOpaque(false);
			keyConfigIndividualPanel_Arrow[i] = new JLabel();
			keyConfigIndividualPanel_Arrow[i].setFont(
																								new Font(MENU_INDEX.Font, Font.PLAIN, 32));
			keyConfigIndividualPanel_Arrow[i].setForeground(Color.WHITE);
			keyConfigIndividualPanel_Arrow[i].setText(arrows[i]);
			keyConfigIndividualPanel_Key[i] = new JButton();
			 
			keyConfigIndividualPanel[i].add(keyConfigIndividualPanel_Arrow[i]);
			keyConfigIndividualPanel[i].add(keyConfigIndividualPanel_Key[i]);
			keyConfigDetailPanel.add(keyConfigIndividualPanel[i]);
		}
		JLabel keyConfigLabel = new JLabel("KeyConfig", JLabel.CENTER);
		keyConfigLabel.setFont(new Font(MENU_INDEX.Font, Font.PLAIN, 36));
		keyConfigLabel.setForeground(Color.MAGENTA);
		keyConfigPanel.setLayout(new BorderLayout());
		keyConfigPanel.add(keyConfigLabel, BorderLayout.NORTH);
		keyConfigDetailPanel.setOpaque(false);
		keyConfigPanel.add(keyConfigDetailPanel, BorderLayout.SOUTH);
		keyConfigPanel.setOpaque(false);
		//	this.add(keyConfigPanel, BorderLayout.SOUTH);
	}
}
