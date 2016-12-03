import java.util.Iterator;
import java.util.NoSuchElementException;

public class Bag<T> implements Iterable<T> {
	private Node<T> head;
	private int size;

	private static class Node<T> {
		private T val;
		private Node<T> next;

		public Node(T val) {
			this.val = val;
			this.next = null;
		}
	}

	public Bag() {
		this.head = null;
		this.size = 0;
	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.head == null;
	}

	public void add(T val) {
		Node<T> old = this.head;
		this.head = new Node<T>(val);
		this.head.next = old; 
		this.size++;
	}


    public Iterator<T> iterator() {
    	return new ListIterator<T>(this.head);
    }

    private class ListIterator<T> implements Iterator<T> {
    	private Node<T> current;

    	public ListIterator(Node<T> head) {
    		this.current = head;
    	}

    	public boolean hasNext() {
    		return this.current != null;
    	}

    	public void remove() {
    		throw new UnsupportedOperationException();
    	}

    	public T next() {
    		if (!this.hasNext())
    			throw new NoSuchElementException();
    		T res = this.current.val;
    		this.current = this.current.next;
    		return res;
    	}
    }

    public static void main(String[] args) {
        Bag<Integer> bag = new Bag<Integer>();
        for (int i = 0; i < 4; i++)
        	bag.add(i);

        System.out.println("size of bag = " + bag.size());
        for (Integer i : bag) {
            System.out.println(i);
        }
    }
}
