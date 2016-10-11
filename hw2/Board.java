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

    // prints out the board
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
        int[][] clonedState = new int[height][width];

        // copy board state
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                clonedState[row][column] = gameBoard[row][column];
            }
        }

        Board boardClone = new Board(width, height, clonedState);
        return boardClone;
    }

    public void populatePieces() {
        boardPieces.clear();
        // loop through board and create unique piece objects
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

    public void setGameBoard(int[][] newBoard){
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                gameBoard[row][column] = newBoard[row][column];
            }
        }
    }
    
    public int[][] getGameBoard() {
        return gameBoard;
    }

    public Map<Integer, Piece> getPieces() {
        return boardPieces;
    }

}
