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

    public void applyMove(Board board, Move move){
        int[][] gameBoard = board.getGameBoard();
        
        //move position of piece to selected direction
        for(int i = 0; i < move.getSelectedMove().size(); i++){
            int pieceRow = move.getSelectedMove().get(i).getRow();
            int pieceColumn = move.getSelectedMove().get(i).getColumn();
            gameBoard[pieceRow][pieceColumn] = move.getMovePiece().getPieceNumber();
        }
        //replace piece positions with zeros
        for(int j = 0; j < move.getMovePiece().getPostions().size(); j++){
            int pieceRow = move.getMovePiece().getPostions().get(j).getRow();
            int pieceColumn = move.getMovePiece().getPostions().get(j).getColumn();
            gameBoard[pieceRow][pieceColumn] = 0;
        }
        
        board.displayBoard();
    }
}
