import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class HW1 {

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
			System.out.println("Initial board state");
			g.getBoard().displayBoard();
			System.out.println();
			/*Test applyMove
			
			Move m = new Move();
			ArrayList<Position> pos = new ArrayList<Position>();
			pos.add(new Position(1,1));
			pos.add(new Position(2,1));
			m.setMovePiece(g.getBoard().getPieces().get(3));
			m.setSelectedMove(pos);
			
			g.applyMove(g.getBoard(), m);
			*/
			
			/*Test applyMoveCloning 
			
			Move m = new Move();
            ArrayList<Position> pos = new ArrayList<Position>();
            pos.add(new Position(1,1));
            pos.add(new Position(2,1));
            m.setMovePiece(g.getBoard().getPieces().get(3));
            m.setSelectedMove(pos);
            
            Board cloneBoard = g.applyMoveCloning(g.getBoard(), m);
			
            
            System.out.println("Original board");
            g.getBoard().displayBoard();
            
            System.out.println(g.identicalStates(g.getBoard(), cloneBoard));
            */
            
            /*Test listAllPieceMoves()
			  
			Piece piece8 = g.getBoard().getPieces().get(3);
			piece8.printPositions();
			Move m = new Move();
			Map<Direction, ArrayList<Position>> moves =m.listAllPieceMoves(piece8,g.getBoard());
			
			for(Direction d : moves.keySet()){
			    System.out.printf("%s positions: ", d);
			    for(int i =0;i < moves.get(d).size(); i++){
			        System.out.printf("(%d,%d) ",moves.get(d).get(i).getRow(),moves.get(d).get(i).getColumn());
			    }
			    System.out.println();
			}*/
			
			
			/* Test listAllMoves() 
			 
			Move m = new Move();
			Map<Piece, Map<Direction, ArrayList<Position>>> possibleMoves = m.listAllMoves(g.getBoard());
			for(Piece p : possibleMoves.keySet()){
			    System.out.printf("Piece #%d possible moves:\n", p.getPieceNumber());
			    for(Direction d : possibleMoves.get(p).keySet()){
			        System.out.printf("%s positions: ", d);
			        for(int i =0;i < possibleMoves.get(p).get(d).size(); i++){
			            System.out.printf("(%d,%d) ",possibleMoves.get(p).get(d).get(i).getRow(),possibleMoves.get(p).get(d).get(i).getColumn());
			        }
			        System.out.println();
		        }
			    System.out.println();
			}
			*/
			
			/* Test normalize() */
			/*for(int p : g.getBoard().getPieces().keySet()){
			    System.out.printf("Piece number before %d\n", g.getBoard().getPieces().get(p).getPieceNumber());
			}*/
			
			g.normalize(g.getBoard());
			
			/*for(int p : g.getBoard().getPieces().keySet()){
                System.out.printf("Piece number after %d\n", g.getBoard().getPieces().get(p).getPieceNumber());
            }*/
			g.getBoard().displayBoard();
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("File name not found");
		}
		
		// g.getBoard().cloneBoard().displayBoard();
		//g.puzzleCompleteCheck();
	}
}