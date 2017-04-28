import java.util.Random;

public class Board {
	protected static final String TENT_SYMBOL = "| T |";
	protected static final String TREE_SYMBOL = "| E |";
	protected static final String EMPTY_SYMBOL = "|   |";
	protected static final String GRASS_SYMBOL = "| . |";;
	private int size;
	private int maxNumberOfTrees;
	protected BoardObject[][] BoardMatrix;
	protected String[][] UserBoard;
	protected String[][] SolutionBoard;

	public Board(int size) {
		this.size = size;
		BoardMatrix = new BoardObject[size + 1][size + 1];
		UserBoard = new String[size + 1][size + 1];
		SolutionBoard = new String[size + 1][size + 1];
	}

	public void setSize(int newSize) {
		size = newSize;
	}

	public int getSize() {
		return size;
	}
	
	public int getNumberOfElements(){
		return treeNumber()*2;
	}

	protected void initializeBoard() {	
		for (int i = 0; i < size + 1; i++) {
			for (int j = 0; j < size + 1; j++) 
				SolutionBoard[i][j] = EMPTY_SYMBOL;
		}
	}

	protected void putTreesNextToTents() {
		int treeCounter = 0;
		maxNumberOfTrees = treeNumber();
		if(treeCounter < maxNumberOfTrees){
		for (int i = 1; i < size + 1; i++) {
			for (int j = 1; j < size + 1; j++) {
				if(SolutionBoard[i][j].equals(TENT_SYMBOL)){	
					Tent tent = (Tent) BoardMatrix[i][j];
					if(!tent.isAttachedtoTree){	
						tent.attachToSuitableTree(this);
						if(tent.isAssigned)
							treeCounter++;
					}
				} 
			 }
		   }
		}
		
		if(treeCounter < maxNumberOfTrees)
			redistributeTrees();
	}
	
	protected void redistributeTrees(){
		resetBoard(SolutionBoard);
		putTreesNextToTents();
	}
	
	protected void createRandomTents() {
		Random random = new Random();
		int tentCounter = 0;
		int maxNumberOfTents = treeNumber();
		if (tentCounter < maxNumberOfTents) {
			for (int i = 1; i < size + 1; i++) {
				for (int j = 1; j < size + 1; j++) {
					int probability = random.nextInt(100); 
					if (tentCounter < maxNumberOfTents && probability < 50) {
						if (isTentValid(i, j) == true) {
							addTent(i, j);
							tentCounter++;
						}
					}
				}
			}
		}
		
		if (tentCounter < maxNumberOfTents) 
			resetBoard(SolutionBoard);
	}
	
	private void resetBoard(String [][] board){
		deleteBoard(SolutionBoard);
		initializeBoard();
		createRandomTents();
	}

	protected void deleteBoard(String [][] board){
		for (int i = 1; i < size + 1; i++) {
			for (int j = 1; j < size + 1; j++) 
				board[i][j] = null;
		}
	}
	
	private int treeNumber() {
		if (size == 8)
			return 12;
		else if (size == 12)
			return 28;
		else if (size == 16)
			return 51;
		else if (size == 20)
			return 80;
		else
			return -1;
	}

	protected void addTree(int row, int column) {
		Tree tree = new Tree(row,column);
		BoardMatrix[row][column] = tree;
		SolutionBoard[row][column] = TREE_SYMBOL;
		UserBoard[row][column] = TREE_SYMBOL;
	}

	private void addTent(int row, int column) {
		Tent tent = new Tent(row,column);
		BoardMatrix[row][column] = tent; 
		SolutionBoard[row][column] = TENT_SYMBOL;
		if (column != 1)
			SolutionBoard[row][column - 1] = GRASS_SYMBOL; // left
		if (column != size)
			SolutionBoard[row][column + 1] = GRASS_SYMBOL; // right
		if (row != 1)
			SolutionBoard[row - 1][column] = GRASS_SYMBOL; // above
		if (row != 1 && column != 1)
			SolutionBoard[row - 1][column - 1] = GRASS_SYMBOL; // top-left
		if (row != 1 && column != size)
			SolutionBoard[row - 1][column + 1] = GRASS_SYMBOL; // top-right
		if (row != size)
			SolutionBoard[row + 1][column] = GRASS_SYMBOL; // below
		if (row != size && column != 1)
			SolutionBoard[row + 1][column - 1] = GRASS_SYMBOL; // bottom-left
		if (row != size && column != size)
			SolutionBoard[row + 1][column + 1] = GRASS_SYMBOL; // bottom-right
	}

	protected void createUserBoard(){
		for (int i = 0; i < size + 1; i++) {
			for (int j = 0; j < size + 1; j++) {
				if(SolutionBoard[i][j].equals(TENT_SYMBOL)){
					UserBoard[i][j] = EMPTY_SYMBOL;
				}
				else if(SolutionBoard[i][j].equals(GRASS_SYMBOL)){
					UserBoard[i][j] = EMPTY_SYMBOL;
				}
				else {
					UserBoard[i][j] = SolutionBoard[i][j];
				}
			}
		}
	} 
	
	private boolean isTentValid(int x, int y) {
		if (isLocationEmpty(x, y))
			return true;
		return false;
	}

	private boolean isLocationEmpty(int row, int column) {
		if (SolutionBoard[row][column].equals(EMPTY_SYMBOL))
			return true;																	 
		return false;
	}

	protected void fillNumberCells() {
		for (int j = 1; j < size + 1; j++) {
			String str = Integer.toString(numberOfTentsInColumn(j)); 
			if(numberOfTentsInColumn(j) < 10)
				SolutionBoard[0][j] = "|_" + str + "_|";
			else
				SolutionBoard[0][j] = "|_" + str + "|" ;    
		}
		for(int i = 0; i < size+1; i++){
			String str = Integer.toString(numberOfTentsInRow(i));
			if(numberOfTentsInRow(i) < 10)
				SolutionBoard[i][0] = "|_" + str + "_|";
			else
				SolutionBoard[i][0] = "|_" + str + "|" ;    
			}
	}
	
	private int numberOfTentsInRow(int rowNumber){
		int counter = 0;
		for(int j=0; j < size+1; j++){
			if(SolutionBoard[rowNumber][j].equals(TENT_SYMBOL))
				counter++;
		}
		return counter;
	}
	
	private int numberOfTentsInColumn(int columnNumber){
		int counter = 0;
		for(int i=0; i < size+1; i++){
			if(SolutionBoard[i][columnNumber].equals(TENT_SYMBOL))
				counter++;
		}
		return counter;
	}
}
