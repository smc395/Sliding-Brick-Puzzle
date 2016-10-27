import java.util.ArrayList;

/**
 * A Node object represents the nodes that build the graph for traversal. It has a {@link Board} and
 * a the moves to get to this node.
 * 
 * Additionally, it has the total estimated cost it takes to get to this node.
 * @author Sung Yan Chao
 *
 */
public class Node {

    private Board nodeBoard;
    private ArrayList<Move> history = new ArrayList<Move>();
    private int fCost = 0;

    /**
     * Constructor
     * @param board - {@link Board}
     */
    public Node(Board board) { nodeBoard = board; }
    
    /**
     * add the move to the history of the node
     * @param move - {@link Move}
     */
    public void addToHistory(Move move) { history.add(move); }
    
    // ********************* SETTERS *********************
    /**
     * set the node's board to the passed board
     * @param board - {@link Board}
     */
    public void setBoard(Board board) { nodeBoard = board; }

    /**
     * sets the node's history to a list of moves
     * @param h - history of nodes
     */
    public void setHistory(ArrayList<Move> h) {
        for (int i = 0; i < h.size(); i++) {
            addToHistory(h.get(i));
        }
    }
    
    /**
     * sets the total estimated cost of the node
     * @param cost - f(node)
     */
    public void setfCost(int cost) { fCost = cost; }

    // ********************* GETTERS *********************
    public ArrayList<Move> getHistory() { return history; }

    public Board getBoard() { return nodeBoard;  }

    public int getfCost() { return fCost; }
}
