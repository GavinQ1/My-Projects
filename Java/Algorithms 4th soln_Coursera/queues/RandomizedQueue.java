import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] rq;
    private int N;
    private int last;
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        N = 0;
        last = -1;
        rq = (Item[]) new Object[2];
    }
    
    // is the queue empty?
    public boolean isEmpty() {
        return N == 0;
    }
    
    // return the number of items on the queue
    public int size() {
        return N;
    }
    
    private void resize(int max) {
        assert max >= N;
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) {
            temp[i] = rq[i];
        }
        rq = temp;
        last  = N - 1;
    }
  
    
    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException("Item cannot be null");
        if (N == rq.length) 
            resize(2*rq.length);  
        rq[N] = item;                        // add item
        if (last == rq.length) 
            last = 0;         
        N++;
        last++;
    }

    
    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) 
            throw new NoSuchElementException("Next item does not exist");
        
        int r = StdRandom.uniform(N);
        Item res = rq[r];
        return res;
    }
            

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) 
            throw new NoSuchElementException("Randomized Queue underflow");
        
        int r = StdRandom.uniform(N);
        Item res = rq[r];
        rq[r] = rq[last];
        rq[last] = null;                            // to avoid loitering
        N--;
        last--;
        if (N > 0 && N == rq.length/4) 
            resize(rq.length/2); 
        return res;
    }
    
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int size;
        private int aLast;
        private Item[] copy;
        
        public ArrayIterator() {
            size = N;
            aLast = last;
            copy = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) 
                copy[i] = rq[i];
        }
            
        public boolean hasNext() { 
            return size > 0;         
        }
        
        public void remove() {
            throw new UnsupportedOperationException(); 
        }

        public Item next() {
            if (!hasNext()) 
                throw new NoSuchElementException();
            
            int r = StdRandom.uniform(size);
            Item item = copy[r];
            copy[r] = copy[aLast];
            copy[aLast] = null;
            aLast--;
            size--;
            return item;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Test for random dequeue:");
        RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();
        test.enqueue(1);
        test.enqueue(2);
        test.enqueue(3);
        test.enqueue(4);
        test.enqueue(5);
        test.enqueue(6);
        test.enqueue(7);
        test.enqueue(8);
        for (int i = 0; i < 8; i++) {
            Integer res = test.dequeue();
            System.out.println("Result: " + res +
                               " Size: " + test.size());
        }
        
        System.out.println("\nTest for iterator 1:");
        test = new RandomizedQueue<Integer>();
        test.enqueue(1);
        test.enqueue(2);
        test.enqueue(3);
        test.enqueue(4);
        test.enqueue(5);
        test.enqueue(6);
        test.enqueue(7);
        test.enqueue(8);
        for (Integer i : test) {
            System.out.println(i);
        }
        System.out.println("\nTest for iterator 2:\n");
        for (Integer i : test) {
            System.out.println(i);
        }
        System.out.println("==================");
        for (Integer i : test) {
            System.out.println(i);
        }
        System.out.println("==================");
        RandomizedQueue<Integer> test2 = new RandomizedQueue<Integer>();
        int trial = 40;
        while (trial > 0) {
            trial--;
            if (StdRandom.uniform(40) > 20) 
                test2.enqueue(StdRandom.uniform(100));
            else if (!test2.isEmpty()) 
                System.out.println(test2.dequeue());
        }
        System.out.println("==================");
        RandomizedQueue<Integer> test3 = new RandomizedQueue<Integer>();
        test3.enqueue(1);
        Iterator<Integer> i = test3.iterator();
        System.out.println(i.next());
        //i.next();
    }
}
