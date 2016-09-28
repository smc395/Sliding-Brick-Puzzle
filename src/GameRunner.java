import java.io.IOException;

public class GameRunner {

	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Missing file name");
			System.exit(0);
		}

		System.out.println("Hello, welcome to sliding brick puzzle!");
        System.out.println("How to play: Move the master block (2) to the goal (-1) by sliding pieces");
        System.out.println("1's are walls, 0's are empty spaces");
        
		Game g = new Game();
		try {
			g.loadGameState(args[0]);
			g.populatePieces();
			Piece piece8 = g.getPieces().get(8);
			piece8.printPositions();
			Move m = new Move(g.getBoard());
			m.listAllPieceMoves(piece8);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("File name not found");
		}
		
		g.getBoard().displayBoard();
		// g.getBoard().cloneBoard().displayBoard();
		//g.puzzleCompleteCheck();
	}
}