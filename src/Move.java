import java.util.ArrayList;

public class Move {

    private Board boardState;

    public Move(Board boardState) {
        this.boardState = boardState;
    }

    public void listAllPieceMoves(Piece piece) {
        boolean moveRight = true, moveLeft = true, moveUp = true, moveDown = true;

        int[][] gameBoard = boardState.getGameBoard();

        // first get piece positions
        ArrayList<Position> positions = piece.getPostions();
        int positionSize = positions.size();

        for (int j = 0; j < positionSize; j++) {
            int pieceRow = positions.get(j).getRow();
            int pieceColumn = positions.get(j).getColumn();
            
            //Must check if master piece too
            // check right
            if (gameBoard[pieceRow][pieceColumn + 1] != piece.getPieceNumber()
                    && gameBoard[pieceRow][pieceColumn + 1] >= 1) {
                moveRight = false;
            }
            // check left
            if (gameBoard[pieceRow][pieceColumn - 1] != piece.getPieceNumber()
                    && gameBoard[pieceRow][pieceColumn - 1] >= 1 && gameBoard[pieceRow][pieceColumn - 1] == -1) {
                moveLeft = false;
            }
            // check up
            if (gameBoard[pieceRow - 1][pieceColumn] != piece.getPieceNumber()
                    && gameBoard[pieceRow - 1][pieceColumn] >= 1) {
                moveUp = false;
            }
            // check down
            if (gameBoard[pieceRow + 1][pieceColumn] != piece.getPieceNumber()
                    && gameBoard[pieceRow + 1][pieceColumn] >= 1) {
                moveDown = false;
            }
        }

        if(moveRight){ System.out.println("Can move right");}
        if(moveLeft){ System.out.println("Can move left");}
        if(moveUp){ System.out.println("Can move up");}
        if(moveDown){ System.out.println("Can move down");}
        
        /*ArrayList<Position> possiblePositions = new ArrayList<Position>();
        for (int k = 0; k < positionSize; k++) {
            
            int pieceRow = positions.get(k).getRow();
            int pieceColumn = positions.get(k).getColumn();
            if (moveRight) {
                Position p = new Position(pieceRow, pieceColumn + 1);
                possiblePositions.add(p);
            }
            if (moveLeft) {
                Position p = new Position(pieceRow, pieceColumn - 1);
                possiblePositions.add(p);
            }
            if (moveUp) {
                Position p = new Position(pieceRow - 1, pieceColumn);
                possiblePositions.add(p);
            }
            if (moveDown) {
                Position p = new Position(pieceRow + 1, pieceColumn);
                possiblePositions.add(p);
            }
        }*/
    }
}
