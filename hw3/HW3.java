import java.io.IOException;
import java.io.PrintWriter;

/**
 * Main function of game 
 * @author Sung Yan Chao
 *
 */
public class HW3 {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Missing file name");
            System.exit(0);
        }

        Game g = new Game();
        try {
            g.loadGameState(args[0]);
            System.out.println("Initial board state");
            g.getBoard().displayBoard();
            /*try {
                PrintWriter writer = new PrintWriter("bricks-level7-output-part2.txt");
                writer.println("******** BRICKS-LEVEL 7 ********");
                writer.println();
                writer.println("Initial board state");
                writer.printf("%d,%d,", g.getBoard().getWidth(), g.getBoard().getHeight());
                writer.println();
                for (int row = 0; row < g.getBoard().getHeight(); row++) {
                    for (int column = 0; column < g.getBoard().getWidth(); column++) {
                        writer.printf("%d,", g.getBoard().getGameBoard()[row][column]);
                    }
                    writer.println();
                }
                writer.println();
                // do a* search
                g.aStarM();

                writer.println("********** A* WITH MANHATTAN **********");
                writer.println();
                writer.println("Number of nodes explored: " + g.getNumNodesExplored());
                writer.println("Length of solution: " + g.getGoalNode().getHistory().size());
                writer.printf("Time taken to complete: %.0f ms", g.getTimeTaken());
                writer.println();
                for (Move m : g.getGoalNode().getHistory()) {
                    writer.printf("(%d,%s)", m.getMovePiece().getPieceNumber(), m.getSelectedMove());
                    writer.println();
                }

                // print solution board
                writer.printf("%d,%d,", g.getBoard().getWidth(), g.getBoard().getHeight());
                writer.println();
                for (int row = 0; row < g.getBoard().getHeight(); row++) {
                    for (int column = 0; column < g.getBoard().getWidth(); column++) {
                        writer.printf("%d,", g.getGoalNode().getBoard().getGameBoard()[row][column]);
                    }
                    writer.println();
                }

                writer.flush();
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            g.aStarM();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File name not found");
        }

    }
}