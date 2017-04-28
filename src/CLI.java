

import java.util.Scanner;

public class CLI {
	private static Board board;
	private static final String TENT_SYMBOL = "| T |";
	private static final String INVALID_ENTRY_ERROR = "Invalid entry. Try again.";
	private static final String WRONG_DIMENSION_ERROR = "Please enter 8,12,16 or 20.";
	private static final String WRONG_INPUT_ERROR = "Wrong input. Please enter 1,2,3 or 4";
	private static final String ALREADY_HAS_TENT_ERROR = "You've already put a tent there.";

	protected void run(Scanner scanner, int input) {
		if (input == 1) {
			createBoardWithGivenDimension(scanner);
		} else if (input == 2) {
			receiveTentCoordinates(scanner);
		} else if (input == 3) {
			System.out.println("Exiting...");
			System.exit(0);
		} else if (input == 4) {
			createNewBoardWithSameDimension();
		} else {
			System.out.println(WRONG_INPUT_ERROR);
		}
	}
	
	protected static void printHelpMenu() {
		String newLine = "\n";
		String helpMessage = "Press 1 to enter the dimension of the board. " 			  			   + newLine + 
							 "Press 2 to enter coordinates. " + newLine + "Press 3 to exit the game. " + newLine + 
							 "Press 4 to restart the game. ";
		System.out.println(helpMessage);
	}

	private void receiveTentCoordinates(Scanner scanner) {
		System.out.println("Please enter the coordinates. Enter for x:");
		int x = scanner.nextInt();
		if (x > board.getSize() || x <= 0) {
			System.out.println(INVALID_ENTRY_ERROR);
			run(scanner, 2);
		} else {
			System.out.println("Enter for y:");
			int y = scanner.nextInt();
			if (y > board.getSize() || y <= 0) {
				System.out.println(INVALID_ENTRY_ERROR);
				run(scanner, 2);
			} else {
				if(board.UserBoard[x][y].equals(TENT_SYMBOL)){
					System.out.println(ALREADY_HAS_TENT_ERROR);
					run(scanner, 2);
				} else{
					putTentToGivenLocation(x, y, scanner);
					if (isGameFinished()) {
						System.out.println("You Win.");
						System.exit(0);
					}
				}
			}
		}
	}

	private void createBoardWithGivenDimension(Scanner scanner) {
		int dimension = 0;
		System.out.println("Enter the dimension of the board.");
		dimension = scanner.nextInt();
		if (dimension != 8 && dimension != 12 && dimension != 16 && dimension != 20) {
			System.out.println(WRONG_DIMENSION_ERROR);
			run(scanner, 1);
		}else {
			board = new Board(dimension);
			createRandomBoard(board);
		
		}
	}

	private void createNewBoardWithSameDimension() {
		int newBoardDimension = board.getSize();
		board.deleteBoard(board.UserBoard);
		board = new Board(newBoardDimension);
		createRandomBoard(board);
		System.out.println("New board succesfully created.");
		//board.printBoard();
	}

	protected void createRandomBoard(Board board) {
		board.initializeBoard();
		board.createRandomTents();
		board.putTreesNextToTents();
		board.fillNumberCells();
		board.createUserBoard();
	}

	private void putTentToGivenLocation(int x, int y, Scanner scanner) {
		if (board.SolutionBoard[x][y].equals(TENT_SYMBOL)) {
			board.UserBoard[x][y] = TENT_SYMBOL;
			System.out.println("Succesfull.");
			//board.printBoard();
		} else {
			System.out.println("Wrong. Try again.");
			//board.printBoard();
			run(scanner, 2);
		}
	}
	
	private boolean isGameFinished(){
		int desiredTentNumber = board.getNumberOfElements()/2;
		int tentCounter = 0;
		for (int i = 0; i < board.getSize() + 1; i++) {
			for (int j = 0; j < board.getSize() + 1; j++) {
				if(board.SolutionBoard[i][j].equals(TENT_SYMBOL) && board.UserBoard[i][j].equals(TENT_SYMBOL))
					tentCounter ++;
			}
		}
		if(tentCounter == desiredTentNumber)
			return true;
	return false;
	}
}	
	
	
	
	
	
	
