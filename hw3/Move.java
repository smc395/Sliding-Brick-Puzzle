import java.util.ArrayList;

/**
 * A move object has a board object that determines what the state of the board
 * looks like with that particular move and knows what step it took to get to
 * that board state that it has.
 * 
 * @author Micah
 *
 */
public class Move {

    private Direction selectedDirection; // the direction that the piece moved
                                         // in
    private Piece movePiece; // piece that will be moved
    private Board board;
    private int cost = 0;
    private int manhattan = 0;
    private int estimatedCost = 0;

    public Move(Board board) {
        this.board = board;
    }

    public Move(int w, int h, int[][] b) {
        this.board = new Board(w, h, b);
    }

    public void setMovePiece(Piece p) {
        movePiece = p;
    }

    public Piece getMovePiece() {
        return movePiece;
    }

    public void setSelectedMove(Direction direction) {
        selectedDirection = direction;
    }

    public Direction getSelectedMove() {
        return selectedDirection;
    }

    public void setMoveBoard(int w, int h, int[][] board) {
        this.board = new Board(w, h, board);
    }

    public Board getMoveBoard() {
        return board;
    }

    public void displayMove() {
        System.out.printf("(%d,%s)\n", movePiece.getPieceNumber(), selectedDirection);
    }

    public void setCost(int c) {
        cost = c;
    }

    public int getCost() {
        return cost;
    }

    public void calculateManhattan() {
        // get the board's goal location
        ArrayList<Position> goal = board.getGoalLocation();

        if(goal.size()==0){
            return;
        }
        
        // get the master piece's position
        ArrayList<Position> piecePositions = board.getPieces().get(2).getPostions();

        // calculate Manhattan distance |goalX - pieceX| + |goalY - pieceY|
        // if goal is on the upper or left portion of the board manhattan is x only
        if (goal.get(0).getRow() == 0 || goal.get(0).getColumn() == 0) {
            int x = Math.abs(goal.get(0).getRow() - piecePositions.get(0).getRow());
            int y = Math.abs(goal.get(0).getColumn() - piecePositions.get(0).getColumn());
            manhattan = x + y;
            // else if goal is on the right or bottom portion of the board manhattan is y only
        } else if (goal.get(goal.size() - 1).getRow() == (board.getHeight() - 1)
                || goal.get(goal.size() - 1).getColumn() == (board.getWidth() - 1)) {
            int x = Math.abs(goal.get(goal.size() - 1).getRow() - piecePositions.get(goal.size() - 1).getRow());
            int y = Math.abs(goal.get(goal.size() - 1).getColumn() - piecePositions.get(goal.size() - 1).getColumn());
            manhattan = x + y;
        }

    }

    public int getManhattan() {
        return manhattan;
    }

    public int calculateF() {
        estimatedCost = cost + manhattan;
        return estimatedCost;
    }
}
