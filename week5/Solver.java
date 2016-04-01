import edu.princeton.cs.algs4.*;
import java.util.Comparator;
import java.util.Stack;
import java.lang.Comparable;

public class Solver {
	private boolean isSolve = false;
	private int move = -1;
	
	private class Node implements Comparable<Node>
	{
		private final Board board;
		private final int move;
		private final int priority;
		private final Node previous;
		private final boolean isTwin;
		public Node(Board board, int move, Node previous, boolean isTwin)
		{
			this.board = board;
			this.move = move;
			this.priority = board.manhattan()+move;
			this.previous = previous;
			this.isTwin = isTwin;
		}
		public int compareTo(Node that)
		{
			if(this.board.equals(that.board)) return 0;
			if(this.priority<that.priority) return -1;
			else return 1;
		}
	}
	private MinPQ<Node> minPQ = new MinPQ<Node>(new Comparator<Node>()
	{
		public int compare(Node w1, Node w2)
		{
			if(w1.priority<w2.priority) return -1;
			else if(w1.priority>w2.priority) return 1;
			else return 0;
		}
	});
	
	private Stack<Board> solutionQueue = new Stack<Board>();
	private Stack<Board> solutionT = new Stack<Board>();
	
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
    	if(initial == null) throw new NullPointerException();
    	Board initialTwin = initial.twin();
    	Node initNode = new Node(initial, 0, null ,false);
    	Node initNodeTwin = new Node(initialTwin, 0, null, true);
    	minPQ.insert(initNode);
    	minPQ.insert(initNodeTwin);
    	solve();
    }
    
    private void solve()
    {
    	while(true){
    		Node node = minPQ.delMin();
    		if(node.board.isGoal())
    		{
    			if(node.isTwin)
    			{
    				isSolve = false;
    				move = -1;
    			}else
    			{
    				isSolve = true;
    				move = node.move;
    				solutionQueue.push(node.board);
    				while(node.previous!=null){
    					node = node.previous;
    					solutionQueue.push(node.board);
    				}
    			}
    			break;
    		}else
    		{
    			for(Board b : node.board.neighbors())
    			{
    				Node neiNode = new Node(b, node.move+1, node, node.isTwin);
    				if(node.previous==null) 
    					minPQ.insert(neiNode);
    				else if(!node.previous.board.equals(neiNode.board))
    					minPQ.insert(neiNode);
    			}
    		}
    	}
    }
   
    public boolean isSolvable()            // is the initial board solvable?
    {
    	return isSolve;
    }
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
    	return move;
    }
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
    	if(isSolve){
    		while(!solutionQueue.empty())
    		{
    			solutionT.push(solutionQueue.pop());
    		}
    		return solutionT;
    	}
    	else return null;
    }
    public static void main(String[] args) // solve a slider puzzle (given below)
    {
    	In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}