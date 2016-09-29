import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Move {

    private Board boardState;

    public Move(Board boardState) {
        this.boardState = boardState;
    }

    public Map<Direction, ArrayList<Position>> listAllPieceMoves(Piece piece) {
        boolean moveRight = true, moveLeft = true, moveUp = true, moveDown = true;

        int[][] gameBoard = boardState.getGameBoard();

        // first get piece current positions
        ArrayList<Position> positions = piece.getPostions();
        int positionSize = positions.size();

        // Check what direction piece can move
        for (int j = 0; j < positionSize; j++) {
            int pieceRow = positions.get(j).getRow();
            int pieceColumn = positions.get(j).getColumn();

            // check right
            // Must check if master piece too
            if ((gameBoard[pieceRow][pieceColumn + 1] != piece.getPieceNumber()
                    && gameBoard[pieceRow][pieceColumn + 1] >= 1)
                    || (gameBoard[pieceRow][pieceColumn + 1] < 0 && !piece.isMasterPiece())) {
                moveRight = false;
            }
            // check left
            if ((gameBoard[pieceRow][pieceColumn - 1] != piece.getPieceNumber()
                    && gameBoard[pieceRow][pieceColumn - 1] >= 1)
                    || (gameBoard[pieceRow][pieceColumn - 1] < 0 && !piece.isMasterPiece())) {
                moveLeft = false;
            }
            // check up
            if ((gameBoard[pieceRow - 1][pieceColumn] != piece.getPieceNumber()
                    && gameBoard[pieceRow - 1][pieceColumn] >= 1)
                    || (gameBoard[pieceRow - 1][pieceColumn] < 0 && !piece.isMasterPiece())) {
                moveUp = false;
            }
            // check down
            if ((gameBoard[pieceRow + 1][pieceColumn] != piece.getPieceNumber()
                    && gameBoard[pieceRow + 1][pieceColumn] >= 1)
                    || (gameBoard[pieceRow + 1][pieceColumn] < 0 && !piece.isMasterPiece())) {
                moveDown = false;
            }
        }

        // Create list of new positions the piece could be in
        ArrayList<Position> possiblePositionsRight = new ArrayList<Position>();
        ArrayList<Position> possiblePositionsLeft = new ArrayList<Position>();
        ArrayList<Position> possiblePositionsUp = new ArrayList<Position>();
        ArrayList<Position> possiblePositionsDown = new ArrayList<Position>();

        for (int k = 0; k < positionSize; k++) {

            int pieceRow = positions.get(k).getRow();
            int pieceColumn = positions.get(k).getColumn();
            if (moveRight) {
                Position p = new Position(pieceRow, pieceColumn + 1);
                possiblePositionsRight.add(p);
            }
            if (moveLeft) {
                Position p = new Position(pieceRow, pieceColumn - 1);
                possiblePositionsLeft.add(p);
            }
            if (moveUp) {
                Position p = new Position(pieceRow - 1, pieceColumn);
                possiblePositionsUp.add(p);
            }
            if (moveDown) {
                Position p = new Position(pieceRow + 1, pieceColumn);
                possiblePositionsDown.add(p);
            }
        }

        // Return all new piece positions in a direction
        Map<Direction, ArrayList<Position>> possibleMoves = new HashMap<>();
        if (!possiblePositionsUp.isEmpty()) {
            possibleMoves.put(Direction.UP, possiblePositionsUp);
        }
        if (!possiblePositionsDown.isEmpty()) {
            possibleMoves.put(Direction.DOWN, possiblePositionsDown);
        }
        if (!possiblePositionsLeft.isEmpty()) {
            possibleMoves.put(Direction.LEFT, possiblePositionsLeft);
        }
        if (!possiblePositionsRight.isEmpty()) {
            possibleMoves.put(Direction.RIGHT, possiblePositionsRight);
        }

        return possibleMoves;
    }

    public Map<Piece, Map<Direction, ArrayList<Position>>> listAllMoves(Map<Integer, Piece> boardPieces) {
        //Map the piece to all possible positions for a direction
        Map<Piece, Map<Direction, ArrayList<Position>>> possibleMoves = new HashMap<Piece, Map<Direction, ArrayList<Position>>>();
        
        for (int i = 0; i < boardPieces.size(); i++) {
            possibleMoves.put(boardPieces.get(i+2),listAllPieceMoves(boardPieces.get(i+2)));
        }
        
        return possibleMoves;
    }
}
