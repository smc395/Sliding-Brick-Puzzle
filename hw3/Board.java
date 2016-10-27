import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A Board object has a board state, width/height of the board, a list of all Pieces,
 * and the position of its goal in the board state
 * @author Sung Yan Chao
 *
 */
public class Board {

    private int width;
    private int height;
    private int[][] gameBoard;
    private Map<Integer, Piece> boardPieces = new HashMap<Integer, Piece>();
    private ArrayList<Position> goalLocation = new ArrayList<Position>();

    /**
     * Constructor
     * @param w - width
     * @param h - height
     * @param board - board state
     */
    public Board(int w, int h, int[][] board) {
        width = w;
        height = h;
        gameBoard = board;
        populatePieces();
    }

    /**
     * prints out the board
     */
    public void displayBoard() {
        System.out.printf("%d,%d,\n", width, height);
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                System.out.printf("%d,", gameBoard[row][column]);
            }
            System.out.println();
        }
    }

    /**
     * clones the board state and returns a new Board object
     * @return Board
     */
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

    /**
     * creates Piece objects with Positions for a Board
     */
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
                } else if (boardValue == -1) {
                    Position goalPosition = new Position(row, column);
                    goalLocation.add(goalPosition);
                }
            }
        }
    }
    
    // ********************* SETTERS *********************
    /**
     * set this Board object's game board to another board
     * @param newBoard
     */
    public void setGameBoard(int[][] newBoard) {
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                gameBoard[row][column] = newBoard[row][column];
            }
        }
    }
    
    // ********************* GETTERS *********************
    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public int[][] getGameBoard() { return gameBoard; }

    public Map<Integer, Piece> getPieces() { return boardPieces; }
    
    public ArrayList<Position> getGoalLocation(){ return goalLocation; }

}
