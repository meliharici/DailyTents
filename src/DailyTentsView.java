import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DailyTentsView extends JPanel {
	private JButton start, restart, exit;
	protected JButton[][] boardCells;
	protected JPanel menuPanel;
	protected JPanel gamePanel;
	private JLabel timeLabel;
	private int currentDimension = 0;
	private int sec = 0;
	private int min = 0;
	private static final String START_BUTTON = "	Play  ";
	private static final String RESTART_BUTTON = " Restart ";
	private static final String EXIT_BUTTON = " Exit ";

	public DailyTentsView(JFrame frame, int dimension, final DailyTentsController controller) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		initializeMenuButtons();
		createMenuPanel();
		gamePanel = new JPanel();
		assignFeaturesToButtons(frame, controller);
		add(menuPanel);
		add(gamePanel);
	}

	private void assignFeaturesToButtons(JFrame frame, final DailyTentsController controller) {
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] dimensions = { 8, 12, 16, 20 };
				int dimension = 0;
				dimension = determinedDimension(frame, dimensions);
				createGamePanel(controller, dimension);
			}
		});
		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restartGamePanel(controller);
			}
		});
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	
	private int determinedDimension(JFrame frame, Object[] dimensions) {
		int dimension;
		int input = JOptionPane.showOptionDialog(frame, "Choose a dimension:", "Dimension",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, dimensions, dimensions[0]);
		switch (input) {
			case 0: dimension = 8;  break;
			case 1: dimension = 12; break;
			case 2: dimension = 16; break;
			case 3: dimension = 20; break;
			default: dimension = 8; break;
		}
		return dimension;
	}

	private void initializeMenuButtons() {
		start = new JButton(START_BUTTON);
		restart = new JButton(RESTART_BUTTON);
		exit = new JButton(EXIT_BUTTON);
		start.setBackground(Color.CYAN);
		restart.setBackground(Color.CYAN);
		exit.setBackground(Color.CYAN);
		restart.setEnabled(false);
	}

	private void createMenuPanel() {
		menuPanel = new JPanel();
		menuPanel.setMaximumSize(new Dimension(1900, 200));
		menuPanel.setBackground(Color.ORANGE);
		menuPanel.add(start);
		menuPanel.add(restart);
		menuPanel.add(exit);
		timeLabel = new JLabel();
		menuPanel.add(timeLabel);
		ImageIcon img = new ImageIcon(DailyTents.class.getResource("/resources/clock.png"));
		timeLabel.setIcon(img);
	}

	protected void updateButton(JButton button, String buttonType) {
		if (buttonType.equals("empty")) {
			button.setText(" ");
			button.setBackground(Color.WHITE);
		}else if (buttonType.equals("tree")) {
			try {
				ImageIcon icon = createImageIcon("/resources/tree.png", "tree icon");
				button.setBackground(Color.WHITE);
				button.setIcon(icon);
			}catch (Exception ex) {
				ex.printStackTrace();
			}
		}else if (buttonType.equals("tent")) {
			try {
				ImageIcon icon = createImageIcon("/resources/nature.png", "tent icon");
				button.setBackground(Color.WHITE);
				button.setIcon(icon);

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}else {
			button.setText(buttonType);
			button.setBackground(Color.WHITE);
		}
	}

	protected ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = DailyTentsView.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		}else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	private void createGamePanel(final DailyTentsController controller, int dimension) {
		gamePanel.setBackground(Color.CYAN);
		gamePanel.removeAll();
		currentDimension = dimension;
		gamePanel.setLayout(new GridLayout(dimension + 1, dimension + 1));
		boardCells = new JButton[dimension + 1][dimension + 1];
		controller.startGame(dimension);
		controller.startTime = System.currentTimeMillis();
		countTime("start");
		putTentIntoButton(boardCells, dimension, controller);
		gamePanel.setVisible(true);
		start.setEnabled(false);
		restart.setEnabled(true);
		DailyTents.frame.setVisible(true);
	}
	
	protected void countTime(String order) {
		if (order.equals("stop")) {
			timeLabel.setVisible(false);
		}else {
			Timer timer = new Timer(1000, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					sec++;
					if (sec == 59) {
						sec = 0;
						min++;
						timeLabel.setText(min + ":" + sec);
					}
					timeLabel.setText(min + ":" + sec);
				}
			});
			timer.start();
		}
	}
	
	private void restartGamePanel(final DailyTentsController controller) {
		gamePanel.removeAll();
		gamePanel.setLayout(new GridLayout(currentDimension + 1, currentDimension + 1));
		boardCells = new JButton[currentDimension + 1][currentDimension + 1];
		controller.startGame(currentDimension);
		controller.startTime = System.currentTimeMillis();
		sec = 0;
		min = 0;
		putTentIntoButton(boardCells, currentDimension, controller);
		DailyTents.frame.setVisible(true);
	}
	
	private void putTentIntoButton(JButton[][] boardCells, int dimension, DailyTentsController controller) {
		for (int i = 0; i < dimension + 1; i++) {
			for (int j = 0; j < dimension + 1; j++) {
				JButton button = boardCells[i][j];
				final int row = i;
				final int col = j;
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						controller.addTent(row, col, button);
						controller.checkIfGameFinished();
					}
				});
			}
		}
	}
}
