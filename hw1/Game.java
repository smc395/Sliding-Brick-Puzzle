import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
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
        int[][] gameBoard = board.getGameBoard();

        for (int row = 0; row < board.getHeight(); row++) {
            for (int column = 0; column < board.getWidth(); column++) {
                Piece p = move.getMovePiece();
                if(gameBoard[row][column] == p.getPieceNumber()){
                     gameBoard[row][column] = 0;
                }
            }
        }

    for( int i = 0;i<move.getSelectedMove().size();i++){
        int pieceRow = move.getSelectedMove().get(i).getRow();
        int pieceColumn = move.getSelectedMove().get(i).getColumn();
        gameBoard[pieceRow][pieceColumn] = move.getMovePiece().getPieceNumber();
    }
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
            Move move = new Move();
            // generate all the moves possible on board
            Map<Piece, Map<Direction, ArrayList<Position>>> allMoves = move.listAllMoves(board);

            // choose a possible move at random
            Random random = new Random();

            int selected = random.nextInt(allMoves.keySet().size());

            int c = 0;
            for (Piece p : allMoves.keySet()) {
                if (c == selected) {
                    int selectedDirection = random.nextInt(allMoves.get(p).keySet().size());
                    int g = 0;
                    for (Direction d : allMoves.get(p).keySet()) {
                        if (g == selectedDirection) {
                            ArrayList<Position> m = allMoves.get(p).get(d); 
                            move.setSelectedMove(m);
                            move.setMovePiece(p);
                            System.out.printf("(%d,%s)\n\n", p.getPieceNumber(), d);
                            
                        } else {
                            g++;
                        }
                    }
                    break;
                } else {
                    c++;
                }
            }
            // applyMove
            applyMove(board, move);
            
            // normalize resulting game state
            normalize(board);
            
            //check if puzzle solved
            puzzleCompleteCheck();
            
            // increment loop count
            count++;
        }
        board.displayBoard();
    }
}
