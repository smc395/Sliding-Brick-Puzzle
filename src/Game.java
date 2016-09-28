import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Game {

    private Board board;
    private Map<Integer, Piece> boardPieces = new HashMap<Integer, Piece>();
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

            // create board
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

        board = new Board(width, height, newBoard);
    }

    public void populatePieces() {
        int[][] gameBoard = board.getGameBoard();

        // loop through board and create unique pieces
        for (int row = 0; row < board.getHeight(); row++) {
            for (int column = 0; column < board.getWidth(); column++) {
                int boardValue = gameBoard[row][column];
                if (boardValue > 1) {
                    Position position = new Position(row, column);
                    if (boardPieces.containsKey(boardValue)) {
                        boardPieces.get(boardValue).getPostions().add(position);
                    } else {
                        Piece piece = new Piece(boardValue);
                        piece.getPostions().add(position);
                        if (boardValue == 2) {
                            piece.setMasterPiece();
                        }
                        boardPieces.put(boardValue, piece);
                    }
                }
            }
        }
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

    public Map<Integer, Piece> getPieces(){
        return boardPieces;
    }
    
    public void puzzleCompleteCheck() {
        boolean goalSpaceExist = false;
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

}
