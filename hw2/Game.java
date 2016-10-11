import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Game {

    private Board board;
    private boolean solved = false;

    public void loadGameState(String fileName) throws NumberFormatException, IOException {

        // read a file from state folder
        BufferedReader reader = new BufferedReader(new FileReader("./state/" + fileName));
        boolean firstLine = true;
        int row = 0;
        int width = 0;
        int height = 0;
        int[][] newBoard = null;
        String line;

        while ((line = reader.readLine()) != null) {
            String[] currentLine = line.split(",");

            // create board of width,height
            if (firstLine == true) {
                width = Integer.parseInt(currentLine[0]);
                height = Integer.parseInt(currentLine[1]);
                firstLine = false;
                newBoard = new int[height][width];
                continue;
            }

            // populate board state
            for (int column = 0; column < currentLine.length; column++) {
                newBoard[row][column] = Integer.parseInt(currentLine[column]);
            }
            row++;
        }
        reader.close();

        // create board for game
        board = new Board(width, height, newBoard);
    }

    public void setBoard(Board b) {
        board = b;
    }

    public Board getBoard() {
        return board;
    }

    public boolean getPuzzleSolved() {
        return solved;
    }

    public void puzzleCompleteCheck() {
        boolean goalSpaceExist = false;
        // loop through each cell in matrix for -1
        for (int row = 0; row < board.getHeight(); row++) {
            for (int column = 0; column < board.getWidth(); column++) {
                if (board.getGameBoard()[row][column] == -1) {
                    goalSpaceExist = true;
                }
            }
        }
        if (goalSpaceExist == false) {
            solved = true;
            System.out.println("Puzzle solved!");
        }
    }

    public void applyMove(Board board, Move move) {
        board.setGameBoard(move.getMoveBoard().getGameBoard());
        board.populatePieces();
    }

    public Board applyMoveCloning(Board board, Move move) {
        Board newBoard = board.cloneBoard();
        applyMove(newBoard, move);
        return newBoard;
    }

    public boolean identicalStates(Board b1, Board b2) {
        for (int k = 0; k < b1.getHeight(); k++) {
            for (int m = 0; m < b1.getWidth(); m++) {
                if (b1.getGameBoard()[k][m] != b2.getGameBoard()[k][m]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void normalize(Board board) {

        int nextIdx = 3;
        for (int row = 0; row < board.getHeight(); row++) {
            for (int column = 0; column < board.getWidth(); column++) {
                int cellValue = board.getGameBoard()[row][column];
                if (cellValue == nextIdx) {
                    nextIdx++;
                } else if (cellValue > nextIdx) {
                    swapIdx(nextIdx, cellValue, board);
                    nextIdx++;
                }
            }
        }
        board.populatePieces();
        setBoard(board);
    }

    private void swapIdx(int idx1, int idx2, Board board) {

        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                if (board.getGameBoard()[i][j] == idx1) {
                    board.getGameBoard()[i][j] = idx2;
                } else if (board.getGameBoard()[i][j] == idx2) {
                    board.getGameBoard()[i][j] = idx1;
                }
            }
        }
    }

    public void randomWalk(Board board, int n) {

        int count = 0;
        while (solved != true && count < n) {
            board.displayBoard();
            // generate all the moves possible on board
            ArrayList<Move> allMoves = listAllMoves(board);

            // choose a possible move at random
            Random random = new Random();
            int selected = random.nextInt(allMoves.size());
            
            // get selected random move
            Move move = allMoves.get(selected);
            move.displayMove();
            
            // applyMove
            applyMove(board, move);

            // normalize resulting game state
            normalize(board);

            // check if puzzle solved
            puzzleCompleteCheck();

            // increment loop count
            count++;
        }
        board.displayBoard();
    }

    public ArrayList<Move> listAllPieceMoves(Piece piece, Board board) {
        boolean moveRight = true, moveLeft = true, moveUp = true, moveDown = true;

        int[][] gameBoard = board.getGameBoard();

        // first get piece current positions
        ArrayList<Position> positions = piece.getPostions();
        int positionSize = positions.size();

        // Check what direction piece can move
        for (int j = 0; j < positionSize; j++) {
            int pieceRow = positions.get(j).getRow();
            int pieceColumn = positions.get(j).getColumn();

            /*
             * check right if piece is the same piece, another piece, a wall and
             * if the piece is a master piece to be able to move to the goal
             * position
             */
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

        ArrayList<Move> moves = new ArrayList<Move>(4);

        //TODO fix swap logic of numbers and zeros
        // create Move objects for all the moves the piece can make
        if (moveRight) {
            int[][] b = board.cloneBoard().getGameBoard();
            for (int k = 0; k < positionSize; k++) {
                int pieceRow = positions.get(k).getRow();
                int pieceColumn = positions.get(k).getColumn();
                b[pieceRow][pieceColumn + 1] = piece.getPieceNumber();
                if(k == 0){
                    b[pieceRow][pieceColumn] = 0;
                }
            }
            Move m = new Move(board.getWidth(), board.getHeight(), b);
            m.setMovePiece(piece);
            m.setSelectedMove(Direction.RIGHT);
            moves.add(m);
        }
        if (moveLeft) {
            int[][] b = board.cloneBoard().getGameBoard();
            for (int k = 0; k < positionSize; k++) {
                int pieceRow = positions.get(k).getRow();
                int pieceColumn = positions.get(k).getColumn();
                b[pieceRow][pieceColumn - 1] = piece.getPieceNumber();
                b[pieceRow][pieceColumn] = 0;
            }
            Move m = new Move(board.getWidth(), board.getHeight(), b);
            m.setMovePiece(piece);
            m.setSelectedMove(Direction.LEFT);
            moves.add(m);
        }
        if (moveUp) {
            int[][] b = board.cloneBoard().getGameBoard();
            for (int k = 0; k < positionSize; k++) {
                int pieceRow = positions.get(k).getRow();
                int pieceColumn = positions.get(k).getColumn();
                b[pieceRow - 1][pieceColumn] = piece.getPieceNumber();
                b[pieceRow][pieceColumn] = 0;
            }
            Move m = new Move(board.getWidth(), board.getHeight(), b);
            m.setMovePiece(piece);
            m.setSelectedMove(Direction.UP);
            moves.add(m);
        }
        if (moveDown) {
            int[][] b = board.cloneBoard().getGameBoard();
            for (int k = 0; k < positionSize; k++) {
                int pieceRow = positions.get(k).getRow();
                int pieceColumn = positions.get(k).getColumn();
                b[pieceRow + 1][pieceColumn] = piece.getPieceNumber();
                b[pieceRow][pieceColumn] = 0;
            }
            Move m = new Move(board.getWidth(), board.getHeight(), b);
            m.setMovePiece(piece);
            m.setSelectedMove(Direction.DOWN);
            moves.add(m);
        }

        return moves;
    }

    public ArrayList<Move> listAllMoves(Board board) {
        ArrayList<Move> allMoves = new ArrayList<Move>();
        for (int i = 0; i < board.getPieces().size(); i++) {
            ArrayList<Move> pieceMoves = listAllPieceMoves(board.getPieces().get(i + 2), board);
            if (!pieceMoves.isEmpty()) {
                for (int m = 0; m < pieceMoves.size(); m++) {
                    allMoves.add(pieceMoves.get(m));
                }
            }
        }
        return allMoves;
    }

    public void breadthFirst(Board board) {

    }
}
