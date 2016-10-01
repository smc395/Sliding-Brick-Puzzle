import java.io.IOException;

public class HW1 {

	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Missing file name");
			System.exit(0);
		}
        
		Game g = new Game();
		try {
			g.loadGameState(args[0]);
			System.out.println("Initial board state");
			
			g.randomWalk(g.getBoard(), 100);
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("File name not found");
		}

	}
}