import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] rq;
	private int length=0;
	
	public RandomizedQueue()                 // construct an empty randomized queue
	{
		rq=(Item[]) new Object[2];
	}
	public boolean isEmpty()                 // is the queue empty?
	{
		return length==0;
	}
	public int size()                        // return the number of items on the queue
	{
		return length;
	}
	private void resize(int Max)
	{
		assert Max>=length;
		Item[] temp = (Item[]) new Object[Max];
		for(int i=0;i<length;i++){
			temp[i]=rq[i];
		}
		rq=temp;
	}	
	public void enqueue(Item item)           // add the item
	{
		if(item == null){
			throw new java.lang.NullPointerException();
		}
		if(length== rq.length) resize(2*rq.length);
		rq[length++]=item;
	}
	public Item dequeue()                    // remove and return a random item
	{
		if(isEmpty()){
			throw new java.util.NoSuchElementException();
		}
		int index=(int)(Math.random()*length);
		Item item=rq[index];
		if(index !=length-1) rq[index]=rq[length-1];
		rq[length-1]=null;
		length--;
		if(length>0 && length==rq.length/4) resize(rq.length/2);
		return item;
	}
	public Item sample()                     // return (but do not remove) a random item
	{
		if(isEmpty()){
			throw new java.util.NoSuchElementException();
		}
		int index=(int)(Math.random()*length);
		Item item=rq[index];
		return item;
	}
	public Iterator<Item> iterator()         // return an independent iterator over items in random order
	{
		return new RQIterator();
	}
	private class RQIterator implements Iterator<Item>
	{
		private int index=0;
		private Item[] r;
		public RQIterator(){
			r=(Item[]) new Object[length];
			for(int i=0;i<length;i++)
				r[i]=rq[i];
			StdRandom.shuffle(r);
		}
		public boolean hasNext(){
			return index<length;
		}
		public void remove(){
			throw new java.lang.UnsupportedOperationException();
		}
		public Item next(){
			if(!hasNext()){
				throw new java.util.NoSuchElementException();
			}
				Item item= r[index++];
				return item;
			}
	}
}
