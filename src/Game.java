import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
        int[][] gameBoard = board.getGameBoard();

        // move position of piece to selected direction
        for (int i = 0; i < move.getSelectedMove().size(); i++) {
            int pieceRow = move.getSelectedMove().get(i).getRow();
            int pieceColumn = move.getSelectedMove().get(i).getColumn();
            gameBoard[pieceRow][pieceColumn] = move.getMovePiece().getPieceNumber();
        }
        // replace piece positions with zeros
        for (int j = 0; j < move.getMovePiece().getPostions().size(); j++) {
            int pieceRow = move.getMovePiece().getPostions().get(j).getRow();
            int pieceColumn = move.getMovePiece().getPostions().get(j).getColumn();
            gameBoard[pieceRow][pieceColumn] = 0;
        }
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
                if (cellValue ==nextIdx) {
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
                if (board.getGameBoard()[i][j]==idx1) {
                    board.getGameBoard()[i][j]=idx2;
                } else if (board.getGameBoard()[i][j]==idx2) {
                    board.getGameBoard()[i][j]=idx1;
                }
            }
        }
    }
    
    public void randomWalk(Board board, int n){
        Move move = new Move();
        move.listAllMoves(board);
    }
}
