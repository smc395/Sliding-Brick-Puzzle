import java.io.IOException;
import java.io.PrintWriter;

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
            g.getBoard().displayBoard();
            /*try {
                PrintWriter writer = new PrintWriter("dfs-level0-output-part2.txt");
                writer.println("Initial board state");
                writer.printf("%d,%d,", g.getBoard().getWidth(), g.getBoard().getHeight());
                writer.println();
                for (int row = 0; row < g.getBoard().getHeight(); row++) {
                    for (int column = 0; column < g.getBoard().getWidth(); column++) {
                        writer.printf("%d,", g.getBoard().getGameBoard()[row][column]);
                    }
                    writer.println();
                }

                // do breadth first search
                g.bfs();

                writer.println("Number of nodes explored: " + g.getNumNodesExplored());
                writer.println("Length of solution: " + g.getSolutionPath().size());
                writer.printf("Time taken to complete: %.0f ms", g.getTimeTaken());
                writer.println();
                for (Move m : g.getSolutionPath()) {
                    writer.printf("(%d,%s)", m.getMovePiece().getPieceNumber(), m.getSelectedMove());
                    writer.println();
                }

                writer.printf("%d,%d,", g.getBoard().getWidth(), g.getBoard().getHeight());
                writer.println();
                for (int row = 0; row < g.getBoard().getHeight(); row++) {
                    for (int column = 0; column < g.getBoard().getWidth(); column++) {
                        writer.printf("%d,", g.getBoard().getGameBoard()[row][column]);
                    }
                    writer.println();
                }

                writer.flush();
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            // g.randomWalk(g.getBoard(), 100);
            System.out.println("Breadth First Search:");
            g.bfs();
            System.out.println();
            
            System.out.println("Depth First Search:");
            g.dfs();
            System.out.println();
            
            System.out.println("Iterative Deepening Search:");
            g.ids();
            System.out.println();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File name not found");
        }

    }
}