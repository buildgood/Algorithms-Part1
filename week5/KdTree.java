import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.TreeSet;

public class KdTree {
	private static class kdNode
	{
		private kdNode leftNode;
		private kdNode rightNode;
		private final boolean vertical;
		private final double x;
		private final double y;
		public kdNode(final double x, final double y, final kdNode leftNode,
				final kdNode rightNode, final boolean vertical)
		{
			this.x = x;
			this.y = y;
			this.leftNode = leftNode;
			this.rightNode = rightNode;
			this.vertical = vertical;
		}
	}
	private static final RectHV container = new RectHV(0,0,1,1);
	private kdNode root;
	private int size;
	   public KdTree()                               // construct an empty set of points 
	   {
		   size = 0;
		   root = null;
	   }
	   public boolean isEmpty()                      // is the set empty? 
	   {
		   return size == 0;
	   }
	   public int size()                         // number of points in the set
	   {
		   return size;
	   }
	   public void insert(final Point2D p)              // add the point to the set (if it is not already in the set)
	   {
		   root = insert(root, p, true);
	   }
	   private kdNode insert(final kdNode node, final Point2D p, final boolean vertical)
	   {
		   if(node == null)
		   {
			   size++;
			   return new kdNode(p.x(),p.y(),null,null,vertical);
		   }else if(node.x == p.x() && node.y == p.y())
		   {
			   return node;
		   }else if(node.vertical && p.x() < node.x || !node.vertical && p.y() < node.y)
		   {
			   node.leftNode = insert(node.leftNode, p, !node.vertical);
		   }else
		   {
			   node.rightNode = insert(node.rightNode, p, !node.vertical);
		   }
		   return node;
	   }
	   public boolean contains(Point2D p)            // does the set contain point p? 
	   {
		   return contains(root, p.x(), p.y());
	   }
	   private boolean contains(kdNode node, double x, double y)
	   {
		   if(node == null) return false;
		   if(node.x == x && node.y == y) return true;
		   if(node.vertical && x <node.x || !node.vertical && y < node.y)
		   {
			   return contains(node.leftNode, x, y);
		   }else{
			   return contains(node.rightNode, x, y);
		   }
	   }
	   public void draw()                         // draw all points to standard draw 
	   {
		   StdDraw.setScale(0, 1);
		   StdDraw.setPenColor(StdDraw.BLACK);
		   StdDraw.setPenRadius();
		   container.draw();
		   draw(root, container);
	   }
	   private void draw(final kdNode node, final RectHV rect)
	   {
		   if(node == null) return;
		   StdDraw.setPenColor(StdDraw.BLACK);
		   StdDraw.setPenRadius(0.01);
		   new Point2D(node.x, node.y).draw();
		   Point2D min, max;
		   if(node.vertical)
		   {
			   StdDraw.setPenColor(StdDraw.RED);
			   min = new Point2D(node.x, rect.ymin());
			   max = new Point2D(node.x, rect.ymax());
		   }else{
			   StdDraw.setPenColor(StdDraw.BLUE);
			   min = new Point2D(rect.xmin(), node.y);
			   max = new Point2D(rect.xmax(), node.y);
		   }
		   StdDraw.setPenRadius();
		   min.drawTo(max);
		   draw(node.leftNode, leftRect(rect, node));
		   draw(node.rightNode, rightRect(rect, node));
	   }
	   private RectHV leftRect(final RectHV rect, final kdNode node)
	   {
		   if(node.vertical)
		   {
			   return new RectHV(rect.xmin(), rect.ymin(), node.x, rect.ymax());
		   }else{
			   return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.y);
		   }
	   }
	   private RectHV rightRect(final RectHV rect, final kdNode node)
	   {
		   if(node.vertical)
		   {
			   return new RectHV(node.x, rect.ymin(), rect.xmax(), rect.ymax());
		   }else{
			   return new RectHV(rect.xmin(), node.y, rect.xmax(), rect.ymax());
		   }
	   }
	   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
	   {
		   final TreeSet<Point2D> containSet = new TreeSet<Point2D>();
		   range(root, container, rect, containSet);
		   return containSet;
	   }
	   
	   private void range(final kdNode  node, final RectHV nrect, final RectHV rect,
			   final TreeSet<Point2D> containSet)
	   {
		   if(node == null) return;
		   if(rect.intersects(nrect))
		   {
			   final Point2D p = new Point2D(node.x, node.y);
			   if(rect.contains(p))
				   containSet.add(p);
			   range(node.leftNode, leftRect(nrect, node), rect, containSet);
			   range(node.rightNode, rightRect(nrect, node), rect, containSet);
		   }
	   }
	   public Point2D nearest(final Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
	   {
		   return nearest(root, container, p.x(), p.y(), null);
	   }
	   private Point2D nearest(final kdNode node, final RectHV rect,
			   final double x, final double y, final Point2D pointP)
	   {
		   if(node == null) return pointP;
		   double dq = 0.0;
		   double dr = 0.0;
		   RectHV left = null;
		   RectHV right = null;
		   final Point2D query = new Point2D(x,y);
		   Point2D nearest = pointP;
		   if(nearest != null)
		   {
			   dq = query.distanceSquaredTo(nearest);
			   dr = rect.distanceSquaredTo(query);
		   }
		   if(nearest == null || dq>dr)
		   {
			   final Point2D point = new Point2D(node.x, node.y);
			   if(nearest == null || dq>query.distanceSquaredTo(point))
				   nearest = point;
			   if (node.vertical) {  
	                left = new RectHV(rect.xmin(), rect.ymin(), node.x, rect.ymax());  
	                right = new RectHV(node.x, rect.ymin(), rect.xmax(), rect.ymax());  
	  
	                if (x < node.x) {  
	                    nearest = nearest(node.leftNode, left, x, y, nearest);  
	                    nearest = nearest(node.rightNode, right, x, y, nearest);  
	                } else {  
	                    nearest = nearest(node.rightNode, right, x, y, nearest);  
	                    nearest = nearest(node.leftNode, left, x, y, nearest);  
	                }  
	            } else {  
	                left = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.y);  
	                right = new RectHV(rect.xmin(), node.y, rect.xmax(), rect.ymax());  
	  
	                if (y < node.y) {  
	                    nearest = nearest(node.leftNode, left, x, y, nearest);  
	                    nearest = nearest(node.rightNode, right, x, y, nearest);  
	                } else {  
	                    nearest = nearest(node.rightNode, right, x, y, nearest);  
	                    nearest = nearest(node.leftNode, left, x, y, nearest);  
	                }  
	            }  
		   }
		   return nearest;
	   }
	 
}
