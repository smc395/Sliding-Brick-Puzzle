import java.util.ArrayList;

public class Piece {

    private ArrayList<Position> positions;
    private boolean master = false;
    private int pieceNumber = 0;

    public Piece(int num) {
        pieceNumber = num;
        positions = new ArrayList<Position>();
    }

    public void setPositions(ArrayList<Position> newPositions) {
        positions = newPositions;
    }

    public ArrayList<Position> getPostions() {
        return positions;
    }

    public void setPieceNumber(int n){
        pieceNumber = n;
    }
    
    public int getPieceNumber(){
        return pieceNumber;
    }
    
    public void setMasterPiece() {
        master = true;
    }

    public boolean isMasterPiece() {
        return master;
    }
    
    public void printPositions(){
        System.out.printf("%d - ",pieceNumber);
        for(Position p : positions){
            System.out.printf("(%d,%d) ", p.getRow(),p.getColumn());
        }
        System.out.println();
    }
}
