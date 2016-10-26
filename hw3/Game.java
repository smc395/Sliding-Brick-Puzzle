import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class Game {

    private Board board;
    private boolean solved = false;
    private int numNodesExplored = 0;
    private double timeTaken = 0.0;
    private ArrayList<Move> solutionPath = new ArrayList<Move>();
    private ArrayList<Board> visitedStates = new ArrayList<Board>();
    private Queue<Node> queue = new LinkedList<Node>();
    private Node goalNode;
    private PriorityQueue<Node> pQueue = new PriorityQueue<Node>(1, new FComparator());
    
    public void loadGameState(String fileName) throws NumberFormatException, IOException {

        // read a file from state folder
        BufferedReader reader = new BufferedReader(new FileReader("./state/" + fileName));
        boolean firstLine = true;
        int row = 0;
        int width = 0;
        int height = 0;
        int[][] newBoard = null;
        String line;

        while ((line = reader.readLine()) != null) {
            String[] currentLine = line.split(",");

            // create board of width,height
            if (firstLine == true) {
                width = Integer.parseInt(currentLine[0]);
                height = Integer.parseInt(currentLine[1]);
                firstLine = false;
                newBoard = new int[height][width];
                continue;
            }

            // populate board state
            for (int column = 0; column < currentLine.length; column++) {
                newBoard[row][column] = Integer.parseInt(currentLine[column]);
            }
            row++;
        }
        reader.close();

        // create board for game
        board = new Board(width, height, newBoard);
    }

    public void setBoard(Board b) {
        board = b;
    }

    public Board getBoard() {
        return board;
    }

    public double getTimeTaken() {
        return timeTaken;
    }

    public int getNumNodesExplored() {
        return numNodesExplored;
    }

    public ArrayList<Move> getSolutionPath() {
        return solutionPath;
    }

    public boolean getPuzzleSolved() {
        return solved;
    }

    public void puzzleCompleteCheck(Board board) {
        boolean goalSpaceExist = false;
        // loop through each cell in matrix for -1
        for (int row = 0; row < board.getHeight(); row++) {
            for (int column = 0; column < board.getWidth(); column++) {
                if (board.getGameBoard()[row][column] == -1) {
                    goalSpaceExist = true;
                }
            }
        }
        if (goalSpaceExist == false) {
            solved = true;
            System.out.println("Puzzle solved!");
        }
    }

    public void applyMove(Board board, Move move) {
        board.setGameBoard(move.getMoveBoard().getGameBoard());
        board.populatePieces();
    }

    public Board applyMoveCloning(Board board, Move move) {
        Board newBoard = board.cloneBoard();
        applyMove(newBoard, move);
        return newBoard;
    }

    public boolean identicalStates(Board b1, Board b2) {
        for (int k = 0; k < b1.getHeight(); k++) {
            for (int m = 0; m < b1.getWidth(); m++) {
                if (b1.getGameBoard()[k][m] != b2.getGameBoard()[k][m]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void normalize(Board board) {

        int nextIdx = 3;
        for (int row = 0; row < board.getHeight(); row++) {
            for (int column = 0; column < board.getWidth(); column++) {
                int cellValue = board.getGameBoard()[row][column];
                if (cellValue == nextIdx) {
                    nextIdx++;
                } else if (cellValue > nextIdx) {
                    swapIdx(nextIdx, cellValue, board);
                    nextIdx++;
                }
            }
        }
        board.populatePieces();
        // setBoard(board);
    }

    private void swapIdx(int idx1, int idx2, Board board) {

        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                if (board.getGameBoard()[i][j] == idx1) {
                    board.getGameBoard()[i][j] = idx2;
                } else if (board.getGameBoard()[i][j] == idx2) {
                    board.getGameBoard()[i][j] = idx1;
                }
            }
        }
    }

    public void randomWalk(Board board, int n) {

        int count = 0;
        while (solved != true && count < n) {
            board.displayBoard();
            // generate all the moves possible on board
            ArrayList<Move> allMoves = listAllMoves(board);

            // choose a possible move at random
            Random random = new Random();
            int selected = random.nextInt(allMoves.size());

            // get selected random move
            Move move = allMoves.get(selected);
            move.displayMove();

            // applyMove
            applyMove(board, move);

            // normalize resulting game state
            normalize(board);

            // check if puzzle solved
            puzzleCompleteCheck(this.board);

            // increment loop count
            count++;
        }
        board.displayBoard();
    }

    public ArrayList<Move> listAllPieceMoves(Piece piece, Board board) {
        boolean moveRight = true, moveLeft = true, moveUp = true, moveDown = true;

        int[][] gameBoard = board.getGameBoard();

        // first get piece current positions
        ArrayList<Position> piecePositions = piece.getPostions();
        int positionSize = piecePositions.size();

        // Check what direction piece can move
        for (int j = 0; j < positionSize; j++) {
            int pieceRow = piecePositions.get(j).getRow();
            int pieceColumn = piecePositions.get(j).getColumn();

            /*
             * check right if piece is the same piece, another piece, a wall and
             * if the piece is a master piece to be able to move to the goal
             * position
             */
            // Must check if master piece too
            if ((gameBoard[pieceRow][pieceColumn + 1] != piece.getPieceNumber()
                    && gameBoard[pieceRow][pieceColumn + 1] >= 1)
                    || (gameBoard[pieceRow][pieceColumn + 1] < 0 && !piece.isMasterPiece())) {
                moveRight = false;
            }
            // check left
            if ((gameBoard[pieceRow][pieceColumn - 1] != piece.getPieceNumber()
                    && gameBoard[pieceRow][pieceColumn - 1] >= 1)
                    || (gameBoard[pieceRow][pieceColumn - 1] < 0 && !piece.isMasterPiece())) {
                moveLeft = false;
            }
            // check up
            if ((gameBoard[pieceRow - 1][pieceColumn] != piece.getPieceNumber()
                    && gameBoard[pieceRow - 1][pieceColumn] >= 1)
                    || (gameBoard[pieceRow - 1][pieceColumn] < 0 && !piece.isMasterPiece())) {
                moveUp = false;
            }
            // check down
            if ((gameBoard[pieceRow + 1][pieceColumn] != piece.getPieceNumber()
                    && gameBoard[pieceRow + 1][pieceColumn] >= 1)
                    || (gameBoard[pieceRow + 1][pieceColumn] < 0 && !piece.isMasterPiece())) {
                moveDown = false;
            }
        }

        ArrayList<Move> moves = new ArrayList<Move>(4);

        // create Move objects for all the moves the piece can make
        if (moveRight) {
            int[][] b = board.cloneBoard().getGameBoard();
            for (int k = positionSize-1; k >= 0; k--) {
                int pieceRow = piecePositions.get(k).getRow();
                int pieceColumn = piecePositions.get(k).getColumn();
                
                // if there is an empty space then start from end of list and swap
                if(b[pieceRow][pieceColumn + 1] == 0){
                    int temp = b[pieceRow][pieceColumn];
                    b[pieceRow][pieceColumn] = b[pieceRow][pieceColumn + 1];
                    b[pieceRow][pieceColumn + 1] = temp;
                } else{
                    // replace piece and don't swap 
                    b[pieceRow][pieceColumn + 1] = piece.getPieceNumber();
                    b[pieceRow][pieceColumn] = 0;
                }
                
            }
            Move m = new Move(board.getWidth(), board.getHeight(), b);
            m.setMovePiece(piece);
            m.setSelectedMove(Direction.RIGHT);
            m.calculateManhattan();
            moves.add(m);
        }
        if (moveLeft) {
            int[][] b = board.cloneBoard().getGameBoard();
            for (int k = 0; k < positionSize; k++) {
                int pieceRow = piecePositions.get(k).getRow();
                int pieceColumn = piecePositions.get(k).getColumn();
                // if there is an empty space then start from beginning of list and swap
                if(b[pieceRow][pieceColumn - 1] == 0){
                    int temp = b[pieceRow][pieceColumn];
                    b[pieceRow][pieceColumn] = b[pieceRow][pieceColumn - 1];
                    b[pieceRow][pieceColumn - 1] = temp;
                } else{
                    // replace piece and don't swap 
                    b[pieceRow][pieceColumn - 1] = piece.getPieceNumber();
                    b[pieceRow][pieceColumn] = 0;
                }
            }
            Move m = new Move(board.getWidth(), board.getHeight(), b);
            m.setMovePiece(piece);
            m.setSelectedMove(Direction.LEFT);
            m.calculateManhattan();
            moves.add(m);
        }
        if (moveUp) {
            int[][] b = board.cloneBoard().getGameBoard();
            for (int k = 0; k < positionSize; k++) {
                int pieceRow = piecePositions.get(k).getRow();
                int pieceColumn = piecePositions.get(k).getColumn();
                // if there is an empty space then start from beginning of list and swap
                if(b[pieceRow - 1][pieceColumn] == 0){
                    int temp = b[pieceRow][pieceColumn];
                    b[pieceRow][pieceColumn] = b[pieceRow - 1][pieceColumn];
                    b[pieceRow - 1][pieceColumn] = temp;
                } else{
                    // replace piece and don't swap 
                    b[pieceRow - 1][pieceColumn] = piece.getPieceNumber();
                    b[pieceRow][pieceColumn] = 0;
                }
            }
            Move m = new Move(board.getWidth(), board.getHeight(), b);
            m.setMovePiece(piece);
            m.setSelectedMove(Direction.UP);
            m.calculateManhattan();
            moves.add(m);
        }
        if (moveDown) {
            int[][] b = board.cloneBoard().getGameBoard();
            for (int k = positionSize-1; k >=0; k--) {
                int pieceRow = piecePositions.get(k).getRow();
                int pieceColumn = piecePositions.get(k).getColumn();
                // if there is an empty space then start from end of list and swap
                if(b[pieceRow + 1][pieceColumn] == 0){
                    int temp = b[pieceRow][pieceColumn];
                    b[pieceRow][pieceColumn] = b[pieceRow + 1][pieceColumn];
                    b[pieceRow + 1][pieceColumn] = temp;
                } else{
                    // replace piece and don't swap 
                    b[pieceRow + 1][pieceColumn] = piece.getPieceNumber();
                    b[pieceRow][pieceColumn] = 0;
                }
            }
            Move m = new Move(board.getWidth(), board.getHeight(), b);
            m.setMovePiece(piece);
            m.setSelectedMove(Direction.DOWN);
            m.calculateManhattan();
            moves.add(m);
        }

        return moves;
    }

    public ArrayList<Move> listAllMoves(Board board) {
        ArrayList<Move> allMoves = new ArrayList<Move>();
        for (int i = 0; i < board.getPieces().size(); i++) {
            ArrayList<Move> pieceMoves = listAllPieceMoves(board.getPieces().get(i + 2), board);
            if (!pieceMoves.isEmpty()) {
                for (int m = 0; m < pieceMoves.size(); m++) {
                    allMoves.add(pieceMoves.get(m));
                }
            }
        }
        return allMoves;
    }

    public void bfs() {
        solved = false;
        visitedStates.clear();
        numNodesExplored = 0;

        double startTime = System.currentTimeMillis();

        // create root node
        Node rootNode = new Node(board);
        queue.add(rootNode);

        Node currentNode = null;

        // do breadth first search
        while (!solved) {

            // dequeue Node
            currentNode = queue.remove();

            // add node to list of all visited node
            visitedStates.add(currentNode.getBoard());

            bfsearch(currentNode);
        }

        double endTime = System.currentTimeMillis();

        timeTaken = endTime - startTime;

        solutionPath = currentNode.getHistory();

        System.out.println("Number of nodes explored: " + numNodesExplored);
        System.out.println("Length of solution: " + currentNode.getHistory().size());
        System.out.printf("Time taken to complete: %.0f ms\n", timeTaken);
        for (Move m : currentNode.getHistory()) {
            m.displayMove();
        }

        currentNode.getBoard().displayBoard();
    }

    private void bfsearch(Node n) {

        // check if state is goal state
        puzzleCompleteCheck(n.getBoard());

        if (solved == false) {

            // get board and list all possible moves
            ArrayList<Move> availableStates = listAllMoves(n.getBoard());

            /*
             * for each possible move, determine if they were previous states if
             * so add to the queue of nodes next to explore, else do not add to
             * the queue
             */
            int visitedSize = visitedStates.size();
            for (Move move : availableStates) {
                Board mBoard = move.getMoveBoard();

                /*
                 * boolean flag before looping through each visited state to see
                 * if it is a previous state
                 */

                boolean sameState = false;

                // check if the move would bring us back to a state we've been in before
                for (int i = 0; i < visitedSize; i++) {

                    Board visitedState = visitedStates.get(i);

                    boolean inPrevState = identicalStates(mBoard, visitedState);

                    // if not, then add the node to the queue to expand
                    if (inPrevState == true) {
                        sameState = true;
                        break;
                    }
                }
                if (!sameState) {
                    numNodesExplored++;
                    Board newBoard = applyMoveCloning(n.getBoard(), move);
                    visitedStates.add(newBoard);
                    Node node = new Node(newBoard);

                    // add to the history of the node the move that took it to that node
                    node.setHistory(n.getHistory());
                    node.addToHistory(move);
                    queue.add(node);
                }
            }
        }
    }

    public void dfs() {
        solved = false;
        visitedStates.clear();
        numNodesExplored = 0;

        double startTime = System.currentTimeMillis();

        // create root node
        Node rootNode = new Node(board);

        // do depth first search
        while (!solved) {
            dfsearch(rootNode);
        }

        double endTime = System.currentTimeMillis();

        timeTaken = endTime - startTime;

        System.out.println("Number of nodes explored: " + numNodesExplored);
        System.out.println("Length of solution: " + goalNode.getHistory().size());
        System.out.printf("Time taken to complete: %.0f ms\n", timeTaken);
        for (Move m : goalNode.getHistory()) {
            m.displayMove();
        }

        this.board.displayBoard();
    }

    private void dfsearch(Node n) {
        // check if node is goal state
        puzzleCompleteCheck(n.getBoard());

        // if goal state, set n to goalNode and return
        if (solved == true) {
            goalNode = n;
            return;
        } else {
            // add board to visited states
            visitedStates.add(n.getBoard());

            ArrayList<Move> availableStates = listAllMoves(n.getBoard());
            int visitedSize = visitedStates.size();

            // for each move and game isn't solved, check if move is a visitedState
            for (Move move : availableStates) {
                if (solved == false) {
                    boolean sameState = false;
                    Board mBoard = move.getMoveBoard();
                    for (int i = 0; i < visitedSize; i++) {

                        Board visitedState = visitedStates.get(i);
                        boolean inPrevState = identicalStates(mBoard, visitedState);

                        if (inPrevState == true) {
                            sameState = true;
                            break;
                        }
                    }
                    if (sameState) {
                        continue;
                    } else {
                        numNodesExplored++;

                        // Create node with move applied to its board
                        Board newBoard = applyMoveCloning(n.getBoard(), move);
                        Node node = new Node(newBoard);

                        // add to the history of the node the move that took it to that node
                        node.setHistory(n.getHistory());
                        node.addToHistory(move);
                        dfsearch(node);
                    }
                } else {
                    return;
                }
            }
        }
    }

    public void ids() {

        solved = false;
        visitedStates.clear();

        double startTime = System.currentTimeMillis();

        // create root node
        Node rootNode = new Node(board);

        // add board to visited states
        visitedStates.add(rootNode.getBoard());

        for (int depth = 0; depth < 1000; depth++) {
            if (!solved) {
                visitedStates.clear();
                numNodesExplored = 0;
                idsearch(rootNode, depth);
            } else {
                break;
            }
        }

        double endTime = System.currentTimeMillis();

        timeTaken = endTime - startTime;

        System.out.println("Number of nodes explored: " + numNodesExplored);
        System.out.println("Length of solution: " + goalNode.getHistory().size());
        System.out.printf("Time taken to complete: %.0f ms\n", timeTaken);
        for (Move m : goalNode.getHistory()) {
            m.displayMove();
        }

        this.board.displayBoard();
    }

    private void idsearch(Node n, int depth) {
        puzzleCompleteCheck(n.getBoard());

        if (solved) {
            goalNode = n;
            return;
        } else if (depth == 0) {
            return;
        } else {
            // list all possible moves
            ArrayList<Move> availableStates = listAllMoves(n.getBoard());
            int visitedSize = visitedStates.size();

            // do a depth first search on each on
            for (Move move : availableStates) {
                if (solved == false) {
                    boolean sameState = false;
                    Board mBoard = move.getMoveBoard();
                    for (int i = 0; i < visitedSize; i++) {

                        Board visitedState = visitedStates.get(i);
                        boolean inPrevState = identicalStates(mBoard, visitedState);

                        if (inPrevState == true) {
                            sameState = true;
                            break;
                        }
                    }
                    if (sameState) {
                        continue;
                    } else {
                        numNodesExplored++;

                        // Create node with move applied to its board
                        Board newBoard = applyMoveCloning(n.getBoard(), move);
                        Node node = new Node(newBoard);

                        // add board to visited states
                        visitedStates.add(newBoard);

                        // add to the history of the node the move that took it to that node
                        node.setHistory(n.getHistory());
                        node.addToHistory(move);
                        idsearch(node, depth - 1);
                    }
                } else {
                    return;
                }
            }
        }
    }

    public void as(){
        solved = false;
        visitedStates.clear();
        numNodesExplored = 0;

        double startTime = System.currentTimeMillis();

        // create root node
        normalize(board);
        Node rootNode = new Node(board);
        pQueue.add(rootNode);
        
        // add board to visited states
        visitedStates.add(rootNode.getBoard());

        Node currentNode = null;
        while(!solved){
            currentNode = pQueue.poll();
            numNodesExplored++;
            aStarSearch(currentNode);
        }

        double endTime = System.currentTimeMillis();

        timeTaken = endTime - startTime;

        System.out.println("Number of nodes explored: " + numNodesExplored);
        System.out.println("Length of solution: " + currentNode.getHistory().size());
        System.out.printf("Time taken to complete: %.0f ms\n", timeTaken);
        for (Move m : currentNode.getHistory()) {
            m.displayMove();
        }

        currentNode.getBoard().displayBoard();
    }
    
    public void aStarSearch(Node n){
        normalize(n.getBoard());
        
        // check if state is goal state
        puzzleCompleteCheck(n.getBoard());

        if (solved == false) {

            // get board and list all possible moves
            ArrayList<Move> availableStates = listAllMoves(n.getBoard());

            /*next Node
            Move selectedNode;
            
            //loop through the moves and calculate f(n) for each move and place moves in order of smallest f to largest
            int smallest=1000;
            for (Move move : availableStates){
                move.setCost(n.getHistory().size());
                int f = move.calculateF();
                if(f < smallest){
                    selectedNode = move;
                    smallest = f;
                }
            }
            
            /*
             * for each possible move, determine if they were previous states if
             * so add to the queue of nodes next to explore, else do not add to
             * the queue
             */
            int visitedSize = visitedStates.size();
            for (Move move : availableStates) {
                Board mBoard = move.getMoveBoard();
                normalize(mBoard);
                move.setCost(n.getHistory().size()+1);

                /*
                 * boolean flag before looping through each visited state to see
                 * if it is a previous state
                 */

                boolean sameState = false;

                // check if the move would bring us back to a state we've been in before
                for (int i = 0; i < visitedSize; i++) {

                    Board visitedState = visitedStates.get(i);
                    normalize(visitedState);

                    boolean inPrevState = identicalStates(mBoard, visitedState);

                    // if not, then add the node to the queue to expand
                    if (inPrevState == true) {
                        sameState = true;
                        break;
                    }
                }
                if (!sameState) {
                    

                    Board newBoard = applyMoveCloning(n.getBoard(), move);
                    normalize(newBoard);
                    visitedStates.add(newBoard);
                    Node node = new Node(newBoard);
                    node.setfCost(move.calculateF());
                    
                    // add to the history of the node the move that took it to that node
                    node.setHistory(n.getHistory());
                    node.addToHistory(move);
                    pQueue.add(node);
                }
            }
        }
    }
}// end of Game class