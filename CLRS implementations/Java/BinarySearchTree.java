import java.util.Vector;

public class BinarySearchTree<T extends Comparable<T>> implements Tree<T> {
    private BSTNode<T> root;
    private int size;
    
    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }
    
    public BinarySearchTree(T[] arr) {
        this();
        for (T k : arr)
            this.insert(k);
    }
    
    public int height() { 
        return (int) lg(this.size); 
    }
    
    public int size() { 
        return this.size; 
    }
    
    public Node<T> root() { return this.root; }
    
    public void root(Node<T> r) { this.root = (BSTNode<T>) r; }
    
    public Node<T> search(T k) { 
        return (BSTNode<T>) this.search(this.root, k);
    }
    
    public Node<T> minium() {
        return (BSTNode<T>) this.minium(this.root);
    }
    
    public Node<T> maxium() {
        return (BSTNode<T>) this.maxium(this.root);
    }
    
    
    public Node<T> successor(T k) {
        Node<T> x = this.search(k);
        return (BSTNode<T>) this.successor(x);
    }
    
    public Node<T> predecessor(T k) {
        Node<T> x = this.search(k);
        return (BSTNode<T>) this.predecessor(x);
    }
    
    public void insert(BSTNode<T> z) {
        this.insert(this, z);
    }
    
    public void insert(T k) {
        BSTNode<T> z = new BSTNode<T>(k);
        this.insert(z);
    }
    
    public void delete(BSTNode<T> z) {
        this.delete(this, z);
    }
    
    public void delete(T k) {
        BSTNode<T> z = (BSTNode<T>) this.search(k);
        this.delete(z);
    }
    
    public void inOrderWalk() { 
        this.inOrderWalk(this.root); 
        System.out.print("\n");
    }
    
    public void preOrderWalk() { 
        this.preOrderWalk(this.root); 
        System.out.print("\n");
    }
    
    public void postOrderWalk() { 
        this.postOrderWalk(this.root); 
        System.out.print("\n");
    }
    
    public void printTree() {
        this.printTree(this.root);
    }
    
    private static class BSTNode<T> implements Node<T> {
        private BSTNode<T> parent, left, right;
        private T key;
        
        public BSTNode(T key) {
            this.parent = null;
            this.left = null;
            this.right = null;
            this.key = key;
        }
        
        public BSTNode<T> parent() { return this.parent; }
        public BSTNode<T> left() { return this.left; }
        public BSTNode<T> right() { return this.right; }
        public void parent(Node<T> p) { this.parent = (BSTNode<T>) p; }
        public void left(Node<T> l) { 
            this.left = (BSTNode<T>) l; 
            if (l != null)
                l.parent(this);
        }
        public void right(Node<T> r) { 
            this.right = (BSTNode<T>) r; 
            if (r != null)
                r.parent(this);
        }
        public T key() { return this.key; }
        
        public String toString() {
            return "Parent: " + ((this.parent == null) ? "NULL" : this.parent.key) +
                "\nLeft Child: " + ((this.left == null) ? "NULL" : this.left.key) +
                "\nRight Child: " + ((this.right == null) ? "NULL" : this.right.key) +
                "\nKey: " + this.key;
        } 
    }
    
    //methods that can be used by subclasses
    public static double lg(int x) { return Math.log(x) / Math.log(2); } 
    
    public void inOrderWalk(Node<T> x) {
        if (x != null) {
            this.inOrderWalk(x.left());
            System.out.print(x.key() + " ");
            this.inOrderWalk(x.right());
        }
    }
    
    public void preOrderWalk(Node<T> x) {
        if (x != null) {
            System.out.print(x.key() + " ");
            this.preOrderWalk(x.left());
            this.preOrderWalk(x.right());
        }
    }
    
    public void postOrderWalk(Node<T> x) {
        if (x != null) {
            this.postOrderWalk(x.left());
            this.postOrderWalk(x.right());
            System.out.print(x.key() + " ");
        }
    }
    
    public void insert(Tree<T> tree, Node<T> z) {
        Node<T> y = null;
        Node<T> x = tree.root();
        
        while (x != null) {
            y = x;
            if (z.key().compareTo(x.key()) < 0)
                x = x.left();
            else 
                x = x.right();
        }
        
        z.parent(y);
        if (y == null)
            tree.root(z);
        else if (z.key().compareTo(y.key()) < 0)
            y.left(z);
        else 
            y.right(z);
        this.size++;
    }
    
    public Node<T> search(Node<T> x, T k) {
        while (x != null && k.compareTo(x.key()) != 0) {
            if (k.compareTo(x.key()) < 0)
                x = x.left();
            else
                x = x.right();
        }
        
        return x;
    }
    
    public Node<T> minium(Node<T> x) {
        while (x != null && x.left() != null)
            x = x.left();
        return x;
    }
    
    public Node<T> maxium(Node<T> x) {
        while (x != null && x.right() != null) {
            x = x.right();
        }
        return x;
    }
    
    public Node<T> successor(Node<T> x) {
        if (x.right() != null)
            return this.minium(x.right());
        Node<T> y = x.parent();
        while (y != null && x == y.right()) {
            x = y;
            y = y.parent();
        }
        return y;
    }
    
    public Node<T> predecessor(Node<T> x) {
        if (x.left() != null)
            return this.maxium(x.left());
        Node<T> y = x.parent();
        while (y != null && x == y.left()) {
            x = y;
            y = y.parent();
        }
        return y;
    }
    
    public void transplant(Tree<T> tree, Node<T> u, Node<T> v) {
        if (u.parent() == null) 
            tree.root(v);
        else if (u == u.parent().left())
            u.parent().left(v);
        else
            u.parent().right(v);
        if (v != null)
            v.parent(u.parent());
    }
     
    public void delete(Tree<T> tree, Node<T> z) {
        if (z.left() == null)
            this.transplant(tree, z, z.right());
        else if (z.right() == null)
            this.transplant(tree, z, z.left());
        else {
            Node<T> y = this.minium(z.right());
            if (y.parent() != z) {
                this.transplant(tree, y, y.right());
                y.right(z.right());
                y.right().parent(y);
            }
            this.transplant(tree, z, y);
            y.left(z.left());
            y.left().parent(y);
        }
    }
    
    public void printTree(Node<T> root) {
        Queue<Node<T>> bfs = new Queue<Node<T>>();
        
        Vector<Node<T>> leaves = new Vector<Node<T>>();
        
        bfs.add(root);
        
        while (!bfs.isEmpty()) {
            Node<T> current = bfs.poll();
            
            if (current == null) continue;
            
            bfs.add(current.left());
            bfs.add(current.right());
            
            if (current.parent() == null) {
                leaves = new Vector<Node<T>>();
                System.out.println("Root: " + current.key().toString());
                continue;
            } else if (leaves.contains(current.parent())) {
                leaves = new Vector<Node<T>>();
                leaves.add(current);
                System.out.print("\n");
            } else {
                leaves.add(current);
            }
            
            if (current == current.parent().right()) {
                System.out.print(current.parent().key().toString() + "->" +
                                 current.key().toString() + " ");
            } else {
                System.out.print(current.key().toString() + "<-" +
                                 current.parent().key().toString() + " ");
            }
        }
        
        System.out.print("\n");
    }
    
    // unit test
    public static void test() {
        Character[] a = { 'z', 'b', 'a', 'c', 'e', 'f', 'd', '1', 'p' };
        BinarySearchTree<Character> tree = new BinarySearchTree<Character>(a);
        tree.printTree();
        tree.delete('b');
        tree.printTree();
    }
    
    public static void main(String[] args) {
        test();
        
        BinarySearchTree<String> tree = new BinarySearchTree<String>();
        for (String s : args) 
            tree.insert(s);
        tree.printTree();
    }
}
    
    
    
    