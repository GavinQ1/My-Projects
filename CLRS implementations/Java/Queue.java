import java.util.NoSuchElementException;

public class Queue<T> {
    private int size;
    private Node<T> first, last;
    
    public Queue() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }
    
    private static class Node<T> {
        private T item;
        private Node<T> next;
        
        public Node(T item) {
            this.item = item;
            this.next = null;
        }
    }
    
    public boolean isEmpty() {
        return this.first == null;
    }
    
    public int size() {
        return this.size;
    }
    
    public void add(T item) {
        Node<T> oldLast = this.last;
        this.last = new Node<T>(item);
        if (this.isEmpty()) this.first = this.last;
        else oldLast.next = this.last;
        this.size++;
    }
    
    public T poll() {
        if (this.isEmpty())
            throw new NoSuchElementException("Queue is empty.");
        
        this.size--;
        T item = this.first.item;
        this.first = this.first.next;
        if (this.isEmpty()) this.last = null;
        return item;
    }
    
    public T peek() {
        if (this.isEmpty())
            throw new NoSuchElementException("Queue is empty.");
        
        return this.first.item;
    }
    
    public static void main(String[] args) {
        Queue<Integer> q = new Queue<Integer>();
        q.add(1);
        q.add(2);
        System.out.println(q.peek());
        System.out.println(q.poll());
        q.add(1);
        System.out.println(q.poll());
        System.out.println(q.poll());
        System.out.println(q.poll());
    }
}
        