import math

class MyQueue(object):
    def __init__(self, a=None):
        if not a:
            a = list()
        self.queue = a

    def poll(self):
        try:
            assert not self.isEmpty(), "Queue is empty"
            return self.queue.pop(0)
        except AssertionError as e:
            print e 

    def add(self, e):
        self.queue.append(e)

    def peek(self):
        try:
            assert not self.isEmpty(), "Queue is empty"
            return self.queue[0]
        except AssertionError as e:
            print e 

    def __len__(self):
        return len(self.queue)

    def isEmpty(self):
        return len(self) == 0

class MyStack(object):
    def __init__(self, a=None):
        if not a:
            a = list()
        self.stack = a

    def pop(self):
        try:
            assert not self.isEmpty(), "Stack is empty"
            return self.stack.pop()
        except AssertionError as e:
            print e 

    def push(self, e):
        self.stack.append(e)

    def peek(self):
        try:
            assert not self.isEmpty(), "Stack is empty"
            return self.stack[-1]
        except AssertionError as e:
            print e 

    def __len__(self):
        return len(self.stack)

    def isEmpty(self):
        return len(self) == 0
        

class Node(object):
    def __init__(self, key, parent=None, *nodes):
        self._key = key
        self._parent = parent
        self._children = []
        for n in nodes:
            if n:
                self.add(n)

    def __str__(self):
        return "Parent: %s\nKey: %s"%(str(self._parent), str(self._key))

    def __repr__(self):
        return str(self._key)

    def __iter__(self):
        for c in self.children:
            yield c

    @property
    def key(self):
        return self._key

    @key.setter
    def key(self, key):
        self._key = key

    @property
    def parent(self):
        return self._parent

    @parent.setter
    def parent(self, parent):
        if isinstance(parent, Node):
            self._parent = parent
        else:
            self._Parent = Node(parent, None, self)

    @property
    def children(self):
        return self._children

    def add(self, *keys):
        for key in keys:
            if isinstance(key, Node):
                self._children.append(key)
            else:
                child = Node(key, self)
                child.parent = self
                self._children.append(child)


class BSTNode(Node):
    def __init__(self, key, parent=None, left=None, right=None):
        #Node.__init__(self, key, parent)
        super(BSTNode, self).__init__(key, parent)
        self._left = None
        self._right = None
        if left:
            self.left = left
        if right:
            self.right = right

    def __str__(self):
        pk = lk = rk = None
        if self._parent:
            pk = self._parent.key
        if self._left:
            lk = self._left.key
        if self._right:
            rk = self._right.key
            
        return "Parent: %s\nLeft: %s\nRight: %s\nKey: %s"%(str(pk), str(lk), str(rk), str(self._key))

    @property
    def parent(self):
        return self._parent

    @parent.setter
    def parent(self, parent):
        if isinstance(parent, BSTNode):
            self._parent = parent
        else:
            self._Parent = BSTNode(parent, None, left=self)

    @property
    def left(self):
        return self._left

    @left.setter
    def left(self, left):
        if isinstance(left, BSTNode):
            left.parent = self
            self._left = left
        else:
            left = BSTNode(left, self)
            self._left = left
        self.add(left)

    @property
    def right(self):
        return self._right

    @right.setter
    def right(self, right):
        if isinstance(right, BSTNode):
            right.parent = self
            self._right = right
        else:
            right = BSTNode(right, self)
            self._right = right
        self.add(right)


class BinaryTree(object):
    def __init__(self, a):
        self.data = a
        self.sz = len(self.data)
        self.max_h = int(math.log(self.sz, 2)) + 1
        self.root = BSTNode(self.data[0])
        self._build_tree(self.root, 0)

    def print_in_order(self):
        self._printInOrder(self.root)

    def _left(self, i):
        return (i + 1) * 2 - 1

    def _right(self, i):
        return (i + 1) * 2

    def _parent(self, i):
        return i // 2

    def _build_tree(self, node, p):
        l = self._left(p)
        r = self._right(p)
        
        if (l < self.sz):
            left = BSTNode(self.data[l])
            node.left = left
            self._build_tree(node.left, l)

        if (r < self.sz):
            right = BSTNode(self.data[r])
            node.right = right
            self._build_tree(node.right, r)

    def _printInOrder(self, node):
        if (node is not None):
            self._printInOrder(node.left)
            print node
            self._printInOrder(node.right)

    def _printTree_by_array(self):
        h = 0
        self.record = []
        
        for h in range(self.max_h, -1, -1):
            max_n = pow(2, h)
            n = pow(2, h) - 1
            self.record = self.record + [' ' * (self.max_h - h)]
            for i in range(max_n):
                if n + i < self.sz and self.data[n+i] is not None:
                    self.record[self.max_h - h] += str(self.data[n+i])
                else:
                    self.record[self.max_h - h] += ''
                self.record[self.max_h - h] += ' ' * (self.max_h - h)

        for i in range(len(self.record)-1, -1, -1):
            print self.record[i]

    def printTree_by_node(self):
        self._printTree_by_node(self.root)

    def _printTree_by_node(self, root):
        bfs = MyQueue()
        
        h = 0
        leaves = [list()]
        bfs.add(root)

        while not bfs.isEmpty():
            current = bfs.poll()
            
            if current.parent and current.parent in leaves[h]:
                leaves.append([current])
                h += 1
            else:
                leaves[h].append(current)

            for child in current:
                bfs.add(child)

        pattern = pow(2, len(leaves)) - 1
        for ls in leaves:
            indent = ' ' * (pattern / (2 * len(ls)))
            sep = ' ' * (pattern / len(ls))
            s = indent
            for l in ls:
                s += repr(l) + sep

            print s


if __name__ == '__main__':
    a = [1, 2, 3, None, 4, 5]
    aa = [1 for i in range(127)]
    t = BinaryTree(aa)
    t.printTree_by_node()
    


    


    
    
