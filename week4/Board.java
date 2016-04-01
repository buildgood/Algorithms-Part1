import java.util.Arrays;
import java.util.Stack;

public class Board {
	
    private final int[][] blocks;
	private final int N;
	
	public Board(int[][] blocks)           // construct a board from an N-by-N array of blocks
	{                                       // (where blocks[i][j] = block in row i, column j)
		N = blocks.length;
		this.blocks = new int[N][];
		for(int i=0;i<N;i++)
		{
			this.blocks[i]=Arrays.copyOf(blocks[i], N);
		}
	}
    public int dimension()                 // board dimension N
    {
    	return N;
    }
    public int hamming()                   // number of blocks out of place
    {
    	int hamCount = 0;
    	for(int i=0;i<N;i++)
    		for(int j=0;j<N;j++)
    		{
    			if(blocks[i][j]!=0&&blocks[i][j]!=i*N+j+1)
    				hamCount++;
    		}
    	return hamCount;
    }
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
    	int manCount=0;
    	for(int i=0;i<N;i++)
    		for(int j=0;j<N;j++)
    		{
    			if(blocks[i][j]!=0)
    			{
    				manCount+=Math.abs((blocks[i][j]-1)/N-i)+Math.abs((blocks[i][j]-1)%N-j);
    			}
    		}
    	return manCount;
    }
    public boolean isGoal()                // is this board the goal board?
    {
    	return hamming() == 0;
    }
    private int[][] copyBoard()
    {
    	int[][] b = new int [N][N];
    	for(int i=0;i<N;i++)
		{
			b[i]=Arrays.copyOf(blocks[i], N);
		}
    	return b;
    }
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
    	int[][] twinBoard = copyBoard();
    	if(blocks[0][0]!=0&&blocks[0][1]!=0)
    	{
    		int tmp=twinBoard[0][0];
    		twinBoard[0][0]=twinBoard[0][1];
    		twinBoard[0][1]=tmp;
    	}else
    	{
    		int tmp=twinBoard[1][0];
    		twinBoard[1][0]=twinBoard[1][1];
    		twinBoard[1][1]=tmp;
    	}
    	return new Board(twinBoard);
    }
    public boolean equals(Object y)        // does this board equal y?
    {
    	if(y==this) return true;
    	if(y==null) return false;
    	if(y.getClass()!=this.getClass()) return false;
    	Board copy = (Board)y;
    	if(this.N!=copy.N) return false;
    	int[][] copyBlocks = copy.blocks;
    	for(int i=0;i<N;i++)
    		for(int j=0;j<N;j++)
    		{
    			if(blocks[i][j]!=copyBlocks[i][j])
    				return false;
    		}
    	return true;
    }
    public Iterable<Board> neighbors()     // all neighboring boards
    {
    	int blankX = 0,blankY = 0;
    		Stack<Board> boards = new Stack<Board>();
    		for(int i=0;i<N;i++)
    			for(int j=0;j<N;j++){
    				if(blocks[i][j]==0)
    				{
    					blankX=i;
    					blankY=j;
    				}
    			}
    		if(blankX!=0){
    			int[][] nb = copyBoard();
    			nb[blankX][blankY]=nb[blankX-1][blankY];
    			nb[blankX-1][blankY]=0;
    			boards.push(new Board(nb));
    		}
    		if(blankX!=N-1){
    			int[][] nb = copyBoard();
    			nb[blankX][blankY]=nb[blankX+1][blankY];
    			nb[blankX+1][blankY]=0;
    			boards.push(new Board(nb));	
    		}
    		if(blankY!=0){
    			int[][] nb = copyBoard();
    			nb[blankX][blankY]=nb[blankX][blankY-1];
    			nb[blankX][blankY-1]=0;
    			boards.push(new Board(nb));		
    		}
    		if(blankY!=N-1){
    			int[][] nb = copyBoard();
    			nb[blankX][blankY]=nb[blankX][blankY+1];
    			nb[blankX][blankY+1]=0;
    			boards.push(new Board(nb));	
    		}
    		return boards;
    }
    public String toString()               // string representation of this board (in the output format specified below)
    {
    	StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

}