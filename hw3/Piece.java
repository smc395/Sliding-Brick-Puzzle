import java.util.ArrayList;

/**
 * A Piece object represents a piece number in a {@link Board}. It has a list of {@link Position} so
 * that every piece knows where it is in the board state.
 * @author Sung Yan Chao
 *
 */
public class Piece {

    private ArrayList<Position> positions;
    private boolean master = false;
    private int pieceNumber = 0;

    /**
     * Constructor
     * @param num - piece number
     */
    public Piece(int num) {
        pieceNumber = num;
        positions = new ArrayList<Position>(2);
    }

    /**
     * Checks if the piece is the master piece
     * @return true if the pieceNumber is 2, false otherwise
     */
    public boolean isMasterPiece() {
        return master;
    }

    /**
     * Print all the {@link Position} of the piece
     */
    public void printPositions() {
        System.out.printf("%d - ", pieceNumber);
        for (Position p : positions) {
            System.out.printf("(%d,%d) ", p.getRow(), p.getColumn());
        }
        System.out.println();
    }
    
    // ********************* SETTERS *********************
    public void setPositions(ArrayList<Position> newPositions) { positions = newPositions; }

    public void setPieceNumber(int n) { pieceNumber = n; }
    
    public void setMasterPiece() { master = true; }
    
    // ********************* GETTERS *********************
    public ArrayList<Position> getPostions() { return positions; }
    
    public int getPieceNumber() { return pieceNumber; }

    
}
