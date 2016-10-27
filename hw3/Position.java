/**
 * A Position object represents the X,Y coordinates a {@link Piece} in the board state 
 * @author Sung Yan Chao
 *
 */
public class Position {
    private int row;
    private int column;

    /**
     * Constructor
     * @param row
     * @param column
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    // *********************SETTERS*********************
    public void setColumn(int column) { this.column = column; }

    public void setRow(int row) { this.row = row; }
    
    // ********************* GETTERS*********************
    public int getColumn() { return column; }

    public int getRow() { return row; }
}
