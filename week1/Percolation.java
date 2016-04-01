import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation { 
	private WeightedQuickUnionUF uf;
	private WeightedQuickUnionUF uf_backwash;
	private int N;
	private boolean[] arrayOpen;
	
	public Percolation(int N)               // create N-by-N grid, with all sites blocked
	{
		if(N<=0){
			throw new IllegalArgumentException();
		}
		this.N=N;
		uf= new WeightedQuickUnionUF(N*N+2);
		uf_backwash= new WeightedQuickUnionUF(N*N+1);
		arrayOpen=new boolean[N*N+2];
		arrayOpen[0]=true;
		arrayOpen[N*N+1]=true;
	}
	public void open(int i, int j)          // open site (row i, column j) if it is not open already
	{
		if(i<1||j<1||i>N||j>N){
			throw new IndexOutOfBoundsException("out of bounds");
		}
		if(arrayOpen[(i-1)*N+j]){
			return;
		}else{
			arrayOpen[(i-1)*N+j]=true;
		}
		if(j>1 &&arrayOpen[(i-1)*N+j-1]){
			uf.union((i-1)*N+j, (i-1)*N+j-1);
			uf_backwash.union((i-1)*N+j, (i-1)*N+j-1);
		}
		if(j<N &&arrayOpen[(i-1)*N+j+1]){
			uf.union((i-1)*N+j, (i-1)*N+j+1);
			uf_backwash.union((i-1)*N+j, (i-1)*N+j+1);
		}
		if(i>1 &&arrayOpen[(i-2)*N+j]){
			uf.union((i-1)*N+j, (i-2)*N+j);
			uf_backwash.union((i-1)*N+j, (i-2)*N+j);
		}
		if(i<N &&arrayOpen[i*N+j]){
			uf.union((i-1)*N+j, i*N+j);
			uf_backwash.union((i-1)*N+j, i*N+j);
		}
		if(i==1){
			uf.union((i-1)*N+j, 0);
			uf_backwash.union((i-1)*N+j, 0);
		}
		if(i==N){
			uf.union((i-1)*N+j, N*N+1);
		}
	}
	public boolean isOpen(int i, int j)     // is site (row i, column j) open?
	{
		if(i<1||j<1||i>N||j>N){
			throw new IndexOutOfBoundsException("out of bounds");
		}
		return arrayOpen[(i-1)*N+j];
	}
	public boolean isFull(int i, int j)     // is site (row i, column j) full?
	{
		if(i<1||j<1||i>N||j>N){
			throw new IndexOutOfBoundsException("out of bounds");
		}
		return uf_backwash.connected((i-1)*N+j, 0)&&arrayOpen[(i-1)*N+j];
	}
	public boolean percolates()             // does the system percolate?
	{
		return uf.connected(0, N*N+1);
	}
}  