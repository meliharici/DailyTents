import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class DailyTents {
	protected static JFrame frame;
	private static Board board;
	protected static final int  WIDTH = 800;
	protected static final int  HEIGHT = 700;
	private static final int DEFAULT_SIZE = 8;
	private static final String GAME_TITLE = "Daily Tents";
	public static void main(String[] args) {
		frame = new JFrame();
		frame.setSize(WIDTH, HEIGHT);
		frame.setTitle(GAME_TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board = new Board(DEFAULT_SIZE);
		DailyTentsController controller = new DailyTentsController(frame, board);
		frame.add(controller.getView());
		ImageIcon img = new ImageIcon(DailyTents.class.getResource("/resources/gameIcon.png"));
		frame.setIconImage(img.getImage());
		frame.setVisible(true);
	}
}
