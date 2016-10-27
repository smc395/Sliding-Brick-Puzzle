import java.util.ArrayList;

/**
 * A Move object describes a move that can be made to a Board object. It has information
 * such as the Piece that was moved and in what direction, and the board state after
 * the selected piece was moved in that direction.
 * 
 * Additionally, it contains information such as the cost, estimated distance from the goal,
 * and total estimated cost for this move.
 * 
 * @author Sung Yan Chao
 *
 */
public class Move {

    private Direction selectedDirection; // the direction that the piece moved in
    private Piece movePiece; // piece that will be moved
    private Board board; // the board after the piece has moved in the selected direction
    private int cost = 0; // g(node)
    private int manhattan = 0; // h(node)
    private int estimatedCost = 0; // f(node) = g(node) + h(node)

    /**
     * Constructor
     * @param board - {@link Board}
     */
    public Move(Board board) {
        this.board = board;
    }

    /**
     * Constructor
     * @param w - width
     * @param h - height
     * @param b - board state
     */
    public Move(int w, int h, int[][] b) {
        this.board = new Board(w, h, b);
    }

    /**
     * Prints out the move's piece number and what direction that piece moved
     */
    public void displayMove() {
        System.out.printf("(%d,%s)\n", movePiece.getPieceNumber(), selectedDirection);
    }
    
    /**
     * Calculates the move's Manhattan distance to the goal
     */
    public void calculateManhattan() {
        // get the board's goal location
        ArrayList<Position> goal = board.getGoalLocation();

        // if we solved the puzzle -1's will be gone so just return
        if (goal.size() == 0) {
            return;
        }
        
        // get the master piece's position
        ArrayList<Position> piecePositions = board.getPieces().get(2).getPostions();

        // calculate Manhattan distance |goalX - pieceX| + |goalY - pieceY|
        
        // if goal is on the upper or left portion of the board
        if (goal.get(0).getRow() == 0 || goal.get(0).getColumn() == 0) {
            int x = Math.abs(goal.get(0).getRow() - piecePositions.get(0).getRow());
            int y = Math.abs(goal.get(0).getColumn() - piecePositions.get(0).getColumn());
            manhattan = x + y;
        
        } 
        // else if goal is on the right or bottom portion of the board
        else if (goal.get(goal.size() - 1).getRow() == (board.getHeight() - 1)
                || goal.get(goal.size() - 1).getColumn() == (board.getWidth() - 1)) {
            int x = Math.abs(goal.get(goal.size() - 1).getRow() - piecePositions.get(goal.size() - 1).getRow());
            int y = Math.abs(goal.get(goal.size() - 1).getColumn() - piecePositions.get(goal.size() - 1).getColumn());
            manhattan = x + y;
        }
    }

    /**
     * Calculates the move's total estimated cost f(node)
     * @return
     */
    public int calculateF() {
        estimatedCost = cost + manhattan;
        return estimatedCost;
    }
    
    // *********************SETTERS*********************
    /**
     * Sets the move's piece
     * @param p - {@link Piece}
     */
    public void setMovePiece(Piece p) { movePiece = p; }

    /**
     * Sets the move's direction
     * @param direction - {@link Direction}
     */
    public void setSelectedMove(Direction direction) { selectedDirection = direction; }
    
    /**
     * Sets the move's board
     * @param w - width
     * @param h - height
     * @param board - board state
     */
    public void setMoveBoard(int w, int h, int[][] board) {
        this.board = new Board(w, h, board);
    }
    
    /**
     * Sets the move's cost (the distance from the root node to this move)
     * @param c - cost
     */
    public void setCost(int c) { cost = c; }

    // *********************GETTERS*********************
    
    public Board getMoveBoard() { return board; }

    public Piece getMovePiece() { return movePiece; }

    public Direction getSelectedMove() { return selectedDirection; }
    
    public int getCost() { return cost; }
    
    public int getManhattan() { return manhattan; }
}
