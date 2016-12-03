public class TreeDebugger<T extends Comparable<T>> extends TreeDecorator<T> {
    
    public TreeDebugger(Tree<T> tree) {
        super(tree);
    }
    
    public TreeDebugger(Tree<T> tree, T[] a) {
        this(tree);
        for (T i : a)
            this.insert(i);
    }
    
    public void insert(T k) {
        System.out.println("Squence " + this.time++ + " --- Insert(" + k + "): \n");
        this.decoratedTree.insert(k);
        this.decoratedTree.printTree();
        System.out.print("\n");
    }
    
    public Node<T> root() {
        return this.decoratedTree.root();
    }
    
    public void root(Node<T> r) {
        this.decoratedTree.root(r);
    }
    
    @Override
    public void insert(Tree<T> tree, Node<T> z) {
        this.decoratedTree.insert(tree, z);
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
    
    public static void test() {
        Integer[] a = { 62, 18, 46, 42, 39, 3, 10 };
        TreeDebugger<Integer> AVLdebugger = new TreeDebugger<Integer>(new AVLTree<Integer>(), a);
    }
    
    public static void main(String[] args) {
        test();
    }
}
        
    