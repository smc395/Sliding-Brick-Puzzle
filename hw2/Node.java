import java.util.ArrayList;

public class Node {

    private Board nodeBoard;
    private ArrayList<Move> history = new ArrayList<Move>();

    public Node(Board board){
        nodeBoard = board;
    }
    
    public void setBoard(Board board){
        nodeBoard = board;
    }
    
    public void addToHistory(Move move){
        history.add(move);
    }
    
    public void setHistory(ArrayList<Move> h){
        for(int i = 0; i < h.size(); i++){
            addToHistory(h.get(i));
        }
    }
    
    public ArrayList<Move> getHistory(){
        return history;
    }
    
    public Board getBoard(){
        return nodeBoard;
    }
}
