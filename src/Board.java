public class Board {

	private int width;
	private int height;
	private int[][] gameBoard;

	public Board(int w, int h, int[][] board) {
		width = w;
		height = h;
		gameBoard = board;
	}

	public Board(Board b) {
		this.width = b.width;
		this.height = b.height;
		this.gameBoard = b.gameBoard;
	}

	public void displayBoard() {
		System.out.printf("%d,%d,\n", width, height);
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				System.out.printf("%d,", gameBoard[row][column]);
			}
			System.out.println();
		}
	}

	public Board cloneBoard() {
		Board boardClone = new Board(this.width, this.height, this.gameBoard);
		return boardClone;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int[][] getGameBoard() {
		return gameBoard;
	}

}
