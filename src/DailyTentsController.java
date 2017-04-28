import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DailyTentsController {
	private Board board;
	private DailyTentsView view;
	private int tentCounter = 0;
	protected long startTime, estimatedTime;
	private static final String TREE_KEYWORD = "tree";
	private static final String TENT_KEYWORD = "tent";
	private static final String EMPTY_KEYWORD = "empty";

	public DailyTentsController(JFrame frame, Board board) {
		this.board = board;
		this.view = new DailyTentsView(frame, board.getSize(), this);
	}

	protected void startGame(int dimension) {
		board = new Board(dimension);
		createRandomBoard(board);
		for (int i = 0; i < dimension + 1; i++) {
			for (int j = 0; j < dimension + 1; j++) {
				JButton bt = new JButton();
				if (board.SolutionBoard[i][j].equals(board.TREE_SYMBOL)) {
					view.updateButton(bt, TREE_KEYWORD);
				} else if (!board.UserBoard[i][j].equals(board.TREE_SYMBOL)) {
					String str = board.UserBoard[i][j];
					String number = str.substring(2, 3);
					view.updateButton(bt, number);
				} else {
					view.updateButton(bt, EMPTY_KEYWORD);
				}
				view.gamePanel.add(bt);
				view.boardCells[i][j] = bt;
			}
		}
	}

	protected void addTent(int row, int column, JButton button) {
		if (board.SolutionBoard[row][column].equals(board.TENT_SYMBOL)) {
			view.updateButton(button, TENT_KEYWORD);
			tentCounter++;
		}
	}

	private void createRandomBoard(Board board) {
		board.initializeBoard();
		board.createRandomTents();
		board.putTreesNextToTents();
		board.fillNumberCells();
		board.createUserBoard();
	}

	protected DailyTentsView getView() {
		return view;
	}

	protected void checkIfGameFinished() {
		estimatedTime = (System.currentTimeMillis() - startTime) / 1000;
		if (tentCounter == board.getNumberOfElements() / 2) {
			view.countTime("stop");
			playSound("resources/applause.wav");
			JOptionPane.showMessageDialog(null, "You win ! Total time: " + estimatedTime + "sec.", "Congratulation !",
					JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
	}

	private void playSound(String filename) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File(filename)));
			clip.start();
		} catch (Exception exc) {
			exc.printStackTrace(System.out);
		}
	}
}
