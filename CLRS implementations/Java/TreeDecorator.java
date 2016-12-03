public abstract class TreeDecorator<T> implements Tree<T> {
    protected Tree<T> decoratedTree;
    protected int time;
    
    public TreeDecorator(Tree<T> tree) {
        this.decoratedTree = tree;
        this.time = 0;
    }
    
    public TreeDecorator(Tree<T> tree, T[] a) {
        this.decoratedTree = tree;
        this.time = 0;
        for (T i : a)
            this.insert(i);
    }
    
    public Node<T> root() {
        return this.decoratedTree.root();
    }
    
    public void root(Node<T> r) {
        this.decoratedTree.root(r);
    }
    
    public void insert(Tree<T> tree, Node<T> z) {
        this.decoratedTree.insert(tree, z);
    }
    
    public void insert(T k) {
        this.decoratedTree.insert(k);
    }
    
    public Node<T> search(Node<T> x, T k) {
        return this.decoratedTree.search(x, k);
    }
    
    public Node<T> search(T k) {
        return this.decoratedTree.search(k);
    }
    
    public Node<T> minium(Node<T> x) {
        return this.decoratedTree.minium(x);
    }
    
    public Node<T> minium() {
        return this.decoratedTree.minium();
    }
    
    public Node<T> maxium(Node<T> x) {
        return this.decoratedTree.maxium(x);
    }
    
    public Node<T> maxium() {
        return this.decoratedTree.maxium();
    }
    
    public Node<T> successor(Node<T> x) {
        return this.decoratedTree.successor(x);
    }
    
    public Node<T> successor(T k) {
        return this.decoratedTree.successor(k);
    }
    
    public Node<T> predecessor(Node<T> x) {
        return this.decoratedTree.predecessor(x);
    }
    
    public Node<T> predecessor(T k) {
        return this.decoratedTree.predecessor(k);
    }
    
    public void transplant(Tree<T> tree, Node<T> u, Node<T> v) {
        this.decoratedTree.transplant(tree, u, v);
    }
            
    public void delete(Tree<T> tree, Node<T> z) {
        this.decoratedTree.delete(tree, z);
    }
    
    public void delete(T k) {
        this.decoratedTree.delete(k);
    }
    
    public void inOrderWalk(Node<T> x) {
        this.decoratedTree.inOrderWalk(x);
    }
    
    public void preOrderWalk(Node<T> x) {
        this.decoratedTree.preOrderWalk(x);
    }
    
    public void postOrderWalk(Node<T> x) {
        this.decoratedTree.postOrderWalk(x);
    }
    
    public void printTree(Node<T> root) {
        this.decoratedTree.printTree(root);
    }
    
    public void printTree() {
        this.decoratedTree.printTree();
    }
}