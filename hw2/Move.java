

/**
 * A move object has a board object that determines what the state of the board looks like with that particular move
 * and knows what step it took to get to that board state that it has.
 * @author Micah
 *
 */
public class Move {

    private Direction selectedDirection;
    private Piece movePiece; // piece that will be moved
    private Board b;

    public Move(int w, int h, int[][]board){
        b = new Board(w,h,board);
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
    
    public void setMoveBoard(int w, int h, int[][] board){
        b = new Board(w,h,board);
    }
    
    public Board getMoveBoard(){
        return b;
    }
    
    public void displayMove(){
        System.out.printf("\n(%d,%s)\n\n", movePiece.getPieceNumber(), selectedDirection );
    }
}
