import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
  
	private Node first;
	private Node last;
	private int length;
	
	private class Node
	{
		Item item;
		Node pre;
		Node next;
	}
	
	public Deque()                           // construct an empty deque
	{
		first=null;
		last=null;
		length=0;
	}
	public boolean isEmpty()                 // is the deque empty?
	{
		return length==0;
	}
	public int size()                        // return the number of items on the deque
	{
		return length;
	}
	public void addFirst(Item item)          // add the item to the front
	{
		if(item == null)
			throw new java.lang.NullPointerException();
		Node oldfirst=first;
		first=new Node();
		first.item=item;
		first.next=oldfirst;
		if(isEmpty()) last=first;
		else oldfirst.pre=first;
		length++;
	}
	public void addLast(Item item)           // add the item to the end
	{
		if(item== null)
			throw new java.lang.NullPointerException();
		Node oldlast=last;
		last= new Node();
		last.item=item;
		last.pre= oldlast;
		if(isEmpty()) first=last;
		else oldlast.next=last;
		length++;
	}
	public Item removeFirst()                // remove and return the item from the front
	{
		if(isEmpty())
		{
			throw new java.util.NoSuchElementException();
		}
		Item item=first.item;
		first=first.next;
		length--;
		if(isEmpty()){
			last=null;
		}else{
			first.pre=null;
		}
		return item;
	}	
	public Item removeLast()                 // remove and return the item from the end
	{
		if(isEmpty())
		{
			throw new java.util.NoSuchElementException();
		}
		Item item=last.item;
		last=last.pre;
		length--;
		if(isEmpty()){
			first=null;
		}else{
			last.next=null;
		}
		return item;
	}
	public Iterator<Item> iterator()         // return an iterator over items in order from front to end
	{
		return new ListIterator();
	}
	private class ListIterator implements Iterator<Item>
	{
		private Node current=first;
		public boolean hasNext(){
			return current != null;
		}
		public void remove(){
			throw new java.lang.UnsupportedOperationException();
		}
		public Item next(){
			if(!hasNext()){
				throw new java.util.NoSuchElementException();
			}
			Item item=current.item;
			current=current.next;
			return item;
		}
		
	}
	public static void main(String[] args)   // unit testing
	{
		Deque<String> deque= new Deque<String> ();
		while(!StdIn.isEmpty()){
			String s=StdIn.readString();
			if(!s.equals("-")){
				StdOut.println("1->deque.size()="+deque.size());
				deque.addFirst(s);
				StdOut.println("2->deque.size()="+deque.size());
			}
			else if(!deque.isEmpty()){
				StdOut.println(deque.removeFirst()+" ");
				StdOut.println("3->deque.size()="+deque.size());
			}
		}
		StdOut.println("("+deque.size()+" left on the deque)");
	}
}