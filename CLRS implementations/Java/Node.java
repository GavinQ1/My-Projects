public interface Node<T> {
    public Node<T> parent();
    
    public Node<T> left();
    
    public Node<T> right();
    
    public T key();
    
    public void parent(Node<T> p);
    
    public void left(Node<T> l);
    
    public void right(Node<T> r);
}