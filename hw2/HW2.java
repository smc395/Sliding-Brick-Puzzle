import java.io.IOException;
import java.util.ArrayList;

public class HW2 {

	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Missing file name");
			System.exit(0);
		}
        
		Game g = new Game();
		try {
			g.loadGameState(args[0]);
			System.out.println("Initial board state");
			//g.getBoard().displayBoard();
			
			
			/*ArrayList<Move> moves = g.listAllMoves(g.getBoard());
			for(int i=0; i < moves.size(); i++){
		       moves.get(i).getMoveBoard().displayBoard();
		       System.out.println();
			}*/
			g.randomWalk(g.getBoard(), 100);
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("File name not found");
		}

	}
}