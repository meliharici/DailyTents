

import java.util.Random;

public class Tent extends BoardObject {
	protected boolean isAttachedtoTree = false;
	private int row;
	private int column;
	protected boolean isAssigned = false;
	private static final String TREE_SYMBOL = "| E |";
	private static final String TENT_SYMBOL = "| T |";
	private static final String UNSUITABLE_LOCATION_ERROR = "No suitable tree for tent.. ";
	
	public Tent(int x, int y){
		row = x;
		column = y;
	}
	
	protected void attachToSuitableTree(Board board){ 
		int choice = findSuitableTreeLocation(board); 
		// suitable location is;  1:right 2:above 3:left 4:below
		if(choice == 1 && !board.SolutionBoard[row][column+1].equals(TENT_SYMBOL)){
			if(!board.SolutionBoard[row][column+1].equals(TREE_SYMBOL)){
				board.addTree(row, column+1);
				isAttachedtoTree = true;
				isAssigned = true;
			}
			else{
				isAssigned = false;
			}
		}
		else if(choice == 2 && !board.SolutionBoard[row-1][column].equals(TENT_SYMBOL)){
			if(!board.SolutionBoard[row-1][column].equals(TREE_SYMBOL)){
				board.addTree(row-1, column);
				isAttachedtoTree = true;
				isAssigned = true;
			}
			else{
				isAssigned = false;
			}
		}
		else if(choice == 3 && !board.SolutionBoard[row][column-1].equals(TENT_SYMBOL)){
			if(!board.SolutionBoard[row][column-1].equals(TREE_SYMBOL)){
				board.addTree(row, column-1);
				isAttachedtoTree = true;
				isAssigned = true;
			}
			else{
				isAssigned = false;
			}
		}
		else if(choice == 4 && !board.SolutionBoard[row+1][column].equals(TENT_SYMBOL)){
			if(!board.SolutionBoard[row+1][column].equals(TREE_SYMBOL)){
				board.addTree(row+1, column);
				isAttachedtoTree = true;
				isAssigned = true;
			}
			else{
				isAssigned = false;
			}
		}
		else{
			System.out.println(UNSUITABLE_LOCATION_ERROR);
		}
	}

	private int findSuitableTreeLocation(Board board) {
		Random random = new Random();
		int probability; 
		int choice;
		
		if(row == 1 && column != 1 && column != board.getSize()){	// row = 1 ,but not at the corners.
			 int[] numbers = {1,3,4};
			 probability = random.nextInt(2);
			 choice = numbers[probability];
		}
		else if(row == board.getSize() && column != 1 && column != board.getSize()){ // row = size ,but no at the corners
			int[] numbers = {1,2,3};
			probability = random.nextInt(2);
			choice = numbers[probability];
		}
		else if(column == board.getSize() && row != 1 && row != board.getSize()){ // column = size, but not at the corners
			int[] numbers = {2,3,4};
			probability = random.nextInt(2);
			choice = numbers[probability];
		}
		else if(column == 1 && row != 1 && row != board.getSize() ){ // column = 1, but not at the corners
			int[] numbers = {1,2,4};
			probability = random.nextInt(2);
			choice = numbers[probability];
		}
		else if(row == 1 && column == 1 ){ // corner case -- top left
			int[] numbers = {1,4};
			probability = random.nextInt(1);
			choice = numbers[probability];
		}
		else if(row == 1 && column == board.getSize()){ // corner case -- top right
			int[] numbers = {3,4};
			probability = random.nextInt(1);
			choice = numbers[probability];
		}
		else if(row == board.getSize() && column == 1){ // corner case -- down left
			int[] numbers = {1,2};
			probability = random.nextInt(1);
			choice = numbers[probability];
		}
		else if(row == board.getSize() && column == board.getSize()){  // corner case -- down right
			int[] numbers = {2,3};
			probability = random.nextInt(1);
			choice = numbers[probability];
		}
		else{	
			int[] numbers = {1,2,3,4};
			probability = random.nextInt(3);
			choice = numbers[probability];
		}
		return choice;
	}	
}
