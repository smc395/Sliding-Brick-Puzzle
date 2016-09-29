import java.util.HashMap;
import java.util.Map;

public class Board {

	private int width;
	private int height;
	private int[][] gameBoard;
    private Map<Integer, Piece> boardPieces = new HashMap<Integer, Piece>();

	public Board(int w, int h, int[][] board) {
		width = w;
		height = h;
		gameBoard = board;
		populatePieces();
	}

	public Board(Board b) {
		this.width = b.width;
		this.height = b.height;
		this.gameBoard = b.gameBoard;
		populatePieces();
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

	public void populatePieces() {

        // loop through board and create unique pieces
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                int boardValue = gameBoard[row][column];
                if (boardValue > 1) {
                    Position position = new Position(row, column);
                    if (boardPieces.containsKey(boardValue)) {
                        boardPieces.get(boardValue).getPostions().add(position);
                    } else {
                        Piece piece = new Piece(boardValue);
                        piece.getPostions().add(position);
                        if (boardValue == 2) {
                            piece.setMasterPiece();
                        }
                        boardPieces.put(boardValue, piece);
                    }
                }
            }
        }
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
	
	public Map<Integer, Piece> getPieces(){
        return boardPieces;
    }

}
