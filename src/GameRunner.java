import java.io.IOException;

public class GameRunner {

	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Missing file name");
			System.exit(0);
		}

		Game g = new Game();
		try {
			g.loadGameState(args[0]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("File name not found");
		}

		System.out.println("Hello, welcome to sliding brick puzzle!");
        System.out.println("How to play: Move the master block (2) to the goal (-1) by sliding pieces");
        System.out.println("1's are walls, 0's are empty spaces");
        
		g.getBoard().displayBoard();
		// g.getBoard().cloneBoard().displayBoard();
		//g.puzzleCompleteCheck();
	}
}