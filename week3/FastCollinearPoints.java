import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
   
	private ArrayList<LineSegment> collinear=new ArrayList<LineSegment>();
	
	public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
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
		for(int i=0;i<N;i++)
		{
			Arrays.sort(temp);
			Arrays.sort(temp, points[i].slopeOrder());
			for (int j = 1; j < N - 2; j++)   
            {  
                if (temp[0].compareTo(temp[j]) < 0  && temp[j].compareTo(temp[j + 1]) < 0  
                        && temp[j + 1].compareTo(temp[j + 2]) < 0)   
                {  
                    int k = -1;  
                    if (temp[0].slopeTo(temp[j-1])!=temp[j-1].slopeTo(temp[j])
                    		&&temp[0].slopeTo(temp[j]) == temp[j].slopeTo(temp[j + 1])  
                            && temp[j].slopeTo(temp[j + 1]) == temp[j + 1].slopeTo(temp[j + 2]))   
                    {  
                            k = j + 2;  
                            while ((k + 1) < N &&temp[k].compareTo(temp[k + 1]) < 0 &&  
                            		temp[k - 1].slopeTo(temp[k]) == temp[k].slopeTo(temp[k + 1]))  
                                k++;  
                    }  
                    if(k != -1)  
                    {  
                    	collinear.add(new LineSegment(temp[0],temp[k]));
                    }  
                }
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
