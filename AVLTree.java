import java.util.LinkedList;
import java.util.Queue;

public class AVLTree<T extends Comparable<T>> {
    Node root;

    // INSERTION
    public void insert(T data) {
        root = insert(root, data);
    }

    private Node insert(Node root, T data) {
        if (root == null) {
            return new Node(data);
        }

        if (data.compareTo(root.data) < 0) {
            root.left = insert(root.left, data);
        } else if (data.compareTo(root.data) > 0) {
            root.right = insert(root.right, data);
        }
        updateHeight(root);
        return rotate(root);
    }

    // DELETION
    public void delete(T data) { 
        root = delete(root, data);
    }

    private Node delete(Node root, T data) {
        if (data.compareTo(root.data) < 0) {
            root.left = delete(root.left, data);
        } else if (data.compareTo(root.data) > 0) {
            root.right = delete(root.right, data);
        } else { // found the data to be deleted
            if (root.left == null) { // case: right child only or leaf node
                return root.right;
            } else if (root.right == null) { // case: left child only
                return root.left;
            } else { // case: two child
                root.data = getMin(root.right); // replace with the successor
                root.right = delete(root.right, root.data); // delete the successor (which is now a duplicate) from the right subtree
            }
        }
        updateHeight(root);
        return rotate(root);
    }

    // SEARCHING (iterative)
    public boolean contains(T data) {
        Node curr = root;
        while (curr != null) {
            if (data.compareTo(curr.data) == 0) {
                return true; // data found
            } else if (data.compareTo(curr.data) < 0) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        return false;
    }

    // updates the height (longest path to a leaf node) of a given node
    private void updateHeight(Node node) {
        int maxHeight = Math.max((node.left != null ? node.left.height : -1),
                                  (node.right != null ? node.right.height : -1));
        node.height = maxHeight + 1;
    }

    // gets the balance factor of a given node
    private int getBalanceFactor(Node node) {
        int leftHeight = (node.left != null ? node.left.height : -1);
        int rightHeight = (node.right != null ? node.right.height : -1);
        return leftHeight - rightHeight;
    }
        
    // ROTATION
    private Node rotate(Node node) {
        int balance = getBalanceFactor(node);
        if (balance > 1) { // left heavy
            if (getBalanceFactor(node.left) < 0) { // left-right case
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }
        if (balance < -1) { // right heavy
            if (getBalanceFactor(node.right) > 0) { // right-left case
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }
        return node;
    }

    // rotate left (right heavy)
    private Node rotateLeft(Node root) {
        // System.out.println("Rotating left " + root.data);
        Node pivot = root.right;
        root.right = pivot.left;
        pivot.left = root;
        updateHeight(root);
        updateHeight(pivot);
        return pivot; 
    }
    
    // rotate right (left heavy)
    private Node rotateRight(Node root) {
        // System.out.println("Rotating right " + root.data);
        Node pivot = root.left;
        root.left = pivot.right;
        pivot.right = root;
        updateHeight(root);
        updateHeight(pivot);
        return pivot;
    }


    // FOR TESTING ONLY

    // testing hehe (for checking if tree structure is correct)
    public void getChildren(T data) {
        Node curr = root;
        while (curr != null) {
            if (data.compareTo(curr.data) == 0) {
                System.out.println("Left: " + (curr.left != null ? curr.left.data : null));
                System.out.println("Right: " + (curr.right != null ? curr.right.data : null));
                return;
            } else if (data.compareTo(curr.data) < 0) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        System.out.println("Data is nonexistent.");
    }

    // testing for height checker
    public void getHeight(T data) {
        Node curr = root;
        while (curr != null) {
            if (data.compareTo(curr.data) == 0) {
                System.out.println(data + " height: " + curr.height);
                return;
            } else if (data.compareTo(curr.data) < 0) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        System.out.println("Data is nonexistent.");
    }
    
    // testing for depth checker
    public void getDepth(T data) {
        Node curr = root;
        int depth = 0;
        while (curr != null) {
            if (data.compareTo(curr.data) == 0) {
                System.out.println(data + " depth: " + depth);
                return;
            } else if (data.compareTo(curr.data) < 0) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
            depth++;
        }
        System.out.println("Data is nonexistent.");
    }


    // TRAVERSAL
    public void inorder() {
        inorder(root);
        System.out.println();
    }

    private void inorder(Node root) {
        if (root == null) {
            return;
        }

        inorder(root.left);
        System.out.print(root.data + " ");
        inorder(root.right);
    }

    public void preorder() {
        preorder(root);
        System.out.println();
    }

    private void preorder(Node root) {
        if (root == null) {
            return;
        }

        System.out.print(root.data + " ");
        preorder(root.left);
        preorder(root.right);
    }

    public void postorder() {
        postorder(root);
        System.out.println();
    }

    private void postorder(Node root) {
        if (root == null) {
            return;
        }

        postorder(root.left);
        postorder(root.right);
        System.out.print(root.data + " ");
    }

    public void levelorder() {
        if (root == null) {
            return;
        }

        levelorder(root);
    }

    private void levelorder(Node root) {
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            Node curr = q.poll();
            if (curr.left != null) {
                q.add(curr.left);
            }
            if (curr.right != null) {
                q.add(curr.right);
            }
            System.out.print(curr.data + " ");
        }
        System.out.println();
    }

    // get the maximum value of a given node subtree
    public T getMax(Node root) {
        if (root == null) {
            return null;
        }

        Node curr = root;
        while (curr.right != null) {
            curr = curr.right;
        }
        return curr.data;
    }

    // get the minimum value of a given node subtree
    public T getMin(Node root) {
        if (root == null) {
            return null;
        }

        Node curr = root;
        while (curr.left != null) {
            curr = curr.left;
        }
        return curr.data;
    }

    public boolean isEmpty() {
        return root == null;
    }

    // Node class structure for this AVL Tree implementation
    class Node {
        T data;
        int height;
        Node left;
        Node right;

        Node(T data) {
            this.data = data;
            this.height = 0;
        }
    }
}