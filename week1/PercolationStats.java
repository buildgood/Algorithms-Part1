import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {  
    private double recT[];
    private double res_mean;
    private double res_stddev;
    private int N;
    private int T;
    
	public PercolationStats(int N, int T)     // perform T independent experiments on an N-by-N grid
	{
		if(N<=0||T<=0){
			throw new IllegalArgumentException("illegal");
		}
		recT = new double[T];
		this.N=N;
		this.T=T;
		int times=0;
		while(times<T){
			Percolation percolation =new Percolation(N);
			boolean[] arrOpen= new boolean[N*N+2];
			int count=0;
			while(true){
				while(true){
					int x=StdRandom.uniform(N)+1;
					int y=StdRandom.uniform(N)+1;
					if(arrOpen[(x-1)*N+y]){
						continue;
					}else{
						percolation.open(x, y);
						arrOpen[(x-1)*N+y]=true;
						break;
					}
				}
				count++;
				if(percolation.percolates()){
					recT[times]=(double)count/((double)N*(double)N);
					break;
				}
			}
			times++;
		}
		this.res_mean = StdStats.mean(recT);
		this.res_stddev= StdStats.stddev(recT);
	}
	public double mean()                      // sample mean of percolation threshold
	{
		return this.res_mean;
	}
	public double stddev()                    // sample standard deviation of percolation threshold
	{
		return this.res_stddev;
	}
	public double confidenceLo()              // low  endpoint of 95% confidence interval
	{
		return this.res_mean-1.96*this.res_stddev/Math.sqrt(T);
	}
	public double confidenceHi()              // high endpoint of 95% confidence interval
	{
		return this.res_mean+1.96*this.res_stddev/Math.sqrt(T);
	}
	   
	public static void main(String[] args)    // test client (described below) 
	{
		int N=StdIn.readInt();
		int T=StdIn.readInt();
		PercolationStats percolationStats= new PercolationStats(N,T);
		StdOut.println("mean = "+percolationStats.mean());
		StdOut.println("stddev = "+percolationStats.stddev());
		StdOut.println("95% confidence interval ="+percolationStats.confidenceLo()+", "+percolationStats.confidenceHi());
	}
}  
