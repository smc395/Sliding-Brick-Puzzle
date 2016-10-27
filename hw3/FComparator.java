import java.util.Comparator;

/**
 * Comparator used in A* Search Manhattan to determine what node to explore next
 * @author Sung Yan Chao
 *
 */
public class FComparator implements Comparator<Node> {

    /**
     * Compares two node's f(node)
     */
    @Override
    public int compare(Node o1, Node o2) {
        if(o1.getfCost() < o2.getfCost()){
            return -1;
        }
        if(o1.getfCost() > o2.getfCost()){
            return 1;
        }
        return 0;
    }

}
