public interface Tree<T> {
    public Node<T> root();   
    public void root(Node<T> r);
    
    public void insert(Tree<T> tree, Node<T> z);
    public void insert(T k);
    
    public Node<T> search(Node<T> x, T k);
    public Node<T> search(T k);
    
    public Node<T> minium(Node<T> x);
    public Node<T> minium();
    
    public Node<T> maxium(Node<T> x);
    public Node<T> maxium();
    
    public Node<T> successor(Node<T> x);
    public Node<T> successor(T k);
    
    public Node<T> predecessor(Node<T> x);
    public Node<T> predecessor(T k);
    
    public void transplant(Tree<T> tree, Node<T> u, Node<T> v);
    public void delete(Tree<T> tree, Node<T> z);
    public void delete(T k);
    
    public void inOrderWalk(Node<T> x);
    public void preOrderWalk(Node<T> x);
    public void postOrderWalk(Node<T> x);
    
    public void printTree(Node<T> root);
    public void printTree();
}