/**
 * A move object has a board object that determines what the state of the board
 * looks like with that particular move and knows what step it took to get to
 * that board state that it has.
 * 
 * @author Micah
 *
 */
public class Move {

    private Direction selectedDirection; // the direction that the piece moved in
    private Piece movePiece; // piece that will be moved
    private Board board;

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
}
