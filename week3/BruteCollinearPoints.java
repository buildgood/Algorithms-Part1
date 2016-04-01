import java.util.Arrays;
import java.util.ArrayList;

public class BruteCollinearPoints {
   
	private ArrayList<LineSegment> collinear=new ArrayList<LineSegment>();
	
   public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
	{
		if(points==null) throw new java.lang.NullPointerException();
		for(int i=0;i<points.length;i++)
		{
			if(points[i]==null) throw new java.lang.NullPointerException();
		}
		for(int i=0;i<points.length-1;i++)
			for(int j=i+1;j<points.length;j++)
			{
				if(points[i].compareTo(points[j])==0)
					throw new java.lang.IllegalArgumentException();
			}
		
		int N=points.length;
		Point[] temp =new Point[N];
		for(int j=0;j<N;j++){
				temp[j]=points[j];
			}
		Arrays.sort(temp);
		for(int i=0;i<=N-4;i++)
			for(int j=i+1;j<=N-3;j++)
				for(int k=j+1;k<=N-2;k++)
					for(int l=k+1;l<N;l++)
					{
						if(temp[i].slopeTo(temp[j])==temp[j].slopeTo(temp[k])&&
								temp[j].slopeTo(temp[k])==temp[k].slopeTo(temp[l]))
						{
							collinear.add(new LineSegment(temp[i],temp[l]));
						}
					}
		
	}
	public int numberOfSegments()        // the number of line segments
	{
		return collinear.size();
	}
	public LineSegment[] segments()                // the line segments
	{
		return collinear.toArray(new LineSegment[collinear.size()]);	
	}
}




