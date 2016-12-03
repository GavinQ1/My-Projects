import java.util.Vector;

public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {
    private AVLNode<T> root;
    private int size;
    
    public AVLTree() {
        this.root = null;
        this.size = 0;
    }
    
    public AVLTree(T[] arr) {
        this();
        for (T k : arr)
            this.insert(k);
    }
    
    public Node<T> root() { 
        return this.root; 
    }
    
    public void root(Node<T> r) { 
        this.root = (AVLNode<T>) r; 
    }
    
    public int height() { 
        return (int) lg(this.size); 
    }
    
    public int size() { 
        return this.size; 
    }
    
    public Node<T> search(T k) { 
        return (AVLNode<T>) this.search(this.root, k);
    }
    
    public Node<T> minium() {
        return (AVLNode<T>) this.minium(this.root);
    }
    
    public Node<T> maxium() {
        return (AVLNode<T>) this.maxium(this.root);
    }
    
    public Node<T> successor(T k) {
        Node<T> x = this.search(k);
        return (AVLNode<T>) this.successor(x);
    }
    
    public Node<T> predecessor(T k) {
        Node<T> x = this.search(k);
        return (AVLNode<T>) this.predecessor(x);
    }
    
    public void insert(AVLNode<T> z) {
        this.insert(this, z);
        this.reBalance(z.parent);       
    }
    
    public void insert(T k) {
        AVLNode<T> z = new AVLNode<T>(k);
        this.insert(z);
    }
    
    public void delete(AVLNode<T> z) {
        AVLNode<T> p = z.parent;
        
        this.delete(this, z);
        
        
        //four cases in fact
        if (p == null) { // delete the root
            if (z.key.compareTo(this.root.key) < 0)
                this.reBalance(this.root.left);
            else
                this.reBalance(this.root.right);
        } else {
            if (z.key.compareTo(p.key) >= 0) {
                if (p.left != null)
                    this.reBalance(p.left);
                else
                    this.reBalance(p.parent.left);
            } else {
                if (p.right != null)
                    this.reBalance(p.right);
                else
                    this.reBalance(p.parent.right);
            }
        }
    }
    
    public void delete(T k) {
        AVLNode<T> z = (AVLNode<T>) this.search(k);
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
    
    public void printTree(AVLNode<T> root) {
        Queue<AVLNode<T>> bfs = new Queue<AVLNode<T>>();
        
        Vector<AVLNode<T>> leaves = new Vector<AVLNode<T>>();
        
        bfs.add(root);
        
        while (!bfs.isEmpty()) {
            AVLNode<T> current = bfs.poll();
            
            if (current == null) continue;
            
            bfs.add(current.left());
            bfs.add(current.right());
            
            if (current.parent() == null) {
                leaves = new Vector<AVLNode<T>>();
                System.out.println("Root: " + current.key().toString() + "(" + current.balance() + ")");
                continue;
            } else if (leaves.contains(current.parent())) {
                leaves = new Vector<AVLNode<T>>();
                leaves.add(current);
                System.out.print("\n");
            } else {
                leaves.add(current);
            }
            
            if (current == current.parent().right()) {
                System.out.print(current.parent().key().toString() + "->" +
                                 current.key().toString() + "(" + current.balance() + ") ");
            } else {
                System.out.print(current.key().toString() + "(" + current.balance() + ")<-" +
                                 current.parent().key().toString() +  " ");
            }
        }
        
        System.out.print("\n");
    }
        
    public void printTree() {
        this.printTree(this.root);
    }
    
    private void rRotate(AVLNode<T> p, AVLNode<T> y) {
       this.transplant(this, p, y);
       
       //make it general
       p.left = y.right;
       if (y.right != null)
           y.right.parent = p;
       
       y.right = p;
       p.parent = y;
       p.height = p.height();
       y.height = y.height();
    }
    
    private void lRotate(AVLNode<T> p, AVLNode<T> y) {
       this.transplant(this, p, y);
       
       //make it general
       p.right = y.left;
       if (y.left != null)
           y.left.parent = p;
       
       y.left = p;
       p.parent = y;
       p.height = p.height();
       y.height = y.height();
    }
    
    private void lrRotate(AVLNode<T> y, AVLNode<T> c) {
        this.lRotate(y, c);
        this.rRotate(c.parent, c);
    }
    
    private void rlRotate(AVLNode<T> y, AVLNode<T> c) {
        this.rRotate(y, c);
        this.lRotate(c.parent, c);
    }
    
    private void reBalance(AVLNode<T> y) {
        while (y != null && y.parent != null) {
            y.height = y.height();
            
            if ((y.balance() == 1 || y.balance() == 0) && y.parent.balance() >= 2)
                this.rRotate(y.parent, y);
            else if ((y.balance() == -1 || y.balance() == 0) && y.parent.balance() <= -2)
                this.lRotate(y.parent, y);
            else if (y.balance() == -1 && y.parent.balance() >= 2)
                this.lrRotate(y, y.right);
            else if (y.balance() == 1 && y.parent.balance() <= -2)
                this.rlRotate(y, y.left);
            
            y = y.parent;
        }
    }

    private static class AVLNode<T> implements Node<T> {
        private AVLNode<T> parent, left, right;
        private T key;
        private int height;
        
        public AVLNode(T key) {
            this.parent = null;
            this.left = null;
            this.right = null;
            this.key = key;
            this.height = 0;
        }
        
        public AVLNode<T> parent() { return this.parent; }
        public AVLNode<T> left() { return this.left; }
        public AVLNode<T> right() { return this.right; }
        public void parent(Node<T> p) { this.parent = (AVLNode<T>) p; }
        public void left(Node<T> l) { 
            this.left = (AVLNode<T>) l; 
            if (l != null)
                l.parent(this);
        }
        public void right(Node<T> r) { 
            this.right = (AVLNode<T>) r; 
            if (r != null)
                r.parent(this);
        }
        public T key() { return this.key; }
        
        public int height() {
            int l, r;
            if (this.left == null)
                l = -1;
            else
                l = this.left.height();
            
            if (this.right == null)
                r = -1;
            else
                r = this.right.height();
            
            return ((r >= l) ? r : l) + 1;
        }
        
        public int balance() {
            int l, r;
            if (this.left == null)
                l = -1;
            else
                l = this.left.height();
            
            if (this.right == null)
                r = -1;
            else
                r = this.right.height();
            
            return l - r;
        }
        
        public String toString() {
            return "Key: " + this.key + 
                "\nParent: " + ((this.parent == null) ? "NULL" : this.parent.key) +
                "\nLeft Child: " + ((this.left == null) ? "NULL" : this.left.key) +
                "\nRight Child: " + ((this.right == null) ? "NULL" : this.right.key) +
                "\nHeight: " + this.height() +
                "\nBalance Factor: " + this.balance();
        } 
    }
    
    // unit test
    public static void test() {
        //Integer[] a = { 9, 5, 8, 3, 2, 4, 7, 16, 18, 2};
        Integer[] a = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 8};
        //Integer[] a = { 9, 5, 6, 3, 2 };
        AVLTree<Integer> tree = new AVLTree<Integer>(a);
        tree.printTree();
        //tree.delete(6);
        //tree.printTree();
        //System.out.println(tree.search(4));
        System.out.println("\n" + tree.root + "\n"); 
    }
    
    public static void main(String[] args) {
        //test();
        
        AVLTree<String> tree = new AVLTree<String>();
        for (String s : args) 
            tree.insert(s);
        tree.printTree();
     
    }
}